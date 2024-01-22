package enums;
/**
 * An enumerated class that represents the status of a Project.
 * @author Andrew Cheam, Bettina Tee, Chung Zhi Xuan, Lebron Lim, Ting Ruo Chee
 * @version 1.0
 * @since 2023-04-12
 */
public enum ProjectStatus {
	/**
	 * The project is not reserved or allocated, and the Faculty who created it is FREE. 
	 * Projects with this status will be displayed when a Student wants to view available projects.
	 */
	AVAILABLE,
	/**
	 * The project is not reserved or allocated, but the Faculty who created it is NOTFREE. 
	 * Projects with this status will not be displayed when a Student wants to view available projects.
	 */
	UNAVAILABLE,
	/**
	 * The project is being requested by a Student to FYP Coordinator for allocation. 
	 * Projects with this status will not be displayed when a Student wants to view available projects.
	 */
	RESERVED,
	/**
	 * The project has been allocated to a Student. 
	 * Projects with this status will not be displayed when a Student wants to view available projects.
	 */
	ALLOCATED
}
