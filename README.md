# project-workcomms
Aurthors:  
- Jashanpreet Singh A01242196  
- Roswell Doria A00870116  
- Xiang Zhu A00795858  

project-workcomms created by GitHub Classroom.


Our application allows Companies to register their Name, License# and location.  

An employer may register at a workplace location and create employee accounts and create groups for them. 

Employers will be able to make posts that all groups will see and employees will be able to make posts only their assigned group may see.  

WorkComms post have live updating and will automatically update it's recycler view whenever a user makes a post.

# What we implmeneted
- We implemented user authentication for Firebase Auth.  
- Companies can register their company and duplicate Name and location may not be created!  
- Employers will be able to register under an existing registered company.  
- Employers will be able to register employees and groups under their company. 
- Employers can make posts that all employees under their company will see. 
- Employees can only view Employers posts and create posts to their assigned group within a company. 
- Live Recycler view updating whenever a post is made by a group or employer announcment.  

# What we could not implement as planned
- Security features to ensure Employers are from a company. As of right now, anyone can register as an Employer of a company!  
- Validation for when a company is registered that it's business license is valid. 
- Post deletions/hiding for posts made. 
- Users ability to upload their account pictures. 
- Post filtering 
- Displaying time stamp of each post.
- Facebook "Like" feature

# How to use our application:
The best demonstration of our application, is to run two emulators with two different accounts from the same company to demonstrate live updating.  
Below is instructions on how to use an existing accounts that we have provided.  
  
1. USING AN EXISTING ACCOUNT TO CREATE A POST:  
   - STEP1: Click login  
   - STEP2: Login as one of the Employer accounts detailed below  
   - STEP3: Click on post and Enter a post message into the Alert box and click post.  

2. REGISTERING A NEW EMPLOYEE AS AN EMPLOYEER:  
   - STEP1: Click on "Add Employee" on the bottom nav bar  
   - STEP2: Enter Emplyoees name and email  
   - STEP3: Select a group to assign them to from the spinner  
   - STEP4: Select the designated roll from radial buttons  
   - STEP5: Click on "Register Employee"  
   - STEP6: Logout (Normally an Employee would be able to login with this email and finalize the registration process)  

3. FINALIZING EMPLOYEE REGISTRATION:  
   - STEP7: Login using the same email that was used when used on STEP2 (The password that you enter here will be registered)  
   - STEP8: You will be logged in as an employee and can click on "Group Chat" on the bottom nav to see your group chat  
   - STEP9: Click on "Add to Group Chat" and type a message inside the alert box and click "Send" to make a post.  

# Employer Accounts

- email: ross@bcit.test.ca
  - Pass: rossbcittest  
- email: tom@walmart.ca
  - Pass: tommanager  

# Employee Accounts
- email: sean@bcit.test.ca
  - Pass: seanbcittest   
- email: jashan@bcit.test.ca
  - Pass: jashanbcittest  
- email: chris@walmart.ca
  - Pass: chrisassociate  
- email: jeff@walmart.ca
  - Pass: jeffassociate  
