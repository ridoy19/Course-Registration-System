package DAOFileImplementation;

import DAOInterface.FacultyDAO;
import model.Faculty;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FacultyDAOFileImpl implements FacultyDAO {

    public List<Faculty> readFile() {
        List<Faculty> facultyList = new ArrayList<>();
        String file = "faculty.csv";
        String line;
        String csvSplitBy = ",";
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
            randomAccessFile.readLine(); // Ignores CSV header column

            while ((line = randomAccessFile.readLine()) != null) {
                String[] splitFaculty = line.split(csvSplitBy);
                Faculty faculty = new Faculty(splitFaculty[0], splitFaculty[1], splitFaculty[2]);
                facultyList.add(faculty);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return facultyList;
    }

    @Override
    public Faculty create(Faculty faculty) {
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile("faculty.csv", "rw");
            randomAccessFile.writeBytes("Initial");
            randomAccessFile.writeBytes(",");
            randomAccessFile.writeBytes("Name");
            randomAccessFile.writeBytes(",");
            randomAccessFile.writeBytes("Rank");
            randomAccessFile.writeBytes("\n");
            randomAccessFile.seek(randomAccessFile.length());

            if (retrieve(faculty.getInitial()) != null) {
                System.err.println("There is already a faculty with the same initial! : " + faculty.getInitial());
            } else {
                randomAccessFile.writeBytes(faculty.getInitial() + "," + faculty.getName() + "," + faculty.getRank());
                randomAccessFile.writeBytes("\n");
                System.out.println("Faculty inserted");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return faculty;
    }

    @Override
    public Faculty retrieve(String initial) {
        Faculty faculty = null;
        List<Faculty> facultyList = new ArrayList<>();
        for (Faculty fac : retrieve()) {
            if (fac.getInitial().equals(initial)) {
                facultyList.add(fac);
            }
        }
        for (Faculty value : facultyList) {
            faculty = value;
        }
        return faculty;
    }

    @Override
    public List<Faculty> retrieve() {
        return readFile();
    }

    @Override
    public List<Faculty> retrieve(Predicate<Faculty> filter) {
        return retrieve().stream().filter(filter).collect(Collectors.toList());
    }

    @Override
    public Faculty update(String initial, String rank, Faculty faculty) {
        List<Faculty> facultyList = retrieve();
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile("faculty.csv","rw");
            randomAccessFile.setLength(0);
            randomAccessFile.writeBytes("Initial");
            randomAccessFile.writeBytes(",");
            randomAccessFile.writeBytes("Name");
            randomAccessFile.writeBytes(",");
            randomAccessFile.writeBytes("Rank");
            randomAccessFile.writeBytes("\n");
            randomAccessFile.seek(randomAccessFile.length());

            for (Faculty faculty1 : facultyList) {
                if (faculty1.getInitial().equals(initial)) {
                    faculty1.setInitial(faculty.getInitial());
                    faculty1.setName(faculty.getName());
                    faculty1.setRank(faculty.getRank());
                }

                String fac = faculty1.getInitial() + "," + faculty1.getName() + "," + faculty1.getRank();
                randomAccessFile.writeBytes(fac + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return retrieve(faculty.getInitial());
    }

    @Override
    public boolean delete(String initial) {
        List<Faculty> facultyList = retrieve();
        for (Faculty fac : facultyList) {
            if (fac.getInitial().equals(initial)) {
                facultyList.remove(fac);
                System.out.println(initial + " Deleted successfully");
                break;
            }
        }

        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile("faculty.csv","rw");
            randomAccessFile.setLength(0);
            randomAccessFile.writeBytes("Initial");
            randomAccessFile.writeBytes(",");
            randomAccessFile.writeBytes("Name");
            randomAccessFile.writeBytes(",");
            randomAccessFile.writeBytes("Rank");
            randomAccessFile.writeBytes("\n");
            randomAccessFile.seek(randomAccessFile.length());
            for (Faculty faculty : facultyList) {
                randomAccessFile.writeBytes(faculty.getInitial() + "," + faculty.getName() + "," + faculty.getRank());
                randomAccessFile.writeBytes("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteAll() {
        try (RandomAccessFile randomAccessFile = new RandomAccessFile("faculty.csv", "rw")) {
            if (randomAccessFile.readLine() != null) {
                randomAccessFile.setLength(0);
                System.out.println("All records deleted successfully!");
            } else
                System.err.println("File is empty! Nothing to delete");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
