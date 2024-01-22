package controller;

import Assignment.Database;
import model.Faculty;
import view.FacultyView;
import view.FYPCoordinatorView;
import view.LoginView;

/** 
 * A controller class that acts as a "middle man" between the
 * view classes {@link FacultyView}, {@link FYPCoordinatorView}, 
 * {@link LoginView} and the model class {@link Faculty}. <p>
 * 
 * It can filter and authenticate {@link Faculty} details.
 * @author Andrew Cheam, Bettina Tee, Chung Zhi Xuan, Lebron Lim, Ting Ruo Chee
 * @version 1.0
 * @since 2023-04-09
 */

public class FacultyController extends UserController<Faculty>{
	
	/**
     * Creates a new FacultyController.
     */
    public FacultyController() {
    	
    }
    
    /**
     * Authenticates the Faculty's password with their user ID.
     * @param userID The faculty's user ID.
     * @param password The password entered by the faculty.
     * @return This Faculty if authentication is successful. 
     * Otherwise, return {@code null}.
     */
    public Faculty authenticate(String userID, String password) {
        if(Database.LOGINFACULTIES.containsKey(userID)) {
        	Faculty faculty = Database.LOGINFACULTIES.get(userID);
            if(faculty.getPassword().equals(password)) {
                return faculty; //authentication success
            }
        }
        return null; //authentication fail
    }
    
    /**
     * Checks if a specific Faculty exists.
     * @param supervisorID The user ID of a supervisor.
     * @return {@code true} if the faculty exists. Otherwise, {@code false} if the faculty does not exist.
     */
    public static boolean checkFacultyExists(String supervisorID) {
    	if (Database.LOGINFACULTIES.containsKey(supervisorID))
    		return true;
    	else
    		return false;
    }    
    
    /**
     * Checks if a Supervisor is supervising any projects.
     * @param supervisorID The user ID of the supervisor.
     * @return {@code true} if the faculty is supervising one or more projects. 
     * Otherwise, {@code false} if the faculty is not supervising any projects.
     */
    public static boolean isSupervising(String supervisorID) {
    	Faculty faculty = Database.LOGINFACULTIES.get(supervisorID);
    	if (faculty.getNumSupervised() > 0)
    		return true;
    	else
    		return false;
    }
    
    /**
     * Prints the list of Supervisors according to their supervisor ID. 
     * This is for the Faculty to transfer their project.
     * @param supervisorID The user ID of the supervisor.
     */
    public static void printSupervisorList(String supervisorID) 
	{
		int i=0;
		System.out.println();
		System.out.println("╔═════════════════╦═══════════════════════════╦═════════════════╦═══════════════════════════╗");
		System.out.printf("║ %-15s ║ %-25s ║ %-15s ║ %-25s ║%n", "Supervisor ID", "Supervisor Name", "Supervisor ID", "Supervisor Name");
		System.out.println("╠═════════════════╬═══════════════════════════╬═════════════════╬═══════════════════════════╣");
    	
		for(Faculty faculty: Database.LOGINFACULTIES.values()) {
	    	if(!faculty.getUserID().equals(supervisorID)) {
	    		System.out.printf("║ %-15s ║ %-25s ", faculty.getUserID(), faculty.getName());	
	    		i++;
	    	}
			if (i%2==0)
				System.out.printf("║%n");
			if (i==Database.LOGINFACULTIES.size())
				System.out.printf("║ %-15s ║ %-25s ║%n");
	    }
		System.out.println("╚═════════════════╩═══════════════════════════╩═════════════════╩═══════════════════════════╝");
	}
    
}
