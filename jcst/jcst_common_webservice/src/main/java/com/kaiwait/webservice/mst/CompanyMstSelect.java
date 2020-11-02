package com.kaiwait.webservice.mst;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kaiwait.bean.mst.entity.Company;
import com.kaiwait.bean.mst.io.CompanyMstInput;
import com.kaiwait.core.process.SingleFunctionIF;
import com.kaiwait.core.process.ValidateResult;
import com.kaiwait.service.mst.CompanyService;
import com.kaiwait.thrift.common.server.annotation.MatchEnum;
import com.kaiwait.thrift.common.server.annotation.Privilege;
@Component("companyMstSelect")
@Privilege(keys= {"84","85","86"}, match=MatchEnum.ANY)
public class CompanyMstSelect implements SingleFunctionIF<CompanyMstInput>{
	@Resource
	private CompanyService companyService;
	@Override
	public List<Company> process(CompanyMstInput inputParam) {
		// TODO Auto-generated method stub
			return companyService.selectCompany(inputParam.getUserID(),Integer.valueOf(inputParam.getCompanyID()),inputParam.getCompany_full_name(),inputParam.getDel_flg());
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
