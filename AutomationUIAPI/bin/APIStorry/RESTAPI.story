Narrative:
API Automation regression test scenarios
Meta:
@TestDataSheet Cropsnap

!-- Sales Scenarios--!

Scenario:1  JSON status field valation 
Meta:
@Flow1	 
Given the Input sheet D:\WC\Restassured\src\res\Automation_Run_Report.xlsx
When the execute the JSON
Then Validate with expected result and store in excel

Scenario:Evaluate Get Method
Meta:
@Flow2
Given the API Request input details
When execute the GET Method
Then Validate the respone with expected values
Examples: 
|Server						|URL			|MethodType	|Header_Keys	|Header_Values		|Param_Keys	|Param_Values	|Expected_Keys		|Expected_Values|ExpectedStatusCode	|ExpectedSchemaPath	|
|http://10.146.217.20:3030	|/api/v1/device	|GET		|Content-Type	|application/json	|NA			|NA				|					|				|200				|					 |

