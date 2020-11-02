package com.kaiwait.webservice.jczh;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kaiwait.bean.jczh.entity.Cost;
import com.kaiwait.bean.jczh.io.CostInput;
import com.kaiwait.core.process.SingleFunctionIF;
import com.kaiwait.core.process.ValidateResult;
import com.kaiwait.service.jczh.CommonMethedService;
import com.kaiwait.service.jczh.CostService;
import com.kaiwait.thrift.common.server.annotation.MatchEnum;
import com.kaiwait.thrift.common.server.annotation.Privilege;

@Component("initCostupdate")
@Privilege(keys= {"44","45","46","47"},match=MatchEnum.ANY)
public class initCostupdate implements SingleFunctionIF<CostInput>{
	
	@Resource
	private CostService costService;
	@Resource
	private CommonMethedService commonMethedService;
	
	@Override
	public Object process(CostInput inputParam) {
		int ss =Integer.parseInt(inputParam.getCompanyID());
		List<Cost> sumcost  =commonMethedService.getSumcost(ss,inputParam.getJob_cd());
		if(sumcost.get(0).getUpdatetime()!="") {
			sumcost.get(0).setUpdatetime(commonMethedService.getTimeByZone(sumcost.get(0).getUpdatetime(),inputParam.getCompanyID()));	
		}
		Map<String,Object> outsoure = costService.initCostupdate(inputParam);
		outsoure.put("sumcost", sumcost);	
		return outsoure;
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
