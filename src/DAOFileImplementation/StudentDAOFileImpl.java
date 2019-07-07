package DAOFileImplementation;

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
        /*String FILE_HEADER = "id,name";
        try {
            FileWriter fileWriter = new FileWriter("student.csv");

            fileWriter.append(FILE_HEADER);
            fileWriter.append("\n");
            fileWriter.append(student.getId());
            fileWriter.append(",");
            fileWriter.append(student.getName());
            fileWriter.append("\n");

            fileWriter.flush();
            fileWriter.close();

            *//*RandomAccessFile randomAccessFile = new RandomAccessFile("student.csv","rw");
            randomAccessFile.seek(randomAccessFile.length());
            randomAccessFile.writeBytes(FILE_HEADER);
            randomAccessFile.writeBytes(student.getId() + "," + student.getName() + "\n");*//*
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        try {

            RandomAccessFile randomAccessFile = new RandomAccessFile("student.csv","rw");

            randomAccessFile.writeBytes("Id");
            randomAccessFile.writeBytes(",");
            randomAccessFile.writeBytes("Name");
            randomAccessFile.writeBytes("\n");
            randomAccessFile.seek(randomAccessFile.length());
            //String s = student.getId() + "," + student.getName();
            randomAccessFile.writeBytes(student.getId() + "," + student.getName());
            //randomAccessFile.writeBytes(s + "\n");
            randomAccessFile.writeBytes("\n");
            //return retrieve(student.getName());
        }catch (IOException e) {
            e.printStackTrace();
        }
        return student;
    }

    private  List<Student> readFile() {
        String csvFile = "student.csv";
        String line;
        String cvsSplitBy = ",";
        List<Student> studentList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            br.readLine(); // This line ignore header for csv
            while ((line = br.readLine()) != null) {
                // use comma as separator
                String[] country = line.split(cvsSplitBy);

                /*String id = line.split(",")[0];
                String name = line.split(",")[1];*/

                Student student = new Student(country[0],country[1]);
                studentList.add(student);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return studentList;
    }

    @Override
    public Student retrieve(String studentId) {
        List<Student>studentList = new ArrayList<>();
        Student student = null;
        String cvsSplitBy = ",";
        try {
            BufferedReader br = new BufferedReader(new FileReader("student.csv"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] country = line.split(cvsSplitBy);

                Student std = new Student(country[0], country[1]);

                if(studentId.equals(std.getId())) {
                    studentList.add(std);
                }
            }
        } catch (IOException ex) {
           ex.printStackTrace();
        }
        student = studentList.get(0);
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
        return null;
    }

    @Override
    public boolean delete(String studentId) {
        return false;
    }

    @Override
    public boolean deleteAll() {
        RandomAccessFile randomAccessFile = null;
        try {
            randomAccessFile = new RandomAccessFile("student.csv","rw");
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
