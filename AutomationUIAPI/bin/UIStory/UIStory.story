Narrative:
HCL regression test scenarios
Meta:
@UserRole Student

Scenario:1  A scenario login with  user and verify PTEID
Meta:
@Flow1
Given user login with below user details
When user clicks the Login button
Then user verify the login should be successfully
Examples: 
|URL																					|Username		|Password	|PTEID		|
|https://www3.pearsonvue.com/testtaker/signin/SignInPage.htm?clientCode=PEARSONLANGUAGE	|RaghuPearson	|Pearson1!	|PTE000552854|

Scenario:2  A scenario is verify the profile details
Meta:
@Flow1
Given the profile details
When user clicks the My Profile
Then verify the profile details
Examples: 
|Name				|DOB			|
|Mr Raghuraman KP	|12 October 1987|
