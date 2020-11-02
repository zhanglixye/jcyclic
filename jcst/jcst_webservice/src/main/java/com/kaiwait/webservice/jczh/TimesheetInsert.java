package com.kaiwait.webservice.jczh;



import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kaiwait.bean.jczh.io.TimesheetInput;
import com.kaiwait.core.process.SingleFunctionIF;
import com.kaiwait.core.process.ValidateResult;
import com.kaiwait.service.jczh.CommonMethedService;
import com.kaiwait.service.jczh.TimesheetService;

@Component("TimesheetInsert")

public class TimesheetInsert implements SingleFunctionIF<TimesheetInput>{
	
	@Resource
	private TimesheetService TimesheetService;
	@Resource
	private CommonMethedService commonMethedService;
	
	@Override
	public Object process(TimesheetInput inputParam) {
		String companycd =inputParam.getCompanyID();
		inputParam.setCompany_cd(companycd);
		inputParam.setUser_cd(inputParam.getUserID());
		return TimesheetService.timesheetInsert(inputParam);
		 
		
		
		 
	}

	public ValidateResult validate(TimesheetInput inputParam) {		
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class getParamType() {
		// TODO Auto-generated method stub
		return TimesheetInput.class;
	}


}
