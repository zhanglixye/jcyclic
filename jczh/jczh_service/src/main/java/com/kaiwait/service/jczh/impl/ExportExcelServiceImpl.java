package com.kaiwait.service.jczh.impl;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.kaiwait.bean.jczh.entity.AccountEntries;
import com.kaiwait.bean.jczh.entity.Calculate;
import com.kaiwait.bean.jczh.entity.Clmst;
import com.kaiwait.bean.jczh.entity.Cost;
import com.kaiwait.bean.jczh.entity.ExcelReport;
import com.kaiwait.bean.jczh.entity.Role;
import com.kaiwait.bean.jczh.io.OutPutInput;
import com.kaiwait.bean.jczh.vo.CommonmstVo;
import com.kaiwait.core.utils.DateUtil;
import com.kaiwait.core.utils.JasperOutPdfUtil;
import com.kaiwait.core.utils.MathController;
import com.kaiwait.mappers.jczh.AccountEntriesMapper;
import com.kaiwait.mappers.jczh.CommonMethedMapper;
import com.kaiwait.mappers.jczh.ExportExcelMapper;
import com.kaiwait.mappers.jczh.JobLandMapper;
import com.kaiwait.service.jczh.CommonMethedService;
import com.kaiwait.service.jczh.ExportExcelService;
import com.kaiwait.utils.common.StringUtil;


@Service
public class ExportExcelServiceImpl implements ExportExcelService {
	
	@Resource
	private ExportExcelMapper exportExcelMapper;
	@Resource
	private CommonMethedService commonMethedService;
	@Resource
	private CommonMethedMapper commonMethedMapper;
	@Resource
	private JobLandMapper jobLandMapper;
	@Resource
	private AccountEntriesMapper accountEntriesMapper;
	//private String bundleNmae = "jcyclicExcelBundle";
	
	public String getFromJppMaxAccountMonth(String companyID)
	{
		try {
			return exportExcelMapper.getFromJppMaxAccountMonth(companyID);
		} catch (Exception e) {
			return "error";
		}
	}
	public boolean isExportExcelFlg(OutPutInput pars)
	{
		int flg = exportExcelMapper.isExportExcelFlg(pars.getEndDate(),pars.getCompanyID());
		if(flg == 0)
		{
			return false;
		}
		return true;
	}
	private int convertHexToNumber(String hex) {
        int num = 0;
        try {
            num = Integer.parseInt(hex, 16);
        } catch (Throwable t) {
           //100;
        }
        return num;
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
	/**
	* 方法名 getLevelNameByLangT
	* 方法的说明  根据语言返回部门的相应语言
	* @param String langT    语言区分
	* @param Role role       部门名字对象
	* @return String 
	* @author 王岩
	* @date 2019.01.11
	*/
	private String getDepartNameByLangT(String langT,Role role)
	{
		String departName = "";
		if(langT.equals("en"))
		{
			departName = role.getDepartNameen();
		}else if(langT.equals("jp"))
		{
			departName = role.getDepartNamejp();
		}else if(langT.equals("zc"))
		{
			departName = role.getDepartNamezc();
		}else if(langT.equals("zt"))
		{
			departName = role.getDepartNamezt();
		}
		return departName;
	}
	/**
	* 方法名 getLevelNameByLangT
	* 方法的说明  根据语言返回级别的相应语言
	* @param String langT    语言区分
	* @param Role role       级别名字对象
	* @return String 
	* @author 王岩
	* @date 2019.01.11
	*/
	private String getLevelNameByLangT(String langT,Role role)
	{
		String levelName = "";
		if(langT.equals("en"))
		{
			levelName = role.getLevelNameen();
		}else if(langT.equals("jp"))
		{
			levelName = role.getLevelNamejp();
		}else if(langT.equals("zc"))
		{
			levelName = role.getLevelNamezc();
		}else if(langT.equals("zt"))
		{
			levelName = role.getLevelNamezt();
		}
		return levelName;
	}
	/**
	* 方法名 getExcelStyle
	* 方法的说明  返回excel样式对象
	* @param HSSFWorkbook workbook      excel页对象
	* @param HSSFFont efont             字体对象
	* @param short lrAlign   			左右对齐方式
	* @param short tbAlign  			上下对象方式
	* @param int isBoder     			是否需要边框
	* @param short border    			边框数值
	* @param short color     			边框颜色
	* @param HSSFDataFormat format 		格式化对象
	* @param String formatStr  			格式化样式
	* @param boolean wrapT 				是否自动换行
	* @return HSSFCellStyle 
	* @author 王岩
	* @date 2019.01.11
	*/
	private XSSFCellStyle getExcelStyle(XSSFWorkbook workbook,XSSFFont titleFont,short lrAlign, 
			short tbAlign, int isBoder,short border,short color,XSSFDataFormat format,String formatStr,boolean wrapT)
	{
		XSSFCellStyle style = workbook.createCellStyle();
		if(titleFont != null)
		{
			style.setFont(titleFont);
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
	private XSSFFont getExcelFont(XSSFWorkbook workbook,short fontSize,String fontName)
	{
		XSSFFont eFont = workbook.createFont();
		eFont.setFontHeightInPoints(fontSize);
        eFont.setFontName(fontName);
        return eFont;
	}
	
	public String exportRoleReport(OutPutInput pars) {
		List<Role> nodeList = exportExcelMapper.getNodes();
		List<Role> nodeListByUser = exportExcelMapper.getNodesByRoles(pars);
		String companyFullName = "";
		if(nodeListByUser.size() < 1)
		{
			companyFullName = exportExcelMapper.getCompanyName(pars.getCompanyID());
		}else {
			companyFullName = nodeListByUser.get(0).getCompanyFullName();
		}
		Locale local = JasperOutPdfUtil.getLocalByLangT(pars.getLangTyp());
		ResourceBundle bundle = ResourceBundle.getBundle(JasperOutPdfUtil.BUNDLE_NAME , local);
		
		String fontName = getFontNameByLangT(pars.getLangTyp());
		
		String timeByZ = commonMethedService.getTimeByZone(DateUtil.getDateForNow(DateUtil.dateTimeFormat), pars.getCompanyID());
		try{
        	// 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        	//HSSFWorkbook workbook = new HSSFWorkbook();
			XSSFWorkbook workbook = new XSSFWorkbook();
        	// 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        	//HSSFSheet sheet = workbook.createSheet(pars.getFileName());
			//HSSFSheet sheet = workbook.createSheet(pars.getFileName());
			XSSFSheet sheet = workbook.createSheet();
			workbook.setSheetName(0,pars.getSheetName());
        	// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
            CellRangeAddress cra=new CellRangeAddress(0, 0, 0, (nodeList.size()+3));
            sheet.addMergedRegion(cra);
            CellRangeAddress crd=new CellRangeAddress(1, 1, 0, 2);
            sheet.addMergedRegion(crd);
            
            XSSFFont titleFont = getExcelFont(workbook,(short)14,fontName); 
            XSSFFont bodyFont = getExcelFont(workbook,(short)10,fontName);
            
            XSSFCellStyle titleStyle = getExcelStyle(workbook, titleFont, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER, 0, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",false);
            XSSFCellStyle outDateStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER, 0, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",false);
            XSSFCellStyle bodyStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",true);
            XSSFCellStyle bodyLeftStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",true);
            
            
            XSSFCell cell = null;
            String colorStr = "";
            
            String nodeStr = "";
            String[] nodeStrArr;
            XSSFRow row = sheet.createRow(0);
            //0行  title
            row.setHeightInPoints(30);
            cell = row.createCell(0);
            cell.setCellValue(bundle.getString("empPowerTitle")+"("+companyFullName+")");
            cell.setCellStyle(titleStyle);
            //1行  输出日行
            //row.setHeightInPoints(30);
            row = sheet.createRow(1);
            cell = row.createCell(0);
            cell.setCellValue(bundle.getString("outPutDay")+"："+DateUtil.getNewTime(timeByZ,0,DateUtil.dateFormat).replace("-","/"));
            cell.setCellStyle(outDateStyle);
            //2行  空白行
            row.setHeightInPoints(18);
            row = sheet.createRow(2);
            //3行  表格title
            row = sheet.createRow(3);
            row.setHeightInPoints(95);
            cell = row.createCell(0);
        	cell.setCellValue(bundle.getString("empCoDE"));
        	cell.setCellStyle(bodyStyle);
        	cell = row.createCell(1);
        	cell.setCellValue(bundle.getString("empName"));
        	cell.setCellStyle(bodyStyle);
        	cell = row.createCell(2);
        	cell.setCellValue(bundle.getString("empDepartName"));
        	cell.setCellStyle(bodyStyle);
        	cell = row.createCell(3);
        	cell.setCellValue(bundle.getString("empLevelName"));
        	cell.setCellStyle(bodyStyle);
        	cell = row.createCell(4);
        	cell.setCellValue(bundle.getString("empPower"));
        	cell.setCellStyle(bodyStyle);
        	sheet.setColumnWidth(0, 12*256);
            sheet.setColumnWidth(1, 12*256);
            sheet.setColumnWidth(2, 12*256);
            sheet.setColumnWidth(3, 16*256);
            sheet.setColumnWidth(4, 26*256);
        	int colorIndex = 8;
          //创建标题
            for(int i = 0;i < nodeList.size();i++)
            {
            	XSSFCellStyle style = workbook.createCellStyle();
            	style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
                style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
                style.setBorderTop(HSSFCellStyle.BORDER_THIN);
                style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                style.setBorderRight(HSSFCellStyle.BORDER_THIN);
                style.setLeftBorderColor(HSSFColor.GREY_80_PERCENT.index);
                style.setRightBorderColor(HSSFColor.GREY_80_PERCENT.index);
                style.setTopBorderColor(HSSFColor.GREY_80_PERCENT.index);
                style.setBottomBorderColor(HSSFColor.GREY_80_PERCENT.index);
                style.setWrapText(true);
                style.setFont(bodyFont);
                
                if(pars.getLangTyp().equals("en"))
        		{
                	nodeStr = nodeList.get(i).getNodeNameen();
        		}else if(pars.getLangTyp().equals("jp"))
        		{
        			nodeStr = nodeList.get(i).getNodeNamejp();
        		}else if(pars.getLangTyp().equals("zt"))
        		{
        			nodeStr = nodeList.get(i).getNodeNamezt();
        		}else
        		{
        			nodeStr = nodeList.get(i).getNodeNamezc();
        		}
            	
            	nodeStrArr = nodeStr.split("-");
            	nodeStr = "";
            	for(int j = 0;j < nodeStrArr.length;j++)
            	{
            		nodeStr += nodeStrArr[j];
            		if(j != (nodeStrArr.length - 1))
            		{
            			nodeStr += "\r\n";
            		}
            		
            	}
            	
            	if(colorStr.equals(""))
            	{
            		colorStr = nodeList.get(i).getNodeColor().substring(1);
            	}else {
            		if(!colorStr.equals(nodeList.get(i).getNodeColor().substring(1)))
            		{
            			colorStr = nodeList.get(i).getNodeColor().substring(1);
            			colorIndex++;
            		}
            	}
            	
            	XSSFCellStyle cellStyle = (XSSFCellStyle) workbook.createCellStyle();
            	style.setFillForegroundColor(new XSSFColor(new java.awt.Color(convertHexToNumber(colorStr.substring(0, 2)), convertHexToNumber(colorStr.substring(2, 4)), convertHexToNumber(colorStr.substring(4, 6)))));
            	style.setFillPattern(CellStyle.SOLID_FOREGROUND);
            	/*
            	HSSFPalette palette = ((XSSFWorkbook) workbook).getCustomPalette();
            	style.setFillForegroundColor((short) colorIndex);
                style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                palette.setColorAtIndex((short) colorIndex,
                        (byte) convertHexToNumber(colorStr.substring(0, 2)),//RGB red
                        (byte) convertHexToNumber(colorStr.substring(2, 4)),//RGB green
                        (byte) convertHexToNumber(colorStr.substring(4, 6)) //RGB blue
                );
                */
                
            	cell = row.createCell(i+5);
            	sheet.setColumnWidth(i+5, 18*256);
            	cell.setCellValue(nodeStr);
            	cell.setCellStyle(style);
            }
            //创建内容
            /*
            for(int i = 0;i < roleList.size();i++)
            {
            	row = sheet.createRow(i + 1);
            	row.setHeightInPoints(18);
            	cell = row.createCell(0);
            	cell.setCellValue(roleList.get(i).getRoleName());
            	
        		for(int j = 0;j < nodeList.size();j++)
            	{
        			cell = row.createCell(j+1);
        			boolean flg = false;
            		for(int n = 0;n < nodeListByRole.size();n++)
            		{
            			if(nodeList.get(j).getNodeID() == nodeListByRole.get(n).getNodeID() && 
            					roleList.get(i).getRoleID() == nodeListByRole.get(n).getRoleID())
            			{
            				flg = true;
            				break;
            			}
            		}
        			if(flg)
            		{
            			cell.setCellValue("o");
            		}else {
            			cell.setCellValue("X");
            		}
            		cell.setCellStyle(bodyStyle);
            	}
            }
            */
            for(int i = 0;i < nodeListByUser.size();i++)
            {
            	row = sheet.createRow(i + 4);
            	row.setHeightInPoints(18);
            	cell = row.createCell(0);
            	cell.setCellValue(nodeListByUser.get(i).getMemberID());
            	cell.setCellStyle(bodyLeftStyle);
            	cell = row.createCell(1);
            	cell.setCellValue(nodeListByUser.get(i).getUserName());
            	cell.setCellStyle(bodyLeftStyle);
            	cell = row.createCell(2);
            	cell.setCellValue(getDepartNameByLangT(pars.getLangTyp(), nodeListByUser.get(i)));
            	cell.setCellStyle(bodyLeftStyle);
            	cell = row.createCell(3);
            	cell.setCellValue(getLevelNameByLangT(pars.getLangTyp(), nodeListByUser.get(i)));
            	cell.setCellStyle(bodyLeftStyle);
            	cell = row.createCell(4);
            	
            	if(pars.getLangTyp().equals("en"))
        		{
            		cell.setCellValue(nodeListByUser.get(i).getRoleNameListStren());
        		}else if(pars.getLangTyp().equals("jp"))
        		{
        			cell.setCellValue(nodeListByUser.get(i).getRoleNameListStrjp());
        		}else if(pars.getLangTyp().equals("zt"))
        		{
        			cell.setCellValue(nodeListByUser.get(i).getRoleNameListStrzt());
        		}else
        		{
        			cell.setCellValue(nodeListByUser.get(i).getRoleNameListStrzc());
        		}
            	
            	cell.setCellStyle(bodyLeftStyle);
            	//这里的nodeIDS代表的是所有用户拥有的权限的NODEID
            	String nodeIDS = nodeListByUser.get(i).getNodeListStr();
            	String[] uNodeList = nodeIDS.split(",");
            	
        		for(int j = 0;j < nodeList.size();j++)
            	{
        			cell = row.createCell(j+5);
        			boolean flg = false;
            		for(int n = 0;n < uNodeList.length;n++)
            		{
            			if(nodeList.get(j).getNodeID() == Integer.valueOf(uNodeList[n]))
            			{
            				flg = true;
            				break;
            			}
            		}
        			if(flg)
            		{
            			cell.setCellValue("○");
            		}else {
            			cell.setCellValue("×");
            		}
            		cell.setCellStyle(bodyStyle);
            	}
            }
//            FileOutputStream fout = new FileOutputStream("D:\\\\jspertxmls\\a.xls");  
//            workbook.write(fout);  
//            fout.close();  
//            return "s";
            
            ByteArrayOutputStream os = new ByteArrayOutputStream();
           	workbook.write(os);
           	byte[] buffer = os.toByteArray();
           	return Base64.getEncoder().encodeToString(buffer);
           	
        }catch(Exception e){
        	Logger LOG = LoggerFactory.getLogger(ExportExcelServiceImpl.class);
        	LOG.error("[excelerr,{}]{}",e.getMessage());
        	System.out.println(e.getMessage());
            //e.printStackTrace();
        	return "error";
        }
		
	}
	//job
	public String exportJobsReport(OutPutInput pars) {
		String dateMonthNow = DateUtil.getAddNumMotnDate(pars.getStartDate(),0,DateUtil.dateFormatYeayMonth);
		String dateMonthOne = DateUtil.getAddNumMotnDate(pars.getStartDate(),1,DateUtil.dateFormatYeayMonth);
		String dateMonthSecond = DateUtil.getAddNumMotnDate(pars.getStartDate(),2,DateUtil.dateFormatYeayMonth);
		String endDateMonth = DateUtil.getAddNumMotnDate(pars.getEndDate(),0,DateUtil.dateFormatYeayMonth);
		
		//if(Integer.valueOf(dateMonthNow) != Integer.valueOf(endDateMonth) && Integer.valueOf(dateMonthOne) != Integer.valueOf(endDateMonth) && Integer.valueOf(dateMonthSecond) != Integer.valueOf(endDateMonth))
		if(!dateMonthNow.equals(endDateMonth) && !dateMonthOne.equals(endDateMonth) && !dateMonthSecond.equals(endDateMonth))
		{
			return "DATE_RANGE_ERROR";
		}
		if(pars.getAccountCd()!=null && !pars.getAccountCd().equals("")) {
			int s = exportExcelMapper.getClmstlength(pars.getCompanyID(),pars.getAccountCd());
			if(s==0) {
				return "CLMST_RANGE_ERROR";
			}
			
		}
		
		int titleLength = 17;
		List<ExcelReport> reportList = null;
		if(pars.getFileName().equals("jobList"))
		{
			reportList = exportExcelMapper.getReportListByJobs(pars.getCompanyID(),pars.getStartDate(),pars.getEndDate(),pars.getAccountCd());
		}else {
			reportList = exportExcelMapper.getReportListByJobsLabel(pars.getCompanyID(),pars.getStartDate(),pars.getEndDate(),pars.getAccountCd());
			titleLength = 18;
		}
		
		Calculate calculate = new Calculate();
		ExcelReport excelReport = new ExcelReport();
		String companyName = exportExcelMapper.getCompanyName(pars.getCompanyID());
		String timeByZ = commonMethedService.getTimeByZone(DateUtil.getDateForNow(DateUtil.dateTimeFormat), pars.getCompanyID());
		int pointNumber = commonMethedMapper.getPointNumberByCompany(Integer.valueOf(pars.getCompanyID()));
		//List<CommonmstVo> foreignCodeVO = jobLandMapper.selectSaleCode(Integer.valueOf(pars.getCompanyID()),"001","0051");
		//int foreignFormatFlg = Integer.valueOf(foreignCodeVO.get(0).getAidcd());
		String pointNum = "";
		for(int i = 0;i < pointNumber;i++)
		{
			if(i == 0)
			{
				pointNum += ".";
			}
			pointNum += "0";
		}
		String jobCreateDate = "";
		String chooseDate = "";
		
    	if(!pars.getStartDate().equals(""))
    	{
    		chooseDate += DateUtil.dateToString(DateUtil.stringtoDate(pars.getStartDate(), DateUtil.dateFormat), DateUtil.accountDateFormat).replace("-", "/");
    	}
    	if(!pars.getEndDate().equals(""))
    	{
    		chooseDate += " - " + DateUtil.dateToString(DateUtil.stringtoDate(pars.getEndDate(), DateUtil.dateFormat), DateUtil.accountDateFormat).replace("-", "/");
    	}
    	String fontName = getFontNameByLangT(pars.getLangTyp());
		try{
			Locale local = JasperOutPdfUtil.getLocalByLangT(pars.getLangTyp());
			ResourceBundle bundle = ResourceBundle.getBundle(JasperOutPdfUtil.BUNDLE_NAME , local);
			
        	// 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        	XSSFWorkbook workbook = new XSSFWorkbook();
        	// 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        	XSSFSheet sheet = workbook.createSheet(pars.getSheetName());
        	CellRangeAddress companyNameCell=new CellRangeAddress(0, 0, 0,2);
        	CellRangeAddress titleCell=new CellRangeAddress(0, 0, 3,15);
        	
        	if(pars.getFileName().equals("jobList")) {
        		CellRangeAddress clNameCell=new CellRangeAddress(1, 1, 1,16);
            	CellRangeAddress clCodeCell=new CellRangeAddress(2, 2, 1,16);
            	sheet.addMergedRegion(clNameCell);
                sheet.addMergedRegion(clCodeCell);
        	}else {
        		CellRangeAddress clNameCell=new CellRangeAddress(1, 1, 1,17);
            	CellRangeAddress clCodeCell=new CellRangeAddress(2, 2, 1,17);
            	sheet.addMergedRegion(clNameCell);
                sheet.addMergedRegion(clCodeCell);
        	}
//        	CellRangeAddress clNameCell=new CellRangeAddress(1, 1, 1,3);
//        	CellRangeAddress clCodeCell=new CellRangeAddress(2, 2, 1,3);
        	CellRangeAddress divdayCell=new CellRangeAddress(3, 3, 1,3);
            sheet.addMergedRegion(companyNameCell);
            sheet.addMergedRegion(titleCell);
//            sheet.addMergedRegion(clNameCell);
//            sheet.addMergedRegion(clCodeCell);
            sheet.addMergedRegion(divdayCell);
        	// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
            XSSFRow row = sheet.createRow(0);
            // 第四步，创建单元格，并设置值表头 设置表头居中
            
            XSSFFont titleFont = getExcelFont(workbook,(short)14,fontName); 
            XSSFFont bodyFont = getExcelFont(workbook,(short)10,fontName);
            
            XSSFDataFormat format= workbook.createDataFormat();
            
            XSSFCellStyle style = getExcelStyle(workbook, titleFont, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER, 0, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",false);
            XSSFCellStyle styleT = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER, 0, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",false);
            XSSFCellStyle bodyStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",true);
            XSSFCellStyle bodyLabelTotalStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",true);
            XSSFCellStyle bodyLeftStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER, 0, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",true);
            XSSFCellStyle cldivLeftStyle= getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER, 0, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",false);
            String moneyUnit = getCompanyMoneyUnit(pars.getCompanyID());
            String formatStr = moneyUnit + "#,##0"+pointNum;
            String porfitRateformatStr = "@";
            XSSFCellStyle porfitRateStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, format,porfitRateformatStr,true);
            XSSFCellStyle porfitLaeblTotalRateStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, format,porfitRateformatStr,true);
            XSSFCellStyle moneyStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, format,formatStr,true);
            XSSFCellStyle moneyLabelTotalStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, format,formatStr,true);
            XSSFCellStyle bodyTabLeftStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",true);
            XSSFCellStyle totalMontyStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, format,formatStr,true);
            
            XSSFCell cell = null;
            row.setHeightInPoints(24);
            int cellIndex = 0;
			 
	          //创建标题
	            cell = row.createCell(0);
	            cell.setCellValue(companyName);
	            cell.setCellStyle(bodyLeftStyle);
	            cell = row.createCell(3);
	            //JOB状況表/JOB状況表（管理ラベル別）
	            if(pars.getFileName().equals("jobList"))
	            {
	            	cell.setCellValue(bundle.getString("jobsTitle"));
	            }else {
	            	cell.setCellValue(bundle.getString("jobsLabelTitle"));
	            }
	            
	            cell.setCellStyle(style);
	            cell = row.createCell((titleLength-1));
	            //outPutDay
	            cell.setCellValue(bundle.getString("outPutDay")+"： "+DateUtil.getNewTime(timeByZ,0,DateUtil.dateFormat).replace("-","/"));
	            cell.setCellStyle(styleT);
	        	
	            if(!pars.getAccountCd().equals(""))
	            {
		        	row = sheet.createRow(1);
		        	row.setHeightInPoints(22);
		        	cell = row.createCell(0);
		        	//得意先
		        	cell.setCellValue(bundle.getString("cldivName")+"　：");
		        	cell.setCellStyle(bodyLeftStyle);
		        	cell = row.createCell(1);
		        	
		        	String AccountIDs = exportExcelMapper.getAccountIDs(pars.getCompanyID(),pars.getAccountCd());
		        	cell.setCellValue(AccountIDs);
		        	cell.setCellStyle(bodyLeftStyle);
		        	
		        	row = sheet.createRow(2);
		        	row.setHeightInPoints(22);
		        	cell = row.createCell(1);
		        	
//		        	if(reportList.size() > 0)
//	        		{
//	        			Set<ExcelReport> userSet = new HashSet<>(reportList);
//	        			List<ExcelReport> list1 = new ArrayList<>(userSet);
//	        			String cldivName = "";
//	        			cldivName = reportList.get(0).getdClientName()+"("+reportList.get(0).getCldivID()+")";
//	        			for(int i =1;i<list1.size();i++) {
//	        				cldivName=cldivName+","+list1.get(i).getdClientName()+"("+list1.get(i).getCldivID()+")";
//	        			}
//	        			cell.setCellValue(cldivName);
//	        		}
		        	List<Clmst> clmstList = null;
		        	clmstList = exportExcelMapper.getClientNames(pars.getCompanyID(),pars.getAccountCd());
		        	String cldivName = "";
		        	if(clmstList.size() > 0) {
		        		for(int i=0;i<clmstList.size();i++) {
		        			if(i==0) {
		        				cldivName=clmstList.get(i).getDivnm()+"("+clmstList.get(i).getCldivcd()+")";
		        			}else{
		        				cldivName=cldivName+","+clmstList.get(i).getDivnm()+"("+clmstList.get(i).getCldivcd()+")";
		        			}
		        		}
		        	}
		        	cell.setCellValue(cldivName);
		        	cell.setCellStyle(cldivLeftStyle);
	            }
	        	row = sheet.createRow(3);
	        	row.setHeightInPoints(18);
	        	cell = row.createCell(0);
	        	//計上（予定）月
	        	cell.setCellValue(bundle.getString("planDlvMonth")+"　：");
	        	cell.setCellStyle(bodyLeftStyle);
	        	cell = row.createCell(1);
	        	cell.setCellValue(chooseDate);
	        	cell.setCellStyle(bodyLeftStyle);
	        	
	        	row = sheet.createRow(4);
	        	row.setHeightInPoints(18);
	        	cell = row.createCell(0);
	        	cell.setCellValue("");
	        	
	        	row = sheet.createRow(5);
	        	row.setHeightInPoints(20);
	        	for(int i = 0;i < titleLength;i++)
	        	{
	        		sheet.setColumnWidth(0, 20*256);
        			sheet.setColumnWidth(1, 20*256);
	        		//sheet.setColumnWidth(3, 16*256);
	        		if(i < 5 && i > 0)
	        		{
	        			sheet.setColumnWidth(i, 12*256);
	        		}else {
	        			sheet.setColumnWidth(i, 14*256);
	        		}
	        		if(!pars.getFileName().equals("jobList") && i == 4)
	        		{
	        			sheet.setColumnWidth((i-1), 12*256);
	        			sheet.setColumnWidth(i, 14*256);
	        		}
	        		
	        		cell = row.createCell(i);
	        		if(!pars.getFileName().equals("jobList"))
	        		{
	        			/*
	        			if(i == 1)
	        			{
	        				cell.setCellValue(bundle.getString("jobs_99"));
	        			}else if(i > 1){
	        				cell.setCellValue(bundle.getString("jobs_"+(i-1)));
	        			}else {
	        				cell.setCellValue(bundle.getString("jobs_"+i));
	        			}
	        			*/
	        			if(i == 0)
	        			{
	        				cell.setCellValue(bundle.getString("jobs_99"));
	        			}else {
	        				cell.setCellValue(bundle.getString("jobs_"+(i-1)));
	        			}
	        			if( i == 17 || i == 16)
	            		{
	            			sheet.setColumnWidth(i, 20*256);
	            		}
	            		if(i == 15)
	            		{
	            			sheet.setColumnWidth(i, 30*256);
	            		}
	            		if(i == 14)
	            		{
	            			sheet.setColumnWidth(i, 36*256);
	            		}
	        		}else {
	        			cell.setCellValue(bundle.getString("jobs_"+i));
	        			if( i == 16 || i == 15)
	            		{
	            			sheet.setColumnWidth(i, 20*256);
	            		}
	            		if(i == 14)
	            		{
	            			sheet.setColumnWidth(i, 30*256);
	            		}
	            		if(i == 13)
	            		{
	            			sheet.setColumnWidth(i, 36*256);
	            		}
	        		}
	        		
	        		
	        		cell.setCellStyle(bodyStyle);
	        	}
	        	Double saleTotal = 0.0;
	        	Double saleVatTotal = 0.0;
	        	Double planCostTotal = 0.0;
	        	Double costTotal = 0.0;
	        	Double costVatTotal = 0.0;
	        	Double taxTotal = 0.0;
	        	Double profitTotal = 0.0;
	        	String clDivID = "";
	        	Double profitRate = 0.0;
	        	int rowIndex = 6;
	        	int isClearFlg = 0;
	        	
	        	Double sumSaleTotal = 0.0;
	        	Double sumSaleVatTotal = 0.0;
	        	Double sumPlanCostTotal = 0.0;
	        	Double sumCostTotal = 0.0;
	        	Double sumCostVatTotal = 0.0;
	        	Double sumTaxTotal = 0.0;
	        	Double sumProfitTotal = 0.0;
	        	Double sumProfitRate = 0.0;
	            //创建内容
	        	for(int i = 0;i < reportList.size();i++)
	    		{
	        		if(!pars.getFileName().equals("jobList"))
	        		{
	        			cellIndex = 0;
	        		}
	        		excelReport = reportList.get(i);
	        		jobCreateDate = commonMethedService.getTimeByZone(excelReport.getJobCreateDate(), pars.getCompanyID());
	        		if(jobCreateDate != "")
	        		{
	        			jobCreateDate = DateUtil.getNewTime(jobCreateDate,0,DateUtil.dateFormat);
	        		}
	        		
	        		//calculate = commonMethedService.calcuateJobTax(excelReport.getJobNo(), Integer.valueOf(pars.getCompanyID()));
	        		calculate = commonMethedService.calcuateJobTaxByJobExcel(excelReport.getJobNo(), Integer.valueOf(pars.getCompanyID()));
	        		row = sheet.createRow(i+rowIndex);
	        		row.setHeightInPoints(26);
	        		
	        		if(!pars.getFileName().equals("jobList"))
	        		{
	        			cell = row.createCell(cellIndex + 0);
	            		cell.setCellValue(excelReport.getLabelName());
	            		cell.setCellStyle(bodyTabLeftStyle);
	            		
	            		cell = row.createCell(cellIndex + 1);
	            		cell.setCellValue(excelReport.getdClientName());
	            		cell.setCellStyle(bodyTabLeftStyle);
	            		cellIndex = 1;
	            		excelReport.setCldivID(excelReport.getLabelID());
	        			if(i != (reportList.size()-1))
	        			{
	        				//出力label报表时，把i的下一位labelID赋值给i的下一位cldivcd，用来与当前值判断是否为一致。
	        				reportList.get(i+1).setCldivID(reportList.get(i+1).getLabelID());
	        			}		
	            		
	        		}else {
	        			cell = row.createCell(cellIndex + 0);
	            		cell.setCellValue(excelReport.getdClientName());
	            		cell.setCellStyle(bodyTabLeftStyle);
	        		}
	        		
	        		cell = row.createCell(cellIndex + 1);
	        		cell.setCellValue(excelReport.getJobNo());
	        		cell.setCellStyle(bodyTabLeftStyle);
	        		
	        		cell = row.createCell(cellIndex + 2);
	        		cell.setCellValue(jobCreateDate.replace("-","/"));
	        		cell.setCellStyle(bodyStyle);
	    			
	        		cell = row.createCell(cellIndex + 3);
	        		cell.setCellValue(excelReport.getPlanDlvday().replace("-","/"));
	        		cell.setCellStyle(bodyStyle);
	        		
	        		cell = row.createCell(cellIndex + 4);
	        		if(excelReport.getIsSellFlg() == 1)
	        		{
	        			cell.setCellValue(bundle.getString("isSell"));
	        		}else {
	        			cell.setCellValue(bundle.getString("isNotSell"));
	        		}
	        		cell.setCellStyle(bodyStyle);
	        		
	        		if(excelReport.getIsSellFlg() == 1)
	        		{
	        			cell = row.createCell(cellIndex + 5);
	            		cell.setCellValue(calculate.getSaleAmt());
	            		cell.setCellStyle(moneyStyle);
	            		
	            		cell = row.createCell(cellIndex + 6);
	            		cell.setCellValue(calculate.getSaleVatAmt());
	            		cell.setCellStyle(moneyStyle);
	        		}else {
	        			cell = row.createCell(cellIndex + 5);
	            		cell.setCellValue(calculate.getPlanSaleAmt());
	            		cell.setCellStyle(moneyStyle);
	            		
	            		cell = row.createCell(cellIndex + 6);
	            		cell.setCellValue(calculate.getPlanSaleVatAmt());
	            		cell.setCellStyle(moneyStyle);
	        		}
	        		
	        		
	        		cell = row.createCell(cellIndex + 7);
	        		cell.setCellValue(calculate.getPlanCostAmt());
	        		cell.setCellStyle(moneyStyle);
	        		
	        		
	        		if(calculate.getCountCost() > 0)
	        		{
	        			cell = row.createCell(cellIndex + 9);
	        			cell.setCellValue(calculate.getCostVatAmt());
	        			cell.setCellStyle(moneyStyle);
	        			
	        			cell = row.createCell(cellIndex + 8);
	            		cell.setCellValue(calculate.getCostAmt());
	            		cell.setCellStyle(moneyStyle);
	        		}else {
	        			if(calculate.getIsCostFinsh() < 1)
	        			{
	        				cell = row.createCell(cellIndex + 9);
	        				//cell.setCellValue(calculate.getPlanCostVatAmt());
	        				cell.setCellValue(0);
	        				cell.setCellStyle(moneyStyle);
	        			}else {
	        				cell = row.createCell(cellIndex + 9);
	        				cell.setCellValue(0);
	        				cell.setCellStyle(moneyStyle);
	        			}
	        			cell = row.createCell(cellIndex + 8);
	            		cell.setCellValue(0);
	            		cell.setCellStyle(moneyStyle);
	        		}
	        		
	        		
	        		cell = row.createCell(cellIndex + 10);
	        		cell.setCellValue(calculate.getTaxTotal());
	        		cell.setCellStyle(moneyStyle);
	        		
	        		
	        		cell = row.createCell(cellIndex + 11);
	        		cell.setCellValue(calculate.getProfit());
	        		cell.setCellStyle(moneyStyle);
	        		
	        		cell = row.createCell(cellIndex + 12);
	        		if(calculate.getProfitRate() == Double.POSITIVE_INFINITY)
	        		{
	        			cell.setCellValue("INF%");
	        		}else {
	        			cell.setCellValue(commonMethedService.profitRateFormat(calculate.getProfitRate().toString())+"%");
	        		}
	        		
	        		cell.setCellStyle(porfitRateStyle);
	        		
	        		cell = row.createCell(cellIndex + 13);
	        		cell.setCellValue(excelReport.getJobName());
	        		cell.setCellStyle(bodyTabLeftStyle);
	        		
	        		cell = row.createCell(cellIndex + 14);
	        		cell.setCellValue(excelReport.getSaleTypName());
	        		cell.setCellStyle(bodyTabLeftStyle);
	        		
	        		cell = row.createCell(cellIndex + 15);
	        		cell.setCellValue(excelReport.getrClientName());
	        		cell.setCellStyle(bodyTabLeftStyle);
	        		
	        		cell = row.createCell(cellIndex + 16);
	        		cell.setCellValue(excelReport.getgClientName());
	        		cell.setCellStyle(bodyTabLeftStyle);
	        		
	            	
	        		if(isClearFlg == 0 || excelReport.getCldivID().equals(clDivID))
	        		{
	        			if(excelReport.getIsSellFlg() == 1)
	            		{
	        				saleTotal = MathController.add(saleTotal, calculate.getSaleAmt());
	                    	saleVatTotal = MathController.add(saleVatTotal, calculate.getSaleVatAmt());
	                    	sumSaleTotal = MathController.add(sumSaleTotal, calculate.getSaleAmt());
	                    	sumSaleVatTotal = MathController.add(sumSaleVatTotal, calculate.getSaleVatAmt());
	            		}else {
	            			saleTotal = MathController.add(saleTotal, calculate.getPlanSaleAmt());
	                    	saleVatTotal = MathController.add(saleVatTotal, calculate.getPlanSaleVatAmt());
	                    	sumSaleTotal = MathController.add(sumSaleTotal, calculate.getPlanSaleAmt());
	                    	sumSaleVatTotal = MathController.add(sumSaleVatTotal, calculate.getPlanSaleVatAmt());
	            		}
	        			
	        			if(calculate.getCountCost() > 0)
	            		{
	        				costTotal = MathController.add(costTotal, calculate.getCostAmt());
	                    	costVatTotal = MathController.add(costVatTotal, calculate.getCostVatAmt());
	                    	sumCostTotal = MathController.add(sumCostTotal, calculate.getCostAmt());
	                    	sumCostVatTotal = MathController.add(sumCostVatTotal, calculate.getCostVatAmt());
	            		}else {
	            			if(calculate.getIsCostFinsh() < 1)
	            			{
	            				//costVatTotal = MathController.add(costVatTotal, calculate.getPlanCostVatAmt());
	            				//sumCostVatTotal = MathController.add(sumCostVatTotal, calculate.getPlanCostVatAmt());
	            				costVatTotal = MathController.add(costVatTotal, 0);
	            				sumCostVatTotal = MathController.add(sumCostVatTotal, 0);
	            			}else {
	            				costVatTotal = MathController.add(costVatTotal, 0);
	            				sumCostVatTotal = MathController.add(sumCostVatTotal, 0);
	            			}
	            			costTotal = MathController.add(costTotal, 0);
	            			sumCostTotal = MathController.add(sumCostTotal, 0);
	            		}
	                	planCostTotal = MathController.add(planCostTotal, calculate.getPlanCostAmt());
	                	taxTotal = MathController.add(taxTotal, calculate.getTaxTotal());
	                	profitTotal = MathController.add(profitTotal, calculate.getProfit());
	                	
	                	sumPlanCostTotal = MathController.add(sumPlanCostTotal, calculate.getPlanCostAmt());
	                	sumTaxTotal = MathController.add(sumTaxTotal, calculate.getTaxTotal());
	                	sumProfitTotal =MathController.add(sumProfitTotal, calculate.getProfit());
	        		}
	        		
	        		if(isClearFlg == 0 )
	        		{
	        			clDivID = excelReport.getCldivID();
	        			isClearFlg = 1;
	        		}
	    			if(i == (reportList.size()-1) || !reportList.get(i+1).getCldivID().equals(clDivID))
	    			{
	    				rowIndex = rowIndex + 1;
	    				row = sheet.createRow(i+rowIndex);
	    				row.setHeightInPoints(26);
	    				clDivID = excelReport.getCldivID();
	    				CellRangeAddress clTotal = null;
	                    cell = row.createCell(0);
	    				if(!pars.getFileName().equals("jobList"))
	            		{
	    					clTotal=new CellRangeAddress(i+rowIndex, i+rowIndex, 0,5);
	    					sheet.addMergedRegion(clTotal);
	    					//ラベル計
	    		            bodyLabelTotalStyle.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
	    					bodyLabelTotalStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	    					moneyLabelTotalStyle.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
	    					moneyLabelTotalStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	    					porfitLaeblTotalRateStyle.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
	    					porfitLaeblTotalRateStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	    					cell.setCellValue(bundle.getString("labelTotal"));
	    					
	            		}else {
	            			clTotal=new CellRangeAddress(i+rowIndex, i+rowIndex, 0,4);
	            			sheet.addMergedRegion(clTotal);
	            			//得意先計
	            			cell.setCellValue(bundle.getString("cldivTotal"));
	            		}
	    				cell.setCellStyle(bodyLabelTotalStyle);
	    				
	    				if(!pars.getFileName().equals("jobList"))
	            		{
	    					cell = row.createCell(cellIndex +0);
	                		cell.setCellStyle(moneyLabelTotalStyle);
	    					
	            		}
	    				
	    				cell = row.createCell(cellIndex +1);
	            		cell.setCellStyle(moneyLabelTotalStyle);
	            		cell = row.createCell(cellIndex +2);
	            		cell.setCellStyle(moneyLabelTotalStyle);
	            		cell = row.createCell(cellIndex +3);
	            		cell.setCellStyle(moneyLabelTotalStyle);
	            		cell = row.createCell(cellIndex +4);
	            		cell.setCellStyle(moneyLabelTotalStyle);
	            		
	            		cell = row.createCell(cellIndex +5);
	            		cell.setCellValue(saleTotal);
	            		cell.setCellStyle(moneyLabelTotalStyle);
	            		
	            		cell = row.createCell(cellIndex +6);
	            		cell.setCellValue(saleVatTotal);
	            		cell.setCellStyle(moneyLabelTotalStyle);
	            		
	            		cell = row.createCell(cellIndex +7);
	            		cell.setCellValue(planCostTotal);
	            		cell.setCellStyle(moneyLabelTotalStyle);
	            		
	            		cell = row.createCell(cellIndex +8);
	            		cell.setCellValue(costTotal);
	            		cell.setCellStyle(moneyLabelTotalStyle);
	            		
	            		cell = row.createCell(cellIndex +9);
	            		cell.setCellValue(costVatTotal);
	            		cell.setCellStyle(moneyLabelTotalStyle);
	            		
	            		cell = row.createCell(cellIndex +10);
	            		cell.setCellValue(taxTotal);
	            		cell.setCellStyle(moneyLabelTotalStyle);
	            		
	            		cell = row.createCell(cellIndex +11);
	            		cell.setCellValue(profitTotal);
	            		cell.setCellStyle(moneyLabelTotalStyle);
	            		
	            		
	            		profitRate = MathController.mul(MathController.div(profitTotal,saleTotal),100);
	            		profitRate = commonMethedService.pointFormatHandler(profitRate,3,2);
	            		
	            		cell = row.createCell(cellIndex +12);
	            		
	            		if(saleTotal == 0.0)
	            		{
	            			cell.setCellValue("INF%");
	            		}else {
	            			cell.setCellValue(commonMethedService.profitRateFormat(profitRate.toString())+"%");
	            		}
	            		cell.setCellStyle(porfitLaeblTotalRateStyle);
	    				
	            		cell = row.createCell(cellIndex +13);
	            		cell.setCellStyle(bodyLabelTotalStyle);
	            		
	            		cell = row.createCell(cellIndex +14);
	            		cell.setCellStyle(bodyLabelTotalStyle);
	            		
	            		cell = row.createCell(cellIndex +15);
	            		cell.setCellStyle(bodyLabelTotalStyle);
	            		
	            		cell = row.createCell(cellIndex +16);
	            		cell.setCellStyle(bodyLabelTotalStyle);
	            		
	    				saleTotal = 0.0;
	                	saleVatTotal = 0.0;
	                	planCostTotal = 0.0;
	                	costTotal = 0.0;
	                	costVatTotal = 0.0;
	                	taxTotal = 0.0;
	                	profitTotal = 0.0;
	                	isClearFlg = 0;
	    			}
	    			
	    			if(i == (reportList.size()-1) && pars.getFileName().equals("jobList"))
	    			{
	    				totalMontyStyle.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
	    				totalMontyStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	    	    		
	    				rowIndex = rowIndex + 1;
	    				row = sheet.createRow(i+rowIndex);
	    				row.setHeightInPoints(26);
	    				CellRangeAddress clTotal=new CellRangeAddress(i+rowIndex, i+rowIndex, 0,4);
	                    sheet.addMergedRegion(clTotal);
	                    cell = row.createCell(0);
	                    //合计
	            		cell.setCellValue(bundle.getString("total"));
	            		cell.setCellStyle(totalMontyStyle);
	            		
	            		cell = row.createCell(1);
	            		cell.setCellStyle(totalMontyStyle);
	            		cell = row.createCell(2);
	            		cell.setCellStyle(totalMontyStyle);
	            		cell = row.createCell(3);
	            		cell.setCellStyle(totalMontyStyle);
	            		cell = row.createCell(4);
	            		cell.setCellStyle(totalMontyStyle);
	            		
	            		cell = row.createCell(5);
	            		cell.setCellValue(sumSaleTotal);
	            		cell.setCellStyle(totalMontyStyle);
	            		
	            		cell = row.createCell(6);
	            		cell.setCellValue(sumSaleVatTotal);
	            		cell.setCellStyle(totalMontyStyle);
	            		
	            		cell = row.createCell(7);
	            		cell.setCellValue(sumPlanCostTotal);
	            		cell.setCellStyle(totalMontyStyle);
	            		
	            		cell = row.createCell(8);
	            		cell.setCellValue(sumCostTotal);
	            		cell.setCellStyle(totalMontyStyle);
	            		
	            		cell = row.createCell(9);
	            		cell.setCellValue(sumCostVatTotal);
	            		cell.setCellStyle(totalMontyStyle);
	            		
	            		cell = row.createCell(10);
	            		cell.setCellValue(sumTaxTotal);
	            		cell.setCellStyle(totalMontyStyle);
	            		
	            		cell = row.createCell(11);
	            		cell.setCellValue(sumProfitTotal);
	            		cell.setCellStyle(totalMontyStyle);
	            		
	            		
	            		sumProfitRate = MathController.mul(MathController.div(sumProfitTotal,sumSaleTotal),100);
	            		sumProfitRate = commonMethedService.pointFormatHandler(sumProfitRate,3,2);
	            		
	            		cell = row.createCell(12);
	            		if(sumSaleTotal == 0.0)
	            		{
	            			cell.setCellValue("INF%");
	            		}else {
	            			cell.setCellValue(commonMethedService.profitRateFormat(sumProfitRate.toString())+"%");
	            		}
	            		
	            		cell.setCellStyle(totalMontyStyle);
	            		
	            		cell = row.createCell(13);
	            		cell.setCellStyle(totalMontyStyle);
	            		
	            		cell = row.createCell(14);
	            		cell.setCellStyle(totalMontyStyle);
	            		
	            		cell = row.createCell(15);
	            		cell.setCellStyle(totalMontyStyle);
	            		
	            		cell = row.createCell(16);
	            		cell.setCellStyle(totalMontyStyle);
	    			}
	    		}
	        	
	        	/*
	            FileOutputStream fout = new FileOutputStream("D:\\\\jspertxmls\\a.xls");  
	            workbook.write(fout);  
	            fout.close();  
	            */
	            ByteArrayOutputStream os = new ByteArrayOutputStream();
	           	workbook.write(os);
	           	byte[] buffer = os.toByteArray();
	           	return Base64.getEncoder().encodeToString(buffer);
			
        }catch(Exception e){
            //e.printStackTrace();
        	return "error";
        }
	}
	//買掛金発生明細2020
	public String exportPaybelReport(OutPutInput pars) {
		if(validateChooeseDate(pars.getEndDate(),pars.getCompanyID(),false))
		{
			return "DATE_RANGE_ERROR";
		}
		
		String startDate = commonMethedMapper.searchStartTimeBySummaryMonth(Integer.valueOf(pars.getCompanyID()),DateUtil.makeDate(pars.getEndDate()));
		String endDate = commonMethedMapper.searchEndTimeBySummaryMonth(Integer.valueOf(pars.getCompanyID()),pars.getEndDate());
		List<ExcelReport> pableTopList = exportExcelMapper.getReportListByPayable(pars.getCompanyID(),startDate,endDate);
		List<ExcelReport> pableButtonList = exportExcelMapper.getReportListByPayableButton(pars.getCompanyID(),startDate,endDate);
		ExcelReport pableTotal = exportExcelMapper.getReportListByPayableTotal(pars.getCompanyID(),startDate,endDate);
		
		ExcelReport excelReport = new ExcelReport();
		String companyName = exportExcelMapper.getCompanyName(pars.getCompanyID());
		String timeByZ = commonMethedService.getTimeByZone(DateUtil.getDateForNow(DateUtil.dateTimeFormat), pars.getCompanyID());
		int pointNumber = commonMethedMapper.getPointNumberByCompany(Integer.valueOf(pars.getCompanyID()));
		//List<CommonmstVo> foreignCodeVO = jobLandMapper.selectSaleCode(Integer.valueOf(pars.getCompanyID()),"001","0051");
		//int foreignFormatFlg = Integer.valueOf(foreignCodeVO.get(0).getAidcd());
		String pointNum = "";
		for(int i = 0;i < pointNumber;i++)
		{
			if(i == 0)
			{
				pointNum += ".";
			}
			pointNum += "0";
		}
		String chooseDate = "";
    	if(!pars.getEndDate().equals(""))
    	{
    		chooseDate += DateUtil.dateToString(DateUtil.stringtoDate(pars.getEndDate(), DateUtil.dateFormat), DateUtil.accountDateFormat);
    	}
    	String fontName = getFontNameByLangT(pars.getLangTyp());
		try{
			Locale local = JasperOutPdfUtil.getLocalByLangT(pars.getLangTyp());
			ResourceBundle bundle = ResourceBundle.getBundle(JasperOutPdfUtil.BUNDLE_NAME , local);
        	// 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        	XSSFWorkbook workbook = new XSSFWorkbook();
        	// 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        	XSSFSheet sheet = workbook.createSheet(pars.getSheetName());
        	CellRangeAddress titleCell=new CellRangeAddress(0, 0, 1,4);
            sheet.addMergedRegion(titleCell);
        	// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
            XSSFRow row = sheet.createRow(0);
            // 第四步，创建单元格，并设置值表头 设置表头居中
            
            XSSFFont titleFont = getExcelFont(workbook,(short)14,fontName); 
            XSSFFont bodyFont = getExcelFont(workbook,(short)10,fontName);
            
            XSSFDataFormat format= workbook.createDataFormat();
            
            XSSFCellStyle style = getExcelStyle(workbook, titleFont, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_TOP, 0, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",false);
            XSSFCellStyle styleT = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER, 0, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",false);
            XSSFCellStyle outDateNowStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER, 0, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",false);
            XSSFCellStyle bodyStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",true);
            XSSFCellStyle bodyLeftStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",true);
            //HSSFCellStyle bodyRightStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",true);
            String moneyUnit = getCompanyMoneyUnit(pars.getCompanyID());
            String formatStr = moneyUnit + "#,##0"+pointNum;
            
            XSSFCellStyle totalmoneyStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, format,formatStr,true);
            XSSFCellStyle moneyStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, format,formatStr,true);
            XSSFCellStyle totalCenterStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,null,true);
            
            XSSFCell cell = null;
            row.setHeightInPoints(24);
            
          //创建标题
            cell = row.createCell(0);
            cell.setCellValue(companyName);
            cell.setCellStyle(styleT);
            cell = row.createCell(1);
            cell.setCellValue(bundle.getString("accountsPayableTitle"));
            cell.setCellStyle(style);
            cell = row.createCell(5);
            //outPutDay
            cell.setCellValue(bundle.getString("outPutDay")+"："+DateUtil.getNewTime(timeByZ,0,DateUtil.dateFormat).replace("-","/"));
            cell.setCellStyle(outDateNowStyle);
        	
        	row = sheet.createRow(1);
        	row.setHeightInPoints(18);
        	cell = row.createCell(0);
        	
        	
        	row = sheet.createRow(2);
        	row.setHeightInPoints(18);
        	cell = row.createCell(0);
        	//計上（予定）月
        	cell.setCellValue(bundle.getString("planDlvMonth")+"　："+chooseDate.replace("-","/"));
        	cell.setCellStyle(styleT);
        	
        	row = sheet.createRow(3);
        	row.setHeightInPoints(18);
        	cell = row.createCell(0);
        	cell.setCellValue("");
        	
        	row = sheet.createRow(4);
        	row.setHeightInPoints(20);
        	for(int i = 0;i < 6;i++)
        	{
        		sheet.setColumnWidth(i, 24*256);
        		sheet.setColumnWidth(0, 36*256);
        		
        		cell = row.createCell(i);
        		cell.setCellValue(bundle.getString("payable_"+i));
        		cell.setCellStyle(bodyStyle);
        	}
        
            //创建内容
        	for(int i = 0;i < pableTopList.size();i++)
        	{
        		excelReport = pableTopList.get(i);
        		row = sheet.createRow(i + 5);
        		row.setHeightInPoints(20);
        		if(excelReport.getOrderIndex().equals("1"))
        		{
        			CellRangeAddress clTotal = null;
                    cell = row.createCell(0);
                    
                    clTotal=new CellRangeAddress((i + 5), (i + 5), 0,4);
        			sheet.addMergedRegion(clTotal);
        			//得意先計
        			cell.setCellValue(bundle.getString("payeeTotal"));
        			cell.setCellStyle(bodyStyle);
        			cell = row.createCell(1);
            		cell.setCellStyle(bodyStyle);
            		cell = row.createCell(2);
            		cell.setCellStyle(bodyStyle);
            		cell = row.createCell(3);
            		cell.setCellStyle(bodyStyle);
            		cell = row.createCell(4);
            		cell.setCellStyle(bodyStyle);
        		}else {
        			cell = row.createCell(0);
            		cell.setCellValue(excelReport.getpClientName());
            		cell.setCellStyle(bodyLeftStyle);
            		
            		cell = row.createCell(1);
            		cell.setCellValue(excelReport.getConfirmDate().replace("-","/"));
            		cell.setCellStyle(bodyStyle);
            		
            		cell = row.createCell(2);
            		cell.setCellValue(excelReport.getJobNo());
            		cell.setCellStyle(bodyLeftStyle);
            		
            		cell = row.createCell(3);
            		cell.setCellValue(excelReport.getCostNo());
            		cell.setCellStyle(bodyLeftStyle);
            		
            		cell = row.createCell(4);
            		cell.setCellValue(excelReport.getInputNo());
            		cell.setCellStyle(bodyLeftStyle);
        		}
        		
        		
        		cell = row.createCell(5);
        		cell.setCellValue(excelReport.getPayAmt());
        		cell.setCellStyle(moneyStyle);
        		
        	}
        	//top total
        	row = sheet.createRow(pableTopList.size() + 5);
        	row.setHeightInPoints(20);
        	CellRangeAddress clTotal = null;
            cell = row.createCell(0);
            clTotal=new CellRangeAddress(pableTopList.size() + 5, pableTopList.size() + 5, 0,4);
			sheet.addMergedRegion(clTotal);
			//得意先計
			cell.setCellValue(bundle.getString("total"));
			cell.setCellStyle(totalCenterStyle);
			
			cell = row.createCell(1);
    		cell.setCellStyle(bodyStyle);
    		cell = row.createCell(2);
    		cell.setCellStyle(bodyStyle);
    		cell = row.createCell(3);
    		cell.setCellStyle(bodyStyle);
    		cell = row.createCell(4);
    		cell.setCellStyle(bodyStyle);
    		
    		cell = row.createCell(5);
    		if(pableTotal == null || StringUtil.isEmpty(String.valueOf(pableTotal.getPayAmt())))
    		{
    			cell.setCellValue("");
    		}else {
    			cell.setCellValue(pableTotal.getPayAmt());
    		}
    		cell.setCellStyle(totalmoneyStyle);
    		
    		//button
    		row = sheet.createRow(pableTopList.size() + 7);
    		row.setHeightInPoints(20);
    		row = sheet.createRow(pableTopList.size() + 8);
    		row.setHeightInPoints(20);
    		cell = row.createCell(0);
    		cell.setCellValue("("+bundle.getString("summaryTable")+")");
			cell.setCellStyle(styleT);
    		row = sheet.createRow(pableTopList.size() + 9);
    		row.setHeightInPoints(20);
    		CellRangeAddress pTotal = null;
            cell = row.createCell(0);
            pTotal=new CellRangeAddress(pableTopList.size() + 9, pableTopList.size() + 9, 0,4);
			sheet.addMergedRegion(pTotal);
			cell.setCellValue(bundle.getString("payeeCodeOrName"));
			cell.setCellStyle(bodyStyle);
			cell = row.createCell(1);
    		cell.setCellStyle(bodyStyle);
    		cell = row.createCell(2);
    		cell.setCellStyle(bodyStyle);
    		cell = row.createCell(3);
    		cell.setCellStyle(bodyStyle);
    		cell = row.createCell(4);
    		cell.setCellStyle(bodyStyle);
    		cell = row.createCell(5);
    		cell.setCellValue(bundle.getString("payable_5"));
    		cell.setCellStyle(bodyStyle);
    		
    		for(int i = 0;i < pableButtonList.size();i++)
    		{
    			row = sheet.createRow(pableTopList.size() + 10 + i);
    			row.setHeightInPoints(20);
    			CellRangeAddress pAccountCode = null;
                cell = row.createCell(0);
                pAccountCode=new CellRangeAddress((pableTopList.size() + 10 + i), (pableTopList.size() + 10 + i), 0,4);
    			sheet.addMergedRegion(pAccountCode);
    			cell.setCellValue(pableButtonList.get(i).getCldivAccountID() + "/" +pableButtonList.get(i).getpClientName());
    			cell.setCellStyle(bodyLeftStyle);
    			cell = row.createCell(1);
        		cell.setCellStyle(bodyStyle);
        		cell = row.createCell(2);
        		cell.setCellStyle(bodyStyle);
        		cell = row.createCell(3);
        		cell.setCellStyle(bodyStyle);
        		cell = row.createCell(4);
        		cell.setCellStyle(bodyStyle);
        		cell = row.createCell(5);
        		cell.setCellValue(pableButtonList.get(i).getPayAmt());
        		cell.setCellStyle(moneyStyle);
    		}
    		
    		totalmoneyStyle.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
    		totalmoneyStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
    		totalCenterStyle.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
    		totalCenterStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
    		//button total
        	row = sheet.createRow(pableTopList.size() + pableButtonList.size() + 10);
        	row.setHeightInPoints(20);
        	CellRangeAddress pbTotal = null;
            cell = row.createCell(0);
            pbTotal=new CellRangeAddress(pableTopList.size() + pableButtonList.size() + 10, pableTopList.size() + pableButtonList.size() + 10, 0,4);
			sheet.addMergedRegion(pbTotal);
			//得意先計
			cell.setCellValue(bundle.getString("total"));
			cell.setCellStyle(totalCenterStyle);
			cell = row.createCell(1);
    		cell.setCellStyle(bodyStyle);
    		cell = row.createCell(2);
    		cell.setCellStyle(bodyStyle);
    		cell = row.createCell(3);
    		cell.setCellStyle(bodyStyle);
    		cell = row.createCell(4);
    		cell.setCellStyle(bodyStyle);
    		cell = row.createCell(5);
    		if(pableTotal == null || StringUtil.isEmpty(String.valueOf(pableTotal.getPayAmt())))
    		{
    			cell.setCellValue("");
    		}else {
    			cell.setCellValue(pableTotal.getPayAmt());
    		}
    		cell.setCellStyle(totalmoneyStyle);
    		/*
            FileOutputStream fout = new FileOutputStream("D:\\\\jspertxmls\\a.xls");  
            workbook.write(fout);  
            fout.close();  
            return null;
            	*/
            ByteArrayOutputStream os = new ByteArrayOutputStream();
           	workbook.write(os);
           	byte[] buffer = os.toByteArray();
           	return Base64.getEncoder().encodeToString(buffer);
           
        }catch(Exception e){
            //e.printStackTrace();
        	return "error";
        }
	}
	//見込原価残高明細
	public String hopleCostReport(OutPutInput pars) {
		if(validateChooeseDate(pars.getEndDate(),pars.getCompanyID(),false))
		{
			return "DATE_RANGE_ERROR";
		}
		
		String startDate = commonMethedMapper.searchStartTimeBySummaryMonth(Integer.valueOf(pars.getCompanyID()),DateUtil.makeDate(pars.getEndDate()));
		String endDate = commonMethedMapper.searchEndTimeBySummaryMonth(Integer.valueOf(pars.getCompanyID()),pars.getEndDate());
		List<ExcelReport> hopleCostTopList = exportExcelMapper.getReportListByHopleCost(pars.getCompanyID(),startDate,endDate,pars.getEndDate(),DateUtil.makeDate(pars.getEndDate()));
		
		ExcelReport excelReport = new ExcelReport();
		String companyName = exportExcelMapper.getCompanyName(pars.getCompanyID());
		String timeByZ = commonMethedService.getTimeByZone(DateUtil.getDateForNow(DateUtil.dateTimeFormat), pars.getCompanyID());
		int pointNumber = commonMethedMapper.getPointNumberByCompany(Integer.valueOf(pars.getCompanyID()));
		//List<CommonmstVo> foreignCodeVO = jobLandMapper.selectSaleCode(Integer.valueOf(pars.getCompanyID()),"001","0051");
		//int foreignFormatFlg = Integer.valueOf(foreignCodeVO.get(0).getAidcd());
		String pointNum = "";
		for(int i = 0;i < pointNumber;i++)
		{
			if(i == 0)
			{
				pointNum += ".";
			}
			pointNum += "0";
		}
		String chooseDate = "";
    	if(!pars.getEndDate().equals(""))
    	{
    		chooseDate += DateUtil.dateToString(DateUtil.stringtoDate(pars.getEndDate(), DateUtil.dateFormat), DateUtil.accountDateFormat);
    	}
    	String fontName = getFontNameByLangT(pars.getLangTyp());
		try{
			Locale local = JasperOutPdfUtil.getLocalByLangT(pars.getLangTyp());
			ResourceBundle bundle = ResourceBundle.getBundle(JasperOutPdfUtil.BUNDLE_NAME , local);
        	// 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        	XSSFWorkbook workbook = new XSSFWorkbook();
        	// 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        	XSSFSheet sheet = workbook.createSheet(pars.getSheetName());
//        	CellRangeAddress titleCell=new CellRangeAddress(0, 0, 1,8);
//            sheet.addMergedRegion(titleCell);
        	// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
            XSSFRow row = sheet.createRow(0);
            // 第四步，创建单元格，并设置值表头 设置表头居中
            
            XSSFFont titleFont = getExcelFont(workbook,(short)14,fontName); 
            XSSFFont bodyFont = getExcelFont(workbook,(short)10,fontName);
            
            XSSFDataFormat format= workbook.createDataFormat();
            
            XSSFCellStyle style = getExcelStyle(workbook, titleFont, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER, 0, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",false);
            XSSFCellStyle styleT = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER, 0, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",false);
            XSSFCellStyle styleR = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER, 0, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",false);
            XSSFCellStyle bodyStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",true);
            XSSFCellStyle bodyLeftStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",true);
            //HSSFCellStyle bodyRightStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",true);
            String moneyUnit = getCompanyMoneyUnit(pars.getCompanyID());
            String formatStr = moneyUnit + "#,##0"+pointNum;
            XSSFCellStyle moneyStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, format,formatStr,true);
            XSSFCellStyle totalMontyStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, format,formatStr,true);
            totalMontyStyle.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
			totalMontyStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			
			XSSFCellStyle totalCenterStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, format,formatStr,true);
			totalCenterStyle.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
			totalCenterStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			
            XSSFCell cell = null;
            row.setHeightInPoints(20);
            
          //创建标题
            cell = row.createCell(0);
            cell.setCellValue(companyName);
            cell.setCellStyle(styleT);
            
            cell = row.createCell(4);
            cell.setCellValue(bundle.getString("hopefulCostTitle"));
            cell.setCellStyle(style);
            
            //outPutDay
            cell = row.createCell(9);
            cell.setCellValue(bundle.getString("outPutDay")+"："+DateUtil.getNewTime(timeByZ,0,DateUtil.dateFormat).replace("-","/"));
            cell.setCellStyle(styleR);
        	
        	row = sheet.createRow(1);
        	row.setHeightInPoints(20);
        	cell = row.createCell(0);
        	
        	
        	row = sheet.createRow(2);
        	row.setHeightInPoints(20);
        	cell = row.createCell(0);
        	//計上（予定）月
        	cell.setCellValue(bundle.getString("planDlvMonth")+"　：");
        	cell.setCellStyle(styleT);
        	cell = row.createCell(1);
        	cell.setCellValue(chooseDate.replace("-","/"));
        	cell.setCellStyle(styleT);
        	
        	row = sheet.createRow(3);
        	row.setHeightInPoints(20);
        	cell = row.createCell(0);
        	cell.setCellValue("");
        	
        	row = sheet.createRow(4);
        	row.setHeightInPoints(20);
        	for(int i = 0;i < 10;i++)
        	{
        		sheet.setColumnWidth(i, 18*256);
        		sheet.setColumnWidth(0, 26*256);
        		sheet.setColumnWidth(3, 30*256);
        		
        		cell = row.createCell(i);
        		cell.setCellValue(bundle.getString("hopefulCost_"+i));
        		cell.setCellStyle(bodyStyle);
        	}
        	Double sumLastMonthResidual = 0.0;
        	Double sumThisMonthHappened = 0.0;
        	Double sumThisMonthAccurate = 0.0;
        	Double sumIsThisMonthAccurate = 0.0;
        	Double sumIsThisMonthHappened = 0.0;
        	
            //创建内容
        	for(int i = 0;i < hopleCostTopList.size();i++)
        	{
        		excelReport = hopleCostTopList.get(i);
        		if(excelReport.getLastMonthResidual() == 0 && excelReport.getThisMonthHappened() == 0 && excelReport.getThisMonthAccurate() == 0 &&
        				excelReport.getIsThisMonthAccurate() == 0 && excelReport.getIsThisMonthHappened() == 0)
        		{
        			hopleCostTopList.remove(i);
        			i--;
        			continue;
        		}
        		if(excelReport.getOrderIndex().equals("1") || excelReport.getOrderIndex().equals("3"))
        		{
        			row = sheet.createRow(i + 5);
    				row.setHeightInPoints(20);
    				CellRangeAddress clTotal = null;
                    cell = row.createCell(0);
                    
                    clTotal=new CellRangeAddress((i + 5), (i + 5), 0,4);
        			sheet.addMergedRegion(clTotal);
        			//得意先計  
        			if(excelReport.getOrderIndex().equals("1"))
        			{
        				cell.setCellValue(bundle.getString("payeeTotal"));
        			}else {
        				cell.setCellValue(bundle.getString("lendTotal"));
        			}
        			
        			cell.setCellStyle(bodyStyle);
        		}else {
        			row = sheet.createRow(i + 5);
            		row.setHeightInPoints(20);
            		cell = row.createCell(0);
            		cell.setCellValue(excelReport.getPayeeName());
            		cell.setCellStyle(bodyLeftStyle);
            		
            		cell = row.createCell(1);
            		String aDate = commonMethedService.getTimeByZone(excelReport.getOrderAddDate(),pars.getCompanyID());
            		if(aDate != "")
            		{
            			aDate = DateUtil.getNewTime(aDate,0,DateUtil.dateFormat);
                		cell.setCellValue(aDate.replace("-","/"));	
            		}
            		
            		
            		cell.setCellStyle(bodyStyle);
            		
            		cell = row.createCell(2);
            		cell.setCellValue(excelReport.getCostNo());
            		cell.setCellStyle(bodyLeftStyle);
            		
            		cell = row.createCell(3);
            		cell.setCellValue(excelReport.getcName());
            		cell.setCellStyle(bodyLeftStyle);
            		
            		cell = row.createCell(4);
            		cell.setCellValue(excelReport.getJobNo());
            		cell.setCellStyle(bodyLeftStyle);
        		}
        		
        		cell = row.createCell(5);
        		cell.setCellValue(excelReport.getLastMonthResidual());
        		cell.setCellStyle(moneyStyle);
        		
        		cell = row.createCell(6);
        		cell.setCellValue(excelReport.getThisMonthHappened());
        		cell.setCellStyle(moneyStyle);
        		
        		cell = row.createCell(7);
        		cell.setCellValue(excelReport.getThisMonthAccurate());
        		cell.setCellStyle(moneyStyle);
        		
        		cell = row.createCell(8);
        		//cell.setCellValue(MathController.sub(excelReport.getLastMonthResidual(), excelReport.getIsThisMonthAccurate()));
        		cell.setCellValue(excelReport.getIsThisMonthAccurate());
        		cell.setCellStyle(moneyStyle);
        		
        		cell = row.createCell(9);
        		//cell.setCellValue(MathController.add(excelReport.getLastMonthResidual(), excelReport.getThisMonthHappened()));
        		cell.setCellValue(excelReport.getIsThisMonthHappened());
        		cell.setCellStyle(moneyStyle);
        		if(excelReport.getOrderIndex().equals("1") || excelReport.getOrderIndex().equals("3"))
        		{
        			sumLastMonthResidual = MathController.add(sumLastMonthResidual,excelReport.getLastMonthResidual());
                	sumThisMonthHappened = MathController.add(sumThisMonthHappened,excelReport.getThisMonthHappened());
                	sumThisMonthAccurate = MathController.add(sumThisMonthAccurate,excelReport.getThisMonthAccurate());
                	sumIsThisMonthAccurate = MathController.add(sumIsThisMonthAccurate,excelReport.getIsThisMonthAccurate());
                	sumIsThisMonthHappened = MathController.add(sumIsThisMonthHappened,excelReport.getIsThisMonthHappened());
        		}
        	}
        	CellRangeAddress clTotal = null;
        	if(hopleCostTopList.size() > 0)
        	{
        		//上半部合计开始
            	row = sheet.createRow(hopleCostTopList.size() + 5);
    			row.setHeightInPoints(20);
    			
                cell = row.createCell(0);
                
                clTotal=new CellRangeAddress((hopleCostTopList.size() + 5), (hopleCostTopList.size() + 5), 0,4);
    			sheet.addMergedRegion(clTotal);
    			//得意先計
    			cell.setCellValue(bundle.getString("total"));
    			cell.setCellStyle(totalCenterStyle);
    			
    			cell = row.createCell(1);
        		cell.setCellValue("");
        		cell.setCellStyle(totalMontyStyle);
        		cell = row.createCell(2);
        		cell.setCellValue("");
        		cell.setCellStyle(totalMontyStyle);
        		cell = row.createCell(3);
        		cell.setCellValue("");
        		cell.setCellStyle(totalMontyStyle);
        		cell = row.createCell(4);
        		cell.setCellValue("");
        		cell.setCellStyle(totalMontyStyle);
    			
    			cell = row.createCell(5);
        		cell.setCellValue(sumLastMonthResidual);
        		cell.setCellStyle(totalMontyStyle);
        		
        		cell = row.createCell(6);
        		cell.setCellValue(sumThisMonthHappened);
        		cell.setCellStyle(totalMontyStyle);
        		
        		cell = row.createCell(7);
        		cell.setCellValue(sumThisMonthAccurate);
        		cell.setCellStyle(totalMontyStyle);
        		
        		cell = row.createCell(8);
        		//cell.setCellValue(MathController.sub(sumLastMonthResidual, sumThisMonthAccurate));
        		cell.setCellValue(sumIsThisMonthAccurate);
        		cell.setCellStyle(totalMontyStyle);
        		
        		cell = row.createCell(9);
        		//cell.setCellValue(MathController.add(sumLastMonthResidual, sumThisMonthHappened));
        		cell.setCellValue(sumIsThisMonthHappened);
        		cell.setCellStyle(totalMontyStyle);
        		//上半部合计结束
        	}
        	
    		
        	
        	int rowIndex = 7 + hopleCostTopList.size();
        	
        	row = sheet.createRow(rowIndex);
        	row.setHeightInPoints(20);
        	cell = row.createCell(0);
        	cell.setCellValue("");
        	
        	row = sheet.createRow(rowIndex);
        	row.setHeightInPoints(20);
        	cell = row.createCell(0);
        	cell.setCellValue("("+bundle.getString("summaryTable")+")");
			cell.setCellStyle(styleT);
			
        	row = sheet.createRow(rowIndex+1);
        	row.setHeightInPoints(20);
        	
                cell = row.createCell(0);
                
                clTotal=new CellRangeAddress((rowIndex+1), (rowIndex+1), 0,4);
    			sheet.addMergedRegion(clTotal);
    			//得意先計
    			cell.setCellValue(bundle.getString("payeeCodeOrName"));
    			cell.setCellStyle(bodyStyle);
    			cell = row.createCell(1);
        		cell.setCellValue("");
        		cell.setCellStyle(bodyStyle);
        		cell = row.createCell(2);
        		cell.setCellValue("");
        		cell.setCellStyle(bodyStyle);
        		cell = row.createCell(3);
        		cell.setCellValue("");
        		cell.setCellStyle(bodyStyle);
        		cell = row.createCell(4);
        		cell.setCellValue("");
        		cell.setCellStyle(bodyStyle);
    		for(int i = 5;i < 10;i++)
        	{
        		cell = row.createCell(i);
        		cell.setCellValue(bundle.getString("hopefulCost_"+i));
        		cell.setCellStyle(bodyStyle);
        	}
        	Double sumLastMonthResidualB = 0.0;
        	Double sumThisMonthHappenedB = 0.0;
        	Double sumThisMonthAccurateB = 0.0;
        	Double sumIsThisMonthAccurateB = 0.0;
        	Double sumIsThisMonthHappenedB = 0.0;
        	int bRowIndex = rowIndex+2;
        	for(int i = 0;i < hopleCostTopList.size();i++)
        	{
        		excelReport = hopleCostTopList.get(i);
        		if(!excelReport.getOrderIndex().equals("1") && !excelReport.getOrderIndex().equals("3"))
        		{
        			continue;
        		}
        		
        		row = sheet.createRow(bRowIndex);
				row.setHeightInPoints(20);
                cell = row.createCell(0);
                
                clTotal=new CellRangeAddress(bRowIndex, bRowIndex, 0,4);
    			sheet.addMergedRegion(clTotal);
    			//得意先計
    			cell.setCellValue(excelReport.getCldivAccountID()+"/"+excelReport.getPayeeName());
    			cell.setCellStyle(bodyStyle);
        		
    			cell = row.createCell(1);
        		cell.setCellValue("");
        		cell.setCellStyle(bodyStyle);
        		cell = row.createCell(2);
        		cell.setCellValue("");
        		cell.setCellStyle(bodyStyle);
        		cell = row.createCell(3);
        		cell.setCellValue("");
        		cell.setCellStyle(bodyStyle);
        		cell = row.createCell(4);
        		cell.setCellValue("");
        		cell.setCellStyle(bodyStyle);
        		
        		cell = row.createCell(5);
        		cell.setCellValue(excelReport.getLastMonthResidual());
        		cell.setCellStyle(moneyStyle);
        		
        		cell = row.createCell(6);
        		cell.setCellValue(excelReport.getThisMonthHappened());
        		cell.setCellStyle(moneyStyle);
        		
        		cell = row.createCell(7);
        		cell.setCellValue(excelReport.getThisMonthAccurate());
        		cell.setCellStyle(moneyStyle);
        		
        		cell = row.createCell(8);
        		//cell.setCellValue(MathController.sub(excelReport.getLastMonthResidual(), excelReport.getIsThisMonthAccurate()));
        		cell.setCellValue(excelReport.getIsThisMonthAccurate());
        		cell.setCellStyle(moneyStyle);
        		
        		cell = row.createCell(9);
        		//cell.setCellValue(MathController.add(excelReport.getLastMonthResidual(), excelReport.getThisMonthHappened()));
        		cell.setCellValue(excelReport.getIsThisMonthHappened());
        		cell.setCellStyle(moneyStyle);
        		
        		sumLastMonthResidualB = MathController.add(sumLastMonthResidualB,excelReport.getLastMonthResidual());
            	sumThisMonthHappenedB = MathController.add(sumThisMonthHappenedB,excelReport.getThisMonthHappened());
            	sumThisMonthAccurateB = MathController.add(sumThisMonthAccurateB,excelReport.getThisMonthAccurate());
            	sumIsThisMonthAccurateB = MathController.add(sumIsThisMonthAccurateB,excelReport.getIsThisMonthAccurate());
            	sumIsThisMonthHappenedB = MathController.add(sumIsThisMonthHappenedB,excelReport.getIsThisMonthHappened());
            	bRowIndex++;
        	}
        	if(hopleCostTopList.size() > 0)
        	{
        		//下半部合计开始
            	row = sheet.createRow(bRowIndex);
    			row.setHeightInPoints(20);
                cell = row.createCell(0);
                
                clTotal=new CellRangeAddress(bRowIndex, bRowIndex, 0,4);
    			sheet.addMergedRegion(clTotal);
    			//得意先計
    			cell.setCellValue(bundle.getString("total"));
    			cell.setCellStyle(totalCenterStyle);
    			
    			cell = row.createCell(1);
        		cell.setCellValue("");
        		cell.setCellStyle(totalMontyStyle);
        		cell = row.createCell(2);
        		cell.setCellValue("");
        		cell.setCellStyle(totalMontyStyle);
        		cell = row.createCell(3);
        		cell.setCellValue("");
        		cell.setCellStyle(totalMontyStyle);
        		cell = row.createCell(4);
        		cell.setCellValue("");
        		cell.setCellStyle(totalMontyStyle);
    			
    			cell = row.createCell(5);
        		cell.setCellValue(sumLastMonthResidualB);
        		cell.setCellStyle(totalMontyStyle);
        		
        		cell = row.createCell(6);
        		cell.setCellValue(sumThisMonthHappenedB);
        		cell.setCellStyle(totalMontyStyle);
        		
        		cell = row.createCell(7);
        		cell.setCellValue(sumThisMonthAccurateB);
        		cell.setCellStyle(totalMontyStyle);
        		
        		cell = row.createCell(8);
        		cell.setCellValue(sumIsThisMonthAccurateB);
        		cell.setCellStyle(totalMontyStyle);
        		
        		cell = row.createCell(9);
        		cell.setCellValue(sumIsThisMonthHappenedB);
        		cell.setCellStyle(totalMontyStyle);
        		//下半部合计结束
        	}
        	
    		/*
            FileOutputStream fout = new FileOutputStream("D:\\\\jspertxmls\\a.xls");  
            workbook.write(fout);  
            fout.close();  
            return null;
            */
            ByteArrayOutputStream os = new ByteArrayOutputStream();
           	workbook.write(os);
           	byte[] buffer = os.toByteArray();
           	return Base64.getEncoder().encodeToString(buffer);
           	
        }catch(Exception e){
            //e.printStackTrace();
        	return "error";
        }
	}
	//当月卖上明细
	public String exportMonthDetailReport(OutPutInput pars) {
		if(validateChooeseDate(pars.getEndDate(),pars.getCompanyID(),false))
		{
			return "DATE_RANGE_ERROR";
		}
		
		String startDate = commonMethedMapper.searchStartTimeBySummaryMonth(Integer.valueOf(pars.getCompanyID()),DateUtil.makeDate(pars.getEndDate()));
		String endDate = commonMethedMapper.searchEndTimeBySummaryMonth(Integer.valueOf(pars.getCompanyID()),pars.getEndDate());
		List<ExcelReport> jobList = exportExcelMapper.getRportJobList(pars.getCompanyID(),pars.getEndDate(),startDate);
		ExcelReport excelReport = new ExcelReport();
		//Calculate calculate = new Calculate();
		Double beforMonthTax = 0.0;
		List<Cost> costListBefor = new ArrayList<Cost>();
		List<Cost> costListNow = new ArrayList<Cost>();
		boolean costNow = true;
		boolean costBefor = true;
		if(jobList.size() == 1)
		{
			jobList.remove(0);
		}
		for(int i = 0;i < jobList.size();i++)
		{
			//jobList.get(i).setSellupdate(DateUtil.dateToString(DateUtil.stringtoDate(jobList.get(i).getSellupdate(),DateUtil.dateFormat), DateUtil.dateFormat));
			if(jobList.get(i).getJobNo().equals("J999999998") || jobList.get(i).getJobNo().equals("J999999999"))
			{
				continue;
			}
			costNow = true;
			costBefor = true;
			costListBefor = commonMethedMapper.getCostList(jobList.get(i).getJobNo(),DateUtil.nextDayDate(pars.getEndDate()),Integer.valueOf(pars.getCompanyID()),"beforMonth");
			costListNow = commonMethedMapper.getCostList(jobList.get(i).getJobNo(),pars.getEndDate(),Integer.valueOf(pars.getCompanyID()),"nowMonth");
			
			if(!DateUtil.stringtoDate(jobList.get(i).getDlvday(), DateUtil.ACCOUNT_MONTH).equals(DateUtil.stringtoDate(pars.getEndDate(), DateUtil.ACCOUNT_MONTH)))
			{
				if(costListBefor.size() > 0 && costListNow.size() > 0)
				{
					for(int n = 0;n < costListBefor.size();n++)
					{
						costNow = true;
						costBefor = true;
						for(int m = 0;m < costListNow.size();m++)
						{
							if(costListBefor.get(n).getCostno().equals(costListNow.get(m).getCostno()))
							{
								if(!costListBefor.get(n).getCostrmb().equals(costListNow.get(m).getCostrmb()) || !costListBefor.get(n).getStatus().equals(costListNow.get(m).getStatus()))
								{
									costBefor = false;
									break;
								}
							}
						}
						if(!costBefor)
						{
							costNow=false;
							break;
						}
					}
					if(costNow)
					{
						jobList.remove(i);
						i--;
						continue;
					}
				}else {
					if(jobList.get(i).getIsThisMonthJob() != 1)
					{
						jobList.remove(i);
						i--;
						continue;
					}
					/*
					if(costListNow.size() < 1)
					{
						jobList.remove(i);
						i--;
						continue;
					}
					*/
				}
			}
			
			//确定原价，仕入増値税
			excelReport = exportExcelMapper.getConfirmAmtAndConfirmVatByJoB(pars.getCompanyID(), jobList.get(i).getJobNo(), startDate, endDate,pars.getEndDate());
			if (excelReport != null) {
				jobList.get(i).setConfirmCostTotalAmt(excelReport.getConfirmCostTotalAmt());
				jobList.get(i).setCostVatTotal(excelReport.getCostVatTotal());
			}else {
				jobList.get(i).setConfirmCostTotalAmt(0.0);
				jobList.get(i).setCostVatTotal(0.0);
			}
			//見込原価合計
			excelReport = exportExcelMapper.getNotConfirmAmtByJoB(pars.getCompanyID(), jobList.get(i).getJobNo(), startDate, endDate,DateUtil.makeDate(pars.getEndDate()),pars.getEndDate());
			if (excelReport != null) {
				jobList.get(i).setNotConfirmCostTotalAmt(excelReport.getNotConfirmCostTotalAmt());
			}else {
				jobList.get(i).setNotConfirmCostTotalAmt(0.0);
			}
			
			String aDate = commonMethedService.getTimeByZone(jobList.get(i).getSellupdate(), pars.getCompanyID());
    		if(aDate != "")
    		{
    			aDate = DateUtil.getNewTime(aDate,0,DateUtil.dateFormat);
    			jobList.get(i).setSellupdate(aDate);
    		}
			if(jobList.get(i).getIsThisMonthJob() == 1)
			{
				//当月记上JOB，计算税金
				beforMonthTax = commonMethedService.calcuateJobTax(jobList.get(i).getJobNo(),Integer.valueOf(pars.getCompanyID()),DateUtil.makeDate(pars.getEndDate()),startDate,endDate,pars.getEndDate(),1);
				jobList.get(i).setTaxTotal(beforMonthTax);
			}else {
				//前月记上JOB，计算税金
				beforMonthTax = commonMethedService.calcuateJobTax(jobList.get(i).getJobNo(),Integer.valueOf(pars.getCompanyID()),DateUtil.makeDate(pars.getEndDate()),startDate,endDate,pars.getEndDate(),0);
				jobList.get(i).setTaxTotal(beforMonthTax);
			}
		}
		
		String companyName = exportExcelMapper.getCompanyName(pars.getCompanyID());
		String timeByZ = commonMethedService.getTimeByZone(DateUtil.getDateForNow(DateUtil.dateTimeFormat), pars.getCompanyID());
		int pointNumber = commonMethedMapper.getPointNumberByCompany(Integer.valueOf(pars.getCompanyID()));
		List<CommonmstVo> foreignCodeVO = jobLandMapper.selectSaleCode(Integer.valueOf(pars.getCompanyID()),"001","0051");
		int foreignFormatFlg = Integer.valueOf(foreignCodeVO.get(0).getAidcd());
		String pointNum = "";
		for(int i = 0;i < pointNumber;i++)
		{
			if(i == 0)
			{
				pointNum += ".";
			}
			pointNum += "0";
		}
		String chooseDate = "";
    	if(!pars.getEndDate().equals(""))
    	{
    		chooseDate += DateUtil.dateToString(DateUtil.stringtoDate(pars.getEndDate(), DateUtil.dateFormat), DateUtil.accountDateFormat);
    	}
    	String fontName = getFontNameByLangT(pars.getLangTyp());
		try{
			Locale local = JasperOutPdfUtil.getLocalByLangT(pars.getLangTyp());
			ResourceBundle bundle = ResourceBundle.getBundle(JasperOutPdfUtil.BUNDLE_NAME , local);
        	// 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        	XSSFWorkbook workbook = new XSSFWorkbook();
        	
        	//FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
    		//CellValue cellValue = null;
        	// 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        	XSSFSheet sheet = workbook.createSheet(pars.getSheetName());
        	//CellRangeAddress titleCell=new CellRangeAddress(0, 0, 1,4);
            //sheet.addMergedRegion(titleCell);
        	// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
            XSSFRow row = sheet.createRow(0);
            // 第四步，创建单元格，并设置值表头 设置表头居中
            
            XSSFFont titleFont = getExcelFont(workbook,(short)14,fontName); 
            XSSFFont bodyFont = getExcelFont(workbook,(short)10,fontName);
            
            
            
            XSSFDataFormat format= workbook.createDataFormat();
            String moneyUnit = getCompanyMoneyUnit(pars.getCompanyID());
            String formatStr = moneyUnit + "#,##0"+pointNum;
            String porfitRateformatStr = "@";
            XSSFCellStyle porfitRateStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, format,porfitRateformatStr,true);
            XSSFCellStyle porfitRateStyleBlack = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, format,porfitRateformatStr,true);
            porfitRateStyleBlack.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
			porfitRateStyleBlack.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			XSSFCellStyle style = getExcelStyle(workbook, titleFont, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER, 0, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",false);
			XSSFCellStyle styleT = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER, 0, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",false);
			XSSFCellStyle styleR = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER, 0, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",false);
			XSSFCellStyle bodyStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",true);
			XSSFCellStyle bodyLeftStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",true);
			XSSFCellStyle bodyRightStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",true);
           
			XSSFCellStyle moneyStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, format,formatStr,true);
			XSSFCellStyle totalMontyStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, format,formatStr,true);
			XSSFCellStyle sStyle = workbook.createCellStyle();
            XSSFCell cell = null;
            row.setHeightInPoints(20);
            
            //CellRangeAddress clTotal1 = null;
            //clTotal1=new CellRangeAddress(0, 0, 1,13);
			//sheet.addMergedRegion(clTotal1);
			
          //创建标题
            cell = row.createCell(0);
            cell.setCellValue(companyName);
            cell.setCellStyle(styleT);
            cell = row.createCell(6);
            cell.setCellValue(bundle.getString("monthBalanceTitle"));
            cell.setCellStyle(style);
            cell = row.createCell(14);
            //outPutDay
            cell.setCellValue(bundle.getString("outPutDay")+"："+DateUtil.getNewTime(timeByZ,0,DateUtil.dateFormat).replace("-","/"));
            cell.setCellStyle(styleR);
        	
        	row = sheet.createRow(1);
        	row.setHeightInPoints(20);
        	cell = row.createCell(0);
        	
        	
        	row = sheet.createRow(2);
        	row.setHeightInPoints(20);
        	cell = row.createCell(0);
        	//計上（予定）月
        	cell.setCellValue(bundle.getString("planDlvMonth")+"　：");
        	cell.setCellStyle(styleT);
        	cell = row.createCell(1);
        	cell.setCellValue(chooseDate.replace("-","/"));
        	cell.setCellStyle(styleT);
        	
        	row = sheet.createRow(3);
        	row.setHeightInPoints(20);
        	cell = row.createCell(0);
        	cell.setCellValue("");
        	
        	row = sheet.createRow(4);
        	row.setHeightInPoints(20);
        	for(int i = 0;i < 15;i++)
        	{
        		sheet.setColumnWidth(i, 24*256);
        		sheet.setColumnWidth(0, 30*256);
        		sheet.setColumnWidth(1, 16*256);
        		sheet.setColumnWidth(2, 30*256);
        		sheet.setColumnWidth(3, 20*256);
        		cell = row.createCell(i);
        		cell.setCellValue(bundle.getString("monthBalance_"+i));
        		cell.setCellStyle(bodyStyle);
        	}
        	int startRowIndex = 5;
        	String dldivcd = "";
        	@SuppressWarnings("unused")
			int sumIndex = 6;
        	String cellFormula = "";
        	Double profitAmt = 0.0; 
        	Double sumTopConfirmOrderAmtByClient = 0.0;
        	Double sumTopNotConfirmOrderAmtByClient = 0.0;
        	Double sumTopConfirmOrderBVatAmtByClient = 0.0;
        	Double sumTopTaxAmtByClient = 0.0;
        	
        	Double sumTopSaleAmt = 0.0;
        	Double sumTopSaleVatAmt = 0.0;
        	Double sumTopConfirmOrderAmt = 0.0;
        	Double sumTopNotConfirmOrderAmt = 0.0;
        	Double sumTopConfirmOrderBVatAmt = 0.0;
        	Double sumTopTaxAmt = 0.0;
        	Double sumTopProfitAmt = 0.0;
        	String sumTopProfitRateAmt = "";
        	for(int i = 0;i < jobList.size();i++)
        	{
        		excelReport = jobList.get(i);
        		if(i == 0 && (excelReport.getJobNo().equals("J999999998") || excelReport.getJobNo().equals("J999999999")))
        		{
        			jobList.remove(i);
        			i--;
        			continue;
        		}
        		
        		row = sheet.createRow(i+startRowIndex);
        		row.setHeightInPoints(20);
        		
        		if(i == 0)
        		{
        			dldivcd = excelReport.getCldivID(); 
        		}else{
        			if(!dldivcd.equals(excelReport.getCldivID()))
        			{
        				dldivcd = excelReport.getCldivID();
        				sumTopConfirmOrderAmtByClient = new Double(0.0);
        	        	sumTopNotConfirmOrderAmtByClient = new Double(0.0);
        	        	sumTopConfirmOrderBVatAmtByClient = new Double(0.0);
        	        	sumTopTaxAmtByClient = new Double(0.0);
        				//sumIndex = sumIndex+i+1;
        			}
        		}
        		if(excelReport.getJobNo().equals("J999999998") && !excelReport.getCldivID().equals(jobList.get(i-1).getCldivID()))
        		{
        			jobList.remove(i);
        			i--;
        			continue;
        		}
        		CellRangeAddress clTotal = null;
        		if(excelReport.getJobNo().equals("J999999998") || excelReport.getJobNo().equals("J999999999"))
        		{
                    clTotal=new CellRangeAddress((i+startRowIndex),(i+startRowIndex), 0,3);
        			sheet.addMergedRegion(clTotal);
        			cell = row.createCell(0);
        			sStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, format,formatStr,true);
        			
        			if(excelReport.getJobNo().equals("J999999998"))
        			{
        				//sStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",true);
        				cell.setCellValue(bundle.getString("cldivTotal"));
        				cell.setCellStyle(bodyStyle);
        				cellFormula += (i+startRowIndex+1)+",";
        			}else {
        				sStyle.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
        				sStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        				bodyRightStyle.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
        				bodyRightStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        				totalMontyStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, format,formatStr,true);
        				totalMontyStyle.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
        				totalMontyStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        				cell.setCellValue(bundle.getString("total"));
        				cell.setCellStyle(totalMontyStyle);
        				sumIndex = 6;
        			}
        			cell = row.createCell(1);
        			cell.setCellValue("");
    				cell.setCellStyle(sStyle);
    				cell = row.createCell(2);
        			cell.setCellValue("");
    				cell.setCellStyle(sStyle);
    				cell = row.createCell(3);
        			cell.setCellValue("");
    				cell.setCellStyle(sStyle);
            		cell = row.createCell(4);
            		cell.setCellValue(excelReport.getSaleAmt());
            		cell.setCellStyle(sStyle);
            		cell = row.createCell(5);
            		cell.setCellValue(excelReport.getSaleVat());
            		cell.setCellStyle(sStyle);
            		if(excelReport.getJobNo().equals("J999999998"))
            		{
            			jobList.get(i).setConfirmCostTotalAmtByClinet(sumTopConfirmOrderAmtByClient);
            			jobList.get(i).setNotConfirmCostTotalAmtByClinet(sumTopNotConfirmOrderAmtByClient);
            			jobList.get(i).setCostVatTotalByClinet(sumTopConfirmOrderBVatAmtByClient);
            			jobList.get(i).setTaxTotalByClinet(sumTopTaxAmtByClient);
            			
            			
            			sumTopConfirmOrderAmt = MathController.add(sumTopConfirmOrderAmt,sumTopConfirmOrderAmtByClient);
                    	sumTopNotConfirmOrderAmt = MathController.add(sumTopNotConfirmOrderAmt,sumTopNotConfirmOrderAmtByClient);
                    	sumTopConfirmOrderBVatAmt = MathController.add(sumTopConfirmOrderBVatAmt,sumTopConfirmOrderBVatAmtByClient);
                    	sumTopTaxAmt = MathController.add(sumTopTaxAmt,sumTopTaxAmtByClient);
            			cell = row.createCell(6);
            			cell.setCellValue(sumTopConfirmOrderAmtByClient);
                		//cell.setCellFormula("SUM(G"+sumIndex+":G"+(i+startRowIndex)+")");
                		cell.setCellStyle(sStyle);
                		cell = row.createCell(7);
                		cell.setCellValue(sumTopNotConfirmOrderAmtByClient);
                		//cell.setCellFormula("SUM(H"+sumIndex+":H"+(i+startRowIndex)+")");
                		cell.setCellStyle(sStyle);
                		cell = row.createCell(8);
                		cell.setCellValue(sumTopConfirmOrderBVatAmtByClient);
                		//cell.setCellFormula("SUM(I"+sumIndex+":I"+(i+startRowIndex)+")");
                		cell.setCellStyle(sStyle);
                		cell = row.createCell(9);
                		cell.setCellValue(sumTopTaxAmtByClient);
                		//cell.setCellFormula("SUM(J"+sumIndex+":J"+(i+startRowIndex)+")");
                		cell.setCellStyle(sStyle);
                		cell = row.createCell(10);
                		
                		
                		profitAmt = MathController.sub(MathController.sub(MathController.sub(excelReport.getSaleAmt(),sumTopConfirmOrderAmtByClient),sumTopNotConfirmOrderAmtByClient),sumTopTaxAmtByClient);
                		jobList.get(i).setProfitAmt(profitAmt);
                		cell.setCellValue(profitAmt);
                		//cell.setCellFormula("E"+(i+startRowIndex+1)+"-G"+(i+startRowIndex+1)+"-H"+(i+startRowIndex+1)+"-J"+(i+startRowIndex+1));
                		//cellValue = evaluator.evaluate(cell);
                		//profitAmt = cellValue.getNumberValue();
                		
                		cell.setCellStyle(sStyle);
                		cell = row.createCell(11);
                		if(excelReport.getSaleAmt()==0.0||excelReport.getSaleAmt()==null)
                		{
                			cell.setCellValue("INF");
                			jobList.get(i).setProfitAmtRate("INF");
                		}else {
                			String prfitRate = calcuateProfitRate(excelReport.getSaleAmt(),profitAmt,foreignFormatFlg,pointNumber)+"%"; 
                			cell.setCellValue(prfitRate);
                			jobList.get(i).setProfitAmtRate(prfitRate);
                		}
                		//cell.setCellFormula("IF(K"+(i+startRowIndex)+"=0,\"Infinity\",K"+(i+startRowIndex)+"/E"+(i+startRowIndex)+"*100)");
                		cell.setCellStyle(totalMontyStyle);
            		}else {
            			//String rowIndexNums[] = cellFormula.split(",");
            			sumTopSaleAmt = excelReport.getSaleAmt();
                    	sumTopSaleVatAmt = excelReport.getSaleVat();
                    	
                    	sumTopProfitAmt = MathController.sub(MathController.sub(MathController.sub(sumTopSaleAmt, sumTopConfirmOrderAmt),sumTopNotConfirmOrderAmt),sumTopTaxAmt);
                    	sumTopProfitRateAmt = calcuateProfitRate(excelReport.getSaleAmt(),sumTopProfitAmt,foreignFormatFlg,pointNumber)+"%";
                    	
            			cell = row.createCell(6);
            			cell.setCellValue(sumTopConfirmOrderAmt);
                		//cell.setCellFormula(getSumTotalByCell("G",cellFormula));
                		cell.setCellStyle(sStyle);
                		cell = row.createCell(7);
                		cell.setCellValue(sumTopNotConfirmOrderAmt);
                		//cell.setCellFormula(getSumTotalByCell("H",cellFormula));
                		cell.setCellStyle(sStyle);
                		cell = row.createCell(8);
                		cell.setCellValue(sumTopConfirmOrderBVatAmt);
                		//cell.setCellFormula(getSumTotalByCell("I",cellFormula));
                		cell.setCellStyle(sStyle);
                		cell = row.createCell(9);
                		cell.setCellValue(sumTopTaxAmt);
                		//cell.setCellFormula(getSumTotalByCell("J",cellFormula));
                		cell.setCellStyle(sStyle);
                		cell = row.createCell(10);
                		cell.setCellValue(sumTopProfitAmt);
                		//cell.setCellFormula("E"+(i+startRowIndex+1)+"-G"+(i+startRowIndex+1)+"-H"+(i+startRowIndex+1)+"-J"+(i+startRowIndex+1));
                		//cellValue = evaluator.evaluate(cell);
                		//profitAmt = cellValue.getNumberValue();
                		cell.setCellStyle(sStyle);
                		cell = row.createCell(11);
                		if(excelReport.getSaleAmt()==0.0||excelReport.getSaleAmt()==null)
                		{
                			cell.setCellValue("INF");
                			sumTopProfitRateAmt = "INF";
                		}else {
                			cell.setCellValue(sumTopProfitRateAmt);
                		}
                		//cell.setCellFormula("IF(K"+(i+startRowIndex)+"=0,\"Infinity\",K"+(i+startRowIndex)+"/E"+(i+startRowIndex)+"*100)");
                		cell.setCellStyle(porfitRateStyleBlack);
                		
            		}
            		
            		cell = row.createCell(12);
            		cell.setCellValue("");
            		cell.setCellStyle(sStyle);
            		cell = row.createCell(13);
            		cell.setCellValue("");
            		cell.setCellStyle(sStyle);
            		cell = row.createCell(14);
            		cell.setCellValue("");
            		cell.setCellStyle(sStyle);
            		sumIndex = 6+i+1;
        		}else {
        			sumTopConfirmOrderAmtByClient = MathController.add(sumTopConfirmOrderAmtByClient,excelReport.getConfirmCostTotalAmt());
                	sumTopNotConfirmOrderAmtByClient = MathController.add(sumTopNotConfirmOrderAmtByClient,excelReport.getNotConfirmCostTotalAmt());
                	sumTopConfirmOrderBVatAmtByClient = MathController.add(sumTopConfirmOrderBVatAmtByClient,excelReport.getCostVatTotal());
                	sumTopTaxAmtByClient = MathController.add(sumTopTaxAmtByClient,excelReport.getTaxTotal());
                	
        			cell = row.createCell(0);
            		cell.setCellValue(excelReport.getdClientName());
            		cell.setCellStyle(bodyLeftStyle);
            		cell = row.createCell(1);
            		cell.setCellValue(excelReport.getJobNo());
            		cell.setCellStyle(bodyLeftStyle);
            		cell = row.createCell(2);
            		cell.setCellValue(excelReport.getJobName());
            		cell.setCellStyle(bodyLeftStyle);
            		cell = row.createCell(3);
            		cell.setCellValue(excelReport.getSaleTypName());
            		cell.setCellStyle(bodyLeftStyle);
            		cell = row.createCell(4);
            		cell.setCellValue(excelReport.getSaleAmt());
            		cell.setCellStyle(moneyStyle);
            		cell = row.createCell(5);
            		cell.setCellValue(excelReport.getSaleVat());
            		cell.setCellStyle(moneyStyle);
            		cell = row.createCell(6);
            		cell.setCellValue(excelReport.getConfirmCostTotalAmt());
            		cell.setCellStyle(moneyStyle);
            		cell = row.createCell(7);
            		cell.setCellValue(excelReport.getNotConfirmCostTotalAmt());
            		cell.setCellStyle(moneyStyle);
            		cell = row.createCell(8);
            		cell.setCellValue(excelReport.getCostVatTotal());
            		cell.setCellStyle(moneyStyle);
            		cell = row.createCell(9);
            		cell.setCellValue(excelReport.getTaxTotal());
            		cell.setCellStyle(moneyStyle);
            		cell = row.createCell(10);
            		profitAmt = MathController.sub(MathController.sub(MathController.sub(excelReport.getSaleAmt(), excelReport.getConfirmCostTotalAmt()), excelReport.getNotConfirmCostTotalAmt()), excelReport.getTaxTotal());
            		cell.setCellValue(profitAmt);
            		//cell.setCellFormula("E"+(i+startRowIndex+1)+"-G"+(i+startRowIndex+1)+"-H"+(i+startRowIndex+1)+"-J"+(i+startRowIndex+1));
            		//cellValue = evaluator.evaluate(cell);
            		//profitAmt = cellValue.getNumberValue();
            		
            		cell.setCellStyle(moneyStyle);
            		cell = row.createCell(11);
            		if(excelReport.getSaleAmt()==0.0||excelReport.getSaleAmt()==null)
            		{
            			cell.setCellValue("INF");
            		}else {
            			cell.setCellValue(calcuateProfitRate(excelReport.getSaleAmt(),profitAmt,foreignFormatFlg,pointNumber)+"%");
            		}
            		//cell.setCellFormula("K"+(i+startRowIndex+1)+"/E"+(i+startRowIndex+1)+"*100");
            		//cell.setCellFormula("IF(K"+(i+startRowIndex)+"=0,\"Infinity\",K"+(i+startRowIndex)+"/E"+(i+startRowIndex)+"*100)");
            		cell.setCellStyle(porfitRateStyle);
            		cell = row.createCell(12);
            		cell.setCellValue(excelReport.getSellupdate().replace("-", "/"));
            		cell.setCellStyle(bodyStyle);
            		cell = row.createCell(13);
            		if(excelReport.getIsReqPublish() == 1)
            		{
            			cell.setCellValue(bundle.getString("finish"));
            		}else {
            			cell.setCellValue(bundle.getString("noFinish"));
            		}
            		cell.setCellStyle(bodyStyle);
            		cell = row.createCell(14);
            		if(excelReport.getIsInvoicePublish() == 1)
            		{
            			cell.setCellValue(bundle.getString("finish"));
            		}else {
            			cell.setCellValue(bundle.getString("noFinish"));
            		}
            		cell.setCellStyle(bodyStyle);
        		}
        	}
        	
        	row = sheet.createRow(7+jobList.size());
        	row.setHeightInPoints(20);
        	cell = row.createCell(0);
        	cell.setCellValue("("+bundle.getString("summaryTable")+")");
        	cell.setCellStyle(styleT);
        	
        	int bRowIndex = 8+jobList.size();
        	row = sheet.createRow(bRowIndex);
        	row.setHeightInPoints(20);
        	sheet.setColumnWidth(0, 36*256);
        	CellRangeAddress clTotal = null;
            clTotal=new CellRangeAddress(bRowIndex, bRowIndex, 0,3);
			sheet.addMergedRegion(clTotal);
			cell = row.createCell(0);
    		cell.setCellValue(bundle.getString("clientCodeOrName"));
    		cell.setCellStyle(bodyStyle);
    		cell = row.createCell(1);
    		cell.setCellStyle(bodyStyle);
    		cell = row.createCell(2);
    		cell.setCellStyle(bodyStyle);
    		cell = row.createCell(3);
    		cell.setCellStyle(bodyStyle);
        	for(int i = 4;i < 12;i++)
        	{
        		sheet.setColumnWidth(i, 24*256);
        		cell = row.createCell(i);
        		cell.setCellValue(bundle.getString("monthBalance_"+i));
        		cell.setCellStyle(bodyStyle);
        	}
        	bRowIndex = bRowIndex+1;
        	int totalIndex = 0;
        	@SuppressWarnings("unused")
			String str[] = cellFormula.split(",");
        	for(int i = 0;i < jobList.size();i++)
        	{
        		
            	excelReport = jobList.get(i);
            	if(!excelReport.getJobNo().equals("J999999998"))
            	{
            		continue;
            	}
            	row = sheet.createRow(bRowIndex+totalIndex);
            	clTotal=new CellRangeAddress((bRowIndex+totalIndex), (bRowIndex+totalIndex), 0,3);
    			sheet.addMergedRegion(clTotal);
            	row.setHeightInPoints(20);
            	cell = row.createCell(0);
        		cell.setCellValue(excelReport.getCldivAccountID()+"/"+excelReport.getdClientName());
        		cell.setCellStyle(bodyStyle);
        		cell = row.createCell(1);
        		cell.setCellStyle(bodyStyle);
        		cell = row.createCell(2);
        		cell.setCellStyle(bodyStyle);
        		cell = row.createCell(3);
        		cell.setCellStyle(bodyStyle);
        		
        		cell = row.createCell(4);
        		//cell.setCellFormula("E"+str[totalIndex]);
        		cell.setCellValue(excelReport.getSaleAmt());
        		cell.setCellStyle(moneyStyle);
        		cell = row.createCell(5);
        		//cell.setCellFormula("F"+str[totalIndex]);
        		cell.setCellValue(excelReport.getSaleVat());
        		cell.setCellStyle(moneyStyle);
        		cell = row.createCell(6);
        		cell.setCellValue(excelReport.getConfirmCostTotalAmtByClinet());
        		//cell.setCellFormula("G"+str[totalIndex]);
        		cell.setCellStyle(moneyStyle);
        		cell = row.createCell(7);
        		cell.setCellValue(excelReport.getNotConfirmCostTotalAmtByClinet());
        		//cell.setCellFormula("H"+str[totalIndex]);
        		cell.setCellStyle(moneyStyle);
        		cell = row.createCell(8);
        		cell.setCellValue(excelReport.getCostVatTotalByClinet());
        		//cell.setCellFormula("I"+str[totalIndex]);
        		cell.setCellStyle(moneyStyle);
        		cell = row.createCell(9);
        		cell.setCellValue(excelReport.getTaxTotalByClinet());
        		//cell.setCellFormula("J"+str[totalIndex]);
        		cell.setCellStyle(moneyStyle);
        		cell = row.createCell(10);
        		cell.setCellValue(excelReport.getProfitAmt());
        		//cell.setCellFormula("K"+str[totalIndex]);
        		cell.setCellStyle(moneyStyle);
        		cell = row.createCell(11);
        		cell.setCellValue(excelReport.getProfitAmtRate());
        		//cell.setCellFormula("L"+str[totalIndex]);
        		cell.setCellStyle(moneyStyle);
            	totalIndex++;
        	}
        	if(jobList.size() > 0)
        	{
        		row = sheet.createRow(bRowIndex+totalIndex);
            	clTotal=new CellRangeAddress((bRowIndex+totalIndex), (bRowIndex+totalIndex), 0,3);
    			sheet.addMergedRegion(clTotal);
            	row.setHeightInPoints(20);
            	cell = row.createCell(0);
            	cell.setCellValue(bundle.getString("total"));
        		cell.setCellStyle(totalMontyStyle);
        		cell = row.createCell(1);
        		cell.setCellStyle(totalMontyStyle);
        		cell = row.createCell(2);
        		cell.setCellStyle(totalMontyStyle);
        		cell = row.createCell(3);
        		cell.setCellStyle(totalMontyStyle);
        		//int strLasterIndex = Integer.valueOf(str[str.length-1])+1;
        		cell = row.createCell(4);
        		cell.setCellValue(sumTopSaleAmt);
        		//cell.setCellFormula("E"+strLasterIndex);
        		cell.setCellStyle(sStyle);
        		cell = row.createCell(5);
        		cell.setCellValue(sumTopSaleVatAmt);
        		//cell.setCellFormula("F"+strLasterIndex);
        		cell.setCellStyle(sStyle);
        		cell = row.createCell(6);
        		cell.setCellValue(sumTopConfirmOrderAmt);
        		//cell.setCellFormula("G"+strLasterIndex);
        		cell.setCellStyle(sStyle);
        		cell = row.createCell(7);
        		cell.setCellValue(sumTopNotConfirmOrderAmt);
        		//cell.setCellFormula("H"+strLasterIndex);
        		cell.setCellStyle(sStyle);
        		cell = row.createCell(8);
        		cell.setCellValue(sumTopConfirmOrderBVatAmt);
        		//cell.setCellFormula("I"+strLasterIndex);
        		cell.setCellStyle(sStyle);
        		cell = row.createCell(9);
        		cell.setCellValue(sumTopTaxAmt);
        		//cell.setCellFormula("J"+strLasterIndex);
        		cell.setCellStyle(sStyle);
        		cell = row.createCell(10);
            	cell.setCellValue(sumTopProfitAmt);
        		//cell.setCellFormula("K"+strLasterIndex);
        		cell.setCellStyle(sStyle);
        		cell = row.createCell(11);
        		cell.setCellValue(sumTopProfitRateAmt);
        		//cell.setCellFormula("L"+strLasterIndex);
        		cell.setCellStyle(sStyle);
        	}
        	
    		 /*
            FileOutputStream fout = new FileOutputStream("D:\\\\jspertxmls\\asda.xls");  
            workbook.write(fout);  
            fout.close();  
            return null;
           	*/
            ByteArrayOutputStream os = new ByteArrayOutputStream();
           	workbook.write(os);
           	byte[] buffer = os.toByteArray();
           
           	return Base64.getEncoder().encodeToString(buffer);
           
        }catch(Exception e){
            //e.printStackTrace();
        	return "error";
        }
	}
//	private String getSumTotalByCell(String keyCode,String cellFormula)
//	{
//		String str[] = cellFormula.split(",");
//		String numStr = "sum(";
//		for(int i = 0;i < str.length;i++)
//		{
//			numStr += keyCode + str[i];
//			if(i != str.length-1)
//			{
//				numStr += ",";
//			}else {
//				numStr += ")";
//			}
//		}
//		return numStr;
//	}
	private String calcuateProfitRate(Double saleAmt,Double profit,int foreignFormatFlg,int pointNumber)
	{
		java.text.NumberFormat nf = java.text.NumberFormat.getInstance();   
		nf.setGroupingUsed(false); 
		Double profitRate = 0.0;
		profitRate = MathController.mul(MathController.div(profit,saleAmt),100);
		profitRate = commonMethedService.pointFormatHandler(profitRate,3,2);
		return commonMethedService.profitRateFormat(String.valueOf(profitRate));//nf.format(profitRate);
	}
	//制作勘定残高明細2020
	public String exportMadeCostReport(OutPutInput pars) {
		if(validateChooeseDate(pars.getEndDate(),pars.getCompanyID(),true))
		{
			return "DATE_RANGE_ERROR";
		}
		String startDate = commonMethedMapper.searchStartTimeBySummaryMonth(Integer.valueOf(pars.getCompanyID()),DateUtil.makeDate(pars.getEndDate()));
		String sysCheckMonth = DateUtil.dateToString(DateUtil.stringtoDate(commonMethedService.getSystemDate(Integer.valueOf(pars.getCompanyID())),DateUtil.dateFormat), DateUtil.dateFormatYeayMonth);
		String choseEndDate = DateUtil.dateToString(DateUtil.stringtoDate(pars.getEndDate(),DateUtil.dateFormat), DateUtil.dateFormatYeayMonth);
		String endDate = "";
		if(sysCheckMonth.equals(choseEndDate))
		{
			endDate = "2099-01-01 01:01:01";
		}else {
			endDate = commonMethedMapper.searchEndTimeBySummaryMonth(Integer.valueOf(pars.getCompanyID()),pars.getEndDate());
		}
		
		List<ExcelReport> madeCostListTop = exportExcelMapper.getReportListBymadeCost(pars.getCompanyID(),startDate,endDate,pars.getEndDate());
		List<ExcelReport> madeCostListButtom = exportExcelMapper.getReportListBymadeCostButtom(pars.getCompanyID(),startDate,endDate,pars.getEndDate());
		
		ExcelReport excelReport = new ExcelReport();
		String companyName = exportExcelMapper.getCompanyName(pars.getCompanyID());
		String timeByZ = commonMethedService.getTimeByZone(DateUtil.getDateForNow(DateUtil.dateTimeFormat), pars.getCompanyID());
		int pointNumber = commonMethedMapper.getPointNumberByCompany(Integer.valueOf(pars.getCompanyID()));
		//List<CommonmstVo> foreignCodeVO = jobLandMapper.selectSaleCode(Integer.valueOf(pars.getCompanyID()),"001","0051");
		//int foreignFormatFlg = Integer.valueOf(foreignCodeVO.get(0).getAidcd());
		String pointNum = "";
		for(int i = 0;i < pointNumber;i++)
		{
			if(i == 0)
			{
				pointNum += ".";
			}
			pointNum += "0";
		}
		String chooseDate = "";
    	if(!pars.getEndDate().equals(""))
    	{
    		chooseDate += DateUtil.dateToString(DateUtil.stringtoDate(pars.getEndDate(), DateUtil.dateFormat), DateUtil.accountDateFormat);
    	}
    	String fontName = getFontNameByLangT(pars.getLangTyp());
		try{
			Locale local = JasperOutPdfUtil.getLocalByLangT(pars.getLangTyp());
			ResourceBundle bundle = ResourceBundle.getBundle(JasperOutPdfUtil.BUNDLE_NAME , local);
        	// 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        	XSSFWorkbook workbook = new XSSFWorkbook();
        	// 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        	XSSFSheet sheet = workbook.createSheet(pars.getSheetName());
        	CellRangeAddress titleCell=new CellRangeAddress(0, 0, 1,8);
            sheet.addMergedRegion(titleCell);
            
            CellRangeAddress outputDate=new CellRangeAddress(0, 0, 9,10);
            sheet.addMergedRegion(outputDate);
        	// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
            XSSFRow row = sheet.createRow(0);
            // 第四步，创建单元格，并设置值表头 设置表头居中
            
            XSSFFont titleFont = getExcelFont(workbook,(short)14,fontName); 
            XSSFFont bodyFont = getExcelFont(workbook,(short)10,fontName);
            
            XSSFDataFormat format= workbook.createDataFormat();
            
            XSSFCellStyle style = getExcelStyle(workbook, titleFont, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_TOP, 0, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",false);
            XSSFCellStyle styleT = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER, 0, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",false);
            XSSFCellStyle styleR = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER, 0, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",false);
            XSSFCellStyle bodyStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",true);
            XSSFCellStyle bodyLeftStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",true);
            //HSSFCellStyle bodyRightStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",true);
            String moneyUnit = getCompanyMoneyUnit(pars.getCompanyID());
            String formatStr = moneyUnit + "#,##0"+pointNum;
            XSSFCellStyle totalmoneyStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, format,formatStr,true);
            XSSFCellStyle moneyStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, format,formatStr,true);
            XSSFCellStyle totalCenterStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,null,true);
            
            totalCenterStyle.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
			totalCenterStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			totalmoneyStyle.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
			totalmoneyStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            
            XSSFCell cell = null;
            row.setHeightInPoints(24);
            
          //创建标题
            cell = row.createCell(0);
            cell.setCellValue(companyName);
            cell.setCellStyle(styleT);
            cell = row.createCell(1);
            if(sysCheckMonth.equals(choseEndDate))
    		{
            	cell.setCellValue(bundle.getString("madeCost_title_checkout_befor"));
    		}else {
    			cell.setCellValue(bundle.getString("madeCost_title"));
    		}
            
            cell.setCellStyle(style);
            cell = row.createCell(9);
            //outPutDay
            
            cell.setCellValue(bundle.getString("outPutDay")+"："+DateUtil.getNewTime(timeByZ, 0, DateUtil.dateFormat).replace("-","/"));
            cell.setCellStyle(styleR);
        	
        	row = sheet.createRow(1);
        	row.setHeightInPoints(18);
        	cell = row.createCell(0);
        	
        	
        	row = sheet.createRow(2);
        	row.setHeightInPoints(18);
        	cell = row.createCell(0);
        	//計上（予定）月
        	cell.setCellValue(bundle.getString("planDlvMonth")+"　："+chooseDate.replace("-","/"));
        	cell.setCellStyle(styleT);
        	
        	row = sheet.createRow(3);
        	row.setHeightInPoints(18);
        	cell = row.createCell(0);
        	cell.setCellValue("");
        	
        	row = sheet.createRow(4);
        	row.setHeightInPoints(20);
        	for(int i = 0;i < 11;i++)
        	{
        		sheet.setColumnWidth(i, 18*256);
        		sheet.setColumnWidth(0, 34*256);
        		sheet.setColumnWidth(1, 34*256);
        		cell = row.createCell(i);
        		cell.setCellValue(bundle.getString("madeCost_talbe_title_"+i));
        		cell.setCellStyle(bodyStyle);
        	}
        	int rowIndex = 5;
        	String jobNo = "";
        	String dClinetId = "";
        	double jobLastMonthResidual = 0.0;
        	double jobThisMonthHappenAmt = 0.0;
        	double jobThisMonthAccurate = 0.0;
        	double jobThisMonthResidual = 0.0;
        	double clientLastMonthResidual = 0.0;
        	double clientThisMonthHappenAmt = 0.0;
        	double clientThisMonthAccurate = 0.0;
        	double clientThisMonthResidual = 0.0;
            //创建内容
        	for(int i = 0;i < madeCostListTop.size();i++)
        	{
        		excelReport = madeCostListTop.get(i);
        		boolean flg = true;
        		if(i == 0)
        		{
        			jobNo = excelReport.getJobNo();
        			dClinetId = excelReport.getCldivID();
        		}
        		if(jobNo.equals(excelReport.getJobNo()) && dClinetId.equals(excelReport.getCldivID())) 
        		{
        			jobLastMonthResidual = MathController.add(jobLastMonthResidual,excelReport.getLastMonthResidual());
                	jobThisMonthHappenAmt = MathController.add(jobThisMonthHappenAmt,excelReport.getThisMonthHappened());
                	jobThisMonthAccurate = MathController.add(jobThisMonthAccurate,excelReport.getThisMonthAccurate());
                	jobThisMonthResidual = MathController.add(jobThisMonthResidual,excelReport.getIsThisMonthHappened());
                	
                	clientLastMonthResidual = MathController.add(clientLastMonthResidual,excelReport.getLastMonthResidual());
                	clientThisMonthHappenAmt = MathController.add(clientThisMonthHappenAmt,excelReport.getThisMonthHappened());
                	clientThisMonthAccurate = MathController.add(clientThisMonthAccurate,excelReport.getThisMonthAccurate());
                	clientThisMonthResidual = MathController.add(clientThisMonthResidual,excelReport.getIsThisMonthHappened());
        		}
    			//job total
    			if(!jobNo.equals(excelReport.getJobNo()))
    			{
    				flg = false;
    				row = sheet.createRow(i + rowIndex);
    				
    				row.setHeightInPoints(20);
    				CellRangeAddress clTotal = null;
                    cell = row.createCell(0);
                    
                    clTotal=new CellRangeAddress((i + rowIndex), (i + rowIndex), 0,4);
        			sheet.addMergedRegion(clTotal);
        			
        			cell = row.createCell(0);
            		cell.setCellValue(bundle.getString("madeCost_job_total"));
            		cell.setCellStyle(bodyStyle);
            		cell = row.createCell(1);
            		cell = row.createCell(2);
            		cell = row.createCell(3);
            		cell = row.createCell(4);
            		
            		cell = row.createCell(5);
            		cell.setCellValue(jobLastMonthResidual);
            		cell.setCellStyle(moneyStyle);
            		
            		cell = row.createCell(6);
            		cell.setCellValue(jobThisMonthHappenAmt);
            		cell.setCellStyle(moneyStyle);
            		cell = row.createCell(7);
            		cell.setCellStyle(moneyStyle);
            		
            		cell = row.createCell(8);
            		cell.setCellValue(jobThisMonthAccurate);
            		cell.setCellStyle(moneyStyle);
            		cell = row.createCell(9);
            		cell.setCellStyle(moneyStyle);
            		
            		cell = row.createCell(10);
            		//cell.setCellFormula("F"+(i + rowIndex+1)+"+G"+(i + rowIndex+1)+"-I"+(i + rowIndex+1));
            		cell.setCellValue(MathController.sub(MathController.add(jobLastMonthResidual, jobThisMonthHappenAmt), jobThisMonthAccurate));
            		cell.setCellStyle(moneyStyle);
    				
    				jobLastMonthResidual = 0.0;
    	        	jobThisMonthHappenAmt = 0.0;
    	        	jobThisMonthAccurate = 0.0;
    	        	jobThisMonthResidual = 0.0;
    				jobNo = excelReport.getJobNo();
    				i--;
    				rowIndex = rowIndex + 1;
    			}
    			
    			if(!dClinetId.equals(excelReport.getCldivID()))
    			{
    				if(!flg)
    				{
    					i++;
    				}
    				flg = false;
    				row = sheet.createRow(i + rowIndex);
    				row.setHeightInPoints(20);
    				CellRangeAddress clTotal = null;
                    cell = row.createCell(0);
                    
                    clTotal=new CellRangeAddress((i + rowIndex), (i + rowIndex), 0,4);
        			sheet.addMergedRegion(clTotal);
        			
        			cell = row.createCell(0);
            		cell.setCellValue(bundle.getString("madeCost_client_total"));
            		cell.setCellStyle(totalCenterStyle);
            		cell = row.createCell(1);
            		cell.setCellStyle(bodyLeftStyle);
            		cell = row.createCell(2);
            		cell.setCellStyle(bodyLeftStyle);
            		cell = row.createCell(3);
            		cell.setCellStyle(bodyLeftStyle);
            		cell = row.createCell(4);
            		cell.setCellStyle(bodyLeftStyle);
            		
            		cell = row.createCell(5);
            		cell.setCellValue(clientLastMonthResidual);
            		cell.setCellStyle(totalmoneyStyle);
            		
            		cell = row.createCell(6);
            		cell.setCellValue(clientThisMonthHappenAmt);
            		cell.setCellStyle(totalmoneyStyle);
            		cell = row.createCell(7);
            		cell.setCellStyle(totalmoneyStyle);
            		
            		cell = row.createCell(8);
            		cell.setCellValue(clientThisMonthAccurate);
            		cell.setCellStyle(totalmoneyStyle);
            		cell = row.createCell(9);
            		cell.setCellStyle(totalmoneyStyle);
            		
            		cell = row.createCell(10);
            		//cell.setCellFormula("F"+(i + rowIndex+1)+"+G"+(i + rowIndex+1)+"-I"+(i + rowIndex+1));
            		cell.setCellValue(MathController.sub(MathController.add(clientLastMonthResidual, clientThisMonthHappenAmt), clientThisMonthAccurate));
            		//cell.setCellValue(jobThisMonthResidual);
            		cell.setCellStyle(totalmoneyStyle);
    				
            		clientLastMonthResidual = 0.0;
                	clientThisMonthHappenAmt = 0.0;
                	clientThisMonthAccurate = 0.0;
                	clientThisMonthResidual = 0.0;
                	dClinetId = excelReport.getCldivID();
                	i--;
                	rowIndex = rowIndex + 1;
    			}
    			
    			if(flg)
    			{
    				row = sheet.createRow(i + rowIndex);
            		row.setHeightInPoints(20);
            		
        			cell = row.createCell(0);
            		cell.setCellValue(excelReport.getdClientName());
            		cell.setCellStyle(bodyStyle);
            		
            		cell = row.createCell(1);
            		cell.setCellValue(excelReport.getrClientName());
            		cell.setCellStyle(bodyStyle);
            		
            		cell = row.createCell(2);
            		cell.setCellValue(excelReport.getJobNo());
            		cell.setCellStyle(bodyStyle);
            		
            		cell = row.createCell(3);
            		cell.setCellValue(excelReport.getCostNo());
            		cell.setCellStyle(bodyStyle);
            		
            		cell = row.createCell(4);
            		cell.setCellValue(excelReport.getInputNo());
            		cell.setCellStyle(bodyStyle);
            		
            		cell = row.createCell(5);
            		cell.setCellValue(excelReport.getLastMonthResidual());
            		cell.setCellStyle(moneyStyle);
            		
            		cell = row.createCell(6);
            		cell.setCellValue(excelReport.getThisMonthHappened());
            		cell.setCellStyle(moneyStyle);
            		
            		cell = row.createCell(7);
            		cell.setCellValue(excelReport.getConfirmDate().replace("-", "/"));
            		cell.setCellStyle(bodyStyle);
            		
            		cell = row.createCell(8);
            		cell.setCellValue(excelReport.getThisMonthAccurate());
            		cell.setCellStyle(moneyStyle);
            		
            		cell = row.createCell(9);
            		cell.setCellValue(excelReport.getPlanDlvday().replace("-", "/"));
            		cell.setCellStyle(bodyStyle);
            		
            		cell = row.createCell(10);
            		//cell.setCellFormula("F"+(i + rowIndex+1)+"+G"+(i + rowIndex+1)+"-I"+(i + rowIndex+1));
            		cell.setCellValue(MathController.sub(MathController.add(excelReport.getLastMonthResidual(), excelReport.getThisMonthHappened()), excelReport.getThisMonthAccurate()));
            		cell.setCellStyle(moneyStyle);
    			}
    			if(i == (madeCostListTop.size() - 1))
    			{
    				int jobTotalCountIndex = i + rowIndex+1;
    				row = sheet.createRow(jobTotalCountIndex);
    				
    				row.setHeightInPoints(20);
    				CellRangeAddress clTotal = null;
                    cell = row.createCell(0);
                    
                    clTotal=new CellRangeAddress(jobTotalCountIndex, jobTotalCountIndex, 0,4);
        			sheet.addMergedRegion(clTotal);
        			
        			cell = row.createCell(0);
            		cell.setCellValue(bundle.getString("madeCost_job_total"));
            		cell.setCellStyle(bodyStyle);
            		cell = row.createCell(1);
            		cell = row.createCell(2);
            		cell = row.createCell(3);
            		cell = row.createCell(4);
            		
            		cell = row.createCell(5);
            		cell.setCellValue(jobLastMonthResidual);
            		cell.setCellStyle(moneyStyle);
            		
            		cell = row.createCell(6);
            		cell.setCellValue(jobThisMonthHappenAmt);
            		cell.setCellStyle(moneyStyle);
            		cell = row.createCell(7);
            		cell.setCellStyle(moneyStyle);
            		
            		cell = row.createCell(8);
            		cell.setCellValue(jobThisMonthAccurate);
            		cell.setCellStyle(moneyStyle);
            		cell = row.createCell(9);
            		cell.setCellStyle(moneyStyle);
            		
            		cell = row.createCell(10);
            		//cell.setCellFormula("F"+(jobTotalCountIndex+1)+"+G"+(jobTotalCountIndex+1)+"-I"+(jobTotalCountIndex+1));
            		cell.setCellValue(MathController.sub(MathController.add(jobLastMonthResidual, jobThisMonthHappenAmt), jobThisMonthAccurate));
            		cell.setCellStyle(moneyStyle);
    				
    			}
    			
    			if(i == (madeCostListTop.size() - 1))
    			{
    				int TotalCountIndex = i + rowIndex+2;
    				row = sheet.createRow(TotalCountIndex);
    				row.setHeightInPoints(20);
    				CellRangeAddress clTotal = null;
                    cell = row.createCell(0);
                    
                    clTotal=new CellRangeAddress(TotalCountIndex, TotalCountIndex, 0,4);
        			sheet.addMergedRegion(clTotal);
        			
        			cell = row.createCell(0);
            		cell.setCellValue(bundle.getString("madeCost_client_total"));
            		cell.setCellStyle(totalCenterStyle);
            		cell = row.createCell(1);
            		cell.setCellStyle(bodyLeftStyle);
            		cell = row.createCell(2);
            		cell.setCellStyle(bodyLeftStyle);
            		cell = row.createCell(3);
            		cell.setCellStyle(bodyLeftStyle);
            		cell = row.createCell(4);
            		cell.setCellStyle(bodyLeftStyle);
            		
            		cell = row.createCell(5);
            		cell.setCellValue(clientLastMonthResidual);
            		cell.setCellStyle(totalmoneyStyle);
            		
            		cell = row.createCell(6);
            		cell.setCellValue(clientThisMonthHappenAmt);
            		cell.setCellStyle(totalmoneyStyle);
            		cell = row.createCell(7);
            		cell.setCellStyle(totalmoneyStyle);
            		
            		cell = row.createCell(8);
            		cell.setCellValue(clientThisMonthAccurate);
            		cell.setCellStyle(totalmoneyStyle);
            		cell = row.createCell(9);
            		cell.setCellStyle(totalmoneyStyle);
            		
            		cell = row.createCell(10);
            		//cell.setCellFormula("F"+(TotalCountIndex+1)+"+G"+(TotalCountIndex+1)+"-I"+(TotalCountIndex+1));
            		cell.setCellValue(MathController.sub(MathController.add(clientLastMonthResidual, clientThisMonthHappenAmt), clientThisMonthAccurate));
            		
            		//cell.setCellValue(jobThisMonthResidual);
            		cell.setCellStyle(totalmoneyStyle);
    			}
        	}
        	
        	row = sheet.createRow(madeCostListTop.size() + rowIndex + 8);
    		row.setHeightInPoints(20);
    		cell = row.createCell(0);
    		cell.setCellValue(bundle.getString("summaryTable"));
    		cell.setCellStyle(styleT);
        	
    		
    		CellRangeAddress clTotal = null;
            clTotal=new CellRangeAddress((madeCostListTop.size() + rowIndex + 9), (madeCostListTop.size() + rowIndex + 9), 0,4);
			sheet.addMergedRegion(clTotal);
			
        	row = sheet.createRow(madeCostListTop.size() + rowIndex + 9);
    		row.setHeightInPoints(20);
    		cell = row.createCell(0);
    		cell.setCellValue(bundle.getString("madeCost_client_code_name"));
    		cell.setCellStyle(bodyStyle);
    		
    		cell = row.createCell(1);
    		cell.setCellStyle(bodyStyle);
    		cell = row.createCell(2);
    		cell.setCellStyle(bodyStyle);
    		cell = row.createCell(3);
    		cell.setCellStyle(bodyStyle);
    		cell = row.createCell(4);
    		cell.setCellStyle(bodyStyle);
    		
    		for(int i = 5;i < 11;i++)
    		{
    			cell = row.createCell(i);
        		cell.setCellValue(bundle.getString("madeCost_talbe_title_"+i));
        		cell.setCellStyle(bodyStyle);
    		}
    		
        	int summaryIndex = madeCostListTop.size()  + rowIndex + 10;
        	Double bottomSumLastMonthResidualAmt = 0.0;
        	Double bottomSumThisMonthHappenedAmt = 0.0;
        	Double bottomSumThisMonthAccurateAmt = 0.0;
        	for(int i = 0;i < madeCostListButtom.size();i++)
        	{
        		excelReport = madeCostListButtom.get(i);
        		row = sheet.createRow(i + summaryIndex);
        		row.setHeightInPoints(20);
        		
        		bottomSumLastMonthResidualAmt = MathController.add(bottomSumLastMonthResidualAmt, excelReport.getLastMonthResidual());
            	bottomSumThisMonthHappenedAmt = MathController.add(bottomSumThisMonthHappenedAmt, excelReport.getThisMonthHappened());
            	bottomSumThisMonthAccurateAmt = MathController.add(bottomSumThisMonthAccurateAmt, excelReport.getThisMonthAccurate());
        		
                cell = row.createCell(0);
                CellRangeAddress clTotal2 = null;
                clTotal2=new CellRangeAddress((i + summaryIndex), (i + summaryIndex), 0,4);
    			sheet.addMergedRegion(clTotal2);
    			
    			cell = row.createCell(0);
        		cell.setCellValue(excelReport.getCldivAccountID() +"/"+excelReport.getdClientName());
        		cell.setCellStyle(bodyStyle);
        		cell = row.createCell(1);
        		cell.setCellStyle(bodyStyle);
        		cell = row.createCell(2);
        		cell.setCellStyle(bodyStyle);
        		cell = row.createCell(3);
        		cell.setCellStyle(bodyStyle);
        		cell = row.createCell(4);
        		cell.setCellStyle(bodyStyle);
        		
        		cell = row.createCell(5);
        		cell.setCellValue(excelReport.getLastMonthResidual());
        		cell.setCellStyle(moneyStyle);
        		
        		cell = row.createCell(6);
        		cell.setCellValue(excelReport.getThisMonthHappened());
        		cell.setCellStyle(moneyStyle);
        		
        		cell = row.createCell(7);
        		cell.setCellStyle(moneyStyle);
        		
        		cell = row.createCell(8);
        		cell.setCellValue(excelReport.getThisMonthAccurate());
        		cell.setCellStyle(moneyStyle);
        		
        		cell = row.createCell(9);
        		cell.setCellStyle(moneyStyle);
        		
        		cell = row.createCell(10);
        		//cell.setCellFormula("F"+(i + summaryIndex+1)+"+G"+(i + summaryIndex+1)+"-I"+(i + summaryIndex+1));
        		cell.setCellValue(MathController.sub(MathController.add(excelReport.getLastMonthResidual(), excelReport.getThisMonthHappened()), excelReport.getThisMonthAccurate()));
        		//cell.setCellValue(jobThisMonthResidual);
        		cell.setCellStyle(moneyStyle);
        	}
        	
        	int totalIndex = madeCostListButtom.size() + summaryIndex;
        	row = sheet.createRow(totalIndex);
    		row.setHeightInPoints(20);
    		
    		CellRangeAddress clTotal1 = null;
            cell = row.createCell(0);
            
            clTotal1=new CellRangeAddress(totalIndex, totalIndex, 0,4);
			sheet.addMergedRegion(clTotal1);
			
			cell = row.createCell(0);
    		cell.setCellValue(bundle.getString("madeCost_total"));
    		cell.setCellStyle(totalCenterStyle);
    		cell = row.createCell(1);
    		cell.setCellStyle(totalCenterStyle);
    		cell = row.createCell(2);
    		cell.setCellStyle(totalCenterStyle);
    		cell = row.createCell(3);
    		cell.setCellStyle(totalCenterStyle);
    		cell = row.createCell(4);
    		cell.setCellStyle(totalCenterStyle);
    	
    		cell = row.createCell(5);
    		//cell.setCellFormula("SUM(F"+(summaryIndex+1)+":F"+totalIndex+")");
    		cell.setCellValue(bottomSumLastMonthResidualAmt);
    		cell.setCellStyle(totalmoneyStyle);
    		
    		cell = row.createCell(6);
    		//cell.setCellFormula("SUM(G"+(summaryIndex+1)+":G"+totalIndex+")");
    		cell.setCellValue(bottomSumThisMonthHappenedAmt);
    		cell.setCellStyle(totalmoneyStyle);
    		
    		cell = row.createCell(7);
    		cell.setCellStyle(totalmoneyStyle);
    		
    		cell = row.createCell(8);
    		//cell.setCellFormula("SUM(I"+(summaryIndex+1)+":I"+totalIndex+")");
    		cell.setCellValue(bottomSumThisMonthAccurateAmt);
    		cell.setCellStyle(totalmoneyStyle);
    		
    		cell = row.createCell(9);
    		cell.setCellStyle(totalmoneyStyle);
    		
    		cell = row.createCell(10);
    		//cell.setCellFormula("F"+(totalIndex+1)+"+G"+(totalIndex+1)+"-I"+(totalIndex+1));
    		cell.setCellValue(MathController.sub(MathController.add(bottomSumLastMonthResidualAmt, bottomSumThisMonthHappenedAmt),bottomSumThisMonthAccurateAmt));
    		cell.setCellStyle(totalmoneyStyle);
        	
            ByteArrayOutputStream os = new ByteArrayOutputStream();
           	workbook.write(os);
           	byte[] buffer = os.toByteArray();
           	return Base64.getEncoder().encodeToString(buffer);
           
        }catch(Exception e){
            //e.printStackTrace();
        	return "error";
        }
	}
	private boolean validateChooeseDate(String dt,String companyID,boolean isGreater)
	{
		String accountDate = DateUtil.getAddNumMotnDate(commonMethedMapper.getSystemDate(Integer.valueOf(companyID)),0,DateUtil.dateFormatYeayMonth);
		String cDate = DateUtil.getAddNumMotnDate(dt,0,DateUtil.dateFormatYeayMonth);
		if(isGreater)
		{
			if(Integer.valueOf(cDate) > Integer.valueOf(accountDate))
			{
				return true;
			}
		}else {
			if(Integer.valueOf(cDate) >= Integer.valueOf(accountDate))
			{
				return true;
			}
		}
		return false;
	}
	
	public Object outPutAccountEntriesPdf(OutPutInput pars)
	{
		String outDate = pars.getEndDate();
		String showOutDate = DateUtil.getNewTime(outDate,0,DateUtil.ACCOUNT_MONTH).replace("-","/");
		//outDate = "2020-04-01";
		//pars.setCompanyID("96");
		String nowDate = commonMethedService.getTimeByZone(DateUtil.getNowDate(),pars.getCompanyID());
		nowDate = DateUtil.getNewTime(nowDate,0,DateUtil.dateFormat);
		String companyName = commonMethedService.getCompanyName(pars.getCompanyID());
		Locale local = JasperOutPdfUtil.getLocalByLangT(pars.getLangTyp());
		ResourceBundle bundle = ResourceBundle.getBundle(JasperOutPdfUtil.BUNDLE_NAME , local);
		String fontName = getFontNameByLangT(pars.getLangTyp());
		int pointNumber = commonMethedMapper.getPointNumberByCompany(Integer.valueOf(pars.getCompanyID()));
		String pointNum = "";
		for(int i = 0;i < pointNumber;i++)
		{
			if(i == 0)
			{
				pointNum += ".";
			}
			pointNum += "0";
		}
		
		List<String> pdfList = new ArrayList<String>();
		
		List<AccountEntries> accountJobDetailsList = accountEntriesMapper.getAccountJobDetailsList(pars.getCompanyID(),outDate,pars.getLangTyp());
		String accountJobDetailsPdf = this.createAccountEntriesJobDetailExcel(accountJobDetailsList,pars.getFileName(),
				companyName,nowDate,showOutDate,pointNum,bundle,fontName,pars.getCompanyID(),outDate,pars.getLangTyp());
		pdfList.add(accountJobDetailsPdf);
		/*
		List<AccountEntries> accountCostDetailsList = accountEntriesMapper.getAccountCostDetailsList(pars.getCompanyID(),outDate,pars.getLangTyp());
		String accountCostDetailsPdf = this.createAccountCostDetailsExcel(accountCostDetailsList,pars.getFileName(),
				companyName,nowDate,showOutDate,pointNum,bundle,fontName);
		pdfList.add(accountCostDetailsPdf);
		
		List<AccountEntries> accountsummaryList = accountEntriesMapper.getAccountsummaryList(pars.getCompanyID(),outDate,pars.getLangTyp());
		String accountsummaryPdf = this.createAccountsummaryExcel(accountsummaryList,pars.getFileName(),
				companyName,nowDate,showOutDate,pointNum,bundle,fontName);
		pdfList.add(accountsummaryPdf);
		
		List<AccountEntries> accountsummaryConfirmList = accountEntriesMapper.getAccountsummaryConfirmList(pars.getCompanyID(),outDate,pars.getLangTyp());
		String accountsummaryConfirmPdf = this.createAccountsummaryConfirmExcel(accountsummaryConfirmList,pars.getFileName(),
				companyName,nowDate,showOutDate,pointNum,bundle,fontName);
		pdfList.add(accountsummaryConfirmPdf);
		*/
		return pdfList;
	}
	
	private String createAccountEntriesJobDetailExcel(List<AccountEntries> accountList,String fileName,String companyFullName,
				String nowDate,String outputDate,String pointNum,ResourceBundle bundle,String fontName,String companyID,String outDate,String langTyp)
	{
		
		try{
        	// 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        	XSSFWorkbook workbook = new XSSFWorkbook();
        	// 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        	XSSFSheet sheet = workbook.createSheet(fileName);
        	workbook.setSheetName(0, "(1)Journal");
        	// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
            CellRangeAddress cra=new CellRangeAddress(0, 0, 2, 8);
            sheet.addMergedRegion(cra);
            CellRangeAddress crb=new CellRangeAddress(0, 0, 0, 1);
            sheet.addMergedRegion(crb);
//            CellRangeAddress crc=new CellRangeAddress(4, 4, 7, 8);
//            sheet.addMergedRegion(crc);
            CellRangeAddress cre=new CellRangeAddress(0, 0, 9, 10);
            sheet.addMergedRegion(cre);
            CellRangeAddress crd=new CellRangeAddress((accountList.size()+6), (accountList.size()+6), 0, 5);
            sheet.addMergedRegion(crd);
            
            
            XSSFFont titleFont = getExcelFont(workbook,(short)14,fontName); 
            XSSFFont bodyFont = getExcelFont(workbook,(short)10,fontName);
            
            XSSFDataFormat format= workbook.createDataFormat();
            String moneyUnit = getCompanyMoneyUnit(companyID);
            String formatStr = moneyUnit + "#,##0"+pointNum;
            XSSFCellStyle titleStyle = getExcelStyle(workbook, titleFont, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER, 0, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",false);
            XSSFCellStyle companyLeftStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER, 0, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",false);
            XSSFCellStyle outDateStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER, 0, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",false);
            XSSFCellStyle outDateLeftStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER, 0, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",false);
            XSSFCellStyle bodyStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",true);
            //HSSFCellStyle bodyLeftStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",true);
            XSSFCellStyle totalMontyStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, format,formatStr,true);
            XSSFCellStyle bodybgStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",true);
            XSSFCellStyle bodyrightStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index,format,formatStr,true);
            
            XSSFCell cell = null;
            XSSFRow row = sheet.createRow(0);
            //0行  title
            row.setHeightInPoints(30);
            cell = row.createCell(0);
            cell.setCellValue(companyFullName);
            cell.setCellStyle(companyLeftStyle);
            cell = row.createCell(2);
            cell.setCellValue(bundle.getString("accounting_entries_title"));
            cell.setCellStyle(titleStyle);
            cell = row.createCell(9);
            cell.setCellValue(bundle.getString("outPutDay")+"："+nowDate.replace("-","/"));
            cell.setCellStyle(outDateStyle);
            
            //1行  输出日行
            //row.setHeightInPoints(30);
            row = sheet.createRow(2);
            cell = row.createCell(0);
            cell.setCellValue(bundle.getString("accounting_confirm_month_date")+"："+outputDate.replace("-","/"));
            cell.setCellStyle(outDateLeftStyle);
            
            //2行  空白行
            row.setHeightInPoints(18);
            row = sheet.createRow(3);
            
            CellRangeAddress tableTitle = null;
            
            //3行  表格title
            row = sheet.createRow(4);
            row.setHeightInPoints(20);
            sheet.setColumnWidth(0, 22*256);
            sheet.setColumnWidth(1, 18*256);
            sheet.setColumnWidth(2, 18*256);
            sheet.setColumnWidth(3, 18*256);
            sheet.setColumnWidth(4, 18*256);
            sheet.setColumnWidth(5, 18*256);
            sheet.setColumnWidth(6, 10*256);
            sheet.setColumnWidth(7, 8*256);
            sheet.setColumnWidth(8, 16*256);
            sheet.setColumnWidth(9, 12*256);
            sheet.setColumnWidth(10, 12*256);
            
            for(int i = 0;i < 11;i++)
            {
            	cell = row.createCell(i);
            	if(i == 7 || i ==8)
            	{
            		cell.setCellValue("科目");
            	}else {
            		cell.setCellValue(bundle.getString("accountingEntries_"+i));
            	}
            	cell.setCellStyle(bodybgStyle);
            }
            bodybgStyle.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
            bodybgStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        	row = sheet.createRow(5);
        	row.setHeightInPoints(20);
          //创建标题
            for(int i = 0;i < 11;i++)
            {
                cell = row.createCell(i);
                if(i == 7 || i ==8)
                {
                	cell.setCellValue(bundle.getString("accountingEntries_"+i));
                }
                cell.setCellStyle(bodybgStyle);
                tableTitle=new CellRangeAddress(4, 5, i, i);
                sheet.addMergedRegion(tableTitle);
            }
            String[] gongtong = {"017","010","014","016","018"};
            String[] debtorIds = {"001","017","008","010","014","016","005","015","018"};
        	String[] creditIds = {"017","007","002","009","010","014","009","016","006","004","018"};
        	double sumDebtorAmt = 0.0;
        	double sumCreditAmt = 0.0;
            for(int i = 0;i < accountList.size();i++)
            {
            	row = sheet.createRow(i + 6);
            	row.setHeightInPoints(20);
            	cell = row.createCell(0);
            	cell.setCellValue(accountList.get(i).getClientAccountCode());
            	cell.setCellStyle(bodyStyle);
            	cell = row.createCell(1);
            	cell.setCellValue(accountList.get(i).getClientName());
            	cell.setCellStyle(bodyStyle);
            	cell = row.createCell(2);
            	cell.setCellValue(accountList.get(i).getReqClientCode());
            	cell.setCellStyle(bodyStyle);
            	cell = row.createCell(3);
            	cell.setCellValue(accountList.get(i).getReqClientName());
            	cell.setCellStyle(bodyStyle);
            	cell = row.createCell(4);
            	cell.setCellValue(accountList.get(i).getJobNo());
            	cell.setCellStyle(bodyStyle);
            	cell = row.createCell(5);
            	cell.setCellValue(accountList.get(i).getJobName());
            	cell.setCellStyle(bodyStyle);
            	cell = row.createCell(6);
            	//cell.setCellValue(accountList.get(i).getAccountNote());
            	cell.setCellStyle(bodyStyle);
            	cell = row.createCell(7);
            	cell.setCellValue(accountList.get(i).getAidCD());
            	//cell.setCellValue(accountList.get(i).getAccountCode());
//            	if(accountList.get(i).getAidCD().equals("")) {
//            		cell.setCellValue(accountList.get(i).getCreditAidCD());
//            	}
//            	else {
//            		cell.setCellValue(accountList.get(i).getAidCD());
//            	}
            	cell.setCellStyle(bodyStyle);
            	cell = row.createCell(8);
            	cell.setCellValue(accountList.get(i).getAccountName());
//            	if(accountList.get(i).getAccountName().equals("")) {
//            		cell.setCellValue(accountList.get(i).getCreditCodeName());
//            	}
//            	else {
//            		cell.setCellValue(accountList.get(i).getAccountName());
//            	}
            	
            	cell.setCellStyle(bodyStyle);
            	
            	cell = row.createCell(9);
            	
            	if(Arrays.asList(debtorIds).contains(accountList.get(i).getAccountCode()))
            	{
            		if(Arrays.asList(gongtong).contains(accountList.get(i).getAccountCode())) {
            			if(accountList.get(i).getDebtorAmt()!=0.0) {
            				sumDebtorAmt = MathController.add(sumDebtorAmt,accountList.get(i).getDebtorAmt());
                			cell.setCellValue(accountList.get(i).getDebtorAmt());
                		}else {
                			cell.setCellValue("");
                		}
            		}else {
            			sumDebtorAmt = MathController.add(sumDebtorAmt,accountList.get(i).getDebtorAmt());
            			cell.setCellValue(accountList.get(i).getDebtorAmt());
            		}
            	}else{
            		cell.setCellValue("");
            	}
            	
            	cell.setCellStyle(bodyrightStyle);
            	cell = row.createCell(10);
            	if(Arrays.asList(creditIds).contains(accountList.get(i).getAccountCode()))
            	{	
            		if(Arrays.asList(gongtong).contains(accountList.get(i).getAccountCode())) {
            			if(accountList.get(i).getCreditAmt()!=0.0) {
            				sumCreditAmt = MathController.add(sumCreditAmt,accountList.get(i).getCreditAmt());
                			cell.setCellValue(accountList.get(i).getCreditAmt());
                		}else {
                			cell.setCellValue("");
                		}
            		}else {
            			sumCreditAmt = MathController.add(sumCreditAmt,accountList.get(i).getCreditAmt());
            			cell.setCellValue(accountList.get(i).getCreditAmt());
            		}
            	}else{
            		cell.setCellValue("");
            	}
            	cell.setCellStyle(bodyrightStyle);
            
            }
            
            totalMontyStyle.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
			totalMontyStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            row = sheet.createRow((accountList.size()+6));
            row.setHeightInPoints(20);
            cell = row.createCell(0);
            cell.setCellValue(bundle.getString("accountingEntries_total"));
            cell.setCellStyle(bodybgStyle);
            cell = row.createCell(1);
            cell.setCellStyle(totalMontyStyle);
            cell = row.createCell(2);
            cell.setCellStyle(totalMontyStyle);
            cell = row.createCell(3);
            cell.setCellStyle(totalMontyStyle);
            cell = row.createCell(4);
            cell.setCellStyle(totalMontyStyle);
            cell = row.createCell(5);
            cell.setCellStyle(totalMontyStyle);
            cell = row.createCell(6);
            cell.setCellStyle(totalMontyStyle);
            cell = row.createCell(7);
            cell.setCellStyle(totalMontyStyle);
            cell = row.createCell(8);
            cell.setCellStyle(totalMontyStyle);
            cell = row.createCell(9);
            //cell.setCellFormula("SUM(J7:J"+(accountList.size()+6)+")");
            cell.setCellValue(sumDebtorAmt);
            cell.setCellStyle(totalMontyStyle);
            cell = row.createCell(10);
            cell.setCellValue(sumCreditAmt);
            //cell.setCellFormula("SUM(K7:K"+(accountList.size()+6)+")");
            cell.setCellStyle(totalMontyStyle);
            
            List<AccountEntries> accountsummaryList = accountEntriesMapper.getAccountsummaryList(companyID,outDate,langTyp);
    		workbook = this.createAccountsummaryExcel( accountsummaryList, fileName, companyFullName,
    				 nowDate, outputDate, pointNum, bundle, fontName,workbook,companyID);
    		
            List<AccountEntries> accountCostDetailsList = accountEntriesMapper.getAccountCostDetailsList(companyID,outDate,langTyp);
            workbook = this.createAccountCostDetailsExcel(accountCostDetailsList, fileName, companyFullName,
    				 nowDate, outputDate, pointNum, bundle, fontName,workbook,companyID);
    		
    		List<AccountEntries> accountsummaryConfirmList = accountEntriesMapper.getAccountsummaryConfirmList(companyID,outDate,langTyp);
    		workbook = this.createAccountsummaryConfirmExcel( accountsummaryConfirmList, fileName, companyFullName,
    				 nowDate, outputDate, pointNum, bundle, fontName,workbook,companyID);
            
            ByteArrayOutputStream os = new ByteArrayOutputStream();
           	workbook.write(os);
           	byte[] buffer = os.toByteArray();
           	os.flush();
           	os.close();
           	return Base64.getEncoder().encodeToString(buffer);
           	
        }catch(Exception e){
        	Logger LOG = LoggerFactory.getLogger(ExportExcelServiceImpl.class);
        	LOG.error("[excelerr,{}]{}",e.getMessage());
        	System.out.println(e.getMessage());
            //e.printStackTrace();
        	return "error";
        }
	}
	private XSSFWorkbook createAccountCostDetailsExcel(List<AccountEntries> accountList,String fileName,String companyFullName,
				String nowDate,String outputDate,String pointNum,ResourceBundle bundle,String fontName,XSSFWorkbook workbook,String companyID)
	{
		try{
        	// 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        	//HSSFWorkbook workbook = new HSSFWorkbook();
        	// 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        	XSSFSheet sheet1 = workbook.createSheet(fileName);
        	workbook.setSheetName(2, "(3)Processed Journal");
        	// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
            CellRangeAddress cra=new CellRangeAddress(0, 0, 2, 8);
            sheet1.addMergedRegion(cra);
            CellRangeAddress crb=new CellRangeAddress(0, 0, 0, 1);
            sheet1.addMergedRegion(crb);
//            CellRangeAddress crc=new CellRangeAddress(4, 4, 7, 8);
//            sheet1.addMergedRegion(crc);
            CellRangeAddress cre=new CellRangeAddress(0, 0, 9, 10);
            sheet1.addMergedRegion(cre);
            CellRangeAddress crd=new CellRangeAddress((accountList.size()+6), (accountList.size()+6), 0, 5);
            sheet1.addMergedRegion(crd);
            
            
            XSSFFont titleFont = getExcelFont(workbook,(short)14,fontName); 
            XSSFFont bodyFont = getExcelFont(workbook,(short)10,fontName);
            
            XSSFDataFormat format= workbook.createDataFormat();
            String moneyUnit = getCompanyMoneyUnit(companyID);
            String formatStr = moneyUnit + "#,##0"+pointNum;
            XSSFCellStyle companyLeftStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER, 0, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",false);
            XSSFCellStyle titleStyle = getExcelStyle(workbook, titleFont, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER, 0, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",false);
            XSSFCellStyle outDateStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER, 0, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",false);
            XSSFCellStyle outDateLeftStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER, 0, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",false);
            XSSFCellStyle bodyStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",true);
            //HSSFCellStyle bodyLeftStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",true);
            XSSFCellStyle bodybgStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",true);
            XSSFCellStyle totalMontyStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, format,formatStr,true);
            XSSFCellStyle bodyrightStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index,format,formatStr,true);
            XSSFCell cell = null;
            XSSFRow row = sheet1.createRow(0);
            //0行  title
            row.setHeightInPoints(30);
            cell = row.createCell(0);
            cell.setCellValue(companyFullName);
            cell.setCellStyle(companyLeftStyle);
            
            cell = row.createCell(2);
            cell.setCellValue(bundle.getString("accounting_entries_account_payable_title"));
            cell.setCellStyle(titleStyle);
            
            cell = row.createCell(9);
            cell.setCellValue(bundle.getString("outPutDay")+"："+nowDate.replace("-","/"));
            cell.setCellStyle(outDateStyle);
            //1行  输出日行
            //row.setHeightInPoints(30);
            row = sheet1.createRow(2);
            cell = row.createCell(0);
            cell.setCellValue(bundle.getString("accounting_confirm_month_date")+"："+outputDate.replace("-","/"));
            cell.setCellStyle(outDateLeftStyle);
            
            //2行  空白行
            row.setHeightInPoints(18);
            row = sheet1.createRow(3);
            row.setHeightInPoints(20);
            CellRangeAddress tableTitle = null;
            //3行  表格title
            row = sheet1.createRow(4);
            sheet1.setColumnWidth(0, 22*256);
            sheet1.setColumnWidth(1, 18*256);
            sheet1.setColumnWidth(2, 18*256);
            sheet1.setColumnWidth(3, 18*256);
            sheet1.setColumnWidth(4, 18*256);
            sheet1.setColumnWidth(5, 18*256);
            sheet1.setColumnWidth(6, 10*256);
            sheet1.setColumnWidth(7, 8*256);
            sheet1.setColumnWidth(8, 16*256);
            sheet1.setColumnWidth(9, 12*256);
            sheet1.setColumnWidth(10, 12*256);
            
            for(int i = 0;i < 11;i++)
            {
            	cell = row.createCell(i);
            	if(i == 7 || i ==8)
            	{
            		cell.setCellValue("科目");
            	}else {
            		cell.setCellValue(bundle.getString("accountingEntriesOrder_"+i));
            	}
            	cell.setCellStyle(bodybgStyle);
            }
            bodybgStyle.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
            bodybgStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        	row = sheet1.createRow(5);
        	row.setHeightInPoints(20);
          //创建标题
            for(int i = 0;i < 11;i++)
            {
                cell = row.createCell(i);
                if(i == 7 || i ==8)
                {
                	cell.setCellValue(bundle.getString("accountingEntriesOrder_"+i));
                }
                cell.setCellStyle(bodybgStyle);
                tableTitle=new CellRangeAddress(4, 5, i, i);
                sheet1.addMergedRegion(tableTitle);
            }
            double sumDebtorAmt = 0.0;
            double sumCreditAmt = 0.0;
            
            for(int i = 0;i < accountList.size();i++)
            {
            	row = sheet1.createRow(i + 6);
            	row.setHeightInPoints(20);
            	cell = row.createCell(0);
            	cell.setCellValue(accountList.get(i).getClientAccountCode());
            	cell.setCellStyle(bodyStyle);
            	cell = row.createCell(1);
            	cell.setCellValue(accountList.get(i).getClientName());
            	cell.setCellStyle(bodyStyle);
            	cell = row.createCell(2);
            	cell.setCellValue(accountList.get(i).getJobNo());
            	cell.setCellStyle(bodyStyle);
            	cell = row.createCell(3);
            	cell.setCellValue(accountList.get(i).getJobName());
            	cell.setCellStyle(bodyStyle);
            	cell = row.createCell(4);
            	cell.setCellValue(accountList.get(i).getInputNo());
            	cell.setCellStyle(bodyStyle);
            	cell = row.createCell(5);
            	cell.setCellValue(accountList.get(i).getOrderName());
            	cell.setCellStyle(bodyStyle);
            	cell = row.createCell(6);
            	cell.setCellValue(accountList.get(i).getAccountNote());
            	cell.setCellStyle(bodyStyle);
            	cell = row.createCell(7);
            	//cell.setCellValue(accountList.get(i).getAccountCode());
            	cell.setCellValue(accountList.get(i).getAidCD());
            	cell.setCellStyle(bodyStyle);
            	cell = row.createCell(8);
            	cell.setCellValue(accountList.get(i).getAccountName());
            	cell.setCellStyle(bodyStyle);
            	cell = row.createCell(9);
            	if(accountList.get(i).getDebtorAmt()==0.0) {
            		cell.setCellValue("");
            	}else {
                    sumDebtorAmt = MathController.add(sumDebtorAmt, accountList.get(i).getDebtorAmt());
        			cell.setCellValue(accountList.get(i).getDebtorAmt());
            	}
            	
            	cell.setCellStyle(bodyrightStyle);
            	cell = row.createCell(10);
            	if(accountList.get(i).getCreditAmt()==0.0) {
            		cell.setCellValue("");
            	}else {
            		sumCreditAmt = MathController.add(sumCreditAmt, accountList.get(i).getCreditAmt());
        			cell.setCellValue(accountList.get(i).getCreditAmt());
            	}
            	//cell.setCellValue(accountList.get(i).getCreditAmt());
            	cell.setCellStyle(bodyrightStyle);
            }
            
            totalMontyStyle.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
			totalMontyStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			
            row = sheet1.createRow((accountList.size()+6));
            row.setHeightInPoints(20);
            cell = row.createCell(0);
            cell.setCellValue(bundle.getString("accountingEntries_total"));
            cell.setCellStyle(bodybgStyle);
            cell = row.createCell(1);
            cell.setCellStyle(totalMontyStyle);
            cell = row.createCell(2);
            cell.setCellStyle(totalMontyStyle);
            cell = row.createCell(3);
            cell.setCellStyle(totalMontyStyle);
            cell = row.createCell(4);
            cell.setCellStyle(totalMontyStyle);
            cell = row.createCell(5);
            cell.setCellStyle(totalMontyStyle);
            cell = row.createCell(6);
            cell.setCellStyle(totalMontyStyle);
            cell = row.createCell(7);
            cell.setCellStyle(totalMontyStyle);
            cell = row.createCell(8);
            cell.setCellStyle(totalMontyStyle);
            cell = row.createCell(9);
            cell.setCellValue(sumDebtorAmt);
            //cell.setCellFormula("SUM(J7:J"+(accountList.size()+6)+")");
            cell.setCellStyle(totalMontyStyle);
            cell = row.createCell(10);
            cell.setCellValue(sumCreditAmt);
           // cell.setCellFormula("SUM(K7:K"+(accountList.size()+6)+")");
            cell.setCellStyle(totalMontyStyle);
            /*
            ByteArrayOutputStream os = new ByteArrayOutputStream();
           	workbook.write(os);
           	byte[] buffer = os.toByteArray();
           	os.flush();
           	os.close();
           	return Base64.getEncoder().encodeToString(buffer);
           	*/
            return workbook;
        }catch(Exception e){
        	Logger LOG = LoggerFactory.getLogger(ExportExcelServiceImpl.class);
        	LOG.error("[excelerr,{}]{}",e.getMessage());
        	System.out.println(e.getMessage());
            //e.printStackTrace();
        	return null;
        }
	}
	private XSSFWorkbook createAccountsummaryExcel(List<AccountEntries> accountList,String fileName,String companyFullName,String nowDate,
				String outputDate,String pointNum,ResourceBundle bundle,String fontName,XSSFWorkbook workbook,String companyID)
	{
		try{
        	// 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        	//HSSFWorkbook workbook = new HSSFWorkbook();
        	// 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        	XSSFSheet sheet2 = workbook.createSheet(fileName);
        	workbook.setSheetName(1, "(2)Journal_Summary");
        	// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
            CellRangeAddress cra=new CellRangeAddress(0, 0, 2, 5);
            sheet2.addMergedRegion(cra);
            CellRangeAddress crb=new CellRangeAddress(0, 0, 0, 1);
            sheet2.addMergedRegion(crb);
            CellRangeAddress cre=new CellRangeAddress(0, 0, 6, 7);
            sheet2.addMergedRegion(cre);
            
            XSSFDataFormat format= workbook.createDataFormat();
            String moneyUnit = getCompanyMoneyUnit(companyID);
            String formatStr = moneyUnit + "#,##0"+pointNum;
            XSSFFont titleFont = getExcelFont(workbook,(short)14,fontName); 
            XSSFFont bodyFont = getExcelFont(workbook,(short)10,fontName);
            XSSFCellStyle numberStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER, 0, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",false);
            XSSFCellStyle companyLeftStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER, 0, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",false);
            XSSFCellStyle titleStyle = getExcelStyle(workbook, titleFont, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER, 0, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",false);
            XSSFCellStyle outDateStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER, 0, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",false);
            XSSFCellStyle outDateLeftStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER, 0, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",false);
            XSSFCellStyle bodyStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",true);
            XSSFCellStyle bodybgStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",true);
            XSSFCellStyle bodyrightStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index,format,formatStr,true);
            
            sheet2.setColumnWidth(0, 22*256);
            sheet2.setColumnWidth(1, 10*256);
            sheet2.setColumnWidth(2, 12*256);
            sheet2.setColumnWidth(3, 18*256);
            sheet2.setColumnWidth(4, 12*256);
            sheet2.setColumnWidth(5, 18*256);
            sheet2.setColumnWidth(6, 20*256);
            sheet2.setColumnWidth(7, 8*256);
            
            XSSFCell cell = null;
            XSSFRow row = sheet2.createRow(0);
            //0行  title
            row.setHeightInPoints(30);
            cell = row.createCell(0);
            cell.setCellValue(companyFullName);
            cell.setCellStyle(companyLeftStyle);
            
            cell = row.createCell(2);
            cell.setCellValue(bundle.getString("accounting_confirm_title"));
            cell.setCellStyle(titleStyle);
            
            cell = row.createCell(6);
            cell.setCellValue(bundle.getString("outPutDay")+"："+nowDate.replace("-","/"));
            cell.setCellStyle(outDateStyle);
            //1行  输出日行
            //row.setHeightInPoints(30);
            row = sheet2.createRow(2);
            cell = row.createCell(0);
            cell.setCellValue(bundle.getString("accounting_confirm_month_date")+"："+outputDate.replace("-","/"));
            cell.setCellStyle(outDateLeftStyle);
            
            
            //2行  空白行
            row.setHeightInPoints(18);
            row = sheet2.createRow(3);
        	
        	row = sheet2.createRow(5);
        	row.setHeightInPoints(22);
        	CellRangeAddress debit=new CellRangeAddress(5, 5, 2, 3);
            sheet2.addMergedRegion(debit);
            CellRangeAddress credit=new CellRangeAddress(5, 5, 4, 5);
            sheet2.addMergedRegion(credit);
            //创建标题
        	bodybgStyle.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
        	bodybgStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        	
        	cell = row.createCell(2);
            cell.setCellValue(bundle.getString("accountingConfirm_debit"));
            cell.setCellStyle(bodybgStyle);
            cell = row.createCell(3);
            cell.setCellStyle(bodybgStyle);
            cell = row.createCell(4);
            cell.setCellStyle(bodybgStyle);
            cell.setCellValue(bundle.getString("accountingConfirm_credit"));
            cell = row.createCell(5);
            cell.setCellStyle(bodybgStyle);
            cell = row.createCell(6);
            cell.setCellValue(bundle.getString("accountingConfirm_amount"));
            cell.setCellStyle(bodybgStyle);
            
            for(int i = 0;i < accountList.size();i++)
            {
            	row = sheet2.createRow(i + 6);
            	row.setHeightInPoints(30);
            	cell = row.createCell(1);
            	cell.setCellValue(i+1);
            	cell.setCellStyle(numberStyle);
            	cell = row.createCell(2);
            	//cell.setCellValue(accountList.get(i).getDebtorCD());
            	cell.setCellValue(accountList.get(i).getDebtorAidCD());
            	cell.setCellStyle(bodyStyle);
            	cell = row.createCell(3);
            	cell.setCellValue(accountList.get(i).getDebtorName());
            	cell.setCellStyle(bodyStyle);
            	cell = row.createCell(4);
            	//cell.setCellValue(accountList.get(i).getCreditCD());
            	cell.setCellValue(accountList.get(i).getCreditAidCD());
            	cell.setCellStyle(bodyStyle);
            	cell = row.createCell(5);
            	cell.setCellValue(accountList.get(i).getCreditName());
            	cell.setCellStyle(bodyStyle);
            	cell = row.createCell(6);
            	cell.setCellValue(accountList.get(i).getDebtorAmt());
            	cell.setCellStyle(bodyrightStyle);
            }
            /*
            ByteArrayOutputStream os = new ByteArrayOutputStream();
           	workbook.write(os);
           	byte[] buffer = os.toByteArray();
           	os.flush();
           	os.close();
           	return Base64.getEncoder().encodeToString(buffer);
           	*/
            return workbook;
        }catch(Exception e){
        	Logger LOG = LoggerFactory.getLogger(ExportExcelServiceImpl.class);
        	LOG.error("[excelerr,{}]{}",e.getMessage());
        	System.out.println(e.getMessage());
            //e.printStackTrace();
        	return null;
        }
	}
	private XSSFWorkbook createAccountsummaryConfirmExcel(List<AccountEntries> accountList,String fileName,String companyFullName,
				String nowDate,String outputDate,String pointNum,ResourceBundle bundle,String fontName,XSSFWorkbook workbook,String companyID)
	{
		try{
        	// 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        	//HSSFWorkbook workbook = new HSSFWorkbook();
        	// 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        	XSSFSheet sheet3 = workbook.createSheet(fileName);
        	workbook.setSheetName(3, "(4)Processed Journal_Summary");
        	// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        	CellRangeAddress cra=new CellRangeAddress(0, 0, 2, 5);
            sheet3.addMergedRegion(cra);
            CellRangeAddress crb=new CellRangeAddress(0, 0, 0, 1);
            sheet3.addMergedRegion(crb);
            CellRangeAddress cre=new CellRangeAddress(0, 0, 6, 7);
            sheet3.addMergedRegion(cre);
            
            XSSFDataFormat format= workbook.createDataFormat();
            String moneyUnit = getCompanyMoneyUnit(companyID);
            String formatStr = moneyUnit + "#,##0"+pointNum;
            XSSFFont titleFont = getExcelFont(workbook,(short)14,fontName); 
            XSSFFont bodyFont = getExcelFont(workbook,(short)10,fontName);
            XSSFCellStyle numberStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER, 0, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",false);
            XSSFCellStyle companyLeftStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER, 0, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",false);
            XSSFCellStyle titleStyle = getExcelStyle(workbook, titleFont, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER, 0, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",false);
            XSSFCellStyle outDateStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER, 0, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",false);
            XSSFCellStyle outDateLeftStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER, 0, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",false);
            XSSFCellStyle bodyStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",true);
            XSSFCellStyle bodybgStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",true);
            XSSFCellStyle bodyrightStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index,format,formatStr,true);
            
            sheet3.setColumnWidth(0, 22*256);
            sheet3.setColumnWidth(1, 10*256);
            sheet3.setColumnWidth(2, 12*256);
            sheet3.setColumnWidth(3, 18*256);
            sheet3.setColumnWidth(4, 12*256);
            sheet3.setColumnWidth(5, 18*256);
            sheet3.setColumnWidth(6, 20*256);
            sheet3.setColumnWidth(7, 8*256);
            
            XSSFCell cell = null;
            XSSFRow row = sheet3.createRow(0);
            //0行  title
            row.setHeightInPoints(30);
            cell = row.createCell(0);
            cell.setCellValue(companyFullName);
            cell.setCellStyle(companyLeftStyle);
            
            cell = row.createCell(2);
            cell.setCellValue(bundle.getString("accounting_entry_title"));
            cell.setCellStyle(titleStyle);
            
            cell = row.createCell(6);
            cell.setCellValue(bundle.getString("outPutDay")+"："+nowDate.replace("-","/"));
            cell.setCellStyle(outDateStyle);
            //1行  输出日行
            //row.setHeightInPoints(30);
            row = sheet3.createRow(2);
            cell = row.createCell(0);
            cell.setCellValue(bundle.getString("accounting_confirm_month_date")+"："+outputDate.replace("-","/"));
            cell.setCellStyle(outDateLeftStyle);
            
        	row = sheet3.createRow(5);
        	row.setHeightInPoints(22);
        	CellRangeAddress debit=new CellRangeAddress(5, 5, 2, 3);
            sheet3.addMergedRegion(debit);
            CellRangeAddress credit=new CellRangeAddress(5, 5, 4, 5);
            sheet3.addMergedRegion(credit);
        	//创建标题
        	bodybgStyle.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
        	bodybgStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        	
        	cell = row.createCell(2);
            cell.setCellValue(bundle.getString("accountingConfirm_debit"));
            cell.setCellStyle(bodybgStyle);
            cell = row.createCell(3);
            cell.setCellStyle(bodybgStyle);
            cell = row.createCell(4);
            cell.setCellStyle(bodybgStyle);
            cell.setCellValue(bundle.getString("accountingConfirm_credit"));
            cell = row.createCell(5);
            cell.setCellStyle(bodybgStyle);
            cell = row.createCell(6);
            cell.setCellValue(bundle.getString("accountingConfirm_amount"));
            cell.setCellStyle(bodybgStyle);
            
            for(int i = 0;i < accountList.size();i++)
            {
            	row = sheet3.createRow(i + 6);
            	row.setHeightInPoints(30);
            	cell = row.createCell(1);
            	cell.setCellValue(i+1);
            	cell.setCellStyle(numberStyle);
            	cell = row.createCell(2);
            	//cell.setCellValue(accountList.get(i).getDebtorCD());
            	cell.setCellValue(accountList.get(i).getDebtorAidCD());
            	cell.setCellStyle(bodyStyle);
            	cell = row.createCell(3);
            	cell.setCellValue(accountList.get(i).getDebtorName());
            	cell.setCellStyle(bodyStyle);
            	cell = row.createCell(4);
            	//cell.setCellValue(accountList.get(i).getCreditCD());
            	cell.setCellValue(accountList.get(i).getCreditAidCD());
            	cell.setCellStyle(bodyStyle);
            	cell = row.createCell(5);
            	cell.setCellValue(accountList.get(i).getCreditName());
            	cell.setCellStyle(bodyStyle);
            	cell = row.createCell(6);
            	cell.setCellValue(accountList.get(i).getDebtorAmt());
            	cell.setCellStyle(bodyrightStyle);
            }
            /*
            ByteArrayOutputStream os = new ByteArrayOutputStream();
           	workbook.write(os);
           	byte[] buffer = os.toByteArray();
           	os.flush();
           	os.close();
           	return Base64.getEncoder().encodeToString(buffer);
           	*/
            return workbook;
        }catch(Exception e){
        	Logger LOG = LoggerFactory.getLogger(ExportExcelServiceImpl.class);
        	LOG.error("[excelerr,{}]{}",e.getMessage());
        	System.out.println(e.getMessage());
            //e.printStackTrace();
        	return null;
        }
	}
	private String getCompanyMoneyUnit(String companyID)
	{
		return commonMethedMapper.getCompanyMoneyUnit(companyID);
	}
}
