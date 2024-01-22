package enums;
/**
 * An enumerated class that represents the status of a Request.
 * @author Andrew Cheam, Bettina Tee, Chung Zhi Xuan, Lebron Lim, Ting Ruo Chee
 * @version 1.0
 * @since 2023-04-12
 */
public enum RequestStatus {
	/** The request has been made but has not been approved or rejected by a Faculty or FYP Coordinator. */
	PENDING,
	/** The request has been approved by a Faculty or FYP Coordinator. */
	APPROVED,
	/** The request has been rejected by a Faculty or FYP Coordinator. */
	REJECTED
}
