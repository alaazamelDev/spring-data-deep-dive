package com.example.university.repo;

import com.example.university.domain.Department;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepo extends JpaRepository<Department, Integer> {

  Optional<Department> findByName(String name);
}
