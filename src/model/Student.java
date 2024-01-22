package model;

import java.util.HashMap;

import Assignment.Database;
import enums.EnumMatcher;
import enums.StudentStatus;
import enums.UserType;

/**
 * Represents a Student member in the school.
 * @author Andrew Cheam, Bettina Tee, Chung Zhi Xuan, Lebron Lim, Ting Ruo Chee
 * @version 1.0
 * @since 2023-04-15
 */

public class Student extends User {
	/** The status of this Student. They are either REGISTERED, NOTREGISTERED, DEREGISTERED or REGISTERING. */
	private StudentStatus status;
	/**
	 * The project ID of the project allocated to this Student. 
	 * It is set to -1 if the Student is not allocated to a project.
	 */
	private int projectID; //is -1 if no projectID
	/** The project allocated to this Student. */
	private Project project;
	/**
	 * The status of whether a Student has viewed their allocated project. 
	 * It is set to {@code false} by default.
	 */
	private boolean newProjectViewed; //initialize default is false
	
	/**
	 * Creates a new Student.
	 * @param name The name of this student.
	 * @param studentID The user ID of this student.
	 * @param password The password for the student to log into the FYP Portal.
	 * @param newProjectViewed The status of whether this student has viewed their allocated project.
	 * @param projectID The project ID of the project allocated to this Student.
	 * @param Status The status of this student.
	 */
	public Student(String name, String studentID, String password, Boolean newProjectViewed, Double projectID, String Status) {
		super(studentID, name, password);
		this.newProjectViewed = newProjectViewed;
		this.projectID = projectID.intValue();
		this.project = Database.PROJECTS.get((int)projectID.intValue());
		this.status = EnumMatcher.matchEnum(StudentStatus.class, Status);
	}
	
	/**
	 * Gets the email of this Student.
	 * @return The email of this student.
	 */
	public String getEmail() {
		return getUserID()+"@e.ntu.edu.sg";
	}
	
	/**
	 * Gets the User type of this Student.
	 * @return STUDENT user type.
	 */
	public UserType getUserType() {
		return UserType.STUDENT;
	}
	
	/**
	 * Gets the status of this Student.
	 * @return The status of this student.
	 */
	public StudentStatus getStatus() {
		return status;
	}
	
	/**
	 * Changes the status of this Student.
	 * @param status The new status of this student.
	 */
	public void setStatus(StudentStatus status) {
		this.status = status;
	}
	
	/**
	 * Allocates a project to this Student.
	 * @param project The project to be allocated to this student.
	 */
	public void setProject(Project project) {
		this.project = project;
		if(project == null) {
			this.projectID = -1;
		}
		else {
			this.projectID = project.getProjectID();
		}
	}	
	
	/**
	 * Gets the project ID of the project allocated to this Student.
	 * @return The project ID of the project allocated to this student.
	 */
	public int getProjectID() {
		return this.projectID;
	}
	
	/**
	 * Changes the project ID of the project allocated to this Student.
	 * @param projectID The new project ID of the project.
	 */
	public void setProjectID(int projectID) {
		this.projectID = projectID;
	}
	
	/**
	 * Gets the status of whether this Student has viewed their allocated project.
	 * @return The viewed status of the project.
	 */
	public boolean isNewProjectViewed() {
		return newProjectViewed;
	}
	
	/**
	 * Changes the status of whether this Student has viewed their allocated project.
	 * @param viewed The new viewed status of the project.
	 */
	public void setNewProjectViewed(boolean viewed) {
		this.newProjectViewed = viewed;
	}
	
	/**
	 * Creates a map containing this Student's email, name, user ID, password, 
	 * status, project ID, and viewed project status.
	 * @return The map containing the student's details.
	 */
	public HashMap<String, Object> toHashMap() {
		HashMap<String, Object> studentHashMap = new HashMap<String, Object>();
		studentHashMap.put("Email", this.getEmail());
		studentHashMap.put("Name", this.getName());
		studentHashMap.put("UserID", this.getUserID());
		studentHashMap.put("Password", this.getPassword());
		studentHashMap.put("Status", getStatus().name());
		studentHashMap.put("ProjectID", this.projectID);
		studentHashMap.put("newProjectViewed", this.newProjectViewed);
		
		return studentHashMap;
	}
	
}
