package com.kaiwait.webservice.mst;



import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kaiwait.bean.mst.io.*;
import com.kaiwait.common.utils.StringUtil;
import com.kaiwait.core.process.SingleFunctionIF;
import com.kaiwait.core.process.ValidateResult;
import com.kaiwait.service.mst.LoginService;

@Component("changePwd")
public class ChangePwd implements SingleFunctionIF<LoginInput>{

	@Resource
	private LoginService loginService;
	
	@Override
	public Object process(LoginInput inputParam) {
		return loginService.changePwd(inputParam.getUserName(), inputParam.getPassword(),inputParam.getOldPassword());
		
		//return "login............";
	}

	@Override
	public ValidateResult validate(LoginInput inputParam) {
		
		if(StringUtil.isEmpty(inputParam.getUserName()))
		{
			ValidateResult validateResult = new ValidateResult("2");
			validateResult.setErrorMessage("用户名不能为空");
			return validateResult;
		}
		if(StringUtil.isEmpty(inputParam.getPassword()))
		{
			ValidateResult validateResult = new ValidateResult("2");
			validateResult.setErrorMessage("密码不能为空");
			return validateResult;
		}
		if(StringUtil.isEmpty(inputParam.getOldPassword()))
		{
			ValidateResult validateResult = new ValidateResult("2");
			validateResult.setErrorMessage("旧密码不能为空");
			return validateResult;
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class getParamType() {
		// TODO Auto-generated method stub
		return LoginInput.class;
	}

}
