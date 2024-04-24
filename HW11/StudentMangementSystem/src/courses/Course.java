/**
 * Class Courses
 * 
 * @author Yipeng Zhang & Zhanqian Wu
 */

package courses;

import roles.Student;

import java.util.ArrayList;
//import java.util.Locale;

public class Course {

    
    String id; // course id
    String courseName; // course name
    String lecturer; //lecturer name
    String date; // dates of course
    String start; // start time of course
    String end; // end time of date
    
    int capacity; // capacity of course
    int total = 0; //total number of students in the course
    

    // List of students of this course  
    ArrayList<Student> students = new ArrayList<>();
    char[] dateList; // date
    int Start; // start time 
    int End; // end time
    
    public Course(String str) {
        String[] course = str.split(";");
        
        // Check that the input string is correctly formatted
        if (course.length != 7) {
            throw new IllegalArgumentException("Invalid course string: " + str);
        }
        
        this.id = course[0].trim();
        this.courseName = course[1].trim();
        this.lecturer = course[2].trim();
        this.date = course[3].trim();
        this.start = course[4].trim();
        this.end = course[5].trim();
        
        // change the format for easy to read and analyze
        try {
            this.Start = Integer.valueOf(this.start.replaceAll(":", ""));
            this.End = Integer.valueOf(this.end.replaceAll(":", ""));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid start or end time: " + start + ", " + end);
        }

        this.dateList = this.date.toCharArray();

        try {
            this.capacity = Integer.valueOf(course[6].trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid capacity: " + course[6]);
        }
    }

    /**
     * Prints the course information in the following format:
     * "{id}|{courseName}, {start}-{end} on {day}, with course capacity: {capacity}, students: {total}, lecturer: Professor {lecturer}"
     */
    
    public void printCourse() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.id).append("|").append(this.courseName)
            .append(", ").append(this.start).append("-").append(this.end)
            .append(" on ").append(this.date)
            .append(", with course capacity: ").append(this.capacity)
            .append(", students: ").append(this.total)
            .append(", lecturer: Professor ").append(this.lecturer);
        System.out.println(sb.toString());
    }
    
    // get ID of course
    public String getId() {
        return this.id;
    }

    // get capacity of course
    public int getCapacity() {
        return this.capacity;
    }

    // get total number of students in the course
    public int getTotal() {
        return this.total;
    }

    // get date
    public char[] getDateList() {
        return this.dateList.clone();
    }

    // get start time
    public int getStart() {
        return this.Start;
    }

    // get end time
    public int getEnd() {
        return this.End;
    }

    // get course name
    public String getName() {
        return this.courseName;
    }

    // get lecturer name
    public String getLecturer() {
        return this.lecturer;
    }

    // Add number of student in the course
    public void setTotal() {
    	if(total < capacity) {
    		this.total++;
    	}
        
    }

    // Add student to this course
    public void AddStudents(Student student) {
        if(student != null){
            this.students.add(student);
        }
    }
    
    // judge the course is full or not
    public boolean isFull() {
        return total >= capacity;
    }

}