package com.kaiwait.webservice.jczh;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kaiwait.bean.jczh.io.PayInput;
import com.kaiwait.core.process.SingleFunctionIF;
import com.kaiwait.core.process.ValidateResult;
import com.kaiwait.service.jczh.CostService;
@Component("payDeal")
public class PayDeal implements SingleFunctionIF<PayInput>{
	@Resource
	private CostService costService;
	
	@Override
	public Object process(PayInput inputParam) {
		return costService.payDealTx(inputParam);
	}

	public ValidateResult validate(PayInput inputParam) {		
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class getParamType() {
		// TODO Auto-generated method stub
		return PayInput.class;
	}
}
