package com.kaiwait.utils.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {

	public static String getCurrentDateyyyyMMddHHmmss() {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sf.format(new Date());
	}

	public static String getCurrentDateYYYYMMDD() {
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
		return sf.format(new Date());
	}

	public static Date paseDateyyyyMMddHHmmss(String strDate)
			throws ParseException {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = sf.parse(strDate);
		return date;
	}

	/**
	 * 把月份控件获取出来的值转换成YYYYMMDD格式
	 * 
	 */
	public static String paseDateYYYYMMToDateYYYYMMDD(String time) {
		return time.substring(0, 10).replaceAll("-", "");
	}

	/**
	 * 把YYYYMMDD转换成YYYY-MM-DD HH:mm:ss
	 */
	public static String paseDateYYYYMMToDateYYYYMMDDHHMMSS(String time) throws ParseException{
		SimpleDateFormat sf1 = new SimpleDateFormat("yyyyMMddHHmmss");
	    SimpleDateFormat sf2 =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	     
	    return sf2.format(sf1.parse(time+"000000"));
	}
	
	/**
	 * 把YYYY-MM-DD HH:mm:ss转换成YYYYMMDDHHmmss
	 */
	public static String paseDateYYYYMMDDHHMMSS(String time) throws ParseException{
		SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    SimpleDateFormat sf2 =new SimpleDateFormat("yyyyMMddHHmmss");
	     
	    return sf2.format(sf1.parse(time));
	}

	/**
	 * 把YYYY-MM-DD hh:mm:ss转换成YYYYMMDD
	 * 
	 */
	public static String paseDateYYYYMMDDHHmmssToDateYYYYMMDD(String time) {
		return time.substring(0, 10).replaceAll("-", "");
	}

	/***
	 * 判断和当前日期是否相差60秒
	 * 
	 */
	public static boolean compareDate(String strDate) throws ParseException {
		if (StringUtil.isNotEmpty(strDate)) {
			Date date = paseDateyyyyMMddHHmmss(strDate);
			Date currDate = new Date();
			if (((currDate.getTime() / 1000) - (date.getTime() / 1000)) < 60) {
				// 相差值在60秒之内
				return false;
			}
		}
		return true;
	}

	/****
	 * 验证日期是否大于当前日期
	 * 
	 */
	public static boolean compareInpAndCurr(String strDate)
			throws ParseException {
		Date date = paseDateyyyyMMddHHmmss(strDate);
		Date currDate = new Date();
		if (date.getTime() > currDate.getTime()) {
			return true;
		}
		return false;
	}
	
	/****
	 * 验证日期是否小于当前日期
	 * 
	 */
	public static boolean checkInputTimeThanCurrDate(String strDate)
			throws ParseException {
		Date date = paseDateyyyyMMddHHmmss(strDate);
		Date currDate = new Date();
		if (date.getTime() < currDate.getTime()) {
			return true;
		}
		return false;
	}

	/****
	 * 根据指定format，将Date型日期转换成字符型日期
	 * 
	 */
	public static String getDateByFormat(String format, Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	/****
	 * 根据指定format，将字符型日期转换成字符型日期
	 * 
	 */
	public static String getDateByFormat(String format1, String format2,
			String strDate) {
		try {
			if (StringUtil.isNotEmpty(format1)
					&& StringUtil.isNotEmpty(format2)
					&& StringUtil.isNotEmpty(strDate)) {

				SimpleDateFormat sdf = new SimpleDateFormat(format1);
				Date date = sdf.parse(strDate);
				return getDateByFormat(format2, date);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 获取当前时分秒
	 */
	public static String getCurrentHHmmss() {
		return getDateByFormat("HHmmss", new Date());
	}

	/******
	 * 系统日期比对象日期晚几日
	 */
	public static long getQuot(String objectDt) {
		if (null == objectDt) {
			return 0;
		}
		long quot = 0;
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date today = new Date();
			today= ft.parse(ft.format(today));
			Date date2 = ft.parse(objectDt);
			quot = (today.getTime() - date2.getTime()) / (24 * 60 * 60 * 1000);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return quot;
	}
	
	public static String getYearMonthDay(String str){
		    
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			 try {
				Date date = sf.parse(str);
				if(null!=date){
					return sf.format(date);
					}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
			
		return "";
	}
	
	
	/**
	 * 对时间进行按数据库格式转换:
	 * 把YYYY-MM-DD HH:mm:ss转换成YYYYMMDDHHmmss
	 */
	public static String converDate1(String dateStr) {
		String string = "";
		// 对时间进行按数据库格式转换:
		SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMddHHmmss");
		SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			if (StringUtil.isNotBlank(dateStr)) {
				string = format1.format(format2.parse(dateStr));
			}
		} catch (ParseException e) {

			e.printStackTrace();
		}
		
		return string;
	}
	
	/**
	 * 转换时间为页面显示的格式
	 * 把YYYYMMDDHHmmss转换成YYYY-MM-DD HH:mm:ss
	 */
	public static String converDate2(String dateStr) {
		String string = "";
		// 对时间进行按数据库格式转换:
		SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMddHHmmss");
		SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			if (StringUtil.isNotBlank(dateStr)){
				string = format2.format(format1.parse(dateStr));
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		return string;
	}

	/**
	 * 支付时间 : "yyyy-MM-dd HH:mm:ss" 转换成Date
	 */
	public static Date converDate3(String dateStr){
		Date date = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 try {
			date = format.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		 return date;
		
	}

}
