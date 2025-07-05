package com.example.university.dao;

import static com.example.university.business.CourseFilter.filterBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.university.business.CourseFilter;
import com.example.university.business.DynamicQueryService;
import com.example.university.business.UniversityService;
import com.example.university.domain.Department;
import com.example.university.domain.Course;
import com.example.university.domain.Staff;
import com.example.university.repo.DepartmentRepo;
import com.example.university.repo.StaffRepo;

/**
 * Test Criteria-based queries
 */
@SpringBootTest
public class CriteriaQueryTest {

    @Autowired
    private DynamicQueryService queryService;
    @Autowired
    private UniversityService universityService;
    @Autowired
    private DepartmentRepo departmentRepo;
    @Autowired
    private StaffRepo staffRepo;

    @Test
    void findByCriteria() {
        UniversityFactory.fillUniversity(universityService);
        Department humanities = departmentRepo.findByName("Humanities").get();
        Staff professorBlack = staffRepo.findByLastName("Black")
                .stream().findFirst().get();

        System.out.println('\n' + "*** All Humanities Courses");
        find(filterBy().department(humanities));

        System.out.println('\n' + "*** 4 credit courses");
        find(filterBy().credits(4));

        System.out.println('\n' + "*** Courses taught by Professor Black");
        find(filterBy().instructor(professorBlack));

        System.out.println('\n' + "*** 4 Credit Courses In Humanties, taught by Professor Black");
        find(filterBy().department(humanities).credits(4)
                .instructor(professorBlack));
    }

    private void find(CourseFilter filter) {
        queryService.filterByExample(filter)
            .forEach(course -> {
                assertTrue(filter.meetsCriteria(course));
                System.out.println(course);
            });
    }
}