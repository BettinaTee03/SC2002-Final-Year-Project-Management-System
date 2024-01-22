package helper;

import java.util.*;
/**
 * A helper class that checks whether a user's input is valid. 
 * It can check both integer and string inputs.
 * @author Bettina Tee
 * @version 1.0
 * @since 2023-04-13
 */
public class OptionChecker {
	/** A Scanner object to take in the user's input. */
	public static final Scanner sc = new Scanner (System.in);
	
	/** Creates a new OptionChecker. */
	public OptionChecker() {}
	/**
	 * Scans a user's input and checks that they have entered an integer 
	 * and nothing else. 
	 * It will continue to prompt user input until they enter a valid choice.
	 * @return The valid integer entered by the user.
	 */
	
	public static int intOnlyOptionReturn() 
	{
		String choice;
		int intChoice;
		choice = sc.nextLine();
		
		while (true)
        {
			try {
				intChoice = Integer.parseInt(choice);
			} catch (NumberFormatException e) {
				System.out.println("\nInvalid choice!");
                System.out.printf("Re-enter choice: ");
                choice = sc.nextLine();
                continue;
			}
			return intChoice;
        }       
	}

	/**
	 * Checks that a user has entered a valid integer within the given range. 
	 * This is used when a user has to choose an option from the displayed menu. 
	 * It will continue to prompt user input until they enter a valid choice.
	 * @param min The minimum number that the user can enter.
	 * @param max The maximum number that the user can enter.
	 * @return The valid integer entered by the user.
	 */
	public static int intOptionReturn(int min, int max) 
	{
		int intChoice;
		
		while (true)
        {
			intChoice = intOnlyOptionReturn();
			
        	if (intChoice<min || intChoice>max) {
            	System.out.println("\nInvalid choice!");
                System.out.printf("Re-enter choice: ");
                continue;
            }
        	else
        		return intChoice;
        }       
	}
	
	/**
	 * Scans a user's input and checks that they have entered only 'y' or 'n'. 
	 * This is used when a user is asked a 'yes' or 'no' question. 
	 * It will continue to prompt user input until they enter a valid choice.
	 * @return The valid option entered by the user, which is either 'y' or 'n'.
	 */
	public static String ynOptionReturn()
	{
		String choice;
		choice = sc.nextLine().toLowerCase();
		while (!choice.equals("y") && !choice.equals("n"))
		{
			System.out.println("\nInvalid Choice!");
			System.out.printf("Re-enter choice: ");
			choice = sc.nextLine().toLowerCase();
		}
		return choice;
	}
}
