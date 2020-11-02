package com.kaiwait.service.mst.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kaiwait.bean.mst.entity.Role;
import com.kaiwait.bean.mst.io.RoleInput;
import com.kaiwait.core.utils.DateUtil;
import com.kaiwait.mappers.mst.RoleMapper;
import com.kaiwait.service.mst.RoleService;

@Service
public class RoleServiceImpl implements RoleService{
	
	@Resource
	private RoleMapper roleMapper;
	
	public List<Role> getRoleList(String companyID)
	{
		return roleMapper.getRoleList(companyID,"","","");
	}
	public int saveRoleListTx(RoleInput roleList)
	{
		return roleMapper.saveRoleListByCompanyID(roleList.getRoleList(),roleList.getCompanyID(),roleList.getUserID(),DateUtil.getDateForNow(DateUtil.dateTimeFormat));
	}
	public Role getNodesByRoles(String companyID,String roleID)
	{
		if(roleID.equals("all"))
		{
			roleID = "";
		}
		Role role = new Role();
		//获取各个权限组所设置的节点
		role.setNodeListByRole(roleMapper.getNodesByRoles(companyID,roleID));
		//获取所有节点
		role.setNodeList(roleMapper.getNodes());
		//根据条件获取相应的权限组个数
		role.setRoleList(roleMapper.getRoleList(companyID,String.valueOf(roleID),"",""));
		return role;
	}
	public Role saveNodeListByRole(RoleInput roleList)
	{
		Role rsRole = new Role();
		String rsRoleName = "";
		int isRoleNum = 0;
		if(roleList.getRoleList().get(0).getRoleList().get(0).getRoleID() == 0)
		{
			isRoleNum = roleMapper.isRoleName(roleList.getCompanyID(), roleList.getRoleList().get(0).getRoleList().get(0).getRoleName(),9988);
			if(isRoleNum > 0)
			{
				rsRole.setRoleNamezc(roleList.getRoleList().get(0).getRoleList().get(0).getRoleName()+",");
				rsRole.setIsNameRole(0);
			}else {
				String sdt = DateUtil.getDateForNow(DateUtil.dateTimeFormat);
				Date dt = DateUtil.stringtoDate(sdt,DateUtil.dateTimeFormat);
				Role role = new Role();
				//role.setRoleNamezc(roleList.getRoleList().get(0).getRoleList().get(0).getRoleName());
				role.setRoleName(roleList.getRoleList().get(0).getRoleList().get(0).getRoleName());
				role.setCompanyID(roleList.getCompanyID());
				role.setAddUserID(roleList.getUserID());
				role.setAddDate(dt);
				roleMapper.addRole(role);
				for(int i=0;i<roleList.getRoleList().get(1).getNodeList().size();i++)
				{
					roleList.getRoleList().get(1).getNodeList().get(i).setRoleID(role.getRoleID());
				}
				roleMapper.saveNodeList(roleList.getRoleList().get(1).getNodeList(),roleList.getCompanyID(),sdt,roleList.getUserID());
				rsRole.setIsNameRole(1);
			}
		}else {
			List<Role> roleLockList = roleMapper.getRoleList(roleList.getCompanyID(),"","","");
			String roleName = "";
			int roleID = 0;
			boolean flg = true;
			
			for(int i = 0;i < roleList.getRoleList().get(0).getRoleList().size();i++)
			{
				for(int j = 0;j < roleLockList.size();j++)
				{
					if(roleLockList.get(j).getRoleID() == roleList.getRoleList().get(0).getRoleList().get(i).getRoleID())
					{
						if(roleLockList.get(j).getLockFlg() > roleList.getRoleList().get(0).getRoleList().get(i).getLockFlg())
						{
							rsRole.setIsNameRole(996);
							return rsRole;
						}else {
							roleList.getRoleList().get(0).getRoleList().get(i).setLockFlg(roleLockList.get(j).getLockFlg()+1);
						}
					}
				}
				roleName = roleList.getRoleList().get(0).getRoleList().get(i).getRoleNamezc();
				roleID = roleList.getRoleList().get(0).getRoleList().get(i).getRoleID();
				isRoleNum = roleMapper.isRoleName(roleList.getCompanyID(), roleName,roleID);
				if(isRoleNum > 0)
				{
					flg = false;
					rsRoleName = rsRoleName + roleName + ",";
				}
			}
			rsRole.setRoleNamezc(rsRoleName);
			if(flg)
			{
				roleMapper.saveRoleInfoList(roleList.getRoleList().get(0).getRoleList(),roleList.getCompanyID(),DateUtil.getDateForNow(DateUtil.dateTimeFormat),roleList.getUserID());
				if(roleList.getRoleList().get(1).getNodeList().size() > 0)
				{
					roleMapper.delNodeByRole(roleList.getRoleList().get(0).getRoleList(),roleList.getCompanyID());
					roleMapper.saveNodeList(roleList.getRoleList().get(1).getNodeList(),roleList.getCompanyID(),DateUtil.getDateForNow(DateUtil.dateTimeFormat),roleList.getUserID());
				}
				rsRole.setIsNameRole(1);
			}else {
				rsRole.setIsNameRole(0);
			}
		}
		
		return rsRole;
	}
}