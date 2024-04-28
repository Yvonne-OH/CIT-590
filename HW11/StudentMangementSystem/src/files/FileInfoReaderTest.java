package files;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.Files;
import java.nio.file.Path;

class FileInfoReaderTest {
    @TempDir
    Path tempDir; // A temporary directory for test data, automatically cleaned up after tests.

    FileInfoReader reader; // The FileInfoReader instance used for testing.

    @BeforeEach
    void setUp() throws Exception {
        // Create temporary files and write initial test data to them.
        Files.writeString(tempDir.resolve("studentInfo.txt"), "Student1;Information");
        Files.writeString(tempDir.resolve("profInfo.txt"), "Prof1;Information");
        Files.writeString(tempDir.resolve("adminInfo.txt"), "Admin1;Information");
        Files.writeString(tempDir.resolve("courseInfo.txt"), "Course1;CS101;John Doe;MWF;08:00;10:00;30");

        // Override paths to use temporary files instead of actual files.
        System.setProperty("studentInfoPath", tempDir.resolve("studentInfo.txt").toString());
        System.setProperty("profInfoPath", tempDir.resolve("profInfo.txt").toString());
        System.setProperty("adminInfoPath", tempDir.resolve("adminInfo.txt").toString());
        System.setProperty("courseInfoPath", tempDir.resolve("courseInfo.txt").toString());

        reader = new FileInfoReader(); // Initialize the FileInfoReader instance.
    }

    @Test
    void testInitialLoad() {
        // Test to check if initial data load from files is correct.
        assertEquals(2, reader.getStudents().size());
        assertEquals(32, reader.getProfessors().size());
        assertEquals(3, reader.getAdmins().size());
        assertEquals(50, reader.getCourses().size());
    }

    @Test
    void testAddAndRemoveCourse() {
        // Test adding a new course and then removing it.
        String newCourseInfo = "Course2;CS102;Jane Doe;TTh;09:00;11:00;40";
        reader.addCourse(newCourseInfo);
        assertEquals(51, reader.getCourses().size()); // Check if the course was added.

        reader.removeCourse("Course2");
        assertEquals(50, reader.getCourses().size()); // Check if the course was removed.
    }

    @Test
    void testExistsFunctions() {
        // Test existence checks for non-existing entities.
        assertFalse(reader.courseExists("Course999")); // Course does not exist.
        assertFalse(reader.professorExists("Prof999")); // Professor does not exist.
    }

    @Test
    void testGetFunctions() {
        // Test retrieval of non-existing entities.
        assertNull(reader.getCourse("Course999")); // Trying to get a non-existing course.
        assertNull(reader.getProfessor("Prof999")); // Trying to get a non-existing professor.
    }
}
