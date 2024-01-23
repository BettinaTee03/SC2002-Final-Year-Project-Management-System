# Final-Year-Project-Management-System
NTU AY2022/2023 Semester 2 | SC2002/CE/CZ2002 Object-Oriented Design & Programming | Final Year Project Management System (FYPMS) Project
All details and specifications of the FYPMS were provided as the assignment released by the NTU AY22/23 SC2002 Coordinator Team.

## Description
The Final Year Project Management System (FYPMS) is an application for staff and students to manage Final Year Projects (FYP). This collaborative group assignment challenged us to model, design, and develop an Object-Oriented application using Java as the programming language. Throughout the development process, we prioritized adherence to SOLID principles, enhancing the system's scalability, maintainability, and overall robustness.

## How to run the system
### Build and Run
Simply download the files to your local drive and open the project in your preferred IDE (e.g. Eclipse). Build the project, locate the main file `src/Assignment/MainApp.java` and run it. 
### Resetting the System Data
1. Locate the Excel sheets (e.g., `student_list.xlsx`, `faculty_list.xlsx`) containing the system data in the Library directory `Library/Excel_Sheets`.
2. Replace the current Excel sheets with the original ones provided in `Library/Excel_Sheets_OG`.
3. Ensure that the system is not running during this process.
4. Run the system again following the steps mentioned above.
### Notes
- If you encounter any issues during the setup or have questions, please feel free to reach out to any of the team members for assistance.

## System Functionalities Details
User Authentication
+ Users, including supervisors, students, and the FYP coordinator (who is a supervisor as well), can log in using their NTU network user ID and the default password "password"
+ Users have the option to change their passwords within the system.

Student Features
+ Students can view available projects, select a project and request allocation.
+ After the project registration is done, students can view their project, propose a title change and request deregistration.
+ Students can view their requests' history and status.

Supervisor Features
+ Supervisors can create projects, modify his/her projects' titles, and view information about his/her projects.
+ Supervisors can modify project titles upon approving student's request.
+ Supervisors can supervise up to 2 projects. When the cap is reached, his/her remaining projects will be listed as unavailable
+ Supervisors can request for transfer of students to replacement supervisors.
+ Supervisors can view all the pending requests from students and approve/reject them.
+ A supervisor can view his incoming and outgoing requests’ history and status. 

FYP Coordinator Features
+ Coordinator manages project allocation, supervisor changes, and student deregistration.
+ FYP Coordinator can generate project details reports based on various filters.
+ FYP Coordinator can view and approve/reject requests from supervisors and students.
+ FYP Coordinator can view requests' history and status.

Final Year Project (FYP) Details
+ FYP is an individual work.
+ Project selection is based on a first-come-first-serve basis.
+ Project status includes available, reserved, unavailable, or allocated.
+ Available Project information displays the projectID, supervisor name, supervisor email address, project title, and status.
+ Allocated Project will also include the student name, student Email address.
+ Rejected student requests will return the status of the project to available
+ Deregistered projects are recycled and added back to the available project list.
+ When the student selects a project, the status of the project will be changed to reserved and will be excluded from the available projects list. 

Data Handling
+ Data is stored and updated in Excel files, ensuring a seamless and structured management process.
+ Student list, faculty list and FYP coordinator information are uploaded using their respective files when the system is initialized.
+ When the system runs for the first time, the rollover project file is loaded to initialize the project list.

## UML Class Diagram
This UML Class Diagram shows the Entity, Control and Boundary classes that are used in the system.
![A33-grp1](https://github.com/BettinaTee03/Final-Year-Project-Management-System/assets/127083047/2e122dad-01f0-46e1-903b-8ab93628e374)

## Demonstration Video
Our demonstration video can be found at this link! 
https://www.youtube.com/watch?v=zZ5IHYYbWF4 

## Report
Our report consists of
+ Design considerations
  + Model View Controller
  + SOLID Design Principles
  + OO Concepts
+ Detailed UML Class Diagram
+ Testing (various test cases and results)
+ Reflections
  + difficulties encountered
  + resolving difficulties
  + learning points
  + future recommendations

Our report can be downloaded here: [report.pdf](https://github.com/BettinaTee03/Final-Year-Project-Management-System/files/14012532/report.pdf)

## Team Members
We are Lab Group A33, Group 1, consisting of:
| Name | GitHub ID |
| -----|-----------|
| Cheam Zhong Sheng Andrew | @cheamy |
| Chung Zhi Xuan | @spaceman03 |
| Lim Jing Jie | @bron322 | 
| Tee Qin Tong Bettina | @BettinaTee03 |
| Ting Ruo Chee | @ruochee723 |
