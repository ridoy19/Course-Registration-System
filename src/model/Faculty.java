package model;

import enumeration.FacultyRank;

import java.util.Objects;

public class Faculty {
    private final FacultyRank rankType;
    private String initial;
    private String name;
    private String rank;

    public Faculty(String initial, String name, String rank) {
        this.initial = initial;
        this.name = name;
        this.rank = rank;
        rankType = null;
    }

    public Faculty(String initial, String name, FacultyRank rankType) {
        this.initial = initial;
        this.name = name;
        this.rankType = rankType;
    }

    public String getInitial() {
        return initial;
    }

    public void setInitial(String initial) {
        this.initial = initial;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "Faculty{" +
                "initial='" + initial + '\'' +
                ", name='" + name + '\'' +
                ", rank='" + rank + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Faculty)) return false;
        Faculty faculty = (Faculty) o;
        return Objects.equals(getInitial(), faculty.getInitial()) &&
                Objects.equals(getName(), faculty.getName()) &&
                Objects.equals(getRank(), faculty.getRank());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getInitial(), getName(), getRank());
    }
}
