package com.kaiwait.webservice.jczh;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kaiwait.bean.jczh.io.AccountMonthInput;
import com.kaiwait.core.process.SingleFunctionIF;
import com.kaiwait.core.process.ValidateResult;
import com.kaiwait.service.jczh.MonthBalanceService;
@Component("monthCheckOut")
public class MonthAccountCheckOut implements SingleFunctionIF<AccountMonthInput>{
	@Resource
	private MonthBalanceService monthBalanceService;
	
	@Override
	public Object process(AccountMonthInput inputParam) {
		return monthBalanceService.checkOutMonthTx(Integer.valueOf(inputParam.getCompanyID()),Integer.valueOf(inputParam.getUserID()));
	}

	public ValidateResult validate(AccountMonthInput inputParam) {		
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class getParamType() {
		// TODO Auto-generated method stub
		return AccountMonthInput.class;
	}
}
