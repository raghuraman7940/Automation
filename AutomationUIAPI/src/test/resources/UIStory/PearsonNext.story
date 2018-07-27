Narrative:
Pearson Next  regression test scenarios
Meta:
@UserRole Admin
Scenario:1  A scenario login with  PA user
Meta:
@Flow1
Given user provide the user details into PA Next
When user selects the Login button
Then verify the login should be successfully
Examples: 
|URL																	|Username		|Password	|
|https://pa-tst-core-main5-customer.pearsondev.com/cust5ui				|admin			|admin		|

Meta:
@UserRole Admin
Scenario:2  A scenario to create Student
Meta:
@Flow1
Given user select create and provide the student details
When user select the save button
Then verify the student details
Examples: 
|Enrolled_Organization	|LastName		|FirstName	|MiddleName	|Birthdate	|Gender	|State_Student_ID	|Local_State_ID	|Grade	|Hispanic	|Race_Asian	|American_Indian	|African_American	|Native_Hawaiian	|White	|Two_More_Races	|English_Learner	|Title_III_Limited	|Gifted_Talented	|Migrant_Status	|Economic_Disadvantage	|Student_Disabilities	|
|SCHOOL A				|admin			|admin		|S			|12/10/1987	|M		|S101111			|				|		|			|			|					|					|					|		|				|					|					|					|				|						|						|
		
