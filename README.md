# Final-Year-Project-Management-System
NTU AY2022/2023 Semester 2 | SC2002/CE/CZ2002 Object-Oriented Design & Programming | Final Year Project Management System (FYPMS) Project
All details of the FYPMS were provided as the assignment provided by NTU AY22/23 SC2002 Coordinator Team.

## Description
The Final Year Project Management System (FYPMS) is an application for staffs and students to manage Final Year Projects (FYP). This collaborative group assignment challenged us to model, design, and develop an Object-Oriented application using Java as the programming language. Throughout the development process, we prioritized adherence to SOLID principles, enhancing the system's scalability, maintainability, and overall robustness.

## How to run the system
### Build and Run
Simply download the files into your local drive and open the project in your preferred IDE (e.g. Eclipse). Build the project, locate the main file `src/Assignment/MainApp.java` and run it. 
### Resetting the System Data
1. Locate the Excel sheets (e.g., `student_list.xlsx`, `faculty_list.xlsx`) containing the system data in the Library directory `Library/Excel_Sheets`.
2. Replace the current Excel sheets with the original ones provided `Library/Excel_Sheets_OG`.
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
+ Supervisors can create projects, modify his/her projects' title, view information of his/her projects.
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
+ Available Project information displays projectID, supervisor name, supervisor email address, project title, and status.
+ Allocated Project will also include student name, student Email address.
+ Rejected student requests will return the status of the project back to available
+ Deregistered projects are recycled and added back to the available project list.
+ When the student select a project, the status of the project will be changed to reserved and will be excluded from available project list. 

Data Handling
+ Data is stored and updated in Excel files, ensuring a seamless and structured management process.
+ Student list, faculty list and FYP coordinator information are uploaded using their respective files when system is initialized.
+ When the system runs at first time, the rollover project file is loaded to initialize the project list.

## UML Class Diagram


## Demonstration Video


## Report


## Team Members
