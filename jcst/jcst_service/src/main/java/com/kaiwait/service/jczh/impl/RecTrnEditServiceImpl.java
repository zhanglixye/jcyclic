package com.kaiwait.service.jczh.impl;


import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.kaiwait.bean.jczh.entity.Pay;
import com.kaiwait.bean.jczh.entity.RecTrn;
import com.kaiwait.bean.jczh.io.JobListInput;
import com.kaiwait.bean.jczh.io.RecTrnInput;
import com.kaiwait.mappers.jczh.CommonMethedMapper;
import com.kaiwait.mappers.jczh.JobMapper;
import com.kaiwait.mappers.jczh.RecTrnMapper;
import com.kaiwait.service.jczh.RecTrnService;

@Service
public class RecTrnEditServiceImpl implements RecTrnService {
	@Resource
	private RecTrnMapper rectrnMapper;
	@Resource
	private JobMapper jobMapper;
	@Resource
	private CommonMethedMapper commonMethedMapper;
	

	public int RecTrnUpdateTx(List<RecTrnInput> inputParam) {
		int totalmum=0;
		JobListInput joblist =new  JobListInput();
		for(int i=0;i<inputParam.size();i++) {
			joblist.setJob_cd(inputParam.get(i).getJob_cd());
			joblist.setSale_no(inputParam.get(i).getSale_no());
			joblist.setCompanyID(inputParam.get(i).getCompany_cd());
			Pay sellnumber= jobMapper.selectLOCKFLG(joblist);
			int recLock=commonMethedMapper.getLockFlg(inputParam.get(i).getJob_cd(),Integer.valueOf(inputParam.get(i).getCompany_cd()),"rectrn");
			//如果没有壳上登录/
			if(sellnumber!=null&&!"".equals(sellnumber.getInputno())){
				//壳上更新了
				if(sellnumber.getLockflg()==inputParam.get(i).getLockflg()) {
					//更新锁
					if(recLock==inputParam.get(i).getReclockflg()) {
					}else {
						return 2;
					}
				}else {
					return 2;
				}
			}else {
				return 2;
			}
			
		}
		for(int i = 0;i<inputParam.size();i++) {
			RecTrnInput recTrnInput =new RecTrnInput();
			recTrnInput =inputParam.get(i);
			if(inputParam.size()>1) {//更新多条
		    rectrnMapper.RecTrnUpdateMoreTx(recTrnInput);		
			}else {
			totalmum=rectrnMapper.RecTrnUpdate1Tx(recTrnInput);	
			}
		}
		return totalmum;
	}

	public int RecTrnInsertTx(List<RecTrnInput> inputParam) {
		JobListInput joblist =new  JobListInput();
		for(int i=0;i<inputParam.size();i++) {
			joblist.setJob_cd(inputParam.get(i).getJob_cd());
			joblist.setSale_no(inputParam.get(i).getSale_no());
			joblist.setCompanyID(inputParam.get(i).getCompany_cd());
			Pay sellnumber= jobMapper.selectLOCKFLG(joblist);
			Integer recLock=commonMethedMapper.getLockFlg(inputParam.get(i).getJob_cd(),Integer.valueOf(inputParam.get(i).getCompany_cd()),"rectrn");
			//如果没有壳上登录/
			if(sellnumber!=null&&!"".equals(sellnumber.getInputno())){
				//壳上更新了
				if(sellnumber.getLockflg()==inputParam.get(i).getLockflg()) {
					//更新锁
					if(recLock==null||recLock==inputParam.get(i).getReclockflg()) {
					}else {
						return 2;
					}
				}else {
					return 2;
				}
			}else {
				return 2;
			}
			
		}
		
		int totalmum=0;
		for(int i = 0;i<inputParam.size();i++) {
			totalmum=rectrnMapper.RecTrnInsertTx(inputParam.get(i));	
		}
		return totalmum;
	}

	public int RecTrnDeleteDateTx(List<RecTrnInput> inputParam) {
		int totalmum=0;
		for(int i = 0;i<inputParam.size();i++) {
			totalmum=rectrnMapper.RecTrnDeleteDateTx(inputParam.get(i));	
		}
		return totalmum;
	}
	public RecTrn getRecAll(String jobcd,int companycd) {
		RecTrn recTrn = new RecTrn();
		recTrn=rectrnMapper.getRecAll(jobcd,companycd);
		return recTrn;
	}


	
}
