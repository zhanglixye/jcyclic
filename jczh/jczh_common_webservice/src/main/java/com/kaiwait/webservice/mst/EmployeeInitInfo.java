package com.kaiwait.webservice.mst;


import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kaiwait.bean.mst.io.UserInfoInput;
import com.kaiwait.core.process.SingleFunctionIF;
import com.kaiwait.core.process.ValidateResult;
import com.kaiwait.service.mst.UserService;
import com.kaiwait.thrift.common.server.annotation.MatchEnum;
import com.kaiwait.thrift.common.server.annotation.Privilege;

@Component("employeeInitInfo")
@Privilege(keys= {"75"}, match=MatchEnum.ANY)
public class EmployeeInitInfo implements SingleFunctionIF<UserInfoInput>{
	
	@Resource
	private UserService userService;
	
	@Override
	public Object process(UserInfoInput inputParam) {
		if(inputParam.getSearchFLg() == null)
		{
			inputParam.setSearchFLg("initFlg");
		}
		return userService.employeeInitInfo(inputParam);
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
