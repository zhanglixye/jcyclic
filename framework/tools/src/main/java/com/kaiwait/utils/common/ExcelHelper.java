/**
 *
 */
package com.kaiwait.utils.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.primefaces.model.DefaultStreamedContent;


/**
 * @author PFM Wanght
 *
 */
public class ExcelHelper {
	/** Logger */
	private static final Logger LOGGER = Logger.getLogger(ExcelHelper.class.getName());
	private static final SimpleDateFormat DATE_FORMAT_YYYY_MM_DD = new SimpleDateFormat("yyyy/MM/dd");
	private static final SimpleDateFormat DATE_FORMAT_Y_M_D = new SimpleDateFormat("yyyy年MM月dd日");
	private XSSFWorkbook workBook;
	@SuppressWarnings("unused")
	private int sheetAt;
	@SuppressWarnings("unused")
	private int startRow;
	private int lastRow;
	private int currentRow;
	private XSSFRow row;
	private XSSFSheet sheet;
	@SuppressWarnings("unused")
	private FormInfo formInfo;
	


	/**
	 *
	 * @throws IOException
	 * @throws InvalidFormatException
	 */
	public ExcelHelper(FormInfo formInfo, int sheetAt, int startRow) {
	    this.formInfo = formInfo;
	    String templateFilePath = formInfo.getFilePath() + formInfo.getFileName();
		try {
			workBook = new XSSFWorkbook(new FileInputStream(templateFilePath));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		this.startRow = startRow-1;
		this.currentRow = startRow-1;
		this.sheetAt = sheetAt;
		this.sheet = workBook.getSheetAt(sheetAt);
		this.lastRow = sheet.getLastRowNum();
		this.row = sheet.getRow(startRow);

	}

	public XSSFCellStyle makeStyle(XSSFCell cloneStyleFrom, Short fontHeightInPoints, String fontName, Boolean bold, Short fontColor) {
		XSSFCellStyle cellStyle = workBook.createCellStyle();

		if (cloneStyleFrom != null) {
			final XSSFCellStyle cellStyleFrom = cloneStyleFrom.getCellStyle();
			cellStyle.cloneStyleFrom(cellStyleFrom);
		}
		if (fontHeightInPoints != null) {
			cellStyle.getFont().setFontHeightInPoints(fontHeightInPoints);
		}
		if (fontName != null) {
			cellStyle.getFont().setFontName(fontName);
		}
		if (bold != null) {
			cellStyle.getFont().setBold(bold);
		}
		if (fontColor != null) {
			cellStyle.getFont().setColor(fontColor);
		}
		return cellStyle;
	}
	
	public void setStyle(XSSFCell cell, Short fontHeightInPoints, String fontName, Boolean bold, Short fontColor) {
		XSSFCellStyle cellStyle = workBook.createCellStyle();

		if (cell != null) {
			final XSSFCellStyle cellStyleFrom = cell.getCellStyle();
			cellStyle.cloneStyleFrom(cellStyleFrom);
		}
		final XSSFFont font = workBook.createFont();
		if (fontHeightInPoints != null) {
			font.setFontHeightInPoints(fontHeightInPoints);
		}
		if (fontName != null) {
			font.setFontName(fontName);
		}
		if (bold != null) {
			font.setBold(bold);
		}
		if (fontColor != null) {
			font.setColor(fontColor);
		}
		cellStyle.setFont(font);
		cell.setCellStyle(cellStyle);
	}

	public void setPrintArea(int sheetIndex, String reference) {
		this.workBook.setPrintArea(sheetIndex, reference);
	}
	/**
	 * @return the currentRow
	 */
	public int getCurrentRow() {
		return currentRow;
	}



//	public DefaultStreamedContent getStreamedContent(String tempFilePath) throws FileNotFoundException, IOException {
//	    SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
//        String nowdatestr = df.format(System.currentTimeMillis());
//        String fileName = formInfo.getFileName();
//        String extName = "";
//        if (!StringUtil.isEmpty(fileName)) {
//            int lastIndexOf = fileName.lastIndexOf(".");
//            if (lastIndexOf >= 0 && lastIndexOf+1 < fileName.length()) {
//                extName = "." + fileName.substring(lastIndexOf+1);
//            }
//        }
//        String outputFileName = ExcelHelper.toUtf8String(formInfo.getOutputFileName()) + "_" + nowdatestr + extName;
//        
//	    String outputFile = tempFilePath + outputFileName;
//		writeWorkbook(outputFile);
//		InputStream stream = new BufferedInputStream(new FileInputStream(outputFile));
//        return new DefaultStreamedContent(stream, "application/vnd.ms-excel", outputFileName);
//	}
	
	public boolean hasNext() {
		return currentRow < lastRow;
	}

	public void moveNext() {
		if (hasNext()) {
			currentRow++;
			this.row = sheet.getRow(currentRow);
		}
	}

	public void moveNextOrCreateNew() {
		if (hasNext()) {
			currentRow++;
			this.row = sheet.getRow(currentRow);
		}
		else {
			currentRow++;
			this.row = sheet.createRow(currentRow);
		}
	}

	public void moveNextOrCopyPrevious(String verticalMergeColIndex, CellRangeAddress... mergeRange ) {
		if (hasNext()) {
			currentRow++;
			this.row = sheet.getRow(currentRow);
		}
		else {
			XSSFRow fromRow = sheet.getRow(currentRow);
			currentRow++;
			this.row = sheet.createRow(currentRow);
			copyRow(fromRow, this.row, verticalMergeColIndex, mergeRange);
		}
	}

	public String getString(int columnNo) {
		XSSFCell cell = row.getCell(columnNo);
		if (cell != null && cell.getCellType() != XSSFCell.CELL_TYPE_BLANK) {
			cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			return cell.getStringCellValue();
		}
		else {
			return null;
		}
	}
	
	public String getStringNvl(int columnNo) {
		return getStringNvl(columnNo, "");
	}
	public String getStringNvl(int columnNo, String nvl) {
		String value = getString(columnNo);
		if (value == null) {
			return nvl;
		} else {
			return value;
		}
	}

	public String getYyyyMdString(int columnNo) {
		XSSFCell cell = row.getCell(columnNo);
		if (cell != null && cell.getCellType() != XSSFCell.CELL_TYPE_BLANK) {
			Date date = cell.getDateCellValue();
			if (date != null) {
				return DATE_FORMAT_YYYY_MM_DD.format(date);
			}
			else {
				return null;
			}
		}
		else {
			return null;
		}
	}

	public Integer getInteger(int columnNo) {
		XSSFCell cell = row.getCell(columnNo);
		if (cell != null && cell.getCellType() != XSSFCell.CELL_TYPE_BLANK) {
			String result = null;
			cell.setCellType(Cell.CELL_TYPE_STRING);
			result = cell.getStringCellValue();
			return Integer.valueOf(result);
		}
		else {
			return null;
		}
	}

	public String readCellAsString(int rowNo, int columnNo) {
		String result = null;
		final XSSFRow getRow = this.sheet.getRow(rowNo);
		if (getRow != null) {
			XSSFCell cell = getRow.getCell(columnNo);
			if (cell != null && cell.getCellType() != XSSFCell.CELL_TYPE_BLANK) {
				cell.setCellType(Cell.CELL_TYPE_STRING);
				result = cell.getStringCellValue();
			}
		}
		return result;
	}

	public String getYyyyMdString(int rowNo, int columnNo) {

		XSSFCell cell = sheet.getRow(rowNo).getCell(columnNo);
		if (cell != null && cell.getCellType() != XSSFCell.CELL_TYPE_BLANK) {
			Date date = cell.getDateCellValue();
			if (date != null) {
				return DATE_FORMAT_YYYY_MM_DD.format(date);
			}
			else {
				return null;
			}
		}
		else {
			return null;
		}
	}

	public Date getYyyyMdDate(int rowNo, int columnNo) {
		final String yyyyMdString = getYyyyMdString(rowNo, columnNo);
		if (StringUtil.isEmpty(yyyyMdString)) {
			return null;
		}
		else {
			try {
				return DATE_FORMAT_YYYY_MM_DD.parse(yyyyMdString);
			}
			catch (ParseException e) {
				return null;
			}
		}
	}

	public Date getYyyyMdDate(int columnNo) {
		final String yyyyMdString = getYyyyMdString(columnNo);
		if (StringUtil.isEmpty(yyyyMdString)) {
			return null;
		}
		else {
			try {
				return DATE_FORMAT_YYYY_MM_DD.parse(yyyyMdString);
			}
			catch (ParseException e) {
				return null;
			}
		}
	}

	public XSSFCell setString(int columnNo, String value) {
		XSSFCell cell = row.getCell(columnNo);
		if (cell == null) {
			cell = row.createCell(columnNo);
		}
		cell.setCellType(XSSFCell.CELL_TYPE_STRING);
		if (value != null) {
			cell.setCellValue(StringUtil.ifNull(value, ""));
		}
		return cell;
	}

	public XSSFCell setString(int rowNo, int columnNo, String value) {
		XSSFRow getRow = sheet.getRow(rowNo);
		if (getRow == null) {
			getRow = sheet.createRow(rowNo);
		}
		XSSFCell cell = getRow.getCell(columnNo);
		if (cell == null) {
			cell = getRow.createCell(columnNo);
		}
		cell.setCellType(XSSFCell.CELL_TYPE_STRING);
		if (value != null) {
			cell.setCellValue(StringUtil.ifNull(value, ""));
		}
		return cell;
	}

	public XSSFCell setDate(int rowNo, int columnNo, Date value) {
		final String formatedValue = DATE_FORMAT_YYYY_MM_DD.format(value);
		return setString(rowNo, columnNo, formatedValue);
	}
	public XSSFCell setDateYMD(int rowNo, int columnNo, Date value) {
		final String formatedValue = DATE_FORMAT_Y_M_D.format(value);
		return setString(rowNo, columnNo, formatedValue);
	}

	private void copyRow(XSSFRow fromRow, XSSFRow toRow, String verticalMergeColIndex, CellRangeAddress... mergeRange  ) {
        XSSFSheet sheet = fromRow.getSheet();
        int rowNum = toRow.getRowNum();
        toRow.setHeight(fromRow.getHeight());
        Iterator<Cell> cellIterator = fromRow.cellIterator();
        while (cellIterator.hasNext()) {
            XSSFCell tmpCell = (XSSFCell) cellIterator.next();
            int columnIndex = tmpCell.getColumnIndex();
            XSSFCell newCell = toRow.createCell(columnIndex);
            if (verticalMergeColIndex != null && verticalMergeColIndex.indexOf(String.valueOf(columnIndex)) >= 0) {
                sheet.addMergedRegion(new CellRangeAddress(rowNum-1, rowNum, columnIndex, columnIndex));
            }
            newCell.setCellStyle(tmpCell.getCellStyle());
        }
        if (mergeRange != null) {
            for (CellRangeAddress rangeAddress : mergeRange) {
                sheet.addMergedRegion(rangeAddress);
            }
        }
    }

	@SuppressWarnings("unused")
	private void writeWorkbook(String outputFilePath) throws FileNotFoundException, IOException {

		// check parameter.
		if (StringUtil.isEmpty(outputFilePath))
			throw new IllegalArgumentException("outputFilePath is empty.");
		File outputFile = new File(outputFilePath);

		if (outputFile.exists())
			LOGGER.warning("outputFilePath[" + outputFilePath + "] already exists." + "Overwrite this.");

		// if parent directory of outputFilePath not exists,
		// create it recursive.
		File parentDir = outputFile.getParentFile();
		if (!parentDir.exists()) {
			try {
				if (!parentDir.mkdirs()) {
					String msg = "Try to create parent directory[" + parentDir.getAbsolutePath() + "] for outputFilePath, " + "but occured something error.";
					LOGGER.severe(msg);
					throw new IllegalStateException(msg);
				}
				LOGGER.fine("Created directory [" + parentDir.getAbsolutePath() + "].");
			}
			catch (SecurityException e) {
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
				throw new IllegalStateException(e.getMessage(), e);
			}
		}

		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(new File(outputFilePath));
			workBook.write(fos);
		}
		catch (FileNotFoundException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			throw e;
		}
		catch (IOException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			throw e;
		}
		finally {
			try {
				if (fos != null)
					fos.close();
			}
			catch (IOException e) {
				LOGGER.log(Level.WARNING, e.getMessage(), e);
			}
		}
	}
	
	
	 public int getLastRow() {
		return lastRow;
	}

	 
	public XSSFSheet getSheet() {
		return sheet;
	}


	public static class FormInfo {
		 	public FormInfo() {
		 		
		 	}
		 	public FormInfo(String filePath, String fileName) {
		 		this.fileName = fileName;
		 		this.filePath = filePath;
		 	}
			public String getFormNo() {
				return formNo;
			}

			public void setFormNo(String formNo) {
				this.formNo = formNo;
			}

			public String getFormName() {
				return formName;
			}

			public void setFormName(String formName) {
				this.formName = formName;
			}

			public String getFormKbn() {
				return formKbn;
			}

			public void setFormKbn(String formKbn) {
				this.formKbn = formKbn;
			}

			public String getFilePath() {
				return filePath;
			}

			public void setFilePath(String filePath) {
				this.filePath = filePath;
			}

			public String getFileName() {
				return fileName;
			}

			public void setFileName(String fileName) {
				this.fileName = fileName;
			}

			public String getOutputFileName() {
				return outputFileName;
			}

			public void setOutputFileName(String outputFileName) {
				this.outputFileName = outputFileName;
			}

			private String formNo;

			private String formName;

			private String formKbn;

			private String filePath;

			private String fileName;

			private String outputFileName;

		}
}



