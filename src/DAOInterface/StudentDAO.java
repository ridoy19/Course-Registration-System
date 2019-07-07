package DAOInterface;

import model.Student;

import java.util.List;
import java.util.function.Predicate;

public interface StudentDAO {
    Student create(Student student);

    Student retrieve(String studentId);
    List<Student> retrieve();
    List<Student> retrieve(Predicate<Student> filter);

    Student update(String studentId, Student student);

    boolean delete(String studentId);
    boolean deleteAll();

    default int studentCount() {
        return retrieve().size();
    }
}
