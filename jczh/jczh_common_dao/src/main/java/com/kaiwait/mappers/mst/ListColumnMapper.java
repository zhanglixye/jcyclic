package com.kaiwait.mappers.mst;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kaiwait.bean.mst.entity.ListColumn;
import com.kaiwait.bean.mst.io.ListColumnInput;
import com.kaiwait.common.dao.BaseMapper;

public interface ListColumnMapper extends BaseMapper{
	
	/**
	* 方法名 searchListColumnList
	* 方法的说明 标签初始页面
	* @param 
	* @return List<ListColumn>
	* @author FQQ
	* @date 2018.05.16
	*/
	List<ListColumn> searchListColumnList(@Param("usercd")String usercd,@Param("companycd")String companycd);
	
	/**
	* 方法名 searchListColumnListCost
	* 方法的说明 标签初始页面
	* @param 
	* @return List<ListColumn>
	* @author FQQ
	* @date 2018.05.16
	*/
	List<ListColumn> searchListColumnListCost(@Param("usercd")String usercd,@Param("companycd")String companycd);
	/**
	* 方法名 searchListColumnList
	* 方法的说明 标签初始页面
	* @param 
	* @return List<ListColumn>
	* @author FQQ
	* @date 2018.05.16
	*/
	List<ListColumn> searchListColumnListInit(@Param("usercd")String usercd,@Param("companycd")String companycd);
	
	/**
	* 方法名 searchListColumnListCost
	* 方法的说明 标签初始页面
	* @param 
	* @return List<ListColumn>
	* @author FQQ
	* @date 2018.05.16
	*/
	List<ListColumn> searchListColumnListCostInit(@Param("usercd")String usercd,@Param("companycd")String companycd);
	
//	/**
//	* 方法名 addListColumn
//	* 方法的说明 留言板新增
//	* @param 
//	* @return 
//	* @author FQQ
//	* @date 2018.05.16
//	*/
//	void addListColumn(ListColumn ListColumn);
//	
//	/**
//	* 方法名 deleteListColumn
//	* 方法的说明 留言板删除
//	* @param 
//	* @return 
//	* @author FQQ
//	* @date 2018.05.16
//	*/
//	void deleteListColumn(@Param("ListColumn_id")String ListColumn_id);
//	
	/**
	* 方法名 updateListColumn
	* 方法的说明 标签修改
	* @param 
	* @return 
	* @author FQQ
	* @date 2018.05.16
	*/
	void updateListColumn(ListColumnInput ListColumn);
	/**
	* 方法名 insertWedgemembersTx
	* 方法的说明 一览关联修改
	* @param List<ClmstAddInput>
	* @return List<User>
	* @author FQQ
	* @date 2018.05.16
	*/
	int insertusersettingtrn(@Param("clietList")List<ListColumnInput> list,@Param("addUserID")String addUserID,@Param("companyID")String companyID,@Param("adddate")String adddate,@Param("userid")String userid);

	/**
	* 方法名 deletetusersettingtrn
	* 方法的说明 一览关联删除
	* @param 
	* @return List<User>
	* @author FQQ
	* @date 2018.05.16
	*/
	void deletetusersettingtrn(@Param("userId")String userId,@Param("companyID")String companyID,@Param("level")String level);
}