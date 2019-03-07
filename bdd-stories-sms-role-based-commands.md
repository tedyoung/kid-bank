# SMS Role-Based Commands

Parent should be able to DEPOSIT or SPEND when texting from Parent's Phone Number

Given Parent phone number is registered
When parent sends "deposit 12.50"
and From Parent phone number
Then $12.50 is added to account
And message of "$12.50 was deposited, and account balance is now $12.50" 


When parent sends "deposit 7.25 bottle return"
Then new deposit transaction with $7.25 and source is "bottle return"

----

Kid or Parent should be able to check BALANCE when texting from their Phone Number
