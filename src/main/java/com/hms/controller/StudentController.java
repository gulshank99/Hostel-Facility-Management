package com.hms.controller;

import com.hms.dto.StudentDto;
import com.hms.service.StudentService;
import com.hms.utils.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/hms/student")
@Slf4j
public class StudentController {
    @Autowired
    private StudentService studentService;

    //POST - to create a student
    @PostMapping("/register")
    public ResponseEntity<StudentDto> createStudent(@Valid @RequestBody StudentDto studentDto) {
        log.info("aaya ki nai aaya");
        StudentDto student = this.studentService.createStudent(studentDto);
        return new ResponseEntity<>(student, HttpStatus.CREATED);
    }

    //PUT - to update student
    @PutMapping("/update/{studentId}")
    public ResponseEntity<StudentDto> updateStudent(@Valid @RequestBody StudentDto studentDto, @PathVariable int studentId) {
        log.info("Update Api Triggered");
        StudentDto studentDto1 = this.studentService.updateStudent(studentDto, studentId);
        return new ResponseEntity<>(studentDto1, HttpStatus.OK);
    }

    //GET - to get student by id
    @GetMapping("/{studentId}")
    public ResponseEntity<StudentDto> getStudent(@Valid @PathVariable int studentId) {
        StudentDto student = this.studentService.getStudent(studentId);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    //GET - to get all student
    @GetMapping("/")
    public ResponseEntity<List<StudentDto>> getAllStudent() {
        List<StudentDto> allStudent = this.studentService.getAllStudent();
        return new ResponseEntity<>(allStudent, HttpStatus.OK);
    }

    //DELETE - to delete a student
    @DeleteMapping("/{studentId}")
    public ResponseEntity<ApiResponse> deleteStudent(@Valid @PathVariable int studentId) {
        this.studentService.deleteStudent(studentId);
        ApiResponse apiResponse = new ApiResponse("User Deleted Successfully", true);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    //GET - to get a student by first name
    @GetMapping
    public ResponseEntity<List<StudentDto>> getByFirstName(@RequestParam("name") String firstName) {
        List<StudentDto> students = this.studentService.getByFirstName(firstName);
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    //GET - to get student by state name
    @GetMapping(params = "state")
    public  ResponseEntity<List<StudentDto>> getByState(@RequestParam("state") String stateName){
        List<StudentDto> students = this.studentService.getByStateName(stateName);
        return new ResponseEntity<>(students, HttpStatus.OK);
    }
    @GetMapping(params = "roll")
    public ResponseEntity<StudentDto> getByRoll(@RequestParam("roll") String roll){
        StudentDto student = studentService.getByRoll(roll);
        return new ResponseEntity<>(student,HttpStatus.OK);
    }

}
