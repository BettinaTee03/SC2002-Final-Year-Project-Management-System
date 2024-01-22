package controller;

import java.util.Scanner;

import Assignment.Database;
import helper.OptionChecker;
import model.User;
import view.LoginView;
import view.UserView;

/**
 * A controller class that acts as a "middle man" between the
 * view classes {@link UserView}, 
 * {@link LoginView} and the model class {@link User}. <p>
 * 
 * It can filter and authenticate {@link User} details.
 * @author Bettina Tee, Lebron Lim
 * @version 1.0
 * @since 2023-04-11
 *
 * @param <T> Generic User type.
 */
public abstract class UserController<T> {
	/** The User whose details are being accessed or modified. */
	private User user;
	
	/**
	 * Authenticates a User's password with their user ID.
	 * @param userID The user ID of the User.
	 * @param password The password of the User.
	 * @return Generic return type.
	 */
	public abstract T authenticate(String userID, String password);
	
	/**
	 * Changes the user's password.
	 * @param userID The user ID of the user.
	 * @return {@code 0} if password was changed successfully. 
	 * Otherwise, {@code 1} if password was not changed successfully.
	 */
	public int changePassword(String userID)
	{
		System.out.println("╔═════════════════════════════════════════════╗");
        System.out.println("║               Change password               ║");
		System.out.println("╚═════════════════════════════════════════════╝");
		
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter new password: ");
        String newPassword = scanner.nextLine();
        user = Database.USERS.get(userID);
        
        while (newPassword.equals(user.getPassword()))
        {
        	System.out.print("\nNew password is the same as current password!\n");
        	System.out.printf("Enter 'y' to return to %s Menu\n", user.getUserType());
        	System.out.println("Enter 'n' to re-enter new password");
        	System.out.printf("Choice: ");
			
			String choiceExit1 = OptionChecker.ynOptionReturn();
			if (choiceExit1.equals("y"))
				return 1;
			else if (choiceExit1.equals("n"))
			{
				System.out.printf("Please re-enter your new password: ");
				newPassword = scanner.nextLine();
			}
        }

        System.out.print("Confirm new password: ");
        String confirmPassword = scanner.nextLine();

        while (!newPassword.equals(confirmPassword)) 
        {
        	System.out.printf("\nConfirm password does not match!\n");
        	System.out.printf("Enter 'y' to return to %s Menu\n", user.getUserType());
        	System.out.println("Enter 'n' to re-confirm password");
        	System.out.printf("Choice: ");
			
			String choiceExit2 = OptionChecker.ynOptionReturn();
			if (choiceExit2.equals("y"))
				return 1;
			
			else if (choiceExit2.equals("n"))
			{
				System.out.printf("Please re-confirm password: ");
				confirmPassword = scanner.nextLine();
			}
        }
        user.setPassword(newPassword);
        System.out.println("\nPassword changed successfully.");
        System.out.println("You will be logged out to the main menu.");
        System.out.println("Re-login to proceed.\n");
        return 0;
	}
}
