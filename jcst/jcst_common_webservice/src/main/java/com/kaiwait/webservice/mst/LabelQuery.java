package com.kaiwait.webservice.mst;


import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kaiwait.bean.mst.io.LabelInput;
import com.kaiwait.core.process.SingleFunctionIF;
import com.kaiwait.core.process.ValidateResult;
import com.kaiwait.service.mst.LabelService;
import com.kaiwait.thrift.common.server.annotation.MatchEnum;
import com.kaiwait.thrift.common.server.annotation.Privilege;
@Privilege(keys= {"71","72"}, match=MatchEnum.ANY)
@Component("LabelQuery")

public class LabelQuery implements SingleFunctionIF<LabelInput>{
	
	@Resource
	private LabelService LabelService;
	
	@Override
	public Object process(LabelInput inputParam) {
		return LabelService.searchLabelList(inputParam);
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
