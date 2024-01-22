package Assignment;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;
import enums.ExcelType;
import enums.RequestType;
import helper.Excel;
import model.FYPCoordinator;
import model.Faculty;
import model.FacultyToFYPCoor;
import model.Project;
import model.Request;
import model.Student;
import model.StudentToFYPCoor;
import model.StudentToSupervisor;
import model.User;

/**
 * Database containing {@link Student} list, {@link Faculty} list, 
 * {@link Project} list and {@link FYPCoordinator} list. 
 * It uses the helper class {@link Excel} to read and write data files.
 * @author Andrew Cheam, Bettina Tee, Chung Zhi Xuan, Lebron Lim, Ting Ruo Chee
 * @version 1.0
 * @since 2023-04-10
 */

public class Database {
	/** Map of student ID to Student. */
	public static final HashMap<String, Student> STUDENTS = new HashMap<String, Student>();
	/** Map of faculty name to Faculty. */
	public static final HashMap<String, Faculty> FACULTIES = new HashMap<String, Faculty>();
	/** Map of faculty ID to Faculty. */
	public static final HashMap<String, Faculty> LOGINFACULTIES = new HashMap<String, Faculty>();
	/** Map of faculty ID to FYP Coordinator. */
	public static final HashMap<String, FYPCoordinator> FYPCOORD = new HashMap<String, FYPCoordinator>();
	/** Map of user ID to User. */
	public static final HashMap<String, User> USERS = new HashMap<String, User>(); 
	/** Map of project ID to Project. */
	public static final HashMap<Integer, Project> PROJECTS = new HashMap<Integer,Project>();
	/** Map of request ID to Request for all requests. All requests are visible to FYP Coordinator. */
	public static final HashMap<Integer, Request> REQUESTS = new HashMap<Integer,Request>();
	/** Map of request ID to Request for Student to Supervisor requests. */
	public static final HashMap<Integer, StudentToSupervisor> StudentToSupREQUESTS = new HashMap<Integer,StudentToSupervisor>();
	/** Map of request ID to Request for Student to FYP Coordinator requests. */
	public static final HashMap<Integer, StudentToFYPCoor> StudentREQUESTS = new HashMap<Integer,StudentToFYPCoor>();
	/** Map of request ID to Request for Faculty to FYP Coordinator requests. */
	public static final HashMap<Integer, FacultyToFYPCoor> FacultyREQUESTS = new HashMap<Integer,FacultyToFYPCoor>();
	
	/** Creates a new Database. */
	public Database() {}
	
	/**
	 * Reads and stores Student details from original data file into STUDENTS and USERS maps.
	 */
	public static void readStudentData()
    {	
    	ArrayList<HashMap<String, Object>> studentList = Excel.readExcel(ExcelType.STUDENT);
		for (HashMap<String, Object> studentMap : studentList)
		{
			Student student = new Student((String)studentMap.get("Name"), (String)studentMap.get("UserID"), (String)studentMap.get("Password"),
			(Boolean)studentMap.get("newProjectViewed"), (Double)studentMap.get("ProjectID"), (String)studentMap.get("Status"));
			STUDENTS.put(student.getUserID(), student);
            USERS.put(student.getUserID(),student);
		}
    }
	
	/**
	 * Writes updated Student details into data file.
	 */
    public static void writeStudentData()
    {
    	ArrayList<HashMap<String, Object>> studentList = new ArrayList<HashMap<String, Object>>();
    	for (Student student : STUDENTS.values()) 
    	{
    		studentList.add(student.toHashMap());
    	}
    	Excel.writeFile(studentList, ExcelType.STUDENT);
    }
    
    /**
     * Reads and stores Faculty details from original data file into FACULTIES, LOGINFACULTIES and USERS maps.
     */
    public static void readFacultyData()
    {
		ArrayList<HashMap<String, Object>> facultyList = Excel.readExcel(ExcelType.FACULTY);
		for (HashMap<String, Object> facultyMap : facultyList)
		{
			Faculty faculty = new Faculty((String)facultyMap.get("Name"), (String)facultyMap.get("UserID"), (String)facultyMap.get("Password"),
			(Double)facultyMap.get("numSupervised"), (String)facultyMap.get("Status"));
			FACULTIES.put(faculty.getName(), faculty);
	        LOGINFACULTIES.put(faculty.getUserID(), faculty);
	        USERS.put(faculty.getUserID(),faculty);		
		}	 
	}
    
    /**
     * Writes updated Faculty details into data file.
     */
    public static void writeFacultyData()
    {
    	ArrayList<HashMap<String, Object>> facultyList = new ArrayList<HashMap<String, Object>>();
    	for (Faculty faculty : FACULTIES.values()) 
    	{
    		facultyList.add(faculty.toHashMap());
    	}
    	Excel.writeFile(facultyList, ExcelType.FACULTY);
    }
    
    /**
     * Reads and stores FYP Coordinator details from original data file 
     * into FACULTIES, FYPCOORD, LOGINFACULTIES and USERS maps.
     */
    public static void readFYPCoorData()
    {
		ArrayList<HashMap<String, Object>> coordList = Excel.readExcel(ExcelType.FYPCOOR);
		for (HashMap<String, Object> coordMap : coordList)
		{
			 FYPCoordinator coordinator = new FYPCoordinator((String)coordMap.get("Name"), (String)coordMap.get("UserID"), (String)coordMap.get("Password"),
			 (Double)coordMap.get("numSupervised"), (String)coordMap.get("Status"));
			 FACULTIES.put(coordinator.getName(), coordinator);
			 FYPCOORD.put(coordinator.getUserID(), coordinator);
			 LOGINFACULTIES.put(coordinator.getUserID(), coordinator);
			 USERS.put(coordinator.getUserID(),coordinator);
		}
    }
    
    /**
     * Writes updated FYP Coordinator details into data file.
     */
    public static void writeFYPCoorData()
    {
    	ArrayList<HashMap<String, Object>> fypCoorList = new ArrayList<HashMap<String, Object>>();
    	for (FYPCoordinator fypCoor : FYPCOORD.values()) 
    	{
    		fypCoorList.add(fypCoor.toHashMap());
    	}
    	Excel.writeFile(fypCoorList, ExcelType.FYPCOOR);
    }
    
    /**
     * Reads and stores Project details from original data file into PROJECTS map.
     */
    public static void readProjectData()
    {
		ArrayList<HashMap<String, Object>> projectList = Excel.readExcel(ExcelType.PROJECT);
		for (HashMap<String, Object> projectMap : projectList)
		{
			 Project project = new Project((String)projectMap.get("Title"), (String)projectMap.get("supervisorID"),
					 (String)projectMap.get("studentID"), (String)projectMap.get("Status"), (Boolean)projectMap.get("isViewedbyFaculty"));
			 PROJECTS.put(project.getProjectID(),project);
		}
    }
    
    /**
     * Writes updated Project details into data file.
     */
    public static void writeProjectData()
    {
    	ArrayList<HashMap<String, Object>> projectList = new ArrayList<HashMap<String, Object>>();
    	for (Project project : PROJECTS.values()) 
    	{
    		projectList.add(project.toHashMap());
    	}
    	Excel.writeFile(projectList, ExcelType.PROJECT);
    }
    
    /**
     * Reads and stores Request details from original data file into REQUESTS and StudentREQUESTS maps.
     */
    public static void readRequestData() 
    {
    	//READ AND STORE PROJECT DATA
		ArrayList<HashMap<String, Object>> RequestList = Excel.readExcel(ExcelType.REQUEST);
		for (HashMap<String, Object> requestMap : RequestList)
		{
			if (requestMap.get("RequestType").equals(RequestType.SELECTING.name()) || requestMap.get("RequestType").equals(RequestType.DEREGISTERING.name()))
			{
				StudentToFYPCoor request = new StudentToFYPCoor((int)(((Double)requestMap.get("ProjectID")).intValue()), 
						 (String)requestMap.get("RequestType"), (String)requestMap.get("StudentID"), (String)requestMap.get("RequestStatus"), (Boolean)requestMap.get("isViewed"));
				 REQUESTS.put((int)(((Double)requestMap.get("RequestID")).intValue()), request);
				 StudentREQUESTS.put(request.getRequestID(), request);
			}
			else if (requestMap.get("RequestType").equals(RequestType.TITLECHANGING.name())) 
			{
				StudentToSupervisor request = new StudentToSupervisor((int)(((Double)requestMap.get("ProjectID")).intValue()), 
						 (String)requestMap.get("newTitle"), (String)requestMap.get("StudentID"), (String)requestMap.get("RequestStatus"), (String)requestMap.get("originalTitle"), (Boolean)requestMap.get("isViewed"));
				 REQUESTS.put((int)(((Double)requestMap.get("RequestID")).intValue()), request);
				 StudentToSupREQUESTS.put(request.getRequestID(), request);
			}
			else if (requestMap.get("RequestType").equals(RequestType.TRANSFERRING.name())) 
			{
				FacultyToFYPCoor request = new FacultyToFYPCoor((int)(((Double)requestMap.get("ProjectID")).intValue()), 
						 (String)requestMap.get("SupervisorID"), (String)requestMap.get("TransferringSupervisorID"), (String)requestMap.get("RequestStatus"), (Boolean)requestMap.get("isViewed"));
				 REQUESTS.put((int)(((Double)requestMap.get("RequestID")).intValue()), request);
				 FacultyREQUESTS.put(request.getRequestID(), request);
			}
			
		}
    }
    
    /**
     * Writes updated Request details into data file.
     */
    public static void writeRequestData()
    {
    	ArrayList<HashMap<String, Object>> requestList = new ArrayList<HashMap<String, Object>>();
    	for (Request request : REQUESTS.values()) 
    	{
    		requestList.add(request.toHashMap());
    	}
    	Excel.writeFile(requestList, ExcelType.REQUEST);
    }
    
    /**
     * Initialises Database by calling {@code readStudentData}, {@code readFacultyData}, 
     * {@code readFYPCoorData}, {@code readProjectData}, and {@code readRequestData}.
     */
    public static void initialiseDataBase() {
    	readStudentData();
    	readFacultyData();
    	readFYPCoorData();
    	readProjectData();
    	readRequestData();
    }
    
    /**
     * Updates data files by calling {@code writeStudentData}, {@code writeFacultyData}, 
     * {@code writeFYPCoorData}, {@code writeProjectData}, and {@code writeRequestData}.
     */
    public static void exportDataBase() {
    	writeStudentData();
    	writeFacultyData();
    	writeFYPCoorData();
    	writeProjectData();
    	writeRequestData();
    }
    
    /**
     * Gets the map of student ID to Student.
     * @return The map of student ID to Student.
     */
	public HashMap<String,Student> getStudentHashmap() {
		return STUDENTS;
	}
	
	/**
     * Gets the map of faculty ID to Faculty.
     * @return The map of faculty ID to Faculty.
     */
	public HashMap<String,Faculty> getFacultyHashmap() {
		return FACULTIES;
	}
	
	/**
     * Gets the map of user ID to User.
     * @return The map of user ID to User.
     */
	public HashMap<String,User> getUserHashmap(){
		return USERS;
	}
	
	/**
     * Gets the map of project ID to Project.
     * @return The map of project ID to Project.
     */
	public HashMap<Integer,Project> getProjectHashmap(){
		return PROJECTS;
	}
	
}