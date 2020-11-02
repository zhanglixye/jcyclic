package com.kaiwait.service.jczh;

import com.kaiwait.bean.jczh.entity.AccountMonth;

public interface MonthBalanceService {

	AccountMonth getAccountBasicInfo(int companyID);

	String checkOutMonthTx(int companyID, int userID);
	
}
