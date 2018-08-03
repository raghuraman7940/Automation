Narrative:
Pearson Next  regression test scenarios

Meta:
@UserRole Admin
@TestDataSheet PARCC_Student

Scenario:1  A scenario login with  PA user
Meta:
@Flow1
Given user provide the user details into PA Next
When user selects the Login button
Then verify the login should be successfully
Examples: 
|URL																	|Username		|Password	|
|https://pa-tst-core-main5-customer.pearsondev.com/cust5ui				|adminauto		|Test1234	|

Scenario:2  A scenario to create Student
Meta:
@Flow1
Given user select create and provide the student details
When user select the save button
Then verify the student details
Examples: 
|Enrolled_Organization	|State_Student_ID	|Local_State_ID	|LastName		|FirstName	|MiddleName	|Birthdate	|Gender	|Grade			|Resp_Sch_Code	|Ship_Organization	|Hispanic	|Race_Asian	|American_Indian	|African_American	|Native_Hawaiian	|White	|Two_More_Races	|English_Learner	|Title_III_Limited	|Gifted_Talented	|Migrant_Status	|Economic_Disadvantage	|Student_Disabilities	|
|LINN SCH 1				|					|				|LName			|Fname		|M			|12/10/1987	|Male	|Fourth Grade	|LINN SCH 1		|LINN SCH 1			|Yes		|Yes		|Yes				|Yes				|Yes				|Yes	|Yes			|Yes				|Not Collected		|Yes				|Yes			|Yes					|No						|
