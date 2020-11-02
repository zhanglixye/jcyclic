package com.kaiwait.service.jczh.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kaiwait.bean.jczh.entity.Lable;
import com.kaiwait.bean.jczh.entity.MsgInfo;
import com.kaiwait.bean.jczh.entity.Role;
import com.kaiwait.bean.jczh.vo.CostListVo;
import com.kaiwait.core.utils.DateUtil;
import com.kaiwait.mappers.jczh.CommonMethedMapper;
import com.kaiwait.mappers.jczh.CostListMapper;
import com.kaiwait.mappers.jczh.TopMapper;
import com.kaiwait.service.jczh.CommonMethedService;
import com.kaiwait.service.jczh.TopService;
@Service
public class TopServiceImpl implements TopService {
	@Resource
	private TopMapper topmapper;
	@Resource
	private CommonMethedMapper commonMethedMapper;
	@Resource
	private CostListMapper costListMapper;
	@Resource
	private CommonMethedService commonService;
	public Map<String, Object> topLoad(int companycd,String usercd) {
		// TODO Auto-generated method stub
		
		Map<String, Object> map = new HashMap<String, Object>();
		CostListVo clvo = new CostListVo();
		Lable lable = new Lable();
		List<Role> roleList =commonMethedMapper.searchNodeListByUser(usercd, String.valueOf(companycd));
		boolean flg = false;
		boolean flgCost = false;
		if(roleList.size()>0) {
			for(int i=0;i<roleList.size();i++){
			   //job
				if(roleList.get(i).getNodeID()==5) {
					lable.setAll("5");
					 flg=true;
					}
				if(roleList.get(i).getNodeID()==6) {
					lable.setCldivUser(usercd);
					 flg=true;
					}
				if(roleList.get(i).getNodeID()==7) {
					lable.setAdUser(usercd);
					 flg=true;
					}
				if(roleList.get(i).getNodeID()==8) {
					lable.setMdUser(usercd);
					 flg=true;
					}
			   //原价
               if(roleList.get(i).getNodeID()==40) {
            	   clvo.setAll("40");
            	   flgCost = true;
				}
				if(roleList.get(i).getNodeID()==41) {
				   clvo.setCldivUser(usercd);
				   flgCost = true;
				}
				if(roleList.get(i).getNodeID()==42) {
				   clvo.setAdUser(usercd);
				   flgCost = true;
				}
				if(roleList.get(i).getNodeID()==43) {
				   clvo.setMdUser(usercd);
				   flgCost = true;
				}
			}
		}
		String sysDate = commonMethedMapper.getSystemDate(companycd);
	    String stt= DateUtil.makeDate(sysDate);
	    String checkDate = costListMapper.selectCheckDate(stt,companycd);
	    clvo.setCompanycd(companycd);
		clvo.setCheckDate(checkDate);
		int adcount = 0;
		int noadcount = 0;
		int progressCount = 0;
		Integer dateFlg = null;
		if(flgCost) {
			//进行中的原价数
			clvo.setStatus("001");
			List<CostListVo> topCostProgress = costListMapper.getTopProgressCount(clvo);
			if(topCostProgress.size()>0) {
				String paySkip = topCostProgress.get(0).getPaySkip();
				for(int i=0;i<topCostProgress.size();i++) {
					if(checkDate==null) {
						if(topCostProgress.get(i).getConfirmdate()!=null&&!topCostProgress.get(i).getConfirmdate().equals("")) {
							adcount++;
						}
					}else {
						dateFlg = topCostProgress.get(i).getConfirmdate().compareTo(checkDate);
						if(dateFlg>0&&topCostProgress.get(i).getConfirmdate()!=null&&!topCostProgress.get(i).getConfirmdate().equals("")) {
							adcount++;
						}
					}
					if((topCostProgress.get(i).getDiscd().equals("001")&&topCostProgress.get(i).getStatuscd().equals("2"))
						||(topCostProgress.get(i).getDiscd().equals("002")&&topCostProgress.get(i).getStatuscd().equals("0"))
						||(topCostProgress.get(i).getDiscd().equals("003")&&topCostProgress.get(i).getStatuscd().equals("0"))
						||(topCostProgress.get(i).getDiscd().equals("001")&&topCostProgress.get(i).getStatuscd().equals("1")&&paySkip.equals("1"))
							) {
						noadcount++;
					}
					
					progressCount++;
				}
			}
			
//			//进行中的原价数
//			 clvo.setStatus("001");
//			 //progressCount = costListMapper.getTopCostList(clvo);
//			 progressCount = costListMapper.getTopProgressCount(clvo).size();
			 //clvo.setAd("0");
			//原价承认件数
			 //adcount = costListMapper.getTopAdCount(clvo);
			 //adcount = costListMapper.getCostListFromTop(clvo).size();
			 //clvo.setAd("1");
			//原价未承认件数
			 //noadcount = costListMapper.getCostListFromTop(clvo).size();
			//noadcount = costListMapper.getTopAdCount(clvo);
			
		}
		map.put("adcount",adcount);
		map.put("noadcount", noadcount);
		map.put("progressCount", progressCount);
		//留言板查询件数
		int msgLimit = Integer.valueOf(topmapper.getMsgLimit());
		List<MsgInfo> msgInfoList = topmapper.getMsg(companycd,usercd,msgLimit);
		if(msgInfoList!=null) {
			for(MsgInfo msgInfo:msgInfoList) {
				msgInfo.setUpdate(commonService.getTimeByZone(msgInfo.getUpdate(), String.valueOf(companycd)));
			}
		}
		lable.setCompanycd(companycd);
		lable.setSysDate(sysDate);
		//map.put("msgTrn", topmapper.getMsg(companycd,usercd,msgLimit));
		map.put("msgTrn", msgInfoList);
		if(flg) {
			lable.setLablelevel(0);
			map.put("myLable", topmapper.getLable(lable));
			lable.setLablelevel(1);
			map.put("sysLable", topmapper.getLable(lable));	
		}else {
			map.put("myLable", null);
			map.put("sysLable", null);	
		}
		
		map.put("accountDate",DateUtil.dateToString(DateUtil.stringtoDate(sysDate,DateUtil.dateFormat),DateUtil.accountDateFormat));
		
		return map;
	}
	public List<MsgInfo> changeMsgStatusTx(int msgId, int companycd,String usercd) {
		// TODO Auto-generated method stub
		 //topmapper.changeMsgStatus(msgId, companycd);
		String nowDate = DateUtil.getDateForNow(DateUtil.dateTimeFormat);
		if(msgId!=0) {
			topmapper.deleteFromUserMsg(msgId, usercd, companycd);
			topmapper.insertReadMsg(msgId, usercd, companycd,nowDate);
		}
		int msgLimit = Integer.valueOf(topmapper.getMsgLimit());
		List<MsgInfo> listMsg = topmapper.getMsg(companycd,usercd,msgLimit);
		if(listMsg!=null) {
			for(MsgInfo msgInfo:listMsg) {
				msgInfo.setUpdate(commonService.getTimeByZone(msgInfo.getUpdate(), String.valueOf(companycd)));
			}
		}
		return listMsg;
	}

}
