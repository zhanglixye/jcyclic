package com.kaiwait.core.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.kaiwait.utils.common.StringUtil;


public class DateUtil{

	public static String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";
	public static String dateFormat = "yyyy-MM-dd";
	public static String dateFormatYeayMonth = "yyyyMM";
	public static String accountDateFormat = "yyyy/MM";
	public static String ACCOUNT_MONTH= "yyyy-MM";
	
	public static String getDateForNow(String str) {
		SimpleDateFormat dformat = new SimpleDateFormat(str); 
		Date dt = new Date();
        return dformat.format(dt);
	} 
	public static Date stringtoDate(String dt,String str)
	{   
		SimpleDateFormat dformat = new SimpleDateFormat(str); 
		if(dt == null || dt.equals(""))
		{
			return null;
		}
		try {
			return dformat.parse(dt);
		} catch (Exception e) {
			return null;
		}
	}
	public static String dateToString(Date dt,String str)
	{ 
		SimpleDateFormat dformat = new SimpleDateFormat(str); 
		if(dt == null)
		{
			return null;
		}
		return dformat.format(dt);
	}
	public static String makeDate(String dt)
	{
		if(dt == null)
		{
			return null;
		}else {
			Calendar date = Calendar.getInstance();
			date.setTime(DateUtil.stringtoDate(dt,dateFormat));
			date.set(Calendar.MONTH, date.get(Calendar.MONTH) - 1);
			SimpleDateFormat df = new SimpleDateFormat(dateFormatYeayMonth); 
			return df.format(date.getTime());
		}
	}
	public static String nextMotnDate(String dt)
	{
		if(dt == null)
		{
			return null;
		}else {
			Calendar date = Calendar.getInstance();
			date.setTime(DateUtil.stringtoDate(dt,dateFormat));
			date.set(Calendar.MONTH, date.get(Calendar.MONTH) + 1);
			SimpleDateFormat df = new SimpleDateFormat(dateFormat); 
			return df.format(date.getTime());
		}
	}
	public static String getAddNumMotnDate(String dt,int nMonth,String dFormat)
	{
		if(dt == null)
		{
			return null;
		}else {
			Calendar date = Calendar.getInstance();
			date.setTime(DateUtil.stringtoDate(dt,dateFormat));
			date.set(Calendar.MONTH, date.get(Calendar.MONTH) + nMonth);
			SimpleDateFormat df = new SimpleDateFormat(dFormat); 
			return df.format(date.getTime());
		}
	}
	public static String nextDayDate(String dt)
	{
		if(dt == null)
		{
			return null;
		}else {
			Calendar date = Calendar.getInstance();
			date.setTime(DateUtil.stringtoDate(dt,dateFormat));
			date.set(Calendar.DATE, date.get(Calendar.DATE) - 1);
			SimpleDateFormat df = new SimpleDateFormat(dateFormat); 
			return df.format(date.getTime());
		}
	}

	public static String getNewTime(String dt, int num) {
		if (dt == null) {
			return null;
		} else {
			Calendar nowTime = Calendar.getInstance();
			nowTime.setTime(DateUtil.stringtoDate(dt, dateTimeFormat));
			nowTime.add(Calendar.HOUR, num);// n小时后/前的时间
			SimpleDateFormat df = new SimpleDateFormat(dateTimeFormat); 
			return df.format(nowTime.getTime());
		}
	}
	public static String getNewTime(String dt, int num,String formatStr) {
		if (dt == null) {
			return null;
		} else {
			if(formatStr.equals(""))
			{
				formatStr = dateTimeFormat;
			}
			Calendar nowTime = Calendar.getInstance();
			nowTime.setTime(DateUtil.stringtoDate(dt, formatStr));
			nowTime.add(Calendar.HOUR, num);// n小时后/前的时间
			SimpleDateFormat df = new SimpleDateFormat(formatStr); 
			return df.format(nowTime.getTime());
		}
	}
	public static void main(String[] args) {
//		String s1 = "2018-07-20 14:31:56";
//		int s2 = -15;
//		String time = getNewTime(s1,s2);
//		System.out.println(time);
//		String str = "1111111111111111";
//		String regEx = "(\\d{1,15})";
//		Pattern pattern = Pattern.compile(regEx);
//		Matcher matcher = pattern.matcher(str);
//		if(!matcher.matches())
//		{
//			System.out.println("eeeeeeeeeeeeeeeeeeeee");
//		}else {
//			System.out.println("sssssssssssssssssss");
//		}
	}
	public static String getNowTime() {
		Date day=new Date();
		SimpleDateFormat df = new SimpleDateFormat(dateTimeFormat); 
		String datenow = df.format(day);
		return datenow;
	}
	public static String getNowDate() {
		Date day=new Date();
		SimpleDateFormat df = new SimpleDateFormat(dateTimeFormat); 
		String datenow = df.format(day);
		return datenow;
	}
	public static String getAccountMonth(String str) {
		SimpleDateFormat dformat = new SimpleDateFormat(DateUtil.ACCOUNT_MONTH); 
		String date = dformat.format(str);
		return date;
	}
	public static String getDate() {
		Date day=new Date();
		SimpleDateFormat df = new SimpleDateFormat(dateFormat); 
		String datenow = df.format(day);
		return datenow;
	}
}

