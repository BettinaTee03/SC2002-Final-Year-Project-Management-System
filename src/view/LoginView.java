package view;

import Assignment.Database;
import controller.FacultyController;
import controller.StudentController;
import enums.UserType;
import helper.OptionChecker;
import model.Faculty;
import model.Student;

import java.util.*;

/**
 * Displays the menu for Users to select the correct domain and login using their user ID and password.
 * @author Andrew Cheam, Bettina Tee, Chung Zhi Xuan, Lebron Lim, Ting Ruo Chee
 * @version 1.0
 * @since 2023-04-15
 */

public class LoginView{
	/** The controller if the User is a Student. */
	private StudentController studentController;
	/** The controller if the User is a Faculty. */
	private FacultyController facultyController;
	/** The name of the type of User logging into the FYP Portal. */
	private String userType;
	/** A Student logging into the portal. */
	private Student student;
	/** A Faculty logging into the portal. */
	private Faculty faculty;
	/** The type of User. */
	private UserType type;
	
	/**
	 * Creates a new LoginView.
	 * @param type The type of user logging into the portal.
	 */
	public LoginView(UserType type) {
		studentController = new StudentController();
		facultyController = new FacultyController();
	}
	
	/**
	 * Displays the main menu for the User to select a domain. 
	 * It then calls the correct display menu for the selected domain.
	 */
	public void displayMenu() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("╔═════════════════════════════════════════════╗");
        System.out.println("║                  Main Menu                  ║");
    	System.out.println("╠═════════════════════════════════════════════╣");
		System.out.println("║ Please select domain:                       ║");
        System.out.println("║ Enter 's' for student                       ║");
        System.out.println("║ Enter 'f' for faculty                       ║");
        System.out.println("║ Enter 'e' to exit portal                    ║");
		System.out.println("╚═════════════════════════════════════════════╝");
        System.out.printf("Selection: ");
        
        userType = scanner.nextLine();
        while (!(userType.equals("s") || userType.equals("f") || userType.equals("e")))
        {
        	System.out.println("\nInvalid choice.");
        	System.out.printf("Re-enter Domain: ");
        	userType = scanner.nextLine();
        }
        
        System.out.println();
        
        if (userType.equals("e"))
        {
        	System.out.println("╔═════════════════════════════════════════════╗");
        	System.out.println("║            Exiting the portal...            ║");
        	System.out.println("╚═════════════════════════════════════════════╝");
        	Database.exportDataBase();
        	return;
        }
        else if(userType.equals("s")) {
    		type = UserType.STUDENT;
    		System.out.println("╔═════════════════════════════════════════════╗");
            System.out.println("║            Entered Student Domain           ║");
    		System.out.println("╚═════════════════════════════════════════════╝");
        	System.out.print("Enter your user ID: ");
    		String userID = scanner.nextLine();
    		while  (!Database.STUDENTS.containsKey(userID)){
            	System.out.println("\n╔═════════════════════════════════════════════╗");
                System.out.println("║ User is not found in Student Domain!        ║");
            	System.out.println("╠═════════════════════════════════════════════╣");
            	System.out.println("║ Y. Re-enter User ID                         ║");
            	System.out.println("║ N. Return to Main Menu                      ║");
            	System.out.println("╚═════════════════════════════════════════════╝");
    			System.out.printf("Choice: ");
    			
    			String choice = OptionChecker.ynOptionReturn();
    			if (choice.equals("n"))
    			{
    				System.out.println();
    				displayMenu();
    				return;
    			}
    			else if (choice.equals("y"))
    			{
    				System.out.printf("\nPlease re-enter your user ID: ");
        			userID = scanner.nextLine();
    			}
    			
        	}
    		System.out.printf("Enter your password: ");
    		String password = scanner.nextLine().trim();
			student = studentController.authenticate(userID, password);
			while (student == null)
			{
				System.out.printf("\nPassword is wrong!\n");
				System.out.printf("Please re-enter your password: ");
				password = scanner.nextLine().trim();
				student = studentController.authenticate(userID, password);
			}
	        System.out.println("\nLogin Successful! Welcome, " + student.getName() + "!");
			StudentView studentView = new StudentView(student.getUserID());
			studentView.displayApp();
    		
        }
        
        else if(userType.equals("f")) {
    		type = UserType.FACULTY;
    		System.out.println("╔═════════════════════════════════════════════╗");
        	System.out.println("║           Entered Faculty Domain            ║");
    		System.out.println("╚═════════════════════════════════════════════╝");
        	System.out.print("Enter your user ID: ");
    		String userID = scanner.nextLine();
    		while  (!Database.LOGINFACULTIES.containsKey(userID)){
            	System.out.println("\n╔═════════════════════════════════════════════╗");
                System.out.println("║ User is not found in Faculty Domain!        ║");
            	System.out.println("╠═════════════════════════════════════════════╣");
            	System.out.println("║ Y. Re-enter User ID                         ║");
            	System.out.println("║ N. Return to Main Menu                      ║");
            	System.out.println("╚═════════════════════════════════════════════╝");
    			System.out.printf("Choice: ");
    			
    			String choice = OptionChecker.ynOptionReturn();
    			if (choice.equals("n"))
    			{
    				System.out.println();
    				displayMenu();
    				return;
    			}
    			else if (choice.equals("y"))
    			{
    				System.out.printf("\nPlease re-enter your user ID: ");
        			userID = scanner.nextLine();
    			}
    			
        	}
    		
    		System.out.printf("Enter your password: ");
    		String password = scanner.nextLine().trim();
    		faculty = facultyController.authenticate(userID, password);
			while (faculty == null)
			{
				System.out.printf("\nPassword is wrong!\n");
				System.out.printf("Please re-enter your password: ");
				password = scanner.nextLine().trim();
				faculty = facultyController.authenticate(userID, password);
			}

			System.out.println("\nLogin Successful! Welcome, " + faculty.getName() + "!");
			if(faculty.getUserID().equals("ASFLI")) {
				type = UserType.FYPCOOR;
				FYPCoordinatorView coorView = new FYPCoordinatorView(faculty.getUserID());
				coorView.displayApp();
			}
			else {
				FacultyView facultyView = new FacultyView(faculty.getUserID());
				facultyView.displayApp();
			}
        }
	}
}
