package com.example.university.repo;

import com.example.university.domain.Staff;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffRepo extends JpaRepository<Staff, Integer> {

  List<Staff> findByMemberLastName(String black);
}
