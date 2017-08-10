package com.pearson.pages;

import static com.pearson.Websteps.MySteps.keywords;

import com.pearson.util.UIConstants;

public class MyProfile {


	public void EditPersonalInfo() {
		// TODO Auto-generated method stub
	}

	public void EditPersonalContactDetails() {
		// TODO Auto-generated method stub
		
	}
	public String ViewPersonalInfo(String Name, String DOB) {
		// TODO Auto-generated method stub
		try
		{
			keywords.click("lnkProfile"," ");
			keywords.pause("5", "5");
			String fName=keywords.getText("lblName", "");
			String fDob=keywords.getText("lblDOB", "");
			System.out.println("Frontend :"+fName+fDob);
			if ((fName==Name)&(fDob==DOB))
				return UIConstants.KEYWORD_PASS;
			else
				return UIConstants.KEYWORD_FAIL;
		}
		catch(Exception e){
			return UIConstants.KEYWORD_FAIL+" -- Not able to verify Personal Info";
		}
		
	}

	public void ViewPersonalContactDetails() {
		// TODO Auto-generated method stub
		
	}

}
