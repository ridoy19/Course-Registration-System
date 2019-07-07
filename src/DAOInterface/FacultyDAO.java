package DAOInterface;

import model.Faculty;

import java.util.List;
import java.util.function.Predicate;

public interface FacultyDAO {
    Faculty create(Faculty faculty);
    Faculty retrieve(String initial);
    List<Faculty> retrieve();
    List<Faculty> retrieve(Predicate<Faculty> filter);

    Faculty update(String initial, String rank, Faculty faculty);

    boolean delete(String initial);
    boolean deleteAll();

    default int facultyCount() {
        return retrieve().size();
    }
}
