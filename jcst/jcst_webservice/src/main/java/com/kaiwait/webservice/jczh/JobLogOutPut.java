package com.kaiwait.webservice.jczh;




import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import com.kaiwait.bean.jczh.io.OutPutInput;
import com.kaiwait.core.process.SingleFunctionIF;
import com.kaiwait.core.process.ValidateResult;
import com.kaiwait.service.jczh.CostService;
import com.kaiwait.service.jczh.JobLandService;
import com.kaiwait.service.jczh.LendtrnService;
import com.kaiwait.service.jczh.PayService;
import com.kaiwait.service.jczh.TrantrnService;
@Component("jobLogOutPut")
public class JobLogOutPut implements SingleFunctionIF<OutPutInput> {
	@Resource
	private JobLandService jobLandService;
	@Resource
	private CostService costService;
	@Resource
	private LendtrnService lendtrnService;
	@Resource
	private TrantrnService trantrnService;
	@Resource
	private PayService payService;
	
	
	@Override
	public Object process(OutPutInput inputParam) {
		try {
			//JOB登録確認票,JOB登録確認票（変更）
			if(inputParam.getFileName().equals("jobCreate"))
			{
				return jobLandService.jobLogOutPut(inputParam);
			}
			if(inputParam.getFileName().equals("jobEdit"))
			{
				return jobLandService.jobUpdateOutPut(inputParam);
			}
			//請求指示書
//			if(inputParam.getFileName().equals("billOrderPdf")&&!inputParam.isMoreFlg())
//			{
//				return jobLandService.reqInsOutPutTx(inputParam);
//			}
			//請求指示書
			if(inputParam.getFileName().equals("billOrder"))
			{
				return jobLandService.outMorePdf(inputParam);
			}
//			//発票発行依頼書
//			if(inputParam.getFileName().equals("invoicePdf")&&!inputParam.isMoreFlg())
//			{
//				return jobLandService.invoiceOrderPdfTx(inputParam);
//			}
			//発票発行依頼書
//			if(inputParam.getFileName().equals("invoicePdf"))
//			{
//				return jobLandService.outMorePdf(inputParam);
//			}
			//発注書
			if(inputParam.getFileName().equals("reportCreate"))
			{
				return costService.OrderPDFTx(inputParam);
			}
			//インプット確認票（支払）
			if(inputParam.getFileName().equals("payCreate"))
			{
				return payService.PayConfirmOutPDF(inputParam);
			}
			//支払申請票
			if(inputParam.getFileName().equals("payRequest"))
			{
				return payService.PayConfirmOutPDF(inputParam);
			}
			//支払承認票
			if(inputParam.getFileName().equals("confirmPay"))
			{
				return payService.PayConfirmOutPDF(inputParam);
			}
			if(inputParam.getFileName().equals("deboursCreate"))
			{
				return lendtrnService.LendtrnConfirmPDF(inputParam);
			}
			//立替原価承認票
			if(inputParam.getFileName().equals("confirmDebours"))
			{
				return lendtrnService.LendtrnApprovalPDF(inputParam);
			}
			//インプット確認票（振替）
			if(inputParam.getFileName().equals("onCostCreate"))
			{
				return trantrnService.TrantrnConfirmPDF(inputParam);
			}
			//経費振替承認票
			if(inputParam.getFileName().equals("confirmOncost"))
			{
				return trantrnService.TrantrnApprovalPDF(inputParam);
			}
			//売買登録票
			if(inputParam.getFileName().equals("jobDetailSale"))
			{
				return jobLandService.bsTicketsOutPut(inputParam);
			}
			//売買承认票
			if(inputParam.getFileName().equals("saleAdminPdf"))
			{
				return jobLandService.saleAdminPdf(inputParam);
			}
			//发票
			if(inputParam.getFileName().equals("invoiceApplication"))
			{
				return jobLandService.outMorePdf(inputParam);
			}
		} catch (Exception e) {
			return null;
		}
		return  null;
	}

	@Override
	public ValidateResult validate(OutPutInput inputParam) {
		// TODO Auto-generated method stub
		return null;
	}
	@SuppressWarnings("rawtypes")
	@Override
	public Class getParamType() {
		// TODO Auto-generated method stub
		return OutPutInput.class;
	}

}
