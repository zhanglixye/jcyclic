package com.kaiwait.webservice.mst;

import java.util.List;

import org.springframework.stereotype.Component;

import com.kaiwait.bean.mst.entity.Company;
import com.kaiwait.bean.mst.io.Company0000001Input;
import com.kaiwait.core.process.SingleFunctionIF;
import com.kaiwait.core.process.ValidateResult;

@Component("Company0000001")
public class Company0000001 implements SingleFunctionIF<Company0000001Input>{
	//private CompanyService companyService;
	
	@Override
	public List<Company> process(Company0000001Input inputParam) {
		// TODO Auto-generated method stub
		//Company ss =inputParam.getCompany();
		
		// @SuppressWarnings("unused")
		//List<Company> bb = companyService.selectCompany(ss.getCompany_full_name());
		//userService.delUser("110");
		//companyService.getCompanyObj(10);
		
		return null;
	}

	@Override
	public ValidateResult validate(Company0000001Input inputParam) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class getParamType() {
		// TODO Auto-generated method stub
		return Company0000001Input.class;
	}

	
}
