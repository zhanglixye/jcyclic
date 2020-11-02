package com.kaiwait.service.mst.impl;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.kaiwait.bean.mst.entity.Msgtrn;
import com.kaiwait.bean.mst.entity.Role;
import com.kaiwait.bean.mst.io.MsgtrnInput;
import com.kaiwait.core.utils.DateUtil;
import com.kaiwait.mappers.mst.UserMapper;
import com.kaiwait.mappers.mst.MsgtrnMapper;
import com.kaiwait.mappers.mst.RoleMapper;
import com.kaiwait.service.mst.MsgtrnService;

@Service
public class MsgtrnServiceImpl implements MsgtrnService{

	@Resource
	private MsgtrnMapper MsgtrnMapper;
	@Resource
	private UserMapper userMapper;
	@Resource
	private RoleMapper roleMapper;
	

	public MsgtrnInput searchMsgtrnList(MsgtrnInput inputParam)
	{
		String companycd = inputParam.getCompanyID();
		String userid = inputParam.getUserID();
		int id = inputParam.getId();
		MsgtrnInput msg = new MsgtrnInput();
		//所有人都能看本公司的系统留言 和自己的留言
		//系统管理员选择参照公司的暂时先不做
		//msg.setMsgtrnList(MsgtrnMapper.searchMsgtrnList(companycd,userid));
		List<Msgtrn> msgtrnList = MsgtrnMapper.searchMsgtrnList(companycd,userid,id);
		String timeZone = userMapper.getTimeZoneByCompany(companycd);
		String upTime = "";
		for(int i = 0;i < msgtrnList.size();i++)
		{
			@SuppressWarnings("unused")
			String time1 = msgtrnList.get(i).getUpdate();
			upTime = DateUtil.getNewTime(msgtrnList.get(i).getUpdate(), Integer.valueOf(timeZone));
			msgtrnList.get(i).setUpdate(upTime);
		}
		msg.setMsgtrnList(msgtrnList);
		msg.setRoleList(userMapper.searchNodeListByUser(inputParam.getUserID(), companycd));
		List<Role> roleList = roleMapper.getRoleListByUserID(inputParam.getUserID());
		String manageflag = "0";//是否有管理组权限
		for(int i=0;i<roleList.size();i++){
			String roleid = String.valueOf(roleList.get(i).getRoleID());
			if(roleid.equals("1")){
				manageflag="1";
			}
		}	
		String companylevel = MsgtrnMapper.getcompanylevel(companycd);//公司类别
		msg.setManageflag(manageflag);
		msg.setLock_flg(MsgtrnMapper.queryLock(String.valueOf(id)));
		msg.setCompanylevel(companylevel);
		return msg;
	}
	
	public void addMsgtrnTx(MsgtrnInput inputParam)
	{	
		inputParam.getMsgtrn().setUpdate(DateUtil.getNowTime());
		inputParam.getMsgtrn().setAdddate(DateUtil.getNowTime());
		inputParam.getMsgtrn().setDel_flg("0");
		MsgtrnMapper.addMsgtrn(inputParam.getMsgtrn());
	}
	
	public int DeleteMsgtrnTx(MsgtrnInput inputParam)
	{
		int locknum = inputParam.getMsgtrn().getLock_flg();
		int locknumnow=MsgtrnMapper.queryLock(inputParam.getMsgtrn().getNum());
		if(locknumnow>locknum){
			 return-1; 
		 }
		MsgtrnMapper.updateLock(inputParam.getMsgtrn().getNum());
		return MsgtrnMapper.deleteMsgtrn(inputParam.getMsgtrn().getNum());

	}
	public int UpdateMsgtrnTx(MsgtrnInput inputParam)
	{
		int locknum = inputParam.getLock_flg();
		int locknumnow=MsgtrnMapper.queryLock(inputParam.getNum());
		if(locknumnow>locknum){
			 return-1; 
		 }
		inputParam.getMsgtrn().setUpdate(DateUtil.getNowTime());
		MsgtrnMapper.updateLock(inputParam.getNum());
		MsgtrnMapper.deleteUsermsgtrn(inputParam.getNum());
		return MsgtrnMapper.updateMsgtrn(inputParam.getMsgtrn());
	}
}
