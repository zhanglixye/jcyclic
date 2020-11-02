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
* @ClassName: VatRateSel 
* @Description: 查询壳上历史增值税信息的接口(这里用一句话描述这个类的作用) 
* @author mayouyi 
* @date 2018年5月2日 上午8:46:22 
*  
*/
@Component("VatRateSel")
public class VatRateSel implements SingleFunctionIF<SalemstInput>{

	@Resource
	private SalemstService SalemstService;
	
	@Override
	public List<SalemstVo> process(SalemstInput inputParam) {
		if(inputParam.getSale_cd()!=null) {
			return SalemstService.selectVatRateByCD(inputParam.getSale_cd(),inputParam.getCompanyID());	
		}
		return null;
		
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
