package enums;
/**
 * An enumerated class that represents the status of a Student in regards to project allocation.
 * @author Andrew Cheam, Bettina Tee, Chung Zhi Xuan, Lebron Lim, Ting Ruo Chee
 * @version 1.0
 * @since 2023-04-12
 */
public enum StudentStatus {
	/** The Student has been allocated to a project. */
	REGISTERED,
	/**
	 * The Student has not been allocated to a project, has not requested for a project, 
	 * and has not deregistered from a project.
	 */
	NOTREGISTERED,
	/** The Student has deregistered from their project. */
	DEREGISTERED,
	/**
	 * The Student has sent a request to FYP Coordinator to register for a project 
	 * and is waiting to be approved or rejected.
	 */
	REGISTERING
}
