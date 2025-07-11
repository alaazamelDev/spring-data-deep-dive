package com.example.university.business;

import com.example.university.domain.Department;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentFilter {

  @Builder.Default
  private Optional<String> name = Optional.empty();

  public boolean meetsCriteria(Department department) {
    return (name.map(n -> department.getName().equals(n))).orElse(true);
  }
}
