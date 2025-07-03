package com.example.university.dao;

import com.example.university.business.UniversityService;
import com.example.university.domain.Course;
import com.example.university.domain.Department;
import com.example.university.domain.Staff;
import com.example.university.domain.Student;

/**
 * Testing Helper class that initializes the database with a seeded
 * set of students, staff, courses and deparments
 */
class UniversityFactory {

    public static void fillUniversity(UniversityService service) {
        service.deleteAll();
        System.out.println("Number of courses = " + service.findAllCourses().size());
        boolean fullTime = true;
        Student jane = service.createStudent("jane", "Doe", fullTime, 20);
        Student john = service.createStudent("john", "Doe", fullTime, 22);
        Student mike = service.createStudent("mike", "Smith", fullTime, 18);
        Student ally = service.createStudent("ally", "Kim", !fullTime, 19);
    }
}