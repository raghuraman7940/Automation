package com.zap.client;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.zaproxy.clientapi.core.ClientApi;

public class SecureTest {

	static final String ZAP_SESSION_IP = "127.0.0.1";
	static final int ZAP_SESSION_PORT = 8080;
	static final String ZAP_API_KEY ="fn4ism7pac59tdfac434mvmpao"; // Change this if you have set the apikey in ZAP via Options / API

	static final String ZAP_HOSTNAME = "localhost";
	static final String ZAP_URI = "http://localhost";
	static final String ZAP_URI_PORT = "http://localhost:49794";
	static final String SELENIUM_URI = "http://localhost:49794";
	
	public static void main(String[] args) throws InterruptedException {
		ZapTools zap = new ZapTools();
		if( zap.startZAP() == false ) {
			System.out.println( "ZAP failed to start. Terminating..." );
			System.exit(0);
		}
		zap.startSession( ZAP_HOSTNAME, ZAP_SESSION_PORT,ZAP_API_KEY );		
		SeleniumTools selenium = new SeleniumTools();
		if( selenium.startService() == false ) {
			System.out.println( "Selenium failed to start. Terminating..." );
			System.exit(0);
		}
		
		//We now have ZAP and Selenium running and ready to go:
		//1. open up the Secure Test App and complete the form with Selenium
		//2. spider the Secure Test App (not necessary as there's only 2 URLs in this Secure Test App - demonstration purposes)
		//3. run an active scan to uncover any vulnerabilities
		//4. check for any errors/warning found in the active scan
		//5. save session for later use
		//6. stop and close Selenium
		//7. stop and close ZAP
		
		// 1
		WebDriver driver = selenium.getWebDriver();
		//username.submit();
		try {
			
			driver.get( SELENIUM_URI );
			Thread.sleep(3000);
		
			WebElement username = driver.findElement(By.xpath("//*[@id=\"content\"]/div/div[2]/div/div[1]/div[1]/div[2]/div/div/div[1]/div/div[1]"));
			String Testcount=username.getText();
			System.out.println("Testcount:"+Testcount);
			driver.findElement(By.xpath("//*[@id=\"content\"]/div/div[1]/div/ul/li[3]/a/div")).click();
			Thread.sleep(3000);
			driver.findElement(By.xpath("//*[@id=\"content\"]/div/div[1]/div/ul/li[4]/a/div")).click();
			Thread.sleep(3000);
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        
        // 2
        ClientApi api = new ClientApi( ZAP_HOSTNAME, ZAP_SESSION_PORT,ZAP_API_KEY );
        if( zap.spider( api, ZAP_URI_PORT ) == false ) {
        	System.out.println( "Spider Failed - see console for details. Continuing..." );
        }
        
        // 3
        if( zap.ascan( api, ZAP_URI_PORT ) == false ) {
        	System.out.println( "Active Scan Failed - see console for details. Continuing..." );
        }
        
        // 4
        System.out.println( zap.checkErrors( api ) );
        
        // 5
        zap.saveSession (api, "secure" );
        
        // 6
        driver.close();
        selenium.stopService();
        
        // 7
        zap.stopZAP(ZAP_SESSION_IP, ZAP_SESSION_PORT,ZAP_API_KEY);
	}

}