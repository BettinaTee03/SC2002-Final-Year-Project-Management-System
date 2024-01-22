package controller;

import Assignment.Database;
import enums.StudentStatus;
import model.Student;
import view.StudentView;
import view.FYPCoordinatorView;
import view.LoginView;
/**
 * A controller class that acts as a "middle man" between the
 * view classes {@link StudentView}, {@link FYPCoordinatorView}, 
 * {@link LoginView} and the model class {@link Student}. <p>
 * 
 * It can filter and authenticate {@link Student} details.
 * @author Andrew Cheam, Bettina Tee, Chung Zhi Xuan, Lebron Lim, Ting Ruo Chee
 * @version 1.0
 * @since 2023-04-11
 */
public class StudentController extends UserController<Student> {
	/**
     * Creates a new StudentController.
     */
    public StudentController() {
    	
    }
    
    /**
     * Authenticates the Student's password with their user ID.
     * @param userID The student's user ID.
     * @param password The password entered by the student.
     * @return This Student if authentication is successful. 
     * Otherwise, return {@code null}.
     */
    public Student authenticate(String userID, String password) {
    	Database database = new Database();
        if(database.getStudentHashmap().containsKey(userID)) {
            Student student = database.getStudentHashmap().get(userID);
            if(student.getPassword().equals(password)) {
                return student; //authentication success
            }
        }
        return null; //authentication fail
    }
    
    /**
     * Checks the status of a student with the given student ID.
     * @param studentID The user ID of the student.
     * @return The status of the student.
     */
    public static StudentStatus checkStudentStatus(String studentID) {
    	Student student = Database.STUDENTS.get(studentID);
    	return student.getStatus();
    }
    
    /**
     * Changes the status of a student with the given student ID to the given status.
     * @param studentID The user ID of the student.
     * @param status The updated status of the student.
     */
    public static void updateStudentStatus(String studentID, StudentStatus status) {
    	Student student = Database.STUDENTS.get(studentID);
    	student.setStatus(status);
    }
    
    /**
     * Checks if a student has viewed their allocated project.
     * @param studentID The user ID of the student.
     * @return {@code true} if the student has viewed their project. 
     * Otherwise, {@code false} if the student has not viewed their project.
     */
    public static boolean checkViewProjectStatus(String studentID) {
    	Student student = Database.STUDENTS.get(studentID);
    	return student.isNewProjectViewed();
    }
    
    /**
     * Changes the status of a student if they have viewed their allocated project.
     * @param studentID The user ID of the student.
     */
    public static void updateViewProjectStatus(String studentID) {
    	Student student = Database.STUDENTS.get(studentID);
    	student.setNewProjectViewed(true);
    } 
    
}
