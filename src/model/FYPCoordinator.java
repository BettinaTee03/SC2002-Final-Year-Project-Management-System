package model;

import java.util.HashMap;

/**
 * Represents an FYP Coordinator in the school.
 * @author Andrew Cheam, Bettina Tee, Chung Zhi Xuan, Lebron Lim, Ting Ruo Chee
 * @version 1.0
 * @since 2023-04-14
 */

public class FYPCoordinator extends Faculty {
	/**
	 * Creates a new FYP Coordinator.
	 * @param name The name of the FYP coordinator.
	 * @param supervisorID The user ID of the FYP coordinator.
	 * @param password The password for the FYP coordinator to log into the FYP Portal.
	 * @param numSupervised The number of projects supervised by the FYP coordinator.
	 * @param Status The status of the FYP coordinator.
	 */
	public FYPCoordinator(String name, String supervisorID, String password, Double numSupervised, String Status) 
	{
		super(name, supervisorID, password, numSupervised, Status);
	}
	
	/**
	 * Creates a map containing this FYP Coordinator's email, name, user ID, 
	 * password, status and number of supervised projects.
	 * @return The map containing the FYP coordinator's details.
	 */
	public HashMap<String, Object> toHashMap() {
		HashMap<String, Object> fypCoordHashMap = new HashMap<String, Object>();
		fypCoordHashMap.put("Email", this.getEmail());
		fypCoordHashMap.put("Name", this.getName());
		fypCoordHashMap.put("UserID", this.getUserID());
		fypCoordHashMap.put("Password", this.getPassword());
		fypCoordHashMap.put("Status", getStatus().name());
		fypCoordHashMap.put("numSupervised", getNumSupervised());
		
		return fypCoordHashMap;
	}
	
}
