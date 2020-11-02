package com.kaiwait.webservice.jczh;



import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kaiwait.bean.jczh.io.LendtrnInput;
import com.kaiwait.core.process.SingleFunctionIF;
import com.kaiwait.core.process.ValidateResult;
import com.kaiwait.service.jczh.LendtrnService;
import com.kaiwait.thrift.common.server.annotation.Privilege;
import com.kaiwait.thrift.common.server.annotation.MatchEnum;

@Privilege(keys= {"60"}, match=MatchEnum.ANY)
@Component("cancelLend")

public class CancelLend implements SingleFunctionIF<LendtrnInput>{
	
	@Resource
	private LendtrnService LendtrnService;
	
	@Override
	public Object process(LendtrnInput inputParam) {
		return LendtrnService.cancelLendTrnTx(inputParam);
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