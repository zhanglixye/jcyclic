package com.kaiwait.service.mst.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.kaiwait.bean.mst.entity.LabelMst;
import com.kaiwait.bean.mst.entity.Role;
import com.kaiwait.bean.mst.io.LabelInput;
import com.kaiwait.core.utils.DateUtil;
import com.kaiwait.mappers.mst.LabelMapper;
import com.kaiwait.mappers.mst.RoleMapper;
import com.kaiwait.mappers.mst.UserMapper;
import com.kaiwait.service.mst.LabelService;

@Service
public class LabelServiceImpl implements LabelService{

	@Resource
	private LabelMapper LabelMapper;
	@Resource
	private RoleMapper RoleMapper;
	@Resource
	private UserMapper userMapper;
	
	public LabelInput searchLabelList(LabelInput inputParam)
	{			
		String companycd = inputParam.getCompanyID();
		LabelInput input = new LabelInput();
		List<Role> listrole = userMapper.searchNodeListByUser(inputParam.getUserID(), companycd);
		String level = "0";
		String timeZone = userMapper.getTimeZoneByCompany(companycd);
		String upTime = "";
		String addTime = "";
		for(int i=0;i<listrole.size();i++) {
			//获取权限列表
			String nodeid = String.valueOf(listrole.get(i).getNodeID());
			if(nodeid.equals("71")) {//71是管理标签权限
				level = "1";
				break;
			}
			if(nodeid.equals("72")) {//72是普通标签权限
				level = "2";			
			}

		}
		//标签类型 0个人 1管理
		String Label_leve = level;
		input.setLabel_level(Label_leve);
		if(level.equals("1")) {
			List<LabelMst> labellist= LabelMapper.searchLabelListmanage(inputParam.getLabel_type(),inputParam.getUserID(),companycd);
			for(int i = 0;i < labellist.size();i++)
			{
				//String time1 = labellist.get(i).getAdddate();
				addTime = DateUtil.getNewTime(labellist.get(i).getAdddate(), Integer.valueOf(timeZone));
				upTime = DateUtil.getNewTime(labellist.get(i).getUpddate(), Integer.valueOf(timeZone));
				labellist.get(i).setAdddate(addTime);
				labellist.get(i).setUpddate(upTime);
			}
			input.setLableList(labellist);
			return input;
		}
		else if (level.equals("2")){
			List<LabelMst> labellist=  LabelMapper.searchLabelList(inputParam.getLabel_type(),inputParam.getUserID(),companycd);
			for(int i = 0;i < labellist.size();i++)
			{
				//String time1 = labellist.get(i).getAdddate();
				addTime = DateUtil.getNewTime(labellist.get(i).getAdddate(), Integer.valueOf(timeZone));
				upTime = DateUtil.getNewTime(labellist.get(i).getUpddate(), Integer.valueOf(timeZone));
				labellist.get(i).setAdddate(addTime);
				labellist.get(i).setUpddate(upTime);
			}
			input.setLableList(labellist);
			return input;
		}
		return null;
		
	}
	
	public int addLabelTx(LabelInput inputParam)
	{
		inputParam.getLabelmst().setAdddate(DateUtil.getNowDate());
		inputParam.getLabelmst().setUpddate(DateUtil.getNowDate());
		String companycd = inputParam.getCompanyID();
		String usercd = inputParam.getUserID();
		//校验同一Label_type下 标签名不能重复
		String labeltype=inputParam.getLabelmst().getLabel_type();
		String name = inputParam.getLabelmst().getLabel_text();
		String labellevel = inputParam.getLabelmst().getLabel_level();
		int count = LabelMapper.checkLabel(labeltype,name,companycd,usercd,labellevel);
		if(count>0){
			return -2;
		}
		//String submit = "";
		List<Role> listrole = userMapper.searchNodeListByUser(inputParam.getUserID(), companycd);
		String level = "0";
		for(int i=0;i<listrole.size();i++) {
			//获取权限列表
			String nodeid = String.valueOf(listrole.get(i).getNodeID());
			if(nodeid.equals("71")) {//71是管理标签权限
				level = "1";//管理标签权限
				break;
			}else if(nodeid.equals("72")){
				level = "2";//个人标签权限
			}
		}
		//标签类型 0个人 1管理
		String Label_leve = inputParam.getLabelmst().getLabel_level();
		if(level.equals("0")){
			return -3;
		}
		else{//有标签权限
			if(level.equals("2")&&Label_leve.equals("1")){
				return -3;
			}				
		}
		inputParam.getLabelmst().setAddusercd(inputParam.getUserID());
		inputParam.getLabelmst().setUpdusercd(inputParam.getUserID());
		return LabelMapper.addLabel(inputParam.getLabelmst());
	}
	public int deleteLabelTx(LabelInput inputParam)
	{
		List<LabelMst> labelList = inputParam.getLableList();
		for(int i=0;i<labelList.size();i++) {
			String id= labelList.get(i).getLabel_id();
			int locknum = labelList.get(i).getLock_flg();
			int locknumnow=LabelMapper.queryLock(id,inputParam.getCompanyID());
			if(locknumnow>locknum){
				 return -1; 
			 }
		}
		for(int i=0;i<labelList.size();i++) {
			LabelMapper.updateLock(labelList.get(i).getLabel_id(),inputParam.getCompanyID());
			LabelMapper.deleteLabel(labelList.get(i).getLabel_id(),DateUtil.getNowDate());
		}
		return 1;
		
	}
	public int UpdateLabelTx(LabelInput inputParam)
	{
		String companycd = inputParam.getCompanyID();
		String usercd = inputParam.getUserID();
		//校验同一Label_type下 标签名不能重复
		for(int i=0;i<inputParam.getLableList().size();i++) {
			String labeltype=inputParam.getLableList().get(i).getLabel_type();
			String labename = inputParam.getLableList().get(i).getLabel_text();
			String labellevel = inputParam.getLableList().get(i).getLabel_level();
			if(labellevel.equals("MYラベル")) {
				labellevel = "0";
			}
			else {
				labellevel = "1";
			}
			//int count = LabelMapper.checkLabel(labeltype,labename,companycd,usercd,labellevel);
			List<LabelMst> labels = LabelMapper.checkLabelList(labeltype, labename, companycd, usercd, labellevel);	
			if(labels.size()>0){
				if(!labels.get(0).getLabel_id().equals(inputParam.getLableList().get(i).getLabel_id())) {
					return -2;
				}
			}
		}
		List<LabelMst> labelList = inputParam.getLableList();
		for(int i=0;i<labelList.size();i++) {
			String id= labelList.get(i).getLabel_id();
			int locknum = labelList.get(i).getLock_flg();
			int locknumnow=LabelMapper.queryLock(id,inputParam.getCompanyID());
			if(locknumnow>locknum){
				 return -1; 
			 }
		}
		for(int i=0;i<labelList.size();i++) {
			String id= labelList.get(i).getLabel_id();
			String name= labelList.get(i).getLabel_text();
			LabelMapper.updateLock(id,inputParam.getCompanyID());
			LabelMapper.updateLabel(id,name,DateUtil.getNowDate());
		}
		return 1;
	}
	public String getMaxKeyCode(String keyFlg,int company_cd) {
		String ss = "";
		 if(keyFlg.equals("JL"))
		{
			ss="L" + LabelMapper.getMaxLableCode(company_cd);
		}
		return ss;
		
	}
}
