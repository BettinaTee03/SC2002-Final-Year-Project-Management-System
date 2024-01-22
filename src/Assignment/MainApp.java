package Assignment;

import enums.UserType;
import view.LoginView;

/**
 * Main application for the Final Year Project Portal.
 * @author Lebron Lim
 * @version 1.0
 * @since 2023-04-10
 */
public class MainApp {
	/**
	 * The type of User logging into MainApp.
	 */
    private UserType type;
    /**
     * Creates a new MainApp.
     */
    public MainApp() {
    }
    
    /**
     * Initialises the {@link Database}, prints a welcome message and calls the {@link LoginView} menu.
     */
    public void run() {       
        Database.initialiseDataBase();
    	// Display welcome message and ask for user type
    	System.out.println("╔═════════════════════════════════════════════╗");
    	System.out.println("║                                             ║");
        System.out.println("║    Welcome to Final Year Project Portal!    ║");
    	System.out.println("║                                             ║");
    	System.out.println("╚═════════════════════════════════════════════╝\n");
        LoginView login = new LoginView(type);
        login.displayMenu();        
    }
    
    /**
     * Starts the main application MainApp.
     * @param args Arguments passed into the application.
     */
    public static void main(String[] args) {
        MainApp app = new MainApp();
        app.run();
    }

}
