package com.zap.client;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class SeleniumTools {
	
	String CHROMEDRIVER_LOCATION = "Drivers\\chromedriver\\chromedriver.exe";
	
	ChromeDriverService service;
	WebDriver driver;
	
	public SeleniumTools() {}
	
	public boolean startService() {
		this.service = new ChromeDriverService.Builder().usingDriverExecutable(new File(this.CHROMEDRIVER_LOCATION)).usingAnyFreePort().build();
		try {
            this.service.start();
            this.driver = new RemoteWebDriver(this.service.getUrl(), DesiredCapabilities.chrome());
            return true;
        } catch (IOException ex) {
            System.out.println( "Error starting the ChromeDriverService." );
            ex.printStackTrace();
            return false;
        }
	}
	
	public void stopService() {
		this.service.stop();
	}
	
	public ChromeDriverService getService() {
		return this.service;
	}
	
	public WebDriver getWebDriver() {
		return this.driver;
	}
	
}