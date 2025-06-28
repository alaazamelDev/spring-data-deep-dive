package com.example.university.repository;

import com.example.university.domain.Course;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Integer> {

  Optional<Course> findByName(String name);

  List<Course> findByDepartmentChairMemberLastName(String lastName);

  Collection<Course> findByPrerequisites(Course prerequisite);

  List<Course> findByCredits(int credits);
}
