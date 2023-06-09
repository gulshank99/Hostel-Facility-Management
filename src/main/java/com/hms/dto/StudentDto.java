package com.hms.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Setter(onMethod_ = @JsonIgnore)
    private Integer id;
    @NotEmpty(message = "Must Not be Empty")
    @Size(min = 2,message = "First Name Must be Greater Than 2 Letters")
    private String firstName;
    private String middleName;
    private String lastName;
    @NotEmpty(message = "Must Not be Empty")
    @Size(min = 12,max = 12,message = "Aadhaar Number Must be of 12 digits")
    private String aadhaar;
    @NotEmpty(message = "Must Not be Empty")
    @Pattern(regexp = "^\\d{10}$",message = "Must Contain 10 Digits")
    private String mobile;
    @NotEmpty(message = "Must Not be Empty")
    @Email(message = "Not a Valid Email Format")
    private String email;
    @NotEmpty(message = "Must not be empty")
    private String street;
    @NotEmpty(message = "Must not be empty")
    private String city;
    @NotEmpty(message = "Must not be empty")
    private String state;
    @NotEmpty(message = "Must not be empty")
    private String gender;
    @NotEmpty(message = "Must not be empty")
    private String course;
    @NotEmpty(message = "Must not be empty")
    private String roll;
    @Min(value = 1 ,message = "Year should be between 1-4")
    @Max(value = 4 ,message = "Year should be between 1-4")
    private int year;
    @NotEmpty(message = "Must not be empty")
    private String department;
    private int roomNo;
    private int isActive;
}
