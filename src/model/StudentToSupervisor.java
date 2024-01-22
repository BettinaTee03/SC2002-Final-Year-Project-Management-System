package model;

import java.util.HashMap;

import Assignment.Database;
import enums.RequestType;

/**
 * Represents a Request from a Student to Faculty. 
 * A Student can only request to change the title of their allocated project.
 * @author Andrew Cheam, Bettina Tee, Chung Zhi Xuan, Lebron Lim, Ting Ruo Chee
 * @version 1.0
 * @since 2023-04-15
 */
public class StudentToSupervisor extends Request {
	/** The student who made this Request. */
	private Student student;
	/** The project being requested under this Request. */
	private Project project;
	/** The new title of the project that is being requested. */
	private String newTitle;
	/** The original title of the project. */
	private String origTitle;
	/** The user ID of the student who made this Request. */
	private String studentID;
	
	/**
	 * Creates a new StudentToSupervisor Request.
	 * @param projectID The project ID of the project under this request.
	 * @param newTitle The new proposed title of the project.
	 * @param studentID The user ID of the student making this request.
	 * @param Status The status of this request.
	 * @param origTitle The original title of the project.
	 * @param isViewed The status of whether this request has been viewed by the supervisor.
	 */
	public StudentToSupervisor(int projectID, String newTitle, String studentID, String Status, String origTitle, Boolean isViewed) {
		super(projectID, Status, isViewed);
		this.newTitle = newTitle;
		student = Database.STUDENTS.get(studentID);
		this.project = Database.PROJECTS.get(projectID);
		this.origTitle = origTitle;
		this.studentID = studentID;
		setRequestType(RequestType.TITLECHANGING);
	}
	
	/**
	 * Changes the title of the student's allocated project.
	 * @param project The project being requested.
	 * @param newTitle The new title of the project.
	 */
	public void changeTitle(Project project, String newTitle) 
	{
		project.setProjectTitle(newTitle);
	}
	
	/**
	 * Gets the creator of this Request.
	 * @return The student who created this request.
	 */
	public Student getCreator()
	{
		return student;
	}
	
	/**
	 * Gets the project under this Request.
	 * @return The requested project.
	 */
	public Project getProject() {
		return project;
	}
	
	/**
	 * Gets the original title of this project.
	 * @return The original title of the project.
	 */
	public String getOrigTitle() {
		return this.origTitle;
	}
	
	/**
	 * Changes the original title of this project.
	 * @param origTitle The original title of the project.
	 */
	public void setOrigTitle(String origTitle) {
		this.origTitle = origTitle;
	}
	
	/**
	 * Gets the new title of this project.
	 * @return The new title of the project.
	 */
	public String getNewTitle() {
		return this.newTitle;
	}
	
	/**
	 * Changes the new title of this project.
	 * @param newTitle The new title of the project.
	 */
	public void setNewTitle(String newTitle) {
		this.newTitle = newTitle;
	}

	/**
	 * Creates a map containing this Request's request ID, request type, request status, 
	 * project ID, student ID, viewed status, original title, and new title.
	 * @return The map containing the request's details.
	 */
	public HashMap<String, Object> toHashMap() {
		HashMap<String, Object> StudentToSupervisorHashMap = new HashMap<String, Object>();
		StudentToSupervisorHashMap.put("RequestID", getRequestID());
		StudentToSupervisorHashMap.put("RequestType", getRequestType().name());
		StudentToSupervisorHashMap.put("RequestStatus", getRequestStatus().name());
		StudentToSupervisorHashMap.put("ProjectID", getRequestedProjectID());
		StudentToSupervisorHashMap.put("StudentID", studentID);
		StudentToSupervisorHashMap.put("isViewed", getisViewed());
		StudentToSupervisorHashMap.put("originalTitle", getOrigTitle());
		StudentToSupervisorHashMap.put("newTitle", getNewTitle());
		
		
		
		return StudentToSupervisorHashMap;
	}
	
}
