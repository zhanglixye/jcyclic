package com.kaiwait.webservice.jczh;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kaiwait.bean.jczh.entity.Cost;
import com.kaiwait.bean.jczh.entity.Prooftrn;
import com.kaiwait.bean.jczh.io.LendtrnInput;
import com.kaiwait.core.process.SingleFunctionIF;
import com.kaiwait.core.process.ValidateResult;
import com.kaiwait.service.jczh.CommonMethedService;
import com.kaiwait.service.jczh.CostService;
import com.kaiwait.service.jczh.LendtrnService;

@Component("LendtrnQuery")

public class LendtrnQuery implements SingleFunctionIF<LendtrnInput>{
	
	@Resource
	private LendtrnService LendtrnService;
	@Resource
	private CommonMethedService commonMethedService;
	@Resource
	private CostService costService;
	
	@Override
	public Object process(LendtrnInput inputParam) {
		String companycd =inputParam.getCompanyID();
		inputParam.getLendtrn().setCompany_cd(companycd);
		Map<String,Object> outsoure = new  HashMap<>();
		String jobcd = inputParam.getLendtrn().getJob_cd();
		LendtrnInput s=  LendtrnService.LendtrnQueryList(inputParam);
		outsoure.put("LendtrnInput", s);
		List<Cost> sumcost  =commonMethedService.getSumcost(Integer.parseInt(companycd),jobcd);
		if(!"".equals(sumcost.get(0).getUpdatetime())) {
			sumcost.get(0).setUpdatetime(commonMethedService.getTimeByZone(sumcost.get(0).getUpdatetime(),companycd));	
		}
		outsoure.put("sumcost", sumcost);	
		List<Cost> CLDIV=	costService.selectCLDIV(jobcd,companycd);
		if(!"".equals(CLDIV.get(0).getUpddate())) {
			CLDIV.get(0).setUpddate(commonMethedService.getTimeByZone(CLDIV.get(0).getUpddate(),companycd));	
		}
		outsoure.put("CLDIV", CLDIV);
		List<Prooftrn> prooftrn  =LendtrnService.getProoftrnList(companycd,inputParam.getLendtrn().getInput_no());
		outsoure.put("prooftrn", prooftrn);
		String rate = commonMethedService.getPayeetaxRate("902",companycd);
		outsoure.put("rate", rate);
		String status = LendtrnService.QueryJobstatus(jobcd,companycd);
		String jobstatus="0";
		if(status==null) {
			jobstatus="1";
		}
		outsoure.put("jobstatus", jobstatus);
		return outsoure;
		
		 
	}

	public ValidateResult validate(LendtrnInput inputParam) {		
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class getParamType() {
		// TODO Auto-generated method stub
		return LendtrnInput.class;
	}


}
