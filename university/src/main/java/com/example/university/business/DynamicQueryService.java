package com.example.university.business;

import com.example.university.domain.Course;
import com.example.university.repo.CourseRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class DynamicQueryService {

  private CourseRepo courseRepo;

  private EntityManagerFactory emf;

  private EntityManager em;

  public DynamicQueryService(CourseRepo courseRepo, EntityManagerFactory emf) {
    this.courseRepo = courseRepo;
    this.em = emf.createEntityManager();
  }

  public List<Course> filterBySpecification(CourseFilter filter) {

    Specification<Course> spec = (root, cq, cb) -> {
      List<Predicate> predicates = new ArrayList<>();

      // department filter
      filter.getDepartment().ifPresent(department -> {
        predicates.add(cb.equal(root.get("department"), department));
      });

      // credits filter
      filter.getCredits().ifPresent(credits -> {
        predicates.add(cb.equal(root.get("credits"), credits));
      });

      // instructor filter
      filter.getInstructor().ifPresent(instructor -> {
        predicates.add(cb.equal(root.get("instructor"), instructor));
      });

      return cb.and(predicates.toArray(new Predicate[0]));
    };

    return courseRepo.findAll(spec);
  }

}
