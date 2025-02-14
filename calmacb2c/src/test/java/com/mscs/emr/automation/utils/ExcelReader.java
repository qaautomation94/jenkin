package com.mscs.emr.automation.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Calendar;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {

	public String path;
	public FileInputStream fis = null;
	public FileOutputStream fileOut = null;
	private XSSFWorkbook workbook = null;
	private XSSFSheet sheet = null;
	private XSSFRow row = null;
	private XSSFCell cell = null;
	boolean autoval = false;

	public ExcelReader(String path) {

		this.path = path;
		try {
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);
			sheet = workbook.getSheetAt(0);
			fis.close();

			workbook = new XSSFWorkbook(new FileInputStream(path));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* Returns the row count in a sheet */
	public int getRowCount(String sheetName) {
		int index = workbook.getSheetIndex(sheetName);
		if (index == -1)
			return 0;
		else {
			sheet = workbook.getSheetAt(index);
			int number = sheet.getLastRowNum() + 1;
			return number;
		}
	}

	/* Returns the data from a cell */
	public String getCellData(String sheetName, String colName, int rowNum) {
		try {

			/*
			 * fis = new FileInputStream(path); workbook = new XSSFWorkbook(fis);
			 */

			if (rowNum <= 0)
				return "";

			int index = workbook.getSheetIndex(sheetName);
			int col_Num = -1;
			if (index == -1)
				return "";

			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(0);
			for (int i = 0; i < row.getLastCellNum(); i++) {
				if (row.getCell(i).getStringCellValue().trim().equals(colName.trim()))
					if (row.getCell(i).getStringCellValue().trim().equalsIgnoreCase(colName.trim()))
						col_Num = i;
			}
			if (col_Num == -1)
				return "";

			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(rowNum - 1);
			if (row == null)
				return "";
			cell = row.getCell(col_Num);

			if (cell == null)
				return "";
			System.out.println(cell.getCellType());
			if (cell.getCellType() == Cell.CELL_TYPE_STRING)
				return cell.getStringCellValue();
			else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {

				String cellText = String.valueOf(cell.getNumericCellValue());
				if (DateUtil.isCellDateFormatted(cell)) {
					double d = cell.getNumericCellValue();

					Calendar cal = Calendar.getInstance();
					cal.setTime(DateUtil.getJavaDate(d));
					cellText = (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
					cellText = cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.MONTH) + 1 + "/" + cellText;
					System.out.println(cellText);
				}
				workbook.close();

				return cellText;
			} else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
				System.out.println("CellType:" + cell.getCellType());

				/*
				 * FormulaEvaluator evaluator =
				 * workbook.getCreationHelper().createFormulaEvaluator();
				 * 
				 * int iCellValue = evaluator.evaluateFormulaCell(cell);
				 * System.out.println(iCellValue); String cellText = String.valueOf(iCellValue);
				 */
				String cellText = "";
				System.out.println("Formula is " + cell.getCellFormula());
				switch (cell.getCachedFormulaResultType()) {
				case Cell.CELL_TYPE_NUMERIC:
					System.out.println("Last evaluated as: " + cell.getNumericCellValue());
					cellText = String.valueOf(cell.getNumericCellValue());
					break;
				case Cell.CELL_TYPE_STRING:
					FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
					evaluator.evaluateFormulaCell(cell);
					System.out.println("Last evaluated as \"" + cell.getRichStringCellValue() + "\"");
					cellText = cell.getRichStringCellValue().toString();
					break;
				}

				return cellText;
			} else if (cell.getCellType() == Cell.CELL_TYPE_BLANK)
				return "";
			else
				return String.valueOf(cell.getBooleanCellValue());

		} catch (Exception e) {

			e.printStackTrace();
			return "row " + rowNum + " or column " + colName + " does not exist in xls";
		}
	}

	/* Returns the data from a cell for xls report */
	public String getCellData(String sheetName, int colNum, int rowNum) {
		try {
			if (rowNum <= 0)
				return "";

			int index = workbook.getSheetIndex(sheetName);

			if (index == -1)
				return "";

			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(rowNum - 1);
			if (row == null)
				return "";
			cell = row.getCell(colNum);
			if (cell == null)
				return "";

			if (cell.getCellType() == Cell.CELL_TYPE_STRING)
				return cell.getStringCellValue();
			else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {

				String cellText = String.valueOf(cell.getNumericCellValue());
				if (DateUtil.isCellDateFormatted(cell)) {
					double d = cell.getNumericCellValue();

					Calendar cal = Calendar.getInstance();
					cal.setTime(DateUtil.getJavaDate(d));
					cellText = (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
					cellText = cal.get(Calendar.MONTH) + 1 + "/" + cal.get(Calendar.DAY_OF_MONTH) + "/" + cellText;

					System.out.println(cellText);
				}
				return cellText;
			} else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
				System.out.println("CellType:" + cell.getCellType());

				/*
				 * FormulaEvaluator evaluator =
				 * workbook.getCreationHelper().createFormulaEvaluator();
				 * 
				 * int iCellValue = evaluator.evaluateFormulaCell(cell);
				 * System.out.println(iCellValue); String cellText = String.valueOf(iCellValue);
				 */
				String cellText = "";
				System.out.println("Formula is " + cell.getCellFormula());
				switch (cell.getCachedFormulaResultType()) {
				case Cell.CELL_TYPE_NUMERIC:
					System.out.println("Last evaluated as: " + cell.getNumericCellValue());
					cellText = String.valueOf(cell.getNumericCellValue());
					break;
				case Cell.CELL_TYPE_STRING:
					FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
					evaluator.evaluateFormulaCell(cell);
					System.out.println("Last evaluated as \"" + cell.getRichStringCellValue() + "\"");
					cellText = cell.getRichStringCellValue().toString();
					break;
				}

				return cellText;
			}

			else if (cell.getCellType() == Cell.CELL_TYPE_BLANK)
				return "";
			else
				return String.valueOf(cell.getBooleanCellValue());
		} catch (Exception e) {

			e.printStackTrace();
			return "row " + rowNum + " or column " + colNum + " does not exist  in xls";
		}
	}

	/* Returns true if data is set successfully else false */
	public boolean setCellData(String sheetName, String colName, int rowNum, String data) {
		try {
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);

			if (rowNum <= 0)
				return false;

			int index = workbook.getSheetIndex(sheetName);
			int colNum = -1;
			if (index == -1)
				return false;

			sheet = workbook.getSheetAt(index);

			row = sheet.getRow(0);
			for (int i = 0; i < row.getLastCellNum(); i++) {
				System.out.println(row.getCell(i).getStringCellValue().trim());
				if (row.getCell(i).getStringCellValue().trim().equals(colName))
					colNum = i;
			}
			if (colNum == -1)
				return false;
			if (autoval == true) {
				sheet.autoSizeColumn(colNum);
				autoval = false;
			}
			row = sheet.getRow(rowNum-1);
			if (row == null)
				row = sheet.createRow(rowNum);

			cell = row.getCell(colNum);
			if (cell == null)
				cell = row.createCell(colNum);

			CellStyle cs = workbook.createCellStyle();
			cs.setWrapText(true);
			cell.setCellStyle(cs);
			cell.setCellValue(data);

			fileOut = new FileOutputStream(path);

			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();

			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/* Returns true if column is created successfully */
	public boolean addColumn(String sheetName, String colName) {
		System.out.println("**************addColumn*********************");

		try {
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);
			int index = workbook.getSheetIndex(sheetName);
			if (index == -1)
				return false;

			XSSFCellStyle style = workbook.createCellStyle();
			style.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);

			// TODO
			// style.setFillPattern(CellStyle.SOLID_FOREGROUND);

			sheet = workbook.getSheetAt(index);

			row = sheet.getRow(0);
			if (row == null)
				row = sheet.createRow(0);
			cell = row.getCell(0);
			if (cell == null)
				System.out.println(row.getLastCellNum());
			if (row.getLastCellNum() == -1)
				cell = row.createCell(0);
			else
				cell = row.createCell(row.getLastCellNum());

			cell.setCellValue(colName);
			cell.setCellStyle(style);

			fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
			fileOut.close();

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;

	}

	/* Find whether sheets exists */
	public boolean isSheetExist(String sheetName) {
		int index = workbook.getSheetIndex(sheetName);
		if (index == -1) {
			index = workbook.getSheetIndex(sheetName.toUpperCase());
			if (index == -1)
				return false;
			else
				return true;
		} else
			return true;
	}

	/* Returns number of columns in a sheet */
	public int getColumnCount(String sheetName) {
		// check if sheet exists
		if (!isSheetExist(sheetName))
			return -1;

		sheet = workbook.getSheet(sheetName);
		row = sheet.getRow(0);

		if (row == null)
			return -1;

		return row.getLastCellNum();

	}

	public boolean clearCellData(String sheetName, String colName, String data) {
		try {
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);
			int index = workbook.getSheetIndex(sheetName);
			int colNum = -1;
			if (index == -1)
				return false;

			sheet = workbook.getSheetAt(index);

			row = sheet.getRow(0);

			for (int i = 0; i < row.getLastCellNum(); i++) {
				System.out.println(row.getCell(i).getStringCellValue().trim());
				if (row.getCell(i).getStringCellValue().trim().equalsIgnoreCase(colName)) {
					colNum = i;
					break;
				}
			}
			if (colNum == -1)
				return false;

			sheet.autoSizeColumn(colNum);

			for (int l = 1; l <= sheet.getLastRowNum(); l++) {
				row = sheet.getRow(l);
				if (row == null) {
					row = sheet.createRow(sheet.getLastRowNum() - 1);
				}
				cell = row.getCell(colNum);
				if (cell != null)

					cell.setCellValue(data);
			}

			fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
			fileOut.close();

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}

}