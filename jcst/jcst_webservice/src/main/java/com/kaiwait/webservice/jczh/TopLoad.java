package com.kaiwait.webservice.jczh;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kaiwait.bean.jczh.entity.JobList;
import com.kaiwait.bean.jczh.entity.Role;
import com.kaiwait.bean.jczh.io.JobListInput;
import com.kaiwait.core.process.SingleFunctionIF;
import com.kaiwait.core.process.ValidateResult;
import com.kaiwait.core.utils.MathController;
import com.kaiwait.mappers.jczh.CommonMethedMapper;
import com.kaiwait.mappers.jczh.CostListMapper;
import com.kaiwait.mappers.jczh.JobMapper;
import com.kaiwait.service.jczh.MonthBalanceService;
import com.kaiwait.service.jczh.TopService;

@Component("topLoad")
public class TopLoad implements SingleFunctionIF<JobListInput> {

	@Resource
	private MonthBalanceService monthBalanceService;
	@Resource
	private TopService topService;
	@Resource
	private CommonMethedMapper commonMethedMapper;
	@Resource
	private JobMapper jobMapper;
	@Resource
	private CostListMapper costListMapper;

	@Override
	public Object process(JobListInput inputParam) {
		int companycd = Integer.valueOf(inputParam.getCompanyID());
		Map<String, Object> map = topService.topLoad(companycd,
				inputParam.getUserID());
		// JobListInput jobInput = inputParam.getJobInput();
		// jobInput.setCompanyID(inputParam.getCompanyID());
		// jobInput.setUserID(inputParam.getUserID());
		inputParam.setDel_flg("0");
		List<Role> role = commonMethedMapper.searchNodeListByUser(inputParam.getUserID(), inputParam.getCompanyID());
		boolean flg = false;
		if (role.size() > 0) {
			for (int i = 0; i < role.size(); i++) {
				if (role.get(i).getNodeID() == 5) {
					inputParam.setAll("5");
					flg = true;
				}
				if (role.get(i).getNodeID() == 6) {
					inputParam.setDyqx("6");
					inputParam.setDdqxcd(inputParam.getUserID());
					flg = true;
				}
				if (role.get(i).getNodeID() == 7) {
					inputParam.setDdqx("7");
					inputParam.setDdqxcd(inputParam.getUserID());
					flg = true;
				}
				if (role.get(i).getNodeID() == 8) {
					inputParam.setGdqx("8");
					inputParam.setDdqxcd(inputParam.getUserID());
					flg = true;
				}
			}
		}
		if (flg) {
			String sysDate = commonMethedMapper.getSystemDate(companycd);
			
		    inputParam.setDlvday(sysDate.substring(0, 7));
		    
		    //卖上登录件数 计上待
			inputParam.setDlvfalg("004");
			//top 查询计上待
			inputParam.setTopFlg("1");
			List<JobList> jobList = jobMapper.searchJobList(inputParam);
			int isSellRegistrationNums = jobList.size();
			double isSellRegistrationAmt = 0.00;
			for (JobList job : jobList) {
				isSellRegistrationAmt = MathController.add(isSellRegistrationAmt, Double.parseDouble(job.getSaleamt()));
			}
			//卖上未登录 件数
			inputParam.setDlvfalg("002");
			List<JobList> jobListNo = jobMapper.searchJobList(inputParam);
			int notSellRegistrationNums = jobListNo.size();
			double notSellRegistrationAmt = 0.00;
			for (JobList job2 : jobListNo) {
				//notSellRegistrationAmt += Double.parseDouble(job2.getSaleamt());
				notSellRegistrationAmt = MathController.add(notSellRegistrationAmt, Double.parseDouble(job2.getSaleamt()));
			}
			inputParam.setCost_finish_flg("001");
			inputParam.setDlvfalg(null);
			int costNotFinshNums = jobMapper.searchJobList(inputParam).size();
			map.put("isSellRegistrationNums", isSellRegistrationNums);
			map.put("notSellRegistrationNums", notSellRegistrationNums);
			map.put("costNotFinshNums", costNotFinshNums);
			map.put("isSellRegistrationAmt", isSellRegistrationAmt);
			map.put("notSellRegistrationAmt", notSellRegistrationAmt);
		} else {
			map.put("isSellRegistrationNums", 0);
			map.put("notSellRegistrationNums", 0);
			map.put("costNotFinshNums", 0);
			map.put("isSellRegistrationAmt", 0);
			map.put("notSellRegistrationAmt", 0);
		}
		return map;
	}

	public ValidateResult validate(JobListInput inputParam) {
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class getParamType() {
		// TODO Auto-generated method stub
		return JobListInput.class;
	}

}
