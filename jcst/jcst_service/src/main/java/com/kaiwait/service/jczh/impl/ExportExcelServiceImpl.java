package com.kaiwait.service.jczh.impl;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.kaiwait.bean.jczh.entity.Calculate;
import com.kaiwait.bean.jczh.entity.Cost;
import com.kaiwait.bean.jczh.entity.ExcelReport;
import com.kaiwait.bean.jczh.entity.Role;
import com.kaiwait.bean.jczh.io.OutPutInput;
import com.kaiwait.bean.jczh.vo.CommonmstVo;
import com.kaiwait.core.utils.DateUtil;
import com.kaiwait.core.utils.JasperOutPdfUtil;
import com.kaiwait.core.utils.MathController;
import com.kaiwait.mappers.jczh.CommonMethedMapper;
import com.kaiwait.mappers.jczh.ExportExcelMapper;
import com.kaiwait.mappers.jczh.JobLandMapper;
import com.kaiwait.service.jczh.CommonMethedService;
import com.kaiwait.service.jczh.ExportExcelService;

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
	
	//private String bundleNmae = "jcyclicExcelBundle";
	
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
	private HSSFCellStyle getExcelStyle(HSSFWorkbook workbook,HSSFFont efont,short lrAlign, 
			short tbAlign, int isBoder,short border,short color,HSSFDataFormat format,String formatStr,boolean wrapT)
	{
		HSSFCellStyle style = workbook.createCellStyle();
		if(efont != null)
		{
			style.setFont(efont);
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
        }
		return style;
	}
	private HSSFFont getExcelFont(HSSFWorkbook workbook,short fontSize,String fontName)
	{
		HSSFFont eFont = workbook.createFont();
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
        	HSSFWorkbook workbook = new HSSFWorkbook();
        	// 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        	HSSFSheet sheet = workbook.createSheet(pars.getFileName());
        	// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
            CellRangeAddress cra=new CellRangeAddress(0, 0, 0, (nodeList.size()+3));
            sheet.addMergedRegion(cra);
            CellRangeAddress crd=new CellRangeAddress(1, 1, 0, 2);
            sheet.addMergedRegion(crd);
            
            
            HSSFFont titleFont = getExcelFont(workbook,(short)14,fontName); 
            HSSFFont bodyFont = getExcelFont(workbook,(short)10,fontName);
            
            HSSFCellStyle titleStyle = getExcelStyle(workbook, titleFont, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER, 0, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",false);
            HSSFCellStyle outDateStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER, 0, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",false);
            HSSFCellStyle bodyStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",true);
            HSSFCellStyle bodyLeftStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",true);
            
            
            HSSFCell cell = null;
            String colorStr = "";
            
            String nodeStr = "";
            String[] nodeStrArr;
            HSSFRow row = sheet.createRow(0);
            //0行  title
            row.setHeightInPoints(30);
            cell = row.createCell(0);
            cell.setCellValue(bundle.getString("empPowerTitle")+"("+companyFullName+")");
            cell.setCellStyle(titleStyle);
            //1行  输出日行
            //row.setHeightInPoints(30);
            row = sheet.createRow(1);
            cell = row.createCell(0);
            cell.setCellValue(bundle.getString("outPutDay")+"："+timeByZ);
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
            	HSSFCellStyle style = workbook.createCellStyle();
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
            	HSSFPalette palette = ((HSSFWorkbook) workbook).getCustomPalette();
            	style.setFillForegroundColor((short) colorIndex);
                style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                palette.setColorAtIndex((short) colorIndex,
                        (byte) convertHexToNumber(colorStr.substring(0, 2)),//RGB red
                        (byte) convertHexToNumber(colorStr.substring(2, 4)),//RGB green
                        (byte) convertHexToNumber(colorStr.substring(4, 6)) //RGB blue
                );
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
		
		int titleLength = 17;
		List<ExcelReport> reportList = null;
		if(pars.getFileName().equals("jobList"))
		{
			reportList = exportExcelMapper.getReportListByJobs(pars.getCompanyID(),pars.getStartDate(),pars.getEndDate(),pars.getCldivID());
		}else {
			reportList = exportExcelMapper.getReportListByJobsLabel(pars.getCompanyID(),pars.getStartDate(),pars.getEndDate(),pars.getCldivID());
			titleLength = 18;
		}
		
		Calculate calculate = new Calculate();
		ExcelReport excelReport = new ExcelReport();
		String companyName = exportExcelMapper.getCompanyName(pars.getCompanyID());
		String timeByZ = commonMethedService.getTimeByZone(DateUtil.getDateForNow(DateUtil.dateTimeFormat), pars.getCompanyID(),DateUtil.dateFormat);
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
    		chooseDate += DateUtil.dateToString(DateUtil.stringtoDate(pars.getStartDate(), DateUtil.dateFormat), DateUtil.accountDateFormat);
    	}
    	if(!pars.getEndDate().equals(""))
    	{
    		chooseDate += " - " + DateUtil.dateToString(DateUtil.stringtoDate(pars.getEndDate(), DateUtil.dateFormat), DateUtil.accountDateFormat);
    	}
    	String fontName = getFontNameByLangT(pars.getLangTyp());
		try{
			Locale local = JasperOutPdfUtil.getLocalByLangT(pars.getLangTyp());
			ResourceBundle bundle = ResourceBundle.getBundle(JasperOutPdfUtil.BUNDLE_NAME , local);
			
        	// 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        	HSSFWorkbook workbook = new HSSFWorkbook();
        	// 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        	HSSFSheet sheet = workbook.createSheet(pars.getFileName());
        	CellRangeAddress companyNameCell=new CellRangeAddress(0, 0, 0,2);
        	CellRangeAddress titleCell=new CellRangeAddress(0, 0, 3,15);
        	CellRangeAddress clNameCell=new CellRangeAddress(1, 1, 1,3);
        	CellRangeAddress clCodeCell=new CellRangeAddress(2, 2, 1,3);
        	CellRangeAddress divdayCell=new CellRangeAddress(3, 3, 1,3);
            sheet.addMergedRegion(companyNameCell);
            sheet.addMergedRegion(titleCell);
            sheet.addMergedRegion(clNameCell);
            sheet.addMergedRegion(clCodeCell);
            sheet.addMergedRegion(divdayCell);
        	// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
            HSSFRow row = sheet.createRow(0);
            // 第四步，创建单元格，并设置值表头 设置表头居中
            
            HSSFFont titleFont = getExcelFont(workbook,(short)14,fontName); 
            HSSFFont bodyFont = getExcelFont(workbook,(short)10,fontName);
            
            HSSFDataFormat format= workbook.createDataFormat();
            
            HSSFCellStyle style = getExcelStyle(workbook, titleFont, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER, 0, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",false);
            HSSFCellStyle styleT = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER, 0, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",false);
            HSSFCellStyle bodyStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",true);
            HSSFCellStyle bodyLabelTotalStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",true);
            HSSFCellStyle bodyLeftStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER, 0, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",true);
            String formatStr = "¥#,##0"+pointNum;
            String porfitRateformatStr = "@";
            HSSFCellStyle porfitRateStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, format,porfitRateformatStr,true);
            HSSFCellStyle porfitLaeblTotalRateStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, format,porfitRateformatStr,true);
            HSSFCellStyle moneyStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, format,formatStr,true);
            HSSFCellStyle moneyLabelTotalStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, format,formatStr,true);
            HSSFCellStyle bodyTabLeftStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",true);
            HSSFCellStyle totalMontyStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, format,formatStr,true);
            
            HSSFCell cell = null;
            row.setHeightInPoints(24);
            
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
            cell.setCellValue(bundle.getString("outPutDay")+"： "+timeByZ);
            cell.setCellStyle(styleT);
        	
            if(!pars.getCldivID().equals(""))
            {
	        	row = sheet.createRow(1);
	        	row.setHeightInPoints(18);
	        	cell = row.createCell(0);
	        	//得意先
	        	cell.setCellValue(bundle.getString("cldivName")+"　：");
	        	cell.setCellStyle(bodyLeftStyle);
	        	cell = row.createCell(1);
	        	cell.setCellValue(reportList.get(0).getCldivAccountID());
	        	cell.setCellStyle(bodyLeftStyle);
	        	
	        	row = sheet.createRow(2);
	        	row.setHeightInPoints(18);
	        	cell = row.createCell(1);
        		if(reportList.size() > 0)
        		{
        			cell.setCellValue(reportList.get(0).getdClientName()+"("+pars.getCldivID()+")");
        		}
	        	cell.setCellStyle(bodyLeftStyle);
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
        			if(i == 1)
        			{
        				cell.setCellValue(bundle.getString("jobs_99"));
        			}else if(i > 1){
        				cell.setCellValue(bundle.getString("jobs_"+(i-1)));
        			}else {
        				cell.setCellValue(bundle.getString("jobs_"+i));
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
        	int cellIndex = 0;
            //创建内容
        	for(int i = 0;i < reportList.size();i++)
    		{
        		if(!pars.getFileName().equals("jobList"))
        		{
        			cellIndex = 0;
        		}
        		excelReport = reportList.get(i);
        		jobCreateDate = commonMethedService.getTimeByZone(excelReport.getJobCreateDate(), pars.getCompanyID(),DateUtil.dateFormat);
        		calculate = commonMethedService.calcuateJobTax(excelReport.getJobNo(), Integer.valueOf(pars.getCompanyID()));
        		row = sheet.createRow(i+rowIndex);
        		row.setHeightInPoints(26);
        		
        		cell = row.createCell(cellIndex + 0);
        		cell.setCellValue(excelReport.getdClientName());
        		cell.setCellStyle(bodyTabLeftStyle);
        		if(!pars.getFileName().equals("jobList"))
        		{
        			cell = row.createCell(cellIndex + 1);
            		cell.setCellValue(excelReport.getLabelName());
            		cell.setCellStyle(bodyTabLeftStyle);
        			cellIndex = 1;
        			excelReport.setCldivID(excelReport.getLabelID());
        			if(i != (reportList.size()-1))
        			{
        				//出力label报表时，把i的下一位labelID赋值给i的下一位cldivcd，用来与当前值判断是否为一致。
        				reportList.get(i+1).setCldivID(reportList.get(i+1).getLabelID());
        			}
        		}
        		cell = row.createCell(cellIndex + 1);
        		cell.setCellValue(excelReport.getJobNo());
        		cell.setCellStyle(bodyTabLeftStyle);
        		
        		cell = row.createCell(cellIndex + 2);
        		cell.setCellValue(jobCreateDate);
        		cell.setCellStyle(bodyStyle);
    			
        		cell = row.createCell(cellIndex + 3);
        		cell.setCellValue(excelReport.getPlanDlvday());
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
        				cell.setCellValue(calculate.getPlanCostVatAmt());
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
        			cell.setCellValue(calculate.getProfitRate()+"%");
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
                    	sumSaleVatTotal = MathController.add(sumSaleVatTotal, calculate.getSaleAmt());
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
            				costVatTotal = MathController.add(costVatTotal, calculate.getPlanCostVatAmt());
            				sumCostVatTotal = MathController.add(sumCostVatTotal, calculate.getPlanCostVatAmt());
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
            			cell.setCellValue(profitRate+"%");
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
            			cell.setCellValue(sumProfitRate+"%");
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
	//買掛金発生明細
	public String exportPaybelReport(OutPutInput pars) {
		if(validateChooeseDate(pars.getEndDate(),pars.getCompanyID()))
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
        	HSSFWorkbook workbook = new HSSFWorkbook();
        	// 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        	HSSFSheet sheet = workbook.createSheet(pars.getFileName());
        	//CellRangeAddress titleCell=new CellRangeAddress(0, 0, 1,4);
            //sheet.addMergedRegion(titleCell);
        	// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
            HSSFRow row = sheet.createRow(0);
            // 第四步，创建单元格，并设置值表头 设置表头居中
            
            HSSFFont titleFont = getExcelFont(workbook,(short)14,fontName); 
            HSSFFont bodyFont = getExcelFont(workbook,(short)10,fontName);
            
            HSSFDataFormat format= workbook.createDataFormat();
            
            HSSFCellStyle style = getExcelStyle(workbook, titleFont, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_TOP, 0, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",false);
            HSSFCellStyle styleT = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER, 0, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",false);
            HSSFCellStyle bodyStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",true);
            HSSFCellStyle bodyLeftStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",true);
            //HSSFCellStyle bodyRightStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",true);
            String formatStr = "¥#,##0"+pointNum;
            
            HSSFCellStyle totalmoneyStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, format,formatStr,true);
            HSSFCellStyle moneyStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, format,formatStr,true);
            HSSFCellStyle totalCenterStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,null,true);
            
            HSSFCell cell = null;
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
            cell.setCellValue(bundle.getString("outPutDay")+"："+timeByZ);
            cell.setCellStyle(styleT);
        	
        	row = sheet.createRow(1);
        	row.setHeightInPoints(18);
        	cell = row.createCell(0);
        	
        	
        	row = sheet.createRow(2);
        	row.setHeightInPoints(18);
        	cell = row.createCell(0);
        	//計上（予定）月
        	cell.setCellValue(bundle.getString("planDlvMonth")+"　：");
        	cell.setCellStyle(styleT);
        	cell = row.createCell(1);
        	cell.setCellValue(chooseDate);
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
            		cell.setCellValue(excelReport.getConfirmDate());
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
        	row = sheet.createRow(pableTopList.size() + 6);
        	row.setHeightInPoints(20);
        	CellRangeAddress clTotal = null;
            cell = row.createCell(0);
            clTotal=new CellRangeAddress(pableTopList.size() + 6, pableTopList.size() + 6, 0,4);
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
    		cell.setCellValue(pableTotal.getPayAmt());
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
    			cell.setCellValue(pableButtonList.get(i).getCldivAccountID() + "//" +pableButtonList.get(i).getpClientName());
    			
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
    		cell.setCellValue(pableTotal.getPayAmt());
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
		if(validateChooeseDate(pars.getEndDate(),pars.getCompanyID()))
		{
			return "DATE_RANGE_ERROR";
		}
		
		String startDate = commonMethedMapper.searchStartTimeBySummaryMonth(Integer.valueOf(pars.getCompanyID()),DateUtil.makeDate(pars.getEndDate()));
		String endDate = commonMethedMapper.searchEndTimeBySummaryMonth(Integer.valueOf(pars.getCompanyID()),pars.getEndDate());
		List<ExcelReport> hopleCostTopList = exportExcelMapper.getReportListByHopleCost(pars.getCompanyID(),startDate,endDate,pars.getEndDate(),DateUtil.makeDate(pars.getEndDate()));
		
		ExcelReport excelReport = new ExcelReport();
		String companyName = exportExcelMapper.getCompanyName(pars.getCompanyID());
		String timeByZ = commonMethedService.getTimeByZone(DateUtil.getDateForNow(DateUtil.dateTimeFormat), pars.getCompanyID(),DateUtil.dateFormat);
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
        	HSSFWorkbook workbook = new HSSFWorkbook();
        	// 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        	HSSFSheet sheet = workbook.createSheet(pars.getFileName());
        	//CellRangeAddress titleCell=new CellRangeAddress(0, 0, 1,8);
           // sheet.addMergedRegion(titleCell);
        	// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
            HSSFRow row = sheet.createRow(0);
            // 第四步，创建单元格，并设置值表头 设置表头居中
            
            HSSFFont titleFont = getExcelFont(workbook,(short)14,fontName); 
            HSSFFont bodyFont = getExcelFont(workbook,(short)10,fontName);
            
            HSSFDataFormat format= workbook.createDataFormat();
            
            HSSFCellStyle style = getExcelStyle(workbook, titleFont, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER, 0, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",false);
            HSSFCellStyle styleT = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER, 0, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",false);
            HSSFCellStyle styleR = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER, 0, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",false);
            HSSFCellStyle bodyStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",true);
            HSSFCellStyle bodyLeftStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",true);
            //HSSFCellStyle bodyRightStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",true);
            String formatStr = "¥#,##0"+pointNum;
            
            HSSFCellStyle moneyStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, format,formatStr,true);
            HSSFCellStyle totalMontyStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, format,formatStr,true);
            totalMontyStyle.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
			totalMontyStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			
			HSSFCellStyle totalCenterStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, format,formatStr,true);
			totalCenterStyle.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
			totalCenterStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			
            HSSFCell cell = null;
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
            cell.setCellValue(bundle.getString("outPutDay")+"："+timeByZ);
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
        	cell.setCellValue(chooseDate);
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
            		cell.setCellValue(commonMethedService.getTimeByZone(excelReport.getOrderAddDate(),pars.getCompanyID(),DateUtil.dateFormat));
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
        	for(int i = 5;i < 10;i++)
        	{
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
		if(validateChooeseDate(pars.getEndDate(),pars.getCompanyID()))
		{
			return "DATE_RANGE_ERROR";
		}
		
		String startDate = commonMethedMapper.searchStartTimeBySummaryMonth(Integer.valueOf(pars.getCompanyID()),DateUtil.makeDate(pars.getEndDate()));
		String endDate = commonMethedMapper.searchEndTimeBySummaryMonth(Integer.valueOf(pars.getCompanyID()),pars.getEndDate());
		List<ExcelReport> jobList = exportExcelMapper.getRportJobList(pars.getCompanyID(),pars.getEndDate());
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
			
			//确定原价，仕入増値税
			excelReport = exportExcelMapper.getConfirmAmtAndConfirmVatByJoB(pars.getCompanyID(), jobList.get(i).getJobNo(), startDate, endDate);
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
			jobList.get(i).setSellupdate(commonMethedService.getTimeByZone(jobList.get(i).getSellupdate(), pars.getCompanyID(),DateUtil.dateFormat));
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
		String timeByZ = commonMethedService.getTimeByZone(DateUtil.getDateForNow(DateUtil.dateTimeFormat), pars.getCompanyID(),DateUtil.dateFormat);
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
        	HSSFWorkbook workbook = new HSSFWorkbook();
        	
        	FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
    		CellValue cellValue = null;
        	// 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        	HSSFSheet sheet = workbook.createSheet(pars.getFileName());
        	//CellRangeAddress titleCell=new CellRangeAddress(0, 0, 1,4);
            //sheet.addMergedRegion(titleCell);
        	// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
            HSSFRow row = sheet.createRow(0);
            // 第四步，创建单元格，并设置值表头 设置表头居中
            
            HSSFFont titleFont = getExcelFont(workbook,(short)14,fontName); 
            HSSFFont bodyFont = getExcelFont(workbook,(short)10,fontName);
            
            
            
            HSSFDataFormat format= workbook.createDataFormat();
            String formatStr = "¥#,##0"+pointNum;
            
            String porfitRateformatStr = "@";
            HSSFCellStyle porfitRateStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, format,porfitRateformatStr,true);
            HSSFCellStyle porfitRateStyleBlack = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, format,porfitRateformatStr,true);
            porfitRateStyleBlack.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
			porfitRateStyleBlack.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            HSSFCellStyle style = getExcelStyle(workbook, titleFont, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER, 0, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",false);
            HSSFCellStyle styleT = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER, 0, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",false);
            HSSFCellStyle styleR = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER, 0, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",false);
            HSSFCellStyle bodyStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",true);
            HSSFCellStyle bodyLeftStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",true);
            HSSFCellStyle bodyRightStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, null,"",true);
           
            HSSFCellStyle moneyStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, format,formatStr,true);
            HSSFCellStyle totalMontyStyle = getExcelStyle(workbook, bodyFont, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.GREY_80_PERCENT.index, format,formatStr,true);
            HSSFCellStyle sStyle = workbook.createCellStyle();
            HSSFCell cell = null;
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
            cell.setCellValue(bundle.getString("outPutDay")+"："+timeByZ);
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
        	cell.setCellValue(chooseDate);
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
        	int sumIndex = 6;
        	String cellFormula = "";
        	Double profitAmt = 0.0; 
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
        				//sumIndex = sumIndex+i+1;
        			}
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
            			cell = row.createCell(6);
                		cell.setCellFormula("SUM(G"+sumIndex+":G"+(i+startRowIndex)+")");
                		cell.setCellStyle(sStyle);
                		cell = row.createCell(7);
                		cell.setCellFormula("SUM(H"+sumIndex+":H"+(i+startRowIndex)+")");
                		cell.setCellStyle(sStyle);
                		cell = row.createCell(8);
                		cell.setCellFormula("SUM(I"+sumIndex+":I"+(i+startRowIndex)+")");
                		cell.setCellStyle(sStyle);
                		cell = row.createCell(9);
                		cell.setCellFormula("SUM(J"+sumIndex+":J"+(i+startRowIndex)+")");
                		cell.setCellStyle(sStyle);
                		cell = row.createCell(10);
                		cell.setCellFormula("E"+(i+startRowIndex+1)+"-G"+(i+startRowIndex+1)+"-H"+(i+startRowIndex+1)+"-J"+(i+startRowIndex+1));
                		cellValue = evaluator.evaluate(cell);
                		profitAmt = cellValue.getNumberValue();
                		 
                		cell.setCellStyle(sStyle);
                		cell = row.createCell(11);
                		if(excelReport.getSaleAmt()==0.0||excelReport.getSaleAmt()==null)
                		{
                			cell.setCellValue("INF");
                		}else {
                			cell.setCellValue(calcuateProfitRate(excelReport.getSaleAmt(),profitAmt,foreignFormatFlg,pointNumber)+"%");
                		}
                		//cell.setCellFormula("IF(K"+(i+startRowIndex)+"=0,\"Infinity\",K"+(i+startRowIndex)+"/E"+(i+startRowIndex)+"*100)");
                		cell.setCellStyle(totalMontyStyle);
            		}else {
            			//String rowIndexNums[] = cellFormula.split(",");
            			cell = row.createCell(6);
                		cell.setCellFormula(getSumTotalByCell("G",cellFormula));
                		cell.setCellStyle(sStyle);
                		cell = row.createCell(7);
                		cell.setCellFormula(getSumTotalByCell("H",cellFormula));
                		cell.setCellStyle(sStyle);
                		cell = row.createCell(8);
                		cell.setCellFormula(getSumTotalByCell("I",cellFormula));
                		cell.setCellStyle(sStyle);
                		cell = row.createCell(9);
                		cell.setCellFormula(getSumTotalByCell("J",cellFormula));
                		cell.setCellStyle(sStyle);
                		cell = row.createCell(10);
                		cell.setCellFormula("E"+(i+startRowIndex+1)+"-G"+(i+startRowIndex+1)+"-H"+(i+startRowIndex+1)+"-J"+(i+startRowIndex+1));
                		cellValue = evaluator.evaluate(cell);
                		profitAmt = cellValue.getNumberValue();
                		cell.setCellStyle(sStyle);
                		cell = row.createCell(11);
                		if(excelReport.getSaleAmt()==0.0||excelReport.getSaleAmt()==null)
                		{
                			cell.setCellValue("INF");
                		}else {
                			cell.setCellValue(calcuateProfitRate(excelReport.getSaleAmt(),profitAmt,foreignFormatFlg,pointNumber)+"%");
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
            		cell.setCellFormula("E"+(i+startRowIndex+1)+"-G"+(i+startRowIndex+1)+"-H"+(i+startRowIndex+1)+"-J"+(i+startRowIndex+1));
            		cellValue = evaluator.evaluate(cell);
            		profitAmt = cellValue.getNumberValue();
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
            		cell.setCellValue(excelReport.getSellupdate());
            		cell.setCellStyle(bodyStyle);
            		cell = row.createCell(13);
            		if(excelReport.getIsInvoicePublish() == 1)
            		{
            			cell.setCellValue(bundle.getString("finish"));
            		}else {
            			cell.setCellValue(bundle.getString("noFinish"));
            		}
            		cell.setCellStyle(bodyStyle);
            		cell = row.createCell(14);
            		if(excelReport.getIsReqPublish() == 1)
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
        		cell.setCellFormula("E"+str[totalIndex]);
        		cell.setCellStyle(moneyStyle);
        		cell = row.createCell(5);
        		cell.setCellFormula("F"+str[totalIndex]);
        		cell.setCellStyle(moneyStyle);
        		cell = row.createCell(6);
        		cell.setCellFormula("G"+str[totalIndex]);
        		cell.setCellStyle(moneyStyle);
        		cell = row.createCell(7);
        		cell.setCellFormula("H"+str[totalIndex]);
        		cell.setCellStyle(moneyStyle);
        		cell = row.createCell(8);
        		cell.setCellFormula("I"+str[totalIndex]);
        		cell.setCellStyle(moneyStyle);
        		cell = row.createCell(9);
        		cell.setCellFormula("J"+str[totalIndex]);
        		cell.setCellStyle(moneyStyle);
        		cell = row.createCell(10);
        		//cell.setCellFormula("=K"+str[totalIndex]);
        		cell.setCellFormula("K"+str[totalIndex]);
        		cell.setCellStyle(moneyStyle);
        		cell = row.createCell(11);
        		cell.setCellFormula("L"+str[totalIndex]);
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
        		int strLasterIndex = Integer.valueOf(str[str.length-1])+1;
        		cell = row.createCell(4);
        		cell.setCellFormula("E"+strLasterIndex);
        		cell.setCellStyle(sStyle);
        		cell = row.createCell(5);
        		cell.setCellFormula("F"+strLasterIndex);
        		cell.setCellStyle(sStyle);
        		cell = row.createCell(6);
        		cell.setCellFormula("G"+strLasterIndex);
        		cell.setCellStyle(sStyle);
        		cell = row.createCell(7);
        		cell.setCellFormula("H"+strLasterIndex);
        		cell.setCellStyle(sStyle);
        		cell = row.createCell(8);
        		cell.setCellFormula("I"+strLasterIndex);
        		cell.setCellStyle(sStyle);
        		cell = row.createCell(9);
        		cell.setCellFormula("J"+strLasterIndex);
        		cell.setCellStyle(sStyle);
        		cell = row.createCell(10);
        		//cell.setCellFormula("=K"+str[totalIndex]);
        		cell.setCellFormula("K"+strLasterIndex);
        		cell.setCellStyle(sStyle);
        		cell = row.createCell(11);
        		cell.setCellFormula("L"+strLasterIndex);
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
	private String getSumTotalByCell(String keyCode,String cellFormula)
	{
		String str[] = cellFormula.split(",");
		String numStr = "sum(";
		for(int i = 0;i < str.length;i++)
		{
			numStr += keyCode + str[i];
			if(i != str.length-1)
			{
				numStr += ",";
			}else {
				numStr += ")";
			}
		}
		return numStr;
	}
	private String calcuateProfitRate(Double saleAmt,Double profit,int foreignFormatFlg,int pointNumber)
	{
		java.text.NumberFormat nf = java.text.NumberFormat.getInstance();   
		nf.setGroupingUsed(false); 
		Double profitRate = 0.0;
		profitRate = MathController.mul(MathController.div(profit,saleAmt),100);
		profitRate = commonMethedService.pointFormatHandler(profitRate,3,2);
		return nf.format(profitRate);
	}
	//制作勘定残高明細
	public String exportMadeCostReport(OutPutInput pars) {
		if(validateChooeseDate(pars.getEndDate(),pars.getCompanyID()))
		{
			return "DATE_RANGE_ERROR";
		}
		return null;
	}
	private boolean validateChooeseDate(String dt,String companyID)
	{
		String accountDate = DateUtil.getAddNumMotnDate(commonMethedMapper.getSystemDate(Integer.valueOf(companyID)),0,DateUtil.dateFormatYeayMonth);
		String cDate = DateUtil.getAddNumMotnDate(dt,0,DateUtil.dateFormatYeayMonth);
		
		if(Integer.valueOf(cDate) >= Integer.valueOf(accountDate))
		{
			return true;
		}
		return false;
	}
}
