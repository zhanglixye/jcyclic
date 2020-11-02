package com.kaiwait.webservice.jczh;



import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kaiwait.bean.jczh.io.TrantrnInput;
import com.kaiwait.core.process.SingleFunctionIF;
import com.kaiwait.core.process.ValidateResult;
import com.kaiwait.service.jczh.TrantrnService;
import com.kaiwait.thrift.common.server.annotation.Privilege;
import com.kaiwait.thrift.common.server.annotation.MatchEnum;

@Privilege(keys= {"61"}, match=MatchEnum.ANY)
@Component("TrantrnApproval")

public class TrantrnApproval implements SingleFunctionIF<TrantrnInput>{
	
	@Resource
	private TrantrnService TrantrnService;
	
	@Override
	public Object process(TrantrnInput inputParam) {
		String companycd =inputParam.getCompanyID();
		String usercd =inputParam.getUserID();
		inputParam.getTrantrn().setCompany_cd(companycd);
		inputParam.getTrantrn().setAdd_usercd(usercd);
		return TrantrnService.TrantrnApproval(inputParam);
		 
	}

	public ValidateResult validate(TrantrnInput inputParam) {		
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class getParamType() {
		// TODO Auto-generated method stub
		return TrantrnInput.class;
	}


}
