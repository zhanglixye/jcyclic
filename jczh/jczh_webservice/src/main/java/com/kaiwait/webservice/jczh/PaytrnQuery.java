package com.kaiwait.webservice.jczh;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kaiwait.bean.jczh.entity.Cost;
import com.kaiwait.bean.jczh.entity.Prooftrn;
import com.kaiwait.bean.jczh.io.PaytrnInput;
import com.kaiwait.core.process.SingleFunctionIF;
import com.kaiwait.core.process.ValidateResult;
import com.kaiwait.mappers.jczh.CostMapper;
import com.kaiwait.service.jczh.CommonMethedService;
import com.kaiwait.service.jczh.PaytrnService;
import com.kaiwait.thrift.common.server.annotation.MatchEnum;
import com.kaiwait.thrift.common.server.annotation.Privilege;
@Privilege(keys= {"53"}, match=MatchEnum.ANY)
@Component("PaytrnQuery")

public class PaytrnQuery implements SingleFunctionIF<PaytrnInput>{
	
	@Resource
	private PaytrnService PaytrnService;
	@Resource
	private CommonMethedService commonMethedService;
	@Resource
	private CostMapper costMapper;
	@Override
	public Object process(PaytrnInput inputParam) {
		String companycd =inputParam.getCompanyID();
		inputParam.getPaytrn().setCompany_cd(companycd);
		String jobcd = inputParam.getPaytrn().getJob_cd();
		Map<String,Object> outsoure = new  HashMap<>();
		PaytrnInput s=  PaytrnService.PaytrnQueryList(inputParam);
		outsoure.put("PaytrnInput", s);
		List<Cost> sumcost  =commonMethedService.getSumcost(Integer.parseInt(companycd),jobcd);
		if(!"".equals(sumcost.get(0).getUpdatetime())) {
			sumcost.get(0).setUpdatetime(commonMethedService.getTimeByZone(sumcost.get(0).getUpdatetime(),companycd));		
		}
		outsoure.put("sumcost", sumcost);	
		List<Cost> CLDIV=	costMapper.selectCLDIV(jobcd,companycd);
		String OrderCLDIV = costMapper.selectOrderCLDIV(inputParam.getPaytrn().getInput_no(),companycd);
		if(!"".equals(CLDIV.get(0).getUpddate())) {
            CLDIV.get(0).setUpddate(commonMethedService.getTimeByZone(CLDIV.get(0).getUpddate(),companycd));        
		}
		CLDIV.get(0).setOrderName(OrderCLDIV);
		outsoure.put("CLDIV", CLDIV);
		List<Prooftrn> prooftrn  =PaytrnService.getProoftrnList(companycd,inputParam.getPaytrn().getInput_no());
		outsoure.put("prooftrn", prooftrn);
		Map<String,Object> remark  =PaytrnService.getRemarkList(companycd,inputParam.getPaytrn().getInput_no(),inputParam.getPaytrn().getJob_cd());
		outsoure.put("remark", remark);
		Map<String,Object> clmst  =PaytrnService.getClmstList(companycd,inputParam.getPaytrn().getInput_no(),inputParam.getPaytrn().getJob_cd());
		outsoure.put("clmst", clmst);
		return outsoure;
		
		 
	}

	public ValidateResult validate(PaytrnInput inputParam) {		
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class getParamType() {
		// TODO Auto-generated method stub
		return PaytrnInput.class;
	}


}
