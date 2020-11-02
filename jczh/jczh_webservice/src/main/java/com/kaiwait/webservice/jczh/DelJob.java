package com.kaiwait.webservice.jczh;


import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kaiwait.bean.jczh.io.JobZhInput;
import com.kaiwait.core.process.SingleFunctionIF;
import com.kaiwait.core.process.ValidateResult;
import com.kaiwait.service.jczh.CommonMethedService;
import com.kaiwait.service.jczh.JobLandService;
import com.kaiwait.thrift.common.server.annotation.MatchEnum;
import com.kaiwait.thrift.common.server.annotation.Privilege;
@Component("delJob")
@Privilege(keys= {"9","10","11","12","13"}, match=MatchEnum.ANY)
public class DelJob implements SingleFunctionIF<JobZhInput> {
	@Resource
	private JobLandService jobLandService;
	@Resource
	private CommonMethedService commonMethedService;
	@Override
	public Object process(JobZhInput inputParam) {
		// TODO Auto-generated method stub
			return jobLandService.delJobTx(inputParam.getJob_cd(),Integer.valueOf(inputParam.getCompanyID()),inputParam.getUserID(),inputParam.getLockFlg());
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