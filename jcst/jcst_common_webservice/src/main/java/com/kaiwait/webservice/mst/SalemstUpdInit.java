package com.kaiwait.webservice.mst;




import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kaiwait.bean.mst.io.SalemstInput;
import com.kaiwait.core.process.SingleFunctionIF;
import com.kaiwait.core.process.ValidateResult;
import com.kaiwait.service.mst.SalemstService;

/** 
* @ClassName: SalesSelect 
* @Description: 查询Sales信息的接口(这里用一句话描述这个类的作用) 
* @author mayouyi 
* @date 2018年5月2日 上午8:46:22 
*  
*/
@Component("salemstUpdInit")
public class SalemstUpdInit implements SingleFunctionIF<SalemstInput>{

	@Resource
	private SalemstService SalemstService;
	
	@Override
	public Object process(SalemstInput inputParam) {
		Object ss =SalemstService.salemstUpdInit(inputParam.getSale_cd(),inputParam.getCompanyID());

		return 	ss;	
		//return "login............";
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
