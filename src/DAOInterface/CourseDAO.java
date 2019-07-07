package DAOInterface;

import model.Course;

import java.util.List;
import java.util.function.Predicate;

public interface CourseDAO {
    List<Course> getAll();
    Course addCourse(Course course);
    Course retrieve(String code);
    boolean removeCourse(String code);
    Course update(String code, Course course);
    boolean removeAllCourse();
}
