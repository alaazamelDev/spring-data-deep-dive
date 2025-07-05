package com.example.university.business;

import com.example.university.domain.Course;
import com.example.university.domain.QCourse;
import com.example.university.repo.CourseQueryDslRepo;
import com.example.university.repo.CourseRepo;
import com.querydsl.core.BooleanBuilder;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;


@Service
public class DynamicQueryService {

  private CourseRepo courseRepo;

  private CourseQueryDslRepo queryDslRepo;

  public DynamicQueryService(CourseRepo courseRepo, CourseQueryDslRepo queryDslRepo) {
    this.courseRepo = courseRepo;
    this.queryDslRepo = queryDslRepo;
  }


  public List<Course> filterBySpecification(CourseFilter filter) {
    return courseRepo.findAll((root, query, criteriaBuilder) -> {
      List<Predicate> predicates = new ArrayList<>();
      filter.getDepartment()
          .ifPresent(d -> predicates.add(criteriaBuilder.equal(root.get("department"), d)));
      filter.getCredits()
          .ifPresent(c -> predicates.add(criteriaBuilder.equal(root.get("credits"), c)));
      filter.getInstructor()
          .ifPresent(i -> predicates.add(criteriaBuilder.equal(root.get("instructor"), i)));
      return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    });
  }

  public List<Course> filterByQueryDsl(CourseFilter filter) {
    QCourse qCourse = com.example.university.domain.QCourse.course;
    BooleanBuilder pred = new BooleanBuilder();
    filter.getDepartment().ifPresent(d -> pred.and(qCourse.department.eq(d)));
    filter.getCredits().ifPresent(c -> pred.and(qCourse.credits.eq(c)));
    filter.getInstructor().ifPresent(i -> pred.and(qCourse.instructor.eq(i)));

    List<Course> courses = new ArrayList<>();
    queryDslRepo.findAll(pred).forEach(courses::add);
    return courses;
  }

  public List<Course> filterByExample(CourseFilter filter) {
    Course course = Course.builder()
        .credits(filter.getCredits().orElse(null))
        .instructor(filter.getInstructor().orElse(null))
        .department(filter.getDepartment().orElse(null))
        .build();
    return courseRepo.findAll(Example.of(course));
  }
}
