package model;

import java.util.HashMap;

import enums.EnumMatcher;
import enums.RequestStatus;
import enums.RequestType;
import enums.StudentStatus;
/**
 * Represents a request in the FYP Portal.
 * @author Andrew Cheam, Bettina Tee, Chung Zhi Xuan, Lebron Lim, Ting Ruo Chee
 * @version 1.0
 * @since 2023-04-15
 */
public abstract class Request {
	/** The request ID of this Request. */
	private int requestID;
	/** The last request ID in the list of Requests. */
	private static int lastrequestID=1;
	/** The status of this Request. It is either PENDING, APPROVED, or REJECTED. */
	private RequestStatus status;
	/** The type of Request being made. */
	private RequestType type;
	/** The project ID of the project under this Request. */
	private int projectID;
	/**
	 * The status of whether this Request has been viewed by the intended recipient. 
	 * It is set to {@code false} by default.
	 */
	private boolean isViewed;
	
	/**
	 * Creates a new Request.
	 * @param projectID The project ID of the project under this request.
	 * @param Status The status of this request. It is set to PENDING by default.
	 * @param isViewed The status of whether this request has been viewed by the intended recipient.
	 */
	public Request(int projectID, String Status, Boolean isViewed){
		this.requestID = lastrequestID++;
        status = RequestStatus.PENDING;
        this.projectID = projectID;
        this.isViewed = isViewed;
        this.status = EnumMatcher.matchEnum(RequestStatus.class, Status);
	}
	
	/**
	 * Gets the status of whether this Request has been viewed by the intended recipient.
	 * @return The viewed status of this request.
	 */
	public boolean getisViewed(){
		return this.isViewed;
	}
	
	/**
	 * Changes the status of whether this Request has been viewed by the intended recipient.
	 * @param viewed The new viewed status of this request.
	 */
	public void setViewed(boolean viewed) {
		this.isViewed = viewed;
	}

	/**
	 * Gets the project ID of the project under this Request.
	 * @return The project ID of the requested project.
	 */
	public int getRequestedProjectID() {
		return this.projectID;
	}
	
	/**
	 * Gets the status of this Request. It is either PENDING, APPROVED or REJECTED.
	 * @return The status of this request.
	 */
	public RequestStatus getRequestStatus() {
		return this.status;
	}
	
	/**
	 * Changes the status of this Request.
	 * @param newStatus The new status of this request.
	 * @return The new status of this request.
	 */
	public RequestStatus setRequestStatus(RequestStatus newStatus) {
		this.status = newStatus;
		return this.status;
	}
	
	/**
	 * Gets the type of Request being made.
	 * @return The type of request.
	 */
	public RequestType getRequestType() {
		return this.type;
	}
	
	/**
	 * Changes the type of Request being made.
	 * @param requestType The new request type.
	 */
	public void setRequestType(RequestType requestType) {
		this.type = requestType;
	}
	
	/**
	 * Gets the request ID of this Request.
	 * @return The request ID of this request.
	 */
	public int getRequestID() {
		return requestID;
	}
	
	/**
	 * Gets the User who created this Request.
	 * @return The creator of this request.
	 */
	public abstract User getCreator();
	
	/**
	 * Creates a map containing this Request's details.
	 * @return The map containing this request's details.
	 */
	public abstract HashMap<String, Object> toHashMap();
	
}
