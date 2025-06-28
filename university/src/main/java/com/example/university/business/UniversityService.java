package com.example.university.business;

import com.example.university.domain.Course;
import com.example.university.domain.Department;
import com.example.university.domain.Person;
import com.example.university.domain.Staff;
import com.example.university.domain.Student;
import com.example.university.repository.CourseRepository;
import com.example.university.repository.DepartmentRepository;
import com.example.university.repository.StaffRepository;
import com.example.university.repository.StudentRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Business Tier class for the University Library
 */
@Service
@RequiredArgsConstructor
public class UniversityService {

  private final DepartmentRepository departmentRepository;
  private final StudentRepository studentRepository;
  private final CourseRepository courseRepository;
  private final StaffRepository staffRepository;


  public Student createStudent(String firstName, String lastName, boolean fullTime, int age) {
    return studentRepository.save(new Student(new Person(firstName, lastName), fullTime, age));
  }

  public Staff createFaculty(String firstName, String lastName) {
    return staffRepository.save(new Staff(new Person(firstName, lastName)));
  }

  public Department createDepartment(String deptname, Staff deptChair) {
    return departmentRepository.save(new Department(deptname, deptChair));
  }

  public Course createCourse(String name, int credits, Staff professor, Department department) {
    return courseRepository.save(new Course(name, credits, professor, department));
  }

  public Course createCourse(
      String name,
      int credits,
      Staff professor,
      Department department,
      Course... prereqs
  ) {
    Course c = new Course(name, credits, professor, department);
    for (Course p : prereqs) {
      c.addPrerequisite(p);
    }
    return courseRepository.save(c);
  }

  public List<Course> findAllCourses() {
    return courseRepository.findAll();
  }

  public List<Staff> findAllStaff() {
    return staffRepository.findAll();
  }

  public List<Department> findAllDepartments() {
    return departmentRepository.findAll();
  }

  public List<Student> findAllStudents() {
    return studentRepository.findAll();
  }

  public void deleteAll() {
    try {
      studentRepository.deleteAll();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
