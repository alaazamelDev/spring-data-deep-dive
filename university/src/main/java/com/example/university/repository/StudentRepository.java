package com.example.university.repository;

import com.example.university.domain.Student;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {

  List<Student> findByFullTime(boolean fullTime);

  List<Student> findByAge(int age);

  List<Student> findByAttendeeLastName(String lastName);
}
