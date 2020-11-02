package com.kaiwait.webservice.jczh;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kaiwait.bean.jczh.entity.Cost;
import com.kaiwait.bean.jczh.io.TrantrnInput;
import com.kaiwait.core.process.SingleFunctionIF;
import com.kaiwait.core.process.ValidateResult;
import com.kaiwait.service.jczh.CommonMethedService;
import com.kaiwait.service.jczh.CostService;
import com.kaiwait.service.jczh.TrantrnService;

@Component("TrantrnQuery")

public class TrantrnQuery implements SingleFunctionIF<TrantrnInput>{
	
	@Resource
	private TrantrnService TrantrnService;
	@Resource
	private CommonMethedService commonMethedService;
	@Resource
	private CostService costService;
	@Override
	public Object process(TrantrnInput inputParam) {
		String companycd =inputParam.getCompanyID();
		inputParam.getTrantrn().setCompany_cd(companycd);
		Map<String,Object> outsoure = new  HashMap<>();
		String jobcd = inputParam.getTrantrn().getJob_cd();
		TrantrnInput s=  TrantrnService.TrantrnQueryList(inputParam);
		outsoure.put("TrantrnInput", s);
		List<Cost> sumcost  =commonMethedService.getSumcost(Integer.parseInt(companycd),jobcd);
		if(!"".equals(sumcost.get(0).getUpdatetime())) {
			sumcost.get(0).setUpdatetime(commonMethedService.getTimeByZone(sumcost.get(0).getUpdatetime(),companycd));		
		}
		outsoure.put("sumcost", sumcost);	
		List<Cost> CLDIV=	costService.selectCLDIV(jobcd,companycd);
		if(!"".equals(CLDIV.get(0).getUpdate())) {
			CLDIV.get(0).setUpdate(commonMethedService.getTimeByZone(CLDIV.get(0).getUpdate(),companycd));	
		}
		outsoure.put("CLDIV", CLDIV);		
		return outsoure;
		
		 
	}

	public ValidateResult validate(TrantrnInput inputParam) {		
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class getParamType() {
		// TODO Auto-generated method stub
		return TrantrnInput.class;
	}


}
