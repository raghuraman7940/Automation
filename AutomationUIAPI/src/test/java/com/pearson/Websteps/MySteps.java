package com.pearson.Websteps;

import org.jbehave.core.annotations.AfterStory;

import org.jbehave.core.annotations.BeforeStory;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import com.pearson.testbase.Keywords;
import com.pearson.util.ReportUtil;
import com.pearson.pages.LoginPage;
import com.pearson.pages.LoggedIn;
import com.pearson.pages.MyProfile;
import static com.pearson.Websteps.UICommonSteps.APP_LOGS;
import static com.pearson.Websteps.UICommonSteps.CONFIG;
import java.io.IOException;


public class MySteps {

	public static String CurrentUserRole;
	public static int iTCGenNo;
	public static String sTCNoTemplate;
	public static Keywords keywords;
	public static LoginPage pagelogin;
	public static LoggedIn pageloggedIn;
	public static MyProfile pageProfile;
	public static ReportUtil Rep;
	public String InputDetails;
	public String VerProfileRes;
	
	 @BeforeStory(uponGivenStory=false)
	    public void beforeGivenStory(@Named("UserRole") String sUserRole) throws NumberFormatException, InterruptedException {

	    	// Initialize reporting
	    	Rep=new ReportUtil();
	    	
			// Initialize keywords and page reusable
			keywords = new Keywords();
			pagelogin=new LoginPage();
			pageloggedIn=new LoggedIn();
			pageProfile=new MyProfile();
			
	    	//Get userRole from Meta
	    	CurrentUserRole=sUserRole;
	    	
	    	//Generate Test case number for every story
	    	iTCGenNo=0;
			sTCNoTemplate="TC_"+CurrentUserRole+"000";
			
	    	//Launch browser for every story file
		
			// initialize the app logs
			keywords.openBrowser("Test",CONFIG.getProperty("browserType"));
			keywords.pause("2", "2");
			
	    }
	
	//Scenario Login with username and password
	@Given("user login with below user details")
	public void givenUserLoginWithBelowUserDetails(@Named("URL") String URL, @Named("Username") String Username, @Named("Password") String Password) throws AbstractMethodError, IOException, NumberFormatException, InterruptedException{
		 //TODO 
		pagelogin.doLogin(URL, Username, Password);
		InputDetails="The login details are <\br> Username :"+Username+"\n <\br>Pasword: "+Password;
	}
	@When("user clicks the Login button")
	public void whenUserClicksTheLoginButton() throws NumberFormatException, InterruptedException{
		 //TODO 
	}
	@Then("user verify the login should be successfully")
	public void thenUserVerifyTheLoginShouldBeSuccessfully(@Named("PTEID") String PTEID) throws AbstractMethodError, IOException{
		 //TODO 
		iTCGenNo=iTCGenNo+1;
		String ActualRes;
		String TCID=sTCNoTemplate+iTCGenNo;
		String TCName="Login -"+CurrentUserRole;
		String warning=pagelogin.VerifyWarning();
		if (warning=="FAIL")
		{
			APP_LOGS.debug("Login Failed with warning");
			keywords.captureScreenshot("TC01_Login",warning);
			ActualRes="User login failed with warning : "+warning;
			Rep.ReportExcelData(TCID,TCName,"The login functionality with "+CurrentUserRole+" user",InputDetails,"Login should be successful","FAIL",ActualRes,"");
		}
		else
		{
		String Rsult=pageloggedIn.VerifyLoggedIn();
		if (Rsult=="PASS")
		{
			String PTENum=pageloggedIn.VerifyPTENum(PTEID);
			ActualRes="User logged in Successfull";
			if (PTENum==PTEID)
				ActualRes=ActualRes+" \n PTE ID matched with expected "+PTENum;
			else
				 ActualRes=ActualRes+" \n PTE ID not matched with expected"+PTENum;
		}
		else
		{
			ActualRes="User login failed";
			keywords.captureScreenshot("TC01_Login",Rsult);
		}
		Rep.ReportExcelData(TCID,TCName,"The login functionality with "+CurrentUserRole+" user",InputDetails,"Login should be successful",Rsult,ActualRes,"");
		}
		
	}
	//End of Scenario 1-Login
	//My Profile
	@Given("the profile details")
	public void GivenTheprofiledetails(@Named("Name") String Name, @Named("DOB") String DOB) throws AbstractMethodError, IOException, NumberFormatException, InterruptedException{
		 //TODO 
		VerProfileRes=pageProfile.ViewPersonalInfo(Name, DOB);
		InputDetails="The Profile details are <\br> Name :"+Name+"\n <\br>DOB: "+DOB;
	}
	@When("user clicks the My Profile")
	public void WhenuserclickstheMyProfile() throws NumberFormatException, InterruptedException{
		 //TODO 
	}
	@Then("verify the profile details")
	public void ThenVerifytheprofiledetailsn() throws NumberFormatException, InterruptedException, AbstractMethodError, IOException{
		 //TODO 
		iTCGenNo=iTCGenNo+1;
		String ActualRes;
		String TCID=sTCNoTemplate+iTCGenNo;
		String TCName="Verify Personal Info -"+CurrentUserRole;
		if (VerProfileRes=="PASS")
		{
			ActualRes="Profile details matched with expected";
		}
		else
		{
			ActualRes="Profile details matched with expected";
		}
		Rep.ReportExcelData(TCID,TCName,"Verify Persona Info functionality with "+CurrentUserRole+" user",InputDetails,"Profile details should match with expected",VerProfileRes,ActualRes,"");
		
	}
		
	@AfterStory(uponGivenStory=false)
	public void afterGivenStory() throws NumberFormatException, InterruptedException {
		keywords.click("btnLogout", "data ");
		keywords.pause("2", "2");
		keywords.closeBroswer("Browser", "Quit");
	}
	

}