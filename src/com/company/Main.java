package com.company;

import DAOFileImplementation.CourseDAOFileImpl;
import DAOFileImplementation.FacultyDAOFileImpl;
import DAOFileImplementation.StudentDAOFileImpl;
import DAOInterface.*;
import DAOMySQLImplementation.*;
import enumeration.FacultyRank;
import model.Course;
import model.Faculty;
import model.Registration;
import model.Section;
import model.Student;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private void propertiesDemo() {
        try {
            //FileReader fileReader = new FileReader("db.properties");
            InputStream inputStream = getClass().getResourceAsStream("resources/db.properties");
            System.out.printf("InputStream %s\n",inputStream);
            InputStreamReader reader = new InputStreamReader(inputStream);
            System.out.println("reader : "+reader);
            Properties properties = new Properties();
            properties.load(reader);
            String username = properties.getProperty("username");
            System.out.printf("Username [%s]\n", username);
            String username1 = System.getProperty("username");
            String password = System.getProperty("password");
            System.out.printf("Username [%s] Password [%s]\n",username1,password);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }


    public Main() {
        StudentDAO studentDAO = new StudentDAOMySQLImpl();
        /*studentDAO.create(new Student("2016000000044", "Elon Musk"));
        studentDAO.create(new Student("2016000000045", "Jeff Bezos"));
        studentDAO.create(new Student("2016000000046", "Mark Zuckerburg"));
        studentDAO.create(new Student("2016000000047", "Robert Darwin"));*/

        // Retrieving a student
        //System.out.println(studentDAO.retrieve("2016000000047"));

        // Retrieving All Students
        //studentDAO.retrieve().forEach(System.out::println);

        // Retrieving using predicates
        //studentDAO.retrieve(student -> student.getName().startsWith("E")).forEach(System.out::println);

        // deleting a students
        //studentDAO.delete("2016000000046");

        // Updating a student
        //studentDAO.update("2016000000045", new Student("2016000000045", "Albert Einstein"));

        // Deleting All Students
        //studentDAO.deleteAll();


        // CSV file Operations
        StudentDAO studentDAOFile = new StudentDAOFileImpl();

        // Creating Student
        /*studentDAOFile.create(new Student("2016000000044", "Elon Musk"));
        studentDAOFile.create(new Student("2016000000045", "Jeff Bezos"));
        studentDAOFile.create(new Student("2016000000049", "Mark Zuckerberg"));
        studentDAOFile.create(new Student("2016000000047", "Robert Darwin"));
        studentDAOFile.create(new Student("2016000000030", "Jennifer Lawrence"));
        studentDAOFile.create(new Student("2016000000052", "Salma Hayek"));
        studentDAOFile.create(new Student("2016000000054", "Jason Statham"));
        studentDAOFile.create(new Student("2016000000057", "Tom Crusie"));
        studentDAOFile.create(new Student("2016000000058", "Tom Hiddleston"));*/

        // This student checks id greater than 13 characters
        //studentDAOFile.create(new Student("20160000000055", "Jahan"));

        // Retrieving Student
        //studentDAOFile.retrieve().forEach(System.out::println);

        // Retrieving By Id
        //System.out.println(studentDAOFile.retrieve("2016000000030"));

        // Retrieving By Predicate
        //studentDAOFile.retrieve(student -> student.getName().startsWith("J")).forEach(System.out::println);

        // Deleting All records
        //studentDAOFile.deleteAll();

        // Deleting a student with id
        //studentDAOFile.delete("2016000000052");

        // Updating a student with id
        //studentDAOFile.update("2016000000052",new Student("2016000000052","Emma Stone"));
        //studentDAOFile.update("2016000000052",new Student("2016000000052","Amber Heard"));

        FacultyDAO facultyDAOMySQL = new FacultyDAOMySQLImpl();
        /*facultyDAOMySQL.create(new Faculty("KMH","Monirul Hasan","Senior Lecturer"));
        facultyDAOMySQL.create(new Faculty("RIK","Rezwan Al Islam Khan","Assistant Professor"));
        facultyDAOMySQL.create(new Faculty("RAJ","Roksana Akter Jolly","Assistant Professor"));
        facultyDAOMySQL.create(new Faculty("SM","Shahriar Manzoor","Associate Professor"));*/
        // Retrieving All Faculty
        //facultyDAOMySQL.retrieve().forEach(System.out::println);

        // Updating By Initial
        //facultyDAOMySQL.update("KMH","Senior Lecturer",new Faculty("KMH","Monirul Hasan Tomal","Assistant Professor"));

        // Deleting Faculty By Initial
        /*boolean kmh = facultyDAOMySQL.delete("KMH");
        System.out.println("Deleted Faculty : "+kmh);*/

        // Deleting All Faculty
        //facultyDAOMySQL.deleteAll();


        // FacultyDAO file implementation
        FacultyDAO facultyDAOFile = new FacultyDAOFileImpl();
        /*facultyDAOFile.create(new Faculty("KMH","Monirul Hasan", FacultyRank.SENIOR_LECTURER.toString()));
        facultyDAOFile.create(new Faculty("SM","Shahriar Manzoor", FacultyRank.ASSOCIATE_PROFESSOR.toString()));
        facultyDAOFile.create(new Faculty("RAJ","Roksana Akter Jolly", FacultyRank.ASSISTANT_PROFESSOR.toString()));
        facultyDAOFile.create(new Faculty("RIK","Rezwan Al Islam Khan", FacultyRank.ASSISTANT_PROFESSOR.toString()));
        facultyDAOFile.create(new Faculty("KIA","Kimia Aksir", FacultyRank.LECTURER.toString()));*/

        // Retrieve all faculties
        //facultyDAOFile.retrieve().forEach(System.out::println);

        // Retrieve By Initials
        //System.out.println(facultyDAOFile.retrieve("KMH"));

        // Deleting all faculty
        //facultyDAOFile.deleteAll();

        // Deleting by Initial
        //facultyDAOFile.delete("KIA");

        // Retrieving by predicate
        //facultyDAOFile.retrieve(faculty -> faculty.getInitial().startsWith("R")).forEach(System.out::println);

        // Updating Faculty
        /*facultyDAOFile.update("SM",FacultyRank.ASSOCIATE_PROFESSOR.toString(),
                new Faculty("SM","Shahriar Manzoor",FacultyRank.PROFESSOR.toString()));*/



        // For Course DAO
        CourseDAO courseDAOMySQL = new CourseDAOMySQLImpl();

        //Adding course to the database
        /*courseDAOMySQL.addCourse(new Course("CSE4048","Advanced Java Lab",1.0));
        courseDAOMySQL.addCourse(new Course("CSE4047","Advanced Java",3.0));
        courseDAOMySQL.addCourse(new Course("CSE2015","Introduction to Java",3.0));
        courseDAOMySQL.addCourse(new Course("CSE2016","Introduction to Java Lab",1.0));
        courseDAOMySQL.addCourse(new Course("CSE4041","Artificial Intelligence",3.0));
        courseDAOMySQL.addCourse(new Course("CSE3031","Operating Systems",3.0));
        courseDAOMySQL.addCourse(new Course("CSE3032","Operating Systems Lab",1.0));*/

        // Retrieving courses with code
        //System.out.println(courseDAOMySQL.retrieve("CSE4048"));

        // Retrieving all courses
        //courseDAOMySQL.getAll().forEach(System.out::println);

        // Removing a course
        //courseDAOMySQL.removeCourse("CSE4041");

        // Updating a course
        //courseDAOMySQL.update("CSE2016", new Course("CSE2016","Introduction to Java Lab",1.5));
        //courseDAOMySQL.update("CSE4041", new Course("CSE4040","Natural Language Processing",3));
        //courseDAOMySQL.update("CSE2016", new Course("CSE4042","Natural Language Processing Lab",1));

        // Removing all course
        //courseDAOMySQL.removeAllCourse();


        CourseDAO courseDAOFile = new CourseDAOFileImpl();
        /*courseDAOFile.addCourse(new Course("CSE4047","Advanced Java",3.0));
        courseDAOFile.addCourse(new Course("CSE4048","Advanced Java Lab",1.0));
        courseDAOFile.addCourse(new Course("CSE2031","Advanced Algorithm",3.0));
        courseDAOFile.addCourse(new Course("CSE2032","Advanced Algorithm Lab",1.0));
        courseDAOFile.addCourse(new Course("CSE3015","Operating System",3.0));
        courseDAOFile.addCourse(new Course("CSE3016","Operating System Lab",1.0));
        courseDAOFile.addCourse(new Course("CSE4041","Artificial Intelligence",3.0));
        courseDAOFile.addCourse(new Course("CSE4041","Artificial Intelligence",3.0));*/

        // Retrieving all courses
        //courseDAOFile.getAll().forEach(System.out::println);

        // Retrieving courses with course code
        //System.out.println(courseDAOFile.retrieve("CSE3015"));

        // Deleting all courses
        //courseDAOFile.removeAllCourse();

        // Remove course with course code
        //courseDAOFile.removeCourse("CSE4047");

        // Updating a course
        //courseDAOFile.update("CSE3015",new Course("CSE3018","Natural Language Processing",3.0));



        // For Section DAO
        SectionDAO sectionDAOMySQL = new SectionDAOMySQLImpl();
        // Adding courses
        /*sectionDAOMySQL.openSection(new Section(10,1,30,
                "AR1001","CSE4047","KMH"));
        sectionDAOMySQL.openSection(new Section(10,2,30,
                "AR1002","CSE4047","KMH"));
        sectionDAOMySQL.openSection(new Section(10,1,25,
                "AR1010","CSE4041","RIK"));
        sectionDAOMySQL.openSection(new Section(11,1,25,
                "B2513","CSE2015","RAJ"));
        sectionDAOMySQL.openSection(new Section(11,1,30,
                "B2512","CSE2016","RAJ"));
        sectionDAOMySQL.openSection(new Section(11,2,30,
                "AR1103","CSE2015","RIK"));*/


        // Retrieving section
        //System.out.println(sectionDAOMySQL.retrieveSection(3));

        // Modifying Section Information
        //sectionDAOMySQL.modifySection(3,25,"SPL","KMH");

        // Retrieving All info about section
        //sectionDAOMySQL.retrieveAll().forEach(System.out::println);

        // Retrieve using certain condition
        //sectionDAOMySQL.findSection(section -> section.getCourseCode().startsWith("CSE")).forEach(System.out::println);

        // Dropping Section
        //sectionDAOMySQL.dropSection(3);

        // Registration
        RegistrationDAO registrationDAOMySQL = new RegistrationDAOMySQLImpl();
        //registrationDAOMySQL.registerSection(new Registration("2016000000044","CSE4047",1,"KMH"));
        //registrationDAOMySQL.registerSection(new Registration("2016000000044","CSE4041",1,"RIK"));
        //registrationDAOMySQL.registerSection(new Registration("2016000000045","CSE2015",1,"RAJ"));
        /*registrationDAOMySQL.registerSection(new Registration("2016000000045","CSE2015",1,"RAJ"));
        registrationDAOMySQL.registerSection(new Registration("2016000000046","CSE4047",1,"KMH"));
        registrationDAOMySQL.registerSection(new Registration("2016000000046","CSE2015",2,"RIK"));
        registrationDAOMySQL.registerSection(new Registration("2016000000047","CSE2016",1,"RAJ"));*/
        /*registrationDAOMySQL.registerSection(new Registration("2016000000047","CSE4047",1,"RIK"));
        registrationDAOMySQL.registerSection(new Registration("2016000000044","CSE2015",1,"RAJ"));
        registrationDAOMySQL.registerSection(new Registration("2016000000044","CSE2016",1,"RAJ"));*/


        // Get all Registered Students
        //registrationDAOMySQL.getAllRegisteredStudentOfCourse("CSE4047").forEach(System.out::println);

        // Dropping Registered Section
        //registrationDAOMySQL.dropSection(1,"CSE2015");

        // Getting all info
        //registrationDAOMySQL.getAllInfo().forEach(System.out::println);




    }


    public static void main(String[] args) {
        // write your code here
        new Main();
    }
}
