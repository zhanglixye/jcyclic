package com.kaiwait.webservice.jczh;


import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kaiwait.bean.jczh.io.JobListInput;
import com.kaiwait.core.process.SingleFunctionIF;
import com.kaiwait.core.process.ValidateResult;
import com.kaiwait.service.jczh.JobService;

@Component("jobSearchByKeyWord")

public class JobSearchByKeyWord implements SingleFunctionIF<JobListInput>{
	
	@Resource
	private JobService jobService;
	
	@Override
	public Object process(JobListInput inputParam) {
		return jobService.searchJobByKeyWord(inputParam);
	}

	public ValidateResult validate(JobListInput inputParam) {		
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class getParamType() {
		// TODO Auto-generated method stub
		return JobListInput.class;
	}


}
