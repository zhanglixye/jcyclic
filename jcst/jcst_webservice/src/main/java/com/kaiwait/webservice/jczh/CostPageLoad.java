package com.kaiwait.webservice.jczh;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kaiwait.bean.jczh.io.CostInput;
import com.kaiwait.core.process.SingleFunctionIF;
import com.kaiwait.core.process.ValidateResult;
import com.kaiwait.service.jczh.CostService;
import com.kaiwait.thrift.common.server.annotation.MatchEnum;
import com.kaiwait.thrift.common.server.annotation.Privilege;
@Component("costPageLoad")
@Privilege(keys= {"40","41","42","43"}, match=MatchEnum.ANY)
public class CostPageLoad implements SingleFunctionIF<CostInput>{
	@Resource
	private CostService costService;
	
	@Override
	public Object process(CostInput inputParam) {
		return costService.pageLoad(inputParam.getCostListVo(),Integer.valueOf(inputParam.getCompanyID()),inputParam.getUserID(),inputParam.getCostListVo().getAd());
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
