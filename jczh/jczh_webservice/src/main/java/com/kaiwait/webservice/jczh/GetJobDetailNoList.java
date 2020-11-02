package com.kaiwait.webservice.jczh;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import com.kaiwait.bean.jczh.io.JobListInput;
import com.kaiwait.core.process.SingleFunctionIF;
import com.kaiwait.core.process.ValidateResult;
import com.kaiwait.service.jczh.JobService;
import com.kaiwait.thrift.common.server.annotation.MatchEnum;
import com.kaiwait.thrift.common.server.annotation.Privilege;
@Component("getJobDetailNoList")
@Privilege(keys= {"5","6","7","8"},match=MatchEnum.ANY)
public class GetJobDetailNoList implements SingleFunctionIF<JobListInput> {
	@Resource
	private JobService JobService;
	@Override
	public Object process(JobListInput inputParam) {
		// TODO Auto-generated method stub
		return JobService.getJobDetailNoList(inputParam);
	}

	@Override
	public ValidateResult validate(JobListInput inputParam) {
		// TODO Auto-generated method stub
		return null;
	}
	@SuppressWarnings("rawtypes")
	@Override
	public Class getParamType() {
		// TODO Auto-generated method stub
		return JobListInput.class;
	}

}