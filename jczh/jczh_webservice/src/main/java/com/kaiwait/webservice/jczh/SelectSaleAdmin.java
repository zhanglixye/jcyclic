package com.kaiwait.webservice.jczh;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kaiwait.bean.jczh.io.SaleZhInput;
import com.kaiwait.core.process.SingleFunctionIF;
import com.kaiwait.core.process.ValidateResult;
import com.kaiwait.service.jczh.SaleTypeService;
import com.kaiwait.thrift.common.server.annotation.MatchEnum;
import com.kaiwait.thrift.common.server.annotation.Privilege;
@Component("selectSaleAdmin")
@Privilege(keys= {"23","24","25","26"}, match=MatchEnum.ANY)
public class SelectSaleAdmin implements SingleFunctionIF<SaleZhInput> {
 @Resource
   private SaleTypeService saleTypeService;
	@Override
	public Object process(SaleZhInput inputParam) {
		// TODO Auto-generated method stub
		
		return saleTypeService.selectSaleAdmin(inputParam);
	}

	@Override
	public ValidateResult validate(SaleZhInput inputParam) {
		// TODO Auto-generated method stub
		return null;
	}
	@SuppressWarnings("rawtypes")
	@Override
	public Class getParamType() {
		// TODO Auto-generated method stub
		return SaleZhInput.class;
	}

}
