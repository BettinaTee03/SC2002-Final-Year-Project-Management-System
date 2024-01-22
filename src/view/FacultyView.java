package view;
import java.util.ArrayList;
import java.util.Scanner;

import controller.FacultyController;
import controller.ProjectController;
import controller.RequestController;
import enums.ProjectStatus;
import enums.RequestStatus;
import enums.UserType;
import helper.OptionChecker;
import model.FacultyToFYPCoor;
import model.Request;
import model.StudentToSupervisor;

/**
 * Displays the menu for Faculty members using the information provided by 
 * the controller classes {@link FacultyController}, {@link ProjectController}, 
 * and {@link RequestController}.
 * @author Andrew Cheam, Bettina Tee, Chung Zhi Xuan, Lebron Lim, Ting Ruo Chee
 * @version 1.0
 * @since 2023-04-15
 */

public class FacultyView extends UserView{
	/** The controller for this Faculty. */
	private FacultyController facultyController;
	/** The user ID of this Faculty. */
	private String supervisorID;
	/** A scanner object to read user input. */
	private Scanner scanner = new Scanner(System.in);
	/** Sets text colour to red for (*NEW*) notifications. */
	public static final String ANSI_RED = "\u001B[31m";
	/** Resets text colour to default. */
	public static final String ANSI_RESET = "\u001B[0m";
	
	/**
	 * Creates a new FacultyView.
	 * @param supervisorID The user ID of the faculty logging into the domain.
	 */
	public FacultyView(String supervisorID) {
        super();
        this.supervisorID = supervisorID;
        facultyController = new FacultyController();
    }
	/**
	 * Displays the menu and notifications for this Faculty.
	 */
    public void displayMenu() {
		System.out.println("╔═════════════════════════════════════════════╗");
        System.out.println("║                 Faculty Menu                ║");
    	System.out.println("╠═════════════════════════════════════════════╣");
        System.out.println("║ Please choose an option:                    ║");
        System.out.println("║ 0. Log Out                                  ║");
        System.out.println("║ 1. Change password                          ║");
        if(ProjectController.anyAllocatedProject(supervisorID)) 
        	System.out.println("║ 2. Create/Update/View Project " + ANSI_RED + "(*NEW*)" + ANSI_RESET + "       ║");
        else 
        	System.out.println("║ 2. Create/Update/View Project               ║");
        if(RequestController.anyPendingStudentToSupervisor()) 
        	System.out.println("║ 3. View Student Requests " + ANSI_RED + "(*NEW*)" + ANSI_RESET + "            ║");
        else 
        	System.out.println("║ 3. View Student Requests                    ║");
        if(RequestController.anyFacultyChangedRequest(supervisorID)) 
        	System.out.println("║ 4. View Request History " + ANSI_RED + "(*NEW*)" + ANSI_RESET + "             ║");
        else 
        	System.out.println("║ 4. View Request History                     ║");
        System.out.println("║ 5. Request to Transfer Student              ║");
    	System.out.println("╚═════════════════════════════════════════════╝");
        System.out.printf("Choice: ");
    }
	
    /**
     * Application to display the menu, read user input and call the selected task.
     */
    public void displayApp() {
    	int choice;
    	do
    	{
    		displayMenu();
    		choice=OptionChecker.intOptionReturn(0,5);
	    	System.out.println();
	        LoginView login = new LoginView(UserType.FACULTY);
	        
	        switch(choice) {
	            case 1:
	            	if (facultyController.changePassword(supervisorID)==0)
	            	{
		        		login.displayMenu();
		        		return;
		        	}
		            break;
	            case 2:
	            	manageProjectView();
	                break;
	            case 3:
	            	displayStudentRequest();
	    			break;
	            case 4:
	    			displayRequestHistory();
	            	break;
	            case 5:
	            	transferStudentView();
	            	break;
	            case 0:
	            	System.out.println("╔═════════════════════════════════════════════╗");
	                System.out.println("║               Logging out...                ║");
	        		System.out.println("╚═════════════════════════════════════════════╝");
	            	login.displayMenu();
	                break;
	        }
	        System.out.println();
    	} while (choice!=0);

    }
	
	/**
	 * Displays a project menu when the Faculty wants to create, update, or view their projects. 
	 * It then reads user input and calls the selected task.
	 */
	public void manageProjectView() {
		System.out.println("╔═════════════════════════════════════════════╗");
        System.out.println("║                 Project Menu                ║");
    	System.out.println("╠═════════════════════════════════════════════╣");
    	System.out.println("║ 1. Create Project                           ║");
    	System.out.println("║ 2. Update Project                           ║");
    	if(ProjectController.anyAllocatedProject(supervisorID)) 
        	System.out.println("║ 3. View My Projects " + ANSI_RED + "(*NEW*)" + ANSI_RESET + "                 ║");
    	else
    		System.out.println("║ 3. View My Projects                         ║");
    	System.out.println("╚═════════════════════════════════════════════╝");
		System.out.print("Choice: ");
		
		int option = OptionChecker.intOptionReturn(1,3);
    	
    	switch(option) {
    	case 1:
    		createProjectView();
    		break;
    	case 2:
    		updateProjectView();
    		break;
    	case 3:
    		System.out.println();
    		ProjectController.printProjectForFaculty(supervisorID);
    		break;
    	}
	}
	
	/**
	 * Displays the instructions for the Faculty to create a new project. 
	 * It will repeatedly ask for a new project title if the one entered already exists.
	 */
	public void createProjectView()
	{
		System.out.print("\nPlease enter your project title: ");
		String newproject = scanner.nextLine();
		ArrayList<String> allProjectsTitle = ProjectController.getAllProjectsTitle();
		while (allProjectsTitle.contains(newproject))
		{
			System.out.print("\nThis project title already exists\n");
			System.out.print("Please enter another project title: ");
			newproject = scanner.nextLine();
		}
		ProjectController.createProject(newproject, supervisorID);
		System.out.print("\nYour project has been created.\n");
	}
	
	/**
	 * Displays the instructions for the Faculty to change the title of their project. 
	 * It will repeatedly ask for a new title if the one entered already exists.
	 */
	public void updateProjectView()
	{
		System.out.println();
		ProjectController.printProjectForFaculty(supervisorID);
		if(ProjectController.getSupervisorProjects(supervisorID).isEmpty()) {
			System.out.print(" ");
		}
		else {
			System.out.println();
			System.out.print("Enter projectID that you want to update the title of: ");
			int requestedprojectID;
			while (true)
	        {
	        	requestedprojectID = OptionChecker.intOnlyOptionReturn();
	        	if (!ProjectController.getSupervisorProjectsID(supervisorID).contains(requestedprojectID))
	        	{
	        		System.out.println("\nThis projectID does not exist in your list of projects.");
	                System.out.printf("Re-enter projectID: ");
	        	}
	        	else
	        		break;
	        }    	
	        
	        System.out.print("\nEnter your new Project Title: ");
	        String newtitle = scanner.nextLine();
	        
	        ArrayList<String> allProjectsTitle = ProjectController.getAllProjectsTitle();
			while (allProjectsTitle.contains(newtitle))
			{
				System.out.println();
				System.out.println("This project title already exists");
				System.out.print("Please enter another project title: ");
				newtitle = scanner.nextLine();
			}
	        ProjectController.updateProjectTitle(requestedprojectID, newtitle);
	        System.out.print("\nYou have successfully updated your project.\n");
		}	
	}
	
	/**
	 * Displays the requests made by all Students to this Faculty. 
	 * It then calls the display menu to approve a request.
	 */
	public void displayStudentRequest() {
    	ArrayList<StudentToSupervisor> suprequests = RequestController.getStudentToSupRequests();
    	ArrayList<StudentToSupervisor> pendingsupRequests = new ArrayList<StudentToSupervisor>();
    	for (StudentToSupervisor request : suprequests) {
    		if (RequestController.checkSupervisor(request).equals(supervisorID) && RequestController.checkRequestStatus(request) == RequestStatus.PENDING)
    			pendingsupRequests.add(request);
		}
    	if (pendingsupRequests.isEmpty())
    	{
    		
    		System.out.println("There are no pending requests for you");
    	}
    	else
    	{
    		RequestController.printStudentToSupRequest(pendingsupRequests);
            ApproveProjectMenu();	
    	}
	}
	
	/**
	 * Displays the menu for the Faculty to approve Student requests. 
	 * It then reads user input and executes the selected task.
	 */
    public void ApproveProjectMenu()
    {
    	System.out.print("Enter requestID: ");
    	int selectedrequestID;
    	while (true)
    	{
    		selectedrequestID = OptionChecker.intOnlyOptionReturn();
    		if(!RequestController.checkRequestExistsStudToSup(selectedrequestID))
    		{
    			System.out.println("This request ID does not exist");
    			System.out.print("Re-enter request ID: ");
    		}
    		else if (RequestController.checkRequestStatus(selectedrequestID) != RequestStatus.PENDING)
    		{
    			System.out.println("This request has been approved or rejected");
    			System.out.print("Re-enter request ID: ");
    		}
    		else
    			break;
    	}
    	System.out.println("╔═════════════════════════════════════════════╗");
        System.out.println("║                Approval Menu                ║");
    	System.out.println("╠═════════════════════════════════════════════╣");
    	System.out.println("║ 1. Approve Project Title Change             ║");
    	System.out.println("║ 2. Reject Project Title Change              ║");
    	System.out.println("║ 0. Return to Faculty Menu                   ║");
    	System.out.println("╚═════════════════════════════════════════════╝");
    	System.out.print("Choice: ");
    	
        int option = OptionChecker.intOptionReturn(0, 2);
        switch (option)
        {
        	case 1:
        		RequestController.updateRequestStatus(selectedrequestID, RequestStatus.APPROVED);
           		ProjectController.updateStudentProjectTitle(selectedrequestID, RequestController.checkNewTitleProposed(selectedrequestID));
           		RequestController.setRequestViewFalse(selectedrequestID);
           		System.out.println("Your project title has been changed.");
           		break;
        	case 2:
        		RequestController.updateRequestStatus(selectedrequestID, RequestStatus.REJECTED);
        		RequestController.setRequestViewFalse(selectedrequestID);
        		break;
        	case 0:
        		break;
        }
    }
    
    /**
     * Displays the request history of this Faculty.
     */
    public void displayRequestHistory() {
    	//this is a list of requests from student to specific supervisor
    	ArrayList<StudentToSupervisor> allStudRequests = RequestController.getStudentToSupRequests();
    	//this is a list of requests from specific supervisor to FYPCoordinator
    	ArrayList<FacultyToFYPCoor> allFacRequests = RequestController.getFacultyRequests();

		RequestController.filterSupervisorInRequest(allStudRequests, supervisorID);
		
		if (allStudRequests.size()==0)
		{
			System.out.println("╔══════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
    		System.out.println("║ You have no incoming requests history.                                                                           ║");
    		System.out.println("╚══════════════════════════════════════════════════════════════════════════════════════════════════════════════════╝");
		}
		else {
			RequestController.printFacultyIncomingRequest(allStudRequests);
		}	
		
		RequestController.filterSupervisorOutRequest(allFacRequests, supervisorID);
		
		if (allFacRequests.size()==0)
		{
			System.out.println("╔══════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
    		System.out.println("║ You have no outgoing requests history.                                                                           ║");
    		System.out.println("╚══════════════════════════════════════════════════════════════════════════════════════════════════════════════════╝");
		}
		else {
			RequestController.printFacultyOutgoingRequest(allFacRequests);
		}
    }
    
    /**
     * Displays the instructions for the Faculty to transfer their project.
     */
    public void transferStudentView() 
    { 
    	if (!FacultyController.isSupervising(supervisorID)) { 
    		System.out.println("You are currently not supervising any projects."); 
    		return; 
    	} 
    	ProjectController.printProjectForFaculty(supervisorID, ProjectStatus.ALLOCATED); 
    	System.out.print("Enter projectID to request transfer: "); 
    	int duplicate; 
    	int requestedprojectID; 
    	while (true) 
    	{ 
    		duplicate = 0; 
    		requestedprojectID = OptionChecker.intOnlyOptionReturn(); 
    		for (Request request : RequestController.getMyRequests(supervisorID)) 
    		{ 
    			if (request.getRequestedProjectID() == requestedprojectID && RequestController.checkRequestStatus(request) == RequestStatus.PENDING) 
    			{ 
    				System.out.println("╔═════════════════════════════════════════════╗");
                    System.out.println("║ A transfer request for this project has     ║");
                    System.out.println("║ already been sent.                          ║");
                	System.out.println("╠═════════════════════════════════════════════╣");
                	System.out.println("║ 1. Re-enter projectID                       ║");
                	System.out.println("║ 2. Return to Faculty Menu                   ║");
                	System.out.println("╚═════════════════════════════════════════════╝");
    				System.out.printf("Choice: ");
    				int choice = OptionChecker.intOptionReturn(1, 2);
    				if (choice == 2)
    					return;
    				duplicate = 1; 
    				break; 
    			} 

    		} 
    		if (duplicate == 1) 
    		{
    			System.out.print("Re-enter projectID: ");
    			continue; 
    		}
    		if (!ProjectController.getSupervisorProjectsID(supervisorID, ProjectStatus.ALLOCATED).contains(requestedprojectID)) 
    		{ 
            	System.out.println("╔═════════════════════════════════════════════╗");
                System.out.println("║ This projectID does not exist in your list  ║");
                System.out.println("║ of supervised projects.                     ║");
            	System.out.println("╠═════════════════════════════════════════════╣");
            	System.out.println("║ 1. Re-enter projectID                       ║");
            	System.out.println("║ 2. Return to Faculty Menu                   ║");
            	System.out.println("╚═════════════════════════════════════════════╝");
				System.out.printf("Choice: ");
				int choice = OptionChecker.intOptionReturn(1, 2);
				if (choice == 2)
					return;
    		} 
    		else 
    			break; 
    	}
    	
    	FacultyController.printSupervisorList(supervisorID); 
    	System.out.printf("\nEnter supervisorID to request transferring to: "); 
    	String transferringSupervisorID = scanner.next(); 
    	scanner.nextLine(); 

    	while (true)
    	{
    		System.out.println(); 
    		if(!FacultyController.checkFacultyExists(transferringSupervisorID)) 
	    		System.out.println("This Supervisor ID does not exist"); 
    		else if(transferringSupervisorID.equals(supervisorID)) 
	    		System.out.println("You cannot transfer to yourself!"); 
    		else
    			break;
    		System.out.printf("Please re-enter Supervisor ID: "); 
    		transferringSupervisorID = scanner.nextLine();  
    	}
    	RequestController.createTransferRequest(requestedprojectID, supervisorID, transferringSupervisorID);
    }

}
