package com.pearson.util;
import java.io.*;
import java.util.Date;
import java.util.Properties;

import com.pearson.util.UIConstants;

public class ReportUtil {
public static String result_FolderName=null;
	public static void main(String[] arg) throws Exception 
	{
		
		// read suite.xls
				ReportUtil Rep=new ReportUtil();
				Rep.GeneratehtmlReport();
	}
	
	 public void ReportExcelData(String TCID, String TestScenario, String Description, String InputDetails,String Expected, String Status, String Actuals, String Comments)throws AbstractMethodError,  IOException
	 {
		
		int NewRow=1;
		
		//Initialize test case sheer
		String XLFileNmae=UIConstants.TEST_XLFILE;
		String SheetName=UIConstants.TEST_STEPS_SHEET;
		
	
		Xls_Reader current_TestCase_xls=null;    
  	 	current_TestCase_xls=new Xls_Reader(System.getProperty("user.dir")+"//src//test//resources//XLS//"+XLFileNmae+".xlsx");
	  	boolean SheetExist=current_TestCase_xls.isSheetExist(SheetName);
	  	System.out.println("SheetName Exist"+SheetExist);
	  	if (SheetExist=true)
	  	{
	  		int rowCount= current_TestCase_xls.getRowCount(SheetName);
			//int cols = current_TestCase_xls.getColumnCount(SheetName);
	  		NewRow=rowCount+1;
	  		current_TestCase_xls.setCellData(SheetName, "TCID", NewRow, TCID);
	    	current_TestCase_xls.setCellData(SheetName, "TestScenario", NewRow, TestScenario);
	    	current_TestCase_xls.setCellData(SheetName, "Description", NewRow, Description);
	    	current_TestCase_xls.setCellData(SheetName, "InputDetails", NewRow, InputDetails);
	    	current_TestCase_xls.setCellData(SheetName, "Expected", NewRow, Expected);
	    	current_TestCase_xls.setCellData(SheetName, "Status", NewRow, Status);
	    	current_TestCase_xls.setCellData(SheetName, "Actuals", NewRow, Actuals);
	    	current_TestCase_xls.setCellData(SheetName, "Comments", NewRow, Comments);
	  	}
  	 
		
	  }
	 
	 public void ClearReportExcel()throws AbstractMethodError,  IOException
	 {
		
		
		//Initialize test case sheer
		String XLFileNmae=UIConstants.TEST_XLFILE;
		String SheetName=UIConstants.TEST_STEPS_SHEET;
		
		
		Xls_Reader current_TestCase_xls=null;
  	 	current_TestCase_xls=new Xls_Reader(System.getProperty("user.dir")+"//src//test//resources//XLS//"+XLFileNmae+".xlsx");
	  	boolean SheetExist=current_TestCase_xls.isSheetExist(SheetName);
		System.out.println("SheetName Exist"+SheetExist);
	  	if (SheetExist=true)
	  	{
	  		int rowCount= current_TestCase_xls.getRowCount(SheetName);
	  		//System.out.println("RowCount : "+rowCount);
	  		if (rowCount>1)
	  		{
	  			current_TestCase_xls.removeAllRow(SheetName);
	  		}
	  		//System.out.println("End Row : ");

	  	}
  	 
	  }
		
	public void GeneratehtmlReport() throws Exception 
	{
		// read suite.xls
		System.out.println("executing");
		Date d = new Date();
		String date=d.toString().replaceAll(" ", "_");
		date=date.replaceAll(":", "_");
		date=date.replaceAll("\\+", "_");
		System.out.println(date);
		 result_FolderName="Reports"+"_"+date;
		String reportsDirPath=System.getProperty("user.dir")+"\\Reports\\"+result_FolderName;
		System.out.println(reportsDirPath);
		System.out.println(result_FolderName);
		new File(reportsDirPath).mkdirs();
		
		FileInputStream fs = new FileInputStream(System.getProperty("user.dir")+"//src//test//resources//config//config.properties");
		Properties CONFIG= new Properties();
		CONFIG.load(fs);
		String environment=CONFIG.getProperty("environment");
		String release=CONFIG.getProperty("release");
		Xls_Reader suiteXLS = new Xls_Reader(System.getProperty("user.dir")+"//src//test//resources//XLS//Suite.xlsx");

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
			  out.write("</b></td></tr></table><h4> <FONT COLOR=660000 FACE= Arial  SIZE=4.5> <u>Report :</u></h4><table  border=1 cellspacing=1 cellpadding=1 width=100%><tr><td width=20% align= center  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2><b>SUITE NAME</b></td><td width=40% align= center  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2><b>DESCRIPTION</b></td><td width=10% align= center  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2><b>EXECUTION RESULT</b></td></tr>");

			 // out.write("<tr><td width=20% align= center><FONT COLOR=#153E7E FACE= Arial  SIZE=2><b>TC04</b></td><td width=40% align= center><FONT COLOR=#153E7E FACE= Arial  SIZE=2><b>TC04</b></td><td width=10% align=center  bgcolor=yellow><FONT COLOR=153E7E FACE=Arial SIZE=2><b>Skip</b></td></tr>");
			  
			  int totalTestSuites=suiteXLS.getRowCount(UIConstants.TEST_SUITE_SHEET);
			  String currentTestSuite=null;
			  Xls_Reader current_suite_xls=null;
			  String suite_result="";
			  for(int currentSuiteID =2;currentSuiteID<= totalTestSuites;currentSuiteID++){
				  suite_result="";
				  currentTestSuite=null;
				  current_suite_xls=null;
				  currentTestSuite = suiteXLS.getCellData(UIConstants.TEST_SUITE_SHEET, UIConstants.SUITE_ID,currentSuiteID);
				  current_suite_xls=new Xls_Reader(System.getProperty("user.dir")+"//src//test//resources//XLS//"+currentTestSuite+".xlsx");
				  
				  String currentTestName=null;
				  String currentTestRunmode=null;
				  String currentTestDescription=null;
				  System.out.println(currentTestRunmode +currentTestDescription );
				     for(int currentTestCaseID=2;currentTestCaseID<=current_suite_xls.getRowCount(UIConstants.TEST_CASES_SHEET);currentTestCaseID++){
				    	 currentTestName=null;
				    	 currentTestDescription=null;
				    	 currentTestRunmode=null;
				    	 
				    	 currentTestName = current_suite_xls.getCellData(UIConstants.TEST_CASES_SHEET, UIConstants.TCID, currentTestCaseID);
				    	 currentTestDescription = current_suite_xls.getCellData(UIConstants.TEST_CASES_SHEET, UIConstants.DESCRIPTION, currentTestCaseID);
				    	 currentTestRunmode = current_suite_xls.getCellData(UIConstants.TEST_CASES_SHEET, UIConstants.RUNMODE, currentTestCaseID);
				    	 System.out.println(currentTestSuite + " -- "+currentTestName );
				    	 
				    	 
				    	 // make the file corresponding to test Steps
				    	 String testSteps_file=reportsDirPath+"\\"+currentTestSuite+"_steps.html";
						  new File(testSteps_file).createNewFile();
						  
						  int rows= current_suite_xls.getRowCount(UIConstants.TEST_STEPS_SHEET);
						  int cols = current_suite_xls.getColumnCount(UIConstants.TEST_STEPS_SHEET);
						  FileWriter fstream_test_steps= new FileWriter(testSteps_file);
						  BufferedWriter out_test_steps= new BufferedWriter(fstream_test_steps);
						  out_test_steps.write("<html><HEAD> <TITLE>"+currentTestSuite+" Test Results</TITLE></HEAD><body><h4 align=center><FONT COLOR=660066 FACE=AriaL SIZE=6><b><u> "+currentTestSuite+" Detailed Test Results</u></b></h4><table width=100% border=1 cellspacing=1 cellpadding=1 >");

						  out_test_steps.write("<tr>");
						  for(int colNum=0;colNum<cols;colNum++){
							  String TestStepColumnName=current_suite_xls.getCellData(UIConstants.TEST_STEPS_SHEET, colNum, 1);
							  //System.out.println("Debug Col Value : "+TestStepColumnName);
							  switch (TestStepColumnName)
							  {
							  case "TCID":
								  out_test_steps.write("<td align= center width=5% bgcolor=#153E7E><FONT COLOR=#ffffff FACE= Arial  SIZE=2><b>");
								  out_test_steps.write(TestStepColumnName);
								  break;
							  case "TestScenario":
								  out_test_steps.write("<td align= center width=10% bgcolor=#153E7E><FONT COLOR=#ffffff FACE= Arial  SIZE=2><b>");
								  out_test_steps.write(TestStepColumnName);
								  break;
							  case "Description":
								  out_test_steps.write("<td align= center width=15% bgcolor=#153E7E><FONT COLOR=#ffffff FACE= Arial  SIZE=2><b>");
								  out_test_steps.write(TestStepColumnName);
								  break;
							  case "InputDetails":
								  out_test_steps.write("<td align= center width=20% bgcolor=#153E7E><FONT COLOR=#ffffff FACE= Arial  SIZE=2><b>");
								  out_test_steps.write(TestStepColumnName);
								  break;
							  case "Expected":
								  out_test_steps.write("<td align= center width=15% bgcolor=#153E7E><FONT COLOR=#ffffff FACE= Arial  SIZE=2><b>");
								  out_test_steps.write(TestStepColumnName);
								  break;
							  case "Status":
								  out_test_steps.write("<td align= center width=5% bgcolor=#153E7E><FONT COLOR=#ffffff FACE= Arial  SIZE=2><b>");
								  out_test_steps.write(TestStepColumnName);
								  break;
							  case "Actuals":
								  out_test_steps.write("<td align= center width=15% bgcolor=#153E7E><FONT COLOR=#ffffff FACE= Arial  SIZE=2><b>");
								  out_test_steps.write(TestStepColumnName);
								  break;
							  case "Comments":
								  out_test_steps.write("<td align= center width=15% bgcolor=#153E7E><FONT COLOR=#ffffff FACE= Arial  SIZE=2><b>");
								  out_test_steps.write(TestStepColumnName);
								  break;
							  default:
								  out_test_steps.write("<td align= centerbgcolor=#153E7E><FONT COLOR=#ffffff FACE= Arial  SIZE=2><b>");
								  out_test_steps.write(TestStepColumnName);
							  }
							  
						  }
						  out_test_steps.write("</b></tr>");

						  // fill the whole sheet
						  boolean result_col=false;
//						  System.out.println("Debug rows : "+rows);
						  for(int rowNum=2;rowNum<=rows;rowNum++){
							  out_test_steps.write("<tr>"); 
//							  System.out.println("Debug cols : "+cols);
							  for(int colNum=0;colNum<cols;colNum++){
								  String data=current_suite_xls.getCellData(UIConstants.TEST_STEPS_SHEET, colNum, rowNum);
								   result_col=current_suite_xls.getCellData(UIConstants.TEST_STEPS_SHEET, colNum, 1).startsWith(UIConstants.RESULT);
								   String TestStepColumnName=current_suite_xls.getCellData(UIConstants.TEST_STEPS_SHEET, colNum, 1);
								   switch(TestStepColumnName)
									  {
									  case "TCID":
										  out_test_steps.write("<td align=center bgcolor=#ffffff><FONT COLOR=#000000 FACE= Arial  SIZE=2>");
										  out_test_steps.write(data);
										  break;
									  case "TestScenario":
										  out_test_steps.write("<td align=center bgcolor=#ffffff><FONT COLOR=#000000 FACE= Arial  SIZE=2>");
										  out_test_steps.write(data);
										  break;
									  case "Description":
										  out_test_steps.write("<td align=left bgcolor=#ffffff><FONT COLOR=#000000 FACE= Arial  SIZE=2>");
										  out_test_steps.write(data);
										  break;
									  case "InputDetails":
										  out_test_steps.write("<td align=left bgcolor=#ffffff><FONT COLOR=#000000 FACE= Arial  SIZE=2>");
										  out_test_steps.write(data);
										  break;
									  case "Expected":
										  out_test_steps.write("<td align=center bgcolor=#ffffff><FONT COLOR=#000000 FACE= Arial  SIZE=2>");
										  out_test_steps.write(data);
										  break;
									  case "Status":
										  if(data.isEmpty()){
											  if(result_col)
												  data="SKIP";  
											  else
											  data=" ";
										  }
										  if((data.startsWith("Pass") || data.startsWith("PASS")) && result_col)
											  out_test_steps.write("<td align=center bgcolor=green><FONT COLOR=#000000 FACE= Arial  SIZE=2>");
										  else if((data.startsWith("Fail") || data.startsWith("FAIL")) && result_col){
											  out_test_steps.write("<td align=center bgcolor=red><FONT COLOR=#000000 FACE= Arial  SIZE=2>");
											  if(suite_result.equals(""))
											  suite_result="FAIL";
										  }
										  else if((data.startsWith("Skip") || data.startsWith("SKIP")) && result_col)
											  out_test_steps.write("<td align=center bgcolor=yellow><FONT COLOR=#000000 FACE= Arial  SIZE=2>");
										  out_test_steps.write(data);
										  break;
									  case "Actuals":
										  out_test_steps.write("<td align=center bgcolor=#ffffff><FONT COLOR=#000000 FACE= Arial  SIZE=2>");
										  out_test_steps.write(data);
										  break;
									  case "Comments":
										  out_test_steps.write("<td align=left bgcolor=#ffffff><FONT COLOR=#000000 FACE= Arial  SIZE=2>");
										  out_test_steps.write(data);
										  break;
									  default:
										  out_test_steps.write("<td align=justify bgcolor=#ffffff><FONT COLOR=#000000 FACE= Arial  SIZE=2>");
										  out_test_steps.write(data);
									  }

							  }
							  out_test_steps.write("</tr>");
						  }
						  
						  out_test_steps.write("</tr>");
						  out_test_steps.write("</table>");  
						  out_test_steps.close();
						  
				     }
				  out.write("<tr><td width=20% align= center><FONT COLOR=#153E7E FACE= Arial  SIZE=2><b>");
				  out.write("<a href="+currentTestSuite.replace(" ", "%20")+"_steps.html>"+currentTestSuite+"</a>");
				  out.write("</b></td><td width=40% align= center><FONT COLOR=#153E7E FACE= Arial  SIZE=2><b>");
				  out.write(suiteXLS.getCellData(UIConstants.TEST_SUITE_SHEET, UIConstants.DESCRIPTION,currentSuiteID));
				  out.write("</b></td><td width=10% align=center  bgcolor=");
				  if(suiteXLS.getCellData(UIConstants.TEST_SUITE_SHEET, UIConstants.RUNMODE,currentSuiteID).equalsIgnoreCase(UIConstants.RUNMODE_YES))
					  if(suite_result.equals("FAIL"))
						  out.write("red><FONT COLOR=153E7E FACE=Arial SIZE=2><b>FAIL</b></td></tr>");
						  else
							  out.write("green><FONT COLOR=153E7E FACE=Arial SIZE=2><b>PASS</b></td></tr>");
				  else
					  out.write("yellow><FONT COLOR=153E7E FACE=Arial SIZE=2><b>SKIP</b></td></tr>");
			  }
			  //Close the output stream
			  out.write("</table>");
			  out.close();
			  }catch (Exception e){//Catch exception if any
			  System.err.println("Error: " + e.getMessage());
			  e.printStackTrace();
			  }
		
		
		
	}

	

}
