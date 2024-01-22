package helper;

import model.Student;
import model.Faculty;
import model.FYPCoordinator;
import model.Project;
import model.Request;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import enums.ExcelType;

/**
 * A helper class that can read in and store details about the model classes 
 * {@link Student}, {@link Faculty}, {@link FYPCoordinator}, {@link Project} 
 * and {@link Request} from data files. 
 * It can also write the updated information from these classes into the data files.
 * 
 * @author Andrew Cheam
 * @version 1.0
 * @since 2023-04-13
 */

public class Excel {	
	/** Map of header names with corresponding index in the Student data file. */
	private static HashMap<String, Integer> studentHeader = new HashMap<String, Integer>();
	/** Map of header names with corresponding index in the Faculty data file. */
	private static HashMap<String, Integer> facultyHeader = new HashMap<String, Integer>();
	/** Map of header names with corresponding index in the FYP Coordinator data file. */
	private static HashMap<String, Integer> FYPCoordHeader = new HashMap<String, Integer>();
	/** Map of header names with corresponding index in the Project data file. */
	private static HashMap<String, Integer> projectHeader = new HashMap<String, Integer>();
	/** Map of header names with corresponding index in the Request data file. */
	private static HashMap<String, Integer> requestHeader = new HashMap<String, Integer>();
	/** The folder in which the data files are found. */
	private static String filePath = "Library/Excel_Sheets/";
	/** Creates a new Excel object. */
	public Excel () {}
	/**
	 * Gets the name of the data file according to the type of object stored in the data file. 
	 * This is used to access the data file for reading and writing information.
	 * @param excelType The type of object stored in the data file.
	 * @return The name of the data file.
	 */
	public static String getfileName(ExcelType excelType) {
		switch (excelType) {
		case STUDENT:
			return "student_list.xlsx";
		case FACULTY:
			return "faculty_list.xlsx";
		case FYPCOOR:
			return "fypCoor_list.xlsx";
		case PROJECT:
			return "project_list.xlsx";
		case REQUEST:
			return "request_List.xlsx";
		default:
			return " ";
				
		}
	}
	
	/**
	 * Changes the header names of the given data file.
	 * @param excelType The type of data file.
	 * @param headerList The map of new header names with corresponding index.
	 */
	public static void updateHeader(ExcelType excelType, HashMap<String, Integer> headerList) {
		switch (excelType) {
		case STUDENT:
			studentHeader = headerList;
			break;
		case FACULTY:
			facultyHeader = headerList;
			break;
		case FYPCOOR:
			FYPCoordHeader = headerList;
		case PROJECT:
			projectHeader = headerList;
			break;
		case REQUEST:
			requestHeader = headerList;
			break;	
		}
	}
	
	/**
	 * Reads the header names of the given data file.
	 * @param excelType The type of data file.
	 * @return The map of header names with corresponding index from the data file.
	 */
	public static HashMap<String, Integer> readHeader(ExcelType excelType) {
		switch (excelType) {
		case STUDENT:
			return studentHeader;
		case FACULTY:
			return facultyHeader;
		case FYPCOOR:
			return FYPCoordHeader;
		case PROJECT:
			return projectHeader;
		case REQUEST:
			return requestHeader;
		default:
			return new HashMap<String, Integer>();
		}
	}
	
	/**
	 * Reads a given data file, stores the header names into a map and reads in the information 
	 * from every cell.
	 * @param excelType The type of data file.
	 * @return The list of information stored in the cells of the data file.
	 */
	public static ArrayList<HashMap<String, Object>> readExcel(ExcelType excelType) {
	    ArrayList<HashMap<String, Object>> objectList = new ArrayList<HashMap<String, Object>>();
		String filename  = filePath + getfileName(excelType);
	    
	    try {
	        // Load the Excel file
	        FileInputStream inputStream = new FileInputStream(filename);
	        Workbook workbook = new XSSFWorkbook(inputStream);

	        // Get the sheet you want to read from
	        Sheet sheet = workbook.getSheetAt(0); // 0 is the index of the first sheet
	        
	        //get a map of the header names and corresponding index
	        HashMap<String, Integer> headerList = new HashMap<String, Integer>();

        	Row headerRow = sheet.getRow(0);
        	for (int k = 0; k < headerRow.getLastCellNum(); k++) 
        	{
        	    Cell cell = headerRow.getCell(k);
        	    headerList.put(cell.getStringCellValue(), k);   
            }
        	updateHeader(excelType, headerList);
	        // Get the number of rows in the sheet
	        int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
	        //FOR EACH STUDENT (WHICH CORRESPONDS TO A SINGLE ROW)
	        for (int i = 1; i < rowCount + 1; i++) 
	        {
	        	Row row = sheet.getRow(i); 
	        	if (row == null) break;
	        	HashMap<String, Object> objectHashMap = new HashMap<String, Object>();
	        	//get the headers for the objectHashMap, each hashmap corresponds to one object instance
		        for (String headerName : headerList.keySet())
		        {
	            	Cell cell = row.getCell(headerList.get(headerName));
	            	if (cell == null) continue;
	            	switch (cell.getCellTypeEnum()) 
	            	{
	                case STRING:
	                	objectHashMap.put(headerName, (Object) cell.getStringCellValue());
	                    break;
	                case NUMERIC:	                
	                	objectHashMap.put(headerName, (Object) cell.getNumericCellValue());
	                    break;
	                case BOOLEAN:
	                	objectHashMap.put(headerName, (Object) cell.getBooleanCellValue());
	                    break;
	                default:
	                    break;
	            	}
	            }
	            objectList.add(objectHashMap);
	        }
	        // Close the workbook and input stream
	        workbook.close();
	        inputStream.close();

	    } 
	    catch (IOException e) 
	    {
	        e.printStackTrace();
	    }
	    return objectList;
	}

	/**
	 * Writes the information in the given list into a given data file.
	 * @param arrayList The list containing the information to be written into the data file.
	 * @param excelType The type of data file.
	 */
	public static void writeFile(ArrayList<HashMap<String, Object>> arrayList, ExcelType excelType)
	{
		String fileName  = getfileName(excelType); 	
		XSSFWorkbook workbook = new XSSFWorkbook();
		  
	        // spreadsheet object
	        XSSFSheet spreadsheet
	            = workbook.createSheet(fileName);
	        
	        
	        HashMap<String, Integer> headerList  = new HashMap<String, Integer>();
	        headerList = readHeader(excelType);
	        int rowCount = arrayList.size();
	        Row headerRow = spreadsheet.createRow(0);
	        //creates header for the excel sheet
	        for (String header : headerList.keySet())
        	{
        		Cell cell = headerRow.createCell(headerList.get(header));
        		cell.setCellValue((String) header);
        	}
	        //creates the content for the excel sheet
	        for (int i = 1; i < rowCount + 1; i++) 
	        {
	        	HashMap<String, Object> objectHashMap = arrayList.get(i-1);
	        	Row row = spreadsheet.createRow(i);
	        	for (String header : headerList.keySet())
	        	{
	        		if (!objectHashMap.containsKey(header)) continue; //check if hashmap contains that key
	        		Cell cell = row.createCell(headerList.get(header));
	        		if (objectHashMap.get(header) instanceof String)
	        		{
	        			cell.setCellValue((String) objectHashMap.get(header));
	        		}
	        		else if (objectHashMap.get(header) instanceof Number)
	        		{
	        			cell.setCellValue(((Number) objectHashMap.get(header)).doubleValue());
	        		}
	        		else if (objectHashMap.get(header) instanceof Boolean)
	        		{
	        			cell.setCellValue((Boolean) objectHashMap.get(header));
	        		}
	        	}
	        }
	        try {
	        	FileOutputStream outputStream = new FileOutputStream(filePath + fileName);
	            workbook.write(outputStream);
	            workbook.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}	
}

	
	
	
	
	
	

