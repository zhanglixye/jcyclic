package com.kaiwait.service.jczh.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.kaiwait.bean.jczh.entity.Cost;
import com.kaiwait.bean.jczh.entity.JobList;
import com.kaiwait.bean.jczh.entity.Role;
import com.kaiwait.bean.jczh.entity.TimesheetUser;
import com.kaiwait.bean.jczh.entity.Timesheettrn;
import com.kaiwait.bean.jczh.entity.User;
import com.kaiwait.bean.jczh.io.JobListInput;
import com.kaiwait.bean.jczh.io.TimesheetInput;
import com.kaiwait.core.utils.DateUtil;
import com.kaiwait.mappers.jczh.CommonMethedMapper;
import com.kaiwait.mappers.jczh.CommonmstMapper;
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
	@Resource
	private CommonmstMapper commonmstdMapper;

	
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
			String usercd = inputParam.getInsertlist().get(i).getUsercd();
			int ishave = TimesheetMapper.timesheetIshave(usercd,companyid,jobcd,jobdate);
			//int f = Integer.parseInt(timenum);
			if (timenum == 0) {
				TimesheetMapper.timesheetDelete(usercd, userid,companyid, jobcd, jobdate, DateUtil.getNowDate(),timenum );
			} else {
				if (ishave > 0) {
					TimesheetMapper.timesheetupdate(usercd,userid, companyid, jobcd, jobdate, DateUtil.getNowDate(),timenum );

				} else {
					TimesheetMapper.timesheetInsert(usercd,userid, companyid, jobcd, jobdate, DateUtil.getNowDate(),timenum );
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
		//计算税率，影业额等所用参数
		String taxFormatFlg =commonmstdMapper.getforeignVatFormatFlg("0052",jobInput.getCompanyID(),"003");
		String foreignFormatFlg =commonmstdMapper.getforeignVatFormatFlg("0051",jobInput.getCompanyID(),"001");
		String userid = jobInput.getUserid();
		//如果为空则查询自己
		if(userid == null || userid.equals("")) {
			jobInput.setUserid(jobInput.getUserID());
		}
		jobInput.setAll("1");
		List<JobList> joblist=	jobMapper.searchJobByKeyWord(jobInput);
		for(int i=0;i<joblist.size();i++){
//			String profitRateO = joblist.get(i).getProfitRate();
//			String  profitRate[]=profitRateO.split("\\.");
//			String profitRateN =profitRate[0]+"."+profitRate[1].substring(0,2)+"%";
//			joblist.get(i).setProfitRate(profitRateN);
			String jobcd=joblist.get(i).getJobcd();
			List<Timesheettrn> ss = TimesheetMapper.timesheetList(jobInput.getCompanyID(), jobInput.getUserid(), jobcd);
			joblist.get(i).setTimesheettrn(ss);
			List<Cost> costInfo = commonMethedMapper.getSumcost(Integer.valueOf(jobInput.getCompanyID()), joblist.get(i).getJobcd());
            joblist.get(i).setCostTotalAmt(costInfo.get(0).getSumamt());
            joblist.get(i).setCostVatTotal(costInfo.get(0).getSumvat());
            joblist.get(i).setPayAmtSum(costInfo.get(0).getPaysum());
		}
		List<JobList> jsflaglanguage =jobMapper.selectMstNameByCD("0020", jobInput.getCompanyID());//计上状态
        List<JobList> reqflglanguage =jobMapper.selectMstNameByCD("0021", jobInput.getCompanyID());         //请求状态
        List<JobList> recflglanguage =jobMapper.selectMstNameByCD("0022", jobInput.getCompanyID()) ;        //入金状态
        List<JobList> invflglanguage =jobMapper.selectMstNameByCD("0021", jobInput.getCompanyID()); //发票状态
        List<JobList> assignflglanguage =jobMapper.selectMstNameByCD("0026", jobInput.getCompanyID()); //割当担当
        List<JobList> costfinishflglanguage =jobMapper.selectMstNameByCD("0027", jobInput.getCompanyID()); //原价完了
        HashMap<String, Object>  jobMesAll = new HashMap<String, Object>();
      
        jobMesAll.put("jsflaglanguage", jsflaglanguage);
        jobMesAll.put("reqflglanguage", reqflglanguage);
        jobMesAll.put("recflglanguage", recflglanguage);
        jobMesAll.put("invflglanguage", invflglanguage);
        jobMesAll.put("assignflglanguage", assignflglanguage);
        jobMesAll.put("costfinishflglanguage", costfinishflglanguage);
		TimesheetInput timesheetinput = new TimesheetInput();
		
		timesheetinput.setTaxFormatFlg(taxFormatFlg);
		timesheetinput.setForeignFormatFlg(foreignFormatFlg);
		timesheetinput.setJoblist(joblist);
		timesheetinput.setAllmap(jobMesAll);
		timesheetinput.setTimesheetuser(TimesheetMapper.timesheetuser(mycompanycd, jobInput.getUserid()));
		if(!mycompanycd.equals(jobInput.getCompanyID())) {
			timesheetinput.setCompanycd("1");
		}
		timesheetinput.setOutputsheetqx("0");
		//查询该用户权限
				List<Role> role =commonMethedMapper.searchNodeListByUser(jobInput.getUserid(), String.valueOf(jobInput.getCompanyID()));
				 if(isHavePower(role,68)) {
					 timesheetinput.setOutputsheetqx("1");
		    	 }
		return timesheetinput;
	}

	@Override
	public String outPutTimeSheetList(JobListInput inputParam) {
		
		String mansge1 = TimesheetMapper.selectCompany(inputParam.getCompany_cd());
		String sqdate = mansge1.substring(1,mansge1.length());
		
		
		SimpleDateFormat fd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;  
		String sqtime = fd.format(new Date());
		int sqtime1 = Integer.parseInt(sqdate);
         
        try {   
            date = fd.parse(sqtime);   
        } catch (Exception ex) {   
            ex.printStackTrace();   
        }    
        Calendar cal = Calendar.getInstance();   
        cal.setTime(date);   
        cal.add(Calendar.HOUR, sqtime1);// 24小时制   
        date = cal.getTime();   
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		//处理日
		String manage = df.format(date);
		String Startdate = inputParam.getDlvmon_sta()+"-01";
		String enddate = inputParam.getDlvmon_end();
		String[] Nenddate = enddate.split("-");
		int endyear =   Integer.parseInt(Nenddate[0]) ;
		int endmonth = Integer.parseInt(Nenddate[1]) ;
		Calendar cal1 = Calendar.getInstance();
		cal1.set(Calendar.YEAR,endyear);
		cal1.set(Calendar.MONTH,endmonth-1);//7月
		int maxDate = cal1.getActualMaximum(Calendar.DATE); 
		String maxdate = maxDate+"";
		String endate =  inputParam.getDlvmon_end()+"-"+maxdate;
		@SuppressWarnings("unused")
		List<TimesheetUser> timeSheetList = null;
		//查询所有符合条件的数据
		List<TimesheetUser> TimeSheetList = TimesheetMapper.outPutTimeSheetList(inputParam.getCompany_cd(),inputParam.getUserid(),Startdate,endate);
		//查询该用户权限
		List<Role> role =commonMethedMapper.searchNodeListByUser(inputParam.getUserid(), String.valueOf(inputParam.getCompany_cd()));
		List<TimesheetUser> sszSheetList =new ArrayList<TimesheetUser>();
		String permission  = null;
		//权限的判断
		 if(isHavePower(role,65)) {
			 permission = "1";
    	 }
		 else if(isHavePower(role,66)) {
			 permission = "2";
		 }
		 else if(isHavePower(role,67)) {
			 permission = "3";
		 }
		 //若没有数据则返回表头
		if(TimeSheetList.size()!=0) {
		if(permission.equals("1")) {
			sszSheetList = TimeSheetList;
		}else if(permission.equals("2")) {
			//查询是否是该公司的所属长 若不是打印自己的数据 、是打印自己部门的数据
			String selectdept = TimesheetMapper.selectdept(inputParam.getUserid(),inputParam.getCompany_cd());
			if(selectdept!=null) {
				String dept_cd = inputParam.getDepartcd();
				for(int j =0;j<TimeSheetList.size();j++) {
					String deptCd = TimeSheetList.get(j).getDepartcd();
					if(deptCd.equals(dept_cd)) {
						sszSheetList.add(TimeSheetList.get(j));
					}
				}
			}else {
				for(int j =0;j<TimeSheetList.size();j++) {
					if(inputParam.getUserid().equals(TimeSheetList.get(j).getUserid())) {
						sszSheetList.add(TimeSheetList.get(j));
					}
				}
			}
		}
		else if(permission.equals("3")) {
			@SuppressWarnings("unused")
			int z =0;
			for(int j =0;j<TimeSheetList.size();j++) {
				if(inputParam.getUserid().equals(TimeSheetList.get(j).getUserid())) {
					sszSheetList.add(TimeSheetList.get(j));
				}
			}
		}
		//公司名称
		String companyname = TimesheetMapper.companyname(inputParam.getCompany_cd());
		//部门名称
		String deptname =null;
		//业务级别
		List<User> professional = TimesheetMapper.selectprofessional(inputParam.getCompany_cd(),inputParam.getUserid());
		String profession = "";
		if(inputParam.getTimesheet().equals("jp")) {
			profession= professional.get(0).getLeveljp();
		}else if(inputParam.getTimesheet().equals("en")) {
			profession = professional.get(0).getLevelen();
		}else if(inputParam.getTimesheet().equals("zc")) {
			profession = professional.get(0).getLevelzc();
		}else if(inputParam.getTimesheet().equals("zt")) {
			profession = professional.get(0).getLevelzt();
		}
		
       	try {
       		
       	
    		//创建HSSF工作薄
       		XSSFWorkbook workbook = new XSSFWorkbook();
            //创建一个Sheet页
       		XSSFSheet sheet = null;
        	sheet = workbook.createSheet("TimeSheetReport");
             //创建第一行（一般是表头）
            XSSFRow row = sheet.createRow(0);
          //创建列
            XSSFCell cell = null;
            row.setHeightInPoints(30);
          //合并单元格(4个参数，分别为起始行，结束行，起始列，结束列)
//              CellRangeAddress regionend = new CellRangeAddress(5+(sszSheetList.size()+1), 5+(sszSheetList.size()+1), 1, 7);
//              sheet.addMergedRegion(regionend);
              
              String fontName = getFontNameByLangT(inputParam.getTimesheet());
              String fontName1 = getFontNameByLangT1(inputParam.getTimesheet());
              //タイムシート集計表的
              XSSFFont titleFontRight = getExcelFont(workbook,(short)14,fontName); 
              //字体加粗
              titleFontRight.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
              
              
              
              XSSFFont titleFontleft = getExcelFont(workbook,(short)11,fontName); 
              //数据
              XSSFFont titleFontleft1 = getExcelFont(workbook,(short)10,fontName); 
              //HSSFFont titleFont = getExcelFont(workbook,(short)14); 
              XSSFFont bodyFont = getExcelFont(workbook,(short)1);
              XSSFCellStyle titleStyle = getExcelStyle(workbook, titleFontRight, HSSFCellStyle.VERTICAL_TOP, HSSFCellStyle.VERTICAL_BOTTOM, 0, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",false);
              @SuppressWarnings("unused")
              XSSFCellStyle outDateStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER, 0, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",false);
            //数据
              XSSFCellStyle bodyStyle2 = getExcelStyle(workbook, titleFontleft1, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.ALIGN_LEFT, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",true);
              //内容头
              XSSFCellStyle bodyStyle3 = getExcelStyle(workbook, titleFontleft1, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_BOTTOM, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",true);
              @SuppressWarnings("unused")
              XSSFCellStyle bodyStyle = getExcelStyle(workbook, titleFontleft, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",true);
              //HSSFCellStyle bodyStyle1 = getExcelStyle(workbook, titleFontleft, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.ALIGN_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",true);
              //集时间 和处理日
              XSSFCellStyle bodyStyle1 = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER, 0, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",false);;
              XSSFFont eFont = workbook.createFont();
	      		eFont.setFontHeightInPoints((short) 11);
	            eFont.setFontName(fontName);
              bodyStyle1.setFont(eFont);
              @SuppressWarnings("unused")
			XSSFCellStyle bodyLeftStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",true);
              XSSFCellStyle bodyRightStyle= getStyle(workbook,HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index);
              @SuppressWarnings("unused")
              XSSFFont eFont1 = workbook.createFont();
	      		eFont.setFontHeightInPoints((short) 10);
	            eFont.setFontName(fontName);
	            bodyRightStyle.setFont(eFont);
              XSSFCellStyle titles = workbook.createCellStyle();
              titles.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
              XSSFFont eFont2 = workbook.createFont();
	      		eFont2.setFontHeightInPoints((short) 11);
	            eFont2.setFontName(fontName1);
	            titles.setFont(eFont2);
              sheet.setColumnWidth(1, 15*256);
              sheet.setColumnWidth(2, 15*256);
              sheet.setColumnWidth(3, 15*256);
              sheet.setColumnWidth(4, 15*256);
              sheet.setColumnWidth(5, 15*256);
              sheet.setColumnWidth(6, 15*256);
              sheet.setColumnWidth(7, 15*256);
              sheet.setColumnWidth(8, 15*256);
              //设置表头
            for (int i = 0; i <9 ; i++) {
                cell=row.createCell(i);
                if(i==0){
                	sheet.setColumnWidth(0, 2*256);
                }
                if(i==1) {
                	   cell.setCellValue(companyname);
                	   cell.setCellStyle(titles);
                }
                if(i==4) {
                	
                	if(inputParam.getTimesheet().equals("jp")) {
                		cell.setCellValue("タイムシート集計表");
            		}else if(inputParam.getTimesheet().equals("en")) {
            			cell.setCellValue("時間表總計表");
            		}else if(inputParam.getTimesheet().equals("zc")) {
            			cell.setCellValue("时间表总计表");
            		}else if(inputParam.getTimesheet().equals("zt")) {
            			cell.setCellValue("時間表總計表");
            		}
                	cell.setCellStyle(titleStyle);
                }
            }
              //集計期間 第二行
              String stdate = inputParam.getDlvmon_sta().replace("-","/");
              String etdate = inputParam.getDlvmon_end().replace("-","/");
               row =sheet.createRow(1);
               cell=row.createCell(0);
               cell=row.createCell(1);
               
               if(inputParam.getTimesheet().equals("jp")) {
            	   cell.setCellValue("集計期間  :   "+stdate+"-"+etdate);
       		}else if(inputParam.getTimesheet().equals("en")) {
       			cell.setCellValue("合計期間   :   "+stdate+"-"+etdate);
       		}else if(inputParam.getTimesheet().equals("zc")) {
       			cell.setCellValue("合计期间  :   "+stdate+"-"+etdate);
       		}else if(inputParam.getTimesheet().equals("zt")) {
       			cell.setCellValue("合計期間  :   "+stdate+"-"+etdate);
       		}
              cell.setCellStyle(bodyStyle1);
              cell=row.createCell(2);
              cell=row.createCell(3);
             cell=row.createCell(4);
              cell=row.createCell(5);
             cell=row.createCell(6);
              cell=row.createCell(7);
              cell=row.createCell(8);
              
              
             //createCell(workbook, row, (short) 1, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_BOTTOM); 
              //处理日 第三行
              row =sheet.createRow(2);
              cell=row.createCell(0);
              cell=row.createCell(1);
              manage = manage.replace("-", "/");
              if(inputParam.getTimesheet().equals("jp")) {
            	  cell.setCellValue("処理日 ：  "+manage);
      		}else if(inputParam.getTimesheet().equals("en")) {
      			 cell.setCellValue("處理日 ：  "+manage);
      		}else if(inputParam.getTimesheet().equals("zc")) {
      			 cell.setCellValue("处理日 ：  "+manage);
      		}else if(inputParam.getTimesheet().equals("zt")) {
      			 cell.setCellValue("處理日 ：  "+manage);
      		}
              cell.setCellStyle(bodyStyle1);
              cell=row.createCell(2);
              cell=row.createCell(3);
              cell=row.createCell(4);
              cell=row.createCell(5);
              cell=row.createCell(6);
              cell=row.createCell(7);
              cell=row.createCell(8);
            //合并单元格(4个参数，分别为起始行，结束行，起始列，结束列)
             
              //createCell(workbook, row, (short) 1, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_BOTTOM); 
              //第三、四行 空白行
              row = sheet.createRow(3);
            //合并单元格(4个参数，分别为起始行，结束行，起始列，结束列)
             
              row = sheet.createRow(4);
              //第五行 列名 
              row =sheet.createRow(5);
              row.setHeightInPoints(19);
//            	  	HSSFCellStyle style = workbook.createCellStyle();
//              		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
	            	cell = row.createCell(0);
	            	cell = row.createCell(1);
	            	
	            	 if(inputParam.getTimesheet().equals("jp")) {
	            		 cell.setCellValue("勤務日");
	         		}else if(inputParam.getTimesheet().equals("en")) {
	         			cell.setCellValue("工作日");
	         		}else if(inputParam.getTimesheet().equals("zc")) {
	         			cell.setCellValue("工作日");
	         		}else if(inputParam.getTimesheet().equals("zt")) {
	         			cell.setCellValue("工作日");
	         		}
	            	
	            	cell.setCellStyle(bodyStyle3);
	            	cell = row.createCell(2);
	            	 if(inputParam.getTimesheet().equals("jp")) {
	            		 cell.setCellValue("社員番号");
	         		}else if(inputParam.getTimesheet().equals("en")) {
	         			cell.setCellValue("職員編號");
	         		}else if(inputParam.getTimesheet().equals("zc")) {
	         			cell.setCellValue("职员编号");
	         		}else if(inputParam.getTimesheet().equals("zt")) {
	         			cell.setCellValue("職員編號");
	         		}
	            	
	            	cell.setCellStyle(bodyStyle3);
	            	cell = row.createCell(3);
	            	 if(inputParam.getTimesheet().equals("jp")) {
	            		 cell.setCellValue("社員氏名");
	         		}else if(inputParam.getTimesheet().equals("en")) {
	         			cell.setCellValue("職員姓名");
	         		}else if(inputParam.getTimesheet().equals("zc")) {
	         			cell.setCellValue("职员姓名");
	         		}else if(inputParam.getTimesheet().equals("zt")) {
	         			cell.setCellValue("職員姓名");
	         		}
	            	cell.setCellStyle(bodyStyle3);
	            	cell = row.createCell(4);
	            	 if(inputParam.getTimesheet().equals("jp")) {
	            		 cell.setCellValue("部門");
	         		}else if(inputParam.getTimesheet().equals("en")) {
	         			cell.setCellValue("部門");
	         		}else if(inputParam.getTimesheet().equals("zc")) {
	         			cell.setCellValue("部门");
	         		}else if(inputParam.getTimesheet().equals("zt")) {
	         			cell.setCellValue("部門");
	         		}
	            	 cell.setCellStyle(bodyStyle3);
	            	cell = row.createCell(5);
	            	 if(inputParam.getTimesheet().equals("jp")) {
	            		 cell.setCellValue("業務レベル");
	         		}else if(inputParam.getTimesheet().equals("en")) {
	         			cell.setCellValue("業務級別");
	         		}else if(inputParam.getTimesheet().equals("zc")) {
	         			cell.setCellValue("业务级别");
	         		}else if(inputParam.getTimesheet().equals("zt")) {
	         			cell.setCellValue("業務級別");
	         		}
	            	 cell.setCellStyle(bodyStyle3);
	            	cell = row.createCell(6);
	            	 if(inputParam.getTimesheet().equals("jp")) {
	            		 cell.setCellValue("JOBNo.");
	         		}else if(inputParam.getTimesheet().equals("en")) {
	         			cell.setCellValue("JOBNo.");
	         		}else if(inputParam.getTimesheet().equals("zc")) {
	         			cell.setCellValue("JOBNo.");
	         		}else if(inputParam.getTimesheet().equals("zt")) {
	         			cell.setCellValue("JOBNo.");
	         		}
	            	 cell.setCellStyle(bodyStyle3);
	            	cell = row.createCell(7);
	            	 if(inputParam.getTimesheet().equals("jp")) {
	            		 cell.setCellValue("JOB件名");
	         		}else if(inputParam.getTimesheet().equals("en")) {
	         			cell.setCellValue("工作名稱");
	         		}else if(inputParam.getTimesheet().equals("zc")) {
	         			cell.setCellValue("工作名称");
	         		}else if(inputParam.getTimesheet().equals("zt")) {
	         			cell.setCellValue("工作名稱");
	         		}
	            	 cell.setCellStyle(bodyStyle3);
	            	cell = row.createCell(8);
	            	 if(inputParam.getTimesheet().equals("jp")) {
	            		 cell.setCellValue("JOB時間");
	         		}else if(inputParam.getTimesheet().equals("en")) {
	         			cell.setCellValue("工作時間");
	         		}else if(inputParam.getTimesheet().equals("zc")) {
	         			cell.setCellValue("工作时间");
	         		}else if(inputParam.getTimesheet().equals("zt")) {
	         			cell.setCellValue("工作時間");
	         		}
	            	cell.setCellStyle(bodyStyle3);
	            	//至此  表头部分设置完毕
	            	String workdate = sszSheetList.get(0).getJobdate();
	            	String[] date1 = workdate.split("-");
	            	@SuppressWarnings("unused")
					String datework = null;
	            	@SuppressWarnings("unused")
					String month =date1[1];
	            	int sumdate = 0;
	            	for(int i =0;i<sszSheetList.size();i++) {
	            		//每有一条就数据创建一行
	            		row =sheet.createRow(5+(i+1+sumdate));
	            		row.setHeightInPoints(18);
	            		cell = row.createCell(0);
	            			cell = row.createCell(1);
	            			cell.setCellValue(sszSheetList.get(i).getJobdate().replace("-", "/"));
	            			cell.setCellStyle(bodyStyle3);
	            			cell = row.createCell(2);
	            			cell.setCellValue(sszSheetList.get(i).getSyname());
	            			cell.setCellStyle(bodyStyle2);
	            			cell = row.createCell(3);
	            			cell.setCellValue(sszSheetList.get(i).getUsername());
	            			cell.setCellStyle(bodyStyle2);
	            			cell = row.createCell(4);
	            			if(inputParam.getTimesheet().equals("jp")) {
	            				deptname= sszSheetList.get(i).getItmnamejp();
	            			}else if(inputParam.getTimesheet().equals("en")) {
	            				deptname = sszSheetList.get(i).getItmnameen();
	            			}else if(inputParam.getTimesheet().equals("zc")) {
	            				deptname = sszSheetList.get(i).getItmname();
	            			}else if(inputParam.getTimesheet().equals("zt")) {
	            				deptname = sszSheetList.get(i).getItmnamehk();
	            			}
	            			cell.setCellValue(deptname);
	            			cell.setCellStyle(bodyStyle2);
	            			cell = row.createCell(5);
	            			if(inputParam.getTimesheet().equals("jp")) {
	            				if(sszSheetList.get(i).getLevel().equals("001")) {
	            					profession= "所属長";
	            				}else {
	            					profession= "一般";
	            				}
	            				
	            			}else if(inputParam.getTimesheet().equals("en")) {
	            				if(sszSheetList.get(i).getLevel().equals("001")) {
	            					profession= "Manager";
	            				}else {
	            					profession= "Non-Manager";
	            				}
	            			}else if(inputParam.getTimesheet().equals("zc")) {
	            				if(sszSheetList.get(i).getLevel().equals("001")) {
	            					profession= "部门负责人";
	            				}else {
	            					profession= "一般";
	            				}
	            			}else if(inputParam.getTimesheet().equals("zt")) {
	            				if(sszSheetList.get(i).getLevel().equals("001")) {
	            					profession= "部門負責人";
	            				}else {
	            					profession= "一般";
	            				}
	            			}
	            			cell.setCellValue(profession);
	            			cell.setCellStyle(bodyStyle2);
	            			cell = row.createCell(6);
	            			cell.setCellValue(sszSheetList.get(i).getJobno());
	            			cell.setCellStyle(bodyStyle2);
	            			cell = row.createCell(7);
	            			cell.setCellValue(sszSheetList.get(i).getJobname());
	            			cell.setCellStyle(bodyStyle2);
	            			cell = row.createCell(8);
	            			
	            			cell.setCellValue(Double.parseDouble(sszSheetList.get(i).getTimeper()));
	            			cell.setCellStyle(bodyRightStyle);
	            	}
	            	//至此数据插入完毕
	            	
	                 ByteArrayOutputStream os = new ByteArrayOutputStream();
	                	workbook.write(os);
	                	byte[] buffer = os.toByteArray();
	                	return Base64.getEncoder().encodeToString(buffer);
	             }catch(Exception e){
	                 //e.printStackTrace();
	             	return "error";
	             }
		}else if(TimeSheetList.size()==0) {
			//公司名称
			String companyname = TimesheetMapper.companyname(inputParam.getCompany_cd());
			//创建HSSF工作薄
            XSSFWorkbook workbook = new XSSFWorkbook();
            //创建一个Sheet页
            XSSFSheet sheet = null;
        	sheet = workbook.createSheet("TimeSheetReport");
             //创建第一行（一般是表头）
            XSSFRow row = sheet.createRow(0);
          //创建列
            XSSFCell cell = null;
            row.setHeightInPoints(30);
          //合并单元格(4个参数，分别为起始行，结束行，起始列，结束列)
//              CellRangeAddress regionend = new CellRangeAddress(5+(sszSheetList.size()+1), 5+(sszSheetList.size()+1), 1, 7);
//              sheet.addMergedRegion(regionend);
              
              String fontName = getFontNameByLangT(inputParam.getTimesheet());
              String fontName1 = getFontNameByLangT1(inputParam.getTimesheet());
              //タイムシート集計表的
              XSSFFont titleFontRight = getExcelFont(workbook,(short)14,fontName); 
              //字体加粗
              titleFontRight.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
              XSSFFont titleFontleft = getExcelFont(workbook,(short)11,fontName); 
              //数据
              XSSFFont titleFontleft1 = getExcelFont(workbook,(short)10,fontName); 
              //HSSFFont titleFont = getExcelFont(workbook,(short)14); 
              XSSFFont bodyFont = getExcelFont(workbook,(short)1);
              XSSFCellStyle titleStyle = getExcelStyle(workbook, titleFontRight, HSSFCellStyle.VERTICAL_TOP, HSSFCellStyle.VERTICAL_BOTTOM, 0, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",false);
              @SuppressWarnings("unused")
              XSSFCellStyle outDateStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER, 0, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",false);
            //数据
              @SuppressWarnings("unused")
              XSSFCellStyle bodyStyle2 = getExcelStyle(workbook, titleFontleft1, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.ALIGN_LEFT, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",true);
              //内容头
              XSSFCellStyle bodyStyle3 = getExcelStyle(workbook, titleFontleft1, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_BOTTOM, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",true);
              @SuppressWarnings("unused")
              XSSFCellStyle bodyStyle = getExcelStyle(workbook, titleFontleft, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",true);
              //HSSFCellStyle bodyStyle1 = getExcelStyle(workbook, titleFontleft, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.ALIGN_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",true);
              //集时间 和处理日
              XSSFCellStyle bodyStyle1 = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER, 0, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",false);;
              XSSFFont eFont = workbook.createFont();
	      		eFont.setFontHeightInPoints((short) 11);
	            eFont.setFontName(fontName);
              bodyStyle1.setFont(eFont);
              @SuppressWarnings("unused")
              XSSFCellStyle bodyLeftStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",true);
              XSSFCellStyle bodyRightStyle= getStyle(workbook,HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index);
              @SuppressWarnings("unused")
              XSSFFont eFont1 = workbook.createFont();
	      		eFont.setFontHeightInPoints((short) 10);
	            eFont.setFontName(fontName);
	            bodyRightStyle.setFont(eFont);
              XSSFCellStyle titles = workbook.createCellStyle();
              titles.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
              XSSFFont eFont2 = workbook.createFont();
	      		eFont2.setFontHeightInPoints((short) 11);
	            eFont2.setFontName(fontName1);
	            titles.setFont(eFont2);
              sheet.setColumnWidth(1, 15*256);
              sheet.setColumnWidth(2, 15*256);
              sheet.setColumnWidth(3, 15*256);
              sheet.setColumnWidth(4, 15*256);
              sheet.setColumnWidth(5, 15*256);
              sheet.setColumnWidth(6, 15*256);
              sheet.setColumnWidth(7, 15*256);
              sheet.setColumnWidth(8, 15*256);
              //设置表头
            for (int i = 0; i <9 ; i++) {
                cell=row.createCell(i);
                if(i==0){
                	sheet.setColumnWidth(0, 2*256);
                }
                if(i==1) {
                	   cell.setCellValue(companyname);
                	   cell.setCellStyle(titles);
                }
                if(i==4) {
                	
                	if(inputParam.getTimesheet().equals("jp")) {
                		cell.setCellValue("タイムシート集計表");
            		}else if(inputParam.getTimesheet().equals("en")) {
            			cell.setCellValue("時間表總計表");
            		}else if(inputParam.getTimesheet().equals("zc")) {
            			cell.setCellValue("时间表总计表");
            		}else if(inputParam.getTimesheet().equals("zt")) {
            			cell.setCellValue("時間表總計表");
            		}
                	cell.setCellStyle(titleStyle);
                }
            }
              //集計期間 第二行
              String stdate = inputParam.getDlvmon_sta().replace("-","/");
              String etdate = inputParam.getDlvmon_end().replace("-","/");
               row =sheet.createRow(1);
               cell=row.createCell(0);
               cell=row.createCell(1);
               
               if(inputParam.getTimesheet().equals("jp")) {
            	   cell.setCellValue("集計期間  :   "+stdate+"-"+etdate);
       		}else if(inputParam.getTimesheet().equals("en")) {
       			cell.setCellValue("合計期間   :   "+stdate+"-"+etdate);
       		}else if(inputParam.getTimesheet().equals("zc")) {
       			cell.setCellValue("合计期间  :   "+stdate+"-"+etdate);
       		}else if(inputParam.getTimesheet().equals("zt")) {
       			cell.setCellValue("合計期間  :   "+stdate+"-"+etdate);
       		}
              cell.setCellStyle(bodyStyle1);
              cell=row.createCell(2);
              cell=row.createCell(3);
             cell=row.createCell(4);
              cell=row.createCell(5);
             cell=row.createCell(6);
              cell=row.createCell(7);
              cell=row.createCell(8);
              
              
             //createCell(workbook, row, (short) 1, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_BOTTOM); 
              //处理日 第三行
              row =sheet.createRow(2);
              cell=row.createCell(0);
              cell=row.createCell(1);
              manage = manage.replace("-", "/");
              if(inputParam.getTimesheet().equals("jp")) {
            	  cell.setCellValue("処理日 ：  "+manage);
      		}else if(inputParam.getTimesheet().equals("en")) {
      			 cell.setCellValue("處理日 ：  "+manage);
      		}else if(inputParam.getTimesheet().equals("zc")) {
      			 cell.setCellValue("处理日 ：  "+manage);
      		}else if(inputParam.getTimesheet().equals("zt")) {
      			 cell.setCellValue("處理日 ：  "+manage);
      		}
              cell.setCellStyle(bodyStyle1);
              cell=row.createCell(2);
              cell=row.createCell(3);
              cell=row.createCell(4);
              cell=row.createCell(5);
              cell=row.createCell(6);
              cell=row.createCell(7);
              cell=row.createCell(8);
            //合并单元格(4个参数，分别为起始行，结束行，起始列，结束列)
             
              //createCell(workbook, row, (short) 1, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_BOTTOM); 
              //第三、四行 空白行
              row = sheet.createRow(3);
            //合并单元格(4个参数，分别为起始行，结束行，起始列，结束列)
             
              row = sheet.createRow(4);
              //第五行 列名 
              row =sheet.createRow(5);
              row.setHeightInPoints(19);
//            	  	HSSFCellStyle style = workbook.createCellStyle();
//              		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
	            	cell = row.createCell(0);
	            	cell = row.createCell(1);
	            	
	            	 if(inputParam.getTimesheet().equals("jp")) {
	            		 cell.setCellValue("勤務日");
	         		}else if(inputParam.getTimesheet().equals("en")) {
	         			cell.setCellValue("工作日");
	         		}else if(inputParam.getTimesheet().equals("zc")) {
	         			cell.setCellValue("工作日");
	         		}else if(inputParam.getTimesheet().equals("zt")) {
	         			cell.setCellValue("工作日");
	         		}
	            	
	            	cell.setCellStyle(bodyStyle3);
	            	cell = row.createCell(2);
	            	 if(inputParam.getTimesheet().equals("jp")) {
	            		 cell.setCellValue("社員番号");
	         		}else if(inputParam.getTimesheet().equals("en")) {
	         			cell.setCellValue("職員編號");
	         		}else if(inputParam.getTimesheet().equals("zc")) {
	         			cell.setCellValue("职员编号");
	         		}else if(inputParam.getTimesheet().equals("zt")) {
	         			cell.setCellValue("職員編號");
	         		}
	            	
	            	cell.setCellStyle(bodyStyle3);
	            	cell = row.createCell(3);
	            	 if(inputParam.getTimesheet().equals("jp")) {
	            		 cell.setCellValue("社員氏名");
	         		}else if(inputParam.getTimesheet().equals("en")) {
	         			cell.setCellValue("職員姓名");
	         		}else if(inputParam.getTimesheet().equals("zc")) {
	         			cell.setCellValue("职员姓名");
	         		}else if(inputParam.getTimesheet().equals("zt")) {
	         			cell.setCellValue("職員姓名");
	         		}
	            	cell.setCellStyle(bodyStyle3);
	            	cell = row.createCell(4);
	            	 if(inputParam.getTimesheet().equals("jp")) {
	            		 cell.setCellValue("部門");
	         		}else if(inputParam.getTimesheet().equals("en")) {
	         			cell.setCellValue("部門");
	         		}else if(inputParam.getTimesheet().equals("zc")) {
	         			cell.setCellValue("部门");
	         		}else if(inputParam.getTimesheet().equals("zt")) {
	         			cell.setCellValue("部門");
	         		}
	            	 cell.setCellStyle(bodyStyle3);
	            	cell = row.createCell(5);
	            	 if(inputParam.getTimesheet().equals("jp")) {
	            		 cell.setCellValue("業務レベル");
	         		}else if(inputParam.getTimesheet().equals("en")) {
	         			cell.setCellValue("業務級別");
	         		}else if(inputParam.getTimesheet().equals("zc")) {
	         			cell.setCellValue("业务级别");
	         		}else if(inputParam.getTimesheet().equals("zt")) {
	         			cell.setCellValue("業務級別");
	         		}
	            	 cell.setCellStyle(bodyStyle3);
	            	cell = row.createCell(6);
	            	 if(inputParam.getTimesheet().equals("jp")) {
	            		 cell.setCellValue("JOBNo.");
	         		}else if(inputParam.getTimesheet().equals("en")) {
	         			cell.setCellValue("JOBNo.");
	         		}else if(inputParam.getTimesheet().equals("zc")) {
	         			cell.setCellValue("JOBNo.");
	         		}else if(inputParam.getTimesheet().equals("zt")) {
	         			cell.setCellValue("JOBNo.");
	         		}
	            	 cell.setCellStyle(bodyStyle3);
	            	cell = row.createCell(7);
	            	 if(inputParam.getTimesheet().equals("jp")) {
	            		 cell.setCellValue("JOB件名");
	         		}else if(inputParam.getTimesheet().equals("en")) {
	         			cell.setCellValue("工作名稱");
	         		}else if(inputParam.getTimesheet().equals("zc")) {
	         			cell.setCellValue("工作名称");
	         		}else if(inputParam.getTimesheet().equals("zt")) {
	         			cell.setCellValue("工作名稱");
	         		}
	            	 cell.setCellStyle(bodyStyle3);
	            	cell = row.createCell(8);
	            	 if(inputParam.getTimesheet().equals("jp")) {
	            		 cell.setCellValue("JOB時間");
	         		}else if(inputParam.getTimesheet().equals("en")) {
	         			cell.setCellValue("工作時間");
	         		}else if(inputParam.getTimesheet().equals("zc")) {
	         			cell.setCellValue("工作时间");
	         		}else if(inputParam.getTimesheet().equals("zt")) {
	         			cell.setCellValue("工作時間");
	         		}
	            	cell.setCellStyle(bodyStyle3);
	                 ByteArrayOutputStream os = new ByteArrayOutputStream();
	                	try {
							workbook.write(os);
							byte[] buffer = os.toByteArray();
		                	return Base64.getEncoder().encodeToString(buffer);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		}
		return "1";
	}
	private XSSFFont getExcelFont(XSSFWorkbook workbook,short fontSize)
	{
		XSSFFont eFont = workbook.createFont();
		eFont.setFontHeightInPoints(fontSize);
        return eFont;
	}
	//用于设置单元格对其样式
	@SuppressWarnings("unused")
	private static void createCell(Workbook wb, Row row, short column, short halign, short valign) {
		Cell cell = row.createCell(column);
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(halign);
        cellStyle.setVerticalAlignment(valign);
        cell.setCellStyle(cellStyle);
	}
	private XSSFCellStyle getExcelStyle(XSSFWorkbook workbook,XSSFFont titleFontRight,short lrAlign, 
			short tbAlign, int isBoder,short border,short color,HSSFDataFormat format,String formatStr,boolean wrapT)
	{
		XSSFCellStyle style = workbook.createCellStyle();
		if(titleFontRight != null)
		{
			style.setFont(titleFontRight);
		}
		if(isBoder == 1)
		{
			style.setBorderBottom(border);
			style.setBorderTop(border);
			style.setBorderLeft(border);
			style.setBorderRight(border);
			style.setLeftBorderColor(color);
			style.setRightBorderColor(color);
			style.setTopBorderColor(color);
			style.setBottomBorderColor(color);
			style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		}
		style.setAlignment(lrAlign); // 创建一个居中格式
        style.setVerticalAlignment(tbAlign);
        style.setWrapText(wrapT);
        if(format != null)
        {
        	style.setDataFormat(format.getFormat(formatStr));
        }else {
        	XSSFDataFormat format1= workbook.createDataFormat();
        	style.setDataFormat(format1.getFormat("@"));
        }
		return style;
	}
	private XSSFCellStyle getStyle(XSSFWorkbook workbook,short border,short color)
	{
		XSSFCellStyle style = workbook.createCellStyle();
		style.setBorderBottom(border);
		style.setBorderTop(border);
		style.setBorderLeft(border);
		style.setBorderRight(border);
		style.setLeftBorderColor(color);
		style.setRightBorderColor(color);
		style.setTopBorderColor(color);
		style.setBottomBorderColor(color);
		style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		style.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
		
		return style;
	}

	private String getFontNameByLangT(String langT)
	{
		//en,jp,zc,zt
		String fontName = "MS PGothic";
		//langT="jp";//测试默认使用，正式使用时删除
		if(langT.equals("en"))
		{
			fontName = "宋体";
		}else if(langT.equals("jp"))
		{
			fontName = "MS PGothic";
		}else if(langT.equals("zc"))
		{
			fontName = "宋体";
		}else if(langT.equals("zt"))
		{
			fontName = "宋体";
		}else {
			fontName = "MS PGothic";
		}
		return fontName;
	}
	private String getFontNameByLangT1(String langT)
	{
		//en,jp,zc,zt
		String fontName = "MS PGothic";
		//langT="jp";//测试默认使用，正式使用时删除
		if(langT.equals("en"))
		{
			fontName = "宋体";
		}else if(langT.equals("jp"))
		{
			fontName = "MS PGothic";
		}else if(langT.equals("zc"))
		{
			fontName = "宋体";
		}else if(langT.equals("zt"))
		{
			fontName = "宋体";
		}else {
			fontName = "MS PGothic";
		}
		return fontName;
	}
	private XSSFFont getExcelFont(XSSFWorkbook workbook,short fontSize,String fontName)
	{
		XSSFFont eFont = workbook.createFont();
		eFont.setFontHeightInPoints(fontSize);
        eFont.setFontName(fontName);
        return eFont;
	}
	public boolean isHavePower(List<Role> roleList,int roleID) {
		boolean flg = false;
		for(Role role : roleList) {
			if(roleID==role.getNodeID()) {
				flg = true;
				break;
			}
		}
		return flg;
	}
}
