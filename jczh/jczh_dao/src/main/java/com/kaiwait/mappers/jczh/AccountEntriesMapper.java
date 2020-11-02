package com.kaiwait.mappers.jczh;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kaiwait.bean.jczh.entity.AccountEntries;
import com.kaiwait.common.dao.BaseMapper;

public interface AccountEntriesMapper extends BaseMapper{
	AccountEntries getInfomation(@Param("companyID")String companyID,@Param("jobNo")String jobNo,@Param("userId")String userId);
	int isAccountDataWithJobSale(@Param("companyID")String companyID,@Param("jobNo")String jobNo);
	void createAccountEntriesData(@Param("accountEntries")AccountEntries accountEntries);
	AccountEntries getPayAccountInfomation(@Param("jobNo")String jobNo,@Param("costNo")String costNo,@Param("inputNo")String inputNo,
				@Param("companyID")String companyID,@Param("userId")String userId);
	AccountEntries getLendAccountInfomation(@Param("jobNo")String jobNo,@Param("inputNo")String inputNo,
			@Param("companyID")String companyID,@Param("userId")String userId);
	AccountEntries getTranAccountInfomation(@Param("jobNo")String jobNo,@Param("inputNo")String inputNo,
			@Param("companyID")String companyID,@Param("userId")String userId);
	void dropAccountDatasByCancel(@Param("jobNo")String jobNo, @Param("inputNo")String inputNo, @Param("companyID")String companyID);
	List<AccountEntries> getPayAccountDataInfomationByMonthCheckout(@Param("monthDate")String monthDate, @Param("companyID")int companyID, 
			@Param("userId")int userId,@Param("thisMonth")String thisMonth,@Param("thisMonthTime")String thisMonthTime,@Param("beforMonth")String beforMonth);
	List<AccountEntries> getLendAccountDataInfomationByMonthCheckout(@Param("monthDate")String monthDate, @Param("companyID")int companyID,
			@Param("userId")int userId,@Param("thisMonth")String thisMonth,@Param("thisMonthTime")String thisMonthTime,@Param("beforMonth")String beforMonth);
	List<AccountEntries> getTranAccountDataInfomationByMonthCheckout(@Param("monthDate")String monthDate, @Param("companyID")int companyID, 
			@Param("userId")int userId,@Param("thisMonth")String thisMonth,@Param("thisMonthTime")String thisMonthTime,@Param("beforMonth")String beforMonth);
	List<AccountEntries> getTaxAccountDataInfomationByMonthCheckout(@Param("monthDate")String monthDate, @Param("companyID")int companyID, 
			@Param("userId")int userId,@Param("thisMonth")String thisMonth,@Param("thisMonthTime")String thisMonthTime,@Param("beforMonth")String beforMonth);
	List<AccountEntries> getJobsForAccountEntries(@Param("thisMonth")String thisMonth,@Param("companyID")int companyID);
	List<AccountEntries> getAccountJobDetailsList(@Param("companyID")String companyID,@Param("outPutDate")String outPutDate,@Param("languageType")String languageType);
	List<AccountEntries> getAccountCostDetailsList(@Param("companyID")String companyID,@Param("outPutDate")String outPutDate,@Param("languageType")String languageType);
	List<AccountEntries> getAccountsummaryList(@Param("companyID")String companyID,@Param("outPutDate")String outPutDate,@Param("languageType")String languageType);
	List<AccountEntries> getAccountsummaryConfirmList(@Param("companyID")String companyID,@Param("outPutDate")String outPutDate,@Param("languageType")String languageType);
	List<AccountEntries> getBeforSaleAccountDataInfomationByMonthCheckout(@Param("monthDate")String monthDate,
			@Param("companyID")int companyID,@Param("userId")int userId,@Param("thisMonth")String thisMonth,@Param("addDate")String addDate);
}
