package com.kaiwait.webservice.jczh;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kaiwait.bean.jczh.io.AccountMonthInput;
import com.kaiwait.core.process.SingleFunctionIF;
import com.kaiwait.core.process.ValidateResult;
import com.kaiwait.service.jczh.MonthBalanceService;
import com.kaiwait.thrift.common.server.annotation.MatchEnum;
import com.kaiwait.thrift.common.server.annotation.Privilege;

@Privilege(keys= {"63"}, match=MatchEnum.ANY)
@Component("monthBalance")
public class MonthBalance implements SingleFunctionIF<AccountMonthInput>{
	@Resource
	private MonthBalanceService monthBalanceService;
	
	@Override
	public Object process(AccountMonthInput inputParam) {
		return monthBalanceService.getAccountBasicInfo(Integer.valueOf(inputParam.getCompanyID()));
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
