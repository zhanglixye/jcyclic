package com.kaiwait.webservice.mst;


import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kaiwait.bean.mst.io.UserInfoInput;
import com.kaiwait.core.process.SingleFunctionIF;
import com.kaiwait.core.process.ValidateResult;
import com.kaiwait.service.mst.CommonmstService;
import com.kaiwait.service.mst.UserService;
import com.kaiwait.thrift.common.server.annotation.MatchEnum;
import com.kaiwait.thrift.common.server.annotation.Privilege;

@Component("userInitInfo")
@Privilege(keys= {"76","77","78"}, match=MatchEnum.ANY)
public class UserInitInfo implements SingleFunctionIF<UserInfoInput>{
	
	@Resource
	private UserService userService;
	@Resource
	private CommonmstService commService;
	
	@Override
	public Object process(UserInfoInput inputParam) {
		return userService.userInfoInit(inputParam.getUserCD(),inputParam.getCompanyID());
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
