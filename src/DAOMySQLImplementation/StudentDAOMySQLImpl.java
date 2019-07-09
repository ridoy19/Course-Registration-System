package DAOMySQLImplementation;

import CustomException.IdValidationException;
import DAOInterface.StudentDAO;
import com.company.DBConnectionSingleton;
import model.Student;

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
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class StudentDAOMySQLImpl implements StudentDAO {

    private Connection connection;
    private PreparedStatement preparedCreateStatement;
    private PreparedStatement preparedRetrieveByIdStatement;
    private PreparedStatement preparedRetrieveAllStatement;
    private PreparedStatement preparedUpdateByIdStatement;
    private PreparedStatement preparedDeleteByIdStatement;
    private PreparedStatement preparedDeleteAllStatement;
    private List<Student> studentList;


    public StudentDAOMySQLImpl() {
        try {
            connection = DBConnectionSingleton.getConnection();
            studentList = new ArrayList<>();
            InputStream inputStream = getClass().getResourceAsStream("/resources/query.properties");
            //System.out.println("InputStream of " + inputStream);
            assert inputStream != null;
            InputStreamReader fileReader = new InputStreamReader(inputStream);
            //System.out.println("InputStreamReader " + fileReader);
            Properties properties = new Properties();
            properties.load(fileReader);
            //System.out.printf("INSERT [%s]\n", properties.getProperty("insert.student"));

            preparedCreateStatement = connection.prepareStatement(properties.getProperty("insert.student"));
            preparedRetrieveByIdStatement = connection.prepareStatement(properties.getProperty("retrieve.by.id"));
            preparedRetrieveAllStatement = connection.prepareStatement(properties.getProperty("retrieve.all"));
            preparedUpdateByIdStatement = connection.prepareStatement(properties.getProperty("update.by.id"));
            preparedDeleteByIdStatement = connection.prepareStatement(properties.getProperty("delete.by.id"));
            preparedDeleteAllStatement = connection.prepareStatement(properties.getProperty("delete.all"));
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Student create(Student student) {
        try {

            if (student.getId().length() > 13) {
                throw new IdValidationException("Id must be less than 13 characters");
            }
            preparedCreateStatement.setString(1,student.getId());
            preparedCreateStatement.setString(2,student.getName());
            preparedCreateStatement.executeUpdate();
        } catch (SQLException | IdValidationException e) {
            e.printStackTrace();
        }

        return retrieve(student.getId());
    }

    @Override
    public Student retrieve(String studentId) {
        try {
            preparedRetrieveByIdStatement.setString(1,studentId);
            ResultSet resultSet = preparedRetrieveByIdStatement.executeQuery();
            if (resultSet.next()) {
                return new Student(studentId,resultSet.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Student> retrieve() {

        try {
            ResultSet resultSet = preparedRetrieveAllStatement.executeQuery();
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");

                Student student = new Student(id, name);
                studentList.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentList;
    }

    @Override
    public List<Student> retrieve(Predicate<Student> filter) {
        return retrieve().stream().filter(filter).collect(Collectors.toList());
    }

    @Override
    public Student update(String studentId, Student student) {
        try {
            preparedUpdateByIdStatement.setString(1,student.getName());
            preparedUpdateByIdStatement.setString(2,studentId);
            preparedUpdateByIdStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return retrieve(studentId);
    }

    @Override
    public boolean delete(String studentId) {
        try {
            preparedDeleteByIdStatement.setString(1,studentId);
            preparedDeleteByIdStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteAll() {
        try {
            int count = preparedDeleteAllStatement.executeUpdate();
            System.out.println(count + "Rows deleted");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
