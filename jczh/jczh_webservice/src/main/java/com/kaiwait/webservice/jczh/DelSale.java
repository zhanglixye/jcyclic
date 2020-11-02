package com.kaiwait.webservice.jczh;


import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kaiwait.bean.jczh.io.JobZhInput;
import com.kaiwait.core.process.SingleFunctionIF;
import com.kaiwait.core.process.ValidateResult;
import com.kaiwait.service.jczh.CommonMethedService;
import com.kaiwait.service.jczh.SaleTypeService;
import com.kaiwait.thrift.common.server.annotation.MatchEnum;
import com.kaiwait.thrift.common.server.annotation.Privilege;
@Component("delSale")
@Privilege(keys= {"22"}, match=MatchEnum.ANY)
public class DelSale implements SingleFunctionIF<JobZhInput> {
	@Resource
	private SaleTypeService saleTypeService;
	@Resource
	private CommonMethedService commonMethedService;
	@Override
	public Object process(JobZhInput inputParam) {
		// TODO Auto-generated method stub
			return saleTypeService.delSaleTx(inputParam.getJob_cd(),Integer.valueOf(inputParam.getCompanyID()),inputParam.getUserID(),inputParam.getSaleLockFlg(),
					inputParam.getRecLockFlg());
	}

	@Override
	public ValidateResult validate(JobZhInput inputParam) {
		// TODO Auto-generated method stub
		return null;
	}
	@SuppressWarnings("rawtypes")
	@Override
	public Class getParamType() {
		// TODO Auto-generated method stub
		return JobZhInput.class;
	}

}