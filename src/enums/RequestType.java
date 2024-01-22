package enums;
/**
 * An enumerated class that represents the type of Request made.
 * @author Andrew Cheam, Bettina Tee, Chung Zhi Xuan, Lebron Lim, Ting Ruo Chee
 * @version 1.0
 * @since 2023-04-12
 */
public enum RequestType {
	/** The request is sent from Student to FYP Coordinator for selecting a project. */
	SELECTING,
	/** The request is sent from Student to FYP Coordinator for deregistering from their project. */
	DEREGISTERING,
	/** The request is sent from Student to Faculty to change the title of their project. */
	TITLECHANGING,
	/**
	 * The request is sent from Faculty to FYP Coordinator to transfer their project to 
	 * a different supervisor.
	 */
	TRANSFERRING
}
