package DAOFileImplementation;

import CustomException.IdValidationException;
import DAOInterface.CourseDAO;
import model.Course;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAOFileImpl implements CourseDAO {

    private List<Course> readFile() {
        String csvFile = "course.csv";
        String line;
        String csvSplitBy = ",";
        List<Course> courseList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            br.readLine(); // This line ignore header for csv
            while ((line = br.readLine()) != null) {
                // use comma as separator
                String[] splitCourse = line.split(csvSplitBy);
                /*String courseCode = line.split(",")[0];
                String courseTitle = line.split(",")[1];
                double courseCredit = Double.parseDouble(line.split(",")[2]);
                Course course = new Course(courseCode,courseTitle,courseCredit);*/
                Course course = new Course(splitCourse[0], splitCourse[1], Double.parseDouble(splitCourse[2]));

                courseList.add(course);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return courseList;
    }

    @Override
    public List<Course> getAll() {
        return readFile();
    }

    @Override
    public Course addCourse(Course course) {
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile("course.csv","rw");
            randomAccessFile.writeBytes("Course Code");
            randomAccessFile.writeBytes(",");
            randomAccessFile.writeBytes("Course Title");
            randomAccessFile.writeBytes(",");
            randomAccessFile.writeBytes("Course Credit");
            randomAccessFile.writeBytes("\n");
            randomAccessFile.seek(randomAccessFile.length());
            if (retrieve(course.getCode()) != null) {
                System.err.println("Course already exists");
            }else {
                String courses = course.getCode() + "," + course.getTitle() + "," + course.getCredit();
                randomAccessFile.writeBytes(courses);
                randomAccessFile.writeBytes("\n");
                System.out.println("Faculty inserted!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return course;
    }

    @Override
    public Course retrieve(String code) {
        List<Course> courseList = new ArrayList<>();
        Course course = null;
        String csvSplitBy = ",";
        String line;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("course.csv"));
            bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null) {
                String[] splitCourse = line.split(csvSplitBy);
                Course courses = new Course(splitCourse[0], splitCourse[1], Double.parseDouble(splitCourse[2]));
                if (code.equals(courses.getCode()))
                    courseList.add(courses);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < courseList.size(); i++) {
            course = courseList.get(0);
        }
        return course;
    }

    @Override
    public boolean removeCourse(String code) {
        List<Course> courseList = readFile();
        for (Course course : courseList) {
            if (code.equals(course.getCode())) {
                courseList.remove(course);
                System.out.println("Course removed successfully");
                break;
            }
        }
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile("course.csv", "rw");
            randomAccessFile.setLength(0); // Deleting previous records
            randomAccessFile.writeBytes("Course Code");
            randomAccessFile.writeBytes(",");
            randomAccessFile.writeBytes("Course Title");
            randomAccessFile.writeBytes(",");
            randomAccessFile.writeBytes("Course Credit");
            randomAccessFile.writeBytes("\n");
            randomAccessFile.seek(randomAccessFile.length());
            // Inserting records except-> that is deleted
            for (Course course : courseList) {
                randomAccessFile.writeBytes(course.getCode() + "," + course.getTitle() + "," + course.getCredit());
                randomAccessFile.writeBytes("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    // TODO
    @Override
    public Course update(String code, Course course) {
        List<Course> courseList = getAll();

        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile("course.csv", "rw");
            randomAccessFile.setLength(0);
            randomAccessFile.writeBytes("Course Code");
            randomAccessFile.writeBytes(",");
            randomAccessFile.writeBytes("Course Title");
            randomAccessFile.writeBytes(",");
            randomAccessFile.writeBytes("Course Credit");
            randomAccessFile.writeBytes("\n");
            randomAccessFile.seek(randomAccessFile.length());
            for (Course value : courseList) {
                if (value.getCode().equals(code)) {
                    value.setCode(course.getCode());
                    value.setTitle(course.getTitle());
                    value.setCredit(course.getCredit());
                }
                String course2 = value.getCode() + "," + value.getTitle() + "," + value.getCredit();

                randomAccessFile.writeBytes(course2 + "\n");
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return retrieve(code);
    }

    @Override
    public boolean removeAllCourse() {
        RandomAccessFile randomAccessFile;
        try {
            randomAccessFile = new RandomAccessFile("course.csv", "rw");
            if (randomAccessFile.readLine() != null)
                randomAccessFile.setLength(0);
            System.out.println("All entries deleted successfully!");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
