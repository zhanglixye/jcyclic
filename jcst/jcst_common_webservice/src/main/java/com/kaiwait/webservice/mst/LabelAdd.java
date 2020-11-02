package com.kaiwait.webservice.mst;


import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kaiwait.bean.mst.io.LabelInput;
import com.kaiwait.core.process.SingleFunctionIF;
import com.kaiwait.core.process.ValidateResult;
import com.kaiwait.service.mst.LabelService;
import com.kaiwait.thrift.common.server.annotation.Privilege;
import com.kaiwait.thrift.common.server.annotation.MatchEnum;

@Privilege(keys= {"71","72"}, match=MatchEnum.ANY)
@Component("LabelAdd")

public class LabelAdd implements SingleFunctionIF<LabelInput>{
	
	@Resource
	private LabelService LabelService;
	
	@Override
	public Object process(LabelInput inputParam) {
		int CompanyID = Integer.parseInt(inputParam.getCompanyID());
		String id = LabelService.getMaxKeyCode("JL",CompanyID);
		inputParam.getLabelmst().setLabel_id(id);
		inputParam.getLabelmst().setCompany_cd(CompanyID);
		return LabelService.addLabelTx(inputParam);
		
	}

	public ValidateResult validate(LabelInput inputParam) {		
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class getParamType() {
		// TODO Auto-generated method stub
		return LabelInput.class;
	}


}
