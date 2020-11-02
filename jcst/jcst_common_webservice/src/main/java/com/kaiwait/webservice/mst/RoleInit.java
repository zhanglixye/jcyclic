package com.kaiwait.webservice.mst;


import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kaiwait.bean.mst.io.RoleInput;
import com.kaiwait.core.process.SingleFunctionIF;
import com.kaiwait.core.process.ValidateResult;
import com.kaiwait.service.mst.RoleService;
import com.kaiwait.thrift.common.server.annotation.MatchEnum;
import com.kaiwait.thrift.common.server.annotation.Privilege;

@Component("roleInit")
@Privilege(keys= {"80","79"}, match=MatchEnum.ANY)
public class RoleInit implements SingleFunctionIF<RoleInput>{
	
	@Resource
	private RoleService roleService;
	
	@Override
	public Object process(RoleInput inputParam) {
		return roleService.getRoleList(inputParam.getCompanyID());
	}

	@Override
	public ValidateResult validate(RoleInput inputParam) {
		
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class getParamType() {
		// TODO Auto-generated method stub
		return RoleInput.class;
	}


}
