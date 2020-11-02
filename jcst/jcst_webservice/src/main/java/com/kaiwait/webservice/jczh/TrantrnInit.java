package com.kaiwait.webservice.jczh;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kaiwait.bean.jczh.entity.Cost;
import com.kaiwait.bean.jczh.io.TrantrnInput;
import com.kaiwait.core.process.SingleFunctionIF;
import com.kaiwait.core.process.ValidateResult;
import com.kaiwait.service.jczh.CommonMethedService;
import com.kaiwait.service.jczh.CostService;
import com.kaiwait.service.jczh.TrantrnService;
import com.kaiwait.thrift.common.server.annotation.MatchEnum;
import com.kaiwait.thrift.common.server.annotation.Privilege;
@Privilege(keys= {"61"}, match=MatchEnum.ANY)
@Component("TrantrnInit")

public class TrantrnInit implements SingleFunctionIF<TrantrnInput>{
	
	@Resource
	private TrantrnService TrantrnService;
	@Resource
	private CommonMethedService commonMethedService;
	@Resource
	private CostService costService;
	
	@Override
	public Object process(TrantrnInput inputParam) {
		String companycd =inputParam.getCompanyID();
		Map<String,Object> outsoure = new  HashMap<>();
		TrantrnInput s=  TrantrnService.initTrantrnList(inputParam);
		String jobcd = inputParam.getTrantrn().getJob_cd();
		outsoure.put("TrantrnInput", s);
		List<Cost> sumcost  =commonMethedService.getSumcost(Integer.parseInt(companycd),jobcd);
		outsoure.put("sumcost", sumcost);	
		List<Cost> CLDIV=	costService.selectCLDIV(jobcd,companycd);
		outsoure.put("CLDIV", CLDIV);
		String rate = commonMethedService.getPayeetaxRate("902",companycd);
		outsoure.put("rate", rate);
		return outsoure;
		 
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
