package DAOMySQLImplementation;

import DAOInterface.SectionDAO;
import com.company.DBConnectionSingleton;
import model.Section;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SectionDAOMySQLImpl implements SectionDAO {

    private Connection connection;
    private PreparedStatement openSectionStatement;
    private PreparedStatement modifySectionStatement;
    private PreparedStatement retrieveSectionByIdStatement;
    private PreparedStatement retrieveAllSectionStatement;
    private PreparedStatement dropSectionStatement;
    private PreparedStatement dropAllSectionStatement;

    public SectionDAOMySQLImpl() {
        connection = DBConnectionSingleton.getConnection();
        InputStream inputStream = getClass().getResourceAsStream("/resources/section.properties");
        InputStreamReader fileReader = new InputStreamReader(inputStream);
        Properties properties = new Properties();
        try {
            properties.load(fileReader);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            openSectionStatement = connection.prepareStatement(properties.getProperty("open.section"));
            modifySectionStatement = connection.prepareStatement(properties.getProperty("modify.section"));
            retrieveSectionByIdStatement = connection.prepareStatement(properties.getProperty("retrieve.by.id"));
            retrieveAllSectionStatement = connection.prepareStatement(properties.getProperty("retrieve.all"));
            dropSectionStatement = connection.prepareStatement(properties.getProperty("drop.section"));
            dropAllSectionStatement = connection.prepareStatement(properties.getProperty("drop.all.section"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Section openSection(Section section) {
        try {
            openSectionStatement.setInt(1,section.getId());
            openSectionStatement.setInt(2,section.getSemester());
            openSectionStatement.setInt(3,section.getSectionNumber());
            openSectionStatement.setInt(4,section.getSeatLimit());
            openSectionStatement.setString(5,section.getRoom());
            openSectionStatement.setString(6,section.getCourseCode());
            openSectionStatement.setString(7,section.getFacultyInitial());

            openSectionStatement.executeUpdate();
            return retrieveSection(section.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Section retrieveSection(int id) {
        try {
            retrieveSectionByIdStatement.setInt(1,id);
            ResultSet resultSet = retrieveSectionByIdStatement.executeQuery();
            if (resultSet.next()) {
                return  new Section(id,resultSet.getInt("semester"),resultSet.getInt("section_number")
                        ,resultSet.getInt("seat_limit"),resultSet.getString("room")
                        ,resultSet.getString("course_code"),resultSet.getString("faculty_initial"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Section> retrieveAll() {
        List<Section> sectionList = new ArrayList<>();
        try {
            ResultSet resultSet = retrieveAllSectionStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int semester = resultSet.getInt("semester");
                int section = resultSet.getInt("section_number");
                int seat = resultSet.getInt("seat_limit");
                String room = resultSet.getString("room");
                String course_code = resultSet.getString("course_code");
                String faculty_initial = resultSet.getString("faculty_initial");

                Section sec = new Section(id,semester,section,seat,room,course_code,faculty_initial);
                sectionList.add(sec);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sectionList;
    }


    @Override
    public Section modifySection(int id,int seatLimit, String room, String faculty) {
        try {
            modifySectionStatement.setInt(1,seatLimit);
            modifySectionStatement.setString(2,room);
            modifySectionStatement.setString(3,faculty);
            modifySectionStatement.setInt(4,id);
            modifySectionStatement.executeUpdate();

            return retrieveSection(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Section> findSection(Predicate<Section> predicate) {
        return retrieveAll().stream().filter(predicate).collect(Collectors.toList());
    }

    @Override
    public boolean dropSection(int id) {
        try {
            dropSectionStatement.setInt(1,id);
            dropSectionStatement.executeUpdate();
            System.out.println("Section " + id + " deleted successfully");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean dropAllSection() {
        int totalRows = 0;
        try {
            totalRows = dropAllSectionStatement.executeUpdate();
            System.out.println(totalRows + "Deleted successfully :)");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
