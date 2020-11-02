package com.kaiwait.service.mst.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kaiwait.bean.mst.entity.BindLable;
import com.kaiwait.bean.mst.entity.Lable;
import com.kaiwait.bean.mst.io.LableInput;
import com.kaiwait.core.utils.DateUtil;
import com.kaiwait.mappers.mst.LableDealMapper;
import com.kaiwait.service.mst.LabelService;
import com.kaiwait.service.mst.LableDealService;

@Service
public class LableDealServiceImp implements LableDealService{
	@Resource
	private LableDealMapper lableDealMapper;
	@Resource
	private LabelService labelService;
	public Object listLableAddTx(LableInput inputParam) {
		// TODO Auto-generated method stub
			String nowDate = DateUtil.getDateForNow(DateUtil.dateTimeFormat);
			String addUsercd = inputParam.getUserID();
		 
		    String lable_id = labelService.getMaxKeyCode("JL",Integer.valueOf(inputParam.getCompanyID()));
			Lable lable = inputParam.getListlable().getLable();
			int num = lableDealMapper.isHaveLable(lable.getLabletext(), lable.getLablelevel(),inputParam.getUserID(), Integer.valueOf(inputParam.getCompanyID()));
			if(num>0) {
				return 250;
			}
			lable.setLableid(lable_id);
			lable.setCompanycd(Integer.valueOf(inputParam.getCompanyID()));
			lable.setUpdusercd(inputParam.getUserID());
			lable.setAddusercd(inputParam.getUserID());
			lable.setAdddate(nowDate);
			lable.setUpddate(nowDate);
			lableDealMapper.addLable(lable);
	//绑定
			
		List<BindLable> bindlable = inputParam.getListlable().getBindlable();
		for(int i = 0;i<bindlable.size();i++) {
			lableDealMapper.bindLable(bindlable.get(i).getJobcd(), bindlable.get(i).getIncostno(), bindlable.get(i).getIncostnolevel(), lable_id, Integer.valueOf(inputParam.getCompanyID()),
					nowDate,addUsercd);
		}
		return lable_id;
	}
	public Object listLableSetTx(LableInput inputParam) {
		// TODO Auto-generated method stub
		List<BindLable> bindlable = inputParam.getListlable().getBindlable();
		List<Lable> lable =  inputParam.getListlable().getListlableid();
		List<Lable> delLable = inputParam.getListlable().getListdellable();
		
		String nowDate = DateUtil.getDateForNow(DateUtil.dateTimeFormat);
		String addUsercd = inputParam.getUserID();
		//先執行刪除
		for(int i=0;i<bindlable.size();i++) {
			for(int j = 0;j<delLable.size();j++) {
				lableDealMapper.delBindLable(bindlable.get(i).getJobcd(), bindlable.get(i).getIncostno(), delLable.get(j).getLableid(), Integer.valueOf(inputParam.getCompanyID()));
			}
			for(int k=0;k<lable.size();k++) {
				//查询是否有 有就过 没有插入
				int num = lableDealMapper.isHaveBind(bindlable.get(i).getJobcd(),bindlable.get(i).getIncostno(),
						lable.get(k).getLableid(), Integer.valueOf(inputParam.getCompanyID()));
				if(num<1) {
					lableDealMapper.bindLable(bindlable.get(i).getJobcd(), bindlable.get(i).getIncostno(), 
						bindlable.get(i).getIncostnolevel(), lable.get(k).getLableid(), Integer.valueOf(inputParam.getCompanyID()),
						nowDate,addUsercd);
				}
			}
		}
		return 1;
	}
	public Object addLableTx(LableInput inputParam) {
		// TODO Auto-generated method stub
		 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		 Date date = new Date();
	
		    String lable_id = labelService.getMaxKeyCode("JL",Integer.valueOf(inputParam.getCompanyID()));
			Lable lable = inputParam.getLable();
			int num = lableDealMapper.isHaveLable(lable.getLabletext(),lable.getLablelevel(), inputParam.getUserID(), Integer.valueOf(inputParam.getCompanyID()));
			if(num>0) {
				return 250;
			}
			lable.setLableid(lable_id);
			lable.setCompanycd(Integer.valueOf(inputParam.getCompanyID()));
			lable.setUpdusercd(inputParam.getUserID());
			lable.setAddusercd(inputParam.getUserID());
			lable.setAdddate(df.format(date));
			lable.setUpddate(df.format(date));
			int cd = lableDealMapper.addLable(lable);
			if(cd>0) {
				return lable_id;
			}else {
				return  null ;
			}
	
	}

}
