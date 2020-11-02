package com.kaiwait.webservice.mst;



import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kaiwait.bean.mst.io.SalemstInput;
import com.kaiwait.bean.mst.vo.SalemstVo;
import com.kaiwait.core.process.SingleFunctionIF;
import com.kaiwait.core.process.ValidateResult;
import com.kaiwait.service.mst.SalemstService;
import com.kaiwait.thrift.common.server.annotation.MatchEnum;
import com.kaiwait.thrift.common.server.annotation.Privilege;

/** 
* @ClassName: salemstSel 
* @Description: 查询Sales信息的接口(这里用一句话描述这个类的作用) 
* @author mayouyi 
* @date 2018年5月2日 上午8:46:22 
*  
*/
@Component("salemstSel")
@Privilege(keys= {"81"}, match=MatchEnum.ANY)
public class SalemstSel implements SingleFunctionIF<SalemstInput>{

	@Resource
	private SalemstService SalemstService;
	
	@Override
	public List<SalemstVo> process(SalemstInput inputParam) {
		return SalemstService.select(inputParam.getSale_cd(),inputParam.getSale_account_cd(),inputParam.getSale_name(),inputParam.getDel_flg(),inputParam.getCompanyID());
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
