/**
 * Class Admin
 * 
 * @author Yipeng Zhang & Zhanqian Wu
 */

package roles;

import courses.Course;
import files.FileInfoReader;

public class Admin extends User {

    /**
     * constructor
     */
    public Admin(String str) {
        String[] admin = str.split(";");
        this.id = admin[0].trim();
        this.name = admin[1].trim();
        this.username = admin[2].trim();
        this.password = admin[3].trim();
    }

    // Default constructor 
    public Admin() {}

    /**
     * Authenticates admin login using username and password.
     *
     * @param username Admin's username.
     * @param password Admin's password.
     * @param file FileInfoReader instance for data access.
     * @return true if authentication is successful, false otherwise.
     */
    public boolean login(String username, String password, FileInfoReader file) {
        Admin admin = findAdminUP(username, password, file);
        if (admin != null) {
            System.out.println("Login successful for admin: " + admin.getName());
            return true;
        } else {
            System.out.println("Login failed for username: " + username);
            return false;
        }
    }
    
    /**
     * Retrieves the logged-in admin instance.
     *
     * @param username Admin's username.
     * @param password Admin's password.
     * @param file FileInfoReader instance for data access.
     * @return Logged in admin instance or null if not found.
     */
    public Admin getLogin(String username, String password, FileInfoReader file) {
        Admin admin = findAdminUP(username, password, file);
        
        if (admin == null) {
            System.out.println("No admin found with the provided credentials.");
        } else {
            System.out.println("Admin retrieved: " + admin.getName() + " (Username: " + admin.getUsername() + ")");
        }
        
        return admin;
    }

    /**
     * Finds an admin by their username and password.
     *
     * @param username Admin's username.
     * @param password Admin's password.
     * @param file FileInfoReader instance for data access.
     * @return Admin instance or null if not found.
     */
    private Admin findAdminUP(String username, String password, FileInfoReader file) {
        return file.getAdmins().stream()
                .filter(admin -> admin.username.equals(username) && admin.password.equals(password))
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Displays all courses available in the system.
     *
     * @param file FileInfoReader instance for data access.
     */
    public void viewAllCourse(FileInfoReader file) {
        if (file == null || file.getCourses() == null) {
            System.out.println("No courses found.");
            return;
        }

        System.out.println("Available courses:");
        for (Course course : file.getCourses()) {
            System.out.println("- " + course.toString());
        }
    }    

    //2- add new courses
    /**
     * Adds a new course to the system.
     * 
     * @param strCourse String representation of the new course.
     * @param file FileInfoReader instance for data access.
     * @param lecturerId ID of the lecturer.
     * @param courseId ID of the new course.
     */
    public void addCourses(String strCourse, FileInfoReader file, String lecturerId, String courseId) {
        // get professor info based on id
        Professor prof = file.getProfessor(lecturerId);

        // check if professor has conflict
        prof.setCoursesTaught(file);
        Course str = new Course(strCourse);


        if (prof.hasCourseConflict(str)) {
        	prof.findConflictingCourse(str).ifPresent(conflictCourse -> printConflict(conflictCourse, str));
        } else {
            file.addCourse(strCourse);
            Course addedCourse = file.getCourse(courseId);
            System.out.print("Successfully added the course: ");
            addedCourse.printCourse();
        }
    }


    /**
     * Checks if a course with the given ID already exists.
     *
     * @param id the ID of the course to check
     * @param file the file reader
     * @return true if the course ID is unique, false otherwise
     */
    public boolean isCourseIdUnique(String id, FileInfoReader file) {
        Course course = file.getCourse(id);
        if (course != null) {
            System.out.println("The course with ID " + id + " already exists.");
            return false;
        }
        return true;
    }



    /**
     * Checks if a professor with the given ID exists.
     *
     * @param lecturerId the ID of the professor to check
     * @param file the file reader
     * @return true if the professor exists, false otherwise
     */
    
    public boolean doesProfessorExist(String lecturerId, FileInfoReader file) {
        for (Professor professor : file.getProfessors()) {
            if (professor.getId().equals(lecturerId)) {
                return true;
            }
        }
        return false;
    }


    /**
     * Prints a conflict message when a course conflicts with an existing course.
     *
     * @param course the course with which the new course conflicts
     * @param newCourse the new course
     */
    
    private void printConflict(Course course, Course newCourse) {
        System.out.print("The newly added course conflicts with the following course: ");
        course.printCourse();
        System.out.print("Unable to add the new course: ");
        newCourse.printCourse();
    }
    
    //3-Delete Course
    /**
     * Deletes a course by its ID.
     *
     * @param courseId The ID of the course to delete.
     * @param file The FileInfoReader instance.
     */
    public void deleteCourse(String courseId, FileInfoReader file) {
        if (file.getCourse(courseId) != null) {
            file.removeCourse(courseId);
            System.out.println("Course deletion successful.");
        } else {
            System.out.println("Course deletion failed: Course does not exist.");
        }
    }

    
    
    //4-Add new professor
    /**
     * Adds a new professor using their string representation.
     *
     * @param professorInfo String representation of the professor.
     * @param file The FileInfoReader instance.
     */
    public void addProfessor(String professorInfo, FileInfoReader file) {
        if (isUniqueProfessorId(professorInfo, file)) {
            file.addProfessor(professorInfo);
        }
    }

    /**
     * Validates the uniqueness of a professor's ID.
     *
     * @param id The ID of the professor.
     * @param file The FileInfoReader instance.
     * @return True if the ID is unique, false otherwise.
     */
    public boolean isUniqueProfessorId(String id, FileInfoReader file) {
        if (file.professorExists(id)) {
            System.out.println("Unable to add professor: Professor ID already exists.");
            return false;
        }
        return true;
    }

    /**
     * Validates the uniqueness of a professor's username.
     *
     * @param username The username to check.
     * @param file The FileInfoReader instance.
     * @return True if the username is unique, false otherwise.
     */

    public boolean isProfUsernameUnique(String username, FileInfoReader file) {
        boolean isExist = file.getProfessors().stream()
                .anyMatch(prof -> prof.getUsername().equals(username));
        if (isExist) {
            System.out.println("Unable to add professor: Username is not available.");
            return false;
        }
        return true;
    }
    
    
    //5-Delete Professor
    /**
     * Deletes a professor based on their ID.
     *
     * @param professorId The ID of the professor to delete.
     * @param file The FileInfoReader instance.
     */
    public void deleteProfessor(String professorId, FileInfoReader file) {
        if (file.professorExists(professorId)) {
            file.removeProfessor(professorId);
            System.out.println("Professor deletion successful.");
        } else {
            System.out.println("Professor deletion failed: Professor does not exist.");
        }
    }
    

    //6-Add new student
    /**
     * Adds a new student to the system.
     *
     * @param studentInfo The string representation of the new student.
     * @param file The FileInfoReader instance.
     */
    public void addStudent(String studentInfo, FileInfoReader file) {
        if (isUniqueStudentId(studentInfo, file)) {
            file.addStudent(studentInfo);
        }
    }

    /**
     * Validates the uniqueness of a student's ID.
     *
     * @param id The ID of the student.
     * @param file The FileInfoReader instance.
     * @return True if the ID is unique, false otherwise.
     */
    public boolean isUniqueStudentId(String id, FileInfoReader file) {
        if (file.studentExists(id)) {
            System.out.println("Unable to add student: Student ID already exists.");
            return false;
        }
        return true;
    }


    /**
     * Validates the uniqueness of a student's username.
     *
     * @param username The username to check.
     * @param file The FileInfoReader instance.
     * @return True if the username is unique, false otherwise.
     */
    
    public boolean isStudentUsernameUnique(String username, FileInfoReader file) {
        boolean isExist = file.getStudents().stream()
                .anyMatch(stu -> stu.getUsername().equals(username));
        if (isExist) {
            System.out.println("Username is not available.");
            return false;
        }
        return true;
    }
    
    //7-Delete Student
    /**
     * Deletes a student by their ID.
     *
     * @param studentId The ID of the student to delete.
     * @param file The FileInfoReader instance.
     */
    public void deleteStudent(String studentId, FileInfoReader file) {
        if (file.studentExists(studentId)) {
            file.removeStudent(studentId);
            System.out.println("Student successfully deleted.");
        } else {
            System.out.println("Student deletion failed: Student does not exist.");
        }
    }

}