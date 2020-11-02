package com.kaiwait.webservice.jczh;


import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kaiwait.bean.jczh.io.JobZhInput;
import com.kaiwait.core.process.SingleFunctionIF;
import com.kaiwait.core.process.ValidateResult;
import com.kaiwait.service.jczh.CommonMethedService;
@Component("getRateByDateAndSaleID")

public class getRateByDateAndSaleID implements SingleFunctionIF<JobZhInput> {
	@Resource
	private CommonMethedService commonMethedService;
	@Override
	public Object process(JobZhInput inputParam) {
		  @SuppressWarnings("unused")
		String fat = inputParam.getDalday();
			return commonMethedService.getRateByDateAndSaleID(inputParam);
	}

	@Override
	public ValidateResult validate(JobZhInput inputParam) {
		// TODO Auto-generated method stub
		return null;
	}
	@SuppressWarnings("rawtypes")
	@Override
	public Class getParamType() {
		// TODO Auto-generated method stub
		return JobZhInput.class;
	}

}
