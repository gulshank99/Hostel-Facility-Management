package com.hms.repo;

import com.hms.dto.StudentDto;
import com.hms.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepo extends JpaRepository<Student,Integer> {

    List<Student> findByFirstName(String firstName);

    Student findByEmail(String email);

    List<Student> findByState(String stateName);
    Student findByRoll(String roll);
    Student findByAadhaar(String aadhaar);
    Student findByMobile(String mobile);

    List<Student> findByEmailAndRollNot(String email,String roll);
    List<Student> findByMobileAndRollNot(String mobile,String roll);
    List<Student> findByAadhaarAndRollNot(String aadhaar,String roll);
}
