package com.kaiwait.webservice.jczh;


import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import com.kaiwait.bean.jczh.io.PaytrnInput;
import com.kaiwait.core.process.SingleFunctionIF;
import com.kaiwait.core.process.ValidateResult;
import com.kaiwait.service.jczh.CommonMethedService;
import com.kaiwait.service.jczh.PaytrnService;

@Component("PaytrnApprovalBack")

public class PaytrnApprovalBack implements SingleFunctionIF<PaytrnInput>{
	
	@Resource
	private PaytrnService PaytrnService;
	@Resource
	private CommonMethedService commonMethedService;
	
	@Override
	public Object process(PaytrnInput inputParam) {
		String companycd =inputParam.getCompanyID();
		inputParam.getPaytrn().setCompany_cd(companycd);
		String userid =inputParam.getUserID();
		inputParam.getPaytrn().setUpusercd(userid);
		int ss =PaytrnService.PaytrnApprovalBack(inputParam);
		return ss;
		
		 
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
