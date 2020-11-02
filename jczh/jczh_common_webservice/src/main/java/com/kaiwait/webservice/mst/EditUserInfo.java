package com.kaiwait.webservice.mst;


import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kaiwait.bean.mst.io.UserInfoInput;
import com.kaiwait.common.utils.StringUtil;
import com.kaiwait.core.process.SingleFunctionIF;
import com.kaiwait.core.process.ValidateResult;
import com.kaiwait.service.mst.UserService;
import com.kaiwait.thrift.common.server.annotation.MatchEnum;
import com.kaiwait.thrift.common.server.annotation.Privilege;

@Component("editUserInfo")

@Privilege(keys= {"76","77"}, match=MatchEnum.ANY)
public class EditUserInfo implements SingleFunctionIF<UserInfoInput>{
	
	@Resource
	private UserService userService;
	
	@Override
	public Object process(UserInfoInput inputParam) {
		if(inputParam.getFlag().equals("changeLanguage"))
		{
			return userService.changeLanguageByUser(inputParam); 
		}else {
			return userService.editUserTx(inputParam);
		}
	}

	@Override
	public ValidateResult validate(UserInfoInput inputParam) {
		
		if(StringUtil.isEmpty(inputParam.getNickname()))
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
