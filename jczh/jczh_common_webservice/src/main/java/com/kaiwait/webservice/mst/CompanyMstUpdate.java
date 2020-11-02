package com.kaiwait.webservice.mst;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kaiwait.bean.mst.io.CompanyMstInput;
import com.kaiwait.core.process.SingleFunctionIF;
import com.kaiwait.core.process.ValidateResult;
import com.kaiwait.service.mst.CompanyService;
import com.kaiwait.thrift.common.server.annotation.MatchEnum;
import com.kaiwait.thrift.common.server.annotation.Privilege;
@Component("companyMstUpd")
@Privilege(keys= {"88","89","90","91"}, match=MatchEnum.ANY)
public class CompanyMstUpdate implements SingleFunctionIF<CompanyMstInput>{
    @Resource
	private CompanyService companyService;
	@Override
	public Integer process(CompanyMstInput inputParam) {
		// TODO Auto-generated method stub
			//Company company = inputParam.getCompany();
			//company.setCompany_cd(Integer.valueOf(inputParam.getCompany_cd()));
			return companyService.updateCompanyTx(inputParam);
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
