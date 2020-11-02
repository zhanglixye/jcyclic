package com.kaiwait.webservice.jczh;


import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kaiwait.bean.jczh.io.CostInput;
import com.kaiwait.core.process.SingleFunctionIF;
import com.kaiwait.core.process.ValidateResult;
import com.kaiwait.service.jczh.CommonMethedService;
import com.kaiwait.service.jczh.JobService;

@Component("searchCostInfo")
public class SearchCostInfo implements SingleFunctionIF<CostInput>{
	
	@Resource
	private JobService jobService;
	@Resource
	private CommonMethedService commonMethedService;
	
	@Override
	public Object process(CostInput inputParam) {
		Map<String,Object> outsoure = jobService.searchCostInfo(inputParam);
		return outsoure;
	}

	public ValidateResult validate(CostInput inputParam) {		
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class getParamType() {
		// TODO Auto-generated method stub
		return CostInput.class;
	}


}
