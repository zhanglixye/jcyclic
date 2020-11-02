package com.kaiwait.mappers.jczh;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kaiwait.bean.jczh.entity.AccountMonth;
import com.kaiwait.bean.jczh.entity.Job;
import com.kaiwait.bean.jczh.entity.OrderHistory;
import com.kaiwait.common.dao.BaseMapper;

public interface MonthBalanceMapper extends BaseMapper{
	AccountMonth getAccountInfo(@Param("companyID")int companyID,@Param("sysDate")String sysDate);
	int upLendReject(@Param("companyID")int companyID,@Param("aMonth")Date aMonth);
	List<Job> getJobsByWillFinsh(@Param("companyID")int companyID,@Param("aMonth")Date aMonth);
	int updateAccountJobInfo(@Param("companyID")int companyID,@Param("dateNow")Date dateNow,@Param("userID")int userID,@Param("aMonth")Date aMonth);
	int updateJobSetEnd(@Param("companyID")int companyID,@Param("jobList")List<Job> jobList,@Param("accountDate")Date accountDate);
	void addSummary(@Param("companyID")int companyID,@Param("userID")int userID,@Param("accountMonth")AccountMonth accountMonth,@Param("accountDate")Date accountDate,@Param("aMonth")Date aMonth);
	int updateAccountMonth(@Param("companyID")int companyID,@Param("nextMonth")String nextMonth,@Param("nextMonthTime")String nextMonthTime);
	int updateCostEnd(@Param("companyID")int companyID,@Param("jobList")List<Job> jobList);
	int updateLendEnd(@Param("companyID")int companyID,@Param("jobList")List<Job> jobList);
	int updateTranEnd(@Param("companyID")int companyID,@Param("jobList")List<Job> jobList);
	List<Job> searchJobsByMonth(@Param("companyID")int companyID,@Param("sysDate")String sysDate);
	List<Job> getNotCheckAmtByMonth(@Param("companyID")int companyID,@Param("sysDate")String sysDate);
	int addPurpose(@Param("jobList")List<Job> jobList,@Param("companyID")int companyID,@Param("userID")int userID,@Param("accountDate")Date accountDate);
	List<Job> getJobsByNotAdmit(@Param("companyID")int companyID,@Param("aMonth")Date aMonth);
	int delAfterPurpose(@Param("jobNo")String jobNo,@Param("companyID")int companyID,@Param("orderNo")String orderNo);
	
	List<OrderHistory> getNotCheckCostByHistory(@Param("companyID")int companyID,@Param("sysDate")String sysDate);
	
	int addOrderByHistory(@Param("order")OrderHistory order,@Param("companyID")int companyID,@Param("userID")int userID,@Param("accountDate")Date accountDate);
	int addJobSellRateByHistory(@Param("companyID")int companyID,@Param("jobNo")String jobNo,@Param("rate2")Double rate2,@Param("rate3")Double rate3,@Param("nowDate")String nowDate,@Param("usercd")String usercd);
	
	List<Job> getCostNotFinshJobs(@Param("companyID")int companyID,@Param("sysDate")String sysDate);
	List<Job> getSellRegistrationNotConfirmJobs(@Param("companyID")int companyID,@Param("sysDate")String sysDate);
	void upCostFinshFlgByJobs(@Param("companyID")int companyID,@Param("dateNow")String dateNow,@Param("job")Job job);
	void upSellNotConfirmJobs(@Param("companyID")int companyID,@Param("dateNow")String dateNow,@Param("job")Job job);
	
}