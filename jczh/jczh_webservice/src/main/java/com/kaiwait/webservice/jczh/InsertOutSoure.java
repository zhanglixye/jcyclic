package com.kaiwait.webservice.jczh;


import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kaiwait.bean.jczh.io.CostInput;
import com.kaiwait.core.process.SingleFunctionIF;
import com.kaiwait.core.process.ValidateResult;
import com.kaiwait.service.jczh.CommonMethedService;
import com.kaiwait.service.jczh.CostService;
import com.kaiwait.thrift.common.server.annotation.MatchEnum;
import com.kaiwait.thrift.common.server.annotation.Privilege;

@Component("insertOutSoure")
@Privilege(keys= {"44","45","46","47"},match=MatchEnum.ANY)
public class InsertOutSoure implements SingleFunctionIF<CostInput>{
	
	@Resource
	private CostService costService;
	@Resource
	private CommonMethedService commonMethedService;
	@Override
	public Object process(CostInput inputParam) {
		String cost_no = commonMethedService.getMaxKeyCode("C",Integer.valueOf(inputParam.getCompanyID()));
		inputParam.setCost_no(cost_no);
		return costService.insertCostOutSoureTx(inputParam);
	}

	public ValidateResult validate(CostInput inputParam) {		
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class getParamType() {
		// TODO Auto-generated method stub
		return CostInput.class;
	}


}
