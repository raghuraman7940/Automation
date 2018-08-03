package com.pearson.pages;
import static com.pearson.Websteps.UICommonSteps.CONFIG;

import java.util.ArrayList;
import java.util.List;

import static com.pearson.Websteps.MySteps.keywords;
import com.pearson.util.UIConstants;
public class Students {

	public String CreateStudent(String Enrolled_Organization,
			String State_Student_ID,
			String Local_State_ID,
			String LastName,
			String FirstName,
			String MiddleName,
			String Birthdate,
			String Gender,
			String Grade,
			String Resp_Sch_Code,
			String Ship_Organization,
			String Hispanic,
			String Race_Asian,
			String American_Indian,
			String African_American,
			String Native_Hawaiian,
			String White,
			String Two_More_Races,
			String English_Learner,
			String Title_III_Limited,
			String Gifted_Talented,
			String Migrant_Status,
			String Student_Disabilities) {
		// TODO Auto-generated method stub
		try
		{
			
			 System.out.println("Ip Values: "+Enrolled_Organization+State_Student_ID+Local_State_ID+LastName+FirstName+MiddleName+Birthdate+Gender+Grade+Resp_Sch_Code+Ship_Organization+Hispanic+Race_Asian+American_Indian+African_American+Native_Hawaiian+White+Two_More_Races+English_Learner+Title_III_Limited+Gifted_Talented+Migrant_Status+Student_Disabilities);
			 
			keywords.waitForElementVisibility("btnCreateStudent", CONFIG.getProperty("implicitwait"));
			keywords.click("btnCreateStudent", " ");
			keywords.pause("2", "2");
			keywords.waitForElementVisibility("frmCreateStudent", CONFIG.getProperty("implicitwait"));
			String Selectedvalue1="";
			//keywords.kendo_multiselect("Ken_MS", "lst",drpvalue);
//			List<String> lstvalue= new ArrayList<>(); 
//			lstvalue.add("SCHOOL B");
//			lstvalue.add("SCHOOL A");
//			keywords.Ken_MultiSelect_SelectValue("Ken_MS", lstvalue);
//			List<String> Selectedvalue= keywords.Ken_MultiSelect_GetSelected("Ken_MS");
//			 System.out.println("Selectedvalue: "+Selectedvalue);
//			String SingleValue="SCHOOL Z";
//			keywords.Ken_MultiSelect_SelectValue("Ken_MS", SingleValue);
//			List<String> Selectedvalue2= keywords.Ken_MultiSelect_GetSelected("Ken_MS");


		
			keywords.Ken_DropDown_SelectValue("lstKendropdown", Enrolled_Organization);
			Selectedvalue1= keywords.Ken_DropDown_GetSelected("lstKendropdown");
			System.out.println("Enrolled_Organization: "+Selectedvalue1);
					 
			keywords.Ken_DropDown_SelectValue("lstKen1", Gender);
			Selectedvalue1= keywords.Ken_DropDown_GetSelected("lstKen1");
			 System.out.println("Gender: "+Selectedvalue1);
			 
			keywords.Ken_DropDown_SelectValue("lstKen2", Grade);
			Selectedvalue1= keywords.Ken_DropDown_GetSelected("lstKen2");
			System.out.println("Grade: "+Selectedvalue1);
			 
			keywords.Ken_DropDown_SelectValue("lstKen3", Resp_Sch_Code);
			Selectedvalue1= keywords.Ken_DropDown_GetSelected("lstKen3");
			System.out.println("Resp_Sch_Code: "+Selectedvalue1);
			 
			keywords.Ken_DropDown_SelectValue("lstKen4", Ship_Organization);
			Selectedvalue1= keywords.Ken_DropDown_GetSelected("lstKen4");
			System.out.println("Ship_Organization: "+Selectedvalue1);
			 
			keywords.Ken_DropDown_SelectValue("lstKen5", Hispanic);
			Selectedvalue1= keywords.Ken_DropDown_GetSelected("lstKen5");
			System.out.println("Hispanic: "+Selectedvalue1);
			
			keywords.Ken_DropDown_SelectValue("lstKen6", Race_Asian);
			Selectedvalue1= keywords.Ken_DropDown_GetSelected("lstKen6");
			System.out.println("Race_Asian: "+Selectedvalue1);
			 
			keywords.Ken_DropDown_SelectValue("lstKen7", American_Indian);
			Selectedvalue1= keywords.Ken_DropDown_GetSelected("lstKen7");
			System.out.println("American_Indian: "+Selectedvalue1);
						 
			keywords.Ken_DropDown_SelectValue("lstKen8", Native_Hawaiian);
			Selectedvalue1= keywords.Ken_DropDown_GetSelected("lstKen8");
			System.out.println("Native_Hawaiian: "+Selectedvalue1);
			 
			keywords.Ken_DropDown_SelectValue("lstKen9", White);
			Selectedvalue1= keywords.Ken_DropDown_GetSelected("lstKen9");
			System.out.println("White: "+Selectedvalue1);
			 
			keywords.Ken_DropDown_SelectValue("lstKen10", Two_More_Races);
			Selectedvalue1= keywords.Ken_DropDown_GetSelected("lstKen10");
			System.out.println("Two_More_Races: "+Selectedvalue1);
			 
			keywords.Ken_DropDown_SelectValue("lstKen11", English_Learner);
			Selectedvalue1= keywords.Ken_DropDown_GetSelected("lstKen11");
			System.out.println("English_Learner: "+Selectedvalue1);
			 
			keywords.Ken_DropDown_SelectValue("lstKen12", "Yes");
			Selectedvalue1= keywords.Ken_DropDown_GetSelected("lstKen12");
			System.out.println("Selectedvalue12: "+Selectedvalue1);
			 
			keywords.Ken_DropDown_SelectValue("lstKen13",Title_III_Limited);
			Selectedvalue1= keywords.Ken_DropDown_GetSelected("lstKen13");
			System.out.println("Title_III_Limited: "+Selectedvalue1);
			 
			keywords.Ken_DropDown_SelectValue("lstKen14", Gifted_Talented);
			Selectedvalue1= keywords.Ken_DropDown_GetSelected("lstKen14");
			System.out.println("Gifted_Talented: "+Selectedvalue1);
			 
			keywords.Ken_DropDown_SelectValue("lstKen15", Migrant_Status);
			Selectedvalue1= keywords.Ken_DropDown_GetSelected("lstKen15");
			System.out.println("Migrant_Status: "+Selectedvalue1);
			 
			keywords.Ken_DropDown_SelectValue("lstKen16", "Yes");
			Selectedvalue1= keywords.Ken_DropDown_GetSelected("lstKen16");
			System.out.println("Selectedvalue16: "+Selectedvalue1);
			 
			 
			keywords.Ken_DropDown_SelectValue("lstKen17", Student_Disabilities);
			Selectedvalue1= keywords.Ken_DropDown_GetSelected("lstKen17");
			System.out.println("Student_Disabilities: "+Selectedvalue1);
			 
			keywords.Ken_DropDown_SelectValue("lstKen18", "AUT - Autism");
			Selectedvalue1= keywords.Ken_DropDown_GetSelected("lstKen18");
			System.out.println("Selectedvalue18: "+Selectedvalue1);
//				 
			//keywords.pause("5", "5");
			//keywords.kendo_multiselect("Ken_MS", "selected",lstvalue);
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
