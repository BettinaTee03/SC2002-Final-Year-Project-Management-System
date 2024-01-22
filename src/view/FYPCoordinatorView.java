package view;

import java.util.ArrayList;
import java.util.Scanner;

import Assignment.Database;
import controller.FacultyController;
import controller.ProjectController;
import controller.RequestController;
import enums.FacultyStatus;
import enums.ProjectStatus;
import enums.RequestStatus;
import enums.RequestType;
import enums.StudentStatus;
import enums.UserType;
import helper.OptionChecker;
import model.Faculty;
import model.FacultyToFYPCoor;
import model.Project;
import model.StudentToFYPCoor;
import model.StudentToSupervisor;

/**
 * Displays the menu for the FYP Coordinator using the information provided by 
 * the controller classes {@link FacultyController}, {@link ProjectController}, 
 * and {@link RequestController}.
 * @author Andrew Cheam, Bettina Tee, Chung Zhi Xuan, Lebron Lim, Ting Ruo Chee
 * @version 1.0
 * @since 2023-04-15
 */
public class FYPCoordinatorView extends FacultyView {
	/** The controller for this FYP Coordinator. */
	private FacultyController facultyController;
	/** The user ID of this FYP Coordinator. */
	private String fypcoorID;
	/** A scanner object to read user input. */
	private Scanner scanner = new Scanner(System.in);
	/** Sets text colour to red for (*NEW*) notifications. */
	public static final String ANSI_RED = "\u001B[31m";
	/** Resets text colour to default. */
	public static final String ANSI_RESET = "\u001B[0m";
	
	/**
	 * Creates a new FYPCoordinatorView.
	 * @param fypcoorID The user ID of the FYP coordinator logging into the domain.
	 */
	public FYPCoordinatorView(String fypcoorID) {
		super(fypcoorID);
		this.fypcoorID = fypcoorID;
		facultyController = new FacultyController();
	}
	
	/**
	 * Displays the menu and notifications for this FYP Coordinator.
	 */
	public void displayMenu() {
		System.out.println("╔═════════════════════════════════════════════╗");
        System.out.println("║             FYPCoordinator Menu             ║");
    	System.out.println("╠═════════════════════════════════════════════╣");
        System.out.println("║ Please choose an option:                    ║");
        System.out.println("║ 0. Log Out                                  ║");
        System.out.println("║ 1. Change password                          ║");
        if(ProjectController.anyAllocatedProject(fypcoorID)) {
        	System.out.println("║ 2. Create/Update/View Project " + ANSI_RED + "(*NEW*)" + ANSI_RESET + "       ║");
        }
        else {
        	System.out.println("║ 2. Create/Update/View Project               ║");
        }
        if(RequestController.anyPendingStudentToFYPCoor()|| RequestController.anyPendingStudentChangingTitle()) {
        	System.out.println("║ 3. View Student Requests " + ANSI_RED + "(*NEW*)" + ANSI_RESET + "            ║");
        }
        else {
        	System.out.println("║ 3. View Student Requests                    ║");
        }
        if(RequestController.anyPendingSupervisorToFYPCoor()) {
        	System.out.println("║ 4. View Faculty Requests " + ANSI_RED + "(*NEW*)" + ANSI_RESET + "            ║");
        }
        else {
        	System.out.println("║ 4. View Faculty Requests                    ║");
        }
        System.out.println("║ 5. View Requests History                    ║");
        System.out.println("║ 6. View Projects based on Status/Supervisor ║");
        System.out.println("║ 7. Request to Transfer Student              ║");
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
			choice = OptionChecker.intOptionReturn(0,7);
	    	System.out.println();
	        LoginView login = new LoginView(UserType.FYPCOOR);
	        
	        switch(choice) {
			case 1:
	        	if (facultyController.changePassword(fypcoorID)==0)
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
				displayFacultyRequest();
				break;
			case 5:
				displayRequestHistory();
				break;
			case 6:
	        	System.out.println("╔═════════════════════════════════════════════╗");
	            System.out.println("║ View Projects by:                           ║");
	        	System.out.println("╠═════════════════════════════════════════════╣");
	        	System.out.println("║ 1. View Project based on Status             ║");
	        	System.out.println("║ 2. View Project based on Supervisor         ║");
	        	System.out.println("╚═════════════════════════════════════════════╝");
	        	System.out.print("Choice: ");
	            int option1 = OptionChecker.intOptionReturn(1, 2);
	        	
	        	switch(option1) {
	        	case 1:
		        	System.out.println("╔═════════════════════════════════════════════╗");
		            System.out.println("║ View Projects by Status:                    ║");
		        	System.out.println("╠═════════════════════════════════════════════╣");
		        	System.out.println("║ 1. AVAILABLE projects                       ║");
		        	System.out.println("║ 2. UNAVAILABLE projects                     ║");
		        	System.out.println("║ 3. ALLOCATED projects                       ║");
		        	System.out.println("║ 4. RESERVED projects                        ║");
		        	System.out.println("╚═════════════════════════════════════════════╝");
	            	System.out.print("Choice: ");
	            	
	            	int option2 = OptionChecker.intOptionReturn(1, 4);
	            	System.out.println();
	            	switch(option2) {
	            	case 1:
	            		ProjectController.displayProjectByStatus(ProjectStatus.AVAILABLE);
	            		break;
	            	case 2:
	            		ProjectController.displayProjectByStatus(ProjectStatus.UNAVAILABLE);
	            		break;
	            	case 3:
	            		ProjectController.displayProjectByStatus(ProjectStatus.ALLOCATED);
	            		break;
	            	case 4:         		
	            		ProjectController.displayProjectByStatus(ProjectStatus.RESERVED);
	            		break;
	            	}
	        		break;
	        	case 2:
	            	System.out.print("Enter Supervisor ID: ");
	            	String supervisorID = scanner.nextLine().trim();
	            	while(!Database.LOGINFACULTIES.containsKey(supervisorID))
	            	{
	            		System.out.println();
	            		System.out.println("╔═════════════════════════════════════════════╗");
	                    System.out.println("║ This Supervisor ID does not exist           ║");
	                	System.out.println("╠═════════════════════════════════════════════╣");
	                	System.out.println("║ Y. Re-enter Supervisor ID                   ║");
	                	System.out.println("║ N. Return to FYPCoordinator Menu            ║");
	                	System.out.println("╚═════════════════════════════════════════════╝");
	                	System.out.printf("Choice: ");
	                	String choiceExit = OptionChecker.ynOptionReturn();
	        			if (choiceExit.equals("n")) {
	        				this.displayApp();
	        				break;}
	        			else if (choiceExit.equals("y"))
	        			{
	        				System.out.printf("\nPlease re-enter Supervisor ID: ");
	        				supervisorID = scanner.nextLine();
	        			}
	            	}
	            	System.out.println();
	            	ProjectController.printProjectForFaculty(supervisorID);
	        		break;
	        	}
				break;
			case 7:
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
		}while (choice!=0);
	}
	
	/**
	 * Displays the requests made by all Students to this FYP Coordinator. 
	 * It then calls the display menu to approve a request.
	 */
    public void displayStudentRequest() {
    	//this is to check whether someone request Li Fang to select a project
    	ArrayList<StudentToFYPCoor> allStudentRequests = RequestController.getStudentToFYPCoorRequest();
    	int i=0;
    	while (i<allStudentRequests.size())
    	{
    		if (allStudentRequests.get(i).getRequestStatus()!=RequestStatus.PENDING)
    			allStudentRequests.remove(i);
    		else
    			i++;
    	}
    	
    	//this is to check whether someone request Li Fang to change title
    	ArrayList<StudentToSupervisor> suprequests = RequestController.getStudentToSupRequests();
    	ArrayList<StudentToSupervisor> pendingsupRequests = new ArrayList<StudentToSupervisor>();
    	for (StudentToSupervisor request : suprequests) {
    		if (RequestController.checkSupervisor(request).equals("ASFLI") && RequestController.checkRequestStatus(request) == RequestStatus.PENDING)
    			pendingsupRequests.add(request);
		}
    	
    	//if no requests at all
    	if(allStudentRequests.isEmpty() && pendingsupRequests.isEmpty()) {
			System.out.println("There are currently no pending requests from students");
		} 
    	else 
		{
    		if (!allStudentRequests.isEmpty())
    		{
    			System.out.println("╔══════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
        		System.out.println("║ Student Project Registering / Deregistering Pending Requests                                                     ║");
        		System.out.println("╠═══════════╦═══════════╦═════════════╦═════════════════════╦═══════════════╦══════════════════════════════════════╣");
    			System.out.printf("║ %-9s ║ %-9s ║ %-11s ║ %-19s ║ %-13s ║ %-36s ║%n", "RequestID", "Sender ID", "Sender Name", "Requested ProjectID", "Action", "Request Status");
    			System.out.println("╠═══════════╬═══════════╬═════════════╬═════════════════════╬═══════════════╬══════════════════════════════════════╣");
    			for(StudentToFYPCoor request : allStudentRequests) 
    				System.out.printf("║ %-9s ║ %-9s ║ %-11s ║ %-19s ║ %-13s ║ %-36s ║%n", request.getRequestID(), request.getCreator().getUserID(), request.getCreator().getName(), request.getRequestedProjectID(), request.getRequestType(), request.getRequestStatus());
    			System.out.println("╚═══════════╩═══════════╩═════════════╩═════════════════════╩═══════════════╩══════════════════════════════════════╝");
    		}
    		if (!pendingsupRequests.isEmpty())
    		{
    			System.out.println("╔══════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
        		System.out.println("║ Student Project Title Change Pending Requests                                                                    ║");
        		System.out.println("╠══════════════════════════╦═══════════════════════════════════════════════════════════════════════════════════════╣");
        		int j = pendingsupRequests.size();
        		for (StudentToSupervisor request : pendingsupRequests)
        		{
        			System.out.printf("║ RequestID                ║ %-85s ║%n", request.getRequestID());
        			System.out.printf("║ Sender ID                ║ %-85s ║%n", request.getCreator().getUserID());
        			System.out.printf("║ Requested ProjectID      ║ %-85s ║%n", request.getProject().getProjectID());
        			System.out.printf("║ Current Title            ║ %-85s ║%n", request.getOrigTitle());
        			System.out.printf("║ Requested Title          ║ %-85s ║%n", request.getNewTitle());
        			System.out.printf("║ Request Status           ║ %-85s ║%n", request.getRequestStatus());
        			j--;
        			if (j!=0)
        				System.out.println("╠══════════════════════════╬═══════════════════════════════════════════════════════════════════════════════════════╣");
                    
        		}
                System.out.println("╚══════════════════════════╩═══════════════════════════════════════════════════════════════════════════════════════╝");
                
    		}
    		ApproveStudentRequestMenu(allStudentRequests, pendingsupRequests);	
		}
    }
    
    /**
	 * Displays the requests made by all Faculty members to this FYP Coordinator. 
	 * It then calls the display menu to approve a request.
	 */
    public void displayFacultyRequest() {
    	
    	ArrayList<FacultyToFYPCoor> allFacultyRequests = RequestController.getFacultyRequests();
    	
    	int j=0;
    	while (j<allFacultyRequests.size())
    	{
    		if (allFacultyRequests.get(j).getRequestStatus()!=RequestStatus.PENDING)
    			allFacultyRequests.remove(j);
    		else
    			j++;
    	}
    	
    	if(allFacultyRequests.isEmpty()) {
			System.out.println("There are currently no pending requests from faculty");
		} 
    	else 
		{
    		System.out.println("╔═══════════╦═════════════════╦═══════════════════════════╦═════════════════════╦══════════════════════════╦════════════════╗");
    		System.out.printf("║ %-9s ║ %-15s ║ %-25s ║ %-19s ║ %-24s ║ %-14s ║%n", "RequestID", "Sender ID", "Sender Name", "Requested ProjectID", "Replacement SupervisorID", "Request Status");
    		System.out.println("╠═══════════╬═════════════════╬═══════════════════════════╬═════════════════════╬══════════════════════════╬════════════════╣");
			
    		for(FacultyToFYPCoor request : allFacultyRequests) 
    			System.out.printf("║ %-9s ║ %-15s ║ %-25s ║ %-19s ║ %-24s ║ %-14s ║%n", request.getRequestID(), request.getCreator().getUserID(), request.getCreator().getName(), request.getRequestedProjectID(), request.getTransferring(), request.getRequestStatus());
			System.out.println("╚═══════════╩═════════════════╩═══════════════════════════╩═════════════════════╩══════════════════════════╩════════════════╝");
			ApproveFacultyRequestMenu();
		}
    }
    
    /**
	 * Displays the menu for the FYP Coordinator to approve student requests to change project title. 
	 * @param selectedrequestID The request ID selected by the FYP Coordinator.
	 */
    public void ApproveProjectMenu(int selectedrequestID)
    {
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
           		System.out.println("Your project title has been changed.");
           		break;
        	case 2:
        		RequestController.updateRequestStatus(selectedrequestID, RequestStatus.REJECTED);
        		break;
        	case 0:
        		break;
        }
    }
    
	/**
	 * Displays the instructions for the FYP Coordinator to approve Student requests.
	 * @param allStudentRequests A list containing all student requests.
	 * @param pendingsupRequests A list containing student requests to change project title.
	 */
    public void ApproveStudentRequestMenu(ArrayList<StudentToFYPCoor> allStudentRequests, ArrayList<StudentToSupervisor> pendingsupRequests) {
    	
    	int selectedrequestID, found;
    	int option;
    	StudentToFYPCoor request = null;
    	StudentToSupervisor request2 = null;
    	
    	System.out.print("\nEnter requestID to approve/reject: ");
        
        while (true)
        {
        	selectedrequestID = OptionChecker.intOnlyOptionReturn();
        	found = 0;
        	for (StudentToFYPCoor req : allStudentRequests)
        	{
        		if (req.getRequestID() == selectedrequestID)
        		{
        			found = 1;
        			request = req;
        			break;
        		}
        	}
        	
        	if (found==0)
        	{
        		for (StudentToSupervisor req : pendingsupRequests)
            	{
            		if (req.getRequestID() == selectedrequestID)
            		{
            			found = 1;
            			request2 = req;
            			break;
            		}
            	}
        	}
        	
        	if (found==0)
        	{
        		System.out.println("\nInvalid requestID.");;
                System.out.printf("Re-enter requestID to approve/reject: ");
                continue;
        	}
        	
            if (request == null && request2.getRequestStatus() != RequestStatus.PENDING){
            	System.out.println("\nThis request has been approved or rejected.");
                System.out.printf("Re-enter requestID to approve/reject: ");
            	continue;
            }
            else if (request2 == null && request.getRequestStatus() != RequestStatus.PENDING){
            	System.out.println("\nThis request has been approved or rejected.");
                System.out.printf("Re-enter requestID to approve/reject: ");
            	continue;
            }
        	else
        		break;
        }
        
        if (request2 != null)
        {
        	ApproveProjectMenu(selectedrequestID);
        }
        else
        {
	        Project project = Database.PROJECTS.get(request.getRequestedProjectID());
		    
	        System.out.println("╔═════════════════════════════════════════════╗");
	        System.out.println("║                Approval Menu                ║");
	        System.out.println("╠═════════════════════════════════════════════╣");
	        System.out.println("║ 1. Approve Request                          ║");
	        System.out.println("║ 2. Reject Request                           ║");
	        System.out.println("║ 0. Return to FYPCoordinator Menu            ║");
	        System.out.println("╚═════════════════════════════════════════════╝");
	        System.out.print("Choice: ");
	        option = OptionChecker.intOptionReturn(0, 2);
	        if (option == 0) 
	        	return;
	        if(request.getRequestType() == RequestType.SELECTING) {
	        	if(project.getSupervisor().getStatus()==FacultyStatus.NOTFREE) {
	    	      	System.out.println("╔════════════════════════════════════════════════════════════════╗");
	                System.out.println("║ This supervisor is currently supervising 2 projects already.   ║");
	                System.out.println("║ You cannot approve this request.                               ║");
	                System.out.println("╠════════════════════════════════════════════════════════════════╣");
	                System.out.println("║ 1. Reject request                                              ║");
	                System.out.println("║ 0. Return to FYPCoordinator Menu                               ║");
	                System.out.println("╚════════════════════════════════════════════════════════════════╝");
	                System.out.print("Choice: ");
	                option = OptionChecker.intOptionReturn(0, 1);
	    		    if (option == 1)
	    		    {
	    		        System.out.print("\nRequest has been rejected.\n");
	    		        request.setRequestStatus(RequestStatus.REJECTED);
	    		        request.getCreator().setStatus(StudentStatus.NOTREGISTERED);
	    		        request.setViewed(false);
	    		    }
	    		    else 
	    		     	return;
	    	    }
	        	else
	        	{
		        	if(option == 1) {
		                ProjectController.allocateProject(request.getCreator().getUserID(), request.getRequestedProjectID());
		                request.setRequestStatus(RequestStatus.APPROVED);
		                request.setViewed(false);
		                Faculty supervisor = request.getProject().getSupervisor();
		                if(supervisor.getNumSupervised()>= 2) {
		                	ProjectController.setFacultyUnavail(supervisor.getUserID());
		                }
		            }
	                else if(option == 2) {
	                 	project.setProjectStatus(ProjectStatus.AVAILABLE);
	                   	request.setRequestStatus(RequestStatus.REJECTED);
	                   	request.getCreator().setStatus(StudentStatus.NOTREGISTERED);
	                   	request.setViewed(false);
	                }
	        	}
	        }
	        else if(request.getRequestType() == RequestType.DEREGISTERING) {
	            if (option == 1) {
	                project.setProjectStatus(ProjectStatus.AVAILABLE);
	                project.deregisterStudent(); //set student and studentID to null
	                project.getSupervisor().decNumSupervised();
	                if(project.getSupervisor().getNumSupervised()<2) {
	                	ProjectController.setFacultyavail(project.getSupervisor().getUserID());
	                }
	               	request.getCreator().setStatus(StudentStatus.DEREGISTERED);
	               	request.setRequestStatus(RequestStatus.APPROVED);
	               	request.deregister(project);
	            }
	            else if (option==2) 
	                request.setRequestStatus(RequestStatus.REJECTED);
	        }    	
        }
    }
    
    /**
     * Displays the instructions for the FYP Coordinator to approve Faculty requests.
     */
    public void ApproveFacultyRequestMenu() {
    	int selectedrequestID;
    	int option;
    	System.out.print("Enter requestID to approve/reject: ");
        
        while (true)
        {
        	selectedrequestID = OptionChecker.intOnlyOptionReturn();
        	if (!Database.FacultyREQUESTS.containsKey(selectedrequestID)) {
        		System.out.println("This requestID does not exist.");;
                System.out.printf("Re-enter requestID to approve/reject: ");
            }
        	else
        		break;
        }
        
        FacultyToFYPCoor request = Database.FacultyREQUESTS.get(selectedrequestID);
        Project project = Database.PROJECTS.get(request.getRequestedProjectID());
        
        if(Database.LOGINFACULTIES.get(request.getTransferring()).getStatus()== FacultyStatus.NOTFREE) {
        	System.out.println();
        	System.out.println("╔════════════════════════════════════════════════════════════════╗");
            System.out.println("║ This supervisor is currently supervising 2 projects already.   ║");
            System.out.println("║ You cannot approve this request.                               ║");
            System.out.println("╠════════════════════════════════════════════════════════════════╣");
            System.out.println("║ 1. Reject request                                              ║");
            System.out.println("║ 0. Return to FYPCoordinator Menu                               ║");
            System.out.println("╚════════════════════════════════════════════════════════════════╝");
            System.out.print("Choice: ");
            option = OptionChecker.intOptionReturn(0, 1);
		    if (option == 1)
		    {
		        System.out.print("\nRequest has been rejected.\n");
		        request.setRequestStatus(RequestStatus.REJECTED);
		        request.setViewed(false);
		    }
		    else 
		     	return;
        }
        else {
        	System.out.println("╔═════════════════════════════════════════════╗");
            System.out.println("║                Approval Menu                ║");
        	System.out.println("╠═════════════════════════════════════════════╣");
        	System.out.println("║ 1. Approve Request                          ║");
        	System.out.println("║ 2. Reject Request                           ║");
        	System.out.println("║ 0. Return to FYPCoordinator Menu            ║");
        	System.out.println("╚═════════════════════════════════════════════╝");
            System.out.print("Choice: ");
            option = OptionChecker.intOptionReturn(0, 2);
            if(option == 1) {
            	ProjectController.transferProject(request.getRequestedProjectID(), project.getSupervisor().getUserID(), request.getTransferring());
            	request.setRequestStatus(RequestStatus.APPROVED);
            	request.setViewed(false);
            	project.setViewedbyFaculty(false);
            }
            else if(option == 2) {
            	request.setRequestStatus(RequestStatus.REJECTED);
            	request.setViewed(false);
            }
            else if (option == 0) return;
        }   
       
    }
    
    /**
     * Displays the request history of this FYP Coordinator.
     */
    public void displayRequestHistory() {
    	//this is a list of requests from student to fypcoor as a supervisor (change title) 
    	ArrayList<StudentToSupervisor> allStudRequests = RequestController.getStudentToSupRequests();
    	//this is a list of requests from student to fypcoor (register/deregister) 
    	ArrayList<StudentToFYPCoor> allStudentFYPCRequests = RequestController.getStudentToFYPCoorRequest();
    	//this is a list of requests from supervisors to fypcoor (transfer)
    	ArrayList<FacultyToFYPCoor> allFacRequests = RequestController.getFacultyRequests();
    	int i;
    	

		// register/deregister display history
		if (allStudentFYPCRequests.size()==0)
		{
			System.out.println("╔══════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
    		System.out.println("║ There is no requests history from students register/deregister.                                                  ║");
    		System.out.println("╚══════════════════════════════════════════════════════════════════════════════════════════════════════════════════╝");
		}
		else
		{
			System.out.println("╔══════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
    		System.out.println("║ Register / Deregister Requests History                                                                           ║");
    		System.out.println("╠═══════════╦═══════════╦═════════════╦═════════════════════╦═══════════════╦══════════════════════════════════════╣");
			System.out.printf("║ %-9s ║ %-9s ║ %-11s ║ %-19s ║ %-13s ║ %-36s ║%n", "RequestID", "Sender ID", "Sender Name", "Requested ProjectID", "Action", "Request Status");
			System.out.println("╠═══════════╬═══════════╬═════════════╬═════════════════════╬═══════════════╬══════════════════════════════════════╣");
			for(StudentToFYPCoor request : allStudentFYPCRequests) 
				System.out.printf("║ %-9s ║ %-9s ║ %-11s ║ %-19s ║ %-13s ║ %-36s ║%n", request.getRequestID(), request.getCreator().getUserID(), request.getCreator().getName(), request.getRequestedProjectID(), request.getRequestType(), request.getRequestStatus());
			System.out.println("╚═══════════╩═══════════╩═════════════╩═════════════════════╩═══════════════╩══════════════════════════════════════╝");
    	}
		
    	
    	// change title display history
		if (allStudRequests.size()==0)
		{
			System.out.println("╔══════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
    		System.out.println("║ There is no requests history from students to change title.                                                      ║");
    		System.out.println("╚══════════════════════════════════════════════════════════════════════════════════════════════════════════════════╝");
		}
		else
		{
			System.out.println("╔══════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
    		System.out.println("║ Project Title Change Requests History                                                                            ║");
    		System.out.println("╠══════════════════════════╦═══════════════════════════════════════════════════════════════════════════════════════╣");
    		i = allStudRequests.size();
    		for (StudentToSupervisor request : allStudRequests)
    		{
    			System.out.printf("║ RequestID                ║ %-85s ║%n", request.getRequestID());
    			System.out.printf("║ Sender ID                ║ %-85s ║%n", request.getCreator().getUserID());
    			System.out.printf("║ Requested ProjectID      ║ %-85s ║%n", request.getProject().getProjectID());
    			System.out.printf("║ Original Title           ║ %-85s ║%n", request.getOrigTitle());
    			System.out.printf("║ Requested Title          ║ %-85s ║%n", request.getNewTitle());
    			System.out.printf("║ Request Status           ║ %-85s ║%n", request.getRequestStatus());
    			i--;
    			if (i!=0)
    				System.out.println("╠══════════════════════════╬═══════════════════════════════════════════════════════════════════════════════════════╣");
    		}
    		System.out.println("╚══════════════════════════╩═══════════════════════════════════════════════════════════════════════════════════════╝");
		}
		
		// transfer student display history
		if (allFacRequests.size()==0)
		{
			System.out.println("╔══════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
    		System.out.println("║ There is no requests history from supervisors to transfer student.                                               ║");
    		System.out.println("╚══════════════════════════════════════════════════════════════════════════════════════════════════════════════════╝");
		}
		else
		{
			System.out.println("╔══════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
    		System.out.println("║ Transfer Student Requests History                                                                                ║");
    		System.out.println("╠══════════════════════════╦═══════════════════════════════════════════════════════════════════════════════════════╣");
    		i = allFacRequests.size();
    		for (FacultyToFYPCoor request : allFacRequests)
    		{
    			System.out.printf("║ RequestID                ║ %-85s ║%n", request.getRequestID());
    			System.out.printf("║ Sender ID                ║ %-85s ║%n", request.getCreator().getUserID());
    			System.out.printf("║ Requested ProjectID      ║ %-85s ║%n", request.getRequestedProjectID());
    			System.out.printf("║ Replacement SupervisorID ║ %-85s ║%n", request.getTransferring());
    			System.out.printf("║ Request Status           ║ %-85s ║%n", request.getRequestStatus());
    			i--;
    			if (i!=0)
    				System.out.println("╠══════════════════════════╬═══════════════════════════════════════════════════════════════════════════════════════╣");
    		}
    		System.out.println("╚══════════════════════════╩═══════════════════════════════════════════════════════════════════════════════════════╝");
		}
    }
    	
}
