package com.kaiwait.webservice.mst;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kaiwait.bean.mst.io.PayeeTaxAddInput;
import com.kaiwait.core.process.SingleFunctionIF;
import com.kaiwait.core.process.ValidateResult;
import com.kaiwait.service.mst.PayeeTaxService;

@Component("PayeeChangeinit")
public class PayeeChangeinit  implements SingleFunctionIF<PayeeTaxAddInput> {

	@Resource
	private PayeeTaxService PayeeTaxService;

	@Override
	public PayeeTaxAddInput process(PayeeTaxAddInput inputParam) {
	return PayeeTaxService.initSalescategory(inputParam);	
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
