package com.kaiwait.webservice.jczh;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kaiwait.bean.jczh.io.JobListInput;
import com.kaiwait.core.process.SingleFunctionIF;
import com.kaiwait.core.process.ValidateResult;
import com.kaiwait.service.jczh.JobService;
@Component("costFinish")
public class CostFinish implements SingleFunctionIF<JobListInput> {
	@Resource
	private JobService JobService;
	@Override
	public Object process(JobListInput inputParam) {
		// TODO Auto-generated method stub
		return JobService.costFinish(inputParam);
	}

	@Override
	public ValidateResult validate(JobListInput inputParam) {
		// TODO Auto-generated method stub
		return null;
	}
	@SuppressWarnings("rawtypes")
	@Override
	public Class getParamType() {
		// TODO Auto-generated method stub
		return JobListInput.class;
	}

}