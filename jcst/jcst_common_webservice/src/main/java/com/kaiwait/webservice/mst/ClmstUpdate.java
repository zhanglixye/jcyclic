package com.kaiwait.webservice.mst;


import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kaiwait.bean.mst.io.ClmstAddInput;
import com.kaiwait.core.process.SingleFunctionIF;
import com.kaiwait.core.process.ValidateResult;
import com.kaiwait.service.mst.ClmstService;
import com.kaiwait.thrift.common.server.annotation.Privilege;
import com.kaiwait.thrift.common.server.annotation.MatchEnum;

@Privilege(keys= {"74"}, match=MatchEnum.ANY)
@Component("clmstUpdate")
public class ClmstUpdate  implements SingleFunctionIF<ClmstAddInput> {

	@Resource
	private ClmstService clmstService;
	
	@Override
	public  Object process(ClmstAddInput inputParam) {

		String UserID = inputParam.getUserID();
		int CompanyID = Integer.parseInt(inputParam.getCompanyID());
		inputParam.getClmst().setUpdusercd(UserID);
		inputParam.getClmst().setCompany_cd(CompanyID);
		return clmstService.updateClmstTx(inputParam);
	}
	@SuppressWarnings("rawtypes")
	@Override
	public Class getParamType() {
		return ClmstAddInput.class;
	}

	@Override
	public ValidateResult validate(ClmstAddInput inputParam) {
		// TODO Auto-generated method stub
		return null;
	}

}
