package DAOInterface;

import model.Registration;
import model.Section;

import java.util.List;

public interface RegistrationDAO {
    Registration registerSection(Registration registration);
    boolean dropSection(int sectionId, String courseCode);
    Registration getAllInfo(int regId);
    List<Registration> getAllInfo();
    List<Registration> getAllRegisteredStudentOfCourse(String courseCode);

    //int updateSeatCount(String courseCode);
    //int updateSeatCount(Registration registration);

}
