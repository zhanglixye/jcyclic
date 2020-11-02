package com.kaiwait.webservice.jczh;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kaiwait.bean.jczh.entity.JobLableTrn;
import com.kaiwait.bean.jczh.entity.JobLand;
import com.kaiwait.bean.jczh.entity.JobUserLable;
import com.kaiwait.bean.jczh.io.JobZhInput;
import com.kaiwait.core.process.SingleFunctionIF;
import com.kaiwait.core.process.ValidateResult;
import com.kaiwait.core.utils.DateUtil;
import com.kaiwait.service.jczh.CommonMethedService;
import com.kaiwait.service.jczh.JobLandService;
import com.kaiwait.thrift.common.server.annotation.MatchEnum;
import com.kaiwait.thrift.common.server.annotation.Privilege;
@Component("jobUpdate")
@Privilege(keys= {"9","10","11","12","13"}, match=MatchEnum.ANY)
public class JobUpdate implements SingleFunctionIF<JobZhInput> {
	@Resource
	private JobLandService jobLandService;
	@Resource
	private CommonMethedService commonMethedService;
	@Override
	public Object process(JobZhInput inputParam) {
		// TODO Auto-generated method stub
		    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    Date date = new Date();
			JobLand jobland = inputParam.getJob_land();
			if(jobland.getCostFinshFlg()==1) {
				jobland.setCostFinishDate(DateUtil.getNowDate());
				jobland.setCostFinishUser(inputParam.getUserID());
			}
			jobland.setCompany_cd(Integer.valueOf(inputParam.getCompanyID()));
			jobland.setUpddate(df.format(date));
			jobland.setUpdusercd(inputParam.getUserID());
			List<JobUserLable> julable = jobland.getCardnew();
			List<JobUserLable> cardAll = jobland.getJulable();
			List<JobUserLable> delcard = jobland.getDelcard();
			for(int i=0;i<julable.size();i++) {
				julable.get(i).setJob_cd(jobland.getJob_cd());
				julable.get(i).setAdd_date(df.format(date));
				julable.get(i).setUpd_date(df.format(date));
				julable.get(i).setAdd_user_cd(inputParam.getUserID());
				julable.get(i).setUpd_user_cd(inputParam.getUserID());
				julable.get(i).setCompany_cd(Integer.valueOf(inputParam.getCompanyID()));
				julable.get(i).setJuser_del_flg("0");
			}
			List<JobLableTrn> jltrn = jobland.getJlTrn();
		    if(jltrn==null||jltrn.size()==0) {
		    	jltrn = null;
		    }else {
			for(int i=0;i<jltrn.size();i++) {
				jltrn.get(i).setJobcd(jobland.getJob_cd());
				jltrn.get(i).setCompanycd(Integer.valueOf(inputParam.getCompanyID()));
				jltrn.get(i).setAddDate(DateUtil.getDateForNow(DateUtil.dateTimeFormat));
				jltrn.get(i).setAddUsercd(inputParam.getUserID());
			}
		    }
		    
			return jobLandService.updateJobTx(jobland,cardAll,julable,delcard,jltrn,inputParam.getUserID());
	}

	@Override
	public ValidateResult validate(JobZhInput inputParam) {
		// TODO Auto-generated method stub
		return null;
	}
	@SuppressWarnings("rawtypes")
	@Override
	public Class getParamType() {
		// TODO Auto-generated method stub
		return JobZhInput.class;
	}

}