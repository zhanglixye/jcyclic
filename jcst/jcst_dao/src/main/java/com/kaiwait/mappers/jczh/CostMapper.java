package com.kaiwait.mappers.jczh;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kaiwait.bean.jczh.entity.Cost;
import com.kaiwait.bean.jczh.entity.CostInfo;
import com.kaiwait.bean.jczh.entity.CostPDF;
import com.kaiwait.bean.jczh.entity.JobLableTrn;
import com.kaiwait.bean.jczh.entity.Lable;
import com.kaiwait.bean.jczh.io.CostInput;
import com.kaiwait.bean.jczh.io.OutPutInput;
import com.kaiwait.bean.jczh.io.PayInput;
import com.kaiwait.common.dao.BaseMapper;

public interface CostMapper extends BaseMapper{
	/** 
	* @Title: insertCostOutSoureTx
	* @Description: 插入外发数据
	* @param    jobInput 
	* @return List<JobList>    
	* @throws 
	*/
	int insertCostOutSoureTx(CostInput jobInput);
	
	int updateCostOutSoureTx(CostInput jobInput);
	
	/** 
	* @Title: insertCostOutSoureTx
	* @Description: 插入数据到发票表中
	* @param    jobInput 
	* @return List<JobList>    
	* @throws 
	*/
	int insertInvoiceintrnTx(CostInput jobInput);
	
	int updateInvoiceintrnTx(CostInput jobInput);
	
	List<Cost>  selectNewUpdUser(String job_cd);
	/** 
	* @Title: selectCLDIV
	* @Description: 查询job的得意先，以及更新者
	* @param    jobInput 
	* @return List<JobList>    
	* @throws 
	*/
	List<Cost>  selectCLDIV(@Param("job_cd")String job_cd,@Param("company_cd")String company_cd);
	
	/** 
	* @Title: selCostForeign
	* @Description: 查询外货
	* @param    jobInput 
	* @return List<JobList>    
	* @throws 
	*/
	List<Cost> selCostForeign(@Param("company_cd")int company_cd,@Param("codecd")String codecd,@Param("mstcd")String mstcd);
	List<Cost> selCostForeigntwo(@Param("company_cd")int company_cd,@Param("codecd")String codecd,@Param("mstcd")String mstcd);
	List<CostInfo> selCostForeignInfo(@Param("mstcd")String mstcd,@Param("company_cd")String company_cd);
	
	/** 
	* @Title: selectOutSource
	* @Description: 查询外发登录是外发信息
	* @param    jobInput 
	* @return List<JobList>    
	* @throws 
	*/
	List<Cost>  selectOutSource(@Param("job_cd")String job_cd,@Param("cost_no")String cost_no,@Param("company_cd")String company_cd);
	
	/** 
	* @Title: selectLableList
	* @Description: 查询自己的标签
	* @param    jobInput 
	* @return List<JobList>    
	* @throws 
	*/
	List<Lable>  selectLableList(@Param("usercd")String usercd,@Param("company_cd")String company_cd);
	
	 //插入 joblabletrn
	 int insertCostLable(@Param("jltrnList")List<JobLableTrn> jltrnList);
	 
	 //更新joblabletrn
	 int updateJobLable(@Param("jltrnList")List<JobLableTrn> jltrnList);
	 
	 List<Cost> selectPayeeType(@Param("mstcd")String mstcd,@Param("company_cd")String company_cd);
	 
	 List<Cost> selectTax(CostInput CostInput);


		/** 
		* @Title: selectCostInfo
		* @Description: 查询外发信息
		* @param    jobInput 
		* @return List<JobList>    
		* @throws 
		*/
	List<Cost>  selectCostInfo(@Param("job_cd")String job_cd,@Param("cost_no")String cost_no,@Param("company_cd")String company_cd);
		/** 
		* @Title: selInvoiceintrn
		* @Description: 查询外发发票
		* @param    jobInput 
		* @return List<JobList>    
		* @throws 
		*/
	List<Cost>  selInvoiceintrn(@Param("cost_no")String cost_no,@Param("company_cd")String company_cd);
	
	  //查询 标签 当前cost选择的标签 
	   List<JobLableTrn> selectCostLable(@Param("costno")String costno,@Param("company_cd")String company_cd,@Param("label_level")int label_level);

	/**   
	 * @Title: deleteOutSoure
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param job_cd
	 * @param costno
	 * @param companyID
	 * @return
	 * @return int  返回值  
	 * @author: "马有翼"
	 * @throws
	 */  
	int deleteOutSoure(@Param("job_cd")String job_cd,@Param("cost_no")String Cost_no,@Param("companyID")String companyID);
	   
	int updateCostStatus (PayInput inputParam); 
	
	CostPDF OrderPDF(OutPutInput payInput);
	
	int updPdftime(CostInput inputParam); 
	int updateCostinputno(CostInput inputParam); 
}