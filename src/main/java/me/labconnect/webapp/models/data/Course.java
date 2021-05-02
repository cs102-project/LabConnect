package me.labconnect.webapp.models.data;

import java.util.Objects;

public class Course {
    
    private String course;
    private int section;
    
    public Course(String course, int section) {
        this.course = course;
        this.section = section;
    }
    
    public String getCourse() {
        return course;
    }
    
    public int getSection() {
        return section;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course1 = (Course) o;
        return section == course1.section && Objects.equals(course, course1.course);
    }

    @Override
    public int hashCode() {
        return Objects.hash(course, section);
    }
}
