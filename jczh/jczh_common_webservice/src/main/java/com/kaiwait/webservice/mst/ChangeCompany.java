package com.kaiwait.webservice.mst;



import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kaiwait.bean.mst.io.*;
import com.kaiwait.core.process.SingleFunctionIF;
import com.kaiwait.core.process.ValidateResult;
import com.kaiwait.service.mst.UserService;

@Component("changeCompany")
public class ChangeCompany implements SingleFunctionIF<UserInfoInput>{

	@Resource
	private UserService userService;
	
	@Override
	public Object process(UserInfoInput inputParam) {
		return userService.changeCompany(inputParam);
		
		//return "change............";
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
