package com.kaiwait.webservice.jczh;



import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kaiwait.bean.jczh.io.TrantrnInput;
import com.kaiwait.core.process.SingleFunctionIF;
import com.kaiwait.core.process.ValidateResult;
import com.kaiwait.service.jczh.CommonMethedService;
import com.kaiwait.service.jczh.TrantrnService;


@Component("TrantrnUpdate")

public class TrantrnUpdate implements SingleFunctionIF<TrantrnInput>{
	
	@Resource
	private TrantrnService TrantrnService;
	@Resource
	private CommonMethedService CommonMethedService;
	
	@Override
	public Object process(TrantrnInput inputParam) {
		String companycd =inputParam.getCompanyID();
		String usercd =inputParam.getUserID();
		inputParam.getTrantrn().setCompany_cd(companycd);
		inputParam.getTrantrn().setAdd_usercd(usercd);
		//新旧no
		String id = CommonMethedService.getMaxKeyCode("T",Integer.parseInt(companycd));
		inputParam.getTrantrn().setNew_input_no(id);
		inputParam.getTrantrn().setOld_input_no(inputParam.getTrantrn().getInput_no());
		return TrantrnService.TrantrnUpdateTx(inputParam);
		 
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
