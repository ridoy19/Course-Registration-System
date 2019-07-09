package model;

import java.util.Objects;

public class Registration {
    private int regId;
    private String studentId;
    private String courseCode;
    private int sectionId;
    private String facultyInitial;
    //private String room;

    /*public Registration(int regId, String studentId, int sectionId) {
        this.regId = regId;
        this.studentId = studentId;
        this.sectionId = sectionId;
    }*/

    public Registration(int regId, String studentId, String courseCode, int sectionId, String facultyInitial) {
        this.regId = regId;
        this.studentId = studentId;
        this.courseCode = courseCode;
        this.sectionId = sectionId;
        this.facultyInitial = facultyInitial;
        //this.room = room;
    }

    public Registration(String studentId, String courseCode, int sectionId, String facultyInitial) {
        this.studentId = studentId;
        this.courseCode = courseCode;
        this.sectionId = sectionId;
        this.facultyInitial = facultyInitial;
    }

    public int getRegId() {
        return regId;
    }

    public void setRegId(int regId) {
        this.regId = regId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public String getFacultyInitial() {
        return facultyInitial;
    }

    public void setFacultyInitial(String facultyInitial) {
        this.facultyInitial = facultyInitial;
    }

   /* public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }*/
/*
    @Override
    public String toString() {
        return "Registration{" +
                "regId='" + regId + '\'' +
                ", studentId='" + studentId + '\'' +
                ", courseCode='" + courseCode + '\'' +
                ", sectionId=" + sectionId +
                ", facultyInitial='" + facultyInitial + '\'' +
                ", room='" + room + '\'' +
                '}';
    }*/

    @Override
    public String toString() {
        return "Registration{" +
                "regId=" + regId +
                ", studentId='" + studentId + '\'' +
                ", courseCode='" + courseCode + '\'' +
                ", sectionId=" + sectionId +
                ", facultyInitial='" + facultyInitial + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Registration)) return false;
        Registration that = (Registration) o;
        return getRegId() == that.getRegId() &&
                getSectionId() == that.getSectionId() &&
                Objects.equals(getStudentId(), that.getStudentId()) &&
                Objects.equals(getCourseCode(), that.getCourseCode()) &&
                Objects.equals(getFacultyInitial(), that.getFacultyInitial());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRegId(), getStudentId(), getCourseCode(), getSectionId(), getFacultyInitial());
    }
}
