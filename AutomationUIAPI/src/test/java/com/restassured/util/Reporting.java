package com.restassured.util;
// reads the xls files and generates corresponding html reports
// Calls sendmail - mail

import static com.restassured.util.App.CONFIG;

import java.io.*;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.pearson.util.Xls_Reader; 



public class Reporting {
public static String result_FolderName=null;
	public static void main(String[] arg) throws Exception {
		// read suite.xls
		Reporting Rep=new Reporting();
				Rep.GeneratehtmlReport();

	}

	 public void ReportExcelData(String XlFile, String XLSheetName, String TCID, String APIMethodType,String APIURL,String APIDescription,String Input_KeyValues,String Expected,String Status,String Description,String RespTime, String Output_Values,String Response)throws AbstractMethodError,  IOException
	 {
		 //String TCID, String APIMethodType,String APIURL,String APIDescription,String Input_KeyValues,String Expected,String Status,String Description,String Output_Values,String Response
		//TCID, APIMethodType,APIURL,APIDescription,Input_KeyValues,Expected,Status,Description,Output_Values,Response
		int NewRow=1;
		
		//Initialize test case sheer
		String XLFileNmae=XlFile;
		String SheetName=XLSheetName;
		
		//System.out.println(SheetName+" : "+TCID+" : "+TestScenario+" : "+Description+" : "+InputDetails+" : "+Expected+" : "+Status+" : "+Actuals+" : "+Comments);
		Xls_Reader current_TestCase_xls=null;
        
  	 	current_TestCase_xls=new Xls_Reader(XLFileNmae);
	  	boolean SheetExist=current_TestCase_xls.isSheetExist(SheetName);
	  	//System.out.println("SheetName Exist"+SheetExist);
	  	if (SheetExist)
	  	{
	  		int rowCount= current_TestCase_xls.getRowCount(SheetName);
			//int cols = current_TestCase_xls.getColumnCount(SheetName);
	  		NewRow=rowCount+1;
	  		current_TestCase_xls.setCellData(SheetName, "Issue key", NewRow, TCID);
	    	current_TestCase_xls.setCellData(SheetName, "APIMethodType", NewRow, APIMethodType);
	    	current_TestCase_xls.setCellData(SheetName, "APIURL", NewRow, APIURL);
	    	current_TestCase_xls.setCellData(SheetName, "APIDescription", NewRow, APIDescription);
	    	current_TestCase_xls.setCellData(SheetName, "Input_KeyValues", NewRow, Input_KeyValues);
	    	current_TestCase_xls.setCellData(SheetName, "Expected", NewRow, Expected);
	    	current_TestCase_xls.setCellData(SheetName, "TestResultStatus", NewRow, Status);
	    	current_TestCase_xls.setCellData(SheetName, "ResponseDescription", NewRow, Description);
	    	current_TestCase_xls.setCellData(SheetName, "ResponseTime", NewRow, RespTime);
	    	current_TestCase_xls.setCellData(SheetName, "Output_Values", NewRow, Output_Values);
	    	current_TestCase_xls.setCellData(SheetName, "Response", NewRow, Response);
	  	}
	  }
	
	 
	 public void ClearReportExcel(String XLFile,String XLSheetName)throws AbstractMethodError,  IOException
	 {
		
		//Initialize test case sheer
		String XLFileNmae=XLFile;//Constants.TEST_XLFILE;
		String SheetName=XLSheetName;
		
		
		Xls_Reader current_TestCase_xls=null;
  	 	current_TestCase_xls=new Xls_Reader(XLFile);
	  	boolean SheetExist=current_TestCase_xls.isSheetExist(SheetName);
		//System.out.println("SheetName "+XLSheetName+" exist :"+SheetExist);
	  	if (SheetExist)
	  	{
	  		//System.out.println("SheetName in : "+SheetName);
	  		int rowCount= current_TestCase_xls.getRowCount(SheetName);
	  		//System.out.println("RowCount : "+rowCount);
	  		if (rowCount>1)
	  		{
	  			current_TestCase_xls.removeAllRow(SheetName);
	  		}
	  		//System.out.println("End Row : ");
	  	}
	  	else
	  	{
	  		//System.out.println("SheetName : "+SheetName);
	  		current_TestCase_xls.addSheet(SheetName);
	  		current_TestCase_xls.addColumn(SheetName, "Issue key");
	  		current_TestCase_xls.addColumn(SheetName, "APIMethodType");
	  		current_TestCase_xls.addColumn(SheetName, "APIURL");
	  		current_TestCase_xls.addColumn(SheetName, "APIDescription");
	  		current_TestCase_xls.addColumn(SheetName, "Input_KeyValues");
	  		current_TestCase_xls.addColumn(SheetName, "Expected");
	  		current_TestCase_xls.addColumn(SheetName, "TestResultStatus");
	  		current_TestCase_xls.addColumn(SheetName, "ResponseDescription");
	  		current_TestCase_xls.addColumn(SheetName, "ResponseTime");
	  		current_TestCase_xls.addColumn(SheetName, "Output_Values");
	  		current_TestCase_xls.addColumn(SheetName, "Response");
	  		
	  		
	  	}
	  	//System.out.println("SheetName Out : "+SheetName);
  	 
	  }
	 
	 
	 public static void ConvertCSVToXLSX(String csvFile,String XlsxFile,String Sheetname) {
		    try {
		        String csvFileAddress = csvFile; //csv file address
		        String xlsxFileAddress = XlsxFile; //xlsx file address
		        XSSFWorkbook workBook = new XSSFWorkbook();
		        XSSFSheet sheet = workBook.createSheet(Sheetname);
		        String currentLine=null;
		        int RowNum=0;
		        BufferedReader br = new BufferedReader(new FileReader(csvFileAddress));
		        while ((currentLine = br.readLine()) != null) {
		            String str[] = currentLine.split(",");
		            XSSFRow currentRow=sheet.createRow(RowNum);
		            for(int i=0;i<str.length;i++){
		            	//System.out.println("Row : "+i+" Length of "+str.length);
		                currentRow.createCell(i).setCellValue(str[i]);
		            }
		            RowNum++;
		        }

		        FileOutputStream fileOutputStream =  new FileOutputStream(xlsxFileAddress);
		        
		        workBook.write(fileOutputStream);
		        fileOutputStream.close();
		        System.out.println("Converted to XLSX");
		    } catch (Exception ex) {
		        System.out.println(ex.getMessage()+"Exception in try");
		    }
		}
	 
	 
	 public static void convertXLSXtoCSV(String inputFilePath, String outputFilePath) 
	 {
	         // For storing data into CSV files
	 StringBuffer cellValue = new StringBuffer();
	 try 
	 {
		 File inputFile=new File(inputFilePath);
		 File outputFile=new File(outputFilePath);
	         FileOutputStream fos = new FileOutputStream(outputFile);
	         // Get the workbook instance for XLSX file
	         XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(inputFile));
	         // Get first sheet from the workbook
	         XSSFSheet sheet = wb.getSheetAt(0);

	         Row row;
	         Cell cell;

	         // Iterate through each rows from first sheet
	         Iterator<Row> rowIterator = sheet.iterator();

	         while (rowIterator.hasNext()) 
	         {
	         row = rowIterator.next();
	         //System.out.println(row);
	         // For each row, iterate through each columns
	         Iterator<Cell> cellIterator = row.cellIterator();
	         while (cellIterator.hasNext()) 
	         {
	                 cell = cellIterator.next();

	                 switch (cell.getCellType()) 
	                 {
	                 
	                 case Cell.CELL_TYPE_BOOLEAN:
	                         cellValue.append(cell.getBooleanCellValue() + ",");
	                         break;
	                 
	                 case Cell.CELL_TYPE_NUMERIC:
	                         cellValue.append(cell.getNumericCellValue() + ",");
	                         break;
	                 
	                 case Cell.CELL_TYPE_STRING:
	                 	//System.out.println("Before: "+cell.getStringCellValue());
	                 	String str=cell.getStringCellValue().replaceAll("\n", "");
	                 	str=str.replaceAll(",", " ");
	                 	//System.out.println("After : "+str);
	                         cellValue.append(str + ",");
	                         break;

	                 case Cell.CELL_TYPE_BLANK:
	                         cellValue.append("" + ",");
	                         break;
	                         
	                 default:
	                         cellValue.append(cell + ",");

	                 }
	                 
	         }
	         cellValue.append("\r\n");
	         }

	 fos.write(cellValue.toString().getBytes());
	 fos.close();
	 System.out.println("Converted to CSV");

	 } 
	 catch (Exception e) 
	 {
	         System.err.println("Exception :" + e.getMessage());
	 }
	 }
	 
	 public static void xlsxtocsv(File inputFile, File outputFile) 
	 {
	     // For storing data into CSV files
	     StringBuffer data = new StringBuffer();
	     try 
	     {
	     FileOutputStream fos = new FileOutputStream(outputFile);

	     // Get the workbook object for XLS file
	     HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(inputFile));
	     // Get first sheet from the workbook
	     HSSFSheet sheet = workbook.getSheetAt(0);
	     Cell cell;
	     Row row;

	     // Iterate through each rows from first sheet
	     Iterator<Row> rowIterator = sheet.iterator();
	     while (rowIterator.hasNext()) 
	     {
	             row = rowIterator.next();
	             // For each row, iterate through each columns
	             Iterator<Cell> cellIterator = row.cellIterator();
	             while (cellIterator.hasNext()) 
	             {
	                     cell = cellIterator.next();
	                     
	                     switch (cell.getCellType()) 
	                     {
	                     case Cell.CELL_TYPE_BOOLEAN:
	                             data.append(cell.getBooleanCellValue() + ",");
	                             break;
	                             
	                     case Cell.CELL_TYPE_NUMERIC:
	                             data.append(cell.getNumericCellValue() + ",");
	                             break;
	                             
	                     case Cell.CELL_TYPE_STRING:
	                             data.append(cell.getStringCellValue() + ",");
	                             break;

	                     case Cell.CELL_TYPE_BLANK:
	                             data.append("" + ",");
	                             break;
	                     
	                     default:
	                             data.append(cell + ",");
	                     }
	                     
	                     
	             }
	             data.append('\n'); 
	     }

	     fos.write(data.toString().getBytes());
	     fos.close();
	     }
	     catch (FileNotFoundException e) 
	     {
	             e.printStackTrace();
	     }
	     catch (IOException e) 
	     {
	             e.printStackTrace();
	     }
	     }

	public void GeneratehtmlReport() throws Exception 
	{
		// read suite.xls
		
		int iNoOfTestCase=0,iNoOfPass = 0,iNoOfFail=0,iNoOfSkip=0;
		//System.out.println("executing");
		Date d = new Date();
		String date=d.toString().replaceAll(" ", "_");
		date=date.replaceAll(":", "_");
		date=date.replaceAll("\\+", "_");
		//System.out.println(date);
		 result_FolderName="Reports"+"_"+date;
		//String reportsDirPath=System.getProperty("user.dir")+"\\Reports";
		 String reportsDirPath=System.getProperty("user.dir")+"\\Reports\\"+result_FolderName;
		new File(reportsDirPath).mkdirs();
		System.out.println("Generates Test Report Path \n"+reportsDirPath);

		String environment=CONFIG.getProperty("environment");
		String release=CONFIG.getProperty("release");
		Xls_Reader suiteXLS = new Xls_Reader(System.getProperty("user.dir")+APIConstants.SRC_RES_FILEPATH+"XLS//"+APIConstants.SUITE_XLFILE);
		// create index.html
		String indexHtmlPath=reportsDirPath+"\\index.html";
		new File(indexHtmlPath).createNewFile();
		try{
			  
			  FileWriter fstream = new FileWriter(indexHtmlPath);
			  BufferedWriter out = new BufferedWriter(fstream);
			  out.write("<html><HEAD> <TITLE>Automation Test Results</TITLE></HEAD><body><h4 align=center><FONT COLOR=660066 FACE=AriaL SIZE=6><b><u> Automation Test Results</u></b></h4><table  border=1 cellspacing=1 cellpadding=1 ><tr><h4> <FONT COLOR=660000 FACE=Arial SIZE=4.5> <u>Test Details :</u></h4><td width=150 align=left bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Arial SIZE=2.75><b>Run Date</b></td><td width=150 align=left><FONT COLOR=#153E7E FACE=Arial SIZE=2.75><b>");
			  out.write(d.toString());
			  out.write("</b></td></tr><tr><td width=150 align=left bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Arial SIZE=2.75><b>Run Environment</b></td><td width=150 align=left><FONT COLOR=#153E7E FACE=Arial SIZE=2.75><b>");
			  out.write(environment);
			  out.write("</b></td></tr><tr><td width=150 align= left  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2.75><b>Release</b></td><td width=150 align= left ><FONT COLOR=#153E7E FACE= Arial  SIZE=2.75><b>");
			  out.write(release);
			  out.write("</b></td></tr></table><h4> <FONT COLOR=660000 FACE= Arial  SIZE=4.5> <u>Report :</u></h4><table  border=1 cellspacing=1 cellpadding=1 width=100%><tr><td width=15% align= center  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2><b>SUITE NAME</b></td><td width=20% align= center  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2><b>DESCRIPTION</b></td><td width=15% align= center  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2><b>Status</b></td>");
			  out.write("<td width=10% align= center  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2><b>No Of TestCases</b></td><td width=10% align= center  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2><b>No Of Passed</b></td><td width=10% align= center  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2><b>No of Failed</b></td><td width=10% align= center  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2><b>No of Skipped</b></td></tr>");

			 // out.write("<tr><td width=20% align= center><FONT COLOR=#153E7E FACE= Arial  SIZE=2><b>TC04</b></td><td width=40% align= center><FONT COLOR=#153E7E FACE= Arial  SIZE=2><b>TC04</b></td><td width=10% align=center  bgcolor=yellow><FONT COLOR=153E7E FACE=Arial SIZE=2><b>Skip</b></td></tr>");
			  
			  int totalTestSuites=suiteXLS.getRowCount(APIConstants.TEST_SUITE_SHEET);
			  
			  String currentTestSuite=null;
			  Xls_Reader current_suite_xls=null;
			  String suite_result="";
			  //System.out.println("Debug:0 "+totalTestSuites);
			  //Harcoded suite count
			  currentTestSuite=null;
			  current_suite_xls=null;
				 // System.out.println("Debug:0 "+Constants.TEST_SUITE_SHEET+Constants.SUITE_ID+currentSuiteID);
				  
			  if (CONFIG.getProperty("InputType").equalsIgnoreCase("Jira"))
		     	{
				  current_suite_xls=new Xls_Reader(System.getProperty("user.dir")+APIConstants.SRC_RES_FILEPATH+"XLS//"+APIConstants.TEST_JIRAXLFILE);
		     	}
			  else
			  {
				  current_suite_xls=new Xls_Reader(System.getProperty("user.dir")+APIConstants.SRC_RES_FILEPATH+"XLS//"+APIConstants.TEST_XLFILE);
			  }
				  //System.out.println("TestCase Sheet: "+Constants.TEST_CASES_SHEET);
				 // System.out.println(suiteXLS.getRowCount(Constants.TEST_CASES_SHEET));
				  String currentTestName=null;
				  String currentTestRunmode=null;
				  String currentTestDescription=null;
				  
			     for(int currentTestCaseID=2;currentTestCaseID<=suiteXLS.getRowCount(APIConstants.TEST_SUITE_SHEET);currentTestCaseID++)
			     {
			    	 
			    	 	iNoOfTestCase=0;
			    	 
			    	    currentTestSuite = suiteXLS.getCellData(APIConstants.TEST_SUITE_SHEET, APIConstants.Test_Suite_ID,currentTestCaseID);
				    	// System.out.println("Debug:1 "+currentTestSuite);
				    	 currentTestName=null;
				    	 currentTestDescription=null;
				    	 currentTestRunmode=null;
				    	 
				    	  //System.out.println("Debug:2.1 "+""+"  Test Step Sheet: "+Constants.TEST_STEPS_SHEET);
						   APIConstants.TEST_STEPS_SHEET=suiteXLS.getCellData(APIConstants.TEST_SUITE_SHEET, APIConstants.TEST_CASES_SHEET, currentTestCaseID);
						   
						   APIConstants.TEST_STEPS_SHEET=APIConstants.TEST_STEPS_SHEET+"_TestResult";
				    	 
						   
						  // System.out.println("Test case name:"+Constants.TEST_STEPS_SHEET);
						   
				    	// System.out.println("Debug:1.2 "+Constants.TEST_CASES_SHEET+"TCID:"+Constants.TCID+currentTestCaseID+"RunMode:"+Constants.RUNMODE);
				    	 currentTestName = current_suite_xls.getCellData( APIConstants.TEST_STEPS_SHEET, APIConstants.TCID, currentTestCaseID);
				    	 currentTestDescription = current_suite_xls.getCellData( APIConstants.TEST_STEPS_SHEET, APIConstants.DESCRIPTION, currentTestCaseID);
				    	 currentTestRunmode = current_suite_xls.getCellData(APIConstants.TEST_STEPS_SHEET, APIConstants.RUNMODE, currentTestCaseID);
				    	 System.out.println(currentTestSuite + " -- "+currentTestName +currentTestRunmode);
				    	 
				    	// System.out.println("Debug:2 TestName: "+currentTestName+"  Des: "+currentTestDescription+"  RunMode: "+currentTestRunmode);
				    	 // make the file corresponding to test Steps
				    	   String testSteps_file=reportsDirPath+"\\"+currentTestSuite+"_steps.html";
						   new File(testSteps_file).createNewFile();						  
						
						
							  int rows= current_suite_xls.getRowCount(APIConstants.TEST_STEPS_SHEET);
								 
							  int cols = current_suite_xls.getColumnCount(APIConstants.TEST_STEPS_SHEET);
							// System.out.println("Debug:2.2 "+""+"  Rows: "+rows+"  Coles: "+cols);
							  FileWriter fstream_test_steps= new FileWriter(testSteps_file);
							  BufferedWriter out_test_steps= new BufferedWriter(fstream_test_steps);
							  out_test_steps.write("<html><HEAD> <TITLE>"+currentTestSuite+" Test Results</TITLE></HEAD><body><h4 align=center><FONT COLOR=660066 FACE=AriaL SIZE=6><b><u> "+currentTestSuite+" Detailed Test Results</u></b></h4><table width=100% border=1 cellspacing=1 cellpadding=1 >");
//							  out_test_steps.write("<tr>");
//							  // System.out.println("Debug:3 "+Constants.TEST_STEPS_SHEET+"Cols:"+cols);
//							  for(int colNum=0;colNum<cols;colNum++){
//								 // System.out.println("Debug:3.1 "+Constants.TEST_STEPS_SHEET+colNum+1);
//								  out_test_steps.write("<td align= center bgcolor=#153E7E><FONT COLOR=#ffffff FACE= Arial  SIZE=2><b>");
//								  out_test_steps.write(current_suite_xls.getCellData(Constants.TEST_STEPS_SHEET, colNum, 1));								
//							  }
//							  out_test_steps.write("</b></tr>");

							  out_test_steps.write("<tr>");
							  for(int colNum=0;colNum<cols;colNum++)
							  {
									 
							    	
								  String TestStepColumnName=current_suite_xls.getCellData(APIConstants.TEST_STEPS_SHEET, colNum, 1);
								 // System.out.println("Debug Col Value : "+TestStepColumnName);
								  switch (TestStepColumnName)
								  {
								  case "Issue key":
									  out_test_steps.write("<td align= center width=10% bgcolor=#153E7E><FONT COLOR=#ffffff FACE= Arial  SIZE=2><b>");
									  out_test_steps.write(TestStepColumnName);
									  break;
								  case "APIMethodType":
									  out_test_steps.write("<td align= center width=5% bgcolor=#153E7E><FONT COLOR=#ffffff FACE= Arial  SIZE=2><b>");
									  out_test_steps.write(TestStepColumnName);
									  break;
								  case "APIURL":
									  out_test_steps.write("<td align= center width=10px bgcolor=#153E7E><FONT COLOR=#ffffff FACE= Arial  SIZE=2><b>");
									  out_test_steps.write(TestStepColumnName);
									  break;
								  case "APIDescription":
									  out_test_steps.write("<td align= center width=10px bgcolor=#153E7E><FONT COLOR=#ffffff FACE= Arial  SIZE=2><b>");
									  out_test_steps.write(TestStepColumnName);
									  break;
								  case "Input_KeyValues":
									  out_test_steps.write("<td align= Left width=15px bgcolor=#153E7E><FONT COLOR=#ffffff FACE= Arial  SIZE=2><b>");
									  out_test_steps.write(TestStepColumnName);
									  break;
								  case "Expected":
									  out_test_steps.write("<td align= center width=15% bgcolor=#153E7E><FONT COLOR=#ffffff FACE= Arial  SIZE=2><b>");
									  out_test_steps.write(TestStepColumnName);
									  break;
								  case "TestResultStatus":
									  out_test_steps.write("<td align= center width=5% bgcolor=#153E7E><FONT COLOR=#ffffff FACE= Arial  SIZE=2><b>");
									  out_test_steps.write(TestStepColumnName);
									  break;
								  case "ResponseDescription":
									  out_test_steps.write("<td align= Left width=10px bgcolor=#153E7E><FONT COLOR=#ffffff FACE= Arial  SIZE=2><b>");
									  out_test_steps.write(TestStepColumnName);
									  break;
								  case "Output_Values":
									  out_test_steps.write("<td align= center width=5% bgcolor=#153E7E><FONT COLOR=#ffffff FACE= Arial  SIZE=2><b>");
									  out_test_steps.write(TestStepColumnName);
									  break;
								  case "Response":
									  out_test_steps.write("<td align= center width=5% bgcolor=#153E7E><FONT COLOR=#ffffff FACE= Arial  SIZE=2><b>");
									  out_test_steps.write(TestStepColumnName);
									  break;
								  default:
									  out_test_steps.write("<td align= center bgcolor=#153E7E><FONT COLOR=#ffffff FACE= Arial  SIZE=2><b>");
									  out_test_steps.write(TestStepColumnName);
								  }
								  
							  }
							  out_test_steps.write("</b></tr>");
							  


						  // fill the whole sheet
						  boolean result_col=false;
						 // System.out.println("Debug:4 Rows: "+rows);
						  iNoOfPass=0;
						  iNoOfFail=0;
						  iNoOfSkip=0;
						  for(int rowNum=2;rowNum<=rows;rowNum++){
							  iNoOfTestCase=iNoOfTestCase+1;
							
							  out_test_steps.write("<tr>");  
							  for(int colNum=0;colNum<cols-1;colNum++)
							  {						
								   String data=current_suite_xls.getCellData(APIConstants.TEST_STEPS_SHEET, colNum, rowNum);
								   data=data.replace("\n", "<br>");
								   result_col=current_suite_xls.getCellData(APIConstants.TEST_STEPS_SHEET, colNum, 1).startsWith(APIConstants.RESULT);
								
								  if(data.isEmpty()){
									  if(result_col)
										  data="SKIP";  
									  else
									  data=" ";
								  }
									//System.out.println(data);
								  if((data.startsWith("Pass") || data.startsWith("PASS")) && result_col)
								  {
									  iNoOfPass=iNoOfPass+1;
									 // System.out.println("Debug:3.2 no of Pass: "+iNoOfPass);
									  out_test_steps.write("<td align=center bgcolor=green><FONT COLOR=#000000 FACE= Arial  SIZE=2>");
								  }
								  else if((data.startsWith("Fail") || data.startsWith("FAIL")) && result_col){
									  iNoOfFail=iNoOfFail+1;
									  out_test_steps.write("<td align=center bgcolor=red><FONT COLOR=#000000 FACE= Arial  SIZE=2>");
									  if(suite_result.equals(""))
										  suite_result="FAIL";
								  	}
								  else if((data.startsWith("Skip") || data.startsWith("SKIP")) && result_col)
								  {
									  iNoOfSkip=iNoOfSkip+1;
									  out_test_steps.write("<td align=center bgcolor=yellow><FONT COLOR=#000000 FACE= Arial  SIZE=2>");
								  }
								  else 
								  {
									  out_test_steps.write("<td align= center bgcolor=#ffffff><FONT COLOR=#000000 FACE= Arial  SIZE=2>");
								  }
								   out_test_steps.write(data );
//								    String TestcaseID1 = current_suite_xls.getCellData(Constants.TEST_STEPS_SHEET,"TCID", rowNum);
//								   if (current_suite_xls.getCellData(Constants.TEST_STEPS_SHEET,"RunMode", rowNum).equalsIgnoreCase("YES"))
//									 {out_test_steps.write("<a href="+current_suite_xls.getCellData(Constants.TEST_STEPS_SHEET,"Response", rowNum)+">"+"TestcaseID1" +"</a>");}
//									 
							  }
							out_test_steps.write("<td>");
							String TestcaseID1 = current_suite_xls.getCellData(APIConstants.TEST_STEPS_SHEET,APIConstants.TCID, rowNum);							  
							String fp = current_suite_xls.getCellData(APIConstants.TEST_STEPS_SHEET,"Response", rowNum);
							//System.out.println("actual link"+"->"+"<a href="+current_suite_xls.getCellData(Constants.TEST_STEPS_SHEET,"Response", rowNum)+">"+TestcaseID1 +"</a>");
							//System.out.println(TestcaseID1 );
							out_test_steps.write("<a href="+fp+">"+TestcaseID1 +"</a>");
							//out_test_steps.write("<a href="+TestcaseID1 +"onclick=location.href='"+current_suite_xls.getCellData(Constants.TEST_STEPS_SHEET,"Response", rowNum)+">"+TestcaseID1+"</a>");}
							out_test_steps.write("</td>"); 
							out_test_steps.write("</tr>");
						  }					  
						  out_test_steps.write("</tr>");
						  out_test_steps.write("</table>");  
						  out_test_steps.close();

//						 out_suite_index.write("<tr><td width=20% align= center><FONT COLOR=#153E7E FACE= Arial  SIZE=2><b>");
//						 out_suite_index.write(currentTestName);
//						 out_suite_index.write("</b></td><td width=40% align= center><FONT COLOR=#153E7E FACE= Arial  SIZE=2><b>");
//						 out_suite_index.write(currentTestDescription);
//						 out_suite_index.write("</b></td><td width=10% align=center  bgcolor=");
//						 
//						 if(!currentTestRunmode.equalsIgnoreCase(Constants.RUNMODE_YES))
//							  out_suite_index.write("yellow><FONT COLOR=153E7E FACE=Arial SIZE=2><b>Skip</b></td></tr>");
//						  else
//							  out_suite_index.write("green><FONT COLOR=153E7E FACE=Arial SIZE=2><b>Pass</b></td></tr>");
//						 
						
				     
//				    out_suite_index.write("</table>");  
//				     out_suite_index.close();	
				  
						 // System.out.println("Summary : "+iNoOfTestCase+":"+iNoOfPass+":"+iNoOfFail+":"+iNoOfSkip);
				  out.write("<tr><td width=10% align= center><FONT COLOR=#153E7E FACE= Arial  SIZE=2><b>");
				  out.write("<a href="+currentTestSuite.replace(" ", "%20")+"_steps.html>"+currentTestSuite+"</a>");
				  out.write("</b></td><td width=25% align= center><FONT COLOR=#153E7E FACE= Arial  SIZE=2><b>");
				  out.write(suiteXLS.getCellData(APIConstants.TEST_SUITE_SHEET, APIConstants.DESCRIPTION,currentTestCaseID));
				  out.write("</b></td><td width=10% align=center  bgcolor=");
				  if(suiteXLS.getCellData(APIConstants.TEST_SUITE_SHEET, APIConstants.RUNMODE,currentTestCaseID).equalsIgnoreCase(APIConstants.RUNMODE_YES))
					  if(suite_result.equals("FAIL"))
						  	out.write("red><FONT COLOR=153E7E FACE=Arial SIZE=2><b>FAIL</b></td>");
					  else
						  	out.write("green><FONT COLOR=153E7E FACE=Arial SIZE=2><b>PASS</b></td>");
				  else
					  out.write("yellow><FONT COLOR=153E7E FACE=Arial SIZE=2><b>SKIP</b></td>");
				  
			     }
			     out.write("<td width=10% align=center ><FONT COLOR=153E7E FACE=Arial SIZE=2><b>"+iNoOfTestCase+"</b></td>");
			     out.write("<td width=10% align=center  bgcolor=green><FONT COLOR=153E7E FACE=Arial SIZE=2><b>"+iNoOfPass+"</b></td>");
			     out.write("<td width=10% align=center  bgcolor=red><FONT COLOR=153E7E FACE=Arial SIZE=2><b>"+iNoOfFail+"</b></td>");
			     out.write("<td width=10% align=center  bgcolor=yellow><FONT COLOR=153E7E FACE=Arial SIZE=2><b>"+iNoOfSkip+"</b></td></tr>");
			    //Close the output stream
				  out.write("</table>");
				  out.close();
				  //System.out.println("totalTestSuites"+totalTestSuites+currentTestDescription+currentTestRunmode);
			  
		}
			  catch (Exception e){//Catch exception if any
			  System.err.println("Error: " + e.getMessage());
			  e.printStackTrace();
		  }
		

		
		//SendMail.execute(CONFIG.getProperty("report_file_name"));
		
		
	}

}
