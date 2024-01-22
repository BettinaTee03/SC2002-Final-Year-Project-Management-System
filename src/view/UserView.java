package view;

/**
 * Displays the menu for all Users using the information provided by 
 * the controller classes.
 * @author Andrew Cheam, Bettina Tee, Chung Zhi Xuan, Lebron Lim, Ting Ruo Chee
 * @version 1.0
 * @since 2023-04-15
 */

public abstract class UserView{
	
	/**
	 * Creates a new UserView.
	 */
    public UserView() {
    	
    }
    
    /**
     * Displays the menu and notifications for this User.
     */
    public abstract void displayMenu();
    
    /**
     * Application to display the menu, read user input and call the selected task.
     */
    public abstract void displayApp();
}
