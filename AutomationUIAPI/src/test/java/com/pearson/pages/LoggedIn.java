package com.pearson.pages;
import static com.pearson.Websteps.UICommonSteps.CONFIG;
import static com.pearson.Websteps.MySteps.keywords;
import com.pearson.util.UIConstants;
public class LoggedIn {
	//public Keywords keywords = new Keywords();
	public String VerifyLoggedIn() {
		// TODO Auto-generated method stub
		try
		{
			String Rsult=keywords.waitForElementVisibility("lnkMyProfile", CONFIG.getProperty("implicitwait"));
			if (Rsult=="PASS")
				return UIConstants.KEYWORD_PASS;
			else
				return UIConstants.KEYWORD_FAIL;
		}
		catch(Exception e){
			return UIConstants.KEYWORD_FAIL+" -- Not able to verify LoggedIn";
		}
	}
	
	
	//public Keywords keywords = new Keywords();
	public String VerifyLoggedIn1() {
		// TODO Auto-generated method stub
		try
		{
			String Rsult=keywords.waitForElementVisibility("lnkUserOption", CONFIG.getProperty("implicitwait"));
			if (Rsult=="PASS")
				return UIConstants.KEYWORD_PASS;
			else
				return UIConstants.KEYWORD_FAIL;
		}
		catch(Exception e){
			return UIConstants.KEYWORD_FAIL+" -- Not able to verify LoggedIn";
		}
	}
	
	//public Keywords keywords = new Keywords();
		public String VerifyLoggedOut() {
			// TODO Auto-generated method stub
			try
			{
				String Rsult=keywords.waitForElementVisibility("lnkUserOption", CONFIG.getProperty("implicitwait"));
				if (Rsult=="PASS")
				{
					keywords.click("lnkUserOption", " ");
					keywords.waitForElementVisibility("lnkLogout", CONFIG.getProperty("implicitwait"));
					keywords.click("lnkLogout", " ");
					Rsult=keywords.waitForElementVisibility("lnkSignIn", CONFIG.getProperty("implicitwait"));
					if (Rsult=="PASS")
						return UIConstants.KEYWORD_PASS;
					else
						return UIConstants.KEYWORD_FAIL;
				}
			}
			catch(Exception e){
				return UIConstants.KEYWORD_FAIL+" -- Not able to verify Logged Out";
			}
			return UIConstants.KEYWORD_PASS;
		}
	
	public String VerifyPTENum(String PTEID) {
		// TODO Auto-generated method stub
		try
		{
			String PTENum=keywords.getText("ElePTENum", "");
			if (PTENum==PTEID)
				return UIConstants.KEYWORD_PASS;
			else
				return UIConstants.KEYWORD_FAIL;
		}
		catch(Exception e){
			return UIConstants.KEYWORD_FAIL+" -- Not able to verify PTENUM";
		}
	}
	
	public String VerifyExamCatalogue(String PTEID) {
		// TODO Auto-generated method stub
		try
		{
			return UIConstants.KEYWORD_PASS;
		}
		catch(Exception e){
			return UIConstants.KEYWORD_FAIL+" -- Not able to verify examcatalogue";
		}
	}

	public void VerifyOrders() {
		// TODO Auto-generated method stub
		
	}


}
