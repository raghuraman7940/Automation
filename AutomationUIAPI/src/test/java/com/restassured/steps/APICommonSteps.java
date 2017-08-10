package com.restassured.steps;
import java.io.FileInputStream;
import java.io.IOException;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.jbehave.core.annotations.*;

import com.restassured.util.APIConstants;
import com.restassured.util.Reporting;

public class APICommonSteps{
	
	public static Logger APP_LOGS;
	public static Reporting Rep;
	// properties
	public static Properties CONFIG;
	
	public static String CurrentTestCaseSheet;
	
	@BeforeStories
	public void beforeStories() throws   IOException, NumberFormatException, InterruptedException   {
   	    // ...
		System.out.println("Start Over Before Stories");
		// Initialize configuration file
		FileInputStream fs = new FileInputStream(System.getProperty("user.dir")+"//src//test//resources//config//config.properties");
		CONFIG= new Properties();
		CONFIG.load(fs);
		
		// Initialize reporting
		Rep=new Reporting();
		

		//Clear test results
		String XlsxInputFile=System.getProperty("user.dir")+APIConstants.SRC_RES_FILEPATH+"XLS\\"+APIConstants.TEST_XLFILE;
		String TestResultSheet=APIConstants.TEST_STEPS_SHEET+"_TestResult";
		//Rep.ClearReportExcel();
		Rep.ClearReportExcel(XlsxInputFile,TestResultSheet);
		
		// Initialize the app logs
		APP_LOGS = Logger.getLogger("devpinoyLogger");			
		APP_LOGS.debug("Hello");
		APP_LOGS.debug("Properties loaded. Starting testing");
		System.out.println("End of Before Stories : ");
	}
	

@AfterStories
public void afterStories() throws Exception {

	Rep.GeneratehtmlReport();
	System.out.println("End of After Storeies");
	
}
}