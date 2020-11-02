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
			return exportExcelService.getFromJppMaxAccountMonth(inputParam.getCompanyID());
		}
		if(inputParam.getFileName().equals("employeesPowerList"))
		{
			return exportExcelService.exportRoleReport(inputParam);
		}else{
			boolean flg = exportExcelService.isExportExcelFlg(inputParam);
			if(!flg)
			{
				return "DATA_IS_NOT_EXIST";
			}
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
		if(inputParam.getFileName().equals("madeCostList"))
		{
			return exportExcelService.exportMadeCostReport(inputParam);
		}
		if(inputParam.getFileName().equals("accountReports"))
		{
			return exportExcelService.outPutAccountEntriesPdf(inputParam);
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