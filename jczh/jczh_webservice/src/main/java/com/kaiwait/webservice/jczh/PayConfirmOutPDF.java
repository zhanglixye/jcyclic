package com.kaiwait.webservice.jczh;



import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kaiwait.bean.jczh.io.OutPutInput;
import com.kaiwait.core.process.SingleFunctionIF;
import com.kaiwait.core.process.ValidateResult;
import com.kaiwait.service.jczh.CommonMethedService;
import com.kaiwait.service.jczh.PayService;

import net.sf.jasperreports.engine.JRException;

@Component("PayConfirmOutPDF")
public class PayConfirmOutPDF implements SingleFunctionIF<OutPutInput>{
	
	@Resource
	private PayService payService;
	@Resource
	private CommonMethedService commonMethedService;
	public Object process(OutPutInput inputParam) {
		String pdf = "";
		try {
			pdf = payService.PayConfirmOutPDF(inputParam);
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pdf;
	}

	public ValidateResult validate(OutPutInput inputParam) {		
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class getParamType() {
		// TODO Auto-generated method stub
		return OutPutInput.class;
	}




}
