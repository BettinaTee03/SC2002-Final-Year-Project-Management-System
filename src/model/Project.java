package model;

import java.util.HashMap;

import Assignment.Database;
import enums.EnumMatcher;
import enums.ProjectStatus;

/**
 * Represents a project in the FYP Portal.
 * @author Andrew Cheam, Bettina Tee, Chung Zhi Xuan, Lebron Lim, Ting Ruo Chee
 * @version 1.0
 * @since 2023-04-14
 */

public class Project {
	/** The last project ID in the list of Projects. */
	private static int lastprojectID=1;
	/** The project ID of this Project. */
	private int projectID;
	/** The title of this Project. */
	private String projectTitle;
	/** The status of this Project. It is either AVAILABLE, UNAVAILABLE, RESERVED, or ALLOCATED. */
	private ProjectStatus status;
	/** The user ID of the faculty that created or is supervising this Project. */
	private String supervisorID;
	/**
	 * The user ID of the student that is allocated to this Project. 
	 * If this project is not allocated, the user ID will be "NULL".
	 */
	private String studentID;
	/** The Faculty that created or is supervising this Project. */
	private Faculty supervisor;
	/**
	 * The Student that registered for this Project.
	 */
	private Student student;
	/**
	 * The status of whether a Project has been viewed by a faculty when they 
	 * log into the FYP Portal. This is used to display the (*NEW*) notification when 
	 * a project has been allocated to a student.
	 */
	private Boolean isViewedbyFaculty;
	
	/**
	 * Creates a new Project.
	 * @param projectTitle The project ID of the project.
	 * @param supervisorID The user ID of the faculty that created this project.
	 * @param studentID The user ID of the student that is allocated to this project.
	 * @param Status The status of this project.
	 * @param isViewedbyFaculty The status of whether this project has been viewed by the faculty.
	 */
	public Project(String projectTitle, String supervisorID, String studentID, String Status, Boolean isViewedbyFaculty) {
		this.projectTitle = projectTitle;
        this.projectID = lastprojectID++;
        this.supervisor = Database.LOGINFACULTIES.get(supervisorID);
        //check if excel stores no userID
        this.supervisorID = supervisorID;
        this.studentID = studentID;
        if (!studentID.equals("NULL"))
        	{
        		this.student = Database.STUDENTS.get(studentID);
        	}
        this.status = EnumMatcher.matchEnum(ProjectStatus.class, Status);
        this.isViewedbyFaculty = isViewedbyFaculty;
	}
	
	/**
	 * Gets the status of whether this Project has been viewed by the faculty.
	 * @return The status of this project.
	 */
	public boolean getViewedbyFaculty() {
		return this.isViewedbyFaculty;
	}
	
	/**
	 * Changes the status of whether this Project has been viewed by the faculty.
	 * @param viewed The new status of this project.
	 */
	public void setViewedbyFaculty(boolean viewed) {
		this.isViewedbyFaculty = viewed;
	}
	
	/**
	 * Gets the status of this Project.
	 * @return The status of this project.
	 */
	public ProjectStatus getProjectStatus() {
		return this.status;
	}
	
	/**
	 * Gets the faculty that created or is supervising this Project.
	 * @return The faculty that created or is supervising this project.
	 */
	public Faculty getSupervisor() {
		return supervisor;
	}	
	
	/**
	 * Changes the supervisor of this Project.
	 * @param newSupervisor The new faculty that is supervising this project.
	 */
	public void setSupervisor(Faculty newSupervisor) {
		this.supervisor = newSupervisor;
		this.supervisorID = newSupervisor.getUserID();
	}
	
	/**
	 * Gets the student that is allocated to this Project.
	 * @return The student that is allocated to this Project.
	 */
	public Student getStudent() {
		return this.student;
	}
	
	/**
	 * Changes the student that is allocated to this Project.
	 * @param student The new Student that is allocated to this Project.
	 */
	public void setStudent(Student student) {
		this.student = student;
		this.studentID = student.getUserID();
	}
	
	/**
	 * Deregisters a student from this Project by setting their ID to "NULL".
	 */
	public void deregisterStudent() {
		this.studentID = "NULL";
		this.student = null;
	}
	
	/**
	 * Gets the project ID of this Project.
	 * @return The project ID of this project.
	 */
	public int getProjectID() {
		return projectID;
	}	
	
	/**
	 * Gets the title of this Project.
	 * @return The title of this project.
	 */
	public String getProjectTitle() {
		return projectTitle;
	}
	
	/**
	 * Changes the title of this Project.
	 * @param newProjectTitle The new title of this project.
	 */
	public void setProjectTitle(String newProjectTitle) {
		this.projectTitle = newProjectTitle;
	}
	
	/**
	 * Changes the status of this Project.
	 * @param newStatus The new status of this project.
	 */
	public void setProjectStatus(ProjectStatus newStatus) {
		this.status = newStatus;
	}
	
	/**
	 * Creates a map containing this Project's project ID, title, supervisor, 
	 * supervisor ID, student ID, status, and viewed status.
	 * @return The map containing the project's details.
	 */
	public HashMap<String, Object> toHashMap() {
		HashMap<String, Object> projectHashMap = new HashMap<String, Object>();
		projectHashMap.put("ProjectID", projectID);
		projectHashMap.put("Title", projectTitle);
		projectHashMap.put("Supervisor", supervisor.getName());
		projectHashMap.put("supervisorID", supervisorID);
		projectHashMap.put("studentID", studentID);
		projectHashMap.put("Status", getProjectStatus().name());
		projectHashMap.put("isViewedbyFaculty", getViewedbyFaculty());
		
		return projectHashMap;
	}
}
