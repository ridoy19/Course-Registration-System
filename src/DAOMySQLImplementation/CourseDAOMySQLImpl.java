package DAOMySQLImplementation;

import DAOInterface.CourseDAO;
import com.company.DBConnectionSingleton;
import model.Course;

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


public class CourseDAOMySQLImpl implements CourseDAO {
    private Connection connection;
    private PreparedStatement preparedAddCourseStatement;
    private PreparedStatement preparedRetrieveByCodeStatement;
    private PreparedStatement preparedRetrieveAllStatement;
    private PreparedStatement preparedUpdateByCodeStatement;
    private PreparedStatement preparedRemoveAllStatement;
    private PreparedStatement preparedRemoveByCodeStatement;

    private List<Course> courseList;

    public CourseDAOMySQLImpl() {
        try {
            connection = DBConnectionSingleton.getConnection();

            InputStream inputStream = getClass().getResourceAsStream("/resources/course.properties");
            //System.out.println("InputStream " + inputStream);
            assert inputStream != null;
            InputStreamReader fileReader = new InputStreamReader(inputStream);
            //System.out.println("InputStreamReader " + fileReader);
            Properties properties = new Properties();
            properties.load(fileReader);

            preparedAddCourseStatement = connection.prepareStatement(properties.getProperty("insert.course"));
            preparedRetrieveByCodeStatement = connection.prepareStatement(properties.getProperty("retrieve.course.by.code"));
            preparedRetrieveAllStatement = connection.prepareStatement(properties.getProperty("retrieve.all"));
            preparedUpdateByCodeStatement = connection.prepareStatement(properties.getProperty("update.by.code"));
            preparedRemoveAllStatement = connection.prepareStatement(properties.getProperty("delete.all"));
            preparedRemoveByCodeStatement = connection.prepareStatement(properties.getProperty("remove.course.by.code"));
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Course> getAll() {
        courseList = new ArrayList<>();
        try {
            ResultSet resultSet = preparedRetrieveAllStatement.executeQuery();
            while (resultSet.next()) {
                String code = resultSet.getString("code");
                String title = resultSet.getString("title");
                double credit = resultSet.getDouble("credit");

                Course course = new Course(code,title,credit);
                courseList.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courseList;
    }

    @Override
    public Course addCourse(Course course) {
        try {
            preparedAddCourseStatement.setString(1,course.getCode());
            preparedAddCourseStatement.setString(2,course.getTitle());
            preparedAddCourseStatement.setDouble(3,course.getCredit());
            preparedAddCourseStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return retrieve(course.getTitle());
    }

    @Override
    public Course retrieve(String code) {
        try {
            preparedRetrieveByCodeStatement.setString(1,code);
            ResultSet resultSet = preparedRetrieveByCodeStatement.executeQuery();
            if (resultSet.next()) {
                return new Course(code,resultSet.getString("title"),resultSet.getDouble("credit"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean removeCourse(String code) {
        try {
            preparedRemoveByCodeStatement.setString(1,code);
            preparedRemoveByCodeStatement.executeUpdate();
            System.out.println(code + " Deleted Successfully");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Course update(String code, Course course) {
        try {
            preparedUpdateByCodeStatement.setString(1,course.getCode());
            preparedUpdateByCodeStatement.setString(2,course.getTitle());
            preparedUpdateByCodeStatement.setDouble(3,course.getCredit());
            preparedUpdateByCodeStatement.setString(4,code);
            preparedUpdateByCodeStatement.executeUpdate();

            System.out.println(code + " Updated to '"+course+"' Successful");
            return course;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean removeAllCourse() {
        try {
            int resultSet = preparedRemoveAllStatement.executeUpdate();
            System.out.println(resultSet+" Rows Deleted");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
