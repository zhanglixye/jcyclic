package com.kaiwait.mappers.jczh;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import com.kaiwait.bean.jczh.entity.TimesheetUser;
import com.kaiwait.bean.jczh.entity.Timesheettrn;
import com.kaiwait.bean.jczh.entity.User;

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
	int timesheetInsert(@Param("usercd")String usercd,@Param("userid")String userid,@Param("companyid")String companyid,@Param("jobcd")String jobcd,@Param("jobdate")String jobdate,@Param("date")String date,@Param("timenum")Double timenum);
	/**
	* 方法名 timesheetInsert
	* 方法的说明 timesheet新增
	* @param 
	* @return List<Paytrn>
	* @author FQQ
	* @date 2018.05.16
	*/
	int timesheetDelete(@Param("usercd")String usercd,@Param("userid")String userid,@Param("companyid")String companyid,@Param("jobcd")String jobcd,@Param("jobdate")String jobdate,@Param("date")String date,@Param("timenum")Double timenum);
		
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
	int timesheetupdate(@Param("usercd")String usercd,@Param("userid")String userid,@Param("companyid")String companyid,@Param("jobcd")String jobcd,@Param("jobdate")String jobdate,@Param("date")String date,@Param("timenum")Double timenum);
	String selectcompanycd(@Param("usercd")String usercd);
	List<TimesheetUser> outPutTimeSheetList(@Param("companycd")String company_cd, @Param("usercd")String userid,@Param("dlvmonsta") String dlvmon_sta, @Param("dlvmonend")String dlvmon_end);
	String selectdept(@Param("userid")String userid, @Param("companycd")String company_cd);
	String companyname(@Param("companycd") String company_cd);
	List<User> selectprofessional(@Param("companycd")String company_cd, @Param("userid")String userid);

	String selectCompany(@Param("companycd")String company_cd);
}