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
import com.kaiwait.service.jczh.CommonMethedService;
import com.kaiwait.service.jczh.RecTrnService;

@Service
public class RecTrnEditServiceImpl implements RecTrnService {
	@Resource
	private RecTrnMapper rectrnMapper;
	@Resource
	private JobMapper jobMapper;
	@Resource
	private CommonMethedMapper commonMethedMapper;
	
	@Resource
	private CommonMethedService commonMethedService;
	

	public int RecTrnUpdateTx(RecTrnInput inputParam) {
        int totalmum=0;
        JobListInput joblist =new  JobListInput();
        
                joblist.setJob_cd(inputParam.getJob_cd());
                joblist.setSale_no(inputParam.getSale_no());
                joblist.setCompanyID(inputParam.getCompany_cd());
                Pay sellnumber= jobMapper.selectLOCKFLG(joblist);
                int recLock=commonMethedMapper.getLockFlg(inputParam.getJob_cd(),Integer.valueOf(inputParam.getCompany_cd()),"rectrn");
                //如果没有壳上登录/
                if(sellnumber!=null&&!"".equals(sellnumber.getInputno())){
                        //壳上更新了
                        if(sellnumber.getLockflg()==inputParam.getLockflg()) {
                                //更新锁
                                if(recLock==inputParam.getReclockflg()) {
	                                }else {
	                                        return 2;
	                                }
				                        }else {
				                                return 2;
				                        }
							                }else {
							                        return 2;
							                }
                //
                totalmum=rectrnMapper.RecTrnUpdate1Tx(inputParam);        
        return totalmum;
        
	}

	public int RecTrnInsertTx(RecTrnInput inputParam) {

        JobListInput joblist =new  JobListInput();
                joblist.setJob_cd(inputParam.getJob_cd());
                joblist.setSale_no(inputParam.getSale_no());
                joblist.setCompanyID(inputParam.getCompany_cd());
                Pay sellnumber= jobMapper.selectLOCKFLG(joblist);
                Integer recLock=commonMethedMapper.getLockFlg(inputParam.getJob_cd(),Integer.valueOf(inputParam.getCompany_cd()),"rectrn");
              //如果没有壳上登录/
                if(sellnumber!=null&&!"".equals(sellnumber.getInputno())){
                        //壳上更新了
                        if(sellnumber.getLockflg()==inputParam.getLockflg()) {
                                //更新锁
                                if(recLock==null||recLock==inputParam.getReclockflg()) {
                                }else {
                                        return 2;
                                }
                        }else {
                                return 2;
                        }
                }else {
                        return 2;
                }
                int totalmum=0;
                totalmum=rectrnMapper.RecTrnInsertTx(inputParam);    
                totalmum=999;
		return totalmum;
	}

	public int RecTrnDeleteDateTx(List<RecTrnInput> inputParam) {
		int totalmum=0;
		for(int i = 0;i<inputParam.size();i++) {
			totalmum=rectrnMapper.RecTrnDeleteDateTx(inputParam.get(i));	
		}
		return totalmum;
	}
	public RecTrn getRecAll(String jobcd,int companycd,String reccd) {
		RecTrn recTrn = new RecTrn();
		recTrn=rectrnMapper.getRecAll(jobcd,companycd,reccd);
		return recTrn;
	}


	
}
