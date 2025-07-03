package com.example.university.repo;

import com.example.university.domain.Course;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface CourseRepo extends JpaRepository<Course, Integer>,
    JpaSpecificationExecutor<Course> {

  Optional<Course> findByName(String name);

  List<Course> findByPrerequisites(Course prerequisite);

  List<Course> findByCredits(int credits);

  List<Course> findByDepartmentChairMemberLastName(String chair);
}
