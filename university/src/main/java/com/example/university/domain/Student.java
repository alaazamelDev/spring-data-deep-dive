package com.example.university.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * JPA Entity representing a student at the University.
 * <p>
 * Created by maryellenbowman
 */
@Entity
@Table(name = "student")
public class Student {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer studentId;

  @Embedded
  private Person attendee;

  @Column(name = "full_time")
  private boolean fullTime;

  @Column(name = "age")
  private Integer age;

  @OneToMany(fetch = FetchType.EAGER,
      cascade = CascadeType.ALL)
  @JoinTable(
      name = "course_student",
      joinColumns = {@JoinColumn(name = "student_id")},
      inverseJoinColumns = {@JoinColumn(name = "id")}
  )
  private List<Course> courses = new ArrayList<>();

  public Student(Person attendee, boolean fullTime, Integer age) {
    this.attendee = attendee;
    this.fullTime = fullTime;
    this.age = age;
    courses = new ArrayList<>();
  }

  protected Student() {
  }

  public Integer getStudentId() {
    return studentId;
  }

  public Person getAttendee() {
    return attendee;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public boolean isFullTime() {
    return fullTime;
  }

  public Integer getAge() {
    return age;
  }

  public List<Course> getCourses() {
    return courses;
  }

  @Override
  public String toString() {
    return "Student{" + "studentId=" + studentId + ", " + attendee + ", fullTime=" + fullTime +
        ", age=" + age + "}\n";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Student student = (Student) o;
    return studentId.equals(student.studentId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(studentId);
  }
}
