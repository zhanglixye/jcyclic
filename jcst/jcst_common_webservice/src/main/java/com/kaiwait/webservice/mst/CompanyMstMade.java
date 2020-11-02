package com.kaiwait.webservice.mst;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kaiwait.bean.mst.io.CompanyMstInput;
import com.kaiwait.core.process.SingleFunctionIF;
import com.kaiwait.core.process.ValidateResult;
import com.kaiwait.service.mst.CompanyService;
@Component("companyMstMade")
public class CompanyMstMade implements SingleFunctionIF<CompanyMstInput>{
  @Resource
  private CompanyService companyService;
	@Override
	public Object process(CompanyMstInput inputParam) {
		// TODO Auto-generated method stub
			return companyService.madeGet();
	}

	@Override
	public ValidateResult validate(CompanyMstInput inputParam) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class getParamType() {
		// TODO Auto-generated method stub
		return CompanyMstInput.class;
	}
}
