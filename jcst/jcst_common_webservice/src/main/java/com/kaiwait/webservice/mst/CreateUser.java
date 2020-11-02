package com.kaiwait.webservice.mst;


import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kaiwait.bean.mst.io.UserInfoInput;
import com.kaiwait.common.utils.StringUtil;
import com.kaiwait.core.process.SingleFunctionIF;
import com.kaiwait.core.process.ValidateResult;
import com.kaiwait.service.mst.UserService;
import com.kaiwait.thrift.common.server.annotation.Privilege;
import com.kaiwait.thrift.common.server.annotation.MatchEnum;

@Component("createUser")

@Privilege(keys= {"76","77"}, match=MatchEnum.ANY)

public class CreateUser implements SingleFunctionIF<UserInfoInput>{
	
	@Resource
	private UserService userService;
	
	@Override
	public Object process(UserInfoInput inputParam) {
		try {
			return userService.addUserTx(inputParam);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public ValidateResult validate(UserInfoInput inputParam) {
		
		if(StringUtil.isEmpty(inputParam.getLoginName()))
		{
			ValidateResult validateResult = new ValidateResult("2");
			validateResult.setErrorMessage("用户名不能为空");
			return validateResult;
		}
		if(StringUtil.isEmpty(inputParam.getPwd()))
		{
			ValidateResult validateResult = new ValidateResult("2");
			validateResult.setErrorMessage("密码不能为空");
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
