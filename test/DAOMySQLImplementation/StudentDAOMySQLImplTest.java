package DAOMySQLImplementation;

import DAOInterface.StudentDAO;
import com.company.DBConnectionSingleton;
import model.Student;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class StudentDAOMySQLImplTest {
    private StudentDAO studentDAOMySQLTest;
    private Student student;

    StudentDAOMySQLImplTest() {
    }


    @BeforeEach
    void setUp() {
        System.out.println("Preparing Test...");
        studentDAOMySQLTest = new StudentDAOMySQLImpl();
        student = new Student();
    }

    @AfterEach
    void tearDown() {
    }

    @BeforeAll
    static void setUpBeforeAll() {
        /*Connection connection = DBConnectionSingleton.getConnection();

        try {
            InputStream inputStream = getClass().getResourceAsStream("/resources/query.properties");
            InputStreamReader fileReader = new InputStreamReader(inputStream);
            Properties properties = new Properties();
            properties.load(fileReader);

            PreparedStatement preparedStatement =
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    @Test
    void createTest() {
        System.out.println("Running \"Create\" Method Test...");
        student = new Student("2016000000050","Elon Mask");
        Student createdStudent = studentDAOMySQLTest.create(student);
        assertEquals(student,createdStudent);
    }

    @Test
    void retrieveByIdTest() {
        student = new Student("2016000000045","Jeff Bezos");
        //studentDAOMySQLTest.create(student);
        assertEquals(student,studentDAOMySQLTest.retrieve(student.getId()));
    }

    @Test
    void retrieveAllTest() {
        /*List<Student> studentList = new ArrayList<>();
        studentList.add(new Student("2016000000044","Elon Mask"));
        studentList.add(new Student("2016000000045","Jeff Bezos"));
        studentList.add(new Student("2016000000046","Mark Zuckerburg"));
        studentList.add(new Student("2016000000047","Robert Darwin"));*/

        List<Student> studentList = studentDAOMySQLTest.retrieve();
        assertEquals(studentList,studentDAOMySQLTest.retrieve());
    }

    @Test
    void retrieveByPredicateTest() {
        List<Student> predictStudent = studentDAOMySQLTest.retrieve(student1 -> student1.getId().endsWith("41"));
        assertSame(predictStudent,studentDAOMySQLTest.retrieve(student1 -> student1.getId().endsWith("41")));
    }

    @Test
    void updateTest() {
        Student elon_musk = studentDAOMySQLTest.update("2016000000050", new Student("2016000000050", "Elon Musk"));
        assertEquals(elon_musk,studentDAOMySQLTest.update("2016000000050", new Student("2016000000050", "Elon Musk")));
    }

    @Test
    void deleteByIdTest() {
        boolean deleted = studentDAOMySQLTest.delete("2016000000041");
        assertTrue(deleted, "This will passed");
    }

    @Test
    void deleteAllTest() {
        boolean deleteAll = studentDAOMySQLTest.deleteAll();
        assertTrue(deleteAll);
    }

}