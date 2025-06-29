package com.example.university.repository;

import com.example.university.domain.Student;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {

  List<Student> findByFullTime(boolean fullTime);

  List<Student> findByAge(int age);

  List<Student> findByAttendeeLastName(String lastName);

  List<Student> findByAttendeeFirstNameAndAttendeeLastName(String firstName, String lastName);

  Optional<Student> findTop1ByOrderByAgeDesc();

  List<Student> findByAgeLessThan(int age);

  List<Student> findByAttendeeLastNameLike(String lastName);

  Optional<Student> findFirstByOrderByAttendeeLastNameAsc();

  List<Student> findTop3ByOrderByAgeDesc();
}
