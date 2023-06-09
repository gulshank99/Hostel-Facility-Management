package com.hms.service;

import com.hms.dto.StudentDto;
import com.hms.entity.Student;

import java.util.List;

public interface StudentService {

    //create student
    StudentDto createStudent(StudentDto studentDto);

    //update student
    StudentDto updateStudent(StudentDto studentDto, int studentId);

    //get all student
    List<StudentDto> getAllStudent();

    //get a student
    StudentDto getStudent(int StudentId);

    //delete student
    void deleteStudent(int studentId);

    //find student by first name
    List<StudentDto> getByFirstName(String firstName);

    //find student by state
    List<StudentDto> getByStateName(String state);
    StudentDto getByRoll(String roll);

    StudentDto getStudentByAadhaar(String aadhaar);
    StudentDto getStudentByMobile(String mobile);

}
