package com.kaiwait.service.jczh;

import com.kaiwait.bean.jczh.io.OutPutInput;

public interface ExportExcelService {
	//权限
	String exportRoleReport(OutPutInput pars);
	//job
	String exportJobsReport(OutPutInput pars);
	//当月卖上明细
	String exportMonthDetailReport(OutPutInput pars);
	//制作勘定残高明細
	String exportMadeCostReport(OutPutInput pars);
	//買掛金発生明細
	String exportPaybelReport(OutPutInput pars);
	//見込原価残高明細
	String hopleCostReport(OutPutInput pars);
	Object outPutAccountEntriesPdf(OutPutInput pars);
	boolean isExportExcelFlg(OutPutInput pars);
	String getFromJppMaxAccountMonth(String companyID);
}
