package com.kaiwait.mappers.mst;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kaiwait.bean.mst.entity.Clmst;
import com.kaiwait.bean.mst.entity.Commonmst;
import com.kaiwait.bean.mst.entity.ListColumn;
import com.kaiwait.bean.mst.entity.PageNode;
import com.kaiwait.bean.mst.entity.Role;
import com.kaiwait.bean.mst.entity.User;
import com.kaiwait.bean.mst.io.UserInfoInput;
import com.kaiwait.bean.mst.vo.CompanyVo;
import com.kaiwait.common.dao.BaseMapper;

public interface UserMapper extends BaseMapper{
	/**
	* 方法名 getUserForLogin
	* 方法的说明 登陆
	* @param String userName 登陆名,String pwd 登陆密码
	* @return User 
	* @author 王岩
	* @date 2018.05.16
	*/
	List<User> getUserForLogin(String userName);
	/**
	* 方法名 changePwdTx
	* 方法的说明 变更密码
	* @param String userName 登陆名,String pwd 登陆密码，String oldPwd 旧密码
	* @return int 
	* @author 王岩
	 * @param pswupdate 
	* @date 2018.05.16
	*/

	int changePwdTx(String userCD,String pwd, @Param("pswUpdateTime")String pswupdate);
	/**
	* 方法名 getUserInitInfo
	* 方法的说明  用户页面初始化
	* @param String userID 用户ID,String companyID 公司ID
	* @return User
	* @author 王岩
	* @date 2018.05.16
	*/

	User getUserInitInfo(@Param("userID")String userID,@Param("companyID")String companyID);
	/**
	* 方法名 getCompanyListForUser
	* 方法的说明 获取公司列表
	* @param 
	* @return List<CompanyVo>
	* @author 王岩
	* @date 2018.05.16
	*/

	List<CompanyVo> getCompanyListForUser(@Param("companyID")String companyID);
	/**
	* 方法名 insertUser
	* 方法的说明 插入用户
	* @param UserInfoInput userVO
	* @return int
	* @author 王岩
	* @date 2018.05.16
	*/

	int insertUserTx(UserInfoInput userVO);
	/**
	* 方法名 searchUserList
	* 方法的说明 查询用户列表
	* @param UserInfoInput userVO
	* @return List<User>
	* @author 王岩
	* @date 2018.05.16
	*/

	List<User> searchUserList(UserInfoInput userVO);
	/**
	* 方法名 upUserInfo
	* 方法的说明 用户更新
	* @param UserInfoInput userVO
	* @return int
	* @author 王岩
	* @date 2018.05.16
	*/

	int upUserInfoTx(UserInfoInput userVO);
	/**
	* 方法名 getUserHistoryByInsert
	* 方法的说明 获取最新更新者的用户和添加者的信息
	* @param String companyID
	* @return List<User>
	* @author 王岩
	* @date 2018.05.16
	*/

	List<User> getUserHistoryByUp(@Param("companyID")String companyID);
	/**
	* 方法名 companyByUser
	* 方法的说明 插入参照公司ID
	* @param String[] agrs 参照公司字符串数组，int userId 被更新者或被添加者ID
	* @return 返回值类型 说明
	* @author 王岩
	* @date 2018.05.16
	*/

	int companyByUserTx(@Param("agrs")String[] agrs, @Param("userId")int userId,@Param("addDate")String addDate,@Param("addUserId")String addUserId);
	/**
	* 方法名 delCompanyByUser
	* 方法的说明 删除所有被更新者的参照公司
	* @param String userId 被更新者或被添加者ID
	* @return 
	* @author 王岩
	* @date 2018.05.16
	*/

	void delCompanyByUserTx(@Param("userId")String userId);
	/**
	* 方法名 getAllClients
	* 方法的说明 获取所有本公司客户ID
	* @param String companyID 公司ID
	* @return String[] 字符串数组
	* @author 王岩
	* @date 2018.05.16
	*/

	String[] getAllClients(@Param("companyID")String companyID);
	/**
	* 方法名 insertAllClientByUser
	* 方法的说明 给指定用户绑定所有客户
	* @param String[] clients 客户ID字符串数组,String userID 用户ID,String companyID 添加者公司ID,String addUserID 添加者
	* @return int
	* @author 王岩
	* @date 2018.05.16
	*/

	int insertAllClientByUserTx(@Param("clients")String[] clients,@Param("userId")String userID,@Param("companyID")String companyID,@Param("addUserID")String addUserID,@Param("addDateTime")String addDateTime);
	/**
	* 方法名 delAllClientByUser
	* 方法的说明 删除所有绑定关系
	* @param String userID，String companyID
	* @return
	* @author 王岩
	* @date 2018.05.16
	*/

	void delAllClientByUserTx(@Param("userID")String userID,@Param("companyID")String companyID);
	/**
	* 方法名 getRelevancedList
	* 方法的说明 获取所有绑定的客户list
	* @param String userID，String companyID
	* @return List<Clmst>
	* @author 王岩
	* @date 2018.05.16
	*/

	List<Clmst> getRelevancedList(@Param("userID")String userID,@Param("companyID")String companyID);
	/**
	* 方法名 getIrrelevantList
	* 方法的说明 获取所有未绑定的客户list
	* @param String userID，String companyID
	* @return List<Clmst>
	* @author 王岩
	* @date 2018.05.16
	*/

	List<Clmst> getIrrelevantList(@Param("userID")String userID,@Param("companyID")String companyID);
	/**
	* 方法名 insertClientByUser
	* 方法的说明 插入用户和客户的绑定关系
	* @param List<Clmst> irrelevantList，String userID，String companyID，String addUserID
	* @return int
	* @author 王岩
	* @date 2018.05.16
	*/

	int insertClientByUserTx(@Param("clietList")List<Clmst> irrelevantList,@Param("userID")String userID,@Param("companyID")String companyID,@Param("addUserID")String addUserID);
	/**
	* 方法名 getCheckedCompanyList
	* 方法的说明 根据用户获取所有已经参照的公司
	* @param String userID
	* @return List<CompanyVo>
	* @author 王岩
	* @date 2018.05.16
	*/
	List<CompanyVo> getCheckedCompanyList(@Param("userID")String userID);
	/**
	* 方法名 roleByUserTx
	* 方法的说明 插入用户所选权限组
	* @param String【】 agrs int userId
	* @return int
	* @author 王岩
	* @date 2018.06.14
	*/
	void roleByUserTx(@Param("agrs")String[] agrs, @Param("userId")int userId,@Param("addDate")String addDate,@Param("addUserId")String addUserId);
	
	void delRoleByUserTx(@Param("userId")String userId);
	List<User> empPopSearch(UserInfoInput inputParam);
	List<Role> searchNodeListByUser(@Param("userID")String userID,@Param("companyID")String companyID);
	List<PageNode> searchPageNodeList();
	String getCompanyType(@Param("companyID")String companyID);
	User serchLevel(@Param("userID")String userID);
	List<ListColumn> serchColumnListAll();
	int addUserCampany(@Param("usercd")String usercd,@Param("companycd")int companycd,@Param("addDate")String addDate,@Param("addUsercd")String addUsercd);
	String searchRoleStr(@Param("userID")String userID);
	String getTimeZoneByCompany(@Param("companyID")String companyID);
	
	User getUserInfoByChangeCompany(@Param("userID")String userID,@Param("companyID")String companyID);
	int upColorByUser(@Param("userID")String userID,@Param("colorV")String colorV);
	int validataLoginName(@Param("loginName")String loginName,@Param("userID")String userID);
	int validataMemberID(@Param("memberID")String memberID,@Param("companyID")String companyID,@Param("userID")String userID);
	int changeLanguageByUser(@Param("userID")String userID,@Param("langTyp")String langTyp);
	int getLockFlg(@Param("userID")String userID,@Param("companyID")String companyID);
	void upUserMstLockFlg(@Param("userID")String userID,@Param("companyID")String companyID,@Param("lockFlg")int lockFlg);
	void delTableHeaders(@Param("userID")String userID,@Param("companyID")String companyID);
	String queryDepart(@Param("userID")String userID);
	String selectcompanmst(@Param("companyID")int companycd);
	List<Commonmst> selectitemcd(@Param("companyID")int companycd);
	List<ListColumn> serchSysColumnListAll(@Param("companyID")String companyID);
	List<ListColumn> serchColumnListAll(@Param("companyID")String companyID);
}