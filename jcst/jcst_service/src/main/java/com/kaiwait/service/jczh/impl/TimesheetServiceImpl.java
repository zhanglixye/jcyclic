package com.kaiwait.service.jczh.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.kaiwait.bean.jczh.entity.JobList;
import com.kaiwait.bean.jczh.entity.Timesheettrn;
import com.kaiwait.bean.jczh.io.JobListInput;
import com.kaiwait.bean.jczh.io.TimesheetInput;
import com.kaiwait.core.utils.DateUtil;
import com.kaiwait.mappers.jczh.CommonMethedMapper;
import com.kaiwait.mappers.jczh.JobMapper;
import com.kaiwait.mappers.jczh.TimesheetMapper;
import com.kaiwait.service.jczh.TimesheetService;

@Service
public class TimesheetServiceImpl implements TimesheetService{

	@Resource
	private TimesheetMapper TimesheetMapper;
	@Resource
	private JobMapper jobMapper;
	@Resource
	private CommonMethedMapper commonMethedMapper;
	
	Date day=new Date();
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
	String datenow = df.format(day);
	
	public static String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";
	public static String dateFormat = "yyyy-MM-dd";
	public static String dateFormatYeayMonth = "yyyyMM";
	
	public List<Map<String,Object>> searchInitList(TimesheetInput inputParam)
	{
		return TimesheetMapper.searchInitList(inputParam.getCompanycd(),inputParam.getUsercd());
	}
	
	public int timesheetInsert(TimesheetInput inputParam)
	{
		String userid = inputParam.getUserID();
		String companyid = inputParam.getCompanyID();
		for(int i=0;i<inputParam.getInsertlist().size();i++) {
			String jobcd = inputParam.getInsertlist().get(i).getJobcd();
			String jobdate = inputParam.getInsertlist().get(i).getDaynum();
			Double timenum= inputParam.getInsertlist().get(i).getTimenum();
			int ishave = TimesheetMapper.timesheetIshave(userid,companyid,jobcd,jobdate);
			//int f = Integer.parseInt(timenum);
			if (timenum == 0) {
				TimesheetMapper.timesheetDelete(userid, companyid, jobcd, jobdate, DateUtil.getNowDate(),timenum );
			} else {
				if (ishave > 0) {
					TimesheetMapper.timesheetupdate(userid, companyid, jobcd, jobdate, DateUtil.getNowDate(),timenum );

				} else {
					TimesheetMapper.timesheetInsert(userid, companyid, jobcd, jobdate, DateUtil.getNowDate(),timenum );
				}
			}
			
		}
		return 1;
	}
	public TimesheetInput timesheetQuery(JobListInput jobInput) {
//		Timesheet 显示规则
//		第一种job：job登陆日在日历最右侧日期之前的job 并且，job状态是进行中的
//		第二种job：job登陆日在日历最右侧日期之前的job 并且，job状态是终了的，并且job的终了日是30天内的（含30天） 		
		String dt = jobInput.getJobenddate();
		Calendar nowTime = Calendar.getInstance();
		@SuppressWarnings("unused")
		SimpleDateFormat dformat = new SimpleDateFormat(dateFormat); 
		nowTime.setTime(DateUtil.stringtoDate(dt, dateFormat));
		nowTime.add(Calendar.DATE, -30);// 30天
		SimpleDateFormat df = new SimpleDateFormat(dateFormat); 
		String jobendmonth= df.format(nowTime.getTime());
		String mycompanycd =TimesheetMapper.selectcompanycd(jobInput.getUserID());
		jobInput.setJobendmonth(jobendmonth);
		String userid = jobInput.getUserid();
		//如果为空则查询自己
		if(userid == null || userid.equals("")) {
			jobInput.setUserid(jobInput.getUserID());
		}
		jobInput.setAll("1");
		List<JobList> joblist=	jobMapper.searchJobByKeyWord(jobInput);
		for(int i=0;i<joblist.size();i++){
			String jobcd=joblist.get(i).getJobcd();
			List<Timesheettrn> ss = TimesheetMapper.timesheetList(jobInput.getCompanyID(), jobInput.getUserid(), jobcd);
			joblist.get(i).setTimesheettrn(ss);
		}
		TimesheetInput timesheetinput = new TimesheetInput();
		timesheetinput.setJoblist(joblist);
		timesheetinput.setTimesheetuser(TimesheetMapper.timesheetuser(mycompanycd, jobInput.getUserid()));
		if(!mycompanycd.equals(jobInput.getCompanyID())) {
			timesheetinput.setCompanycd("1");
		}
		return timesheetinput;
	}
}
