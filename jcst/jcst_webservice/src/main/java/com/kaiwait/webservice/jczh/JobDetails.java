package com.kaiwait.webservice.jczh;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kaiwait.bean.jczh.io.JobListInput;
import com.kaiwait.core.process.SingleFunctionIF;
import com.kaiwait.core.process.ValidateResult;
import com.kaiwait.service.jczh.JobService;
@Component("jobDetails")
public class JobDetails implements SingleFunctionIF<JobListInput> {

	@Resource
	private JobService jobService;
	@Override
	public Object process(JobListInput inputParam) {
		// TODO Auto-generated method stub
		String jobcd = inputParam.getJob_cd();
		return jobService.searchJobInfoByJobNo(jobcd,inputParam.getCompanyID());
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