package com.kaiwait.mappers.mst;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kaiwait.bean.mst.entity.LabelMst;
import com.kaiwait.common.dao.BaseMapper;

public interface LabelMapper extends BaseMapper{
	
	/**
	* 方法名 searchLabelList
	* 方法的说明 标签初始页面
	* @param 
	* @return List<Label>
	* @author FQQ
	* @date 2018.05.16
	*/
	List<LabelMst> searchLabelList(@Param("labeltype")String labeltype,@Param("userid")String userid,@Param("companycd")String companycd);
	/**
	* 方法名 searchLabelList
	* 方法的说明 标签初始页面
	* @param 
	* @return List<Label>
	* @author FQQ
	* @date 2018.05.16
	*/
	List<LabelMst> searchLabelListmanage(@Param("labeltype")String labeltype,@Param("userid")String userid,@Param("companycd")String companycd);
	/**
	* 方法名 addLabel
	* 方法的说明 标签新增
	* @param 
	* @return 
	* @author FQQ
	* @date 2018.05.16
	*/
	int addLabel(LabelMst LabelMst);
	
	/**
	* 方法名 deleteLabel
	* 方法的说明 标签删除
	* @param 
	* @return 
	* @author FQQ
	* @date 2018.05.16
	*/
	int deleteLabel(@Param("labelid")String labelid,@Param("deletedate")String deletedate);
	
	/**
	* 方法名 updateLabel
	* 方法的说明 标签修改
	* @param 
	* @return 
	* @author FQQ
	* @date 2018.05.16
	*/
	int updateLabel(@Param("labelid")String labelid,@Param("labelname")String labelname,@Param("deletedate")String deletedate);
	String getMaxLableCode(@Param("companyID")int companyID);
	int checkLabel(@Param("labeltype")String labeltype,@Param("name")String name,@Param("companycd")String companycd,@Param("usercd")String usercd,@Param("labellevel")String labellevel);
	int queryLock(@Param("label_id")String label_id,@Param("companycd")String companycd);
	int updateLock(@Param("label_id")String label_id,@Param("companycd")String companycd);
	List<LabelMst> checkLabelList(@Param("labeltype")String labeltype,@Param("name")String name,@Param("companycd")String companycd,@Param("usercd")String usercd,@Param("labellevel")String labellevel);

}