package com.kaiwait.mappers.jczh;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kaiwait.bean.jczh.entity.Trantrn;
import com.kaiwait.bean.jczh.entity.TrantrnApprovalBeen;
import com.kaiwait.bean.jczh.entity.TrantrnConfirmBeen;
import com.kaiwait.common.dao.BaseMapper;

public interface TrantrnMapper extends BaseMapper{
	
	/**
	* 方法名 searchTrantrnList
	* 方法的说明 标签初始页面
	* @param 
	* @return List<Trantrn>
	* @author FQQ
	* @date 2018.05.16
	*/
	List<Trantrn> searchTrantrnList(@Param("usercd")String usercd,@Param("companycd")String companycd);
	
//	/**
//	* 方法名 addTrantrn
//	* 方法的说明 留言板新增
//	* @param 
//	* @return 
//	* @author FQQ
//	* @date 2018.05.16
//	*/
//	void addTrantrn(Trantrn Trantrn);
//	
	/**
	* 方法名 deleteTrantrn
	* 方法的说明 留言板删除
	* @param 
	* @return 
	* @author FQQ
	* @date 2018.05.16
	*/
	int trantrnapproval(Trantrn Trantrn);
	
	/**
	* 方法名 updateTrantrn
	* 方法的说明 标签修改
	* @param 
	* @return 
	* @author FQQ
	* @date 2018.05.16
	*/
	int updateTrantrn(Trantrn Trantrn);
	/**
	* 方法名 insertusersettingtrn
	* 方法的说明 振替登录
	* @param List<ClmstAddInput>
	* @return List<User>
	* @author FQQ
	* @date 2018.05.16
	*/
	int updateDelete(@Param("jobcd")String jobcd,@Param("inputno")String inputno,@Param("companyID")String companyID,@Param("datenow")String datenow,@Param("usercd")String usercd);
	/**
	* 方法名 insertusersettingtrn
	* 方法的说明 振替删除
	* @param List<ClmstAddInput>
	* @return List<User>
	* @author FQQ
	* @date 2018.05.16
	*/
	int insertusersettingtrn(Trantrn Trantrn);

	/**
	* 方法名 deletetusersettingtrn
	* 方法的说明 一览关联删除
	* @param 
	* @return List<User>
	* @author FQQ
	* @date 2018.05.16
	*/
	void deletetusersettingtrn(@Param("userId")String userId,@Param("companyID")String companyID);
	
	/**
	* 方法名 trantrnquery
	* 方法的说明 振替确认初始化
	* @param 
	* @return 
	* @author FQQ
	* @date 2018.05.16
	*/
	List<Trantrn> trantrnquery(Trantrn Trantrn);
	/**
	* 方法名 trantrnDate
	* 方法的说明 查询登陆日，更新时间，承认时间，承认取消时间
	* @param 
	* @return 
	* @author FQQ
	* @date 2018.05.16
	*/
	Trantrn trantrnDate(Trantrn Trantrn);
	/**
	* 方法名 cancelTran
	* 方法的说明 振替取消
	* @param 
	* @return 
	* @author LS
	* @date 2018.08.22
	*/
	int cancelTran(Trantrn Trantrn);
	String getstatus(@Param("jobcd")String jobcd,@Param("inputno")String inputno,@Param("companyID")String companyID);
	TrantrnConfirmBeen TrantrnConfirmPDF(@Param("jobcd")String jobcd,@Param("companycd")int companycd,@Param("inputno")String inputno);
	TrantrnApprovalBeen TrantrnApprovalPDF(@Param("jobcd")String jobcd,@Param("companycd")int companycd,@Param("inputno")String inputno);
	int queryLock(@Param("jobcd")String jobcd,@Param("companycd")String companycd,@Param("inputno")String inputno);
	int updateLock(@Param("jobcd")String jobcd,@Param("companycd")String companycd,@Param("inputno")String inputno);
}