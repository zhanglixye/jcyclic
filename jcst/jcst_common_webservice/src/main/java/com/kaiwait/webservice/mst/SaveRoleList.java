package com.kaiwait.webservice.mst;


import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kaiwait.bean.mst.io.RoleInput;
import com.kaiwait.core.process.SingleFunctionIF;
import com.kaiwait.core.process.ValidateResult;
import com.kaiwait.service.mst.RoleService;

@Component("saveRoleList")
public class SaveRoleList implements SingleFunctionIF<RoleInput>{
	
	@Resource
	private RoleService roleService;
	
	@Override
	public Object process(RoleInput inputParam) {
		return roleService.saveRoleListTx(inputParam);
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
