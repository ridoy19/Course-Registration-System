package DAOMySQLImplementation;

import DAOInterface.FacultyDAO;
import com.company.DBConnectionSingleton;
import model.Faculty;

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

public class FacultyDAOMySQLImpl implements FacultyDAO {

    private Connection connection;
    private PreparedStatement preparedCreateStatement;
    private PreparedStatement preparedRetrieveByInitialStatement;
    private PreparedStatement preparedRetrieveAllStatement;
    private PreparedStatement preparedUpdateByInitialStatement;
    private PreparedStatement preparedDeleteByInitialStatement;
    private PreparedStatement preparedDeleteAllStatement;

    public FacultyDAOMySQLImpl() {
        try {
            connection = DBConnectionSingleton.getConnection();

            InputStream inputStream = getClass().getResourceAsStream("/resources/faculty.properties");
            //System.out.println("InputStream " + inputStream);
            assert inputStream != null;
            InputStreamReader fileReader = new InputStreamReader(inputStream);
            //System.out.println("InputStreamReader " + fileReader);
            Properties properties = new Properties();
            properties.load(fileReader);
            //System.out.printf("INSERT [%s]\n", properties.getProperty("insert.faculty"));

            preparedCreateStatement = connection.prepareStatement(properties.getProperty("insert.faculty"));
            preparedRetrieveByInitialStatement = connection.prepareStatement(properties.getProperty("retrieve.faculty.by.initial"));
            preparedRetrieveAllStatement = connection.prepareStatement(properties.getProperty("retrieve.all"));
            preparedUpdateByInitialStatement = connection.prepareStatement(properties.getProperty("update.by.initial"));
            preparedDeleteByInitialStatement = connection.prepareStatement(properties.getProperty("delete.by.initial"));
            preparedDeleteAllStatement = connection.prepareStatement(properties.getProperty("delete.all"));
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Faculty create(Faculty faculty) {
        try {
            preparedCreateStatement.setString(1,faculty.getInitial());
            preparedCreateStatement.setString(2,faculty.getName());
            preparedCreateStatement.setString(3,faculty.getRank());
            preparedCreateStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return retrieve(faculty.getInitial());
    }

    @Override
    public Faculty retrieve(String initial) {
        try {
            preparedRetrieveByInitialStatement.setString(1,initial);
            ResultSet resultSet = preparedRetrieveByInitialStatement.executeQuery();
            if (resultSet.next()) {
                return new Faculty(initial,resultSet.getString("name"),resultSet.getString("rank"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Faculty> retrieve() {
        List<Faculty> facultyList = new ArrayList<>();
        try {
            ResultSet resultSet = preparedRetrieveAllStatement.executeQuery();
            while (resultSet.next()) {
                String initial = resultSet.getString("initial");
                String name = resultSet.getString("name");
                String rank = resultSet.getString("rank");

                Faculty faculty = new Faculty(initial, name, rank);
                facultyList.add(faculty);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return facultyList;
    }

    @Override
    public List<Faculty> retrieve(Predicate<Faculty> filter) {
        return retrieve().stream().filter(filter).collect(Collectors.toList());
    }

    @Override
    public Faculty update(String initial, String rank, Faculty faculty) {
        try {
            preparedUpdateByInitialStatement.setString(1,faculty.getName());
            preparedUpdateByInitialStatement.setString(2,faculty.getRank());
            preparedUpdateByInitialStatement.setString(3,initial);
            preparedUpdateByInitialStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return retrieve(initial);
    }

    @Override
    public boolean delete(String initial) {
        try {
            preparedDeleteByInitialStatement.setString(1,initial);
            preparedDeleteByInitialStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteAll() {
        try {
            preparedDeleteAllStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
