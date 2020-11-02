package com.kaiwait.webservice.mst;


import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kaiwait.bean.mst.io.CompanyMstInput;
import com.kaiwait.core.process.SingleFunctionIF;
import com.kaiwait.core.process.ValidateResult;
import com.kaiwait.core.utils.DateUtil;
import com.kaiwait.service.mst.CompanyService;
import com.kaiwait.service.mst.UserService;
import com.kaiwait.thrift.common.server.annotation.MatchEnum;
import com.kaiwait.thrift.common.server.annotation.Privilege;

@Component("companyMstInsert")
@Privilege(keys= {"87"}, match=MatchEnum.ANY)
public class CompanyMstInsert implements SingleFunctionIF<CompanyMstInput>{
	@Resource
	private CompanyService companyService;
	@Resource
	private UserService userService;
	@Override
	public Object process(CompanyMstInput inputParam) {
		try {
			Map<String, Object> map = companyService.insertCompanyTx(inputParam);
			if(!map.isEmpty()) {
				int usercd =  (int) map.get("usercd");
				int companycd = (int) map.get("companycd");
				String logid = (String) map.get("logId");
				String pwd = (String) map.get("pwd");
				userService.setUserTableHeaders(Integer.valueOf(usercd), Integer.toString(companycd),DateUtil.getDateForNow(DateUtil.dateTimeFormat),inputParam.getUserID(),"1");
				return logid+":"+pwd;
			}else {
				return 0;
			}
		} catch (Exception e) {
			// TODO: handle exception
			return 0;
		}
	}
	@Override
	public ValidateResult validate(CompanyMstInput inputParam) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class getParamType() {
		// TODO Auto-generated method stub
		return CompanyMstInput.class;
	}

	
}
