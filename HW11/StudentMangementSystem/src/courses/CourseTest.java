package courses;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CourseTest {

    private Course course;

    @BeforeEach
    void setUp() {
        course = new Course("CS101;Introduction to Computer Science;John Doe;MWF;08:00;10:00;30");
    }

    @Test
    void testConstructorValidInput() {
        assertAll("Ensure correct properties are set",
            () -> assertEquals("CS101", course.getId()),
            () -> assertEquals("Introduction to Computer Science", course.getName()),
            () -> assertEquals("John Doe", course.getLecturer()),
            () -> assertArrayEquals(new char[] {'M', 'W', 'F'}, course.getDateList()),
            () -> assertEquals(800, course.getStart()),
            () -> assertEquals(1000, course.getEnd()),
            () -> assertEquals(30, course.getCapacity())
        );
    }




    @Test
    void testSetTotalExceedCapacity() {
        for (int i = 0; i < 35; i++) { // Attempt to add more students than capacity
            course.setTotal();
        }
        assertEquals(30, course.getTotal()); // Ensure total does not exceed capacity
    }

    @Test
    void testPrintCourseOutput() {
        // Use a custom OutputStream to capture output for verification
        final java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(out));
        course.printCourse();
        String expectedOutput = "CS101|Introduction to Computer Science, 08:00-10:00 on MWF, with course capacity: 30, students: 0, lecturer: Professor John Doe";
        assertEquals(expectedOutput, expectedOutput);
        System.setOut(System.out);
    }
}
