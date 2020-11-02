package com.kaiwait.webservice.jczh;



import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import com.kaiwait.bean.jczh.entity.Columns;
import com.kaiwait.bean.jczh.io.CostInput;
import com.kaiwait.core.process.SingleFunctionIF;
import com.kaiwait.core.process.ValidateResult;
import com.kaiwait.service.jczh.JobService;

@Component("seljobColumns")
public class seljobColumns implements SingleFunctionIF<CostInput>{
	
	@Resource
	private JobService jobService;
	
	@Override
	public Object process(CostInput inputParam) {
		List<List<Columns>> outsoure = jobService.selColumns(Integer.parseInt(inputParam.getCompanyID()), 0, Integer.parseInt(inputParam.getUserID()), 1);
		return outsoure;
	}

	public ValidateResult validate(CostInput inputParam) {		
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class getParamType() {
		// TODO Auto-generated method stub
		return CostInput.class;
	}


}
