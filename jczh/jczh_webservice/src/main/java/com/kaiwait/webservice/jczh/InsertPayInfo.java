package com.kaiwait.webservice.jczh;


import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kaiwait.bean.jczh.io.PayInput;
import com.kaiwait.core.process.SingleFunctionIF;
import com.kaiwait.core.process.ValidateResult;
import com.kaiwait.service.jczh.CommonMethedService;
import com.kaiwait.service.jczh.PayService;
import com.kaiwait.thrift.common.server.annotation.MatchEnum;
import com.kaiwait.thrift.common.server.annotation.Privilege;

@Component("insertPayInfo")
@Privilege(keys= {"49","50","51","52"},match=MatchEnum.ANY)
public class InsertPayInfo implements SingleFunctionIF<PayInput>{
	
	@Resource
	private PayService payService;
	@Resource
	private CommonMethedService commonMethedService;
	public Object process(PayInput inputParam) {
		String input_no = commonMethedService.getMaxKeyCode("P",Integer.valueOf(inputParam.getCompanyID()));
		inputParam.setInput_no(input_no);
		Map<String, Object> output =  payService.insertPayInfoTx(inputParam);
		output.put("input_no", input_no);
		return output;
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
