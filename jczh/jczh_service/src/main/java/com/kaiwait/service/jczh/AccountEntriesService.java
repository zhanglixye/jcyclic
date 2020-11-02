package com.kaiwait.service.jczh;


public interface AccountEntriesService {
	void createSaleAccountsData(String companyID,String jobNo,String userId,String actionName);
	void createPayAccountsData(String jobNo,String costno,String inputNo,String companyID,String userId,String monthDate);
	void createLendAccountsData(String jobNo,String inputNo,String companyID,String userId,String monthDate);
	void createTranAccountsData(String jobNo,String inputNo,String companyID,String userId,String monthDate);
	void dropAccountDatasByCancel(String jobNo,String inputNo,String companyID);
	void crateAccountDataByMonthCheckoutTx(String aMonth,int companyID,int userId,String thisMonth);
}
