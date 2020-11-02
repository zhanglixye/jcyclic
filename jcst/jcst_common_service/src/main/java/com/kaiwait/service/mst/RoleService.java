package com.kaiwait.service.mst;

import java.util.List;

import com.kaiwait.bean.mst.entity.Role;
import com.kaiwait.bean.mst.io.RoleInput;

public interface RoleService {
	
	List<Role> getRoleList(String companyID);
	int saveRoleListTx(RoleInput roleList);
	Role getNodesByRoles(String companyID,String roleID);
	Role saveNodeListByRole(RoleInput roleList);
}