package model;

import java.util.HashMap;

import enums.EnumMatcher;
import enums.FacultyStatus;
import enums.UserType;

/**
 * Represents a Faculty member in the school.
 * @author Andrew Cheam, Bettina Tee, Chung Zhi Xuan, Lebron Lim, Ting Ruo Chee
 * @version 1.0
 * @since 2023-04-14
 */

public class Faculty extends User {
	/** The number of projects that are currently being supervised by this Faculty. */
	private int numSupervised;
	/**
	 * The status of this Faculty. They are FREE by default and NOTFREE 
	 * if they are supervising 2 projects.
	 */
	private FacultyStatus status;
	
	/**
	 * Creates a new Faculty.
	 * @param name The name of the faculty.
	 * @param supervisorID The user ID of the faculty.
	 * @param password The password for the faculty to log into the FYP Portal.
	 * @param numSupervised The number of projects supervised by the faculty.
	 * @param Status The status of the faculty.
	 */
	public Faculty(String name, String supervisorID, String password, Double numSupervised, String Status) 
	{
		super(supervisorID,name, password);
		this.status = EnumMatcher.matchEnum(FacultyStatus.class, Status);
		this.numSupervised = (int)numSupervised.intValue();
	}
	
	/**
	 * Gets the email of this Faculty.
	 * @return The email of this faculty.
	 */
	public String getEmail() {
		return getUserID()+ "@ntu.edu.sg";
	}
	
	/**
	 * Gets the User type of this Faculty.
	 * @return FACULTY user type.
	 */
	public UserType getUserType() {
		return UserType.FACULTY;
	}
	
	/**
	 * Gets the status of this Faculty.
	 * @return The status of this faculty.
	 */
	public FacultyStatus getStatus() {
		return status;
	}
	
	/**
	 * Changes the status of this Faculty.
	 * @param status The new status of this faculty.
	 */
	public void setStatus(FacultyStatus status) {
		this.status = status;
	}
	
	/**
	 * Gets the number of projects being supervised by this Faculty.
	 * @return The number of supervised projects under this faculty.
	 */
	public int getNumSupervised() {
		return numSupervised;
	}
	
	/**
	 * Increments the number of projects being supervised under this Faculty by 1. 
	 * This happens when the Faculty supervises a new project.
	 */
	public void incNumSupervised() {
		this.numSupervised = this.numSupervised + 1;
	}
	
	/**
	 * Decreases the number of projects being supervised under this Faculty by 1. 
	 * This happens when the Faculty transfers their project to another supervisor
	 * or the Student has deregistered from their project.
	 */
	public void decNumSupervised() {
		this.numSupervised = this.numSupervised - 1;
	}
	
	/**
	 * Creates a map containing this Faculty's email, name, user ID, password, 
	 * status and number of supervised projects.
	 * @return The map containing the faculty's details.
	 */
	public HashMap<String, Object> toHashMap() {
		HashMap<String, Object> facultyHashMap = new HashMap<String, Object>();
		facultyHashMap.put("Email", this.getEmail());
		facultyHashMap.put("Name", this.getName());
		facultyHashMap.put("UserID", this.getUserID());
		facultyHashMap.put("Password", this.getPassword());
		facultyHashMap.put("Status", getStatus().name());
		facultyHashMap.put("numSupervised", this.numSupervised);
		
		return facultyHashMap;
	}
	
}
