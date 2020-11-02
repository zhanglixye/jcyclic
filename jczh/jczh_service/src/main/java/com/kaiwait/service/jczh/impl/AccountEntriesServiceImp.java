package com.kaiwait.service.jczh.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kaiwait.bean.jczh.entity.AccountEntries;
import com.kaiwait.core.utils.DateUtil;
import com.kaiwait.mappers.jczh.AccountEntriesMapper;
import com.kaiwait.service.jczh.AccountEntriesService;
import com.kaiwait.service.jczh.CommonMethedService;


@Service
public class AccountEntriesServiceImp implements AccountEntriesService{

	@Resource
	private AccountEntriesMapper accountEntriesMapper;
	@Resource
	private CommonMethedService commonMethedService;
	
	//结账时，插入所有会计分录数据
	public void crateAccountDataByMonthCheckoutTx(String monthDate, int companyID, int userId,String thisMonth) 
	{
		List<AccountEntries> accountList = accountEntriesMapper.getJobsForAccountEntries(thisMonth, companyID);
		for(int i = 0;i < accountList.size();i++)
		{
			this.createSaleAccountsData(String.valueOf(companyID), accountList.get(i).getJobNo(), String.valueOf(userId), "monthBalance");
		}
		this.createPayAccountDataByMonthCheckout(monthDate, companyID, userId,thisMonth);
		this.createLendAccountDataByMonthCheckout(monthDate, companyID, userId,thisMonth);
		this.createTranAccountDataByMonthCheckout(monthDate, companyID, userId,thisMonth);
		this.createTaxAccountDataByMonthCheckout(monthDate, companyID, userId,thisMonth);
		this.createBeforSaleAccountDataByMonthCheckout(monthDate, companyID, userId,thisMonth);
	}
	//插入数据
	private void insertAccountDates(List<AccountEntries> accountList)
	{
		for(int i = 0;i < accountList.size();i++)
		{
				accountEntriesMapper.createAccountEntriesData(accountList.get(i));
		}
	}
	//创建卖上分录数据
	public void createSaleAccountsData(String companyID,String jobNo,String userId,String actionName)
	{
		//当前服务器时间
		String nowDate = DateUtil.getDateForNow(DateUtil.dateTimeFormat);
		//获取基础的卖上信息
		AccountEntries accountBean = new AccountEntries();
		accountBean = accountEntriesMapper.getInfomation(companyID, jobNo,userId);
		accountBean.setAddDate(nowDate);
		
		List<AccountEntries> accountList = new ArrayList<AccountEntries>();
		//不是结账并且发票发行必填
		if(!actionName.equals("monthBalance") && accountBean.getInvFlg() == 1)
		{
			//组装数据源，包括，001  017 001  007
			accountList = this.reloadSaleAccountEntriesData(accountBean);
		}
		//月结
		if(actionName.equals("monthBalance"))
		{
			//结账时，判断是否在之前有插入锅001  017 001  007会计分录数据
			int isAccountData = accountEntriesMapper.isAccountDataWithJobSale(companyID, jobNo);
			if(isAccountData < 1 && accountBean.getInvFlg() == 0)
			{
				//组装数据源，包括，001  017 001  007
				accountList = this.reloadSaleAccountEntriesData(accountBean);
			}
			if(accountBean.getIsHaveBeforInvoice() == 0)
			{
				//017  002
				accountBean.setDebtorCD("017");
				accountBean.setDebtorAmt(accountBean.getSalveAmt());
				accountBean.setCreditCD("002");
				accountBean.setCreditAmt(accountBean.getSalveAmt());
				accountList.add(accountBean);
			}
		}
		this.insertAccountDates(accountList);
	}
	//创建支付分录数据
	public void createPayAccountsData(String jobNo,String costNo,String inputNo,String companyID,String userId,String monthDate)
	{
		AccountEntries accountBean = accountEntriesMapper.getPayAccountInfomation(jobNo,costNo,inputNo,companyID,userId);
		if(accountBean!=null) {
			accountBean.setFiscalMonth(monthDate);
			accountBean.setAddDate(DateUtil.getNowTime());
			this.insertAccountDates(this.reloadPayAccountEntriesData(accountBean));
		}
		
	}
	//创建立替分录数据
	public void createLendAccountsData(String jobNo,String inputNo,String companyID,String userId,String monthDate)
	{
		AccountEntries accountBean = accountEntriesMapper.getLendAccountInfomation(jobNo,inputNo,companyID,userId);
		if(accountBean!=null) {
		accountBean.setFiscalMonth(monthDate);
		accountBean.setAddDate(DateUtil.getNowTime());
		this.insertAccountDates(this.reloadLendAccountEntriesData(accountBean));
		}
	}
	//创建真替分录数据
	public void createTranAccountsData(String jobNo,String inputNo,String companyID,String userId,String monthDate)
	{
		AccountEntries accountBean = accountEntriesMapper.getTranAccountInfomation(jobNo,inputNo,companyID,userId);
		if(accountBean!=null) {
		accountBean.setFiscalMonth(monthDate);
		accountBean.setAddDate(DateUtil.getNowTime());
		accountEntriesMapper.createAccountEntriesData(accountBean);
		}
	}
	//重新组装立替分录数据
	public List<AccountEntries> reloadLendAccountEntriesData(AccountEntries accountBean)
	{
		List<AccountEntries> accountList = new ArrayList<AccountEntries>();
		try {
			//009  011
			AccountEntries accountBean1 = accountBean.clone();
			accountBean1.setDebtorCD("009");
			accountBean1.setDebtorAmt(accountBean1.getSalveAmt());
			accountBean1.setCreditCD("013");
			accountBean1.setCreditAmt(accountBean1.getSalveAmt());
			//012  011
			AccountEntries accountBean2 = accountBean.clone();
			accountBean2.setDebtorCD("012");
			accountBean2.setDebtorAmt(accountBean2.getVatAmt());
			accountBean2.setCreditCD("013");
			accountBean2.setCreditAmt(accountBean2.getVatAmt());
			
			accountList.add(accountBean1);
			accountList.add(accountBean2);
		} catch (CloneNotSupportedException e) {
			e.getMessage();
			return null;
		}
		return accountList;
	}
	//重新组装支付分录数据
	public List<AccountEntries> reloadPayAccountEntriesData(AccountEntries accountBean)
	{
		List<AccountEntries> accountList = new ArrayList<AccountEntries>();
		try {
			//009  011
			AccountEntries accountBean1 = accountBean.clone();
			accountBean1.setDebtorCD("009");
			accountBean1.setDebtorAmt(accountBean1.getSalveAmt());
			accountBean1.setCreditCD("011");
			accountBean1.setCreditAmt(accountBean1.getSalveAmt());
			//012  011
			AccountEntries accountBean2 = accountBean.clone();
			accountBean2.setDebtorCD("012");
			accountBean2.setDebtorAmt(accountBean2.getVatAmt());
			accountBean2.setCreditCD("011");
			accountBean2.setCreditAmt(accountBean2.getVatAmt());
			
			accountList.add(accountBean1);
			accountList.add(accountBean2);
		} catch (CloneNotSupportedException e) {
			e.getMessage();
			return null;
		}
		return accountList;
	}
	//重新组装卖上分录数据
	public List<AccountEntries> reloadSaleAccountEntriesData(AccountEntries accountBean)
	{
		List<AccountEntries> accountList = new ArrayList<AccountEntries>();
		try {
			//001  017
			AccountEntries accountBean1 = accountBean.clone();
			accountBean1.setDebtorCD("001");
			accountBean1.setDebtorAmt(accountBean1.getSalveAmt());
			accountBean1.setCreditCD("017");
			accountBean1.setCreditAmt(accountBean1.getSalveAmt());
			//001  007
			AccountEntries accountBean2 = accountBean.clone();
			accountBean2.setDebtorCD("001");
			accountBean2.setDebtorAmt(accountBean2.getSaleVatAmt());
			accountBean2.setCreditCD("007");
			accountBean2.setCreditAmt(accountBean2.getSaleVatAmt());
			
			accountList.add(accountBean1);
			accountList.add(accountBean2);
		} catch (CloneNotSupportedException e) {
			e.getMessage();
			return null;
		}
		return accountList;
	}
	//根据条件删除分录数据
	public void dropAccountDatasByCancel(String jobNo, String inputNo, String companyID) 
	{
		accountEntriesMapper.dropAccountDatasByCancel(jobNo, inputNo, companyID);
	}
	//结账时，创建支付会计分录数据
	private void createPayAccountDataByMonthCheckout(String monthDate, int companyID, int userId,String thisMonth)
	{
		String beforMonth = DateUtil.makeDate(thisMonth);
		List<AccountEntries> accountList = accountEntriesMapper.getPayAccountDataInfomationByMonthCheckout(monthDate, companyID, userId,thisMonth,DateUtil.getNowTime(),beforMonth);
		this.insertAccountDates(accountList);
	}
	//结账时，创建立替会计分录数据
	private void createLendAccountDataByMonthCheckout(String monthDate, int companyID, int userId,String thisMonth)
	{
		String beforMonth = DateUtil.makeDate(thisMonth);
		List<AccountEntries> accountList = accountEntriesMapper.getLendAccountDataInfomationByMonthCheckout(monthDate, companyID, userId,thisMonth,DateUtil.getNowTime(),beforMonth);
		this.insertAccountDates(accountList);
	}
	//结账时，创建振替会计分录数据
	private void createTranAccountDataByMonthCheckout(String monthDate, int companyID, int userId,String thisMonth)
	{
		String beforMonth = DateUtil.makeDate(thisMonth);
		List<AccountEntries> accountList = accountEntriesMapper.getTranAccountDataInfomationByMonthCheckout(monthDate, companyID, userId,thisMonth,DateUtil.getNowTime(),beforMonth);
		this.insertAccountDates(accountList);
	}
	private void createTaxAccountDataByMonthCheckout(String monthDate, int companyID, int userId,String thisMonth)
	{
		String beforMonth = DateUtil.makeDate(thisMonth);
		List<AccountEntries> accountList = accountEntriesMapper.getTaxAccountDataInfomationByMonthCheckout(monthDate, companyID, userId,thisMonth,DateUtil.getNowTime(),beforMonth);
		accountList = commonMethedService.calcuateAccountEntriesJobTax(accountList,thisMonth,beforMonth);
		this.insertAccountDates(accountList);
	}
	//结账时，创建前受金会计分录数据
	private void createBeforSaleAccountDataByMonthCheckout(String monthDate, int companyID, int userId,String thisMonth)
	{
		//当前服务器时间
		String nowDate = DateUtil.getDateForNow(DateUtil.dateTimeFormat);
		List<AccountEntries> accountList = 
				accountEntriesMapper.getBeforSaleAccountDataInfomationByMonthCheckout( monthDate,  companyID,  userId, thisMonth, nowDate);
		if( accountList.size() > 0 )
		{
			this.insertAccountDates(accountList);
		}
	}
}
