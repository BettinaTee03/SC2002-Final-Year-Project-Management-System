package enums;
/**
 * An enumerated class that represents the status of a Faculty in regards to 
 * availability of supervising projects.
 * @author Andrew Cheam, Bettina Tee, Chung Zhi Xuan, Lebron Lim, Ting Ruo Chee
 * @version 1.0
 * @since 2023-04-12
 */
public enum FacultyStatus {
	/** The Faculty is already supervising 2 projects and is therefore not free. */
	NOTFREE,
	/** The Faculty is supervising less than 2 projects and is therefore free. */
	FREE
}
