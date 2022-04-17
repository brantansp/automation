package environment;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class Excel {
	
	/**
	 * Create a New Excel sheet 
	 * Usage: | newSheet| headerRow| firstRow| Filename|
	 * @param headerRow
	 * @param firstRow
	 * @param Filename
	 * @throws IOException
	 */
	
	public static void newSheet(List headerRow, List firstRow,String Filename) throws IOException {
		
		// String FILE_NAME = "/tmp/MyFirstExcel.xlsx";
		 
		 XSSFWorkbook workbook = new XSSFWorkbook();
	     XSSFSheet sheet = workbook.createSheet("Sample");
	       
	       List<List> datatypes = new ArrayList<List>();
			datatypes.add(headerRow);
			datatypes.add(firstRow);
	        
	        int rowNum = 0;
	        
	        for (List datatype : datatypes) {
	            Row row = sheet.createRow(rowNum++);
	            int colNum = 0;
	            for (Object field : datatype) {
	                Cell cell = row.createCell(colNum++);
	                if (field instanceof String) {
	                    cell.setCellValue((String) field);
	                } else if (field instanceof Integer) {
	                    cell.setCellValue((Integer) field);
	                }
	            }
	        }
	        
	        
	        try {
	            FileOutputStream outputStream = new FileOutputStream(Filename);
	            workbook.write(outputStream);
	            workbook.close();
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		
	}

	/** Create a New Excel sheet with a header and n number of columns by add | pipe symbol
	 * Usage: | newSheetDynamicColumns| header| columns| Filename|
	 * @param header
	 * @param columns
	 * @param Filename
	 * @throws IOException
	 */
	@SuppressWarnings("deprecation")
	public static void newSheetDynamicColumns(String header, String columns,String Filename) throws IOException {

		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Sample");

		String[] headerVal = header.split("\\,");

		int rowNum = 0;
		int colNum = 0;

		Row row = sheet.createRow(rowNum++);

		for (String data : headerVal) {
			Cell cell = row.createCell(colNum++);
			cell.setCellValue((String) data);
		}

		String[] column = columns.split("\\|");
		for (String col : column) {
			int colNum1 =0;
			Row row1 = sheet.createRow(rowNum++);
			String[] colValue = col.split("(?<!\\\\),");
			//String[] colValue = col.split("\\,");
			for (String value : colValue) {
				value = value.replace("\\", "");
				Cell cell = row1.createCell(colNum1++);
				if (cell.getCellTypeEnum() == CellType.STRING) {
					cell.setCellValue((String) value);
				} else if (cell.getCellTypeEnum() == CellType.NUMERIC) {
					cell.setCellValue((String) value);
				} else {
					cell.setCellValue((String) value);
				}
			}
		}

		try {
			FileOutputStream outputStream = new FileOutputStream(Filename);
			workbook.write(outputStream);
			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * Create a New Excel sheet  with 1 header and 5 rows
	 * Usage: | multiNewSheet| headerRow|  firstRow| secondRow| thirdRow| fourthRow| fifthRow| Filename|
	 * @param headerRow
	 * @param firstRow
	 * @param secondRow
	 * @param thirdRow
	 * @param fourthRow
	 * @param fifthRow
	 * @param Filename
	 * @throws IOException
	 */
	
public static void multiNewSheet(List headerRow, List firstRow,List secondRow,List thirdRow,List fourthRow,List fifthRow,String Filename) throws IOException {
		
		// String FILE_NAME = "/tmp/MyFirstExcel.xlsx";
		 
		 XSSFWorkbook workbook = new XSSFWorkbook();
	     XSSFSheet sheet = workbook.createSheet("Sample");
	       
	       List<List> datatypes = new ArrayList<List>();
			datatypes.add(headerRow);
			datatypes.add(firstRow);
			datatypes.add(secondRow);
			datatypes.add(thirdRow);
			datatypes.add(fourthRow);
			datatypes.add(fifthRow);

	        
	        int rowNum = 0;
	        
	        for (List datatype : datatypes) {
	            Row row = sheet.createRow(rowNum++);
	            int colNum = 0;
	            for (Object field : datatype) {
	                Cell cell = row.createCell(colNum++);
	                if (field instanceof String) {
	                    cell.setCellValue((String) field);
	                } else if (field instanceof Integer) {
	                    cell.setCellValue((Integer) field);
	                }
	            }
	        }
	        
	        
	        try {
	            FileOutputStream outputStream = new FileOutputStream(Filename);
	            workbook.write(outputStream);
	            workbook.close();
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		
	}

	
	/**
	 * Create a New Excel sheet  with 1 header and 9 rows
	 * Usage: | multiLargeNewSheet| headerRow|  firstRow| secondRow| thirdRow| fourthRow| fifthRow| sixthRow| seventhRow| eigthRow| ninthRow| Filename
	 * @param headerRow
	 * @param firstRow
	 * @param secondRow
	 * @param thirdRow
	 * @param fourthRow
	 * @param fifthRow
	 * @param sixthRow
	 * @param seventhRow
	 * @param eigthRow
	 * @param ninthRow
	 * @param Filename
	 * @throws IOException
	 */
	public static void multiLargeNewSheet(List headerRow, List firstRow,List secondRow,List thirdRow,List fourthRow,List fifthRow,List sixthRow,List seventhRow,List eigthRow,List ninthRow,String Filename) throws IOException {
		
		// String FILE_NAME = "/tmp/MyFirstExcel.xlsx";
		 
		 XSSFWorkbook workbook = new XSSFWorkbook();
	     XSSFSheet sheet = workbook.createSheet("Sample");
	       
	       List<List> datatypes = new ArrayList<List>();
			datatypes.add(headerRow);
			datatypes.add(firstRow);
			datatypes.add(secondRow);
			datatypes.add(thirdRow);
			datatypes.add(fourthRow);
			datatypes.add(fifthRow);
			datatypes.add(sixthRow);
			datatypes.add(seventhRow);
			datatypes.add(eigthRow);
			datatypes.add(ninthRow);
	
	
	        
	        int rowNum = 0;
	        
	        for (List datatype : datatypes) {
	            Row row = sheet.createRow(rowNum++);
	            int colNum = 0;
	            for (Object field : datatype) {
	                Cell cell = row.createCell(colNum++);
	                if (field instanceof String) {
	                    cell.setCellValue((String) field);
	                } else if (field instanceof Integer) {
	                    cell.setCellValue((Integer) field);
	                }
	            }
	        }
	        
	        
	        try {
	            FileOutputStream outputStream = new FileOutputStream(Filename);
	            workbook.write(outputStream);
	            workbook.close();
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		
	}	

	
}
