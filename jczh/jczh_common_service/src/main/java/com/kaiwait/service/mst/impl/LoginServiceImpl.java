package com.kaiwait.service.mst.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kaiwait.bean.mst.entity.User;
import com.kaiwait.core.utils.DateUtil;
import com.kaiwait.core.utils.RequestUtil;
import com.kaiwait.core.utils.SpringUtil;
import com.kaiwait.mappers.mst.UserMapper;
import com.kaiwait.service.mst.LoginService;

@Service
public class LoginServiceImpl implements LoginService {

	@Resource
	private UserMapper userMapper;
	
	
	public User login(String userName, String pwd) {
		//try {
			/*
			MessageDigest md = MessageDigest.getInstance("MD5");  
			BASE64Encoder base64en = new BASE64Encoder();
			
			*/
			List<User> userList  = userMapper.getUserForLogin(userName);
			User user = new User();
			String timeZone = "";
			Boolean isLogin = false;
			for(int i = 0;i < userList.size();i++)
			{
				isLogin = SpringUtil.match(pwd,userList.get(i).getPwd());
				if(isLogin)
				{
					user = userList.get(i);
					user.setRoleStr(userMapper.searchRoleStr(userList.get(i).getUserCD()));
					if(user.getSysLockFlg() == 1)
					{
						Boolean flg = RequestUtil.getSysLockFlg(user.getRoleStr());
						if(flg)
						{
							user.setSysLockMsg("locked");
							return user;
						}
					}
					user.setRoleList(userMapper.searchNodeListByUser(userList.get(i).getUserCD(), userList.get(i).getCompanyCD()));
					user.setPageNodeList(userMapper.searchPageNodeList());
					user.setComapnyList(userMapper.getCheckedCompanyList(userList.get(i).getUserCD()));
					timeZone = userMapper.getTimeZoneByCompany(userList.get(i).getCompanyCD());
					user.setTimeZoneType(timeZone);
					user.setDateCompanyZone(DateUtil.getNewTime(DateUtil.getDateForNow(DateUtil.dateTimeFormat), Integer.valueOf(timeZone)));
					break;
				}
			}
			if(isLogin)
			{
				return user;
			}else {
				return null;
			}
			
		//} catch (Exception e) {
			
		//	return null;
		//}
		
	}
	
	public int changePwd(String userName, String pwd,String oldPassword) {
		User user = new User();
		user = this.login(userName,oldPassword);
		if(user != null)
		{
			Date date = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
			String pswupdate = formatter.format(date);
			String password = SpringUtil.encrypt(pwd);
			int isChange =  (int)userMapper.changePwdTx(user.getUserCD(),password,pswupdate);
			if(isChange > 0)
			{
				return 1;
			}
			else {
				return 0;
			}
		}
		return 0;
		/*
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");  
			BASE64Encoder base64en = new BASE64Encoder();
			String password = base64en.encode(md.digest(pwd.getBytes("UTF-8")));
			String oldPwd = base64en.encode(md.digest(oldPassword.getBytes("UTF-8")));
			
			int isChange =  (int)userMapper.changePwdTx(userName,password,oldPwd);
			if(isChange > 0)
			{
				return 1;
			}
			else {
				return 0;
			}
			
		} catch (Exception e) {
			
			return 0;
		}
		*/
		
	}
}
