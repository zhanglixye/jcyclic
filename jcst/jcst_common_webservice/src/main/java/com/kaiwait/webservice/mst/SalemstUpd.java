package com.kaiwait.webservice.mst;




import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kaiwait.bean.mst.entity.Salemst;
import com.kaiwait.bean.mst.io.SalemstInput;
import com.kaiwait.core.process.SingleFunctionIF;
import com.kaiwait.core.process.ValidateResult;
import com.kaiwait.service.mst.SalemstService;

/** 
* @ClassName: salemstUpd 
* @Description: 更新Sales信息的接口(这里用一句话描述这个类的作用) 
* @author mayouyi 
* @date 2018年5月4日 上午8:46:22 
*  
*/
@Component("salemstUpd")
public class SalemstUpd implements SingleFunctionIF<SalemstInput>{

	@Resource
	private SalemstService SalemstService;
	
	@Override
	public Object process(SalemstInput inputParam) {
		Salemst salemst = new Salemst();
		salemst=inputParam.getSalemst();
		salemst.setAdd_user(inputParam.getUserID());
		salemst.setCompany_cd(inputParam.getCompanyID());
		salemst.setCompanyID(inputParam.getCompanyID());
		return SalemstService.updateOldSaleTx(inputParam.getSalemst());
	}

	@Override
	public ValidateResult validate(SalemstInput inputParam) {
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class getParamType() {
		// TODO Auto-generated method stub
		return SalemstInput.class;
	}

}
