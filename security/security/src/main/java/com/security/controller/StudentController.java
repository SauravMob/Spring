package com.security.controller;

import com.security.model.Student;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class StudentController {

    List<Student> list = new ArrayList<>();
    @GetMapping("/students")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Student> students(){
        Student student1 = new Student();
        student1.setId(1);
        student1.setFirstname("Saurav");
        student1.setLastname("Upadhyay");

        Student student2 = new Student();
        student2.setId(2);
        student2.setFirstname("Shivani");
        student2.setLastname("Gurnale");

        list.add(student1);
        list.add(student2);

        return list;
    }

    @PostMapping("/student")
    public Student createStudent(@RequestBody Student student) {
        students().add(student);
        return student;
    }
}
