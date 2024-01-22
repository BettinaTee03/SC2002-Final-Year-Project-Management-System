package model;
import java.util.HashMap;

import enums.UserType;

/**
 * Represents a User in the school.
 * @author Andrew Cheam, Bettina Tee, Chung Zhi Xuan, Lebron Lim, Ting Ruo Chee
 * @version 1.0
 * @since 2023-04-15
 */

public abstract class User {
	/** The user ID of this User. */
	private String userID;
	/** The password for this User to log into the FYP Portal. */
	private String password;
	/** The name of this User. */
	private String name;
	
	/**
	 * Creates a new User.
	 * @param userID The user ID of this user.
	 * @param name The name of this user.
	 * @param password The password for this user to log into the FYP Portal.
	 */
	public User(String userID, String name, String password) {
		this.userID = userID;
		this.password = password;
		this.name = name;
	}
	
	/**
	 * Gets the password of this User.
	 * @return This User's password.
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * Changes the password of this User.
	 * @param password The new password of this User.
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * Gets the user ID of this User.
	 * @return This User's user ID.
	 */
	public String getUserID() {
		return userID;
	}
	
	/**
	 * Changes the user ID of this User.
	 * @param userID The new user ID of this user.
	 */
	public void setUserID(String userID) {
		this.userID = userID;
	}
	
	/**
	 * Gets the email of this User.
	 * @return The email of this User.
	 */
	public abstract String getEmail();
	
	/**
	 * Gets the name of this User.
	 * @return This User's name.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Changes the name of this User.
	 * @param name This new name of this user.
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Gets the user type of this User.
	 * @return The user type of this user.
	 */
	public abstract UserType getUserType();
	
	/**
	 * Creates a map containing this User's details.
	 * @return The map containing this user's details.
	 */
	public abstract HashMap<String, Object> toHashMap();
	
}
