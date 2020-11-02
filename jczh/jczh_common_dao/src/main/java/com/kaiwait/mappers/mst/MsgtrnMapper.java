package com.kaiwait.mappers.mst;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kaiwait.bean.mst.entity.Msgtrn;
import com.kaiwait.common.dao.BaseMapper;

public interface MsgtrnMapper extends BaseMapper{
	
	/**
	* 方法名 searchMsgtrnList
	* 方法的说明 留言板初始页面
	* @param 
	* @return List<Msgtrn>
	* @author FQQ
	* @date 2018.05.16
	*/
	List<Msgtrn> searchMsgtrnList(@Param("companyid")String companyid,@Param("adduser")String adduser,@Param("id")int id);
	
	/**
	* 方法名 addMsgtrn
	* 方法的说明 留言板新增
	* @param 
	* @return 
	* @author FQQ
	* @date 2018.05.16
	*/
	void addMsgtrn(Msgtrn Msgtrn);
	
	/**
	* 方法名 deleteMsgtrn
	* 方法的说明 留言板删除
	* @param 
	* @return 
	* @author FQQ
	* @date 2018.05.16
	*/
	int deleteMsgtrn(@Param("num")String num);
	
	/**
	* 方法名 updateMsgtrn
	* 方法的说明 留言板修改
	* @param 
	* @return 
	* @author FQQ
	* @date 2018.05.16
	*/
	int updateMsgtrn(Msgtrn Msgtrn);
	String getcompanylevel(@Param("companyid")String companyid);
	int queryLock(@Param("num")String num);
	int updateLock(@Param("num")String num);
	int deleteUsermsgtrn(@Param("num")String num);
}