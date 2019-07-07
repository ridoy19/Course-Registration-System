package DAOMySQLImplementation;

import DAOInterface.CourseDAO;
import model.Course;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CourseDAOMySQLImplTest {
    private CourseDAO courseDAOMySQLTest;
    private Course course;

    public CourseDAOMySQLImplTest() {
    }

    @BeforeEach
    void setUp() {
        System.out.println("Preparing Test...");
        courseDAOMySQLTest = new CourseDAOMySQLImpl();
        course = new Course();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAllTest() {
        // Test Fails -- Though they are all identical -- Need Help
        List<Course> courseList = courseDAOMySQLTest.getAll();
        assertEquals(courseList,courseDAOMySQLTest.getAll());
    }

    @Test
    void addCourse() {
    }

    @Test
    void retrieve() {
    }

    @Test
    void removeCourse() {
    }

    @Test
    void update() {
    }

    @Test
    void removeAllCourse() {
    }
}