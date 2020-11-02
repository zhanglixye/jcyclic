package com.kaiwait.service.jczh.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kaiwait.bean.jczh.entity.AccountMonth;
import com.kaiwait.bean.jczh.entity.Job;
import com.kaiwait.bean.jczh.entity.OrderHistory;
import com.kaiwait.bean.jczh.entity.Skip;
import com.kaiwait.core.utils.DateUtil;
import com.kaiwait.core.utils.MathController;
import com.kaiwait.mappers.jczh.CommonMethedMapper;
import com.kaiwait.mappers.jczh.MonthBalanceMapper;
import com.kaiwait.service.jczh.MonthBalanceService;
@Service
public class MonthBalanceImpl implements MonthBalanceService{

	@Resource
	private MonthBalanceMapper monthBalanceMapper;
	@Resource
	private CommonMethedMapper commonMethedMapper;
	
	public AccountMonth getAccountBasicInfo(int companyID) {
		AccountMonth accountMonth = new AccountMonth();
		String sysDate = commonMethedMapper.getSystemDate(companyID);
		accountMonth = monthBalanceMapper.getAccountInfo(companyID, sysDate);
		
		int sellCountNums = (int)MathController.add(MathController.add(accountMonth.getNotSellRegistrationNums(), accountMonth.getSellRegistrationNotConfirmNums()),accountMonth.getSellIsConfirmNums());
		/*
		List<Job> jobList = monthBalanceMapper.searchJobsByMonth(companyID,sysDate);
		for(int i = 0;i < jobList.size();i++)
		{
			if(jobList.get(i).getOrderType().equals("cost"))
			{
				accountMonth.setNotConfirmCostAmt(MathController.add(accountMonth.getNotConfirmCostAmt(),jobList.get(i).getPurposrAmt()));
			}else {
				accountMonth.setNotConfirmLendAmt(MathController.add(accountMonth.getNotConfirmLendAmt(),jobList.get(i).getPurposrAmt()));
			}
		}
		*/
		Double costAmTotal = MathController.add(accountMonth.getNotConfirmCostAmt(),accountMonth.getConfirmCostAmt());
		Double lendAmtTotal = MathController.add(accountMonth.getNotConfirmLendAmt(),accountMonth.getConfirmLendAmt());
		Double orderAmtTotal = MathController.add(MathController.add(costAmTotal,lendAmtTotal),accountMonth.getTranTotalAmt());
		accountMonth.setSellCountNums(sellCountNums);
		accountMonth.setOrderAmtTotal(orderAmtTotal);
		accountMonth.setCostAmTotal(costAmTotal);
		accountMonth.setLendAmtTotal(lendAmtTotal);
		Skip skip = commonMethedMapper.selectSkipByCd(companyID);
		accountMonth.setIsCostFinshFlg(skip.getCost());
		accountMonth.setIsSellconfirmFlg(skip.getConfirm());
		return accountMonth;
	}

	public int checkOutMonthTx(int companyID,int userID)
	{
		Skip skip = commonMethedMapper.selectSkipByCd(companyID);
		AccountMonth accountMonth = new AccountMonth();
		accountMonth = this.getAccountBasicInfo(companyID);
		
		if(accountMonth.getNotSellRegistrationNums() > 0 || (accountMonth.getCostNotFinshNums() > 0 && skip.getCost() == 0) || accountMonth.getNotConfirmTranNums() > 0 || 
				accountMonth.getPayReqNotConfirmNums() > 0 || accountMonth.getNotConfirmLendNums() > 0  || (accountMonth.getSellRegistrationNotConfirmNums() > 0 && skip.getConfirm() == 0))
		{
			return 0;
		}else {
			
			String dt = commonMethedMapper.getSystemDate(companyID);
			Date aMonth = DateUtil.stringtoDate(dt, DateUtil.dateFormat);
			String nextMonth = DateUtil.nextMotnDate(dt);
			
			String dateNow = DateUtil.getDateForNow(DateUtil.dateTimeFormat);
			Date accountDate = DateUtil.stringtoDate(dateNow, DateUtil.dateTimeFormat);
			
			if(skip.getCost() == 1)
			{
				List<Job> costNotFinshJobs = monthBalanceMapper.getCostNotFinshJobs(companyID,dt);
				for(int i = 0;i < costNotFinshJobs.size();i++)
				{
					monthBalanceMapper.upCostFinshFlgByJobs(companyID,dateNow,costNotFinshJobs.get(i));
				}
			}
			if(skip.getConfirm() == 1)
			{
				List<Job> sellNotConfirmJobs = monthBalanceMapper.getSellRegistrationNotConfirmJobs(companyID,dt);
				for(int i = 0;i < sellNotConfirmJobs.size();i++)
				{
					Job job = new Job();
					job = sellNotConfirmJobs.get(i);
					monthBalanceMapper.upSellNotConfirmJobs(companyID,dateNow,job);
					monthBalanceMapper.addJobSellRateByHistory(companyID, job.getJobNo(), job.getRate2(), job.getRate3(),dateNow,job.getUserIDByMonth());
				}
			}
			
			//更新所有JOB的Account状态
			monthBalanceMapper.updateAccountJobInfo(companyID,accountDate,userID,aMonth);
			//获取所有需要终了的JOB
			List<Job> jobList = monthBalanceMapper.getJobsByWillFinsh(companyID,aMonth);
			List<Job> jobNotAdmitList = monthBalanceMapper.getJobsByNotAdmit(companyID,aMonth);
			List<Job> jobNewArr = new ArrayList<Job>();
			Boolean flg = true;
			//更新所有需要终了的JOB的状态
			//卖上终了日小于等于当月
			for(int i = 0;i < jobList.size();i++)
			{
				flg = true;
				//卖上终了日小于等于当月，存在未批准的成本
				for(int j = 0;j < jobNotAdmitList.size();j++)
				{
					if(jobList.get(i).getJobNo().equals(jobNotAdmitList.get(j).getJobNo()))
					{
						flg = false;
						break;
					}
				}
				if(flg)
				{
					jobNewArr.add(jobList.get(i));
				}
			}
			if(jobNewArr != null && jobNewArr.size() > 0)
			{
				monthBalanceMapper.updateJobSetEnd(companyID,jobNewArr,accountDate);
				
			}
			if(jobList.size() > 0)
			{
				monthBalanceMapper.updateCostEnd(companyID,jobList);
				monthBalanceMapper.updateLendEnd(companyID,jobList);
				monthBalanceMapper.updateTranEnd(companyID,jobList);
			}
			
			//更新所有立替保留状态
			monthBalanceMapper.upLendReject(companyID,aMonth);
			//插入summary
			monthBalanceMapper.addSummary(companyID,userID,accountMonth,accountDate,aMonth);
			//更新会计年月
			monthBalanceMapper.updateAccountMonth(companyID, nextMonth, dateNow);
			
			List<Job> jobs = monthBalanceMapper.getNotCheckAmtByMonth(companyID,dt);
			if(jobs.size() > 0)
			{
				for(int i=0;i<jobs.size();i++)
				{
					monthBalanceMapper.delAfterPurpose(jobs.get(i).getJobNo(),companyID,jobs.get(i).getOrderNo());
				}
				monthBalanceMapper.addPurpose(jobs,companyID,userID,accountDate);
			}
			/**********结账时记录所有未承认的外发，代垫款。*****************/
			List<OrderHistory> orderHisList = monthBalanceMapper.getNotCheckCostByHistory(companyID,dt);
			
			for(int i = 0;i < orderHisList.size();i++)
			{
				monthBalanceMapper.addOrderByHistory(orderHisList.get(i),companyID,userID,accountDate);
			}
			/*for(int i = 0;i < jobList.size();i++)
			{
				if(jobList.get(i).getIsThisMonth() == 1)
				{
					monthBalanceMapper.addJobSellRateByHistory(companyID,jobList.get(i).getJobNo(),jobList.get(i).getRate2(),jobList.get(i).getRate3());
				}
			}*/
			/*************************************************/
			return 1;
		}
	}

}
