package com.kaiwait.webservice.jczh;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kaiwait.bean.jczh.entity.RecTrn;
import com.kaiwait.bean.jczh.io.RecTrnInput;
import com.kaiwait.core.process.SingleFunctionIF;
import com.kaiwait.core.process.ValidateResult;
import com.kaiwait.service.jczh.CommonMethedService;
import com.kaiwait.service.jczh.RecTrnService;
import com.kaiwait.thrift.common.server.annotation.MatchEnum;
import com.kaiwait.thrift.common.server.annotation.Privilege;

@Component("recTrnEdit")
@Privilege(keys= {"35"},match=MatchEnum.ANY)
public class RecTrnEdit implements SingleFunctionIF<RecTrnInput> {

	@Resource
	private RecTrnService rectrnService;
	@Resource
	private CommonMethedService commonMethedService;

	@Override
	public Object process(RecTrnInput inputParam) {
		@SuppressWarnings("unused")

        //String deleteRecdate = null;
        int totalmum = 0;
        List<RecTrnInput> RecTrn = inputParam.getRecList();
        List<RecTrnInput> RecTrnlist = inputParam.getRecList();
        int RecTrnSize = RecTrn.size();
        RecTrn recTrn = new RecTrn();
        for (int i = 0; i < RecTrnSize; i++) {
        	
                Date date = new Date();
                SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                RecTrn.get(i).setUpdate(dateformat.format(date));
                RecTrn.get(i).setUpusercd(inputParam.getUserID());
                RecTrn.get(i).setAdddate(dateformat.format(date));
                RecTrn.get(i).setAddusercd(inputParam.getUserID());
                RecTrn.get(i).setCompany_cd(inputParam.getCompanyID());
                //deleteRecdate = RecTrn.get(i).getDeleteRecdate();
                recTrn  = rectrnService.getRecAll(RecTrn.get(i).getJob_cd(),Integer.valueOf(RecTrn.get(i).getCompany_cd()),RecTrn.get(i).getRec_cd());
                if(recTrn==null) {//执行insert
                        String rec_cd = commonMethedService.getMaxKeyCode("C", Integer.valueOf(inputParam.getCompanyID()));
                        RecTrn.get(i).setRec_cd(rec_cd);
                        RecTrnlist.get(i).setRec_cd(rec_cd);
                        RecTrnlist.get(i).setInsertflag("1");
                        int mum = rectrnService.RecTrnInsertTx(RecTrn.get(i));
                        totalmum = totalmum+1;
                }else {//执行update
                        int num = rectrnService.RecTrnUpdateTx(RecTrn.get(i));
                        totalmum = totalmum+1;
                }
        }
        return RecTrnlist;

		
		/*if("0".equals(RecTrn.get(0).getUdpflg())) {//更新
			totalmum = rectrnService.RecTrnUpdateTx(RecTrn);
			return totalmum;
		}else 
		{
			for (int i = 0; i < RecTrnSize; i++) {
				String rec_cd = commonMethedService.getMaxKeyCode("C", Integer.valueOf(inputParam.getCompanyID()));
				RecTrn.get(i).setRec_cd(rec_cd);
			}
			totalmum = rectrnService.RecTrnInsertTx(RecTrn);
			return totalmum;
		}*/
		
		/*if ("0".equals(deleteRecdate)) {
			// 删除所有你时间操作
			totalmum = rectrnService.RecTrnDeleteDateTx(RecTrn);
			return totalmum;
		} else if (RecTrn.get(0).getRec_cd() != null&&RecTrn.get(0).getRec_cd() !=""){// 存在入金id。update
			// 逐条更新
			totalmum = rectrnService.RecTrnUpdateTx(RecTrn);
			return totalmum;
		} else {
			for (int i = 0; i < RecTrnSize; i++) {
				String rec_cd = commonMethedService.getMaxKeyCode("C", Integer.valueOf(inputParam.getCompanyID()));
				RecTrn.get(i).setRec_cd(rec_cd);
			}
			totalmum = rectrnService.RecTrnInsertTx(RecTrn);
			return totalmum;
		}*/
	}

	public ValidateResult validate(RecTrnInput inputParam) {
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class getParamType() {
		// TODO Auto-generated method stub
		return RecTrnInput.class;
	}

}
