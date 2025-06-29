package com.example.university.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.university.business.UniversityService;
import com.example.university.domain.Student;
import com.example.university.repository.StudentRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Test queries that involve clauses and expressions
 */
@SpringBootTest
public class FindByClausesAndExpressions {

  @Autowired
  private UniversityService universityService;

  @Autowired
  private StudentRepository studentRepository;

  @Test
  public void findByClausesAndExpressions() {

    // Test Create
    UniversityFactory.fillUniversity(universityService);
    List<Student> students = universityService.findAllStudents();
    Student firstStudent = students.getFirst();

    assertEquals(22, (int) studentRepository.findTop1ByOrderByAgeDesc().get().getAge());

    assertEquals(
        firstStudent,
        studentRepository.findByAttendeeFirstNameAndAttendeeLastName(
            firstStudent.getAttendee().getFirstName(),
            firstStudent.getAttendee().getLastName()
        ).getFirst()
    );

    studentRepository.findByAgeLessThan(20).forEach(s -> assertTrue(s.getAge() < 20));

    studentRepository.findByAttendeeLastNameLike("%o%")
        .forEach(s -> assertTrue(s.getAttendee().getLastName().contains("o")));

    assertEquals("Doe",
        studentRepository.findFirstByOrderByAttendeeLastNameAsc()
            .get()
            .getAttendee()
            .getLastName()
    );

    List<Student> students3 = studentRepository.findTop3ByOrderByAgeDesc();
    assertEquals(3, students3.size());
    students3.forEach(s -> assertTrue(s.getAge() != 18));
  }
}
