package com.kaiwait.webservice.mst;


import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kaiwait.bean.mst.io.MsgtrnInput;
import com.kaiwait.core.process.SingleFunctionIF;
import com.kaiwait.core.process.ValidateResult;
import com.kaiwait.service.mst.MsgtrnService;

@Component("MsgtrnAdd")

public class MsgtrnAdd implements SingleFunctionIF<MsgtrnInput>{
	
	@Resource
	private MsgtrnService MsgtrnService;
	
	@Override
	public Object process(MsgtrnInput inputParam) {
		inputParam.getMsgtrn().setUpuser(inputParam.getUserID());
		inputParam.getMsgtrn().setAdduser(inputParam.getUserID());
		inputParam.getMsgtrn().setCompany_cd(inputParam.getCompanyID());
		String msg_level = inputParam.getMsgtrn().getMsg_level();//0:个人留言1：系统消息
		String manageflag = inputParam.getMsgtrn().getManageflag();//是否有管理组权限,0没有；1有
		String companylevel = inputParam.getMsgtrn().getCompanylevel();//公司级别0：博報堂、1：博报堂系以外、2：管理用
		String roletype = inputParam.getMsgtrn().getRoletype();//留言板权限 0没有 1有
		//留言板权限
		if(roletype.equals("0")) {
			return "NOADD";
		}
		//没有管理组权限，不能新增系统留言
		//管理组权限+管理公司级别+一定有留言板权限 可以增删改系统消息，不能改个人留言
		if(msg_level.equals("0") && manageflag.equals("1")&& companylevel.equals("2")) {
			return "NOADD";
		}
		//管理组权限+一般公司级别+不增删改改个人留言
		if(msg_level.equals("0") && manageflag.equals("1")&& (companylevel.equals("0")||companylevel.equals("1"))) {
			return "NOADD";
		}
		//非管理组权限+管理公司级别+任何留言板权限  不能增删改系统消息
		if(manageflag.equals("0")&& companylevel.equals("2")&&msg_level.equals("1")) {
			return "NOADD";
		}		
		//非管理组权限+一般公司级别+留言板权限  可以增删改个人消息，不能改系统消息
		if(manageflag.equals("0")&& (companylevel.equals("0")||companylevel.equals("1"))&& roletype.equals("1")&& msg_level.equals("1")) {
			return "NOADD";
		}

		MsgtrnService.addMsgtrnTx(inputParam);
		return null;
	}

	public ValidateResult validate(MsgtrnInput inputParam) {		
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class getParamType() {
		// TODO Auto-generated method stub
		return MsgtrnInput.class;
	}


}
