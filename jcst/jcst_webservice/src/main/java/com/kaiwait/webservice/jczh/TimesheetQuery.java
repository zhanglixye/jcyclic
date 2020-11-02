package com.kaiwait.webservice.jczh;


import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kaiwait.bean.jczh.io.JobListInput;
import com.kaiwait.core.process.SingleFunctionIF;
import com.kaiwait.core.process.ValidateResult;
import com.kaiwait.service.jczh.TimesheetService;
import com.kaiwait.thrift.common.server.annotation.MatchEnum;
import com.kaiwait.thrift.common.server.annotation.Privilege;
@Privilege(keys= {"65","66","67","68"}, match=MatchEnum.ANY)
@Component("TimesheetQuery")

public class TimesheetQuery implements SingleFunctionIF<JobListInput>{
	
	@Resource
	private TimesheetService TimesheetService;

	
	@Override
	public Object process(JobListInput inputParam) {		
		return TimesheetService.timesheetQuery(inputParam);
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
