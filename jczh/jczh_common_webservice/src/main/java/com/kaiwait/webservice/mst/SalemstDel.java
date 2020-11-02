package com.kaiwait.webservice.mst;



import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kaiwait.bean.mst.io.SalemstInput;
import com.kaiwait.bean.mst.vo.SalemstVo;
import com.kaiwait.core.process.SingleFunctionIF;
import com.kaiwait.core.process.ValidateResult;
import com.kaiwait.service.mst.SalemstService;

/** 
* @ClassName: SalemstDel 
* @Description: 新增Sales信息的接口(这里用一句话描述这个类的作用) 
* @author mayouyi 
* @date 2018年5月4日 上午8:46:22 
*  
*/
@Component("salemstDel")
public class SalemstDel implements SingleFunctionIF<SalemstInput>{

	@Resource
	private SalemstService SalemstService;
	
	@Override
	public List<SalemstVo> process(SalemstInput inputParam) {
		inputParam.getSalemst().setCompany_cd(inputParam.getCompanyID());
		SalemstService.deleteSaleTx(inputParam.getSalemst());
		return null;
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
