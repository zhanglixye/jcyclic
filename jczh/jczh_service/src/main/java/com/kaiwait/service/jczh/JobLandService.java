package com.kaiwait.service.jczh;

import java.util.List;

import com.kaiwait.bean.jczh.entity.JobLableTrn;
import com.kaiwait.bean.jczh.entity.JobLand;
import com.kaiwait.bean.jczh.entity.JobUserLable;
import com.kaiwait.bean.jczh.io.OutPutInput;
import com.kaiwait.bean.jczh.vo.JobLandVo;

import net.sf.jasperreports.engine.JRException;

public interface JobLandService {
	   Object insertJobTx(JobLand jobland,List<JobUserLable> julable,List<JobLableTrn> jltrn,String upusercd);
	   Object updateJobTx(JobLand jobland,List<JobUserLable> cardAll,List<JobUserLable> julable,List<JobUserLable> delcard,List<JobLableTrn> jltrn,String usercd);
	   JobLandVo initShow(int company_cd,String usercd,String jobcd);
//	   JobLand selectJobByCd(String jobcd);
	   int delJobTx(String jobcd,int companycd,String usercd,int lockFlg);
	   String jobLogOutPut (OutPutInput jobLand) throws JRException;
	   String reqInsOutPutTx(OutPutInput jobLand) throws JRException;
	   String bsTicketsOutPut(OutPutInput jobLand) throws JRException;
	   String jobUpdateOutPut(OutPutInput jobLand) throws JRException;
	   String saleAdminPdf(OutPutInput jobLand) throws JRException;
	   String invoiceOrderPdfTx(OutPutInput jobLand) throws JRException;
	   Object outMorePdf(OutPutInput jobLand) throws JRException;
	   String reqInsOutPutTx(OutPutInput jobLand,String jobcd) throws JRException;
	   String invoiceOrderPdfTx(OutPutInput jobLand,String jobcd) throws JRException;
}
