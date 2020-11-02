package com.kaiwait.webservice.mst;



import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kaiwait.bean.mst.io.*;
import com.kaiwait.common.utils.StringUtil;
import com.kaiwait.core.process.SingleFunctionIF;
import com.kaiwait.core.process.ValidateResult;
import com.kaiwait.service.mst.UserService;

@Component("relevanceClient")
public class RelevanceClient implements SingleFunctionIF<UserInfoInput>{

	@Resource
	private UserService userService;
	
	@Override
	public Object process(UserInfoInput inputParam) {
		return userService.relevanceClientByUserTx(inputParam);
	}

	@Override
	public ValidateResult validate(UserInfoInput inputParam) {
		
		if(StringUtil.isEmpty(inputParam.getUserCD()))
		{
			ValidateResult validateResult = new ValidateResult("2");
			validateResult.setErrorMessage("用户名不能为空");
			return validateResult;
		}
		
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class getParamType() {
		// TODO Auto-generated method stub
		return UserInfoInput.class;
	}

	

}
