package model;

import java.util.Objects;

public class Section {
    private int id;
    private int semester;
    private int sectionNumber;
    private int seatLimit;
    private String room;
    private String courseCode;
    private String facultyInitial;


    public Section(int semester, int sectionNumber, int seatLimit, String room, String courseCode, String facultyInitial) {
        this.semester = semester;
        this.sectionNumber = sectionNumber;
        this.seatLimit = seatLimit;
        this.room = room;
        this.courseCode = courseCode;
        this.facultyInitial = facultyInitial;
    }

    public Section(int id, int semester, int sectionNumber, int seatLimit,
                   String room, String courseCode, String facultyInitial) {
        this.id = id;
        this.semester = semester;
        this.sectionNumber = sectionNumber;
        this.seatLimit = seatLimit;
        this.room = room;
        this.courseCode = courseCode;
        this.facultyInitial = facultyInitial;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public int getSectionNumber() {
        return sectionNumber;
    }

    public void setSectionNumber(int sectionNumber) {
        this.sectionNumber = sectionNumber;
    }

    public int getSeatLimit() {
        return seatLimit;
    }

    public void setSeatLimit(int seatLimit) {
        this.seatLimit = seatLimit;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getFacultyInitial() {
        return facultyInitial;
    }

    public void setFacultyInitial(String facultyInitial) {
        this.facultyInitial = facultyInitial;
    }

    @Override
    public String toString() {
        return "Section{" +
                "id='" + id + '\'' +
                ", semester=" + semester +
                ", sectionNumber=" + sectionNumber +
                ", seatLimit=" + seatLimit +
                ", room='" + room + '\'' +
                ", courseCode='" + courseCode + '\'' +
                ", facultyInitial='" + facultyInitial + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Section)) return false;
        Section section = (Section) o;
        return getId() == section.getId() &&
                getSemester() == section.getSemester() &&
                getSectionNumber() == section.getSectionNumber() &&
                getSeatLimit() == section.getSeatLimit() &&
                Objects.equals(getRoom(), section.getRoom()) &&
                Objects.equals(getCourseCode(), section.getCourseCode()) &&
                Objects.equals(getFacultyInitial(), section.getFacultyInitial());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getSemester(), getSectionNumber(), getSeatLimit(), getRoom(), getCourseCode(), getFacultyInitial());
    }
}
