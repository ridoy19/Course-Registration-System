package DAOFileImplementation;

import CustomException.IdValidationException;
import DAOInterface.StudentDAO;

import com.sun.deploy.util.SyncFileAccess;
import model.Student;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class StudentDAOFileImpl implements StudentDAO {


    @Override
    public Student create(Student student) {
        try {

            RandomAccessFile randomAccessFile = new RandomAccessFile("student.csv", "rw");

            randomAccessFile.writeBytes("Id");
            randomAccessFile.writeBytes(",");
            randomAccessFile.writeBytes("Name");
            randomAccessFile.writeBytes("\n");
            randomAccessFile.seek(randomAccessFile.length());
            if (student.getId().length() > 13) {
                throw new IdValidationException("Id must be less than 13 characters.");
            }

            if (retrieve(student.getId()) != null) {
                System.err.println("Id already exists!");
            } else {
                //String s = student.getId() + "," + student.getName();
                randomAccessFile.writeBytes(student.getId() + "," + student.getName());
                //randomAccessFile.writeBytes(s + "\n");
                randomAccessFile.writeBytes("\n");
                //return retrieve(student.getName());
                System.out.println("Student inserted :)");
            }
        } catch (IOException | IdValidationException e) {
            e.printStackTrace();
        }
        return student;
    }

    private List<Student> readFile() {
        String csvFile = "student.csv";
        String line;
        String cvsSplitBy = ",";
        List<Student> studentList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            br.readLine(); // This line ignore header for csv
            while ((line = br.readLine()) != null) {
                // use comma as separator
                String[] splitStd = line.split(cvsSplitBy);
                /*String id = line.split(",")[0];
                String name = line.split(",")[1];*/
                Student student = new Student(splitStd[0], splitStd[1]);

                studentList.add(student);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return studentList;
    }

    @Override
    public Student retrieve(String studentId) {
        List<Student> studentList = new ArrayList<>();
        Student student = null;
        String cvsSplitBy = ",";
        try {
            BufferedReader br = new BufferedReader(new FileReader("student.csv"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] splitStd = line.split(cvsSplitBy);

                Student std = new Student(splitStd[0], splitStd[1]);

                if (studentId.equals(std.getId())) {
                    studentList.add(std);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        for (int i = 0; i < studentList.size(); i++) {
            student = studentList.get(0);
        }
        return student;
    }

    @Override
    public List<Student> retrieve() {
        return readFile();
    }

    @Override
    public List<Student> retrieve(Predicate<Student> filter) {
        return retrieve().stream().filter(filter).collect(Collectors.toList());
    }

    @Override
    public Student update(String studentId, Student student) {
        List<Student> studentList = retrieve();
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile("student.csv", "rw");
            randomAccessFile.setLength(0);
            randomAccessFile.writeBytes("Id");
            randomAccessFile.writeBytes(",");
            randomAccessFile.writeBytes("Name");
            randomAccessFile.writeBytes("\n");
            randomAccessFile.seek(randomAccessFile.length());
            for (Student value : studentList) {
                if (value.getId().equals(studentId)) {
                    value.setName(student.getName());
                }
                String std = value.getId() + "," + value.getName();
                randomAccessFile.writeBytes(std + "\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return retrieve(studentId);
    }

    @Override
    public boolean delete(String studentId) {
        List<Student> studentList = retrieve();
        for (Student s : studentList){
            if (s.getId().equals(studentId)){
                studentList.remove(s);
                System.out.println(studentId + " Successfully deleted :)");
                break;
            }
        }

        try(RandomAccessFile randomAccessFile = new RandomAccessFile("student.csv", "rw")){
            randomAccessFile.setLength(0);// clearing the file
            randomAccessFile.writeBytes("Id");
            randomAccessFile.writeBytes(",");
            randomAccessFile.writeBytes("Name");
            randomAccessFile.writeBytes("\n");
            randomAccessFile.seek(randomAccessFile.length());
            for (Student s : studentList){
                randomAccessFile.writeBytes(s.getId() + "," + s.getName());
                randomAccessFile.writeBytes("\n");
            }
        } catch (IOException e){
            System.err.println("File could not be accessed!");
        }
        return false;
    }

    @Override
    public boolean deleteAll() {
        RandomAccessFile randomAccessFile;
        try {
            randomAccessFile = new RandomAccessFile("student.csv", "rw");
            if (randomAccessFile.readLine() != null)
                randomAccessFile.setLength(0);
            System.out.println("All records deleted successfully");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
