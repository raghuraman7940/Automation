package com.restassured.testbase;

import static com.restassured.steps.APICommonSteps.CONFIG;
import static com.restassured.util.App.APIStatus;
import static com.restassured.util.App.ApiDescription;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.pearson.util.Xls_Reader;
import com.restassured.util.APIConstants;
import com.restassured.util.App;
import com.restassured.util.Reporting;

import io.restassured.response.Response;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;

public class DriverScript {

	public static Logger APP_LOGS;
	//suite.xlsx
	static Xls_Reader XlsxSuite_xls=null;
	public int currentSuiteID;
	public String currentTestSuite;
	
	// current test suite
	
  	static Xls_Reader current_TestCase_xls=null;
  

	public static App API;
  	public static Reporting Rep;

//	// properties
//	public static Properties CONFIG;


	public DriverScript() throws NoSuchMethodException, SecurityException{

		
	}
	
	public static void main(String[] args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException, NoSuchMethodException, SecurityException {
		FileInputStream fs = new FileInputStream(System.getProperty("user.dir")+"//src//test//resources//config//config.properties");
		CONFIG= new Properties();
		CONFIG.load(fs);
		
		API=new App();
		Rep=new Reporting();
		DriverScript test = new DriverScript();

		test.start();
	}
	
	
	public void start() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, IOException{
		// initialize the app logs
		APP_LOGS = Logger.getLogger("devpinoyLogger");
		APP_LOGS.debug("Hello");
		APP_LOGS.debug("Properties loaded. Starting testing");
		
		API=new App();
		Rep=new Reporting();
		
		// 1) check the runmode of test Suite
		// 2) Runmode of the test case in test suite
	    // 3) Execute keywords of the test case serially
		// 4) Execute Keywords as many times as
		// number of data sets - set to Y
		APP_LOGS.debug("Intialize Suite xlsx");
		try {
	    	
	        //Export JIRA
	    	int JiraStatusCode=200;
	    	if (JiraStatusCode==200)
	    	{
	    		ReadExcel_Data();//Main driver function
	    	}
			//Updated JIRA with Status		    	
	    	//API.JIRAImport();
			Rep.GeneratehtmlReport();//Generate report
			
	    	
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	
	}
	
	
	/*************************************************************************************************
	 *  Function name 		: ReadExcel_Data
	 *  Reuse Function 		: 
	 *  Description 		: Read the all the Excel values.Based on the inputs flow the functionality. 
	/
	 * @throws ProcessingException **************************************************************************************************/  
    public void ReadExcel_Data()throws AbstractMethodError, BiffException, IOException,  WriteException, ProcessingException
    {
        String APIURL,APIServer,Url,MethodType,ParamKey,ParamValues,ExpectedKeys,ExpectedValues,Actual,TCID,OpKey,WebServices,RunMode1,SwitchingMode,ExpectedStatusCode,ExpectedSchemaPath;
        String OutValues,Details,HeaderKey,HeaderValues,ContentType;
        String InputValues,GivenExpectedValues,HeaderKeyValue;
        String XLSheetname_TestResults,XLInputSheetName,XlsxSuiteFile,XlsxInputFile = null;
        int ExpStatusid;
        long responseTime=0;
        String ResponseFilePath,sResponseTimeinMs="";
        boolean bExpected,bJsonSchema;
        
        
        //Set Input file name
        XlsxSuiteFile =System.getProperty("user.dir")+APIConstants.SRC_RES_FILEPATH+"XLS\\"+APIConstants.SUITE_XLFILE;
        XlsxSuite_xls=new Xls_Reader(XlsxSuiteFile);
 	 	int TEST_CASES_rows= XlsxSuite_xls.getRowCount(APIConstants.TEST_SUITE_SHEET);     	 	
		for(int rowNum1=2;rowNum1<=TEST_CASES_rows;rowNum1++)
			{	
				//Suite level Iteration
				//Selected Suite
				String RunMode=XlsxSuite_xls.getCellData(APIConstants.TEST_SUITE_SHEET, APIConstants.RUNMODE, rowNum1);
				if (RunMode.equalsIgnoreCase(APIConstants.RUNMODE_YES))					
				{
			        XLInputSheetName=XlsxSuite_xls.getCellData(APIConstants.TEST_SUITE_SHEET, APIConstants.TEST_CASES_SHEET, rowNum1);
			        
			        
			        //Get Input Sheet
			        XlsxInputFile=GetSelectedInputFile(XLInputSheetName);
			        System.out.println("InputFile : "+XlsxInputFile);
			        System.out.println("SuiteName : "+XLInputSheetName);
			        //Set Input file name
			        current_TestCase_xls=new Xls_Reader(XlsxInputFile);

					//Create or Clear Test Result sheet for each suite
			        XLSheetname_TestResults=XLInputSheetName+"_TestResult";
					Rep.ClearReportExcel(XlsxInputFile,XLSheetname_TestResults);
			 	 	int TEST_STEPS_rows= current_TestCase_xls.getRowCount(XLInputSheetName);			
					for(int rowNum=2;rowNum<=TEST_STEPS_rows;rowNum++)
					{
						
						//Test Iteration
						APIConstants.TEST_STEPS_SHEET=XLInputSheetName;
						TCID=current_TestCase_xls.getCellData(XLInputSheetName, APIConstants.TCID, rowNum);
						APIServer=current_TestCase_xls.getCellData(XLInputSheetName, APIConstants.SERVER, rowNum);
						Url=current_TestCase_xls.getCellData(XLInputSheetName, APIConstants.URL, rowNum);
						OpKey=current_TestCase_xls.getCellData(XLInputSheetName, APIConstants.GetOutputKey, rowNum);
						MethodType=current_TestCase_xls.getCellData(XLInputSheetName,APIConstants.MethodType, rowNum);
						Details=current_TestCase_xls.getCellData(XLInputSheetName,APIConstants.Details, rowNum);
						HeaderKey=current_TestCase_xls.getCellData(XLInputSheetName,APIConstants.Header_Keys, rowNum);
						HeaderValues=current_TestCase_xls.getCellData(XLInputSheetName,APIConstants.Header_Values, rowNum);
						ParamKey=current_TestCase_xls.getCellData(XLInputSheetName, APIConstants.Param_Keys, rowNum);
						ParamValues=current_TestCase_xls.getCellData(XLInputSheetName, APIConstants.Param_Values, rowNum);
						ExpectedKeys=current_TestCase_xls.getCellData(XLInputSheetName, APIConstants.Expected_Keys, rowNum);
						ExpectedValues=current_TestCase_xls.getCellData(XLInputSheetName, APIConstants.Expected_Values, rowNum);
						ExpectedStatusCode=current_TestCase_xls.getCellData(XLInputSheetName,APIConstants.ExpectedStatusCode, rowNum);
						ExpectedSchemaPath=current_TestCase_xls.getCellData(XLInputSheetName, APIConstants.ExpectedSchema, rowNum);						
						RunMode1=current_TestCase_xls.getCellData(XLInputSheetName, APIConstants.TESTRUNMODE, rowNum);
						SwitchingMode=current_TestCase_xls.getCellData(XLInputSheetName,APIConstants.SwitchingMode, rowNum);
						
						//System.out.println("Column Wise "+MethodType+ParamKey+ParamValues+ExpectedKeys+ExpectedValues+RunMode1);
						if (RunMode1.equalsIgnoreCase("YES"))
						{
							
							//Initialize test level variable
							ApiDescription="";
							OutValues="";
							ExpStatusid=0;
							APIStatus=APIConstants.KEYWORD_FAIL;
							ResponseFilePath=null;
							ContentType="";
							InputValues="";
							GivenExpectedValues="";
							HeaderKeyValue="";
							sResponseTimeinMs="";
							ExpStatusid =(int) Double.parseDouble(ExpectedStatusCode);
							//API Url initialize
							//APIURL=APIServer+Url;
							while (Url.contains(APIConstants.KEYWORD_REF))
							{
								Url= API.GetReferenceValue(XlsxInputFile,XLInputSheetName,Url);
							}
							APIURL=APIServer+Url;
							//Header value
							if ((HeaderValues.length()>0)&&(!HeaderValues.equalsIgnoreCase("na"))&&(!HeaderValues.equalsIgnoreCase("null")))
							{
								while (HeaderValues.contains(APIConstants.KEYWORD_REF))
								{
									HeaderValues= API.GetReferenceValue(XlsxInputFile,XLInputSheetName,HeaderValues);
								}
								HeaderKeyValue=" <b>Request Headers:</b>"+API.Rep_InputValues(HeaderKey,HeaderValues);
							}
							//Parameter input values
							if ((ParamValues.length()>0)&&(!ParamValues.equalsIgnoreCase("na"))&&(!ParamValues.equalsIgnoreCase("null")))
							{
								while (ParamValues.contains(APIConstants.KEYWORD_REF))
								{
									ParamValues= API.GetReferenceValue(XlsxInputFile,XLInputSheetName,ParamValues);
								}
								InputValues="\n <b>Request Parameters:</b> "+API.Rep_InputValues(ParamKey,ParamValues);
							}
							//Expected values
							GivenExpectedValues=" <b>Expected Criteria</b> \n Status Code :"+ExpectedStatusCode;
							
							if ((ExpectedValues.length()>0)&&(!ExpectedValues.equalsIgnoreCase("null")))
							{
								while (ExpectedValues.contains(APIConstants.KEYWORD_REF))
								{
									ExpectedValues= API.GetReferenceValue(XlsxInputFile,XLInputSheetName,ExpectedValues);
								}
								GivenExpectedValues=GivenExpectedValues+"\n Expected Key Values :"+API.Rep_InputValues(ExpectedKeys,ExpectedValues);
							}
							if ((ExpectedSchemaPath.length()>0)&&(!ExpectedSchemaPath.equalsIgnoreCase("null")))
							{
								GivenExpectedValues=GivenExpectedValues+"\n Expected Schema File : "+ExpectedSchemaPath;
								ExpectedSchemaPath=CONFIG.getProperty("SchemaPath")+ExpectedSchemaPath;
							}
							
							System.out.println("API Input Values : "+APIURL+MethodType+HeaderValues+ParamValues+ExpectedKeys+ExpectedValues+RunMode1);
							
							//Start test level execution
							if (!APIStatus.equalsIgnoreCase("Skip"))
							{
								switch(MethodType) 
								{
							    case "GET":		
									//GET Request
							    	 
							    	Response resGetSrcDir=API.Get_Method(APIURL,HeaderKey,HeaderValues);
							    	String sResGetSrcDir=API.GetRespString(resGetSrcDir);
							    	int GStatusCode=resGetSrcDir.getStatusCode();	
			//								        HttpResponse resGetSrcDir = HttpGet_Method(APIURL,HeaderKey,HeaderValues);
			//								        String sResGetSrcDir = GetHttpRespString(resGetSrcDir);
			//								    	int GStatusCode = resGetSrcDir.getStatusLine().getStatusCode();
							    	
							    	//Store response in local path
							    	ContentType=API.GetResponseContentType(resGetSrcDir);
							    	ResponseFilePath=API.StoreRespose(current_TestCase_xls,TCID,ContentType,sResGetSrcDir,rowNum);
							    	//Validate response status
							    	API.VerifyResponseStatus(resGetSrcDir,ExpStatusid);
							    	//After success validate the further
							     	 if (APIStatus.equalsIgnoreCase(APIConstants.KEYWORD_PASS))
							    	 {
							     		 //Get Response time in ms
								    	sResponseTimeinMs=API.GetResponseTimeinMS(resGetSrcDir);
								    	//Get outkey value and switching mode
								    	OutValues=API.GetOutkeyValueFromResponse(resGetSrcDir,OpKey);
								    	API.ImplSwitchingMode(XlsxInputFile,XLInputSheetName,resGetSrcDir,SwitchingMode);
								    	//Validate Schema and expected values
								    	API.ValidataSchema(resGetSrcDir,ExpectedSchemaPath,ResponseFilePath);
								    	bExpected=API.ResponseExpectedValidation(resGetSrcDir,ExpectedKeys,ExpectedValues);
							    	 }
					        	 	ReportActual(rowNum,APIStatus, ApiDescription,OutValues,sResponseTimeinMs);
							        break;
								case "POST":
									//POST Request
									
							    	Response Pres= API.Post_Method(APIURL,HeaderKey,HeaderValues,ParamKey,ParamValues);
							    	String sPostResp = API.GetRespString(Pres);
									int PStatusCode=Pres.getStatusCode();
									
			//								        HttpResponse Pres = HTTPPost_Method(APIURL,HeaderKey,HeaderValues,ParamKey,ParamValues);
			//								        String sPostResp = GetHttpRespString(Pres);
			//								    	int PStatusCode = Pres.getStatusLine().getStatusCode();
									
									//Store response in local path
									ContentType=API.GetResponseContentType(Pres);
							    	ResponseFilePath=API.StoreRespose(current_TestCase_xls,TCID,ContentType,sPostResp,rowNum);
							    	//Validate response status
							    	API.VerifyResponseStatus(Pres,ExpStatusid);
							     	 if (APIStatus.equalsIgnoreCase(APIConstants.KEYWORD_PASS))
							    	 {
							     		 //Get Response time in ms
								    	sResponseTimeinMs=API.GetResponseTimeinMS(Pres);
								    	//Get outkey value and switching mode
								    	OutValues=API.GetOutkeyValueFromResponse(Pres,OpKey);
								    	API.ImplSwitchingMode(XlsxInputFile,XLInputSheetName,Pres,SwitchingMode);
								    	//Validate Schema and expected values
								    	API.ValidataSchema(Pres,ExpectedSchemaPath,ResponseFilePath);
							    		bExpected=API.ResponseExpectedValidation(Pres,ExpectedKeys,ExpectedValues);
							    	 }
						    		ReportActual(rowNum,APIStatus, ApiDescription,OutValues, sResponseTimeinMs);
						    		
							        break;
								case "PUT":		
									//PUT Request
							    	Response Putres= API.Put_Method(APIURL,HeaderKey,HeaderValues,ParamKey,ParamValues);
							    	int PutStatusCode=Putres.getStatusCode();
							    	String sPuttResp = API.GetRespString(Putres);
			//										HttpResponse Putres = HttpPut_Method(APIURL,HeaderKey,HeaderValues,ParamKey,ParamValues);
			//								        String sPuttResp = GetHttpRespString(Putres);
			//								    	int PutStatusCode = Putres.getStatusLine().getStatusCode();
							    	//Store response in local path
							    	ContentType=API.GetResponseContentType(Putres);
							    	ResponseFilePath=API.StoreRespose(current_TestCase_xls,TCID,ContentType,sPuttResp,rowNum);
							    	//Validate response status
							    	API.VerifyResponseStatus(Putres,ExpStatusid);
							     	 if (APIStatus.equalsIgnoreCase(APIConstants.KEYWORD_PASS))
							    	 {
							     		 //Get Response time in ms
								    	sResponseTimeinMs=API.GetResponseTimeinMS(Putres);
								    	//Get outkey value and switching mode
								    	OutValues=API.GetOutkeyValueFromResponse(Putres,OpKey);
								    	API.ImplSwitchingMode(XlsxInputFile,XLInputSheetName,Putres,SwitchingMode);
							        	//Validate Schema and expected values
								    	API.ValidataSchema(Putres,ExpectedSchemaPath,ResponseFilePath);
							    		bExpected=API.ResponseExpectedValidation(Putres,ExpectedKeys,ExpectedValues);
							    	 }
						        	ReportActual(rowNum,APIStatus, ApiDescription,OutValues, sResponseTimeinMs);
							        break;
							    	case "DELETE":	
										//DELETE Request
								    	Response Delres= API.Delete_Method(APIURL,HeaderKey,HeaderValues,ParamKey,ParamValues);
								    	int DelStatusCode=Delres.getStatusCode();
								    	String sDelResp = API.GetRespString(Delres);
			//								    		HttpResponse Delres = HttpDelete_Method(APIURL,HeaderKey,HeaderValues,ParamKey,ParamValues);
			//									        String sDelResp = GetHttpRespString(Delres);
			//									        int DelStatusCode = Delres.getStatusLine().getStatusCode();
								    	//Store response in local path 
								    	ContentType=API.GetResponseContentType(Delres);
								    	ResponseFilePath=API.StoreRespose(current_TestCase_xls,TCID,ContentType,sDelResp,rowNum);
								    	//Validate response status
								    	API.VerifyResponseStatus(Delres,ExpStatusid);
								     	 if (APIStatus.equalsIgnoreCase(APIConstants.KEYWORD_PASS))
								    	 {
								     		 //Get Response time in ms
									    	sResponseTimeinMs=API.GetResponseTimeinMS(Delres);
									    	//Get outkey value
								    		OutValues=API.GetOutkeyValueFromResponse(Delres,OpKey);
								    		//Validate Schema and expected values
								    		API.ValidataSchema(Delres,ExpectedSchemaPath,ResponseFilePath);
								    		bExpected=API.ResponseExpectedValidation(Delres,ExpectedKeys,ExpectedValues);
								    	 }
							    		ReportActual(rowNum,APIStatus, ApiDescription,OutValues, sResponseTimeinMs);
							    	break;
							    default:
						}
					}
		//System.out.println(XLSheetname_TestResults+TCID+MethodType+APIURL+Details+HeaderKeyValue+InputValues+GivenExpectedValues+APIStatus+ApiDescription+OutValues+ResponseFilePath);
		Rep.ReportExcelData(XlsxInputFile,XLSheetname_TestResults, TCID, MethodType,APIServer+"\n<\br>"+Url,Details,HeaderKeyValue+InputValues,GivenExpectedValues,APIStatus,ApiDescription,sResponseTimeinMs,OutValues,ResponseFilePath);
		}
	 }
	}
	}
		if (CONFIG.getProperty("InputType").equalsIgnoreCase("Jira"))
    	{
		Rep.convertXLSXtoCSV(XlsxInputFile,CONFIG.getProperty("JiracsvImportPath"));
    	}
      }
	
	
	
    /***************************************************************************************
 	 *  Function name 		: GetSelectedInputFile
 	 *  Reuse Function 		:  
 	 *  Description 		: GET Selected input xl file depends on Inputtype
 	/****************************************************************************************/ 
    public static String GetSelectedInputFile(String Sheetname)
    {
    
    	 String XlsxInputFile=null;
    	if (CONFIG.getProperty("InputType").equalsIgnoreCase("Jira"))
    	{
		    String InputFileName=CONFIG.getProperty("JiracsvExportPath");
		   
		    String XLInputSheetName=Sheetname;
		    
		    String fext =FilenameUtils.getExtension(InputFileName);
		    if(fext.equalsIgnoreCase("csv"))
		    {
		    	XlsxInputFile=System.getProperty("user.dir")+APIConstants.SRC_RES_FILEPATH+"XLS\\"+APIConstants.TEST_JIRAXLFILE;
		    	Rep.ConvertCSVToXLSX(InputFileName, XlsxInputFile,XLInputSheetName);
		    }
    	}
    	else
	    {
	    	XlsxInputFile=System.getProperty("user.dir")+APIConstants.SRC_RES_FILEPATH+"XLS\\"+APIConstants.TEST_XLFILE;
	    }
	    return XlsxInputFile;
    }
	
	/*************************************************************************************************
	 *  Function name 		: ReportActual
	 *  Reuse Function 		: 
	 *  Description 		: Report status, output value, Description 
	/**************************************************************************************************/    
  public void ReportActual(int rowNum,String Status, String Description,String Output_Values, String sResponseTimeMs)
  {
	 current_TestCase_xls.setCellData(APIConstants.TEST_STEPS_SHEET, APIConstants.RESULT, rowNum,Status);
 	 current_TestCase_xls.setCellData(APIConstants.TEST_STEPS_SHEET, APIConstants.Output_Values, rowNum, Output_Values);
 	 current_TestCase_xls.setCellData(APIConstants.TEST_STEPS_SHEET, APIConstants.ResponseDescription, rowNum,Description);
 	 current_TestCase_xls.setCellData(APIConstants.TEST_STEPS_SHEET, APIConstants.ResponseTime, rowNum,sResponseTimeMs);
 	 
  }

	
}
