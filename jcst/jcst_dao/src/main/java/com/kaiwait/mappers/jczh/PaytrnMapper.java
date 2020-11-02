package com.kaiwait.mappers.jczh;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.kaiwait.bean.jczh.entity.Costtrn;
import com.kaiwait.bean.jczh.entity.Invoiceintrn;
import com.kaiwait.bean.jczh.entity.OrderHistorytrn;
import com.kaiwait.bean.jczh.entity.Paytrn;
import com.kaiwait.bean.jczh.entity.Prooftrn;
import com.kaiwait.common.dao.BaseMapper;

public interface PaytrnMapper extends BaseMapper{
	
	/**
	* 方法名 searchPaytrnList
	* 方法的说明 标签初始页面
	* @param 
	* @return List<Paytrn>
	* @author FQQ
	* @date 2018.05.16
	*/
	List<Paytrn> searchPaytrnList(@Param("usercd")String usercd,@Param("companycd")String companycd);
	
	/**
	* 方法名 getpayflg
	* 方法的说明 获取支付状态
	* @param 
	* @return List<Paytrn>
	* @author FQQ
	* @date 2018.05.16
	*/
	String getpayflg(@Param("inputno")String inputno,@Param("companycd")String companycd);
//	
	/**
	* 方法名 Paytrnapproval
	* 方法的说明 支付申请
	* @param 
	* @return 
	* @author FQQ
	* @date 2018.05.16
	*/
	int Paytrnapply(Paytrn Paytrn);
	
	/**
	* 方法名 Paytrnapproval
	* 方法的说明 付款信息批准
	* @param 
	* @return 
	* @author FQQ
	* @date 2018.05.16
	*/
	int Paytrnapproval(Paytrn Paytrn);
	
	/**
	* 方法名 Paytrnapproval
	* 方法的说明 付款信息驳回
	* @param 
	* @return 
	* @author FQQ
	* @date 2018.05.16
	*/
	int Paytrnapprovalback(Paytrn Paytrn);
	/**
	* 方法名 PaytrnApprovalCancel
	* 方法的说明 付款信息取消
	* @param 
	* @return 
	* @author FQQ
	* @date 2018.05.16
	*/
	int PaytrnApprovalCancel(Paytrn Paytrn);
	/**
	* 方法名 PaytrnApprovalCancel
	* 方法的说明 付款信息取消
	* @param 
	* @return 
	* @author FQQ
	* @date 2018.05.16
	*/
	int PaytrnApprovalJumpCancel(Paytrn Paytrn);
	
	/**
	* 方法名 updatePaytrn
	* 方法的说明 立替修改
	* @param 
	* @return 
	* @author FQQ
	* @date 2018.05.16
	* 
	*/
	int updatePaytrn(Paytrn Paytrn);
	/**
	* 方法名 insertPaytrn
	* 方法的说明 力替登录
	* @param List<ClmstAddInput>
	* @return List<User>
	* @author FQQ
	* @date 2018.05.16
	*/
	int insertPaytrn(Paytrn Paytrn);
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
	* 方法名 Paytrnquery
	* 方法的说明 立替确认初始化
	* @param 
	* @return 
	* @author FQQ
	* @date 2018.05.16
	*/
	List<Paytrn> Paytrnquery(Paytrn Paytrn);
	
	/**
	* 方法名 Paytrnquery
	* 方法的说明 立替确认初始化
	* @param 
	* @return 
	* @author FQQ
	* @date 2018.05.16
	*/
	List<Costtrn> Costtrnquery(Paytrn Paytrn);
	String Costtrnnoquery(@Param("input_no")String input_no,@Param("job_cd")String job_cd,@Param("company_cd")String company_cd);
	/**
	* 方法名 Paytrnquery
	* 方法的说明 立替确认初始化
	* @param 
	* @return 
	* @author FQQ
	* @date 2018.05.16
	*/
	List<Invoiceintrn> Invoiceintrnquery(@Param("num")String num,@Param("companyID")String companycd);
	
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
	Map<String,Object> getRemark(@Param("companyID")String companycd,@Param("inputno")String inputno,@Param("jobcd")String jobcd,@Param("costno")String costno);
	/**
	* 方法名 rooftrnquery
	* 方法的说明 凭证信息初始化
	* @param 
	* @return 
	* @author FQQ
	* @date 2018.05.16
	*/
	int costtrnUpdate(@Param("companycd")String companycd,@Param("jobcd")String jobcd,@Param("status")String status,@Param("costno")String costno);
	int getcostflg(@Param("companycd")String companycd,@Param("costno")String costno,@Param("jobcd")String jobcd);
	int getpayjump(@Param("companycd")String companycd);
	Map<String,Object> getClmstList(@Param("companyID")String companycd,@Param("inputno")String inputno,@Param("jobcd")String jobcd);
	int addOrder(OrderHistorytrn orderhistorytrn);
	int DeleteOrder(OrderHistorytrn orderhistorytrn);
	int queryLock(@Param("inputno")String inputno,@Param("companycd")String companycd);
	int updateLock(@Param("inputno")String inputno,@Param("companycd")String companycd);
	String queryUpuser(@Param("inputno")String inputno,@Param("companycd")String companycd);
}