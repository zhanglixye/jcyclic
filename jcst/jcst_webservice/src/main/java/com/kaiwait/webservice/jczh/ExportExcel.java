package com.kaiwait.webservice.jczh;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kaiwait.bean.jczh.io.OutPutInput;
import com.kaiwait.core.process.SingleFunctionIF;
import com.kaiwait.core.process.ValidateResult;
import com.kaiwait.service.jczh.ExportExcelService;

@Component("exportExcel")

public class ExportExcel implements SingleFunctionIF<OutPutInput>{
	
	@Resource
	private ExportExcelService exportExcelService;
	
	@Override
	public Object process(OutPutInput inputParam) {
		if(inputParam.getFileName().equals("reportInit"))
		{
			return 1;
		}
		if(inputParam.getFileName().equals("employeesPowerList"))
		{
			return exportExcelService.exportRoleReport(inputParam);
		}
		if(inputParam.getFileName().equals("jobList") || inputParam.getFileName().equals("jobLabelsReport"))
		{
			return exportExcelService.exportJobsReport(inputParam);
		}
		if(inputParam.getFileName().equals("paybelReport"))
		{
			return exportExcelService.exportPaybelReport(inputParam);
		}
		if(inputParam.getFileName().equals("monthBalance"))
		{
			return exportExcelService.exportMonthDetailReport(inputParam);
		}
		if(inputParam.getFileName().equals("predestineCostList"))
		{
			return exportExcelService.hopleCostReport(inputParam);
		}
		return null;
	}

	public ValidateResult validate(OutPutInput inputParam) {		
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class getParamType() {
		// TODO Auto-generated method stub
		return OutPutInput.class;
	}


}