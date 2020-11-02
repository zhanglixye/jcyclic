package com.kaiwait.webservice.jczh;



import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kaiwait.bean.jczh.io.LendtrnInput;
import com.kaiwait.core.process.SingleFunctionIF;
import com.kaiwait.core.process.ValidateResult;
import com.kaiwait.service.jczh.CommonMethedService;
import com.kaiwait.service.jczh.LendtrnService;
import com.kaiwait.thrift.common.server.annotation.Privilege;

import com.kaiwait.thrift.common.server.annotation.MatchEnum;

@Privilege(keys= {"56","57","58","59"}, match=MatchEnum.ANY)
@Component("LendtrnUpdate")

public class LendtrnUpdate implements SingleFunctionIF<LendtrnInput>{
	
	@Resource
	private LendtrnService LendtrnService;
	@Resource
	private CommonMethedService CommonMethedService;
	
	@Override
	public Object process(LendtrnInput inputParam) {
		String companycd =inputParam.getCompanyID();
		String usercd =inputParam.getUserID();
		inputParam.getLendtrn().setCompany_cd(companycd);
		inputParam.getLendtrn().setAddusercd(usercd);
		//新旧no
		String id = CommonMethedService.getMaxKeyCode("R",Integer.parseInt(companycd));
		inputParam.getLendtrn().setNew_input_no(id);
		inputParam.getLendtrn().setOld_input_no(inputParam.getLendtrn().getInput_no());
		return LendtrnService.LendtrnUpdateTx(inputParam);

		 
	}

	public ValidateResult validate(LendtrnInput inputParam) {		
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class getParamType() {
		// TODO Auto-generated method stub
		return LendtrnInput.class;
	}


}
