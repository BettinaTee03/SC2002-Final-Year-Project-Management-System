package model;

import java.util.HashMap;


import Assignment.Database;
import enums.EnumMatcher;
import enums.RequestType;

/**
 * Represents a Request from a Student to FYP Coordinator. 
 * A Student can request to select a project or deregister from their allocated project.
 * @author Andrew Cheam, Bettina Tee, Chung Zhi Xuan, Lebron Lim, Ting Ruo Chee
 * @version 1.0
 * @since 2023-04-15
 */
public class StudentToFYPCoor extends Request {
	/** The user ID of the student who made this Request. */
	private String studentID;
	/** The student who made this Request. */
	private Student student;
	
	/**
	 * Creates a new StudentToFYPCoor Request.
	 * @param projectID The project ID of the project under this request.
	 * @param requestType The type of request.
	 * @param studentID The user ID of the student making this request.
	 * @param Status The status of this request.
	 * @param isViewed The status of whether this request has been viewed by the FYP coordinator.
	 */
	public StudentToFYPCoor(int projectID, String requestType, String studentID, String Status, Boolean isViewed) {
		super(projectID, Status, isViewed);
		this.studentID = studentID;
		student = Database.STUDENTS.get(studentID);
		setRequestType(EnumMatcher.matchEnum(RequestType.class, requestType));
	}
	
	/**
	 * Deregisters a student from their project and sets their project to {@code null}.
	 * @param project The project to be deregistered.
	 */
	public void deregister(Project project) {
		student.setProject(null);
	}
	
	/**
	 * Gets the creator of this Request.
	 * @return The student who created this request.
	 */
	public Student getCreator() {
		return this.student;
	}
	
	/**
	 * Gets the project requested under this Request.
	 * @return The project requested under this request.
	 */
	public Project getProject() {
		return Database.PROJECTS.get(super.getRequestedProjectID());
	}
	
	/**
	 * Creates a map containing this Request's request ID, request type, request status, 
	 * project ID, student ID, and viewed status.
	 * @return The map containing the request's details.
	 */
	public HashMap<String, Object> toHashMap() {
		HashMap<String, Object> StudentToFYPCoorHashMap = new HashMap<String, Object>();
		StudentToFYPCoorHashMap.put("RequestID", getRequestID());
		StudentToFYPCoorHashMap.put("RequestType", getRequestType().name());
		StudentToFYPCoorHashMap.put("RequestStatus", getRequestStatus().name());
		StudentToFYPCoorHashMap.put("ProjectID", getRequestedProjectID());
		StudentToFYPCoorHashMap.put("StudentID", studentID);
		StudentToFYPCoorHashMap.put("isViewed", getisViewed());
		
		
		return StudentToFYPCoorHashMap;
	}
}
