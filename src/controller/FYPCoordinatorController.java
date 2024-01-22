package controller;

import Assignment.Database;
import model.FYPCoordinator;
import view.FYPCoordinatorView;
import view.LoginView;

/** 
 * A controller class that acts as a "middle man" between the
 * view classes {@link FYPCoordinatorView}, {@link LoginView} 
 * and the model class {@link FYPCoordinator}. <p>
 * 
 * It can authenticate {@link FYPCoordinator} details.
 * @author Lebron Lim
 * @version 1.0
 * @since 2023-04-09
 */

public class FYPCoordinatorController extends UserController<FYPCoordinator> {
	/**
     * Creates a new FYPCoordinatorController.
     */
	public FYPCoordinatorController() {
		
    }

	/**
     * Authenticates the FYPCoor's password with their user ID.
     * @param userID The FYPCoor's user ID.
     * @param password The password entered by the FYPCoor.
     * @return this FYPCoor if authentication is successful. 
     * Otherwise, return {@code null}.
     */
    public FYPCoordinator authenticate(String userID, String password) {
        if(Database.FYPCOORD.containsKey(userID)) {
        	FYPCoordinator FYPCoor = Database.FYPCOORD.get(userID);
            if(FYPCoor.getPassword().equals(password)) {
                return FYPCoor; //authentication success
            }
        }
        return null; //authentication fail
    }
}
