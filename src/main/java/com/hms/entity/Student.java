package com.hms.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "students")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "middle_name")
    private String middleName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "aadhaar_no",unique = true,nullable = false)
    private String aadhaar;
    @Column(name = "mobile_no",unique = true,nullable = false,length = 10)
    private String mobile;
    @Column(name = "email_id",unique = true,nullable = false)
    private String email;
    private String street;
    private String city;
    private String state;
    private String gender;
    private String course;
    @Column(unique = true,nullable = false)
    private String roll;
    @Column(nullable = false)
    private int year;
    @Column(nullable = false)
    private String department;
    @Column(name = "room_no")
    private int roomNo;
    private int isActive;
}
