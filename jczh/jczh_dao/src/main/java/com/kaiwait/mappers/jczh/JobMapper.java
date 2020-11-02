package com.kaiwait.mappers.jczh;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kaiwait.bean.jczh.entity.Cost;
import com.kaiwait.bean.jczh.entity.JobInfo;
import com.kaiwait.bean.jczh.entity.JobList;
import com.kaiwait.bean.jczh.entity.Pay;
import com.kaiwait.bean.jczh.entity.SaleInfo;
import com.kaiwait.bean.jczh.io.JobListInput;
import com.kaiwait.common.dao.BaseMapper;

public interface JobMapper extends BaseMapper{
	/** 
	* @Title: searchJobList
	* @Description: 详细检索
	* @param    jobInput 
	* @return List<JobList>    
	* @throws 
	*/
	List<JobList> searchJobList(JobListInput jobInput);
	/** 
	* @Title: searchJobByKeyWord
	* @Description: 关键字检索
	* @param    jobInput 
	* @return List<JobList>    
	* @throws 
	*/
	List<JobList> searchJobByKeyWord(JobListInput jobInput);
	
	/** 
	* @Title: selectMstNameByCD
	* @Description: 根据主表编号(mstcd)和系统标号(itemcd)查询所有主表分类名
	* @param    mstcd 
	*  @param    itemcd 
	* @return List<JobList>    
	* @throws 
	*/
	List<JobList> selectMstNameByCD(@Param("mstcd")String mstcd,@Param("company_cd")String company_cd);
	/** 
	* @Title: selectUserDPM
	* @Description: 根据员工id查询员工所在部门
	* @param    mstcd 
	*  @param    itemcd 
	* @return List<JobList>    
	* @throws 
	*/
	List<JobList> selectUserDPM(@Param("usercd")String usercd,@Param("companyID")String companyID );
	
	/** 
	* @Title: selecCldivALL
	* @Description: 查询所有(得以先,请求先，相手先G会社)的id与名称
	* @param    client_flg 得以先标识，pay_flg请求先表示，hdy_flg是相手先G会社的标识。注意。这三个标识在此方法中只能有个有值。否则sql会报错。
	*  @param    itemcd 
	* @return List<JobList>    
	* @throws 
	*/
	//List<JobList> selecCldivALL(@Param("company_cd")String company_cd,@Param("client_flg")String client_flg,@Param("hdy_flg")String hdy_flg,@Param("pay_flg")String pay_flg);
	/** 
	* @Title: selecCldivALL
	* @Description: 查询所有壳上种目
	* @param   
	*  @param    itemcd 
	* @return List<JobList>    
	* @throws 
	*/
	List<JobList> selectSaleName(String companyID);
	JobInfo searchJobInfoByNo(@Param("jobNo")String jobNo,@Param("companyID")String companyID);
	SaleInfo searchSellOrderByJobNo(@Param("jobNo")String jobNo,@Param("companyID")String companyID);
	List<Cost> searchOrderNosByJobNo(@Param("jobNo")String jobNo,@Param("companyID")String companyID);
	int saleCancel (JobListInput jobInput);
	int saleAdmitCancel(JobListInput jobInput);
	  //原价完了 
	   int costFinish(JobListInput jobInput);
	 //原价完了 
	   int costFinishCancel(JobListInput jobInput);
	   
	   List<JobList>  selectjobStatus(JobListInput jobInput);
	   
	   int deleteratehistory(@Param("job_cd")String job_cd,@Param("company_cd")String company_cd);
	   
	  Pay selectLOCKFLG(JobListInput jobInput);
	List<Cost> searchOederNosByCostIsHave(@Param("jobNo")String jobNo,@Param("companyID")String companyID);
	List<Cost> searchOrderNosBysellplan(@Param("jobNo")String jobNo,@Param("companyID")String companyID);
	void addInvoiceWithJob(@Param("companyID")String companyID,@Param("jobNo")String jobNo,@Param("invoiceNo")String invoiceNo,@Param("userId")String userId,@Param("addDate")String addDate);
	void delInvoiceNosWithJob(@Param("companyID")String companyID,@Param("jobNo")String jobNo);
	void deljournalDataWithJob(@Param("companyID")String companyID,@Param("jobNo")String jobNo);
	String selectInvoiceNo(@Param("companyID")String  companyID, @Param("jobNo")String job_cd);
	String checkInvoiceIsShow(@Param("companyID")String  companyID, @Param("jobNo")String job_cd);
	int searchJobListCount(JobListInput jobInput);
	List<JobList> searchJobByKeyWordCount(JobListInput jobInput);
}