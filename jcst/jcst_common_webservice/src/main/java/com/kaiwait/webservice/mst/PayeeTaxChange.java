package com.kaiwait.webservice.mst;


import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kaiwait.bean.mst.io.PayeeTaxAddInput;
import com.kaiwait.core.process.SingleFunctionIF;
import com.kaiwait.core.process.ValidateResult;
import com.kaiwait.service.mst.PayeeTaxService;

@Component("PayeeTaxChange")
public class PayeeTaxChange  implements SingleFunctionIF<PayeeTaxAddInput> {

	@Resource
	private PayeeTaxService PayeeTaxService;
	
	@Override
	public  Object process(PayeeTaxAddInput inputParam) {
		//String UserID = inputParam.getUserID();
		String CompanyID = inputParam.getCompanyID();
		inputParam.getPayeetaxmst().setCompany_cd(CompanyID);
		return PayeeTaxService.updatePayeetaxmstTx(inputParam.getPayeetaxmst());
	}
	@SuppressWarnings("rawtypes")
	@Override
	public Class getParamType() {
		return PayeeTaxAddInput.class;
	}

	@Override
	public ValidateResult validate(PayeeTaxAddInput inputParam) {
		// TODO Auto-generated method stub
		return null;
	}

}
