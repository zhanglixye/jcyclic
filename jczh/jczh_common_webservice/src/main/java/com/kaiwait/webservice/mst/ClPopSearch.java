package com.kaiwait.webservice.mst;



import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kaiwait.bean.mst.io.*;
import com.kaiwait.core.process.SingleFunctionIF;
import com.kaiwait.core.process.ValidateResult;
import com.kaiwait.service.mst.ClmstService;

@Component("clPopSearch")
public class ClPopSearch implements SingleFunctionIF<UserInfoInput>{

	@Resource
	private ClmstService clmstService;
	
	@Override
	public Object process(UserInfoInput inputParam) {
		if(inputParam.getDepartCD().equals("004")) {
			return clmstService.searchClmstByYY(inputParam);
		}
		return clmstService.searchClmstByPop(inputParam);
	}

	@Override
	public ValidateResult validate(UserInfoInput inputParam) {
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class getParamType() {
		// TODO Auto-generated method stub
		return UserInfoInput.class;
	}

}
