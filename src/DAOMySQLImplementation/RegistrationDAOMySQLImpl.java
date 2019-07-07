package DAOMySQLImplementation;

import DAOInterface.RegistrationDAO;
import com.company.DBConnectionSingleton;
import model.Registration;
import model.Section;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class RegistrationDAOMySQLImpl implements RegistrationDAO {
    private Connection connection;
    private PreparedStatement preparedRegisterStatement;
    private PreparedStatement preparedDropSectionStatement;
    private PreparedStatement preparedGetAllRegisteredStudentOfCourseStatement;
    private PreparedStatement preparedGetAllInfoStatement;
    private PreparedStatement retrieveRegInfoByRegIdStatement;
    private PreparedStatement updateSeatCountStatement;
    private PreparedStatement isRegisteredStatement;

    private List<Registration> registrationList;

    public RegistrationDAOMySQLImpl() {
        connection = DBConnectionSingleton.getConnection();
        InputStream inputStream = getClass().getResourceAsStream("/resources/registration.properties");
        assert inputStream != null;
        InputStreamReader fileReader = new InputStreamReader(inputStream);
        Properties properties = new Properties();
        try {
            properties.load(fileReader);

            preparedRegisterStatement =
                    connection.prepareStatement(properties.getProperty("register.section"));
            preparedDropSectionStatement =
                    connection.prepareStatement(properties.getProperty("drop.section.id.code"));
            preparedGetAllRegisteredStudentOfCourseStatement =
                    connection.prepareStatement(properties.getProperty("get.all.registered.student.of.course"));
            preparedGetAllInfoStatement = connection.prepareStatement(properties.getProperty("get.all.info"));
            retrieveRegInfoByRegIdStatement = connection.prepareStatement(properties.getProperty("get.reg.info.reg.id"));
            updateSeatCountStatement = connection.prepareStatement(properties.getProperty("update.seat.count"));
            isRegisteredStatement = connection.prepareStatement(properties.getProperty("is.registered"));

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }



    @Override
    public Registration registerSection(Registration registration) {

        try {

            /*
            * Give {@Error} java.sql.SQLException: Parameter index out of range (1 > number of parameters, which is 0).
            *
            * Need Help! Why it's not working properly
            *
            * */
            /*isRegisteredStatement.setString(1, registration.getStudentId());
            isRegisteredStatement.setString(2, registration.getCourseCode());
            isRegisteredStatement.setInt(3, registration.getSectionId());
            isRegisteredStatement.setString(4, registration.getFacultyInitial());
            ResultSet resultSet = isRegisteredStatement.executeQuery();
            boolean recordAdded = false;

            /*while(!rs.next()){
                /// Do your insertion of new records
                preparedRegisterStatement.setInt(1,registration.getRegId());
                preparedRegisterStatement.setString(2,registration.getStudentId());
                preparedRegisterStatement.setString(3,registration.getCourseCode());
                preparedRegisterStatement.setInt(4,registration.getSectionId());
                preparedRegisterStatement.setString(5,registration.getFacultyInitial());

                updateSeatCount(registration.getCourseCode(),registration.getSectionId());
                preparedRegisterStatement.executeUpdate();
                recordAdded = true;
            }
            if( recordAdded ){
                System.out.println("Record Done");
            }else{
                System.out.println("Already registered for the same course and section");
            }
            */


            /*
            * No {@Error} perfectly working
            *
            * */
            final String queryCheck = "SELECT * from registration WHERE student_id = ? AND course_code = ? AND section_id = ? ";
            final PreparedStatement ps = connection.prepareStatement(queryCheck);
            ps.setString(1, registration.getStudentId());
            ps.setString(2, registration.getCourseCode());
            ps.setInt(3, registration.getSectionId());
            final ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                System.err.println("Already registered");
            }else {
                preparedRegisterStatement.setInt(1,registration.getRegId());
                preparedRegisterStatement.setString(2,registration.getStudentId());
                preparedRegisterStatement.setString(3,registration.getCourseCode());
                preparedRegisterStatement.setInt(4,registration.getSectionId());
                preparedRegisterStatement.setString(5,registration.getFacultyInitial());

                updateSeatCount(registration.getCourseCode(),registration.getSectionId());
                preparedRegisterStatement.executeUpdate();
                System.out.format("Registration for {Course : [%s] , Section : [%s]} successful."
                        ,registration.getCourseCode(),registration.getSectionId());
            }


            return getAllInfo(registration.getRegId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void updateSeatCount(String courseCode, int sectionId) {
        try {
            updateSeatCountStatement.setString(1,courseCode);
            updateSeatCountStatement.setInt(2,sectionId);
            updateSeatCountStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean dropSection(int sectionId, String courseCode) {
        try {
            preparedDropSectionStatement.setInt(1,sectionId);
            preparedDropSectionStatement.setString(2,courseCode);
            preparedDropSectionStatement.executeUpdate();
            System.out.format("Section [%d] | Course [%s] dropped successfully",sectionId, courseCode);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Registration getAllInfo(int regId) {
        try {
            retrieveRegInfoByRegIdStatement.setInt(1,regId);
            ResultSet resultSet = retrieveRegInfoByRegIdStatement.executeQuery();
            if (resultSet.next()) {
                return  new Registration(regId,resultSet.getString("student_id"),resultSet.getString("course_code")
                ,resultSet.getInt("section_id"),resultSet.getString("faculty_initial"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public List<Registration> getAllInfo() {
        registrationList = new ArrayList<>();
        try {
            ResultSet resultSet = preparedGetAllInfoStatement.executeQuery();
            while (resultSet.next()) {
                int reg_id = resultSet.getInt("reg_id");
                String student_id = resultSet.getString("student_id");
                String course_code = resultSet.getString("course_code");
                int section_id = resultSet.getInt("section_id");
                String faculty_initial = resultSet.getString("faculty_initial");
                //String room = resultSet.getString("room");

                Registration registration = new Registration(reg_id,student_id,course_code,section_id,faculty_initial);
                registrationList.add(registration);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return registrationList;
    }

    @Override
    public List<Registration> getAllRegisteredStudentOfCourse(String courseCode) {
        List<Registration> registeredStudent = new ArrayList<>();
        try {
            preparedGetAllRegisteredStudentOfCourseStatement.setString(1,courseCode);
            ResultSet resultSet = preparedGetAllRegisteredStudentOfCourseStatement.executeQuery();
            while (resultSet.next()) {
                int reg_id = resultSet.getInt("reg_id");
                String student_id = resultSet.getString("student_id");
                String course_code = resultSet.getString("course_code");
                int section_id = resultSet.getInt("section_id");
                String faculty_initial = resultSet.getString("faculty_initial");
                //String room = resultSet.getString("room");

                Registration registration = new Registration(reg_id,student_id,course_code,section_id,faculty_initial);
                registeredStudent.add(registration);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return registeredStudent;
    }

    //@Override
    /*public int updateSeatCount(Registration registration) {
        int i = 0;
        try {
            updateSeatCountStatement.setString(1,registration.getCourseCode());
            updateSeatCountStatement.setString(2,registration.getStudentId());
            i = updateSeatCountStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }*/


}
