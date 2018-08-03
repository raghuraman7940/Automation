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
import com.pearson.pages.Students;
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
	public static Students Students;
	public static ReportUtil Rep;
	public String InputDetails;
	public String VerProfileRes;
	public static String CurrentTestDataSheet;
	
	 @BeforeStory(uponGivenStory=false)
	    public void beforeGivenStory(@Named("UserRole") String sUserRole, @Named("TestDataSheet") String sTestSheet) throws NumberFormatException, InterruptedException {

	    	// Initialize reporting
	    	Rep=new ReportUtil();
	    	
			// Initialize keywords and page reusable
			keywords = new Keywords();
			pagelogin=new LoginPage();
			pageloggedIn=new LoggedIn();
			pageProfile=new MyProfile();
			Students= new Students();
			
	    	//Get userRole from Meta
	    	CurrentUserRole=sUserRole;
	    	
	    	//Get testdatasheet and userRole from Meta
	    	CurrentTestDataSheet=sTestSheet;
	    	
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
	
	@Given("user provide the user details into PA Next")
	public void givenUserProvideTheUserDetailsIntoPANext(@Named("URL") String URL, @Named("Username") String Username, @Named("Password") String Password) throws NumberFormatException, InterruptedException {
		 //TODO 
		
		pagelogin.doLogin2(URL, Username, Password);
		InputDetails="The login details are <\br> Username :"+Username+"\n <\br>Pasword: "+Password;
		//Students.CreateStudent();
	}
	
	@When("user selects the Login button")
	public void whenUserSelectsTheLoginButton(){
		 //TODO 
	}
	@Then("verify the login should be successfully")
	public void thenVerifyTheLoginShouldBeSuccessfully() throws IOException{
		 //TODO 
		
		 //TODO 
		iTCGenNo=iTCGenNo+1;
		String ActualRes;
		String TCID=sTCNoTemplate+iTCGenNo;
		String TCName="Login -"+CurrentUserRole;
		
		String Rsult=pageloggedIn.VerifyLoggedIn1();
		if (Rsult=="PASS")
		{
			ActualRes="User logged in Successfull";
	
		}
		else
		{
			ActualRes="User login failed";
			keywords.captureScreenshot("TC01_Login",Rsult);
		}
		Rep.ReportExcelData(TCID,TCName,"The login functionality with "+CurrentUserRole+" user",InputDetails,"Login should be successful",Rsult,ActualRes,"");
		
		
	}
	
	
	@Given("user select create and provide the student details")
	public void givenUserSelectCreateAndProvideTheStudentDetails(@Named("Enrolled_Organization") String Enrolled_Organization,@Named("State_Student_ID") String State_Student_ID,
			@Named("Local_State_ID") String Local_State_ID,
			@Named("LastName") String LastName,
			@Named("FirstName") String FirstName,
			@Named("MiddleName") String MiddleName,
			@Named("Birthdate") String Birthdate,
			@Named("Gender") String Gender,
			@Named("Grade") String Grade,
			@Named("Resp_Sch_Code") String Resp_Sch_Code,
			@Named("Ship_Organization") String Ship_Organization,
			@Named("Hispanic") String Hispanic,
			@Named("Race_Asian") String Race_Asian,
			@Named("American_Indian") String American_Indian,
			@Named("African_American") String African_American,
			@Named("Native_Hawaiian") String Native_Hawaiian,
			@Named("White") String White,
			@Named("Two_More_Races") String Two_More_Races,
			@Named("English_Learner") String English_Learner,
			@Named("Title_III_Limited") String Title_III_Limited,
			@Named("Gifted_Talented") String Gifted_Talented,
			@Named("Migrant_Status") String Migrant_Status,
			@Named("Student_Disabilities") String Student_Disabilities
){
		 //TODO 
		Students.CreateStudent(Enrolled_Organization,State_Student_ID,Local_State_ID,LastName,FirstName,MiddleName,Birthdate,Gender,Grade,Resp_Sch_Code,Ship_Organization,Hispanic,Race_Asian,American_Indian,African_American,Native_Hawaiian,White,Two_More_Races,English_Learner,Title_III_Limited,Gifted_Talented,Migrant_Status,Student_Disabilities);
	}
	@When("user select the save button")
	public void whenUserSelectTheSaveButton(){
		 //TODO 
	}
	@Then("verify the student details")
	public void thenVerifyTheStudentDetails(){
		 //TODO 
	}
		
	@AfterStory(uponGivenStory=false)
	public void afterGivenStory() throws NumberFormatException, InterruptedException {
	//	keywords.click("btnLogout", "data ");
		pageloggedIn.VerifyLoggedOut();
		keywords.pause("2", "2");
		keywords.closeBroswer("Browser", "Quit");
	}
	

}