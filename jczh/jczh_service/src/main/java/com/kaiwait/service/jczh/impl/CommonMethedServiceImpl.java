package com.kaiwait.service.jczh.impl;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ibm.icu.math.BigDecimal;
import com.ibm.icu.text.DecimalFormat;
import com.kaiwait.bean.jczh.entity.AccountEntries;
import com.kaiwait.bean.jczh.entity.Calculate;
import com.kaiwait.bean.jczh.entity.Columns;
import com.kaiwait.bean.jczh.entity.Cost;
import com.kaiwait.bean.jczh.entity.Role;
import com.kaiwait.bean.jczh.io.JobZhInput;
import com.kaiwait.bean.jczh.vo.CommonmstVo;
import com.kaiwait.bean.jczh.vo.SalemstVo;
import com.kaiwait.core.utils.DateUtil;
import com.kaiwait.core.utils.MathController;
import com.kaiwait.mappers.jczh.CommonMethedMapper;
import com.kaiwait.mappers.jczh.JobLandMapper;
import com.kaiwait.service.jczh.CommonMethedService;
import com.kaiwait.utils.common.StringUtil;

@Service
public class CommonMethedServiceImpl implements CommonMethedService{
	@Resource
	private CommonMethedMapper commonMethedMapper;
	@Resource
	private JobLandMapper jobLandMapper;
	
	public String getMaxKeyCode(String keyFlg,int company_cd) {
		String sysDate = this.getSystemDate(company_cd);
		String nowDate = this.getTimeByZone(DateUtil.getDateForNow(DateUtil.dateTimeFormat), String.valueOf(company_cd));
		nowDate = DateUtil.getNewTime(nowDate,0,DateUtil.dateFormat);
		int number_rules = commonMethedMapper.selectNumberRules(company_cd);
		String ss = "";
		if(keyFlg.equals("J")){
			ss=keyFlg + commonMethedMapper.getMaxJobCode(company_cd, number_rules,sysDate);
		}else if(keyFlg.equals("JL"))
		{
			ss="L" + commonMethedMapper.getMaxLableCode(company_cd,sysDate);
		}else if(keyFlg.equals("C")){
			ss = commonMethedMapper.getCostMaxCode(company_cd,nowDate);
		}else if(keyFlg.equals("R")){
			ss = commonMethedMapper.getDeboursMaxCode(company_cd,nowDate);
		}else if(keyFlg.equals("T")){
			ss = commonMethedMapper.getOncostMaxCode(company_cd,nowDate);
		}else if(keyFlg.equals("P")){
			ss = commonMethedMapper.getPayMaxInPutNo(company_cd,nowDate);
		}else if(keyFlg.equals("REC"))
		{
			ss = commonMethedMapper.getRecMaxCode(company_cd,sysDate);
		}
		else if(keyFlg.equals("REQ"))
		{
			ss = commonMethedMapper.getReqMaxCode(company_cd,sysDate);
		}else if(keyFlg.equals("S"))
		{
			ss = commonMethedMapper.getSellMaxCode(company_cd,sysDate);
		}
		
		return ss;
		
	}
	public String getCompanyName(String companyID)
	{
		return commonMethedMapper.getCompanyName(companyID);
	}
	public String getMaxCode(String keyFlg,String jobCD)
	{
		if(keyFlg.equals("S") || keyFlg.equals("I") || keyFlg.equals("B") )
		{
			return keyFlg+jobCD.substring(1);
		}
		return "";
	}
	
	public List<Cost> getSumcost(int company_cd,String job_cd){
		List<Cost> list=commonMethedMapper.getSumcost(company_cd,job_cd);		
		return list;
	}
	
	public List<Cost> selectCLDIV(int company_cd,String job_cd){
		List<Cost> list=commonMethedMapper.selectCLDIV(company_cd,job_cd);		
		return list;
	}
	//查询动态列
	public  List<List<Columns>> selColumns(int company_cd,int level,int usercd,int ison){
		 List<List<Columns>> colList = new ArrayList<List<Columns>>();
		 colList.add(commonMethedMapper.selColumns(company_cd,level,usercd,ison,"COLUMN_SHOW_NAME"));
		 colList.add(commonMethedMapper.selColumns(company_cd,level,usercd,ison,"COLUMN_SHOW_NAME_EN"));
		 colList.add(commonMethedMapper.selColumns(company_cd,level,usercd,ison,"COLUMN_SHOW_NAME_JP"));
		 colList.add(commonMethedMapper.selColumns(company_cd,level,usercd,ison,"COLUMN_SHOW_NAME_HK"));
		return colList;
	}
	public String getSystemDate(int companyID)
	{
		return commonMethedMapper.getSystemDate(companyID);
	}
	public String getPayeetaxRate(String num,String companyID)
	{
		return commonMethedMapper.getPayeetaxRate(num,companyID);
	}
	public String getTimeByZone(String datetime,String companyID)
	{
		String timeZone = commonMethedMapper.getTimeZoneByCompany(companyID);
		if(datetime == null || datetime.equals(""))
		{
			return "";
		}
		if(timeZone == null || timeZone.equals(""))
		{
			return "";
		}
		return DateUtil.getNewTime(datetime, Integer.valueOf(timeZone));
	}
	/*
	public String getTimeByZone(String datetime,String companyID,String formatStr)
	{
		String timeZone = commonMethedMapper.getTimeZoneByCompany(companyID);
		if(datetime == null || datetime.equals(""))
		{
			return "";
		}
		if(timeZone == null || timeZone.equals(""))
		{
			return "";
		}
		return DateUtil.getNewTime(datetime, Integer.valueOf(timeZone),formatStr);
	}
	*/
	public SalemstVo getRateByDateAndSaleID(JobZhInput inputParam) {
		// TODO Auto-generated method stub
		return commonMethedMapper.getRateByDateAndSaleID(inputParam.getDalday(), inputParam.getSalecd(), Integer.valueOf(inputParam.getCompanyID()));
	}
	public boolean validatePower(String jobcd,String usercd,int companycd,String cldivcd,String interflg) {
		// TODO Auto-generated method stub
		List<Role> role =commonMethedMapper.searchNodeListByUser(usercd, String.valueOf(companycd));
		List<String> cldivList = null;
		List<String> reqList = null;
		List<String> mdList = null;
		
		if(!StringUtil.isBlank(jobcd)) {
			if(cldivcd==null) {
				cldivcd = commonMethedMapper.selJobcldiv(jobcd, String.valueOf(companycd));	
			}
			cldivList = commonMethedMapper.selUsercldiv(usercd, String.valueOf(companycd));
		    reqList  = commonMethedMapper.selJobuser("2",jobcd, String.valueOf(companycd));
		    mdList  = commonMethedMapper.selJobuser("3",jobcd, String.valueOf(companycd));
		}
		
		boolean flg = false;
	     switch(interflg) {
	     case "jobUpdateMD":
             if(isHavePower(role,13)) {
                     flg = true;
             }
             break;
	     case "jobLogin":
	    	 if(isHavePower(role,3)) {
	    		 flg = true;
	    	 }
	    	 if(isHavePower(role,4)) {
	    		 flg = true;
	    	 }
	    	 break;
	     case "jobUpdate":
	    	 if(isHavePower(role,9)) {
    			 flg = true;
	    	 }
	    	 if(isHavePower(role,10)) {
	    		 if(isIn(cldivList,cldivcd)) {
	    			 flg = true;
	    		 }
	    	 }
	    	 if(isHavePower(role,11)) {
	    		 if(isIn(reqList,usercd)) {
	    			 flg = true;
	    		 }
	    	 }
	    	 if(isHavePower(role,12)) {
	    		 if(isIn(mdList,usercd)) {
	    			 flg = true;
	    		 }
	    	 }
	    	 break;
	     case "saleLogin":
	    	 if(isHavePower(role,18)) {
    			 flg = true;
	    	 }
	    	 if(isHavePower(role,19)) {
	    		 if(isIn(cldivList,cldivcd)) {
	    			 flg = true;
	    		 }
	    	 }
	    	 if(isHavePower(role,20)) {
	    		 if(isIn(reqList,usercd)) {
	    			 flg = true;
	    		 }
	    	 }
	    	 if(isHavePower(role,21)) {
	    		 if(isIn(mdList,usercd)) {
	    			 flg = true;
	    		 }
	    	 }
	    	 break;
	     case "saleUpdate":
	    	 if(isHavePower(role,18)) {
    			 flg = true;
	    	 }
	    	 if(isHavePower(role,19)) {
	    		 if(isIn(cldivList,cldivcd)) {
	    			 flg = true;
	    		 }
	    	 }
	    	 if(isHavePower(role,20)) {
	    		 if(isIn(reqList,usercd)) {
	    			 flg = true;
	    		 }
	    	 }
	    	 if(isHavePower(role,21)) {
	    		 if(isIn(mdList,usercd)) {
	    			 flg = true;
	    		 }
	    	 }
	    	 break;
	     case "saleAdmin":
	    	 if(isHavePower(role,23)) {
    			 flg = true;
	    	 }
	    	 if(isHavePower(role,24)) {
	    		 if(isIn(cldivList,cldivcd)) {
	    			 flg = true;
	    		 }
	    	 }
	    	 if(isHavePower(role,25)) {
	    		 if(isIn(reqList,usercd)) {
	    			 flg = true;
	    		 }
	    	 }
	    	 if(isHavePower(role,26)) {
	    		 if(isIn(mdList,usercd)) {
	    			 flg = true;
	    		 }
	    	 }
	    	 break;
	     case "saleAdminCancel":
	    	 if(isHavePower(role,23)) {
    			 flg = true;
    	     }
	    	 if(isHavePower(role,24)) {
	    		 if(isIn(cldivList,cldivcd)) {
	    			 flg = true;
	    		 }
	    	 }
	    	 if(isHavePower(role,25)) {
	    		 if(isIn(reqList,usercd)) {
	    			 flg = true;
	    		 }
	    	 }
	    	 if(isHavePower(role,26)) {
	    		 if(isIn(mdList,usercd)) {
	    			 flg = true;
	    		 }
	    	 }
	    	 break;
	     case "insertAndUpdCost":
	    	 if(isHavePower(role,44)) {
	    			 flg = true;
	    	 }
	    	 if(isHavePower(role,45)) {
	    		 if(isIn(cldivList,cldivcd)) {
	    			 flg = true;
	    		 }
	    	 }
	    	 if(isHavePower(role,46)) {
	    		 if(isIn(reqList,usercd)) {
	    			 flg = true;
	    		 }
	    	 }
	    	 if(isHavePower(role,47)) {
	    		 if(isIn(mdList,usercd)) {
	    			 flg = true;
	    		 }
	    	 }
	    	 break;
	     case "insertAndUpdPay":
	    	 if(isHavePower(role,49)) {
	    			 flg = true;
	    	 }
	    	 if(isHavePower(role,50)) {
	    		 if(isIn(cldivList,cldivcd)) {
	    			 flg = true;
	    		 }
	    	 }
	    	 if(isHavePower(role,51)) {
	    		 if(isIn(reqList,usercd)) {
	    			 flg = true;
	    		 }
	    	 }
	    	 if(isHavePower(role,52)) {
	    		 if(isIn(mdList,usercd)) {
	    			 flg = true;
	    		 }
	    	 }
	    	 break;
	     case "joblist":
	    	 if(isHavePower(role,5)) {
	    			 flg = true;
	    	 }
	    	 if(isHavePower(role,6)) {
	    			 flg = true;
	    	 }
	    	 if(isHavePower(role,7)) {
	    			 flg = true;
	    	 }
	    	 if(isHavePower(role,8)) {
	    			 flg = true;
	    	 }
	    	 break;
	     }
		return flg;
	}
	public boolean isIn(List<String> list,String str) {
		boolean flg = false;
		for(int i = 0;i<list.size();i++) {
			if(str.equals(list.get(i))) {
				flg = true;
				break;
			}
		}
		return flg;
	}
	public boolean isHavePower(List<Role> roleList,int roleID) {
		boolean flg = false;
		for(Role role : roleList) {
			if(roleID==role.getNodeID()) {
				flg = true;
				break;
			}
		}
		return flg;
	}
	public String changSqlInput(String oldword) {
		if(oldword!=null&&!"".equals(oldword)) {
			String changekeyword=oldword.replace("_", "\\_");
			String finalkeyword = changekeyword.replace("%", "\\%");
			return finalkeyword;
		}else {
			return null;
		}
	}
	public Calculate calcuateJobTax(String jobNo,int companyID)
	{
		Calculate calculate = commonMethedMapper.getCalculateTaxInfo(jobNo,companyID);
		List<Cost> list=commonMethedMapper.getSumcost(companyID,jobNo);
		int pointNumber = commonMethedMapper.getPointNumberByCompany(companyID);
		List<CommonmstVo> foreignCodeVO = jobLandMapper.selectSaleCode(companyID,"001","0051");
		List<CommonmstVo> taxCodeVO = jobLandMapper.selectSaleCode(companyID,"003","0052");
	/*	JobLand joblist = jobLandMapper.selectJobByCd(jobNo, companyID);
		if(joblist.getAccountFlg()!=0) {
			List<JobList> jobs = jobLandMapper.selectjobhistrn(jobNo, companyID);
			calculate.setRate(Double.parseDouble(jobs.get(0).getHistoryrate2()));
			calculate.setRate1(Double.parseDouble(jobs.get(0).getHistoryrate3()));
		}*/
		calculate.setCountCost(Integer.valueOf(list.get(0).getCostnum()));
		
		//外货端数flg，0051 001
		int foreignFormatFlg = Integer.valueOf(foreignCodeVO.get(0).getAidcd());
		//taxFormatFlg	0052 003
		int taxFormatFlg = Integer.valueOf(taxCodeVO.get(0).getAidcd());
		
		Double rateWH = calculate.getRate();
		Double rateZZF = calculate.getRate1();
		Double saleAmt = calculate.getSaleAmt();
		Double saleVatAmt = calculate.getSaleVatAmt();
		Double reqAmt = calculate.getReqAmt();
		Double costAmt = 0.0;
		Double costVatAmt = 0.0;
		Double payAmt = 0.0;
		Double taxTotal = 0.0;
		Double taxWH = 0.0;
		Double taxZZF = 0.0;
		Double profit = 0.0;
		Double profitRate = 0.0;
		if(saleAmt == null)
		{
			saleAmt = calculate.getPlanSaleAmt();
			saleVatAmt = calculate.getPlanSaleVatAmt();
			reqAmt = calculate.getPlanReqAmt();
		}

		if(calculate.getCountCost() > 0)
		{
			calculate.setCostVatAmt(Double.valueOf(list.get(0).getSumvat()));
			calculate.setCostAmt(Double.valueOf(list.get(0).getSumamt()));
			calculate.setPayAmt(Double.valueOf(list.get(0).getPaysum()));
			
			costAmt = calculate.getCostAmt();
			costVatAmt = calculate.getCostVatAmt();
			payAmt = calculate.getPayAmt();
		}else {
			if(calculate.getIsCostFinsh() < 1)
			{
				costAmt = calculate.getPlanCostAmt();
				costVatAmt = calculate.getPlanCostVatAmt();
				payAmt = calculate.getPlanPayAmt();
			}
		}
		
		
		//文化建设税 = （请求金额-支付金额）*税率1
		taxWH = MathController.mul(MathController.sub(reqAmt,payAmt),rateWH);
		taxWH = pointFormatHandler(taxWH,taxFormatFlg,pointNumber);
		calculate.setTaxWH(taxWH);
		//增值附加税 = （卖上增值税-支付增值税）*税率2
		taxZZF = MathController.mul(MathController.sub(saleVatAmt,costVatAmt),rateZZF);
		taxZZF = pointFormatHandler(taxZZF,taxFormatFlg,pointNumber);
		calculate.setTaxZZF(taxZZF);
		//税金合计 = 文化+增值税附加
		taxTotal = MathController.add(taxWH, taxZZF);
		taxTotal = pointFormatHandler(taxTotal,taxFormatFlg,pointNumber);
		calculate.setTaxTotal(taxTotal);
		//营收 = 卖上金额-（原价金额+税金合计）
		profit = MathController.sub(saleAmt, MathController.add(costAmt, taxTotal));
		profit = pointFormatHandler(profit,foreignFormatFlg,pointNumber);
		calculate.setProfit(profit);
		//营收率=营收/卖上金额 * 100
		if(saleAmt==0.0||saleAmt==null)
		{
			calculate.setProfitRate(Double.POSITIVE_INFINITY);
		}else {
			profitRate = MathController.div(profit,saleAmt);
			profitRate = pointFormatHandler(profitRate,3,4);
			profitRate = MathController.mul(profitRate,100);
			calculate.setProfitRate(profitRate);
		}
		return calculate;
	}
	public Double pointFormatHandler(Double amt,int calFlg,int pointNumber)
	{
		Double fAmt = amt;
		
		if(Double.isInfinite(Math.abs(fAmt)))
		{
			return fAmt;
		}
		
		int cr = (int) Math.pow(10,pointNumber);
		
		if(pointNumber == 0)
		{
			cr = 1;
		}
		int nv = 1;
		if(fAmt < 0)
		{
			nv = -1;
		}
		switch (calFlg){
			//且上
			case 1:
				fAmt =  MathController.div(Math.floor(MathController.mul(Math.abs(fAmt),100)), 100);
				fAmt = MathController.div(Math.ceil(MathController.mul(Math.abs(fAmt),cr)),cr);
				break;
				//切下
			case 2:
				fAmt = MathController.div(Math.floor(MathController.mul(Math.abs(fAmt),100)), 100);
				fAmt = MathController.div(Math.floor(MathController.mul(Math.abs(fAmt),cr)),cr);
				break;
				//45入
			case 3:
				//fAmt = MathController.div(Math.round(MathController.mul(fAmt,cr)), cr);
				fAmt = MathController.div(Math.round(MathController.mul(Math.abs(fAmt),cr)), cr);
				
				break;
		}
		fAmt = MathController.mul(fAmt,nv);
		return fAmt;
	}
	public String getNewNum(String num,int count) {
		if("".equals(num)||num==null) {
		    num ="0";	
		}
		BigDecimal a=new BigDecimal(num);
		//DecimalFormat df=new DecimalFormat(",###,##0");//没有小数
		String  decimal ="0";
		if(count==0) {
			decimal="";
		}else {
			for(int i=0;i<count-1;i++){
				decimal+=decimal;
			}
			decimal="."+decimal;
		}
		DecimalFormat df=new DecimalFormat(",###,##0"+decimal); //保留一位小数  
		df.format(a);
		System.out.println(df.format(a));
		return df.format(a);
	}
	public String profitRateFormat(String num) {
		if("".equals(num)||num==null) {
		    num ="0";	
		}
		int count = 2;
		BigDecimal a=new BigDecimal(num);
		//DecimalFormat df=new DecimalFormat(",###,##0");//没有小数
		String  decimal ="0";
		if(count==0) {
			decimal="";
		}else {
			for(int i=0;i<count-1;i++){
				decimal+=decimal;
			}
			decimal="."+decimal;
		}
		DecimalFormat df=new DecimalFormat("#####0"+decimal); //保留一位小数  
		df.format(a);
		System.out.println(df.format(a));
		return df.format(a);
	}
	public Double calcuateJobTax(String jobNo,int companyID,String beforDate,String startDate,String endDate,String outDate,int isOutMonthFlg)
	{
		Calculate calculate = null;
		String newOutDate = DateUtil.getAddNumMotnDate(outDate,0,DateUtil.dateFormatYeayMonth);
		
		if(isOutMonthFlg == 1)
		{
			calculate = commonMethedMapper.getOutMonthPayAmtAndCostVat(jobNo,companyID,newOutDate);
		}else {
			calculate = commonMethedMapper.getBeforMonthPayAmtAndCostVat(companyID,jobNo,beforDate,newOutDate);
		}
		
		if(calculate == null)
		{
			return .0;
		}
		int pointNumber = commonMethedMapper.getPointNumberByCompany(companyID);
		List<CommonmstVo> taxCodeVO = jobLandMapper.selectSaleCode(companyID,"003","0052");
		
		//taxFormatFlg	0052 003
		int taxFormatFlg = Integer.valueOf(taxCodeVO.get(0).getAidcd());
		
		Double rateWH = calculate.getRate();
		Double rateZZF = calculate.getRate1();
		Double costVatAmt = calculate.getCostVatAmt();
		Double payAmt = calculate.getPayAmt();
		Double beforMonthCostVatAmt = calculate.getPlanCostVatAmt();
		Double beforMonthPayAmt = calculate.getPlanPayAmt();
		Double saleVatAmt = 0.0;
		Double reqAmt = 0.0;
		Double taxTotal = 0.0;
		Double beforMonthTaxTotal = 0.0;
		Double taxWH = 0.0;
		Double taxZZF = 0.0;
		Double diffTax = 0.0;
		if(isOutMonthFlg == 1)
		{
			reqAmt = calculate.getReqAmt();
			saleVatAmt = calculate.getSaleVatAmt();
		}
		//文化建设税 = （请求金额-支付金额）*税率1
		taxWH = MathController.mul(MathController.sub(reqAmt,payAmt),rateWH);
		taxWH = pointFormatHandler(taxWH,taxFormatFlg,pointNumber);
		//增值附加税 = （卖上增值税-支付增值税）*税率2
		taxZZF = MathController.mul(MathController.sub(saleVatAmt,costVatAmt),rateZZF);
		taxZZF = pointFormatHandler(taxZZF,taxFormatFlg,pointNumber);
		//税金合计 = 文化+增值税附加
		taxTotal = MathController.add(taxWH, taxZZF);
		taxTotal = pointFormatHandler(taxTotal,taxFormatFlg,pointNumber);
		
		if(isOutMonthFlg == 0)
		{
			//文化建设税 = （请求金额-支付金额）*税率1
			taxWH = MathController.mul(MathController.sub(reqAmt,beforMonthPayAmt),rateWH);
			taxWH = pointFormatHandler(taxWH,taxFormatFlg,pointNumber);
			//增值附加税 = （卖上增值税-支付增值税）*税率2
			taxZZF = MathController.mul(MathController.sub(saleVatAmt,beforMonthCostVatAmt),rateZZF);
			taxZZF = pointFormatHandler(taxZZF,taxFormatFlg,pointNumber);
			//税金合计 = 文化+增值税附加
			beforMonthTaxTotal = MathController.add(taxWH, taxZZF);
			beforMonthTaxTotal = pointFormatHandler(beforMonthTaxTotal,taxFormatFlg,pointNumber);
		}
		
		
		diffTax = MathController.sub(taxTotal, beforMonthTaxTotal);
		
		return diffTax;
	}
	public List<AccountEntries> calcuateAccountEntriesJobTax(List<AccountEntries> accountList,String thisMonth,String beforMonth)
	{
		for(int i = 0;i < accountList.size();i++)
		{
			if(accountList.get(i).getIsThisMonthEndFlg() == 0)
			{
				//对象JOB在当前月的税金
				Calculate calculate = this.calcuateJobTax(accountList.get(i).getJobNo(),Integer.valueOf(accountList.get(i).getCompanyID()));
				
				if(accountList.get(i).getDebtorCD().equals("005"))
				{
					accountList.get(i).setDebtorAmt(calculate.getTaxWH());
					accountList.get(i).setCreditAmt(calculate.getTaxWH());
				}else {
					accountList.get(i).setDebtorAmt(calculate.getTaxZZF());
					accountList.get(i).setCreditAmt(calculate.getTaxZZF());
				}
			}
			
			//如果是前月以前売上計上JOB,计算差额
			if(accountList.get(i).getIsThisMonthEndFlg() == 1)
			{
				String thisMonthYM = DateUtil.getAddNumMotnDate(thisMonth,0,DateUtil.dateFormatYeayMonth);
				AccountEntries accountEntries = accountList.get(i);
				AccountEntries accountEntriesbefor = new AccountEntries();
				AccountEntries accountEntriesNow = new AccountEntries();
				accountEntriesbefor = this.calcuateBeforTaxByAccountEntries(accountList.get(i),beforMonth,thisMonthYM,0);
				accountEntriesNow = this.calcuateBeforTaxByAccountEntries(accountList.get(i),beforMonth,thisMonthYM,1);
				double diffAmt = .0;
				diffAmt = MathController.sub(accountEntriesNow.getDebtorAmt(),accountEntriesbefor.getDebtorAmt());
				
				accountEntries.setDebtorAmt(diffAmt);
				accountEntries.setCreditAmt(diffAmt);
				
				int EPSINON = (int) Math.ceil(Math.abs(MathController.mul(accountEntries.getDebtorAmt(),100)));
				
				if( EPSINON > 0 )
				{
					accountList.set(i, accountEntries);
				}else {
					accountList.remove(i);
					i--;
				}
			}
		}
		
		return accountList;
	}
	private AccountEntries calcuateBeforTaxByAccountEntries(AccountEntries accountEntries,String beforMonth,String outMonthDate,int flg)
	{
		try {
			AccountEntries accountBean = accountEntries.clone();
			String jobNo = accountBean.getJobNo();
			int companyID = Integer.valueOf(accountBean.getCompanyID());
			Calculate calculate = new Calculate();
			Calculate calculate1 = new Calculate();
			if(flg == 0)
			{
				calculate = commonMethedMapper.getOutMonthPayAmtAndCostVat(jobNo,companyID,beforMonth);
				//edit by wangyan 20200702 获取前月成本时，没有加算已经承认的成本
				//calculate1 = commonMethedMapper.getBeforMonthPayAmtAndCostVat(companyID,jobNo,beforMonth,outMonthDate);
				calculate1 = commonMethedMapper.getBeforMonthAllPayAmtAndAllCostVat(companyID,jobNo,beforMonth,outMonthDate);
				calculate.setPayAmt(calculate1.getPlanPayAmt());
				calculate.setCostVatAmt(calculate1.getPlanCostVatAmt());
			}else {
				calculate = commonMethedMapper.getOutMonthPayAmtAndCostVat(jobNo,companyID,outMonthDate);
			}
			
			
			//List<Cost> list=commonMethedMapper.getSumcost(companyID,jobNo);
			int pointNumber = commonMethedMapper.getPointNumberByCompany(companyID);
			List<CommonmstVo> taxCodeVO = jobLandMapper.selectSaleCode(companyID,"003","0052");
			
			//taxFormatFlg	0052 003
			int taxFormatFlg = Integer.valueOf(taxCodeVO.get(0).getAidcd());
			
			Double rateWH = calculate.getRate();
			Double rateZZF = calculate.getRate1();
			Double costVatAmt = calculate.getCostVatAmt();
			Double payAmt = calculate.getPayAmt();
			Double saleVatAmt = calculate.getSaleVatAmt();
			Double reqAmt = calculate.getReqAmt();
			Double taxWH = 0.0;
			Double taxZZF = 0.0;
			
			//文化建设税 = （请求金额-支付金额）*税率1
			taxWH = MathController.mul(MathController.sub(reqAmt,payAmt),rateWH);
			taxWH = pointFormatHandler(taxWH,taxFormatFlg,pointNumber);
			//taxWH = MathController.sub(accountEntries.getDebtorAmt(),taxWH);
			
			//增值附加税 = （卖上增值税-支付增值税）*税率2
			taxZZF = MathController.mul(MathController.sub(saleVatAmt,costVatAmt),rateZZF);
			taxZZF = pointFormatHandler(taxZZF,taxFormatFlg,pointNumber);
			//taxZZF = MathController.sub(accountEntries.getDebtorAmt(),taxZZF);
			
			if(accountBean.getDebtorCD().equals("005"))
			{
				accountBean.setDebtorAmt(taxWH);
				accountBean.setCreditAmt(taxWH);
			}else {
				accountBean.setDebtorAmt(taxZZF);
				accountBean.setCreditAmt(taxZZF);
			}
			return accountBean;
		} catch (CloneNotSupportedException e) {
			return null;
		}
		
	}
	public Calculate calcuateJobTaxByJobExcel(String jobNo,int companyID)
	{
		Calculate calculate = commonMethedMapper.getCalculateTaxInfo(jobNo,companyID);
		List<Cost> list=commonMethedMapper.getSumcost(companyID,jobNo);
		int pointNumber = commonMethedMapper.getPointNumberByCompany(companyID);
		List<CommonmstVo> foreignCodeVO = jobLandMapper.selectSaleCode(companyID,"001","0051");
		List<CommonmstVo> taxCodeVO = jobLandMapper.selectSaleCode(companyID,"003","0052");
	/*	JobLand joblist = jobLandMapper.selectJobByCd(jobNo, companyID);
		if(joblist.getAccountFlg()!=0) {
			List<JobList> jobs = jobLandMapper.selectjobhistrn(jobNo, companyID);
			calculate.setRate(Double.parseDouble(jobs.get(0).getHistoryrate2()));
			calculate.setRate1(Double.parseDouble(jobs.get(0).getHistoryrate3()));
		}*/
		calculate.setCountCost(Integer.valueOf(list.get(0).getCostnum()));
		
		//外货端数flg，0051 001
		int foreignFormatFlg = Integer.valueOf(foreignCodeVO.get(0).getAidcd());
		//taxFormatFlg	0052 003
		int taxFormatFlg = Integer.valueOf(taxCodeVO.get(0).getAidcd());
		
		Double rateWH = calculate.getRate();
		Double rateZZF = calculate.getRate1();
		Double saleAmt = calculate.getSaleAmt();
		Double saleVatAmt = calculate.getSaleVatAmt();
		Double reqAmt = calculate.getReqAmt();
		Double costAmt = 0.0;
		Double costVatAmt = 0.0;
		Double payAmt = 0.0;
		Double taxTotal = 0.0;
		Double taxWH = 0.0;
		Double taxZZF = 0.0;
		Double profit = 0.0;
		Double profitRate = 0.0;
		if(saleAmt == null)
		{
			saleAmt = calculate.getPlanSaleAmt();
			saleVatAmt = calculate.getPlanSaleVatAmt();
			reqAmt = calculate.getPlanReqAmt();
		}

		if(calculate.getCountCost() > 0)
		{
			calculate.setCostVatAmt(Double.valueOf(list.get(0).getSumvat()));
			calculate.setCostAmt(Double.valueOf(list.get(0).getSumamt()));
			calculate.setPayAmt(Double.valueOf(list.get(0).getPaysum()));
			
			costAmt = calculate.getCostAmt();
			costVatAmt = calculate.getCostVatAmt();
			payAmt = calculate.getPayAmt();
		}
		
		
		//文化建设税 = （请求金额-支付金额）*税率1
		taxWH = MathController.mul(MathController.sub(reqAmt,payAmt),rateWH);
		taxWH = pointFormatHandler(taxWH,taxFormatFlg,pointNumber);
		calculate.setTaxWH(taxWH);
		//增值附加税 = （卖上增值税-支付增值税）*税率2
		taxZZF = MathController.mul(MathController.sub(saleVatAmt,costVatAmt),rateZZF);
		taxZZF = pointFormatHandler(taxZZF,taxFormatFlg,pointNumber);
		calculate.setTaxZZF(taxZZF);
		//税金合计 = 文化+增值税附加
		taxTotal = MathController.add(taxWH, taxZZF);
		taxTotal = pointFormatHandler(taxTotal,taxFormatFlg,pointNumber);
		calculate.setTaxTotal(taxTotal);
		//营收 = 卖上金额-（原价金额+税金合计）
		profit = MathController.sub(saleAmt, MathController.add(costAmt, taxTotal));
		profit = pointFormatHandler(profit,foreignFormatFlg,pointNumber);
		calculate.setProfit(profit);
		//营收率=营收/卖上金额 * 100
		if(saleAmt==0.0||saleAmt==null)
		{
			calculate.setProfitRate(Double.POSITIVE_INFINITY);
		}else {
			profitRate = MathController.div(profit,saleAmt);
			profitRate = pointFormatHandler(profitRate,3,4);
			profitRate = MathController.mul(profitRate,100);
			calculate.setProfitRate(profitRate);
		}
		return calculate;
	}
}
