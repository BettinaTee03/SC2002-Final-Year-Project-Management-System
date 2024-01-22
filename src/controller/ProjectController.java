package controller;
import java.util.ArrayList;

import Assignment.Database;
import enums.FacultyStatus;
import enums.ProjectStatus;
import enums.RequestStatus;
import enums.StudentStatus;
import model.Faculty;
import model.Project;
import model.Student;
import model.StudentToFYPCoor;
import model.StudentToSupervisor;
import view.FacultyView;
import view.FYPCoordinatorView;
import view.StudentView;

/** 
 * A controller class that acts as a "middle man" between the
 * view classes {@link FacultyView}, {@link FYPCoordinatorView}, 
 * {@link StudentView} and the model class {@link Project}. <p>
 * 
 * It can create, update or filter {@link Project} details.
 * @author Andrew Cheam, Bettina Tee, Chung Zhi Xuan, Lebron Lim, Ting Ruo Chee
 * @version 1.0
 * @since 2023-04-09
 */

public class ProjectController {
	/**
     * Creates a new ProjectController.
     */
    public ProjectController() {
    	
    }

    /**
     * Creates a new Project in the ProjectController.
     * @param newProject The title of the new project.
     * @param supervisorID The user ID of the project creator.
     */
    public static void createProject(String newProject, String supervisorID) {
    	Project project = new Project(newProject, supervisorID, "NULL", "AVAILABLE", false);
		project.setSupervisor(Database.LOGINFACULTIES.get(supervisorID));
    	Database.PROJECTS.put(project.getProjectID(),project);
    	if(project.getSupervisor().getStatus() == FacultyStatus.NOTFREE) {
    		project.setProjectStatus(ProjectStatus.UNAVAILABLE);
    	}
    }
    
    /**
     * Updates the title of the project. 
     * This is when the faculty wants to change the title of their own project.
     * @param projectID The project ID of the project to be updated.
     * @param newTitle The new title of the project.
     */
    public static void updateProjectTitle(int projectID, String newTitle) {
    	Project project = Database.PROJECTS.get(projectID);
    	project.setProjectTitle(newTitle);
    }
    
    /**
     * Updates the title of the project.
     * This is when a student requests to change the title of their allocated project.
     * @param requestID The request ID of the request made by the student.
     * @param newTitle The new title of the project.
     */
    public static void updateStudentProjectTitle(int requestID, String newTitle) {
    	int projectID = Database.REQUESTS.get(requestID).getRequestedProjectID();
    	Project project = Database.PROJECTS.get(projectID);
    	StudentToSupervisor request = Database.StudentToSupREQUESTS.get(requestID);
    	request.setOrigTitle(project.getProjectTitle());
    	request.setNewTitle(newTitle);
    	project.setProjectTitle(newTitle);
    }
    
    /**
     * Allocates a project to a student.
     * @param studentID The user ID of the student.
     * @param projectID The project ID of the project that the student is requesting for.
     */
    public static void allocateProject(String studentID, int projectID)
    {
    	Student student = Database.STUDENTS.get(studentID);
    	Project project = Database.PROJECTS.get(projectID);
    	project.setProjectStatus(ProjectStatus.ALLOCATED);
    	project.getSupervisor().incNumSupervised();
    	project.setStudent(student);
    	student.setStatus(StudentStatus.REGISTERED);
    	student.setProject(project);
    }
    
    /**
     * Transfers a project from a supervisor to another.
     * @param projectID The project ID of the project being transferred.
     * @param senderID The user ID of the supervisor requesting to transfer.
     * @param receiverID The user ID of the new supervisor.
     */
    public static void transferProject(int projectID, String senderID, String receiverID)
    {
    	Project project = Database.PROJECTS.get(projectID);
    	Faculty sender = Database.LOGINFACULTIES.get(senderID);
    	Faculty receiver = Database.LOGINFACULTIES.get(receiverID);
    	
    	sender.decNumSupervised();
    	if(sender.getNumSupervised() < 2) {
    		setFacultyavail(senderID);
    	}
    	project.setSupervisor(receiver);
    	receiver.incNumSupervised();
    	if(receiver.getNumSupervised()>= 2) {
    		setFacultyUnavail(receiverID);
    	}
    }
    
    /**
     * Gets a list of all the projects.
     * @return The list of {@link Project} objects.
     */
    public static ArrayList<Project> getAllProjects() {
    	ArrayList<Project> allProjects = new ArrayList<>();
    	for(Project project: Database.PROJECTS.values()) {
    		allProjects.add(project);
    	}
    	return allProjects;
    }
    
    /**
     * Gets a list of projects by project status.
     * @param status The status to be filtered.
     * @return The list of {@link Project} objects with the corresponding status.
     */
    public static ArrayList<Project> getProjectByStatus(ProjectStatus status){
    	ArrayList<Project> projectStatus = new ArrayList<>();
    	for(Project project:Database.PROJECTS.values()) {
    		if(project.getProjectStatus().equals(status)) {
    			projectStatus.add(project);
    		}
    	}
    	return projectStatus;
    }
    
    /**
     * Gets the titles of all the projects.
     * @return The list of project titles.
     */
    public static ArrayList<String> getAllProjectsTitle() {
    	ArrayList<String> allProjectsTitle = new ArrayList<>();
    	for(Project project: Database.PROJECTS.values()) {
    		allProjectsTitle.add(project.getProjectTitle());
    	}
    	return allProjectsTitle;
    }
    
    /**
     * Gets the project that is allocated to a specific Student.
     * @param studentID The user ID of the student.
     * @return projectID The project ID of the allocated project.
     */
    public static int getStudentRegisteredProject(String studentID) {
    	int projectID = 0;
        for(Project project: Database.PROJECTS.values()) {
        	if(project.getProjectStatus()==ProjectStatus.ALLOCATED && project.getStudent().getUserID().equals(studentID)) {
        		projectID = project.getProjectID();
        	}
        }
        return projectID;
    }
    
    /**
     * Gets all the projects under a specific Supervisor.
     * @param supervisorID The user ID of the supervisor.
     * @return The list of {@link Project} objects that corresponds to the supervisor.
     */
    public static ArrayList<Project> getSupervisorProjects(String supervisorID){
    	ArrayList<Project> myProjects = new ArrayList<>();
    	for(Project project:Database.PROJECTS.values()) {
    		if(project.getSupervisor().getUserID().equals(supervisorID)) {
    			myProjects.add(project);
    		}
    	}
    	return myProjects;
    }
    
    /**
     * Gets the projects under a Supervisor with the given project status.
     * @param supervisorID The user ID of the supervisor.
     * @param status The status of the project that is to be filtered.
     * @return The list of {@link Project} objects that corresponds to the supervisor and project status.
     */
    public static ArrayList<Project> getSupervisorProjects(String supervisorID, ProjectStatus status){
    	ArrayList<Project> myProjects = new ArrayList<>();
    	for(Project project:Database.PROJECTS.values()) {
    		if(project.getSupervisor().getUserID().equals(supervisorID) && project.getProjectStatus() == status) {
    			myProjects.add(project);
    		}
    	}
    	return myProjects;
    }
    
    /**
     * Gets the project IDs under a specific Supervisor.
     * @param supervisorID The user ID of the supervisor.
     * @return The list of projectIDs that corresponds to the supervisor.
     */
    public static ArrayList<Integer> getSupervisorProjectsID(String supervisorID){
    	ArrayList<Integer> myProjects = new ArrayList<>();
    	for(Project project:Database.PROJECTS.values()) {
    		if(project.getSupervisor().getUserID().equals(supervisorID)) {
    			myProjects.add(project.getProjectID());
    		}
    	}
    	return myProjects;
    }
    
    /**
     * Gets the project IDs under a Supervisor with the given project status.
     * @param supervisorID The user ID of the supervisor.
     * @param status The status of the project that is to be filtered.
     * @return The list of projectIDs that corresponds to the supervisor and project status.
     */
    public static ArrayList<Integer> getSupervisorProjectsID(String supervisorID, ProjectStatus status){
    	ArrayList<Integer> myProjects = new ArrayList<>();
    	for(Project project:Database.PROJECTS.values()) {
    		if(project.getSupervisor().getUserID().equals(supervisorID) && project.getProjectStatus() == status) {
    			myProjects.add(project.getProjectID());
    		}
    	}
    	return myProjects;
    }
    
    /**
     * Notifies a Student if they have received a project.
     * @param studentID The user ID of the student.
     * @return {@code true} if student has received a project. 
     * Otherwise, {@code false} if the student has not received a project.
     */
    public static boolean anyApprovedProject(String studentID) {
    	for(StudentToFYPCoor request:Database.StudentREQUESTS.values()) {
    		if(request.getRequestStatus() == RequestStatus.APPROVED && request.getCreator()==Database.STUDENTS.get(studentID) && request.getProject().getProjectStatus() != ProjectStatus.AVAILABLE) {
        		return true;
        	}
    	}
    	return false;
    }
    
    /**
     * Notifies a Faculty if their project has been allocated.
     * @param supervisorID The user ID of the supervisor.
     * @return {@code true} if faculty's project has been allocated. 
     * Otherwise, {@code false} if the faculty's project was not allocated.
     */
    public static boolean anyAllocatedProject(String supervisorID) {
    	for(Project project:Database.PROJECTS.values()) {
    		if(project.getSupervisor().equals(Database.LOGINFACULTIES.get(supervisorID)) && project.getProjectStatus() == ProjectStatus.ALLOCATED && !project.getViewedbyFaculty()) {
    			return true;
    		}
    	}
    	return false;
    }
    
    /**
     * Checks the current status of a project.
     * @param projectID The project ID of the project.
     * @return {@link ProjectStatus} based on the status of the project.
     */
    public static ProjectStatus checkProjectStatus(int projectID) {
    	return Database.PROJECTS.get(projectID).getProjectStatus();
    }
    
    /**
     * Changes the status of a Faculty to NOTFREE and changes the status 
     * of their remaining projects to UNAVAILABLE if they are supervising 2 projects.
     * @param supervisorID The user ID of the supervisor.
     */
    public static void setFacultyUnavail(String supervisorID) 
    {
    	Faculty faculty = Database.LOGINFACULTIES.get(supervisorID);
    	faculty.setStatus(FacultyStatus.NOTFREE);
    	ArrayList<Project> myProjects = ProjectController.getSupervisorProjects(faculty.getUserID());
    	for(Project project2:myProjects) {
    		if(project2.getProjectStatus()!=ProjectStatus.ALLOCATED) {
    			project2.setProjectStatus(ProjectStatus.UNAVAILABLE);
    		}
    	}		
    }
    
    /**
     * Changes the status of a faculty to FREE and changes the status 
     * of their remaining projects to AVAILABLE if they are supervising less than 2 projects.
     * @param supervisorID The user ID of the supervisor.
     */
    public static void setFacultyavail(String supervisorID) 
    {
    	Faculty faculty = Database.LOGINFACULTIES.get(supervisorID);
    	faculty.setStatus(FacultyStatus.FREE);
    	ArrayList<Project> myProjects = ProjectController.getSupervisorProjects(faculty.getUserID());
    	for(Project project2:myProjects) {
    		if(project2.getProjectStatus()==ProjectStatus.UNAVAILABLE) {
    			project2.setProjectStatus(ProjectStatus.AVAILABLE);
    		}
    	}
    }
    
    /**
     * Prints all of the projects created and supervised by a specific Faculty.
     * @param supervisorID The user ID of the supervisor.
     */
    public static void printProjectForFaculty(String supervisorID)
    {
    	ArrayList<Project> projectList = ProjectController.getSupervisorProjects(supervisorID);
    	
    	if(projectList.isEmpty()) {
            System.out.printf(supervisorID + " have not created any projects or " + supervisorID + " transferred all projects to another supervisor.\n");
            return;
        } 
    	
    	System.out.println("╔══════════════════════════╦═══════════════════════════════════════════════════════════════════════════════════════╗");
    	for(Project project : projectList) {
    		project.setViewedbyFaculty(true);
    		if(project.getStudent()== null) {
    			System.out.printf("║ ProjectID                ║ %-85s ║%n", project.getProjectID());
    			System.out.printf("║ Project Title            ║ %-85s ║%n", project.getProjectTitle());
    			System.out.printf("║ Supervisor Name          ║ %-85s ║%n", project.getSupervisor().getName());
    			System.out.printf("║ Supervisor Email         ║ %-85s ║%n", project.getSupervisor().getEmail());
    			System.out.printf("║ Student Name             ║ %-85s ║%n", "-");
    			System.out.printf("║ Student Email            ║ %-85s ║%n", "-");
    			System.out.printf("║ Status                   ║ %-85s ║%n", project.getProjectStatus());
    		}
    		else
    		{
    			System.out.printf("║ ProjectID                ║ %-85s ║%n", project.getProjectID());
    			System.out.printf("║ Project Title            ║ %-85s ║%n", project.getProjectTitle());
    			System.out.printf("║ Supervisor Name          ║ %-85s ║%n", project.getSupervisor().getName());
    			System.out.printf("║ Supervisor Email         ║ %-85s ║%n", project.getSupervisor().getEmail());
    			System.out.printf("║ Student Name             ║ %-85s ║%n", project.getStudent().getName());
    			System.out.printf("║ Student Email            ║ %-85s ║%n", project.getStudent().getEmail());
    			System.out.printf("║ Status                   ║ %-85s ║%n", project.getProjectStatus());
    		}
			if (projectList.indexOf(project)!=projectList.size()-1)
				System.out.println("╠══════════════════════════╬═══════════════════════════════════════════════════════════════════════════════════════╣");
		}
		System.out.println("╚══════════════════════════╩═══════════════════════════════════════════════════════════════════════════════════════╝");
   }
    
    /**
     * Prints all of the projects created and supervised by a specific Faculty based on the project status.
     * @param supervisorID The user ID of the supervisor.
     * @param status The project status to be filtered.
     */
    public static void printProjectForFaculty(String supervisorID, ProjectStatus status)
    {
    	ArrayList<Project> projectList = ProjectController.getSupervisorProjects(supervisorID, status);
    	
    	if(projectList.isEmpty()) {
            System.out.println("You have not created any projects.");
            return;
        } 
    	
    	System.out.println("╔══════════════════════════╦═══════════════════════════════════════════════════════════════════════════════════════╗");
    	for(Project project : projectList) {
    		project.setViewedbyFaculty(true);
    		if(project.getStudent()== null) {
    			System.out.printf("║ ProjectID                ║ %-85s ║%n", project.getProjectID());
    			System.out.printf("║ Project Title            ║ %-85s ║%n", project.getProjectTitle());
    			System.out.printf("║ Supervisor Name          ║ %-85s ║%n", project.getSupervisor().getName());
    			System.out.printf("║ Supervisor Email         ║ %-85s ║%n", project.getSupervisor().getEmail());
    			System.out.printf("║ Student Name             ║ %-85s ║%n", "-");
    			System.out.printf("║ Student Email            ║ %-85s ║%n", "-");
    			System.out.printf("║ Status                   ║ %-85s ║%n", project.getProjectStatus());
    		}
    		else
    		{
    			System.out.printf("║ ProjectID                ║ %-85s ║%n", project.getProjectID());
    			System.out.printf("║ Project Title            ║ %-85s ║%n", project.getProjectTitle());
    			System.out.printf("║ Supervisor Name          ║ %-85s ║%n", project.getSupervisor().getName());
    			System.out.printf("║ Supervisor Email         ║ %-85s ║%n", project.getSupervisor().getEmail());
    			System.out.printf("║ Student Name             ║ %-85s ║%n", project.getStudent().getName());
    			System.out.printf("║ Student Email            ║ %-85s ║%n", project.getStudent().getEmail());
    			System.out.printf("║ Status                   ║ %-85s ║%n", project.getProjectStatus());
    		}
			if (projectList.indexOf(project)!=projectList.size()-1)
				System.out.println("╠══════════════════════════╬═══════════════════════════════════════════════════════════════════════════════════════╣");
		}
		System.out.println("╚══════════════════════════╩═══════════════════════════════════════════════════════════════════════════════════════╝");
   }
    
    /**
     * Displays the project details for a Student.
     * @param projectList The list of projects to be displayed.
     */
    public static void printProjectList(ArrayList<Project> projectList) 
    {
    	System.out.println("╔═══════════╦═══════════════════════════╦════════════════════════════╦═══════════════════════════════════════════════════════════════════════════════════════╦═════════════╗");
    	System.out.printf("║ %-9s ║ %-25s ║ %-26s ║ %-85s ║ %-11s ║%n", "ProjectID", "Supervisor Name", "Supervisor Email", "Project Title", "Status");
    	System.out.println("╠═══════════╬═══════════════════════════╬════════════════════════════╬═══════════════════════════════════════════════════════════════════════════════════════╬═════════════╣");
    	for(Project project : projectList) {
    		System.out.printf("║ %-9s ║ %-25s ║ %-26s ║ %-85s ║ %-11s ║%n", project.getProjectID(), project.getSupervisor().getName(), project.getSupervisor().getEmail(), project.getProjectTitle(), project.getProjectStatus());
    	}
    	System.out.println("╚═══════════╩═══════════════════════════╩════════════════════════════╩═══════════════════════════════════════════════════════════════════════════════════════╩═════════════╝");
    }
    
    /**
     * Displays the registered project of a Student.
     * @param studentID The user ID of the student.
     */
    public static void printStudentRegisteredProject(String studentID) {
    	Student student = Database.STUDENTS.get(studentID);
    	int projectID = student.getProjectID();
    	Project project = Database.PROJECTS.get(projectID);
    	System.out.println("╔══════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║ Your Registered Project Details                                                                          ║");
		System.out.println("╠══════════════════╦═══════════════════════════════════════════════════════════════════════════════════════╣");
        System.out.printf("║ ProjectID        ║ %-85s ║%n", project.getProjectID());
        System.out.printf("║ Project Title    ║ %-85s ║%n", project.getProjectTitle());
        System.out.printf("║ Supervisor Name  ║ %-85s ║%n", project.getSupervisor().getName());
        System.out.printf("║ Supervisor Email ║ %-85s ║%n", project.getSupervisor().getEmail());
        System.out.println("╚══════════════════╩═══════════════════════════════════════════════════════════════════════════════════════╝");
    }
    
    /**
     * Displays the project list based on project status.
     * @param status The project status to be filtered.
     */
    public static void displayProjectByStatus(ProjectStatus status) {
    	ArrayList<Project> projectList = getProjectByStatus(status);
 
        if(projectList.isEmpty()) {
            System.out.println("There are currently no projects that are " + status + ".");
        } 
        else ProjectController.printProjectList(projectList);
    }
}
