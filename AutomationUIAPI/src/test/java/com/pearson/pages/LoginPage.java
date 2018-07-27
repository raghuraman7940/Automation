package com.pearson.pages;

import static com.pearson.Websteps.UICommonSteps.CONFIG;
import static com.pearson.Websteps.MySteps.keywords;
import com.pearson.util.UIConstants;

public class LoginPage {
	//public Keywords keywords = new Keywords();
	public String doLogin(String URL, String UserName, String Password) throws NumberFormatException, InterruptedException {
		// TODO Auto-generated method stub
		try
		{
			keywords.navigate("Test",URL);
			keywords.pause("2", "2");
			keywords.waitForElementVisibility(UserName, CONFIG.getProperty("implicitwait"));
			keywords.writeInInput("iUsername", UserName);
			keywords.writeInInput("iPassword", Password);
			keywords.pause("2", "2");
			keywords.click("btnLogin", " ");
			keywords.pause("5", "5");
		return UIConstants.KEYWORD_PASS;
		}
		catch(Exception e){
			return UIConstants.KEYWORD_FAIL+" -- Not able to Login";
		}
	}
	
	//public Keywords keywords = new Keywords();
	public String doLogin2(String URL, String UserName, String Password) throws NumberFormatException, InterruptedException {
		// TODO Auto-generated method stub
		try
		{
			keywords.navigate("Test",URL);
			keywords.pause("2", "2");
			keywords.waitForElementVisibility("lnkSignIn", CONFIG.getProperty("implicitwait"));
			keywords.click("lnkSignIn", " ");
			keywords.pause("2", "2");
			keywords.waitForElementVisibility(UserName, CONFIG.getProperty("implicitwait"));
			keywords.writeInInput("txtUserName", UserName);
			keywords.writeInInput("txtPassword", Password);
			keywords.pause("2", "2");
			keywords.click("btnLogin1", " ");
			keywords.pause("5", "5");
		return UIConstants.KEYWORD_PASS;
		}
		catch(Exception e){
			return UIConstants.KEYWORD_FAIL+" -- Not able to Login";
		}
	}

	public String VerifyWarning() {
		// TODO Auto-generated method stub
		try{
		String warning=keywords.waitForElementVisibility("WarningLogin", CONFIG.getProperty("implicitwait"));
		if (warning=="PASS")
			return UIConstants.KEYWORD_PASS;
		else
			return UIConstants.KEYWORD_FAIL;
		}
		catch(Exception e){
			return UIConstants.KEYWORD_FAIL+" -- Not able to Login";
		}
		
	}
	public void CreateWebAccount() {
		// TODO Auto-generated method stub
		
	}
	
	public void fortgotUsername() {
		// TODO Auto-generated method stub
		
	}
	
	public void fortgotPassword() {
		// TODO Auto-generated method stub
		
	}

}
