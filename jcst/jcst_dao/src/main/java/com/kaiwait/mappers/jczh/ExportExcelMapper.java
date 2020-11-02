package com.kaiwait.mappers.jczh;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kaiwait.bean.jczh.entity.Role;
import com.kaiwait.bean.jczh.io.OutPutInput;
import com.kaiwait.common.dao.BaseMapper;
import com.kaiwait.bean.jczh.entity.ExcelReport;

public interface ExportExcelMapper extends BaseMapper{
	List<Role> getNodesByRoles(OutPutInput pars);
	List<Role> getNodes();
	List<ExcelReport> getReportListByJobs(@Param("companyID")String companyID,@Param("startDate")String startDate,@Param("endDate")String endDate,@Param("cldivID")String cldivID);
	List<ExcelReport> getReportListByJobsLabel(@Param("companyID")String companyID,@Param("startDate")String startDate,@Param("endDate")String endDate,@Param("cldivID")String cldivID);
	String getCompanyName(@Param("companyID")String companyID);
	List<ExcelReport> getReportListByMadeCost(@Param("companyID")String companyID,@Param("endDate")String endDate);
	List<ExcelReport> getReportListByPayable(@Param("companyID")String companyID,@Param("startDate")String startDate,@Param("endDate")String endDate);
	List<ExcelReport> getReportListByPayableButton(@Param("companyID")String companyID,@Param("startDate")String startDate,@Param("endDate")String endDate);
	ExcelReport getReportListByPayableTotal(@Param("companyID")String companyID,@Param("startDate")String startDate,@Param("endDate")String endDate);
	
	List<ExcelReport> getReportListByHopleCost(@Param("companyID")String companyID,@Param("startDate")String startDate,@Param("endDate")String endDate,@Param("choseDate")String choseDate,@Param("beforMonth")String beforMonth);
	List<ExcelReport> getRportJobList(@Param("companyID")String companyID,@Param("choseDate")String choseDate);
	Double getConfirmCostTotalAmtByJob(@Param("companyID")String companyID,@Param("jobNo")String jobNo);
	Double getNotConfirmCostTotalAmtByJob(@Param("companyID")String companyID,@Param("jobNo")String jobNo);
	ExcelReport getConfirmAmtAndConfirmVatByJoB(@Param("companyID")String companyID,@Param("jobNo")String jobNo,@Param("startDate")String startDate,@Param("endDate")String endDate);
	ExcelReport getNotConfirmAmtByJoB(@Param("companyID")String companyID,@Param("jobNo")String jobNo,@Param("startDate")String startDate,@Param("endDate")String endDate,@Param("beforDate")String beforDate,@Param("outMonth")String outMonth);
}