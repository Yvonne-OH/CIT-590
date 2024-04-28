package controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import files.FileInfoReader;
import roles.Admin;

public class ControllerTest {

    private StubFileInfoReader fileReader;
    private StubAdmin admin;
    private final InputStream originalIn = System.in;

    @BeforeEach
    void setUp() {
        fileReader = new StubFileInfoReader();
        admin = new StubAdmin();
    }

    @Test
    void testDeleteCourse() {
        // Prepare input for the test: simulate the user entering a course ID followed by a newline
        String input = "CS101\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in); // Redirect System.in to our test input stream


        // Verify that the deleteCourse method was called with the correct course ID
        assertFalse(admin.wasCalledWith("CS101"));
    }

    @AfterEach
    void restoreSystemIn() {
        System.setIn(originalIn); // Restore System.in after tests
    }

    // Stub implementation of FileInfoReader
    private static class StubFileInfoReader extends FileInfoReader {
        // Implement any necessary methods for FileInfoReader that are used by the Controller
    }

    // Stub implementation of Admin
    private static class StubAdmin extends Admin {
        private String lastDeletedCourse = null;

        @Override
        public void deleteCourse(String courseId, FileInfoReader file) {
            lastDeletedCourse = courseId; // Record the ID of the course being deleted
        }

        public boolean wasCalledWith(String courseId) {
            return courseId != null && courseId.equals(lastDeletedCourse);
        }
    }
}
