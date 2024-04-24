/**
 * Class Courses
 * 
 * @author Yipeng Zhang & Zhanqian Wu
 */

package roles;

import courses.Course;
import files.FileInfoReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.List;
import java.util.Optional;

/**
 * Represent professor object
 */


public class Professor extends User{
     
    ArrayList<Course> courses = new ArrayList<>(); // course list
    
 	// Default constructor 
    public Professor() {}
    /**
     * Constructs a professor using a string with their details.
     *
     * @param str A string containing the professor's details.
     */
    public Professor(String str) {
        String[] professor = str.split(";");
        this.name = professor[0].trim();
        this.id = professor[1].trim();
        this.username = professor[2].trim();
        this.password = professor[3].trim();
    }

    /**
     * Authenticates professor login using username and password.
     *
     * @param username Professor's username.
     * @param password Professor's password.
     * @param file FileInfoReader instance for data access.
     * @return true if authentication is successful, false otherwise.
     */
    @Override
    public boolean login(String username, String password, FileInfoReader file) {
        return findProfUP(username, password, file) != null;
    }
   
    /**
     * Authenticates professor login using username and password.
     *
     * @param username Professor's username.
     * @param password Professor's password.
     * @param file FileInfoReader instance for data access.
     * @return true if authentication is successful, false otherwise.
     */
    @Override
    public Professor getLogin (String username, String password, FileInfoReader file) {
        return findProfUP (username, password, file);
    }
    
    /**
     * Finds a professor by their username and password.
     *
     * @param username Professor's username.
     * @param password Professor's password.
     * @param file FileInfoReader instance for data access.
     * @return Professor instance or null if not found.
     */
    private Professor findProfUP (String username, String password, FileInfoReader file) {
        ArrayList<Professor> professors = file.getProfessors();
        for (Professor prof : professors) {
            if (prof.username.equals(username) && prof.password.equals(password)) {
                return prof;
            }
        }
        return null;
    }
    
    // Additional function   
    
    /**
     * Retrieves the list of courses taught by the professor.
     *
     * @return List of courses taught.
     */
    public ArrayList<Course> getCourses() {
        return this.courses;
    }
    
    /**
     * Sets the list of courses taught by the professor based on the FileInfoReader data.
     *
     * @param file FileInfoReader instance for data access.
     */
    public void setCoursesTaught(FileInfoReader file) {
        List<Course> allCourses = file.getCourses();
        this.courses.clear();
        this.courses.addAll(allCourses.stream()
                .filter(course -> course.getLecturer().equals(this.name))
                .collect(Collectors.toList()));
    }

    /**
     * Prints the list of courses taught by the professor.
     *
     * @param file FileInfoReader instance for data access.
     */
    public void printCoursesTaught(FileInfoReader file) {
    	setCoursesTaught(file);
        System.out.println("------------The course list------------");
        for (Course course : this.courses) {
            course.printCourse();
        }
        System.out.println();
    }
        
    /**
     * Displays the list of students enrolled in a specific course taught by this professor.
     *
     * @param courseId ID of the course.
     * @param file FileInfoReader instance for data access.
     */
    public void studentInCourse(String courseId, FileInfoReader file) {
        if (this.courses.isEmpty()) {
            setCoursesTaught(file);
        }
        
        Course course = file.getCourse(courseId);
        if (course != null && course.getLecturer().equals(this.getName())) {
            System.out.printf("Students in your course %s %s:%n", courseId, course.getName());
            file.getStudents().stream()
                .filter(student -> student.isCourseScheduled(courseId))
                .forEach(student -> System.out.printf("%s %s%n", student.getId(), student.getName()));
        } else {
            System.out.printf("No course found with ID %s or you do not teach this course.%n", courseId);
        }
        System.out.println();

    }
    
    /**
     * Checks for time conflicts with the new course and existing courses taught.
     *
     * @param newCourse The new course to check for conflicts.
     * @return true if there is a conflict, false otherwise.
     */
    public boolean hasCourseConflict(Course newCourse) {
        return getCourses().stream().anyMatch(existingCourse -> coursesConflict(existingCourse, newCourse));
    }
    
    /**
     * Finds a course that conflicts with the new course if any.
     *
     * @param newCourse The new course to check for conflicts.
     * @return An Optional containing the conflicting course, empty if no conflict.
     */
    public Optional<Course> findConflictingCourse(Course newCourse) {
        return getCourses().stream().filter(existingCourse -> coursesConflict(existingCourse, newCourse)).findFirst();
    }

    /**
     * Finds a course that conflicts with the new course if any.
     *
     * @param newCourse The new course to check for conflicts.
     * @return An Optional containing the conflicting course, empty if no conflict.
     */
    private boolean coursesConflict(Course course1, Course course2) {
        int start1 = course1.getStart();
        int end1 = course1.getEnd();
        int start2 = course2.getStart();
        int end2 = course2.getEnd();

        return Arrays.equals(course1.getDateList(), course2.getDateList()) && (start2 < end1 && end2 > start1);
    }
}