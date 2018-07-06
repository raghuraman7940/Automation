package com.hcl.testing.selenium.base;


//import static com.pearson.Websteps.UICommonSteps.APP_LOGS;
//import static com.pearson.Websteps.UICommonSteps.CONFIG;
//import static com.pearson.Websteps.UICommonSteps.OR;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

///Testing Import
//import static com.auto.web.Testing.APP_LOGS;
//import static com.auto.web.Testing.CONFIG;
//import static com.auto.web.Testing.OR;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
//import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
//import org.openqa.selenium.remote.DesiredCapabilities;
//import org.openqa.selenium.safari.SafariDriver;
//import org.openqa.selenium.security.UserAndPassword;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.log4j.Logger;

import com.hcl.testing.selenium.base.UIConstants;


public class Keywords {

	//APP_LOGS = Logger.getLogger("devpinoyLogger");
	
	public WebDriver driver;
	public static Logger APP_LOGS;
	// properties
	public static Properties CONFIG;
	public static Properties OR;
	
	Keywords() throws IOException
	{
		// Initialize the app logs
				APP_LOGS = Logger.getLogger("devpinoyLogger");			
				APP_LOGS.debug("Hello");
				APP_LOGS.debug("Properties loaded. Starting testing");
				
				// Initialize configuration file
				FileInputStream fs = new FileInputStream(System.getProperty("user.dir")+"//src//test//resources//config//config.properties");
				CONFIG= new Properties();
				CONFIG.load(fs);
				
				// Initialize object repositories file
				fs = new FileInputStream(System.getProperty("user.dir")+"//src//test//resources//config//or.properties");
				OR= new Properties();
				OR.load(fs);
				
	}
	
	
	public String openBrowser(String object,String data){	
		System.out.println(object+data);
		
		APP_LOGS.debug("Opening browser");
		try{
		if(data.equals("Mozilla"))
		{
//			FirefoxBinary binary = new FirefoxBinary(new File("D://Users//raghuraman-k//AppData//Local//Mozilla Firefox//firefox.exe"));
//			driver = new FirefoxDriver(binary,null);
//			driver.manage().window().maximize();
//			File pathToBinary = new File("D:\\Users\\raghuraman-k\\AppData\\Local\\Mozilla Firefox\\firefox.exe");
//			FirefoxBinary ffBinary = new FirefoxBinary(pathToBinary);
//			FirefoxProfile firefoxProfile = new FirefoxProfile();     
//			driver = new FirefoxDriver(ffBinary,firefoxProfile);
//			driver.manage().window().maximize();
//			APP_LOGS.debug("Opening Mozilla browser");
			
			   
			   System.setProperty("webdriver.gecko.driver",System.getProperty("user.dir")+"\\Drivers\\geckodriver\\geckodriver.exe");
			   driver = new FirefoxDriver();
			   driver.manage().window().maximize();
			   APP_LOGS.debug("Opening Mozilla browser");
			   
			/*
			 * 
			 * 
				For IE and Firefox:
				
				diver.manage().window().maximize();
				
				For Chrome:
				
				ChromeOptions options = new ChromeOptions();
				options.addArguments("--start-maximized");
				driver = new ChromeDriver( options )
			 * 
			 * 
			 */

		}
		else if(data.equals("IE"))
		{ 
//			System.setProperty("webdriver.ie.driver", "C:\\Program Files\\Internet Explorer\\iexplore.exe");
			System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+"\\Drivers\\IEDriverServerWin32\\IEDriverServer.exe");
			driver=new InternetExplorerDriver();
			driver.manage().window().maximize();
			APP_LOGS.debug("Opening IE browser");

		}
					
		else if(data.equals("Chrome"))
		{ 

		    System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\Drivers\\chromedriver\\chromedriver.exe");
		
		 	//driver=new ChromeDriver();
		 	ChromeOptions options = new ChromeOptions();
			options.addArguments("--start-maximized");
			driver = new ChromeDriver( options );
			APP_LOGS.debug("Opening Chrome browser");
			//....
			
//			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
//			String[] switches = {"user-data-dir=D:\\Users\\raghuraman-k\\AppData\\Local\\Google\\Chrome\\User Data\\Your_Name"};
//			capabilities.setCapability("chrome.switches", switches);
//			driver = new ChromeDriver( capabilities );
			//webDriver = new RemoteWebDriver(gridServerURL, capabilities);

//			File file = new File("D:\\Selenium_Automation_Framework\\Driver\\chromedriver_win32_2.3\\chromedriver.exe");
//		    System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
		}
		
		long implicitWaitTime=Long.parseLong(CONFIG.getProperty("implicitwait"));
		driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
		return UIConstants.KEYWORD_PASS;
		}
		catch(Exception e){
			return UIConstants.KEYWORD_FAIL+" -- Not able to launch browser";
		}
		

	}
	
	public String navigate(String object,String data){		
		APP_LOGS.debug("Navigating to URL "+data);
		try{
		driver.navigate().to(data);
		}catch(Exception e){
			return UIConstants.KEYWORD_FAIL+" -- Not able to navigate";
		}
		return UIConstants.KEYWORD_PASS;
	}
	
	public String clickLink(String object,String data){
        APP_LOGS.debug("Clicking on link "+object);
        try{
        	ChooseElement(OR.getProperty(object)).click();
        	//driver.findElement(By.xpath(OR.getProperty(object))).click();
        }catch(Exception e){
			return UIConstants.KEYWORD_FAIL+" -- Not able to click on link"+e.getMessage();
        }
     
		return UIConstants.KEYWORD_PASS;
	}
	public String clickLink_linkText(String object,String data){
        APP_LOGS.debug("Clicking on link "+object);
        ChooseElement(OR.getProperty(object)).click();
        //driver.findElement(By.linkText(OR.getProperty(object))).click();
     
		return UIConstants.KEYWORD_PASS;
	}
	
	public  String verifyLinkText(String object,String data){
        APP_LOGS.debug("Verifying link Text "+object);
        try{
        	//String actual=driver.findElement(By.xpath(OR.getProperty(object))).getText();
        	String actual=ChooseElement(OR.getProperty(object)).getText();
        	String expected=data;
        	
        	if(actual.equals(expected))
        		return UIConstants.KEYWORD_PASS;
        	else
        		return UIConstants.KEYWORD_FAIL+" -- Link text not verified";
        	
        }catch(Exception e){
			return UIConstants.KEYWORD_FAIL+" -- Link text not verified"+e.getMessage();

        }
        
	}
	
	
	public  String clickButton(String object,String data){
        APP_LOGS.debug("Clicking on Button "+object);
        try{
        	ChooseElement(OR.getProperty(object)).click();
            //driver.findElement(By.xpath(OR.getProperty(object))).click();
            }catch(Exception e){
    			return UIConstants.KEYWORD_FAIL+" -- Not able to click on Button"+e.getMessage();
            }
        
        
		return UIConstants.KEYWORD_PASS;
	}
	
	public  String verifyButtonText(String object,String data){
		APP_LOGS.debug("Verifying the button text"+object);
		try{
		String actual=ChooseElement(OR.getProperty(object)).getText();
    	String expected=data;

    	if(actual.equals(expected))
    		return UIConstants.KEYWORD_PASS;
    	else
    		return UIConstants.KEYWORD_FAIL+" -- Button text not verified "+actual+" -- "+expected;
		}catch(Exception e){
			return UIConstants.KEYWORD_FAIL+" Object not found "+e.getMessage();
		}
		
	}
	
	public  String selectList(String object, String data){
		APP_LOGS.debug("Selecting from list"+object);
		try{
			
	
			APP_LOGS.debug("Selecting data from list :"+data);
			if(!data.equals(UIConstants.RANDOM_VALUE)){
				ChooseElement(OR.getProperty(object)).sendKeys(data);
			}else{
				// logic to find a random value in list
				WebElement droplist= ChooseElement(OR.getProperty(object)); 
				List<WebElement> droplist_cotents = droplist.findElements(By.tagName("option"));
				Random num = new Random();
				int index=num.nextInt(droplist_cotents.size());
				String selectedVal=droplist_cotents.get(index).getText();
				System.out.println("List Selected "+selectedVal);
				ChooseElement(OR.getProperty(object)).sendKeys(selectedVal);
				
//				Thread.sleep(3000);
//				JavascriptExecutor js = (JavascriptExecutor)driver;
//				String sText =  js.executeScript("('input[name=ecpdId]').val()").toString();
//				System.out.println("Jquery Vlaue"+sText);
				
			}
		}catch(Exception e){
			return UIConstants.KEYWORD_FAIL +" - Could not select from list. "+ e.getMessage();	

		}
		
		return UIConstants.KEYWORD_PASS;	
	}
	
	public String verifyAllListElements(String object, String data){
		APP_LOGS.debug("Verifying the selection of the list"+object);
	try{	
		APP_LOGS.debug("Selecting data from list :"+data);
		WebElement droplist= ChooseElement(OR.getProperty(object)); 
		List<WebElement> droplist_cotents = droplist.findElements(By.tagName("option"));
		
		// extract the expected values from OR. properties
		String temp=data;
		String allElements[]=temp.split(",");
		// check if size of array == size if list
		if(allElements.length != droplist_cotents.size())
			return UIConstants.KEYWORD_FAIL +"- size of lists do not match";	
		
		for(int i=0;i<droplist_cotents.size();i++){
			if(!allElements[i].equals(droplist_cotents.get(i).getText())){
					return UIConstants.KEYWORD_FAIL +"- Element not found - "+allElements[i];
			}
		}
	}catch(Exception e){
		return UIConstants.KEYWORD_FAIL +" - Could not select from list. "+ e.getMessage();	

	}
		
		
		return UIConstants.KEYWORD_PASS;	
	}
	
	public  String verifyListSelection(String object,String data){
		APP_LOGS.debug("Verifying all the list elements"+object);
		try{
			String expectedVal=data;
			//System.out.println(driver.findElement(By.xpath(OR.getProperty(object))).getText());
			WebElement droplist= ChooseElement(OR.getProperty(object)); 
			List<WebElement> droplist_cotents = droplist.findElements(By.tagName("option"));
			String actualVal=null;
			for(int i=0;i<droplist_cotents.size();i++){
				String selected_status=droplist_cotents.get(i).getAttribute("selected");
				if(selected_status!=null)
					actualVal = droplist_cotents.get(i).getText();			
				}
			
			if(!actualVal.equals(expectedVal))
				return UIConstants.KEYWORD_FAIL + "Value not in list - "+expectedVal;

		}catch(Exception e){
			return UIConstants.KEYWORD_FAIL +" - Could not find list. "+ e.getMessage();	

		}
		return UIConstants.KEYWORD_PASS;	

	}
	public String switchwindow(String object, String data){
		
		APP_LOGS.debug("Switching Window "+object);
		int i=1;
        try {

        String winHandleBefore = driver.getWindowHandle();
        System.out.println(" Switch to Window"+i+" : "+winHandleBefore);
        for(String winHandle : driver.getWindowHandles()){
        	i=1+1;
            driver.switchTo().window(winHandle);
            System.out.println(" Switch to Window"+i+" : "+winHandle);
            //driver.get("http://admin:admin@Example.com");
    
        }
        }catch(Exception e){
        return UIConstants.KEYWORD_FAIL+ "Unable to Switch Window" + e.getMessage();
        }
        return UIConstants.KEYWORD_PASS;
        }
	

	public String switchwindowback(String object, String data){
		APP_LOGS.debug("Switching back Window "+object);
        try {
            String winHandleBefore = driver.getWindowHandle();
            System.out.println(" Switch Back :"+winHandleBefore);
            driver.close(); 
            //Switch back to original browser (first window)
            driver.switchTo().window(winHandleBefore);
            //continue with original browser (first window)
            
        }catch(Exception e){
        return UIConstants.KEYWORD_FAIL+ "Unable to Switch to main window" + e.getMessage();
        }
        return UIConstants.KEYWORD_PASS;
        }
	public  String WebAuthentication(String username,String password)
	{
		APP_LOGS.debug("WebAuthentication "+username);
		 try {
		System.out.println(" Web Alet Value :"+username+password);
		WebDriverWait wait = new WebDriverWait(driver, 10);      
		Alert alert = wait.until(ExpectedConditions.alertIsPresent()); 
		System.out.println(" Web Alet present: "+alert);
		//alert.authenticateUsing(new UserAndPassword(username, password));
		driver.get("http://admin:admin@Example.com");
		 }
		 catch(Exception e)
		 {
		   return UIConstants.KEYWORD_FAIL+ "Unable to Switch to main window" + e.getMessage();
		 }
		 return UIConstants.KEYWORD_PASS;
	}
	
	public void WebSendkeyEvent(String Keytext)
	{
		
		APP_LOGS.debug("Send Action key "+Keytext);
	Actions action = new Actions(driver);
    action.sendKeys(Keytext);
    action.perform();
    
    
	}
	public void WebEnterSendkeys()
	{
		APP_LOGS.debug("Send Action ENTER key ");
	Actions action = new Actions(driver);
    action.sendKeys(Keys.ENTER);
    action.perform();
	}
	public void WebTabSendkeys()
	{
		APP_LOGS.debug("Send Action TAB key ");
	Actions action = new Actions(driver);
    action.sendKeys(Keys.TAB);
    action.perform();
	}
	
	public void AlertAuthentication(String AuthUsername, String AuthPassword) throws InterruptedException, AWTException
	{
		
		APP_LOGS.debug("WebAuthentication "+AuthUsername);
		 Thread.sleep(5000);
     	System.out.println("Robot");
         //create robot for keyboard operations
         Robot rb = new Robot();

         //Enter user name by ctrl-v
         StringSelection username = new StringSelection(AuthUsername);
         Toolkit.getDefaultToolkit().getSystemClipboard().setContents(username, null);            
         rb.keyPress(KeyEvent.VK_CONTROL);
         rb.keyPress(KeyEvent.VK_V);
         rb.keyRelease(KeyEvent.VK_V);
         rb.keyRelease(KeyEvent.VK_CONTROL);

         //tab to password entry field
         rb.keyPress(KeyEvent.VK_TAB);
         rb.keyRelease(KeyEvent.VK_TAB);
         Thread.sleep(2000);

         //Enter password by ctrl-v
         StringSelection pwd = new StringSelection(AuthPassword);
         Toolkit.getDefaultToolkit().getSystemClipboard().setContents(pwd, null);
         rb.keyPress(KeyEvent.VK_CONTROL);
         rb.keyPress(KeyEvent.VK_V);
         rb.keyRelease(KeyEvent.VK_V);
         rb.keyRelease(KeyEvent.VK_CONTROL);

         //press enter
         rb.keyPress(KeyEvent.VK_ENTER);
         rb.keyRelease(KeyEvent.VK_ENTER);

         //wait
         Thread.sleep(5000);
	}
	public  String getListSelection(String object,String data){
		APP_LOGS.debug("Verifying all the list elements : "+object);
		String actualVal=null;
		try{
			WebElement droplist= ChooseElement(OR.getProperty(object)); 
			List<WebElement> droplist_cotents = droplist.findElements(By.tagName("option"));
			
			for(int i=0;i<droplist_cotents.size();i++){
				String selected_status=droplist_cotents.get(i).getAttribute("selected");
				if(selected_status!=null)
					actualVal = droplist_cotents.get(i).getText();			
				}
			
		}catch(Exception e){
			return UIConstants.KEYWORD_FAIL +" - Could not find list. "+ e.getMessage();	

		}
		return actualVal;	

	}
	
//	public  String selectRadio(String object, String data){
//		//Check and rewrite
//		APP_LOGS.debug("Selecting a radio button :"+object);
//		try{
//			String temp[]=object.split(Constants.DATA_SPLIT);
//			driver.findElement(By.xpath(OR.getProperty(temp[0])+data+OR.getProperty(temp[1]))).click();
//			
//		}catch(Exception e){
//			return Constants.KEYWORD_FAIL +"- Not able to find radio button";	
//		}
//		
//		return Constants.KEYWORD_PASS;	
//
//	}
	
	public  String selectRadio(String object, String data){
		//Check and rewrite
		//ChooseElements
		try
		{
			String sRadioValue,sRadioChecked;
			
			List<WebElement> radioButton = ChooseElements(OR.getProperty(object));
			//List<WebElement> radioButton =driver.findElements(By.name("platform"));
		    System.out.println("Radio Size: "+radioButton.size());
		    //If u want to select the radio button
		   // driver.findElement(By.id("radios-1")).click();
		   // Thread.sleep(3000);
		    //If u want the Text in U R console
		    for(int i=0;i<radioButton.size();i++){
		    	System.out.println("Radio Text : "+radioButton.get(i).getText());
		    	System.out.println("Radio Value : "+radioButton.get(i).getAttribute("value"));
		    	sRadioValue=radioButton.get(i).getAttribute("value");
		    	if (data.equalsIgnoreCase(sRadioValue))
		    	{
		    		sRadioChecked=radioButton.get(i).getAttribute("checked");
		    		if(sRadioChecked==null)
		    		{
		    			radioButton.get(i).click();
		    			return UIConstants.KEYWORD_PASS;	
		    		}
		    		else
		    		{
		    			return UIConstants.KEYWORD_PASS;	
		    			
		    		}
		    	}
		    } 
		}
		catch(Exception e){
			return UIConstants.KEYWORD_FAIL +"- Not able to find radio button";	

		}
		return UIConstants.KEYWORD_FAIL; 
	}
	
	public  String verifyRadioSelected(String object, String data){
		//Check and rewrite
		APP_LOGS.debug("Verify Radio Selected "+object);
		try{
			String temp[]=object.split(UIConstants.DATA_SPLIT);
			String checked=driver.findElement(By.xpath(OR.getProperty(temp[0])+data+OR.getProperty(temp[1]))).getAttribute("checked");
			if(checked==null)
				return UIConstants.KEYWORD_FAIL+"- Radio not selected";	

				
		}catch(Exception e){
			return UIConstants.KEYWORD_FAIL +"- Not able to find radio button";	

		}
		
		return UIConstants.KEYWORD_PASS;	

	}
	
	
	public  String checkCheckBox(String object,String data){
		APP_LOGS.debug("Checking checkbox : "+object);
		try{
			// true or null
			String checked=ChooseElement(OR.getProperty(object)).getAttribute("checked");
			if(checked==null)// checkbox is unchecked
				driver.findElement(By.xpath(OR.getProperty(object))).click();
		}catch(Exception e){
			return UIConstants.KEYWORD_FAIL+" - Could not find checkbox";
		}
		return UIConstants.KEYWORD_PASS;
		
	}
	
	
	
	public  String checkCheckBox1(String object, String data){
		//Check and rewrite
		//ChooseElements
		try
		{
			String sChkValue,sChecked;
			
			List<WebElement> oCheckBox = ChooseElements(OR.getProperty(object));
			//List<WebElement> radioButton =driver.findElements(By.name("platform"));
		    System.out.println("Check Size: "+oCheckBox.size());
		    //If u want to select the radio button
		   // driver.findElement(By.id("radios-1")).click();
		   // Thread.sleep(3000);
		    //If u want the Text in U R console
		    for(int i=0;i<oCheckBox.size();i++){
		    	System.out.println("Check Value checked : "+oCheckBox.get(i).getAttribute("checked"));
		    	System.out.println("Check Value : "+oCheckBox.get(i).getAttribute("value"));
		    	sChkValue=oCheckBox.get(i).getAttribute("value");
		    	if (data.equalsIgnoreCase(sChkValue))
		    	{
		    		sChecked=oCheckBox.get(i).getAttribute("checked");
		    		if(sChecked==null)
		    		{
		    			oCheckBox.get(i).click();
		    			return UIConstants.KEYWORD_PASS;	
		    		}
		    		else
		    		{
		    			return UIConstants.KEYWORD_PASS;	
		    			
		    		}
		    	}
		    } 
		}
		catch(Exception e){
			return UIConstants.KEYWORD_FAIL +"- Not able to find radio button";	

		}
		return UIConstants.KEYWORD_FAIL; 
	}
	
	
	public  String UncheckCheckBox1(String object, String data){
		//Check and rewrite
		//ChooseElements
		try
		{
			String sChkValue,sChecked;
			
			List<WebElement> oCheckBox = ChooseElements(OR.getProperty(object));
			//List<WebElement> radioButton =driver.findElements(By.name("platform"));
		    System.out.println("Check Size: "+oCheckBox.size());
		    //If u want to select the radio button
		   // driver.findElement(By.id("radios-1")).click();
		   // Thread.sleep(3000);
		    //If u want the Text in U R console
		    for(int i=0;i<oCheckBox.size();i++){
		    	System.out.println("Check Value checked : "+oCheckBox.get(i).getAttribute("checked"));
		    	System.out.println("Check Value : "+oCheckBox.get(i).getAttribute("value"));
		    	sChkValue=oCheckBox.get(i).getAttribute("value");
		    	if (data.equalsIgnoreCase(sChkValue))
		    	{
		    		sChecked=oCheckBox.get(i).getAttribute("checked");
		    		if(sChecked!=null)
		    		{
		    			oCheckBox.get(i).click();
		    			return UIConstants.KEYWORD_PASS;	
		    		}
		    		else
		    		{
		    			return UIConstants.KEYWORD_PASS;	
		    			
		    		}
		    	}
		    } 
		}
		catch(Exception e){
			return UIConstants.KEYWORD_FAIL +"- Not able to find radio button";	

		}
		return UIConstants.KEYWORD_FAIL; 
	}
	
	public String unCheckCheckBox(String object,String data){
		APP_LOGS.debug("Unchecking checkBox :"+object);
		try{
			String checked=ChooseElement(OR.getProperty(object)).getAttribute("checked");
			if(checked!=null)
				driver.findElement(By.xpath(OR.getProperty(object))).click();
		}catch(Exception e){
			return UIConstants.KEYWORD_FAIL+" - Could not find checkbox";
		}
		return UIConstants.KEYWORD_PASS;
		
	}
	
	
	public  String verifyCheckBoxSelected(String object,String data){
		APP_LOGS.debug("Verifying checkbox selected :"+object);
		try{
			String checked=ChooseElement(OR.getProperty(object)).getAttribute("checked");
			if(checked!=null)
				return UIConstants.KEYWORD_PASS;
			else
				return UIConstants.KEYWORD_FAIL + " - Not selected";
			
		}catch(Exception e){
			return UIConstants.KEYWORD_FAIL+" - Could not find checkbox";

		}
		
		
	}
	
	public String verifyText(String object, String data){
		APP_LOGS.debug("Verifying the text "+object);
		try{
			String actual=ChooseElement(OR.getProperty(object)).getText();
	    	String expected=data;

	    	if(actual.equals(expected))
	    		return UIConstants.KEYWORD_PASS;
	    	else
	    		return UIConstants.KEYWORD_FAIL+" -- text not verified "+actual+" -- "+expected;
			}catch(Exception e){
				return UIConstants.KEYWORD_FAIL+" Object not found "+e.getMessage();
			}
		
	}
	
	public  String writeInInput(String object,String data){
		APP_LOGS.debug("Writing in text box : "+object);
		try{
			//driver.findElement(By.LocatorStrategy(OR.getProperty(object))).sendKeys(data);
//			String ExistingValue=this.getVal(object,data);
			System.out.println(" OR Value"+OR.getProperty(object));
			ChooseElement(OR.getProperty(object)).clear();;
			ChooseElement(OR.getProperty(object)).sendKeys(data);
		//	driver.findElement(By.id("username")).sendKeys("Sales");
		}catch(Exception e){
			return UIConstants.KEYWORD_FAIL+" Unable to write "+e.getMessage();

		}
		return UIConstants.KEYWORD_PASS;
		
	}
	
	 public String getVal(String object,String data) {
		 APP_LOGS.debug("Get attribute value :"+object);
			String RetnString=null;
			try
			{
	        RetnString=ChooseElement(OR.getProperty(object)).getAttribute("value");
	        System.out.println(" Attr value: "+RetnString);
			}
			catch(Exception e){
				return UIConstants.KEYWORD_FAIL+" Unable to write "+e.getMessage();
			}
	        
//	        String ss="$('input[name$='ecpdId']').val()";
//	        System.out.println(" value: "+ss);
//	        
//	        JavascriptExecutor js = (JavascriptExecutor)driver;
//	        
//	        String sText =  js.executeScript("return document.documentElement.innerText;").toString();
//	        String sText2 =  js.executeScript("$('input[name$='ecpdId']').val()").toString();
//	        String sText3 =  js.executeScript("return document.title;").toString();
//	        System.out.println(" value:  "+sText2+" : "+sText3);
//	        
	        
//	        RetnString=(String) js.executeScript(String.format("return $('#%s').val();",ChooseElement(OR.getProperty(object)).getAttribute("name")));
//	        
//	        
//	        if (driver instanceof JavascriptExecutor) {
//	            ((JavascriptExecutor)driver).executeScript("yourScript();");
//	        } else {
//	         
//	        	throw new IllegalStateException("This driver does not support JavaScript!");
//	        }
//		 	JavascriptExecutor js = (JavascriptExecutor)driver;
//			String sText =  js.executeScript("('name=ecpdId').val()").toString();
//			System.out.println(" JQ value: "+sText);
//	        JavascriptExecutor js = (JavascriptExecutor) driver;
//	        RetnString=(String) js.executeScript(String.format("return $('#%s').val();",ChooseElement(OR.getProperty(object)).getAttribute("name")));
//	        System.out.println(" JQ Retun: "+RetnString);
	        return RetnString;
	}
	 
	 public String getText(String object,String data) {
		 APP_LOGS.debug("Get text value : "+object);
			String RetnString=null;
			try
			{
	        RetnString=ChooseElement(OR.getProperty(object)).getText();
	        System.out.println(" Gettext value: "+RetnString);
			}
			catch(Exception e){
				return UIConstants.KEYWORD_FAIL+" Unable to write "+e.getMessage();
			}
			
	        return RetnString;
	}
	 
	 
	 public String getDivVal(String object1,String object2,int row) {
		 APP_LOGS.debug("Get div value : "+object1);
			String RetnString=null;
			String xpathvalue=null;
			System.out.println(" Input  value: "+object1+" : "+object2+" : "+row);
			
			try
			{
			String ORProperty1=OR.getProperty(object1);
			String temp1[]=ORProperty1.split(UIConstants.DATA_SPLIT);
	       // String byStrategy1=temp1[0];
	        String locatorValue1=temp1[1];
	        
	    	String ORProperty2=OR.getProperty(object2);
			String temp2[]=ORProperty2.split(UIConstants.DATA_SPLIT);
	        //String byStrategy2=temp2[0];
	        String locatorValue2=temp2[1];
	        xpathvalue=locatorValue1+"["+row+"]"+locatorValue2;
	        System.out.println(" xpathvalue : "+xpathvalue);
	        
	        RetnString=driver.findElement(By.xpath(xpathvalue)).getText();
	        System.out.println(" Get text value: "+RetnString);
			}
			catch(Exception e){
				return UIConstants.KEYWORD_FAIL+" Unable to write "+e.getMessage();
			}
	        return RetnString;
	}
	public WebElement ChooseElement(String byStrgylocValue) {
        By by = null;
        //System.out.println(byStrgylocValue);
        
   
        if (byStrgylocValue.contains("|"))
        {
        	
	        String temp[]=byStrgylocValue.split(UIConstants.DATA_SPLIT);
	        String byStrategy=temp[0];
//	        System.out.println(temp[0]);
//	        System.out.println(temp[1]);
	        String locatorValue=temp[1];
	        switch (byStrategy) {
	        case "ID":
	            by = By.id(locatorValue);
	            break;
	        case "NAME":
	            by = By.name(locatorValue);
	            break;
	        case "CLASS":
	            by = By.className(locatorValue);
	            break;
	        case "LINKTEXT":
	            by = By.linkText(locatorValue);
	            break;
	        case "XPATH":
	            by = By.xpath(locatorValue);
	            break;
	        case "CSS":
	            by = By.cssSelector(locatorValue);
	            break;
	        case "TAGNAME":
	            by = By.tagName(locatorValue);
	            break;
        }
        }
        return driver.findElement(by);
    }

	
	public List<WebElement> ChooseElements(String byStrgylocValue) {
        By by = null;
//       System.out.println(byStrgylocValue);
//        
        if (byStrgylocValue.contains("|"))
        {
	        String temp[]=byStrgylocValue.split(UIConstants.DATA_SPLIT);
	        String byStrategy=temp[0];
//	        System.out.println(temp[0]);
//	        System.out.println(temp[1]);
	        String locatorValue=temp[1];
	        switch (byStrategy) {
	        case "ID":
	            by = By.id(locatorValue);
	            break;
	        case "NAME":
	            by = By.name(locatorValue);
	            break;
	        case "CLASS":
	            by = By.className(locatorValue);
	            break;
	        case "LINKTEXT":
	            by = By.linkText(locatorValue);
	            break;
	        case "XPATH":
	            by = By.xpath(locatorValue);
	            break;
	        case "CSS":
	            by = By.cssSelector(locatorValue);
	            break;
	        case "TAGNAME":
	            by = By.tagName(locatorValue);
	            break;
        }
        }
        return driver.findElements(by);
    }
	
	public  String verifyTextinInput(String object,String data){
       APP_LOGS.debug("Verifying the text in input box :"+object);
       try{
    	   //By OR.getProperty(object)
			String actual = ChooseElement(OR.getProperty(object)).getAttribute("value");
			String expected=data;

			if(actual.equals(expected)){
				return UIConstants.KEYWORD_PASS;
			}else{
				return UIConstants.KEYWORD_FAIL+" Not matching ";
			}
			
		}catch(Exception e){
			return UIConstants.KEYWORD_FAIL+" Unable to find input box "+e.getMessage();

		}
	}
	
	public  String clickImage(){
	       APP_LOGS.debug("Clicking the image ");
			
			return UIConstants.KEYWORD_PASS;
	}
	
	public  String verifyFileName(){
	       APP_LOGS.debug("Verifying inage filename");
			
			return UIConstants.KEYWORD_PASS;
	}
	
	
	
	
	public  String verifyTitle(String object, String data){
	       APP_LOGS.debug("Verifying title : "+object);
	       try{
	    	   String actualTitle= driver.getTitle();
	    	   String expectedTitle=data;
	    	   if(actualTitle.equals(expectedTitle))
		    		return UIConstants.KEYWORD_PASS;
		    	else
		    		return UIConstants.KEYWORD_FAIL+" -- Title not verified "+expectedTitle+" -- "+actualTitle;
			   }catch(Exception e){
					return UIConstants.KEYWORD_FAIL+" Error in retrieving title";
			   }		
	}
	
	public String exist(String object,String data){
	       APP_LOGS.debug("Checking existance of element"+object);
	       try{
	    	   ChooseElement(OR.getProperty(object));
			   }catch(Exception e){
					return UIConstants.KEYWORD_FAIL+" Object doest not exist";
			  }
	       
	       
			return UIConstants.KEYWORD_PASS;
	}
	
	public  String click(String object,String data){
	      APP_LOGS.debug("Clicking on any element :"+object);
	       try{
	    	   ChooseElement(OR.getProperty(object)).click();
			   }catch(Exception e){
					return UIConstants.KEYWORD_FAIL+" Not able to click";
			  }
			return UIConstants.KEYWORD_PASS;
	}
	
	public  String clickMultiple(String object,String data)
	{
		 APP_LOGS.debug("Clicking on any element :"+object);
		 try{
			String ORProperty=OR.getProperty(object);
			
			String temp[]=ORProperty.split(UIConstants.DATA_SPLIT);
	        //String byStrategy=temp[0];
	        String locatorValue=temp[1];
			List<WebElement> ClickWebElmnt = driver.findElements(By.xpath(locatorValue));
			for(int i=0;i<ClickWebElmnt.size();i++)
			{
				boolean Displayed= ClickWebElmnt.get(i).isDisplayed();
				if (Displayed==true)
				{
					ClickWebElmnt.get(i).click();
					return UIConstants.KEYWORD_PASS;
				}
	
			}
		  }catch(Exception e){
				return UIConstants.KEYWORD_FAIL+" Not able to click";
		  }
	return UIConstants.KEYWORD_PASS;
	}
	
	
	 public String VerifyWebtable(String object,Hashtable<String, String> hdata) {
		 
		 APP_LOGS.debug("Verify data in WebTable :" +object);
		 String RetnResult=null;
		 try{
		
			  //To locate table.
			  WebElement mytable = ChooseElement(OR.getProperty(object));
			  //To locate rows of table.
			  List<WebElement> rows_table = mytable.findElements(By.tagName("tr"));
			  //To calculate no of rows In table.
			  int rows_count = rows_table.size();
			  
			  //Loop will execute till the last row of table.
			  for (int row=0; row<rows_count; row++){
			   //To locate columns(cells) of that specific row.
			   List<WebElement> Columns_row = rows_table.get(row).findElements(By.tagName("td"));
			   //To calculate no of columns(cells) In that specific row.
			   int columns_count = Columns_row.size();
			   //System.out.println("Number of cells In Row "+row+" are "+columns_count);
			   
			   //Loop will execute till the last cell of that specific row.
			   for (int column=0; column<columns_count; column++){
			    //To retrieve text from that specific cell.
			    String celtext = Columns_row.get(column).getText();
			    //System.out.println("Cell Value Of row number "+row+" and column number "+column+" Is "+celtext);
			    boolean vFlag1 = hdata.containsValue(celtext);
			    if(vFlag1==true)
			    {
			    	System.out.println("Matched cell Value Of row number "+row+" and column number "+column+" Is "+celtext);
			    	RetnResult=RetnResult+"\n </br> "+celtext;
			    	
			    }
	 
			   }
			  // System.out.println("--------------------------------------------------");
		  }
		 }
		 catch(Exception e){
				return RetnResult;
		  }
		  return RetnResult;
	 }
	 

	 public String VerifyWebtable(String object,String data) {
		 
		 APP_LOGS.debug("Verify data in WebTable : "+object);
		 String RetnResult=null;  
		 try
		 {
		  //To locate table.
		  WebElement mytable = ChooseElement(OR.getProperty(object));
		  //To locate rows of table.
		  List<WebElement> rows_table = mytable.findElements(By.tagName("tr"));
		  //To calculate no of rows In table.
		  int rows_count = rows_table.size();
		  
		  //Loop will execute till the last row of table.
		  for (int row=0; row<rows_count; row++){
		   //To locate columns(cells) of that specific row.
		   List<WebElement> Columns_row = rows_table.get(row).findElements(By.tagName("td"));
		   //To calculate no of columns(cells) In that specific row.
		   int columns_count = Columns_row.size();
		   //System.out.println("Number of cells In Row "+row+" are "+columns_count);
		   
		   //Loop will execute till the last cell of that specific row.
		   for (int column=0; column<columns_count; column++){
		    //To retrieve text from that specific cell.
		    String celtext = Columns_row.get(column).getText();
		    //System.out.println("Cell Value Of row number "+row+" and column number "+column+" Is "+celtext);

		    if(data.equalsIgnoreCase(celtext))
		    {
		    	RetnResult=RetnResult+"\n </br> "+celtext;
	   			 System.out.println("Selected Element in Cell Value Of row number "+row+" and column number "+column+" Is "+celtext);
	   			//System.out.println("Columns_row.get(column).getText "+Columns_row.get(column).findElement(By.xpath(locatorValue)).getText());
	   			//Columns_row.get(column).click();
	   			return UIConstants.KEYWORD_PASS;
		    }

		   }
		  // System.out.println("--------------------------------------------------");
		  }
		 }
		  catch(Exception e){
			  return UIConstants.KEYWORD_FAIL+" Error in webtable verification"+e.getMessage();
		  	}
		  return UIConstants.KEYWORD_FAIL+" Error in webtable verification";
	 }

	 public String SelectElementWebtable(String object,Hashtable<String, String> hdata, int ObjectColnum,String Tableobject) {
		 
		 APP_LOGS.debug("Verify data in WebTable : "+object);
		 String RetnResult=null;
		 int VerifiedRow=0;
		 try
		 {
		 String ORProperty=OR.getProperty(Tableobject);
			String temp[]=ORProperty.split(UIConstants.DATA_SPLIT);
	      //  String byStrategy=temp[0];
	        String locatorValue=temp[1];
	        
		  //To locate table.
		  WebElement mytable = ChooseElement(OR.getProperty(object));
		  //To locate rows of table.
		  List<WebElement> rows_table = mytable.findElements(By.tagName("tr"));
		  //To calculate no of rows In table.
		  int rows_count = rows_table.size();
		  
		  //Loop will execute till the last row of table.
		  for (int row=0; row<rows_count; row++){
		   //To locate columns(cells) of that specific row.
		   List<WebElement> Columns_row = rows_table.get(row).findElements(By.tagName("td"));
		   //To calculate no of columns(cells) In that specific row.
		   int columns_count = Columns_row.size();
		   //System.out.println("Number of cells In Row "+row+" are "+columns_count);
		   
		   //Loop will execute till the last cell of that specific row.
		   for (int column=0; column<columns_count; column++){
		    //To retrieve text from that specific cell.
		    String celtext = Columns_row.get(column).getText();
		    //System.out.println("Cell Value Of row number "+row+" and column number "+column+" Is "+celtext);
		    boolean vFlag1 = hdata.containsValue(celtext);
		    if(vFlag1==true)
		    {
		    	
		    	//System.out.println("Matched cell Value Of row number "+row+" and column number "+column+" Is "+celtext);
		    	RetnResult=RetnResult+"\n </br> "+celtext;
		    	VerifiedRow=VerifiedRow+1;
		    	//System.out.println("VerifiedRow "+VerifiedRow);
		    	
		    }
		    
		    if (VerifiedRow>2)
	    	{
	    	 	//System.out.println("ObjectColnum "+ObjectColnum);
		    	//System.out.println("column "+column);
	    		if(ObjectColnum==column)
	    		{
	    			int Currentrow=row+1;
	    			 System.out.println("Selected Element in Cell Value Of row number "+row+" and column number "+column+" Is "+celtext);
	    			//System.out.println("Columns_row.get(column).getText "+Columns_row.get(column).findElement(By.xpath(locatorValue)).getText());
	    			//Columns_row.get(column).click();
	    			String odj="//tr["+Currentrow+"]/td["+ObjectColnum+"]"+locatorValue;
	    			System.out.println("lOCATOR "+odj);
	    			Columns_row.get(column).findElement(By.xpath(odj)).click();
	    			return RetnResult;
	    		}
	    		
	    	}
 
		   }
		  // System.out.println("--------------------------------------------------");
		  }
		 }
		  catch(Exception e){
			  return RetnResult;
		  	}
		  return RetnResult;
	 }
	 
	 

	 public String SelectElementWebtableFromRowValue(String object,String RowValue, int ObjectColnum,String Tableobject,String data) {
		 
		 APP_LOGS.debug("Verify data in WebTable :"+object);
		 String FnRetnResult=null;
		 
		 int Currentrow=0;
		 try
		 {
		  //To locate table.
		  WebElement mytable = ChooseElement(OR.getProperty(object));
		  //To locate rows of table.
		  List<WebElement> rows_table = mytable.findElements(By.tagName("tr"));
		  //To calculate no of rows In table.
		  int rows_count = rows_table.size();
		  
		  //Loop will execute till the last row of table.
		  for (int row=0; row<rows_count; row++){
		   //To locate columns(cells) of that specific row.
		   List<WebElement> Columns_row = rows_table.get(row).findElements(By.tagName("td"));
		   //To calculate no of columns(cells) In that specific row.
		   int columns_count = Columns_row.size();
		   //System.out.println("Number of cells In Row "+row+" are "+columns_count);
		   
		   //Loop will execute till the last cell of that specific row.
		   for (int column=0; column<columns_count; column++){
		    //To retrieve text from that specific cell.
		    String celtext = Columns_row.get(column).getText();
		    System.out.println("Cell Value Of row number "+row+" and column number "+column+" Is "+celtext);

		    if(RowValue.equalsIgnoreCase(celtext))
		    {
		    	
		    	String odj;
		    	
		    	
		    	String ORProperty=OR.getProperty(Tableobject);
				String temp[]=ORProperty.split(UIConstants.DATA_SPLIT);
		       // String byStrategy=temp[0];
		        String locatorValue=temp[1];

	   			 System.out.println("Selected Element in Cell Value Of row number "+row+" and column number "+column+" Is "+celtext);
	   			//System.out.println("Columns_row.get(column).getText "+Columns_row.get(column).findElement(By.xpath(locatorValue)).getText());
	   			//Columns_row.get(column).click();
	   			FnRetnResult=FnRetnResult+"\n </br>  "+celtext;

	   			String ObjectType=StringUtils.substring(Tableobject, 0, 3);
				 switch(ObjectType.toLowerCase())
				  {
				  case "txt":
					  Currentrow=row;
			   			odj="//tr["+Currentrow+"]/td["+ObjectColnum+"]"+locatorValue;
			   			System.out.println("lOCATOR : "+odj);
			   			FnRetnResult=FnRetnResult+"\n </br> The provided value of "+celtext+" is "+data;
			   			Columns_row.get(column).findElement(By.xpath(odj)).sendKeys(data);
					  break;
				  case "lst":
						Currentrow=row;
						odj="//tr["+Currentrow+"]/td["+ObjectColnum+"]"+locatorValue;
			   			System.out.println("lOCATOR : "+odj);
			   			FnRetnResult=FnRetnResult+"\n </br> The provided value of "+celtext+" is "+data;
			   			Select Selectby=new Select(Columns_row.get(column).findElement(By.xpath(odj)));			   			
			   			Selectby.selectByVisibleText(data);	
			   			break;
				  case "btn":
					  if (data.equalsIgnoreCase("current"))//For some row +1 and for some row
					  {
						  Currentrow=row;
					  }
					  else
					  {
						  Currentrow=row+1;
					  }
			   			odj="//tr["+Currentrow+"]/td["+ObjectColnum+"]"+locatorValue;
			   			System.out.println("lOCATOR : "+odj);
					  Columns_row.get(column).findElement(By.xpath(odj)).click();
					  break;
				  }
				
	   			return FnRetnResult;
		    }

		   }
		  // System.out.println("--------------------------------------------------");
		  }
		 }
		  catch(Exception e){
			  return FnRetnResult;
		  	}
		  return FnRetnResult;
	 }
	 
	 
 public String ClickDivElementFromRowValue(String ListDivObj,String VerifyValueinRow, String SetObjinVerifiedRow) {
		 
		 APP_LOGS.debug("Verify data in DIV Tag :"+ListDivObj);

		 int Verifiedrow=-1;
		 try
		 {
		  //To locate table.		 
    	String PORProperty=OR.getProperty(ListDivObj);
		String pArr[]=PORProperty.split(UIConstants.DATA_SPLIT);
		//String bypDivStrategy=pArr[0];
		String ListPDivlocatorValue=pArr[1];
		        
		String SORProperty=OR.getProperty(SetObjinVerifiedRow);
		String sArr[]=SORProperty.split(UIConstants.DATA_SPLIT);
		String bySDivStrategy=sArr[0];
		String ListSDivlocatorValue=sArr[1];
	
		  //To locate rows of table.
		  List<WebElement> ListPrDivElmt = driver.findElements(By.xpath(ListPDivlocatorValue));
		  //To calculate no of rows In table.
		  int rows_count = ListPrDivElmt.size();
		  System.out.println("rows_count:  "+rows_count);
		  //Loop will execute till the last row of table.
		  String sPrimaryText,sSecondTxt,sSelectObjText;
		  for (int row=0; row<rows_count; row++)
		  {
			  sPrimaryText= ListPrDivElmt.get(row).getText();
			  System.out.println("sPrimaryText:  "+row+" : "+sPrimaryText);
			  List<WebElement> ListPrDivElmt2 = ListPrDivElmt.get(row).findElements(By.tagName("div"));
			  System.out.println("Bytag Row Num:  "+ListPrDivElmt2.size());
			  for (int Secrow=0; Secrow<ListPrDivElmt2.size(); Secrow++)
			  {
				  sSecondTxt= ListPrDivElmt2.get(Secrow).getText();
				  System.out.println("sSecondTxt:  "+Secrow+" : "+sSecondTxt);
				  if(sSecondTxt.equalsIgnoreCase(VerifyValueinRow))
				  {
					Verifiedrow=row;
			  		System.out.println("Verified sSecondTxt:  "+sSecondTxt);
			    }
				  if (row==Verifiedrow)
				  {
			
					System.out.println("Get Att bySDivStrategy:  "+bySDivStrategy);
					sSelectObjText= ListPrDivElmt2.get(Secrow).getAttribute(bySDivStrategy);
					System.out.println("Attr sSelectObjText:  "+row+" : "+Secrow+" : "+sSelectObjText);
					if (sSelectObjText!=null)
					{
					  if (sSelectObjText.equalsIgnoreCase(ListSDivlocatorValue))
					  {
						  System.out.println("Verified Attr sSelectObjText:  "+row+" : "+Secrow+" : "+sSelectObjText);
						  
			   			String ObjectType=StringUtils.substring(SetObjinVerifiedRow, 0, 3);
						 switch(ObjectType.toLowerCase())
						  {
						  case "btn":
							  ListPrDivElmt2.get(Secrow).click();
							  Verifiedrow=-1;
							  return UIConstants.KEYWORD_PASS;
							  //break;
						  }

					
					  }
				  }
					  
					  
				  }
			  }


		   }
		 }
		  catch(Exception e){
			  return UIConstants.KEYWORD_FAIL+" Error in webtable verification"+e.getMessage();
		  	}
	
		  return UIConstants.KEYWORD_FAIL;
	 }
	 
 
 
 public String VerifyDivElementFromHashTree(String ListDivObj, Hashtable<String, String> hdata) {
	 
	 APP_LOGS.debug("Verify data in Div Tag : "+ListDivObj);
	 
	 String sPrimaryText,sSecondTxt;
	 int Verifiedrow=0;
	 String RetnResult=null;
	 try
	 {
	//To locate table.		 
	String PORProperty=OR.getProperty(ListDivObj);
	String pArr[]=PORProperty.split(UIConstants.DATA_SPLIT);
	//String bypDivStrategy=pArr[0];
	String ListPDivlocatorValue=pArr[1];
	        
	  //To locate rows of table.
	  List<WebElement> ListPrDivElmt = driver.findElements(By.xpath(ListPDivlocatorValue));
	  //To calculate no of rows In table.
	  int rows_count = ListPrDivElmt.size();
	  System.out.println("rows_count:  "+rows_count);
	  //Loop will execute till the last row of table.

	  for (int row=0; row<rows_count; row++)
	  {
		  sPrimaryText= ListPrDivElmt.get(row).getText();
		  System.out.println("sPrimaryText:  "+row+" : "+sPrimaryText);
		  List<WebElement> ListPrDivElmt2 = ListPrDivElmt.get(row).findElements(By.tagName("div"));
		  System.out.println("Bytag Row Num:  "+ListPrDivElmt2.size());
		  
		  for (int Secrow=0; Secrow<ListPrDivElmt2.size(); Secrow++)
		  {
			  sSecondTxt= ListPrDivElmt2.get(Secrow).getText();
			  System.out.println("sSecondTxt:  "+Secrow+" : "+sSecondTxt);
			  boolean vFlag1 = hdata.containsValue(sSecondTxt);
			    if(vFlag1==true)
			    {
			    	
			    	RetnResult=RetnResult+"\n </br> "+sSecondTxt;
			    	Verifiedrow=Verifiedrow+1;
			    }
		  }
		  if (Verifiedrow>2)
		  {
			
		  		System.out.println("Matched cell Value Of row number "+RetnResult);
		  		return RetnResult;
			  
		  }
		  else
		  {
			  Verifiedrow=0; 
		  }

	   }
	 }
	  catch(Exception e){
		  return RetnResult;
	  	}

  		System.out.println("Matched cell Value Of row number "+RetnResult);
	  return RetnResult;
 }

public String VerifyDivElementFromValue(String ListDivObj, String VerifyValue) 
{
	 	 APP_LOGS.debug("Verify data in Div tags : "+ListDivObj);
	 //String FnRetnResult=null;
		 String sPrimaryText,sSecondTxt;
		 int iVerifiedrow;
		 iVerifiedrow=0;
		 String RetnResult=null;
	 	 try
	 	 {

	//To locate table.		 
	String PORProperty=OR.getProperty(ListDivObj);
	String pArr[]=PORProperty.split(UIConstants.DATA_SPLIT);
	//String bypDivStrategy=pArr[0];
	String ListPDivlocatorValue=pArr[1];
	        
	  //To locate rows of table.
	  List<WebElement> ListPrDivElmt = driver.findElements(By.xpath(ListPDivlocatorValue));
	  //To calculate no of rows In table.
	  int rows_count = ListPrDivElmt.size();
	  System.out.println("rows_count:  "+rows_count);
	  //Loop will execute till the last row of table.
	  System.out.println(iVerifiedrow);
	  for (int row=0; row<rows_count; row++)
	  {
		  
		  sPrimaryText= ListPrDivElmt.get(row).getText();
		  System.out.println("sPrimaryText:  "+row+" : "+sPrimaryText);
		  List<WebElement> ListPrDivElmt2 = ListPrDivElmt.get(row).findElements(By.tagName("div"));
		  System.out.println("Bytag Row Num:  "+ListPrDivElmt2.size());
		  
		  for (int Secrow=0; Secrow<ListPrDivElmt2.size(); Secrow++)
		  {
			  sSecondTxt= ListPrDivElmt2.get(Secrow).getText();
			  System.out.println("sSecondTxt:  "+Secrow+" : "+sSecondTxt);
			  if (sSecondTxt.equalsIgnoreCase(VerifyValue))
			  {
				  RetnResult=RetnResult+"\n </br> "+sSecondTxt;
				  iVerifiedrow=1;
				  System.out.println("Matched cell Value Of row number "+RetnResult);
			  	return UIConstants.KEYWORD_PASS;
				  
			  }
			 
		  }

	   }
	  
  		System.out.println("Matched cell Value Of row number "+RetnResult);
		 }
		  catch(Exception e){
			  return RetnResult;
		  	}
  		return UIConstants.KEYWORD_FAIL;
 }

 public String SelectDivElementFromRowValue(String ListDivObj,String ByTagname , String VerifyValueinRow, String SetObjinVerifiedRow,String Setvalue) {
		 
		 APP_LOGS.debug("Select data in Div Tag : "+ListDivObj);
		 try
		 {
    	String PORProperty=OR.getProperty(ListDivObj);
		String pArr[]=PORProperty.split(UIConstants.DATA_SPLIT);
		
		String ListPDivlocatorValue=pArr[1];
		        
		String SORProperty=OR.getProperty(SetObjinVerifiedRow);
		String sArr[]=SORProperty.split(UIConstants.DATA_SPLIT);
		String bySDivStrategy=sArr[0];
		String ListSDivlocatorValue=sArr[1];
	
		  //To locate rows of table.
		  List<WebElement> ListPrDivElmt = driver.findElements(By.xpath(ListPDivlocatorValue));
		  //To calculate no of rows In table.
		  int rows_count = ListPrDivElmt.size();
		  System.out.println("rows_count:  "+rows_count);
		  //Loop will execute till the last row of table.
		  String sPrimaryText,sSecondTxt,sSelectObjText;
		  for (int row=0; row<rows_count; row++)
		  {
			  sPrimaryText= ListPrDivElmt.get(row).getText();
			  System.out.println("sPrimaryText:  "+row+" : "+sPrimaryText);
			  //List<WebElement> ListPrDivElmt2 = ListPrDivElmt.get(row).findElements(By.tagName("div"));
			  List<WebElement> ListPrDivElmt2 = ListPrDivElmt.get(row).findElements(By.tagName(ByTagname));
			if (sPrimaryText.contains(VerifyValueinRow))
			{
			  for (int Secrow=0; Secrow<ListPrDivElmt2.size(); Secrow++)
			  {
				  sSecondTxt= ListPrDivElmt2.get(Secrow).getText();
				  System.out.println("Secondary text : "+Secrow+" :"+sSecondTxt);
				  sSelectObjText= ListPrDivElmt2.get(Secrow).getAttribute(bySDivStrategy);
				  System.out.println("Verify object name:  "+sSelectObjText+" : "+ListSDivlocatorValue);
				 if(sSelectObjText.equalsIgnoreCase(ListSDivlocatorValue))
				    {
						 
					 System.out.println("Verified Attr sSelectObjText:  "+row+" : "+Secrow+" : "+sSelectObjText);

			   			String ObjectType=StringUtils.substring(SetObjinVerifiedRow, 0, 3);
						 switch(ObjectType.toLowerCase())
						  {
						  case "txt":
							  String sExistingText=ListPrDivElmt2.get(Secrow).getText();
							  System.out.println("Existing text:  "+row+" : "+Secrow+" : "+sExistingText);
							  ListPrDivElmt2.get(Secrow).sendKeys(Setvalue);
							  return UIConstants.KEYWORD_PASS;
						  case "lst":
							  System.out.println("Enter into lst selection text:  ");
					   			Select Selectby=new Select(ListPrDivElmt2.get(Secrow));			   			
					   			Selectby.selectByVisibleText(Setvalue);
					   			return UIConstants.KEYWORD_PASS;
						  case "btn":
							  ListPrDivElmt2.get(Secrow).click();
							  return UIConstants.KEYWORD_PASS;
						  }
					 
					 
				    }
				
			  	}
				 
			  }


		   }
		 }
		  catch(Exception e){
			  return UIConstants.KEYWORD_FAIL;
		  	}
		  //return FnRetnResult;
		  return UIConstants.KEYWORD_FAIL;
	 }
	 

 
 public String GetValueDivElementFromRowValue(String ListDivObj,String ByTagname , String VerifyValueinRow, String SetObjinVerifiedRow,String Setvalue) {
	 
	 APP_LOGS.debug("Get data in Div Tag : "+ListDivObj);
	 String data;
	 String FnRetnResult=null;
	 try
	 {
	String PORProperty=OR.getProperty(ListDivObj);
	String pArr[]=PORProperty.split(UIConstants.DATA_SPLIT);
	String ListPDivlocatorValue=pArr[1];
	
	String SORProperty=OR.getProperty(SetObjinVerifiedRow);
	String sArr[]=SORProperty.split(UIConstants.DATA_SPLIT);
	String bySDivStrategy=sArr[0];
	String ListSDivlocatorValue=sArr[1];

	  //To locate rows of table.
	  List<WebElement> ListPrDivElmt = driver.findElements(By.xpath(ListPDivlocatorValue));
	  //To calculate no of rows In table.
	  int rows_count = ListPrDivElmt.size();
	  System.out.println("rows_count:  "+rows_count);
	  //Loop will execute till the last row of table.
	  String sPrimaryText,sSecondTxt,sSelectObjText;
	  for (int row=0; row<rows_count; row++)
	  {
		  sPrimaryText= ListPrDivElmt.get(row).getText();
		  System.out.println("sPrimaryText:  "+row+" : "+sPrimaryText);
		  //List<WebElement> ListPrDivElmt2 = ListPrDivElmt.get(row).findElements(By.tagName("div"));
		  List<WebElement> ListPrDivElmt2 = ListPrDivElmt.get(row).findElements(By.tagName(ByTagname));
		if (sPrimaryText.contains(VerifyValueinRow))
		{
		  for (int Secrow=0; Secrow<ListPrDivElmt2.size(); Secrow++)
		  {
			  sSecondTxt= ListPrDivElmt2.get(Secrow).getText();
			  System.out.println("Secondary text : "+Secrow+" :"+sSecondTxt);
			  sSelectObjText= ListPrDivElmt2.get(Secrow).getAttribute(bySDivStrategy);
			  System.out.println("Verify object name:  "+sSelectObjText+" : "+ListSDivlocatorValue);
			 if(sSelectObjText.equalsIgnoreCase(ListSDivlocatorValue))
			    {
					 
				 System.out.println("Verified Attr sSelectObjText:  "+row+" : "+Secrow+" : "+sSelectObjText);

		   			String ObjectType=StringUtils.substring(SetObjinVerifiedRow, 0, 3);
					 switch(ObjectType.toLowerCase())
					  {
					  case "txt":
						  data=ListPrDivElmt2.get(Secrow).getAttribute("value");
						  FnRetnResult=FnRetnResult+"\n"+StringUtils.mid(SetObjinVerifiedRow, 3,50)+" : "+data;
						  return FnRetnResult;
					  case "lst":
						  
				   			Select Selectby=new Select(ListPrDivElmt2.get(Secrow));		
				   			WebElement option =Selectby.getFirstSelectedOption();
				   			data=option.getText();
				   			FnRetnResult=FnRetnResult+"\n "+StringUtils.mid(SetObjinVerifiedRow, 3,50)+" : "+data;
				   			return FnRetnResult;
					  case "btn":
						  ListPrDivElmt2.get(Secrow).click();
						  return FnRetnResult;
					  }
				 
				 
			    }
			
		  	}
			 
		  }


	   }
	  
	 }
	  catch(Exception e){
		  return FnRetnResult;
	  	}

	  //return FnRetnResult;
	  return FnRetnResult;
 }

	 public String GetElementWebtableFromRowValue(String object,String RowValue, int ObjectColnum,String Tableobject,String data) {
		 
		 APP_LOGS.debug("Verify data in WebTable : "+object);
		 String FnRetnResult=null;
		 try
		 {
		 int Currentrow=0;
		  //To locate table.
		  WebElement mytable = ChooseElement(OR.getProperty(object));
		  //To locate rows of table.
		  List<WebElement> rows_table = mytable.findElements(By.tagName("tr"));
		  //To calculate no of rows In table.
		  int rows_count = rows_table.size();
		  
		  //Loop will execute till the last row of table.
		  for (int row=0; row<rows_count; row++){
		   //To locate columns(cells) of that specific row.
		   List<WebElement> Columns_row = rows_table.get(row).findElements(By.tagName("td"));
		   //To calculate no of columns(cells) In that specific row.
		   int columns_count = Columns_row.size();
		   //System.out.println("Number of cells In Row "+row+" are "+columns_count);
		   
		   //Loop will execute till the last cell of that specific row.
		   for (int column=0; column<columns_count; column++){
		    //To retrieve text from that specific cell.
		    String celtext = Columns_row.get(column).getText();
		    System.out.println("Cell Value Of row number "+row+" and column number "+column+" Is "+celtext);

		    if(RowValue.equalsIgnoreCase(celtext))
		    {
		    	
		    	String odj;
		    	
		    	
		    	String ORProperty=OR.getProperty(Tableobject);
				String temp[]=ORProperty.split(UIConstants.DATA_SPLIT);
		      //  String byStrategy=temp[0];
		        String locatorValue=temp[1];

	   			 System.out.println("Selected Element in Cell Value Of row number "+row+" and column number "+column+" Is "+celtext);
	   			//System.out.println("Columns_row.get(column).getText "+Columns_row.get(column).findElement(By.xpath(locatorValue)).getText());
	   			//Columns_row.get(column).click();
	   			FnRetnResult=null;

	   			String ObjectType=StringUtils.substring(Tableobject, 0, 3);
				 switch(ObjectType.toLowerCase())
				  {
				  case "txt":
					  Currentrow=row;
			   			odj="//tr["+Currentrow+"]/td["+ObjectColnum+"]"+locatorValue;
			   			System.out.println("lOCATOR : "+odj);
			   			data=Columns_row.get(column).findElement(By.xpath(odj)).getAttribute("value");
			   			FnRetnResult=FnRetnResult+"\n"+StringUtils.mid(Tableobject, 3,50)+" : "+data;
				        System.out.println(" Attr value: "+data);
					  break;
				  case "lst":
					  	Currentrow=row;
						odj="//tr["+Currentrow+"]/td["+ObjectColnum+"]"+locatorValue;
			   			System.out.println("lOCATOR : "+odj);
//			   			data=Columns_row.get(column).findElement(By.xpath(odj)).getAttribute("selected");
//			   			System.out.println("Selected list : "+data);
			   			Select Selectby=new Select(Columns_row.get(column).findElement(By.xpath(odj)));
//			   			
			   			WebElement option =Selectby.getFirstSelectedOption();
			   			data=option.getText();
			   			System.out.println("Selected list : "+data);
			   			FnRetnResult=FnRetnResult+"\n "+StringUtils.mid(Tableobject, 3,50)+" : "+data;

					  break;
				  case "btn":
			   			Currentrow=row+1;//For some row +1 and for some row
			   			odj="//tr["+Currentrow+"]/td["+ObjectColnum+"]"+locatorValue;
			   			System.out.println("lOCATOR : "+odj);
					  Columns_row.get(column).findElement(By.xpath(odj)).click();
					  break;
				  }
				
	   			return FnRetnResult;
		    }

		   }
		  // System.out.println("--------------------------------------------------");
		  }
		 }
		  catch(Exception e){
			  return FnRetnResult;
		  	}
		  return FnRetnResult;
	 }
	
	public  String synchronize(String object,String data){
		APP_LOGS.debug("Waiting for page to load");
		((JavascriptExecutor) driver).executeScript(
        		"function pageloadingtime()"+
        				"{"+
        				"return 'Page has completely loaded'"+
        				"}"+
        		"return (window.onload=pageloadingtime());");
        
		return UIConstants.KEYWORD_PASS;
	}
	
	public  String waitForElementVisibility(String object,String data){
		APP_LOGS.debug("Waiting for an element to be visible");
		//System.out.println("WaitforElement"+object+" : "+data);
		int start=0;
		int time=(int)Double.parseDouble(data);
		try{
		 while(time == start){
			 System.out.println("Start"+start);
			 System.out.println("time "+time);
			if(driver.findElements(By.xpath(OR.getProperty(object))).size() == 0){
				 System.out.println("Passed "+start+time);
				Thread.sleep(1000L);
				start++;
			}else{
				 System.out.println("Failed "+start+time);
				break;
			}
		 }
		}catch(Exception e){
			return UIConstants.KEYWORD_FAIL+"Unable to close browser. Check if its open"+e.getMessage();
		}
		return UIConstants.KEYWORD_PASS;
	}
	
	public  String closeBroswer(String object, String data){
		APP_LOGS.debug("Closing the browser");
		try{
			driver.close();
			Thread.sleep(3000);
			driver.quit();
			Thread.sleep(3000);
		}catch(Exception e){
			return UIConstants.KEYWORD_FAIL+"Unable to close browser. Check if its open"+e.getMessage();
		}
		return UIConstants.KEYWORD_PASS;

	}
	
	
	public String pause(String object, String data) throws NumberFormatException, InterruptedException{
		long time = (long)Double.parseDouble(object);
		Thread.sleep(time*1000L);
		return UIConstants.KEYWORD_PASS;
	}
	
	
	
	
	
	public  String verifyLaptops(String object, String data){
        APP_LOGS.debug("Verifying the laptops in app");
		// brand
        String brand="";
        //currentTestSuiteXLS.getCellData(currentTestCaseName, "Brand", currentTestDataSetID).toLowerCase();
        for(int i=1 ; i<=4;i++){
        	String text = driver.findElement(By.xpath(OR.getProperty("laptop_name_link_start")+i+OR.getProperty("laptop_name_link_end"))).getText().toLowerCase();
        	if(text.indexOf(brand) == -1){
        		return UIConstants.KEYWORD_FAIL+" Brand not there in - "+text;
        	}
        	
        }
        
        
		return UIConstants.KEYWORD_PASS;
	}
	
	
	public String verifySearchResults(String object,String data){
        APP_LOGS.debug("Verifying the Search Results");
        try{
        	data=data.toLowerCase();
        	for(int i=3;i<=5;i++){
        		String text=driver.findElement(By.xpath(OR.getProperty("search_result_heading_start")+i+OR.getProperty("search_result_heading_end"))).getText().toLowerCase();
        		if(text.indexOf(data) == -1){
        			return UIConstants.KEYWORD_FAIL+" Got the text - "+text;
        		}
        	}
        	
        }catch(Exception e){
			return UIConstants.KEYWORD_FAIL+"Error -->"+e.getMessage();
		}
        
		return UIConstants.KEYWORD_PASS;


	}
	
	public  String VerifyImage(String object, String data)
	{
			 
		 WebElement value1= driver.findElement(By.xpath(data));
		 System.out.println(value1.getText());
		 return value1.getText();
	}
	
	public  String VerifyWebTable(String object, String data)
	{
		 WebElement value=driver.findElement(By.xpath(data));
		 System.out.println(value.getText());
		 return value.getText();
	}	
	
	// not a keyword
	
	public void captureScreenshot(String filename, String keyword_execution_result) throws IOException{
		
		
		if(CONFIG.getProperty("screenshot_everystep").equals("Y")){
			// capturescreen
			System.out.println("Capture Screen Shot step.......");
			// take screen shots
			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		    FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir") +"//screenshots//"+filename+".jpg"));
			
		}else if (keyword_execution_result.startsWith(UIConstants.KEYWORD_FAIL) && CONFIG.getProperty("screenshot_error").equals("Y") ){
		// capture screenshot
			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		    FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir") +"//screenshots//"+filename+".jpg"));
		}
	}
	
	
		public String AutoSuggestControl(String object,String data){
	        APP_LOGS.debug("Auto suggest value");
	        driver.findElement(By.xpath(OR.getProperty(object))).click();
			return UIConstants.KEYWORD_PASS;
		}
}
