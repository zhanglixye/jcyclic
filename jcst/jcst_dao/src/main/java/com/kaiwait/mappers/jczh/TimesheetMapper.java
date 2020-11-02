package com.kaiwait.mappers.jczh;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.kaiwait.bean.jczh.entity.TimesheetUser;
import com.kaiwait.bean.jczh.entity.Timesheettrn;
import com.kaiwait.common.dao.BaseMapper;

public interface TimesheetMapper extends BaseMapper{
	
	/**
	* 方法名 searchInitList
	* 方法的说明 timesheet初始页面
	* @param 
	* @return List<Paytrn>
	* @author FQQ
	* @date 2018.05.16
	*/
	List<Map<String,Object>> searchInitList(@Param("companycd")String companycd,@Param("usercd")String usercd);
	
	/**
	* 方法名 timesheetInsert
	* 方法的说明 timesheet新增
	* @param 
	* @return List<Paytrn>
	* @author FQQ
	* @date 2018.05.16
	*/
	int timesheetInsert(@Param("userid")String userid,@Param("companyid")String companyid,@Param("jobcd")String jobcd,@Param("jobdate")String jobdate,@Param("date")String date,@Param("timenum")Double timenum);
	/**
	* 方法名 timesheetInsert
	* 方法的说明 timesheet新增
	* @param 
	* @return List<Paytrn>
	* @author FQQ
	* @date 2018.05.16
	*/
	int timesheetDelete(@Param("userid")String userid,@Param("companyid")String companyid,@Param("jobcd")String jobcd,@Param("jobdate")String jobdate,@Param("date")String date,@Param("timenum")Double timenum);
		
	/**
	* 方法名 timesheetQuery
	* 方法的说明 timesheet初始页面
	* @param 
	* @return List<TimesheetInput>
	* @author FQQ
	* @date 2018.05.16
	*/
	List<Timesheettrn> timesheetList(@Param("companycd")String companycd,@Param("usercd")String usercd,@Param("jobcd")String jobcd);
	List<TimesheetUser> timesheetuser(@Param("companycd")String companycd,@Param("usercd")String usercd);
	int timesheetIshave(@Param("userid")String userid,@Param("companyid")String companyid,@Param("jobcd")String jobcd,@Param("jobdate")String jobdate);
	int timesheetupdate(@Param("userid")String userid,@Param("companyid")String companyid,@Param("jobcd")String jobcd,@Param("jobdate")String jobdate,@Param("date")String date,@Param("timenum")Double timenum);
	String selectcompanycd(@Param("usercd")String usercd);
}