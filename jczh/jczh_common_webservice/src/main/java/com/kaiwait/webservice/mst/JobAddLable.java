package com.kaiwait.webservice.mst;


import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import com.kaiwait.bean.mst.io.LableInput;
import com.kaiwait.core.process.SingleFunctionIF;
import com.kaiwait.core.process.ValidateResult;
import com.kaiwait.service.mst.LableDealService;
import com.kaiwait.thrift.common.server.annotation.MatchEnum;
import com.kaiwait.thrift.common.server.annotation.Privilege;
@Component("jobAddLable")
@Privilege(keys= {"71","72"}, match=MatchEnum.ANY)
public class JobAddLable implements SingleFunctionIF<LableInput> {
	@Resource
	private LableDealService lableDealService;
	@Override
	public Object process(LableInput inputParam) {
		// TODO Auto-generated method stub
		return lableDealService.addLableTx(inputParam);
	}

	@Override
	public ValidateResult validate(LableInput inputParam) {
		// TODO Auto-generated method stub
		return null;
	}
	@SuppressWarnings("rawtypes")
	@Override
	public Class getParamType() {
		// TODO Auto-generated method stub
		return LableInput.class;
	}

}