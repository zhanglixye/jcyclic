package com.kaiwait.mappers.mst;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kaiwait.bean.mst.entity.Role;
import com.kaiwait.common.dao.BaseMapper;

public interface RoleMapper extends BaseMapper{
	
	List<Role> getRoleList(@Param("companyID")String companyID,@Param("roleID")String roleID,@Param("companyTyp")String companyTyp,@Param("isOnShow")String isOnShow);
	List<Role> getRoleListByUserID(String userID);
	int saveRoleListByCompanyID(@Param("roleList")List<Role> roleList,@Param("companyID")String companyID,@Param("userID")String userID,@Param("upDateTime")String upDateTime);
	List<Role> getNodesByRoles(@Param("companyID")String companyID,@Param("roleID")String roleID);
	List<Role> getNodes();
	int saveRoleInfoList(@Param("roleList")List<Role> roleList,@Param("companyID")String companyID,@Param("upDateTime")String upDateTime,@Param("userId")String userId);
	int saveNodeList(@Param("nodeList")List<Role> nodeList,@Param("companyID")String companyID,@Param("upDateTime")String upDateTime,@Param("userId")String userId);
	int delNodeByRole(@Param("roleList")List<Role> roleList,@Param("companyID")String companyID);
	int addRole(Role role);
	int addUserRoleTrn(@Param("usercd")String usercd,@Param("roleid")int roleid,@Param("addDate")String addDate,@Param("addUsercd")String addUsercd);
	List<Role> getRoleMstID();
	int insertRoleComOrderTrn(@Param("listRole")List<Role> listRole);
	int isRoleName(@Param("companyID")String companyID,@Param("roleName")String roleName,@Param("roleID")int roleID);
}