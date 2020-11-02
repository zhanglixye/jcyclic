package com.kaiwait.core.utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Locale;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.query.JsonQueryExecuterFactory;

public class JasperOutPdfUtil {
	//public static final String JRXMLPATH = "D:\\\\jspertxmls_jczh\\";
	public static final String JRXMLPATH = "/opt/jspertxmls_jczh/";
	public static final String BUNDLE_NAME = "jcyclicExcelBundle";
	public static Locale getLocalByLangT(String langT)
	{
		//en,jp,zc,zt
		Locale local = null;
		//langT="jp";//测试默认使用，正式使用时删除
		if(langT.equals("en"))
		{
			local = new Locale("en", "US"); 
		}else if(langT.equals("jp"))
		{
			local = new Locale("ja", "JP");
		}else if(langT.equals("zc"))
		{
			local = new Locale("zh", "CN");
		}else if(langT.equals("zt"))
		{
			local = new Locale("zh", "TW");
		}else {
			local = new Locale("ja", "JP");
		}
		return local;
	}
	public static String outPutPdfWithJson(String dataJson,String pdfName,String langTyp) throws JRException, UnsupportedEncodingException
	{
		//try {
			// 查询得到的person实体列表
			byte[] bytes = null;
			//String jasper =PDF_PATH + pdfName + ".jasper";
			String jasperPath = JRXMLPATH + pdfName + ".jrxml";
			// 编译模板文件变成类
			//使用jrxml
			JasperReport jasperReport = (JasperReport)JasperCompileManager.compileReport(jasperPath);
			// 将Collection填充到模板文件类中
			InputStream is = new ByteArrayInputStream(dataJson.getBytes("UTF-8"));
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put(JsonQueryExecuterFactory.JSON_LOCALE, Locale.ENGLISH);
			if(langTyp.equals("jp"))
			{
				parameters.put(JRParameter.REPORT_LOCALE, Locale.JAPAN);
			}
			if(langTyp.equals("en"))
			{
				//parameters.put(JRParameter.REPORT_LOCALE, Locale.ENGLISH);
				parameters.put(JRParameter.REPORT_LOCALE, Locale.US);
			}
			if(langTyp.equals("zc"))
			{
				parameters.put(JRParameter.REPORT_LOCALE, Locale.CHINA);
			}
			if(langTyp.equals("zt"))
			{
				parameters.put(JRParameter.REPORT_LOCALE, Locale.TAIWAN);
			}
			
			parameters.put("JSON_INPUT_STREAM", is);
			
			/*使用jasper出报表
			String jasperFile = JasperFillManager.fillReportToFile(jasperPath, parameters);
			JasperPrint jasperPrint = (JasperPrint)JRLoader.loadObjectFromFile(jasperFile);
			*/
			//使用jrxml
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters);
			
			
			bytes = JasperExportManager.exportReportToPdf(jasperPrint);
			return Base64.getEncoder().encodeToString(bytes);
		//} catch (Exception e) {
		//	return "error";
		//}
	}
	public static String outPutPdfWithJavaBean(JRBeanCollectionDataSource dataSource,String pdfName,String langTyp) throws JRException
	{
		//try {
			// 查询得到的person实体列表
			byte[] bytes = null;
			String jasperPath = JRXMLPATH + pdfName + ".jrxml";
			// 编译模板文件变成类
			JasperReport jasperReport = (JasperReport)JasperCompileManager.compileReport(jasperPath);
			// 将Collection填充到模板文件类中
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put(JsonQueryExecuterFactory.JSON_LOCALE, Locale.ENGLISH);
			if(langTyp.equals("jp"))
			{
				parameters.put(JRParameter.REPORT_LOCALE, Locale.JAPAN);
			}
			if(langTyp.equals("en"))
			{
				//parameters.put(JRParameter.REPORT_LOCALE, Locale.ENGLISH);
				parameters.put(JRParameter.REPORT_LOCALE, Locale.US);
			}
			if(langTyp.equals("zc"))
			{
				parameters.put(JRParameter.REPORT_LOCALE, Locale.CHINA);
			}
			if(langTyp.equals("zt"))
			{
				parameters.put(JRParameter.REPORT_LOCALE, Locale.TAIWAN);
			}
			
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,dataSource);
			
			
			bytes = JasperExportManager.exportReportToPdf(jasperPrint);
			return Base64.getEncoder().encodeToString(bytes);
		//} catch (Exception e) {
		//	return "error:--------"+e.getMessage();
		//}
	}
	
	//test pdf insert to mysql
	public static byte[] getPdfBytesWithJavaBean(JRBeanCollectionDataSource dataSource,String pdfName,String langTyp) throws JRException
	{
		//try {
			// 查询得到的person实体列表
			byte[] bytes = null;
			String jasperPath = JRXMLPATH + pdfName + ".jrxml";
			// 编译模板文件变成类
			JasperReport jasperReport = (JasperReport)JasperCompileManager.compileReport(jasperPath);
			// 将Collection填充到模板文件类中
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put(JsonQueryExecuterFactory.JSON_LOCALE, Locale.ENGLISH);
			if(langTyp.equals("jp"))
			{
				parameters.put(JRParameter.REPORT_LOCALE, Locale.JAPAN);
			}
			if(langTyp.equals("en"))
			{
				//parameters.put(JRParameter.REPORT_LOCALE, Locale.ENGLISH);
				parameters.put(JRParameter.REPORT_LOCALE, Locale.US);
			}
			if(langTyp.equals("zc"))
			{
				parameters.put(JRParameter.REPORT_LOCALE, Locale.CHINA);
			}
			if(langTyp.equals("zt"))
			{
				parameters.put(JRParameter.REPORT_LOCALE, Locale.TAIWAN);
			}
			
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,dataSource);
			
			
			bytes = JasperExportManager.exportReportToPdf(jasperPrint);
			return bytes;
		//} catch (Exception e) {
		//	return "error:--------"+e.getMessage();
		//}
	}
	
	public static String outPutPdfWithJavaBean(JRBeanCollectionDataSource dataSource,String pdfName,String langTyp,HashMap<String, Object> parameters ) throws JRException
	{
		//try {
			// 查询得到的person实体列表
			byte[] bytes = null;
			String jasperPath = JRXMLPATH + pdfName + ".jrxml";
			// 编译模板文件变成类
			JasperReport jasperReport = (JasperReport)JasperCompileManager.compileReport(jasperPath);
			// 将Collection填充到模板文件类中
		//	HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put(JsonQueryExecuterFactory.JSON_LOCALE, Locale.ENGLISH);
			if(langTyp.equals("jp"))
			{
				parameters.put(JRParameter.REPORT_LOCALE, Locale.JAPAN);
			}
			if(langTyp.equals("en"))
			{
				//parameters.put(JRParameter.REPORT_LOCALE, Locale.ENGLISH);
				parameters.put(JRParameter.REPORT_LOCALE, Locale.US);
			}
			if(langTyp.equals("zc"))
			{
				parameters.put(JRParameter.REPORT_LOCALE, Locale.CHINA);
			}
			if(langTyp.equals("zt"))
			{
				parameters.put(JRParameter.REPORT_LOCALE, Locale.TAIWAN);
			}
			
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,dataSource);
			
			
			bytes = JasperExportManager.exportReportToPdf(jasperPrint);
			return Base64.getEncoder().encodeToString(bytes);
		//} catch (Exception e) {
		//	return "error:"+e.getMessage();
		//}
	}
}
