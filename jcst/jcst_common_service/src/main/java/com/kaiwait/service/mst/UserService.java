package com.kaiwait.service.mst;



import java.util.Map;

import com.kaiwait.bean.mst.entity.User;
import com.kaiwait.bean.mst.io.UserInfoInput;

public interface UserService {
	/**
	* 方法名 userInfoInit
	* 方法的说明 用户页面初始化
	* @param String userID,String companyID
	* @return User 
	* @author 王岩
	* @date 2018.05.16
	*/
	User userInfoInit(String userID,String companyID);
	/**
	* 方法名 addUser
	* 方法的说明 添加用户
	* @param UserInfoInput userVO
	* @return int 
	* @author 王岩
	 * @throws Exception 
	* @date 2018.05.16
	*/
	int addUserTx(UserInfoInput userVO) throws Exception;
	/**
	* 方法名 employeeInitInfo
	* 方法的说明 用户一览初始化
	* @param UserInfoInput userVO
	* @return User 
	* @author 王岩
	* @date 2018.05.16
	*/
	User employeeInitInfo(UserInfoInput userVO);
	/**
	* 方法名 editUser
	* 方法的说明 变更用户
	* @param UserInfoInput userVO
	* @return int 
	* @author 王岩
	* @date 2018.05.16
	*/
	int editUserTx(UserInfoInput userVO);
	/**
	* 方法名 relevanceClientByUser
	* 方法的说明 客户绑定
	* @param UserInfoInput userVO
	* @return int 
	* @author 王岩
	* @date 2018.05.16
	*/
	int relevanceClientByUserTx(UserInfoInput userVO);
	User employeePopSearch(UserInfoInput inputParam);
	void setUserTableHeaders(int userID,String companyID,String dateTimeNow, String addUserId);
	User changeCompany(UserInfoInput inputParam);
	int changeColorByUser(UserInfoInput inputParam);
	int changeLanguageByUser(UserInfoInput inputParam);
	Map<String,String> uploadEmpFile(UserInfoInput inputParam);
}
