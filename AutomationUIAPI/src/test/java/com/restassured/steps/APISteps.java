package com.restassured.steps;

import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.AfterStories;
import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.annotations.BeforeStory;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import com.restassured.testbase.DriverScript;
import com.restassured.util.APIConstants;
import com.restassured.util.App;
import com.restassured.util.Reporting;

import io.restassured.response.Response;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.pearson.util.Xls_Reader;

import static com.restassured.util.App.APIStatus;
import static com.restassured.util.App.ApiDescription;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import jxl.read.biff.BiffException;
import jxl.write.WriteException;

public class APISteps{
	
	public static String XlsxInputFile;
	public static String TestResultSheet;
	public static int iTCGenNo;
	public static String sTCNoTemplate;
	public static Xls_Reader currentTestSuiteXLS;
	public static Response resGetSrcDir;
	public static App API;
	public static Reporting Rep;
	public static DriverScript test;
	public static String TCID,MethodType,APIUrl,Details,HeaderKeyValue,InputValues,GivenExpectedValues,ApiDescription,responseTime,OutValues,ResponseFilePath;
	
	
	
	 @BeforeStory(uponGivenStory=false)
	    public void beforeGivenStory() throws NumberFormatException, InterruptedException, NoSuchMethodException, SecurityException, IOException {

	    	API=new App();
	    	// Initialize reporting
	    	Rep=new Reporting();
	    	test = new DriverScript();
	    	
	    	XlsxInputFile=System.getProperty("user.dir")+APIConstants.SRC_RES_FILEPATH+"XLS//"+APIConstants.TEST_XLFILE;
			TestResultSheet=APIConstants.TEST_STEPS_SHEET+"_TestResult";
			
	    	//Get testdatasheet and userRole from Meta
	        currentTestSuiteXLS=new Xls_Reader(System.getProperty("user.dir")+APIConstants.SRC_RES_FILEPATH+"XLS//"+APIConstants.TEST_XLFILE);
	    	
	    	//Generate Test case number for every story
	    	iTCGenNo=0;
			sTCNoTemplate="TC_API_000";

			
	    }

	 @BeforeScenario
	 public void beforeEachScenario() {
	     // ...
		 TCID="";
		 MethodType="";
		 APIUrl="";
		 Details="";
		 HeaderKeyValue="";
		 InputValues="";
		 GivenExpectedValues="";
		 APIStatus="";
		 ApiDescription="";
		 responseTime="";
		 OutValues="";
		 ResponseFilePath="";
	 }
	  
	@When("the execute the JSON")
	public void whenTheExecuteTheJSON() throws AbstractMethodError, BiffException, WriteException, IOException, ProcessingException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		 //TODO 
		test.start();
		System.out.println("When");
	}
	@Then("Validate with expected result and store in excel")
	public void thenValidateWithExpectedResultAndStoreInExcel(){
		 //TODO 
	}
	@Given("the Input sheet $appurl")
	public void givenTheInputSheetDWCRestassuredsrcresAutomation_Run_Reportxlsx(String sappurl) throws AbstractMethodError, BiffException, WriteException, IOException, ProcessingException{
		 //TODO 
	}
	
	@Given("the API Request input details")
	public void givenTheAPIRequestInputDetails(){
		 //TODO 
		 TCID="";
		 MethodType="";
		 APIUrl="";
		 Details="";
		 HeaderKeyValue="";
		 InputValues="";
		 GivenExpectedValues="";
		 APIStatus="";
		 ApiDescription="";
		 responseTime="";
		 OutValues="";
		 ResponseFilePath="";
	}
	@When("execute the GET Method")
	public void whenExecuteTheGETMethod(@Named("Server") String Server, @Named("URL") String URL, @Named("MethodType") String MethodType, @Named("Header_Keys") String Header_Keys, @Named("Header_Values") String Header_Values, @Named("Param_Keys") String Param_Keys, @Named("Param_Values") String Param_Values) throws FileNotFoundException{
		 //TODO 
		//APIURL
		APIUrl=Server+URL;
		
		//Header value
		if ((Header_Values.length()>0)&&(!Header_Values.equalsIgnoreCase("na"))&&(!Header_Values.equalsIgnoreCase("null")))
		{
			HeaderKeyValue=" <b>Request Headers:</b>"+API.Rep_InputValues(Header_Keys,Header_Values);
		}
		//Parameter input values
		if ((Param_Values.length()>0)&&(!Param_Values.equalsIgnoreCase("na"))&&(!Param_Values.equalsIgnoreCase("null")))
		{
			InputValues="\n <b>Request Parameters:</b> "+API.Rep_InputValues(Param_Keys,Param_Values);
		}
		resGetSrcDir=API.Get_Method(APIUrl,Header_Keys,Header_Values);
    	String sResGetSrcDir=API.GetRespString(resGetSrcDir);
    	//Store response in local path
    	String ContentType=API.GetResponseContentType(resGetSrcDir);
    	iTCGenNo=iTCGenNo+1;
    	TCID=sTCNoTemplate+iTCGenNo;
    	ResponseFilePath=API.StoreRespose(currentTestSuiteXLS,TCID,ContentType,sResGetSrcDir,iTCGenNo);
	}
	@Then("Validate the respone with expected values")
	public void thenValidateTheResponeWithExpectedValues(@Named("Expected_Keys") String Expected_Keys, @Named("Expected_Values") String Expected_Values, @Named("ExpectedStatusCode") int ExpectedStatusCode, @Named("ExpectedSchemaPath") String ExpectedSchemaPath) throws ProcessingException, IOException{
		 //TODO
		
		//Expected values
		
		GivenExpectedValues=" <b>Expected Criteria</b> \n Status Code :"+ExpectedStatusCode;
		if ((Expected_Values.length()>0)&&(!Expected_Values.equalsIgnoreCase("null")))
		{
			GivenExpectedValues=GivenExpectedValues+"\n Expected Key Values :"+API.Rep_InputValues(Expected_Keys,Expected_Values);
		}
		if ((ExpectedSchemaPath.length()>0)&&(!ExpectedSchemaPath.equalsIgnoreCase("null")))
		{
			GivenExpectedValues=GivenExpectedValues+"\n Expected Schema File : "+ExpectedSchemaPath;
		}
		//Validate response status
    	API.VerifyResponseStatus(resGetSrcDir,ExpectedStatusCode);
    
    	//After success validate the further
     	 if (APIStatus.equalsIgnoreCase(APIConstants.KEYWORD_PASS))
    	 {
     		 //Get Response time in ms
	    	String sResponseTimeinMs=API.GetResponseTimeinMS(resGetSrcDir);
	    	//Validate Schema and expected values
	    	API.ValidataSchema(resGetSrcDir,ExpectedSchemaPath,ResponseFilePath);
	    	API.ResponseExpectedValidation(resGetSrcDir,Expected_Keys,Expected_Values);
    	 }
     	Rep.ReportExcelData(XlsxInputFile,TestResultSheet, TCID, MethodType,APIUrl,Details,HeaderKeyValue+InputValues,GivenExpectedValues,APIStatus,ApiDescription,responseTime,OutValues,ResponseFilePath);
	}
	@AfterScenario // equivalent to  @AfterScenario(uponOutcome=AfterScenario.Outcome.ANY)
	public void afterAnyScenario() throws AbstractMethodError, IOException {
	    // ...
	}
	@AfterStories
	public void afterStories() throws Exception {
	    // ...
		//Rep.GeneratehtmlReport();
	}

	
}