package com.kaiwait.webservice.jczh;



import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kaiwait.bean.jczh.io.PayInput;
import com.kaiwait.core.process.SingleFunctionIF;
import com.kaiwait.core.process.ValidateResult;
import com.kaiwait.service.jczh.CommonMethedService;
import com.kaiwait.service.jczh.PayService;

@Component("deletePayInfo")
public class deletePayInfo implements SingleFunctionIF<PayInput>{
	
	@Resource
	private PayService payService;
	@Resource
	private CommonMethedService commonMethedService;
	
	@Override
	public Object process(PayInput inputParam) {
		int num = payService.deletePayInfoTx(inputParam);
		return num;
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
