package com.kaiwait.service.mst.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.kaiwait.bean.mst.io.ListColumnInput;
import com.kaiwait.core.utils.DateUtil;
import com.kaiwait.mappers.mst.ListColumnMapper;
import com.kaiwait.service.mst.ListColumnService;

@Service
public class ListColumnServiceImpl implements ListColumnService{

	@Resource
	private ListColumnMapper ListColumnMapper;
	

	public ListColumnInput searchListColumnList(ListColumnInput inputParam)
	{
		ListColumnInput listcolumn = new ListColumnInput();
		String level = inputParam.getLevel();
		//0 job;1 cost;2 all
		if(level==null||level.equals("")) {			
			listcolumn.setListColumnList(ListColumnMapper.searchListColumnList(inputParam.getUserID(),inputParam.getCompanyID()));
			listcolumn.setListColumnListCost(ListColumnMapper.searchListColumnListCost(inputParam.getUserID(),inputParam.getCompanyID()));
		}else {
			listcolumn.setListColumnList(ListColumnMapper.searchListColumnListInit(inputParam.getUserID(),inputParam.getCompanyID()));
			listcolumn.setListColumnListCost(ListColumnMapper.searchListColumnListCostInit(inputParam.getUserID(),inputParam.getCompanyID()));
		}
		
		return listcolumn;
	}
	
//	public void addListColumnTx(ListColumnInput inputParam)
//	{
//		ListColumnMapper.addListColumn(inputParam.getListColumnmst());
//	}
//	
//	public void deleteListColumnTx(ListColumnInput inputParam)
//	{
//		ListColumnMapper.deleteListColumn(inputParam.getListColumn_id());
//	}
	public int ListColumnUpdateTx(ListColumnInput inputParam)
	{
		@SuppressWarnings("unused")
		String ss=inputParam.getListcolumninput().get(0).getLevel();
		//删除所有原有的绑定关系
		ListColumnMapper.deletetusersettingtrn(inputParam.getUserID(),inputParam.getCompanyID(),inputParam.getListcolumninput().get(0).getLevel());
		//插入新的绑定关系
		return ListColumnMapper.insertusersettingtrn(inputParam.getListcolumninput(),inputParam.getUserID(),inputParam.getCompanyID(),DateUtil.getNowTime(),inputParam.getUserID());

	}
}
