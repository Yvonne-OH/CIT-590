package files;

import roles.Admin;
import roles.Professor;
import roles.Student;
import courses.Course;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileInfoReader {

    private static final Logger LOGGER = Logger.getLogger(FileInfoReader.class.getName());

    private ArrayList<Course> courses = new ArrayList<>();
    private ArrayList<Student> students = new ArrayList<>();
    private ArrayList<Admin> admins = new ArrayList<>();
    private ArrayList<Professor> professors = new ArrayList<>();

    // Paths to the data files
    private static final String STUDENT_INFO_PATH = "src/studentInfo.txt";
    private static final String PROF_INFO_PATH = "src/profInfo.txt";
    private static final String ADMIN_INFO_PATH = "src/adminInfo.txt";
    private static final String COURSE_INFO_PATH = "src/courseInfo.txt";

    public FileInfoReader() {
        loadInitialData();
    }

    private void loadInitialData() {
        try {
            // Read data from files
            List<String> studentList = Files.readAllLines(Paths.get(STUDENT_INFO_PATH));
            List<String> profList = Files.readAllLines(Paths.get(PROF_INFO_PATH));
            List<String> adminList = Files.readAllLines(Paths.get(ADMIN_INFO_PATH));
            List<String> courseList = Files.readAllLines(Paths.get(COURSE_INFO_PATH));

            // Construct objects from data
            courses.addAll(constructCourses(courseList));
            students.addAll(constructStudents(studentList));
            admins.addAll(constructAdmins(adminList));
            professors.addAll(constructProfessors(profList));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error reading from files", e);
            throw new RuntimeException("Failed to initialize FileInfoReader due to I/O errors", e);
        }
    }

    private List<Course> constructCourses(List<String> courseList) {
        List<Course> tempList = new ArrayList<>();
        courseList.forEach(courseInfo -> tempList.add(new Course(courseInfo)));
        return tempList;
    }

    private List<Admin> constructAdmins(List<String> adminList) {
        List<Admin> tempList = new ArrayList<>();
        adminList.forEach(adminInfo -> tempList.add(new Admin(adminInfo)));
        return tempList;
    }

    private List<Student> constructStudents(List<String> studentList) {
        List<Student> tempList = new ArrayList<>();
        studentList.forEach(studentInfo -> tempList.add(new Student(studentInfo)));
        return tempList;
    }

    private List<Professor> constructProfessors(List<String> profList) {
        List<Professor> tempList = new ArrayList<>();
        profList.forEach(profInfo -> tempList.add(new Professor(profInfo)));
        return tempList;
    }

    public ArrayList<Student> getStudents() {
        return new ArrayList<>(students);
    }

    public ArrayList<Admin> getAdmins() {
        return new ArrayList<>(admins);
    }

    public ArrayList<Professor> getProfessors() {
        return new ArrayList<>(professors);
    }

    public ArrayList<Course> getCourses() {
        return new ArrayList<>(courses);
    }

    public void addCourse(String courseInfo) {
        courses.add(new Course(courseInfo));
    }

    public void addStudent(String studentInfo) {
        students.add(new Student(studentInfo));
    }

    public void addProfessor(String professorInfo) {
        professors.add(new Professor(professorInfo));
    }

    public boolean courseExists(String courseId) {
        return courses.stream().anyMatch(course -> course.getId().equals(courseId));
    }

    public boolean professorExists(String professorId) {
        return professors.stream().anyMatch(professor -> professor.getId().equals(professorId));
    }

    public boolean studentExists(String studentId) {
        return students.stream().anyMatch(student -> student.getId().equals(studentId));
    }

    public Professor getProfessor(String profId) {
        return professors.stream().filter(prof -> prof.getId().equals(profId)).findFirst().orElse(null);
    }

    public Course getCourse(String courseId) {
        return courses.stream().filter(course -> course.getId().equals(courseId)).findFirst().orElse(null);
    }

    public void removeCourse(String courseId) {
        courses.removeIf(course -> course.getId().equals(courseId));
    }

    public void removeProfessor(String professorId) {
        professors.removeIf(professor -> professor.getId().equals(professorId));
    }

    public void removeStudent(String studentId) {
        students.removeIf(student -> student.getId().equals(studentId));
    }
}
