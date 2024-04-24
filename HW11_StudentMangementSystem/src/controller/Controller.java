/**
 * Class Controller
 * 
 * @author Yipeng Zhang & Zhanqian Wu
 */

package controller;

import files.FileInfoReader;
import roles.Admin;
import roles.Professor;
import roles.Student;
import roles.User;

import java.util.Scanner;


/**
 * represent a Student Management System
 */

public class Controller {
	// Scanner for user input
    private static Scanner scanner = new Scanner(System.in);

//System Interface------------------------------------------------------------------

    // Entry interface of the system.
    public static void main(String[] str) {
        Controller system = new Controller();
        FileInfoReader file = new FileInfoReader();

        while (true) {
            int choice = system.displayMainMenu();

            switch (choice) {
            case 1:
                system.loginjug(new Student(), file);
                break;
            case 2:
                system.loginjug(new Professor(), file);
                break;
            case 3:
                system.loginjug(new Admin(), file);
                    break;
                case 4:
                	System.out.println("Exiting system...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    continue;
            }
        }
    }
    
    /**
     * Displays the main menu and gets the user's choice.
     * 
     * @return The user's choice as an integer.
     */
    public int displayMainMenu() {
        System.out.println("----------------------------");
        System.out.println("| Students Management System |");
        System.out.println("----------------------------");
        System.out.println("1. Welcome, Login as a student");
        System.out.println("2. Welcome, Login as a professor");
        System.out.println("3. Welcome, Login as an admin");
        System.out.println("4. Exit");
        System.out.print("Enter your choice: ");
        
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next(); // consume the non-integer input
            System.out.print("Enter your choice: ");
        }
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume the newline character after the number
        
        return choice;
    }
    
    /**
     * Handles the login process for a user and directs them to role-specific actions.
     * 
     * This method prompts the user for their username and password, attempts to authenticate them,
     * and on successful login, directs them to the appropriate actions based on their role
     * (Student, Professor, or Admin).
     *
     * @param user The user object (Student, Professor, Admin) to authenticate.
     * @param fileReader The FileInfoReader instance to access and verify user data.
     */
    
    private void loginjug(User user, FileInfoReader fileReader) {
        String[] credentials = promptUP();
        if ("q".equals(credentials[0])) return;  // Quick exit if 'q' is entered

        User loggedInUser = authenticateUser(user, credentials, fileReader);
        if (loggedInUser == null) {
            System.out.println("Login failed after multiple attempts.");
            return;
        }

        // User is successfully authenticated; proceed to user-specific actions
        navigateToRoleSpecificAction(loggedInUser, fileReader);
    }
    
    private User authenticateUser(User user, String[] credentials, FileInfoReader fileReader) {
        User loggedInUser = user.Login(credentials[0], credentials[1], fileReader);
        while (loggedInUser == null) {
            System.out.println("Invalid username or password. Please try again or type 'q' to quit.");
            credentials = promptUP();
            if ("q".equals(credentials[0])) return null; // Quick exit if 'q' is entered
            loggedInUser = user.Login(credentials[0], credentials[1], fileReader);
        }
        return loggedInUser;
    }
    
    private void navigateToRoleSpecificAction(User loggedInUser, FileInfoReader fileReader) {
        if (loggedInUser instanceof Student) {
            StudentActions((Student) loggedInUser, fileReader);
        } else if (loggedInUser instanceof Professor) {
            handleProfActions((Professor) loggedInUser, fileReader);
        } else if (loggedInUser instanceof Admin) {
            handleAdminActions((Admin) loggedInUser, fileReader);
        }
    }
    
    /**
     * Prompts for the user's username and password.
     * 
     * @return An array containing the username at index 0 and password at index 1.
     */
    private String[] promptUP() {
        System.out.print("Enter username (or type 'q' to quit): ");
        String username = scanner.nextLine().trim(); // Use nextLine to capture entire line input
        if ("q".equalsIgnoreCase(username)) {
            return new String[] {"q"}; // Early exit if user decides to quit
        }

        System.out.print("Enter password (or type 'q' to quit): ");
        String password = scanner.nextLine().trim(); // Use nextLine to ensure complete password input
        if ("q".equalsIgnoreCase(password)) {
            return new String[] {"q"}; // Allow early exit on password prompt too
        }

        return new String[] {username, password};
    }
    
    /**
     * Prints the student interface and asks for an option.
     *
     * @param s The student.
     * @return The chosen option as an integer.
     */
    private int StudentInterface(Student student) {
        System.out.println("\n--------------------------------------------------");
        System.out.printf("Welcome, %s%n", student.getName());
        System.out.println("--------------------------------------------------");

        // Array of options for the student menu
        String[] options = {
            "View all courses",
            "Add courses to your list",
            "View enrolled courses",
            "Drop courses in your list",
            "View grades",
            "Return to previous menu"
        };

        // Displaying options and asking for the user's choice
        return askForChoice(options);
    }

    /**
     * Prints the professor interface and asks for an option.
     *
     * @param p The professor.
     * @return The chosen option as an integer.
     */
    private int ProfessorInterface(Professor professor) {
        System.out.println("\n--------------------------------------------------");
        System.out.printf("Welcome, Professor %s%n", professor.getName());
        System.out.println("--------------------------------------------------");

        // Array of options for the professor menu
        String[] options = {
            "View given courses",
            "View student list of the given course",
            "Return to previous menu"
        };

        // Displaying options and asking for the user's choice
        return askForChoice(options);
    }

    /**
     * Prints the administrator interface and asks for an option.
     *
     * @return The chosen option as an integer.
     */
    private int AdminInterface() {
        System.out.println("\n-----------------------------------------------");
        System.out.println("Welcome, Admin");
        System.out.println("-----------------------------------------------");

        // Array of options for the admin menu
        String[] options = {
            "View all courses",
            "Add new courses",
            "Delete courses",
            "Add new professor",
            "Delete professor",
            "Add new student",
            "Delete student",
            "Return to previous menu"
        };

        // Displaying options and asking for the user's choice
        return askForChoice(options);
    }

    /**
     * Prints the provided options and asks the user to choose one.
     * 
     * @param options An array of options to be displayed to the user.
     * @return The chosen option as an integer.
     */
    private int askForChoice(String[] options) {
        for (int i = 0; i < options.length; i++) {
            System.out.printf("%d -- %s%n", i + 1, options[i]);
        }
        System.out.print("\nPlease enter your choice (e.g., '1' for the first option): ");

        while (true) {
            String input = scanner.nextLine().trim(); // Use nextLine to handle the entire line of input
            try {
                int choice = Integer.parseInt(input); // Attempt to parse the input as an integer
                if (choice >= 1 && choice <= options.length) {
                    return choice; // Return the choice if it's within the valid range
                } else {
                    System.out.printf("Please enter a valid choice between 1 and %d: ", options.length);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number."); // Handle non-integer inputs
            }
        }
    }

//Login as a student------------------------------------------------------------
    /**
     * Handles actions available to a logged-in student.
     *
     * @param student The logged-in student instance.
     * @param file The FileInfoReader instance for accessing course data.
     */
    private void StudentActions(Student student, FileInfoReader file) {
        boolean isRunning = true;
        while (isRunning) {
            int option = StudentInterface(student);
            switch (option) {
                case 1:
                    student.viewAllCourse(file);
                    break;
                case 2:
                    processCourseAction(student, file, true); // true for adding course
                    break;
                case 3:
                    student.checkCourseSchedule();
                    break;
                case 4:
                    processCourseAction(student, file, false); // false for dropping course
                    break;
                case 5:
                    student.viewGrades(file);
                    break;
                case 6:
                    isRunning = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }

    /**
     * Processes adding or dropping a course based on the student's choice.
     *
     * @param student The student instance.
     * @param file The FileInfoReader instance.
     * @param isAdding Indicates whether the course is being added (true) or dropped (false).
     */
    private void processCourseAction(Student student, FileInfoReader file, boolean isAdding) {
        String courseId;
        while (!(courseId = promptCourseAction(isAdding)).equals("q")) {
            if (isAdding) {
                student.addCourse(courseId, file);
            } else {
                student.dropCourse(courseId);
            }
        }
    }

    /**
     * Prompts the user to input a course ID for adding or dropping a course.
     *
     * @param isAdding Indicates whether the prompt is for adding (true) or dropping (false) a course.
     * @return The course ID input by the user.
     */
    private String promptCourseAction(boolean isAdding) {
        System.out.print(isAdding ? "Enter course ID to add or 'q' to quit: " : "Enter course ID to drop or 'q' to quit: ");
        return scanner.nextLine().trim();
    }

//Login as a professor------------------------------------------------------------
    /**
     * Handles the actions available to a logged-in professor.
     *
     * @param professor The professor object.
     * @param file The FileInfoReader object for accessing data.
     */
    private void handleProfActions(Professor professor, FileInfoReader file) {
        boolean isRunning = true;

        while (isRunning) {
            int option = ProfessorInterface(professor);  // Display professor menu and get choice

            switch (option) {
                case 1:
                    // View given courses
                    professor.printCoursesTaught(file);
                    break;
                case 2:
                    // View the student list for a selected course
                    handleStudentList(professor, file);
                    break;
                case 3:
                    // Exit the loop and method
                    isRunning = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
    
    private void handleStudentList(Professor professor, FileInfoReader file) {
        String prompt = "Enter course ID to view its students, or 'q' to quit: ";
        String courseId = promptForCourseID(professor, file, prompt);
        if (courseId.equalsIgnoreCase("q")) {
            return;  // Early exit if 'q' is entered
        }
        professor.studentInCourse(courseId, file);
    }
    
    /**
     * Prompts the professor for a course ID.
     *
     * @param professor The professor object.
     * @param prompt The message prompt for input.
     * @return The entered course ID or 'q' to quit.
     */
    private String promptForCourseID(Professor professor, FileInfoReader file, String prompt) {
        professor.printCoursesTaught(file);
        System.out.println(prompt);
        return scanner.nextLine().trim();
    }


//Login as an Admin------------------------------------------------------------
    /**
     * Handles the interaction when the user is logged in as an admin.
     *
     * @param admin the admin object
     * @param file  the file reader object
     */
    private void handleAdminActions(Admin admin, FileInfoReader file) {
        while (true) {
            int option = AdminInterface();  // Display admin menu and get choice

            switch (option) {
                case 1:
                    // View all courses
                    displayCourseList(admin, file);
                    break;
                case 2:
                    // Add new courses
                    adminAddProf(admin, file);
                    break;
                case 3:
                    // Delete courses
                    deleteCourses(admin, file);
                    break;
                case 4:
                    // Add new professor
                    adminAddProf1(admin, file);
                    break;
                case 5:
                    // Delete professor
                    deleteProfessors(admin, file);
                    break;
                case 6:
                    // Add new student
                    adminAddStudent(admin, file);
                    break;
                case 7:
                    // Delete student
                    deleteStudents(admin, file);
                    break;
                case 8:
                    // Exit the loop and method
                    return;  // Use return to exit directly, removing the need for isRunning variable
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
    
    /**
     * Displays the list of courses.
     * 
     * @param admin the admin user
     * @param file  the file containing the course information
     */
    private void displayCourseList(Admin admin, FileInfoReader file) {
        System.out.println("\n--------------------------------------------------");
        System.out.println("Course List:");
        System.out.println("--------------------------------------------------");

        admin.viewAllCourse(file);  // This method is responsible for printing out the course details.

        System.out.println("--------------------------------------------------");
        System.out.println("End of Course List");
        System.out.println("--------------------------------------------------\n");
    }
    
    // delete part
    /**
     * Deletes a course based on the provided course ID.
     * 
     * @param admin the admin user
     * @param file  the file containing the course information
     */
    private void deleteCourses(Admin admin, FileInfoReader file) {
        String courseId = promptForDeletion("course");
        if (courseId == null) {
            System.out.println("Course deletion cancelled."); // User cancelled the deletion.
            return;
        }

        // Execute the deletion without additional existence check
        try {
            admin.deleteCourse(courseId, file);
            System.out.println("Course " + courseId + " deleted successfully.");
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            // Optionally, log the exception or handle it according to your application's requirements
        }
    }

    /**
     * Deletes a professor based on the provided professor ID.
     * 
     * @param admin the admin user
     * @param file  the file containing the professor information
     */
    private void deleteProfessors(Admin admin, FileInfoReader file) {
        String profId = promptForDeletion("professor");
        if (profId == null) {
            System.out.println("Professor deletion cancelled."); // User cancelled the deletion.
            return;
        }

        try {
            admin.deleteProfessor(profId, file);
            System.out.println("Professor " + profId + " deleted successfully.");
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            // Optionally, log the exception or handle it according to your application's requirements
        }
    }

    /**
     * Deletes a student based on the provided student ID.
     * 
     * @param admin the admin user
     * @param file  the file containing the student information
     */
    private void deleteStudents(Admin admin, FileInfoReader file) {
        String studentId = promptForDeletion("student");
        if (studentId == null) {
            System.out.println("Student deletion cancelled."); // User cancelled the deletion.
            return;
        }

        try {
            admin.deleteStudent(studentId, file);
            System.out.println("Student " + studentId + " deleted successfully.");
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            // Optionally, log the exception or handle it according to your application's requirements
        }
    }

    /**
     * Prompts for the ID of the entity to be deleted and validates the input.
     * 
     * @param entityType The type of entity to delete.
     * @return The ID of the entity or null if the operation is canceled.
     */
    private String promptForDeletion(String entityType) {
        while (true) {
            System.out.printf("Enter the ID of the %s to delete, or 'q' to cancel: ", entityType);
            String input = scanner.nextLine().trim();  // Use nextLine to capture the full input line

            if (input.isEmpty()) {
                System.out.println("Input cannot be empty. Please try again or enter 'q' to cancel.");
                continue;  // Prompt the user again if input is empty
            }

            if (input.equalsIgnoreCase("q")) {
                return null;  // Return null to indicate cancellation
            }

            // Validate the input (optional: add specific ID format validation here if necessary)
            if (isValidId(input)) {
                return input;  // Return the valid ID
            } else {
                System.out.println("Invalid ID format. Please try again or enter 'q' to cancel.");
            }
        }
    }
    
    private boolean isValidId(String id) {
        // Example validation: ID should be numeric and at least 4 digits long
        return id.matches("\\d{4,}");
    }
    
    // Add part
    // Add course 
    /**
     * As Admin, adds a new course to the system.
     * 
     * @param admin the admin performing the operation
     * @param file  the file containing the course information
     */
    private void adminAddProf1(Admin admin, FileInfoReader file) {
        String profId = getUniqueProfessorId(admin, file);
        if (profId == null) return;

        String name = getInput("Enter the professor's name, or 'q' to quit: ");
        if (name == null) return;

        String username = getUniqueUsername(admin, file);
        if (username == null) return;

        String password = getInput("Enter a password: ");
        if (password == null) return;

        // Constructing the professor's information string
        String profInfo = String.join(";", name, profId, username, password);
        admin.addProfessor(profInfo, file);
        System.out.println("Successfully added new professor: " + name + " (ID: " + profId + ")");
    }
    
    // Add professor
    /**
     * Adds a new professor to the system.
     * 
     * @param admin The admin responsible for adding the professor.
     * @param file  The file reader object containing the professor information.
     */
    private void adminAddProf(Admin admin, FileInfoReader file) {
        String profId = getUniqueProfessorId(admin, file);
        if (profId == null) return;

        String name = getInput("Enter the professor's name, or 'q' to quit: ");
        if (name == null) return;

        String username = getUniqueUsername(admin, file);
        if (username == null) return;

        String password = getInput("Enter a password: ");
        if (password == null) return;

        // Constructing the professor's information string
        String profInfo = String.join(";", name, profId, username, password);
        admin.addProfessor(profInfo, file);
        System.out.println("Successfully added new professor: " + name + " (ID: " + profId + ")");
    }

    /**
     * Prompts for and validates a unique professor ID.
     * 
     * @param admin The admin object.
     * @param file  The file reader object.
     * @return A unique professor ID or null if the operation is canceled.
     */
    private String getUniqueProfessorId(Admin admin, FileInfoReader file) {
        String profId;
        while (true) {
            System.out.println("Enter the professor's ID, or type 'q' to quit:");
            profId = scanner.nextLine().trim();

            if ("q".equalsIgnoreCase(profId)) {
                System.out.println("Operation cancelled."); // User decided to cancel the operation
                return null;
            }

            if (profId.isEmpty()) {
                System.out.println("Professor ID cannot be empty. Please try again.");
                continue;
            }

            if (admin.isUniqueProfessorId(profId, file)) {
                return profId; // Return the valid and unique ID
            } else {
                System.out.println("This ID already exists. Please enter a different ID.");
            }
        }
    }

    /**
     * Prompts for and validates a unique username.
     * 
     * @param admin The admin object.
     * @param file  The file reader object.
     * @return A unique username or null if the operation is canceled.
     */
    private String getUniqueUsername(Admin admin, FileInfoReader file) {
        String username;
        while (true) {
            System.out.println("Enter a username, or type 'q' to quit:");
            username = scanner.nextLine().trim();

            if ("q".equalsIgnoreCase(username)) {
                System.out.println("Operation cancelled."); // User decided to cancel the operation
                return null;
            }

            if (username.isEmpty()) {
                System.out.println("Username cannot be empty. Please try again.");
                continue;
            }

            if (admin.isProfUsernameUnique(username, file)) {
                return username; // Return the valid and unique username
            } else {
                System.out.println("This username already exists. Please enter a different username.");
            }
        }
    }
    
    // Add Student
    /**
     * Adds a new student to the system with the provided information.
     * 
     * @param admin The admin performing the operation.
     * @param file  The file containing the student information.
     */
    private void adminAddStudent(Admin admin, FileInfoReader file) {
        String studentId = getUniqueStudentId(admin, file);
        if (studentId == null) return;

        String name = getInput("Enter the student's name, or 'q' to quit: ");
        if (name == null) return;

        String username = getUniqueUsernameForStudent(admin, file);
        if (username == null) return;

        String password = getInput("Enter a password: ");
        if (password == null) return;

        String studentInfo = buildStudentInfo(studentId, name, username, password);
        admin.addStudent(studentInfo, file);
        System.out.println("Successfully added new student: " + name + " (ID: " + studentId + ")");
    }

    /**
     * Prompts for and validates a unique student ID.
     * 
     * @param admin The admin object.
     * @param file  The file reader object.
     * @return A unique student ID or null if the operation is canceled.
     */
    private String getUniqueStudentId(Admin admin, FileInfoReader file) {
        while (true) {
            System.out.println("Enter the student's ID, or type 'q' to quit:");
            String studentId = scanner.nextLine().trim();

            if ("q".equalsIgnoreCase(studentId)) {
                System.out.println("Operation cancelled."); // User decided to cancel the operation
                return null;
            }

            if (studentId.isEmpty()) {
                System.out.println("Student ID cannot be empty. Please try again.");
                continue;
            }

            if (admin.isUniqueStudentId(studentId, file)) {
                return studentId; // Return the valid and unique ID
            } else {
                System.out.println("This ID already exists. Please enter a different ID.");
            }
        }
    }

    /**
     * Prompts for and validates a unique username for a student.
     * 
     * @param admin The admin object.
     * @param file  The file reader object.
     * @return A unique username for a student or null if the operation is canceled.
     */
    private String getUniqueUsernameForStudent(Admin admin, FileInfoReader file) {
        while (true) {
            System.out.println("Enter a username for the student, or type 'q' to quit:");
            String username = scanner.nextLine().trim();

            if ("q".equalsIgnoreCase(username)) {
                System.out.println("Operation cancelled."); // User decided to cancel the operation
                return null;
            }

            if (username.isEmpty()) {
                System.out.println("Username cannot be empty. Please try again.");
                continue;
            }

            if (admin.isStudentUsernameUnique(username, file)) {
                return username; // Return the valid and unique username
            } else {
                System.out.println("This username already exists. Please enter a different username.");
            }
        }
    }

    /**
     * Constructs the information string for a new student.
     * 
     * @param studentId The student's ID.
     * @param name      The student's name.
     * @param username  The student's username.
     * @param password  The student's password.
     * @return The student information as a string.
     */
    private String buildStudentInfo(String studentId, String name, String username, String password) {
        StringBuilder studentInfo = new StringBuilder(studentId + ";" + name + ";" + username + ";" + password);
        collectStudentCourseData(studentInfo);
        return studentInfo.toString();
    }

    /**
     * Collects course data for the new student.
     * 
     * @param studentInfo StringBuilder to append course data.
     */
    private void collectStudentCourseData(StringBuilder studentInfo) {
        int count = 0;
        while (true) {
            String courseId = getInput("Enter course ID for past courses, 'n' to finish, 'q' to quit:");
            if ("n".equals(courseId)) break;
            if (courseId == null) return;

            String grade = getInput("Enter the grade for " + courseId + ", e.g., 'A': ");
            if (grade == null) return;

            studentInfo.append(count > 0 ? "," : ";").append(courseId).append(":").append(grade);
            count++;
        }
    }
    
    // Common input function for all class
    /**
     * Retrieves user input from the console with a given prompt.
     *
     * @param prompt the message displayed to the user as a prompt
     * @return the user's input as a String, or null if the input is "q"
     */
    private String getInput(String prompt) {
        System.out.println(prompt + " (Type 'q' to quit):");
        String input = scanner.nextLine().trim();  // Read the full line of input and trim whitespace

        if ("q".equalsIgnoreCase(input)) {
            return null;  // Return null if the user wants to quit
        }
        return input.isEmpty() ? null : input;  // Also return null if input is empty to prevent invalid entries
    }

}