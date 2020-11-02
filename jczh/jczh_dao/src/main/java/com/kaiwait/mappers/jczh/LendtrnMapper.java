package com.kaiwait.mappers.jczh;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kaiwait.bean.jczh.entity.Lendtrn;
import com.kaiwait.bean.jczh.entity.LendtrnApprovalBeen;
import com.kaiwait.bean.jczh.entity.LendtrnConfirmBeen;
import com.kaiwait.bean.jczh.entity.OrderHistorytrn;
import com.kaiwait.bean.jczh.entity.Prooftrn;
import com.kaiwait.common.dao.BaseMapper;

public interface LendtrnMapper extends BaseMapper{
	
	/**
	* 方法名 searchLendtrnList
	* 方法的说明 标签初始页面
	* @param 
	* @return List<Lendtrn>
	* @author FQQ
	* @date 2018.05.16
	*/
	List<Lendtrn> searchLendtrnList(@Param("usercd")String usercd,@Param("companycd")String companycd);
	
//	/**
//	* 方法名 addLendtrn
//	* 方法的说明 留言板新增
//	* @param 
//	* @return 
//	* @author FQQ
//	* @date 2018.05.16
//	*/
//	void addLendtrn(Lendtrn Lendtrn);
//	
	/**
	* 方法名 Lendtrnapproval
	* 方法的说明 立替承认
	* @param 
	* @return 
	* @author FQQ
	* @date 2018.05.16
	*/
	int lendtrnapproval(Lendtrn Lendtrn);
	
	/**
	* 方法名 updateLendtrn
	* 方法的说明 立替修改
	* @param 
	* @return 
	* @author FQQ
	* @date 2018.05.16
	*/
	int updateLendtrn(Lendtrn Lendtrn);
	/**
	* 方法名 insertlendtrn
	* 方法的说明 力替登录
	* @param List<ClmstAddInput>
	* @return List<User>
	* @author FQQ
	* @date 2018.05.16
	*/
	int lendtrndelete(@Param("jobcd")String jobcd,@Param("inputno")String inputno,@Param("companyID")String companyID,@Param("datenow")String datenow,@Param("usercd")String usercd);
	/**
	* 方法名 insertlendtrn
	* 方法的说明 力替登录
	* @param List<ClmstAddInput>
	* @return List<User>
	* @author FQQ
	* @date 2018.05.16
	*/
	int insertlendtrn(Lendtrn Lendtrn);
	/**
	* 方法名 insertrooftrn
	* 方法的说明 凭证登录
	* @param List<ClmstAddInput>
	* @return List<User>
	* @author FQQ
	* @date 2018.05.16
	*/
	int insertProoftrn(@Param("clietList")List<Prooftrn> list,@Param("UserID")String UserID,@Param("companyID")String companyID,@Param("inputno")String Input_no,@Param("upddate")String upddate);
	/**
	* 方法名 insertrooftrn
	* 方法的说明 凭证删除
	* @param List<ClmstAddInput>
	* @return List<User>
	* @author FQQ
	* @date 2018.05.16
	*/
	int deleteProoftrn(@Param("companyID")String companyID,@Param("inputno")String Input_no);
	
	/**
	* 方法名 Lendtrnquery
	* 方法的说明 立替确认初始化
	* @param 
	* @return 
	* @author FQQ
	* @date 2018.05.16
	*/
	List<Lendtrn> Lendtrnquery(Lendtrn Lendtrn);
	/**
	* 方法名 LendtrnDate
	* 方法的说明 登录者,更新者，承认者，承认取消者
	* @param 
	* @return 
	* @author FQQ
	* @date 2018.05.16
	*/
	Lendtrn LendtrnDate(Lendtrn Lendtrn);
	/**
	* 方法名 rooftrnquery
	* 方法的说明 凭证信息初始化
	* @param 
	* @return 
	* @author FQQ
	* @date 2018.05.16
	*/
	List<Prooftrn> rooftrnquery(@Param("companyID")String companycd,@Param("inputno")String inputno);
	/**
	* 方法名 rooftrnquery
	* 方法的说明 凭证信息初始化
	* @param 
	* @return 
	* @author FQQ
	* @date 2018.05.16
	*/
	int cancelLendTrn(Lendtrn Lendtrn);
	String getstatus(@Param("jobcd")String jobcd,@Param("inputno")String inputno,@Param("companyID")String companyID);
	String QueryJobstatus(@Param("jobcd")String jobcd,@Param("companyID")String companyID);
	LendtrnConfirmBeen LendtrnConfirmPDF(@Param("jobcd")String jobcd,@Param("companycd")int companycd,@Param("inputno")String inputno);
	LendtrnApprovalBeen LendtrnApprovalPDF(@Param("jobcd")String jobcd,@Param("companycd")int companycd,@Param("inputno")String inputno,@Param("langTyp")String langTyp);
	int addOrder(OrderHistorytrn orderhistorytrn);
	int DeleteOrder(OrderHistorytrn orderhistorytrn);
	int queryLock(@Param("jobcd")String jobcd,@Param("companycd")String companycd,@Param("inputno")String inputno);
	int updateLock(@Param("jobcd")String jobcd,@Param("companycd")String companycd,@Param("inputno")String inputno);

	String getnewInputNo(@Param("jobcd")String jobcd, @Param("inputno")String inputno, @Param("companyID")String companyID);
}