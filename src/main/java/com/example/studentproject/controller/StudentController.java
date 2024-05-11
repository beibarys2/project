package com.example.studentproject.controller;

import com.example.studentproject.dto.AuthenticationRequest;
import com.example.studentproject.dto.AuthenticationResponse;
import com.example.studentproject.dto.RegisterRequest;
import com.example.studentproject.dto.StudentDto;
import com.example.studentproject.model.Student;
import com.example.studentproject.model.User;
import com.example.studentproject.service.AuthenticationService;
import com.example.studentproject.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
@Slf4j
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudent();
    }

    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }

    @PostMapping
    public void addStudent(@RequestBody StudentDto dto) {
        log.info("Firstname: {}, LastName: {}", dto.getFirstName(), dto.getLastName());
        Student student = new Student();
        student.setFirstName(dto.getFirstName());
        student.setLastName(dto.getLastName());
        studentService.addStudent(student);
    }

    @RestController
    @RequiredArgsConstructor
    @RequestMapping("/api/v1/auth")
    public static class AuthenticationController {

        private final AuthenticationService service;
        @PostMapping("/register")
        public ResponseEntity<AuthenticationResponse> register(
                @RequestBody RegisterRequest request
        ) {
            return ResponseEntity.ok(service.register(request));
        }

        @PostMapping("/authenticate")
        public ResponseEntity<AuthenticationResponse> register(
                @RequestBody AuthenticationRequest request
        ) {
            return ResponseEntity.ok(service.authenticate(request));
        }

        @GetMapping("/{id}")
        public User getUserById(@PathVariable Long id) {
            return service.getUserById(id);
        }
    }
}
