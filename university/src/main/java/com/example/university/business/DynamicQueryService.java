package com.example.university.business;

import com.example.university.domain.Course;
import com.example.university.domain.QCourse;
import com.example.university.repo.CourseQueryDslRepo;
import com.example.university.repo.CourseRepo;
import com.querydsl.core.BooleanBuilder;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class DynamicQueryService {

  private CourseRepo courseRepo;
  private CourseQueryDslRepo queryDslRepo;

  public DynamicQueryService(CourseRepo courseRepo, CourseQueryDslRepo queryDslRepo) {
    this.queryDslRepo = queryDslRepo;
    this.courseRepo = courseRepo;
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

  public List<Course> filterByQueryDslRepo(CourseFilter filter) {
    QCourse qCourse = QCourse.course;
    BooleanBuilder predicate = new BooleanBuilder();
    filter.getInstructor().ifPresent(i -> predicate.and(qCourse.instructor.eq(i)));
    filter.getDepartment().ifPresent(d -> predicate.and(qCourse.department.eq(d)));
    filter.getCredits().ifPresent(c -> predicate.and(qCourse.credits.eq(c)));
    List<Course> courses = new ArrayList<>();
    queryDslRepo.findAll(predicate).forEach(courses::add);
    return courses;
  }
}
