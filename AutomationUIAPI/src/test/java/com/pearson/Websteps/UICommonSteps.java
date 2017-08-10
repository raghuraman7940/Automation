package com.pearson.Websteps;
import java.io.FileInputStream;
import java.io.IOException;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.jbehave.core.annotations.*;

import com.pearson.util.ReportUtil;

public class UICommonSteps{
	
	public static Logger APP_LOGS;
	public static ReportUtil Rep;
	// properties
	public static Properties CONFIG;
	public static Properties OR;
	
	public static String CurrentTestCaseSheet;
	
	@BeforeStories
	public void beforeStories() throws   IOException, NumberFormatException, InterruptedException   {
 
		// Initialize configuration file
		FileInputStream fs = new FileInputStream(System.getProperty("user.dir")+"//src//test//resources//config//config.properties");
		CONFIG= new Properties();
		CONFIG.load(fs);
		
		// Initialize object repositories file
		fs = new FileInputStream(System.getProperty("user.dir")+"//src//test//resources//config//or.properties");
		OR= new Properties();
		OR.load(fs);

		// Initialize reporting
		Rep=new ReportUtil();

		//Clear test results
		Rep.ClearReportExcel();
		
		
		// Initialize the app logs
		APP_LOGS = Logger.getLogger("devpinoyLogger");			
		APP_LOGS.debug("Hello");
		APP_LOGS.debug("Properties loaded. Starting testing");
	}
	

@AfterStories
public void afterStories() throws Exception {
	Rep.GeneratehtmlReport();
	
}
}