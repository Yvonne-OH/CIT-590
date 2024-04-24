/**
 * Class Student
 * 
 * @author Yipeng Zhang & Zhanqian Wu
 */

package roles;

import courses.Course;
import files.FileInfoReader;

import java.util.*;


/**
 * Represent a student
 */


public class Student extends User {
	// HashMap to store course grades for this student
	private HashMap<String, String> courseGradesMap = new HashMap<>();
	
	// List to store courses in this student's schedule
	private List<Course> enrolledCourses = new LinkedList<>();	
    
	// Default constructor 
	public Student() {}
	
	/**
     * Constructs a student with details parsed from a given string.
     *
     * @param str String containing student details.
     */
    public Student(String str) {
        String[] student = str.split(";");
        this.id = student[0].trim();
        this.name = student[1].trim();
        this.username = student[2].trim();
        this.password = student[3].trim();
        String course = student[4].trim();
        Arrays.stream(course.split(","))
        .map(c -> c.split(":"))
        .forEach(parts -> courseGradesMap.put(parts[0].trim(), parts[1].trim()));
    }
       
    /**
     * Authenticates student login using username and password.
     *
     * @param username Student's username.
     * @param password Student's password.
     * @param file FileInfoReader instance for data access.
     * @return true if authentication is successful, false otherwise.
     */
    @Override
    public boolean login(String username, String password, FileInfoReader file) {
        return findStudentUP(username, password, file) != null;
    }

    /**
     * Retrieves the logged-in student instance.
     *
     * @param username Student's username.
     * @param password Student's password.
     * @param file FileInfoReader instance for data access.
     * @return Logged in student instance or null if not found.
     */
    @Override
    public Student getLogin (String username, String password, FileInfoReader file) {
        return findStudentUP(username, password, file);
    }

    // Check a student by username and password.
    private Student findStudentUP(String username, String password, FileInfoReader file) {
    	return file.getStudents().stream()
                .filter(s -> s.username.equals(username) && s.password.equals(password))
                .findFirst()
                .orElse(null);
    }

    // Additional function
    /**
     * Displays all courses available in the system.
     *
     * @param file FileInfoReader instance for data access.
     */
    public void viewAllCourse(FileInfoReader file) {
        ArrayList<Course> courses = file.getCourses();
        for (Course c : courses) {
            c.printCourse();
        }
    }

    /**
     * Adds a course to the student's schedule if valid.
     *
     * @param courseId ID of the course to add.
     * @param file FileInfoReader instance for data access.
     */
    public void addCourse(String courseId, FileInfoReader file) {
        // check whether in schedule
        if(courseValid(courseId, file)) {
            System.out.println("Course added successfully");
            System.out.println();
        }
    }
    
    /**
     * Retrieves the current list of courses in the student's schedule.
     *
     * @return List of enrolled courses.
     */
    public List<Course> getSchedule() {
        return this.enrolledCourses;
    }
    
    /**
     * Checks if a specific course is already scheduled for the student.
     *
     * @param courseId ID of the course to check.
     * @return true if the course is already scheduled, false otherwise.
     */
    public boolean isCourseScheduled(String courseId) {
        return enrolledCourses.stream().anyMatch(course -> course.getId().equals(courseId));
    }

    /**
     * Checks if a specific course is available in the system.
     *
     * @param courseId ID of the course to check.
     * @param file FileInfoReader instance for data access.
     * @return Optional containing the course if found, empty otherwise.
     */
    private Optional<Course> courseInSystem(String courseId, FileInfoReader file) {
        return file.getCourses().stream()
            .filter(course -> course.getId().equals(courseId))
            .findFirst();
    }

    /**
     * Validates if the selected course can be added to the student's schedule.
     *
     * @param courseId ID of the course to validate.
     * @param file FileInfoReader instance for data access.
     * @return true if the course can be added, false otherwise.
     */
    private boolean courseValid(String courseId, FileInfoReader file) {
        // check if in schedule
        if (isCourseScheduled(courseId)) {
            System.out.println("The course you selected is already in your list");
            return false;
        }

        // check if in system
        Optional<Course> courseOpt = courseInSystem(courseId, file);
        if (!courseOpt.isPresent()) {
            System.out.println("The course you selected is not in system");
            return false;
        }

        Course course = courseOpt.get();    
        // check capacity
        if (!isEnrollmentPossible(course)) {
            return false;
        }

        enrollInCourse(course);
        return true;
    }
    
    /**
     * Checks if enrolling in a new course is possible without schedule conflicts.
     *
     * @param course The new course to be checked.
     * @return true if enrollment is possible, false otherwise.
     */
    private boolean isEnrollmentPossible(Course course) {
        if (course.isFull()) {
            System.out.println("The course is full.");
            return false;
        }
        return !TimeConflict(course);
    }

    /**
     * Enrolls the student in a specified course.
     *
     * @param course The course to enroll in.
     */
    private void enrollInCourse(Course course) {
        enrolledCourses.add(course);
        course.setTotal();
        course.AddStudents(this);
    }

    /**
     * Checks for time conflicts with already enrolled courses.
     *
     * @param newCourse The new course to check.
     * @return true if there is a time conflict, false otherwise.
     */
    private boolean TimeConflict(Course newcourse) {
        return enrolledCourses.stream().anyMatch(enrolledCourse ->{
        	Set<Character> daysOfNewCourse = new HashSet<>();
        	for (char day : newcourse.getDateList()) {
        	    daysOfNewCourse.add(day);
        	}

        	Set<Character> daysOfEnrolledCourse = new HashSet<>();
        	for (char day : enrolledCourse.getDateList()) {
        	    daysOfEnrolledCourse.add(day);
        	}
        	
         // Check if there is a common day
            daysOfNewCourse.retainAll(daysOfEnrolledCourse);
            if (daysOfNewCourse.isEmpty()) {
                return false; // No common day, no conflict
            }

            // Check for time overlap
            int startNew = newcourse.getStart();
            int endNew = newcourse.getEnd();
            int startEnrolled = enrolledCourse.getStart();
            int endEnrolled = enrolledCourse.getEnd();

            // Time overlap occurs if the start of one course is within the duration of the other
            boolean isOverlap = (startNew < endEnrolled && startNew >= startEnrolled) ||
                                (endNew > startEnrolled && endNew <= endEnrolled);
            
            if (isOverlap) {
                System.out.println("The course you selected has time conflict with " + enrolledCourse.getId() + " " + enrolledCourse.getName());
                return true;
            }
            return false;
        });
    }
    
    /**
     * Removes a course from the student's schedule by its ID.
     *
     * @param courseId ID of the course to remove.
     */
    public void dropCourse(String courseId) {
        if (enrolledCourses.removeIf(course -> course.getId().equals(courseId))) {
            System.out.println("Course dropped successfully: " + courseId);
        } else {
            System.out.println("Course not found in your schedule: " + courseId);
        }
    }
      
    /**
     * Displays the student's current course schedule.
     */
    public void checkCourseSchedule() {
    	enrolledCourses.forEach(course -> course.printCourse());
    }
    
    /**
     * Prints the grades for each enrolled course.
     *
     * @param file FileInfoReader instance for data access.
     */
    public void viewGrades(FileInfoReader file) {
        courseGradesMap.forEach((courseId, grade) -> {
            String courseName = file.getCourses().stream()
                                   .filter(course -> course.getId().equals(courseId))
                                   .map(Course::getName)
                                   .findFirst()
                                   .orElse("Unknown Course");
            System.out.println("Grade for " + courseId + " " + courseName + ": " + grade);
        });
    }
}