package com.kaiwait.utils.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFName;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 */
public class ExcelUtil {
	/** Logger */
	private static final Log LOGGER = LogFactory.getLog(ExcelUtil.class);
	private XSSFWorkbook workBook;
	private XSSFSheet currentSheet;
	private String DATE_FORMAT_STR = "yyyy/MM/dd";
	private XSSFFormulaEvaluator evaluator;

	/**
	 * 创建并读入一个只有一个sheet的工作薄
	 */
	public ExcelUtil() {
		this(null);
	}

	/**
	 * 创建并读入一个只有一个sheet的工作薄并指定sheetname
	 * 
	 */
	public ExcelUtil(String sheetName) {
		workBook = new XSSFWorkbook();// 创建一个工作簿
		if (sheetName == null) {
			this.currentSheet = workBook.createSheet();// 创建工作表
		} else {
			this.currentSheet = workBook.createSheet(sheetName);// 创建工作表
		}
	}

	/**
	 * 根据InputStream，sheet名，开始行来读入一个工作薄
	 *
	 */
	public ExcelUtil(InputStream is, String sheetName) {
		try {
			workBook = new XSSFWorkbook(is);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		this.currentSheet = workBook.getSheet(sheetName);
	}

	/**
	 * 根据文件名，sheet名，开始行来读入一个工作薄
	 *
	 */
	public ExcelUtil(String fileName, String sheetName) {
		try {
			workBook = new XSSFWorkbook(new FileInputStream(fileName));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		this.currentSheet = workBook.getSheet(sheetName);
	}

	/**
	 * 根据InputStream，sheetindex，开始行来读入一个工作薄
	 *
	 */
	public ExcelUtil(InputStream is, int sheetIndex) {
		try {
			workBook = new XSSFWorkbook(is);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		this.currentSheet = workBook.getSheetAt(sheetIndex);
	}

	/**
	 * 根据文件名，sheetindex，开始行来读入一个工作薄
	 *
	 */
	public ExcelUtil(String fileName, int sheetIndex) {
		try {
			workBook = new XSSFWorkbook(new FileInputStream(fileName));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		this.currentSheet = workBook.getSheetAt(sheetIndex);
	}

	/**
	 * 创建新的sheet
	 */
	public String createNewSheet() {
		return workBook.createSheet().getSheetName();// 创建工作表
	}

	/**
	 * 创建新的sheet并命名
	 */
	public String createNewSheet(String sheetName) {
		return workBook.createSheet(sheetName).getSheetName();// 创建工作表
	}

	/**
	 * 根据sheet名切换当前sheet
	 */
	public void switchSheet(String sheetName) {
		this.currentSheet = workBook.getSheet(sheetName);
	}

	public void setCellName(String cname, String reference) {
		XSSFName namedCell = workBook.getName(cname);
		if (namedCell == null) {
			namedCell = workBook.createName();
			namedCell.setNameName(cname);
		}
		namedCell.setRefersToFormula(reference);
	}

	/**
	 * 根据sheetIndex切换当前sheet
	 */
	public void switchSheet(int sheetIndex) {
		this.currentSheet = workBook.getSheetAt(sheetIndex);
	}

	// public XSSFCellStyle makeStyle(XSSFCell cloneStyleFrom, Short
	// fontHeightInPoints, String fontName, Boolean bold,
	// Short fontColor) {
	// XSSFCellStyle cellStyle = workBook.createCellStyle();
	//
	// if (cloneStyleFrom != null) {
	// final XSSFCellStyle cellStyleFrom = cloneStyleFrom.getCellStyle();
	// cellStyle.cloneStyleFrom(cellStyleFrom);
	// }
	// if (fontHeightInPoints != null) {
	// cellStyle.getFont().setFontHeightInPoints(fontHeightInPoints);
	// }
	// if (fontName != null) {
	// cellStyle.getFont().setFontName(fontName);
	// }
	// if (bold != null) {
	// cellStyle.getFont().setBold(bold);
	// }
	// if (fontColor != null) {
	// cellStyle.getFont().setColor(fontColor);
	// }
	// return cellStyle;
	// }

	// public void setStyle(XSSFCell cell, Short fontHeightInPoints, String
	// fontName, Boolean bold, Short fontColor) {
	// XSSFCellStyle cellStyle = workBook.createCellStyle();
	//
	// if (cell != null) {
	// final XSSFCellStyle cellStyleFrom = cell.getCellStyle();
	// cellStyle.cloneStyleFrom(cellStyleFrom);
	// }
	// final XSSFFont font = workBook.createFont();
	// if (fontHeightInPoints != null) {
	// font.setFontHeightInPoints(fontHeightInPoints);
	// }
	// if (fontName != null) {
	// font.setFontName(fontName);
	// }
	// if (bold != null) {
	// font.setBold(bold);
	// }
	// if (fontColor != null) {
	// font.setColor(fontColor);
	// }
	// cellStyle.setFont(font);
	// cell.setCellStyle(cellStyle);
	// }

	// /**
	// * 设定打印区间
	// */
	// public void setPrintArea(int sheetIndex, String reference) {
	// this.workBook.setPrintArea(sheetIndex, reference);
	// }

	// /**
	// * 判断是否有下一行
	// *
	// */
	// public boolean hasNextRow() {
	// return currentRowIndex < lastRowIndex;
	// }

	// /**
	// * 如果有下一行，则移动到下一行
	// *
	// */
	// public boolean moveNextRow() {
	// if (hasNextRow()) {
	// currentRowIndex++;
	// this.currentRow = currentSheet.getRow(currentRowIndex);
	// return true;
	// }
	// return false;
	// }
	//
	// /**
	// * 如果有下一行，则移动到下一行，没有则创建一行
	// */
	// public void moveNextOrCreateNewRow() {
	// if (hasNextRow()) {
	// currentRowIndex++;
	// this.currentRow = currentSheet.getRow(currentRowIndex);
	// } else {
	// currentRowIndex++;
	// this.currentRow = currentSheet.createRow(currentRowIndex);
	// }
	// }

	/**
	 * 取当前行的单元格内容
	 * 
	 */
	private String getString(XSSFRow row, int columnNo) {
		return getData(row, columnNo, DATE_FORMAT_STR);
	}

	/**
	 * 取当前行的单元格内容
	 * 
	 */
	private String getData(XSSFRow row, int columnNo, String dateFormat) {
		if (row == null) {
			return StringUtil.EMPTY_STRING;
		}
		XSSFCell cell = row.getCell(columnNo, Row.CREATE_NULL_AS_BLANK);

		int cellType = cell.getCellType();
		if (cellType == Cell.CELL_TYPE_FORMULA) {
			if (evaluator == null) {
				evaluator = new XSSFFormulaEvaluator(workBook);
			}
			CellValue formulaCellValue = evaluator.evaluate(cell);
			if (formulaCellValue.getCellType() != Cell.CELL_TYPE_ERROR) {
				cellType = formulaCellValue.getCellType();
			}
		}

		switch (cellType) {
		case Cell.CELL_TYPE_BLANK:
			return StringUtil.EMPTY_STRING;
		case Cell.CELL_TYPE_BOOLEAN:
			return String.valueOf(cell.getBooleanCellValue());
		// 数值
		case Cell.CELL_TYPE_NUMERIC:
			boolean isCellDate = false;
			try {
				isCellDate = DateUtil.isCellDateFormatted(cell);
			} catch (Exception e) {
			}
			if (isCellDate) {
				if (StringUtil.isBlank(dateFormat)) {
					dateFormat = DATE_FORMAT_STR;
				}
				return DateFormatUtils.format(cell.getDateCellValue(), dateFormat);
			} else {
				try {
					return new BigDecimal(String.valueOf(cell.getNumericCellValue())).stripTrailingZeros()
							.toPlainString();
				} catch (Exception e) {
				}
				cell.setCellType(Cell.CELL_TYPE_STRING);
				String temp = cell.getStringCellValue();
				if (StringUtil.isNotBlank(temp)) {
					temp = temp.replaceAll("#N/A", StringUtil.EMPTY_STRING).trim();
				}
				return temp;
			}
		case Cell.CELL_TYPE_STRING:
			return cell.getStringCellValue();
		case Cell.CELL_TYPE_ERROR:
			return StringUtil.EMPTY_STRING;
		case Cell.CELL_TYPE_FORMULA:
			boolean isFormulaCellDate = false;
			try {
				isFormulaCellDate = DateUtil.isCellDateFormatted(cell);
			} catch (Exception e) {
			}
			if (isFormulaCellDate) {
				if (StringUtil.isBlank(dateFormat)) {
					dateFormat = DATE_FORMAT_STR;
				}
				return DateFormatUtils.format(cell.getDateCellValue(), dateFormat);
			} else {
				try {
					return new BigDecimal(String.valueOf(cell.getNumericCellValue())).stripTrailingZeros()
							.toPlainString();
				} catch (Exception e) {
				}

				cell.setCellType(Cell.CELL_TYPE_STRING);
				String temp = cell.getStringCellValue();
				if (StringUtil.isNotBlank(temp)) {
					temp = temp.replaceAll("#N/A", StringUtil.EMPTY_STRING).trim();
				}
				return temp;
			}
		default:
			return StringUtil.EMPTY_STRING;
		}
	}
	/**
	 * 取当前行的单元格内容
	 * 
	 */
	/*
	 * private String getDataWithNumericFormat(XSSFRow row, int columnNo, String
	 * dateFormat) { if (row == null) { return StringUtil.EMPTY_STRING; }
	 * XSSFCell cell = row.getCell(columnNo, Row.CREATE_NULL_AS_BLANK);
	 * 
	 * int cellType = cell.getCellType(); if (cellType ==
	 * Cell.CELL_TYPE_FORMULA) { if (evaluator == null) { evaluator =
	 * workBook.getCreationHelper().createFormulaEvaluator(); } cellType =
	 * evaluator.evaluateFormulaCell(cell); }
	 * 
	 * switch (cellType) { case Cell.CELL_TYPE_BLANK: return
	 * StringUtil.EMPTY_STRING; case Cell.CELL_TYPE_BOOLEAN: return
	 * String.valueOf(cell.getBooleanCellValue()); // 数值 case
	 * Cell.CELL_TYPE_NUMERIC: boolean isCellDate = false; try { isCellDate =
	 * DateUtil.isCellDateFormatted(cell); } catch (Exception e) { } if
	 * (isCellDate) { if (StringUtil.isBlank(dateFormat)) { dateFormat =
	 * DATE_FORMAT_STR; } return DateFormatUtils.format(cell.getDateCellValue(),
	 * dateFormat); } else { DataFormatter dataFormatter = new DataFormatter();
	 * try { return dataFormatter.formatCellValue(cell, evaluator); } catch
	 * (Exception e) { return StringUtil.EMPTY_STRING; } } case
	 * Cell.CELL_TYPE_STRING: return cell.getStringCellValue(); case
	 * Cell.CELL_TYPE_ERROR: return StringUtil.EMPTY_STRING; case
	 * Cell.CELL_TYPE_FORMULA: default: return StringUtil.EMPTY_STRING; } }
	 */

	// /**
	// * 取当前行的单元格内容
	// *
	// */
	// public String getString(int columnNo) {
	// return getString(currentRow, columnNo);
	// }

	/**
	 * 取当前行的单元格内容
	 * 
	 */
	public String getString(int rowNo, int columnNo) {
		return getString(currentSheet.getRow(rowNo), columnNo);
	}

	/**
	 * 取当前行的单元格日期内容
	 * 
	 */
	private Date getDate(XSSFRow row, int columnNo) {
		if (row == null) {
			return null;
		}
		XSSFCell cell = row.getCell(columnNo);
		if (cell != null && cell.getCellType() != XSSFCell.CELL_TYPE_BLANK) {
			Date date = cell.getDateCellValue();
			return date;
		} else {
			return null;
		}
	}

	// /**
	// * 取当前行的单元格日期内容
	// *
	// */
	// public Date getDate(int columnNo) {
	// return getDate(currentRow, columnNo);
	// }

	/**
	 * 取当前行的单元格日期内容
	 * 
	 */
	public Date getDate(int rowNo, int columnNo) {
		return getDate(currentSheet.getRow(rowNo), columnNo);
	}

	// /**
	// * 取当前行的单元格日期内容
	// *
	// */
	// public String getStringWithDateFormat(int columnNo, String dateFormat) {
	// return getData(currentRow, columnNo, dateFormat);
	// }

	/**
	 * 取当前行的单元格日期内容
	 * 
	 */
	public String getStringWithDateFormat(int rowNo, int columnNo, String dateFormat) {
		return getData(currentSheet.getRow(rowNo), columnNo, dateFormat);
	}

	// /**
	// * 设置当前行的单元格内容
	// *
	// */
	// public XSSFCell setString(int columnNo, String value) {
	// XSSFCell cell = currentRow.getCell(columnNo);
	// if (cell == null) {
	// cell = currentRow.createCell(columnNo);
	// }
	// cell.setCellType(XSSFCell.CELL_TYPE_STRING);
	// if (value != null) {
	// cell.setCellValue(StringUtil.defaultIfEmpty(value,
	// StringUtil.EMPTY_STRING));
	// }
	// return cell;
	// }

	/**
	 * 根据行和列，设置单元格内容
	 * 
	 */
	public XSSFCell setString(int rowNo, int columnNo, String value) {
		XSSFRow getRow = currentSheet.getRow(rowNo);
		if (getRow == null) {
			getRow = currentSheet.createRow(rowNo);
		}
		XSSFCell cell = getRow.getCell(columnNo);
		if (cell == null) {
			cell = getRow.createCell(columnNo);
		}
		cell.setCellType(XSSFCell.CELL_TYPE_STRING);
		if (value != null) {
			cell.setCellValue(value);
		}
		return cell;
	}

	/**
	 * 导出文件到指定位置
	 * 
	 */
	public void writeWorkbook(String outputFilePath) throws FileNotFoundException, IOException {
		// check parameter.
		if (StringUtil.isEmpty(outputFilePath))
			throw new IllegalArgumentException("outputFilePath is empty.");
		File outputFile = new File(outputFilePath);

		if (outputFile.exists())
			LOGGER.warn("outputFilePath[" + outputFilePath + "] already exists." + "Overwrite this.");

		// if parent directory of outputFilePath not exists,
		// create it recursive.
		File parentDir = outputFile.getParentFile();
		if (!parentDir.exists()) {
			try {
				if (!parentDir.mkdirs()) {
					String msg = "Try to create parent directory[" + parentDir.getAbsolutePath()
							+ "] for outputFilePath, " + "but occured something error.";
					LOGGER.error(msg);
					throw new IllegalStateException(msg);
				}
				LOGGER.debug("Created directory [" + parentDir.getAbsolutePath() + "].");
			} catch (SecurityException e) {
				LOGGER.error(e.getMessage(), e);
				throw new IllegalStateException(e.getMessage(), e);
			}
		}

		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(new File(outputFilePath));
			workBook.write(fos);
		} catch (FileNotFoundException e) {
			LOGGER.error(e.getMessage(), e);
			throw e;
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
			throw e;
		} finally {
			try {
				if (fos != null)
					fos.close();
			} catch (IOException e) {
				LOGGER.error(e.getMessage(), e);
			}
		}
	}

	/**
	 * 取某个sheet数据
	 * 
	 */
	public String[][] getData() {
		return getData(null, null, null, null, null);
	}

	/**
	 * 取某个sheet数据
	 * 
	 */
	public String[][] getData(String dateFormat) {
		return getData(null, null, null, null, dateFormat);
	}

	/**
	 * 取某个sheet数据
	 * 
	 */
	public String[][] getData(Integer startRow, Integer endRow, Integer startCell, Integer endCell) {
		return getData(startRow, endRow, startCell, endCell, null);
	}

	/**
	 * 取某个sheet数据
	 * 
	 */
	public String[][] getData(Integer startRow, Integer endRow, Integer startCell, Integer endCell, String dateFormat) {
		if (currentSheet.getPhysicalNumberOfRows() == 0) {
			return null;
		}
		int startRowInt;
		if (startRow == null) {
			startRowInt = getMinRowNum();
		} else {
			startRowInt = startRow.intValue();
		}

		if (startRowInt < 0) {
			return null;
		}

		int endRowInt;
		if (endRow == null) {
			endRowInt = currentSheet.getLastRowNum();
		} else {
			endRowInt = endRow.intValue();
		}

		int startCellInt;
		if (startCell == null) {
			startCellInt = getMinCellNum();
		} else {
			startCellInt = startCell.intValue();
		}

		if (startCellInt < 0) {
			return null;
		}

		int endCellInt;
		if (endCell == null) {
			endCellInt = getMaxCellNum();
		} else {
			endCellInt = endCell.intValue();
		}

		int rowSize = endRowInt - startRowInt + 1;
		int cellSize = endCellInt - startCellInt + 1;

		String[][] sheetData = new String[rowSize][cellSize];
		for (int i = startRowInt; i <= endRowInt; i++) {
			XSSFRow row = currentSheet.getRow(i);
			for (int j = startCellInt; j <= endCellInt; j++) {
				sheetData[i - startRowInt][j - startCellInt] = getData(row, j, dateFormat);
			}
		}
		return sheetData;
	}

	/**
	 * 生成Excel表格
	 * 
	 */
	public void setData(String[][] sheetData) {
		setData(sheetData, null, null);
	}

	/**
	 * 生成Excel表格
	 * 
	 */
	public void setData(String[][] sheetData, Integer startRow, Integer startCell) {
		int startRowInt = 0;
		if (startRow != null) {
			startRowInt = startRow.intValue();
		}
		int startCellInt = 0;
		if (startCell != null) {
			startCellInt = startCell.intValue();
		}

		// 创建数据栏单元格并填入数据
		for (int i = 0; i < sheetData.length; i++) {// 遍历
			String[] rowData = sheetData[i];
			for (int j = 0; j < rowData.length; j++) {
				setString(i + startRowInt, j + startCellInt, rowData[j]);
			}
		}
	}

	/**
	 * 取sheet数
	 * 
	 */
	public int getSheetSize() {
		return workBook.getNumberOfSheets();
	}

	/**
	 * 取最大的列数,如果一列都没则返回-1
	 * 
	 */
	public int getMaxCellNum() {
		if (currentSheet.getPhysicalNumberOfRows() == 0) {
			return -1;
		}
		int firstRowNum = currentSheet.getFirstRowNum();
		int lastRowNum = currentSheet.getLastRowNum();
		int maxCellNum = 0;

		for (int i = firstRowNum; i <= lastRowNum; i++) {
			XSSFRow row = currentSheet.getRow(i);
			if (row != null) {
				if (row.getLastCellNum() - 1 > maxCellNum) {
					maxCellNum = row.getLastCellNum() - 1;
				}
			}
		}

		return maxCellNum;
	}

	/**
	 * 取最小的行数,如果一行都没则返回-1
	 * 
	 */
	public int getMinRowNum() {
		if (currentSheet.getPhysicalNumberOfRows() == 0) {
			return -1;
		}
		return currentSheet.getFirstRowNum();
	}

	/**
	 * 取最大的行数
	 * 
	 */
	public int getMaxRowNum() {
		if (currentSheet.getPhysicalNumberOfRows() == 0) {
			return -1;
		}
		return currentSheet.getLastRowNum();
	}

	/**
	 * 取最小的列数
	 * 
	 */
	public int getMinCellNum() {
		if (currentSheet.getPhysicalNumberOfRows() == 0) {
			return -1;
		}
		int firstRowNum = currentSheet.getFirstRowNum();
		int lastRowNum = currentSheet.getLastRowNum();
		int minCellNum = currentSheet.getRow(firstRowNum).getFirstCellNum();

		for (int i = firstRowNum + 1; i <= lastRowNum; i++) {
			XSSFRow row = currentSheet.getRow(i);
			if (row != null) {
				if (row.getFirstCellNum() < minCellNum) {
					minCellNum = row.getFirstCellNum();
				}
			}
		}

		return minCellNum;
	}

	/**
	 * 取sheet名
	 * 
	 */
	public List<String> getSheetNames() {
		List<String> sheetNames = new ArrayList<String>();
		for (int i = 0; i < getSheetSize(); i++) {
			sheetNames.add(workBook.getSheetName(i));
		}
		return sheetNames;
	}

	/**
	 */
	public XSSFWorkbook getWorkBook() {
		return workBook;
	}

	/**
	 */
	public XSSFSheet getCurrentSheet() {
		return currentSheet;
	}
}
