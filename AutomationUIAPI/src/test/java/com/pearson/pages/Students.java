package com.pearson.pages;
import static com.pearson.Websteps.UICommonSteps.CONFIG;
import static com.pearson.Websteps.MySteps.keywords;
import com.pearson.util.UIConstants;
public class Students {

	public String CreateStudent() {
		// TODO Auto-generated method stub
		try
		{
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

	public void VerifyOrders() {
		// TODO Auto-generated method stub
		
	}

}
