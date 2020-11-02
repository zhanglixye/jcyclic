package com.kaiwait.webservice.mst;


import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kaiwait.bean.mst.io.PayeeTaxAddInput;
import com.kaiwait.core.process.SingleFunctionIF;
import com.kaiwait.core.process.ValidateResult;
import com.kaiwait.service.mst.PayeeTaxService;
import com.kaiwait.thrift.common.server.annotation.MatchEnum;
import com.kaiwait.thrift.common.server.annotation.Privilege;
@Privilege(keys= {"82"}, match=MatchEnum.ANY)
@Component("PayeeTaxList")
public class PayeeTaxList  implements SingleFunctionIF<PayeeTaxAddInput> {

	@Resource
	private PayeeTaxService PayeeTaxService;
	
	@Override
	public  Object process(PayeeTaxAddInput inputParam) {
		//列表查询
		return PayeeTaxService.queryPayeeTaxList(inputParam);

	}
	@SuppressWarnings("rawtypes")
	@Override
	public Class getParamType() {
		return PayeeTaxAddInput.class;
	}

	@Override
	public ValidateResult validate(PayeeTaxAddInput inputParam) {
		// TODO Auto-generated method stub
		return null;
	}

}
