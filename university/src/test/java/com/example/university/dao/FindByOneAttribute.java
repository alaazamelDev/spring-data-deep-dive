package com.example.university.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.university.business.UniversityService;
import com.example.university.domain.Course;
import com.example.university.repository.CourseRepository;
import com.example.university.repository.StudentRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Tests that query by one attribute
 */
@SpringBootTest
public class FindByOneAttribute {

  @Autowired
  private UniversityService universityService;

  @Autowired
  private StudentRepository studentRepository;

  @Autowired
  private CourseRepository courseRepository;

  @Test
  public void findByOneAttribute() {
    // Test Create
    UniversityFactory.fillUniversity(universityService);

    studentRepository.findByFullTime(true).forEach(s -> assertTrue(s.isFullTime()));

    studentRepository.findByAge(20).forEach(student -> assertEquals(20, student.getAge()));

    studentRepository.findByAttendeeLastName("King")
        .forEach(s -> assertEquals("King", s.getAttendee().getLastName()));

    List<Course> allCourses = universityService.findAllCourses();
    Course firstCourse = allCourses.getFirst();

    assertEquals(firstCourse, courseRepository.findByName(firstCourse.getName()).get());

    assertEquals(firstCourse.getDepartment().getChair().getMember().getLastName(),
        courseRepository.findByDepartmentChairMemberLastName(
                firstCourse
                    .getDepartment()
                    .getChair()
                    .getMember()
                    .getLastName()
            )
            .getFirst()
            .getDepartment()
            .getChair()
            .getMember()
            .getLastName()
    );

    Course courseWithPrerequisites = allCourses.stream()
        .filter(x -> !x.getPrerequisites().isEmpty())
        .findFirst()
        .get();

    Course prerequisite = courseWithPrerequisites.getPrerequisites().getFirst();
    assertTrue(
        courseRepository.findByPrerequisites(prerequisite).contains(courseWithPrerequisites));
    courseRepository.findByCredits(3).forEach(x -> assertEquals(3, x.getCredits()));
  }
}
