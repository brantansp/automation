package environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

public class ExcelQuery {
	
	static Fillo fillo=new Fillo();
	static Connection connection;
	
	/**
	 * 
	 * Select Query from Excel
	 * Usage: | selectQuery | filePath| query| column|
	 * @param filePath
	 * @return 
	 * @return
	 * @throws FilloException 
	 */
	public static ArrayList<String> selectQuery(String filePath,String query, String column) throws FilloException {
		
		
		connection = fillo.getConnection(filePath);	
		
		Recordset recordset=connection.executeQuery(query);
		
		ArrayList <String> result = new ArrayList<String>();

		while(recordset.next()){
			result.add(recordset.getField(column));
		 
		}
		
		connection.close();

		
		return result;
		
		
	}
	
	
	/**
	 * Get Total Count of rows from Excel
	 * Usage: | getCountQuery | filePath| query|
	 * @param filePath
	 * @param query
	 * @return
	 * @throws FilloException
	 */
	
	public static int getCountQuery(String filePath,String query) throws FilloException {
		
       connection = fillo.getConnection(filePath);	
       int count = 0;
		
		Recordset recordset=connection.executeQuery(query);
		count = recordset.getCount();
		
		connection.close();

		
		return count;

	}
	
	/**
	 * Insert or Update Query to Excel
	 * Usage: | insertOrUpdateQuery | filePath| query|
	 * @param filePath
	 * @return 
	 * @return
	 * @throws FilloException 
	 */
	public static void insertOrUpdateQuery(String filePath, String query) throws FilloException {
        connection = fillo.getConnection(filePath);
        connection.executeUpdate(query);
        connection.close();
	}
	
	/**
	 * To read the Excel file as Json Object
	 * Usage: | readExcelFileAsJsonObject | filePath|
	 * @param filePath
	 * @return
	 * @throws IOException 
	 */
	public static JSONObject readExcelFileAsJsonObject(String filePath) throws IOException {
		DataFormatter dataFormatter = new DataFormatter();
		JSONObject workbookJson = new JSONObject();
		JSONArray sheetJson = new JSONArray();
		JSONObject rowJson = new JSONObject();
		FileInputStream excelFile = null;
		try {

			excelFile = new FileInputStream(new File(filePath));
			Workbook workbook = new XSSFWorkbook(excelFile);
			FormulaEvaluator formulaEvaluator = new XSSFFormulaEvaluator((XSSFWorkbook) workbook);

			for (Sheet sheet : workbook) {
				sheetJson = new JSONArray();
				int lastRowNum = sheet.getLastRowNum();
				int lastColumnNum = sheet.getRow(0).getLastCellNum();
				Row firstRowAsKeys = sheet.getRow(0);

				for (int i = 1; i <= lastRowNum; i++) {
					rowJson = new JSONObject();
					Row row = sheet.getRow(i);

					if (row != null) {
						for (int j = 0; j < lastColumnNum; j++) {
							formulaEvaluator.evaluate(row.getCell(j));
							String key = "";
							String value = "";
							try {
								key = firstRowAsKeys.getCell(j).getStringCellValue();
							} catch (Exception e) {
								key = "";
							}

							try {
								value = dataFormatter.formatCellValue(row.getCell(j), formulaEvaluator);
							} catch (Exception e) {
								value = "";
							}

							rowJson.put(key, value);
						}
						sheetJson.put(rowJson);
					}
				}
				workbookJson.put(sheet.getSheetName(), sheetJson);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			excelFile.close();
		}
		return workbookJson;
	}
	
    public static void main(String arg[]) throws IOException {
    	//insertOrUpdateQuery("C:\\Users\\Downloads\\optima.xlsx", "INSERT into Sheet1 (\"Customer/Ship To Ref Id\",\"Sold To Ref Id\",\"Category\",\"Sub Category\") VALUES ('brd','akd','ag','mt')");
        String filePath = "C:\\Users\\brantan.sp\\Downloads\\Invoice-2021-09-18T09_40_55.424Z.xlsx";
        JSONObject data = readExcelFileAsJsonObject(filePath);
        System.out.println(data);
    }
}