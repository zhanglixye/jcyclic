package com.kaiwait.service.mst;

import com.kaiwait.bean.mst.entity.User;

public interface LoginService {
	User login(String userName,String pwd);
	int changePwd(String userName,String pwd,String oldPassword);
}
