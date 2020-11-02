package com.kaiwait.service.jczh;

import java.util.List;

import com.kaiwait.bean.jczh.entity.Calculate;
import com.kaiwait.bean.jczh.entity.Columns;
import com.kaiwait.bean.jczh.entity.Cost;
import com.kaiwait.bean.jczh.io.JobZhInput;
import com.kaiwait.bean.jczh.vo.SalemstVo;

public interface CommonMethedService {
	
	String getMaxKeyCode(String keyFlg,int company_cd);
	String getMaxCode(String keyFlg,String jobCD);
	//查询成本最新更新记录
	List<Cost> getSumcost(int company_cd,String job_cd);
	//查询job得意先，以及最新更新信息
	List<Cost> selectCLDIV(int company_cd,String job_cd);
	List<List<Columns>> selColumns(int company_cd,int level,int usercd,int ison);
	String getSystemDate(int company_cd);
	String getPayeetaxRate(String num,String company_cd);
	String getTimeByZone(String datetime,String companyID);
	String getTimeByZone(String datetime,String companyID,String formatStr);
	SalemstVo getRateByDateAndSaleID(JobZhInput inputParam);
	boolean validatePower(String jobcd,String usercd,int companycd,String cldivcd,String interflg);
	String changSqlInput(String oldword);
	Calculate calcuateJobTax(String jobNo,int companyID);
	Double calcuateJobTax(String jobNo,int companyID,String beforDate,String startDate,String endDate,String outDate,int isOutMonthFlg);
    Double pointFormatHandler(Double amt,int calFlg,int pointNumber);
    //千分符转换
    String getNewNum(String num,int count);
}
