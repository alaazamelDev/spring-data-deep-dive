package com.example.university.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.university.repository.StaffRepository;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.university.business.UniversityService;
import com.example.university.domain.Staff;

/**
 * Tests that verify simple CRUD methods
 */
@SpringBootTest
public class SimpleDBCrudTest {

    @Autowired
    private UniversityService universityService;

    @Autowired
    private StaffRepository staffRepository;

    private List<Staff> allStaff;
    private Optional<Staff> oneStaff;
    private int id;

    @Test
    public void testStaffCrud() {
        // Test Create
        UniversityFactory.fillUniversity(universityService);
        //Test FindA ll
        List<Staff> allStaff = universityService.findAllStaff();
        int totalStaff = allStaff.size();
        allStaff.stream().forEach(System.out::println);
        assertEquals(11,  allStaff.size());

        // Test Find by Id
        Staff deanThomas = allStaff.get(0);
        System.out.println(deanThomas);
        assertEquals(deanThomas, staffRepository.findById(deanThomas.getId()).get());

        // Test Update, Change first Name to Patrick
        deanThomas.getMember().setFirstName("Patrick");
        staffRepository.save(deanThomas);

        assertEquals("Patrick",
                staffRepository.findById(deanThomas.getId()).get().getMember().getFirstName());

        staffRepository.delete(deanThomas);
        allStaff = staffRepository.findAll();
        assertEquals(totalStaff -1, allStaff.size());
        allStaff.stream().forEach(System.out::println);
    }
}
