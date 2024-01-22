package model;

import java.util.HashMap;

import Assignment.Database;
import enums.RequestType;
/**
 * Represents a Request from a Faculty to FYP Coordinator. 
 * A Faculty can only request to transfer their project to another supervisor.
 * @author Andrew Cheam, Bettina Tee, Chung Zhi Xuan, Lebron Lim, Ting Ruo Chee
 * @version 1.0
 * @since 2023-04-14
 */
public class FacultyToFYPCoor extends Request {
	/** The faculty who made this Request. */
	private Faculty supervisor;
	/** The user ID of the faculty who made this Request. */
	private String supervisorID;
	/** The user ID of the new supervisor that the project is being transferred to. */
	private String transferringsupervisorID;
	
	/**
	 * Creates a new FacultyToFYPCoor Request.
	 * @param projectID The project ID of the project being transferred.
	 * @param supervisorID The user ID of the faculty making this request.
	 * @param transferringsupervisorID The user ID of the new supervisor 
	 * that the project is being transferred to.
	 * @param Status The status of this request.
	 * @param isViewed The status of whether this request has been viewed by the FYP coordinator.
	 */
	public FacultyToFYPCoor(int projectID, String supervisorID, String transferringsupervisorID, String Status,
			Boolean isViewed) {
		super(projectID, Status, isViewed);
		this.supervisorID = supervisorID;
		this.transferringsupervisorID = transferringsupervisorID;
		this.supervisor = Database.LOGINFACULTIES.get(supervisorID);
		setRequestType(RequestType.TRANSFERRING);
	}
	
	/**
	 * Gets the creator of this Request.
	 * @return The faculty who created this request.
	 */
	public Faculty getCreator() {
		return this.supervisor;
	}
	
	/**
	 * Gets the user ID of the new supervisor that a project is being transferred to.
	 * @return The user ID of the new supervisor.
	 */
	public String getTransferring() {
		return this.transferringsupervisorID;
	}
	
	/**
	 * Creates a map containing this Request's request ID, request type, request status, 
	 * project ID, supervisor ID, viewed status, and transferring supervisor ID.
	 * @return The map containing the request's details.
	 */
	public HashMap<String, Object> toHashMap() {
		HashMap<String, Object> FacultytoFYPCoorHashMap = new HashMap<String, Object>();
		FacultytoFYPCoorHashMap.put("RequestID", getRequestID());
		FacultytoFYPCoorHashMap.put("RequestType", getRequestType().name());
		FacultytoFYPCoorHashMap.put("RequestStatus", getRequestStatus().name());
		FacultytoFYPCoorHashMap.put("ProjectID", getRequestedProjectID());
		FacultytoFYPCoorHashMap.put("SupervisorID", supervisorID);
		FacultytoFYPCoorHashMap.put("isViewed", getisViewed());
		FacultytoFYPCoorHashMap.put("TransferringSupervisorID", transferringsupervisorID);

		return FacultytoFYPCoorHashMap;
	}
}
