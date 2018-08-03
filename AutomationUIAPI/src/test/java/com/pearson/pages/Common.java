package com.pearson.pages;
import com.pearson.util.Xls_Reader;
import static com.pearson.Websteps.UICommonSteps.CONFIG;
import static com.pearson.Websteps.MySteps.keywords;
import com.pearson.util.UIConstants;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.lang.StringUtils;

public class Common {


	public String WriteTextWithExcelData(String CurrentTestDataSheet,String Column,String getColumnName,String AddorEdit) throws AbstractMethodError
	{
		String Resut=null;
		String ColumnValue;
		String OldValues=null;
		ColumnValue=GetExcelData(CurrentTestDataSheet,UIConstants.TEST_STEPS_OBJCOLUMN,Column,getColumnName,AddorEdit);
		
		if (ColumnValue!=null)
		{
			ColumnValue=this.getDynamicvalue(ColumnValue);
			String TrimObjectName=StringUtils.mid(Column, 3,50);
			Resut=TrimObjectName+" := "+ColumnValue;
			
			String ObjectType=StringUtils.substring(Column, 0, 3);
			 switch(ObjectType.toLowerCase())
			  {
			  case "txt":
				  System.out.println("Text Edit value : "+Column+" : "+ColumnValue);
				  if (AddorEdit=="Edit")
					{
					  OldValues=keywords.getVal(Column, ColumnValue);
					  Resut=TrimObjectName+" : "+OldValues+" =>"+ColumnValue;
					}
				  	keywords.writeInInput(Column, ColumnValue);
	
				  break;
			  case "lst":
				  if (AddorEdit=="Edit")
					{
					  OldValues=keywords.getListSelection(Column, ColumnValue);
					  System.out.println("old List value : "+OldValues);
					  Resut=TrimObjectName+" : "+OldValues+" =>"+ColumnValue;
					}
				 // System.out.println("List value : "+Column+" : "+ColumnValue);
				  keywords.selectList(Column, ColumnValue);
			
				  break;
				  
			  }
			
		}
		
		return Resut;
	}
	
	
	 public String GetExcelData(String DataSheetName, String Columnname, String ColumnValue, String Data,String AddorEdit)throws AbstractMethodError,  NumberFormatException
	 {
		
		//System.out.println(DataSheetName+" : "+Columnname+" : "+ColumnValue+" : "+Data+" :"+AddorEdit);
		Xls_Reader current_TestCase_xls=null;
        String OutValues=null;
  	 	current_TestCase_xls=new Xls_Reader(System.getProperty("user.dir")+"//src//test//java//com//auto//res//xls//Agtech_TestData.xlsx");
  	 	
  	 	int RowNum=current_TestCase_xls.getCellRowNum(DataSheetName,Columnname,ColumnValue);
  	 	//System.out.println("Row exist for "+RowNum+" : "+DataSheetName+" : "+Columnname+" : "+ColumnValue);
			if (RowNum!=-1)
			{
				//System.out.println("EditFlag :"+AddorEdit+" Condition: "+(AddorEdit=="Edit"));
				if (AddorEdit=="Edit")
				{
					String Ediflag=current_TestCase_xls.getCellData(DataSheetName, UIConstants.TEST_STEPS_EDITFLAG, RowNum);
					//System.out.println("XCEL EditFlag : "+Ediflag+" Condition : "+(Ediflag.equalsIgnoreCase("Y"))+" Condition2 : "+(Ediflag=="Y"));
					if(Ediflag.equalsIgnoreCase("Y"))
					{
						OutValues=current_TestCase_xls.getCellData(DataSheetName, Data, RowNum);
					}
				}
				else
				{
					OutValues=current_TestCase_xls.getCellData(DataSheetName, Data, RowNum);	
				}
				
			}
			return OutValues;
	  }
	 
	 
	 public String getDynamicvalue(String RandomValue)
	 {
			//String ss="AutoAcct#random6";
		 String RetnResult;
		 if (RandomValue.contains("#random"))
		 {
	        String[] ArKeys = RandomValue.split("#random");
//	        System.out.println("Split Value : "+ArKeys[0]);
//	        System.out.println("Split Value2 : "+ArKeys[1]);
	        int NofValue = Integer.parseInt(ArKeys[1]);
			SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMddHHmmss");//dd/MM/yyyy
			Date now = new Date();
			String strDate = sdfDate.format(now);
		    System.out.println("Randam Full Value : "+strDate);//20160627172129
		    String GeneratedRmValue=StringUtils.right(strDate, NofValue);
			System.out.println("Randam Value : "+GeneratedRmValue);
			RetnResult=ArKeys[0]+GeneratedRmValue;
		 }
		 else
		 {
			 RetnResult=RandomValue;
		 }
		 return RetnResult;
	 }

}
