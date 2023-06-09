package com.hms.service;

import com.hms.dto.StudentDto;
import com.hms.entity.Student;
import com.hms.exceptions.ResourceNotFoundException;
import com.hms.exceptions.UserDoesNotExistException;
import com.hms.exceptions.UserExistException;
import com.hms.entity.Room;
import com.hms.repo.StudentRepo;
import com.hms.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class StudentServiceImpl implements StudentService{

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private StudentRepo studentRepo;
    @Autowired
    private RoomService roomService;

    //to create student in DB
    @Override
    public StudentDto createStudent(StudentDto studentDto) {
        log.info("inside student service");
        Student user1 = studentRepo.findByRoll(studentDto.getRoll());
        if(user1 != null && user1.getIsActive()==1){
            throw new UserExistException("User With Roll No- "+user1.getRoll()+ " Already Exist");
        }
        Student user = studentRepo.findByEmail(studentDto.getEmail());
        if(user != null && user.getIsActive()==1){
            throw new UserExistException("User With Email No- "+user.getEmail()+"  Already Exist");
        }
        user = studentRepo.findByAadhaar(studentDto.getAadhaar());
        if(user != null && user.getIsActive()==1){
            throw new UserExistException("User With Aadhaar No- "+user.getAadhaar()+"  Already Exist");
        }
        user = studentRepo.findByMobile(studentDto.getMobile());
        if(user != null && user.getIsActive()==1){
            throw new UserExistException("User With Mobile No- "+user.getMobile()+"  Already Exist");
        }
        if(studentDto.getGender().equalsIgnoreCase("male")) {
            if (studentDto.getYear() == 1) {
                List<Room> rooms = this.roomService.findByHostelName(Constants.VIVEKANANDA);
                for (Room room : rooms) {
                    if (room.getIsEmpty() != 2) {
                        studentDto.setRoomNo(room.getRoomNo());
                        roomService.updateRoomByRoomNo(room.getRoomNo(), room.getIsEmpty() == 1 ? 2 : 1, Constants.VIVEKANANDA);
                        studentDto.setIsActive(1);
                        break;
                    }
                }
            } else if (studentDto.getYear() == 2 || studentDto.getYear() == 3) {
                List<Room> rooms = this.roomService.findByHostelName(Constants.JCBOSE);
                for (Room room : rooms) {
                    if (room.getIsEmpty() != 2) {
                        studentDto.setRoomNo(room.getRoomNo());
                        roomService.updateRoomByRoomNo(room.getRoomNo(), room.getIsEmpty() == 1 ? 2 : 1, Constants.JCBOSE);
                        studentDto.setIsActive(1);
                        break;
                    }
                }
            } else if (studentDto.getYear() == 4) {
                List<Room> rooms = this.roomService.findByHostelName(Constants.APJ);
                for (Room room : rooms) {
                    if (room.getIsEmpty() != 2) {
                        studentDto.setRoomNo(room.getRoomNo());
                        log.info(room.getIsEmpty() + "--------" + room.getRoomNo());
                        roomService.updateRoomByRoomNo(room.getRoomNo(), room.getIsEmpty() == 1 ? 2 : 1, Constants.APJ);
                        studentDto.setIsActive(1);
                        break;
                    }
                }
            }
        }
        else{
            List<Room> rooms = this.roomService.findByHostelName(Constants.SAROJINI);
            for (Room room : rooms) {
                if (room.getIsEmpty() != 2) {
                    studentDto.setRoomNo(room.getRoomNo());
                    roomService.updateRoomByRoomNo(room.getRoomNo(), room.getIsEmpty() == 1 ? 2 : 1, Constants.SAROJINI);
                    studentDto.setIsActive(1);
                    break;
                }
            }
        }
        if(user1!=null && user1.getIsActive()==0) {
            List<Student> students = studentRepo.findByEmailAndRollNot(studentDto.getEmail(), user1.getRoll());
            if(students!=null && students.size()>0){
                throw new UserExistException("The Email: "+studentDto.getEmail()+" is already registered For Roll: "+students.get(0).getRoll());
            }
            students = studentRepo.findByAadhaarAndRollNot(studentDto.getAadhaar(), user1.getAadhaar());
            if(students!=null && students.size()>0){
                throw new UserExistException("The Aadhaar: "+studentDto.getAadhaar()+" is already registered For Roll: "+students.get(0).getRoll());
            }
            students = studentRepo.findByMobileAndRollNot(studentDto.getMobile(),user1.getRoll());
            if(students!=null && students.size()>0){
                throw new UserExistException("The Mobile: "+studentDto.getMobile()+" is already registered For Roll: "+students.get(0).getRoll());
            }
            user1.setRoomNo(studentDto.getRoomNo());
            user1.setCity(studentDto.getCity());
            user1.setDepartment(studentDto.getDepartment());
            user1.setCourse(studentDto.getCourse());
            user1.setAadhaar(studentDto.getAadhaar());
            user1.setEmail(studentDto.getEmail());
            user1.setFirstName(studentDto.getFirstName());
            user1.setMiddleName(studentDto.getMiddleName());
            user1.setLastName(studentDto.getLastName());
            user1.setState(studentDto.getState());
            user1.setMobile(studentDto.getMobile());
            user1.setIsActive(studentDto.getIsActive());
            user1.setGender(studentDto.getGender());
            user1.setStreet(studentDto.getStreet());
            user1.setYear(studentDto.getYear());
            Student student = studentRepo.save(user1);
            return modelMapper.map(student,StudentDto.class);
        } else {
            Student student = this.modelMapper.map(studentDto, Student.class);
            Student save = this.studentRepo.save(student);
            return this.modelMapper.map(save, StudentDto.class);
        }
    }

    //to update student
    @Override
    public StudentDto updateStudent(StudentDto studentDto, int studentId) {
        Student student = this.studentRepo.findById(studentId).orElseThrow(() -> new ResourceNotFoundException("Student", "Id", studentId));
        student.setFirstName(studentDto.getFirstName());
        student.setLastName(studentDto.getLastName());
        student.setMiddleName(studentDto.getMiddleName());
        student.setEmail(studentDto.getEmail());
        student.setRoll(studentDto.getRoll());
        student.setGender(studentDto.getGender());
        student.setCourse(studentDto.getCourse());
        student.setAadhaar(studentDto.getAadhaar());
        student.setCity(studentDto.getCity());
        student.setState(studentDto.getState());
        student.setStreet(studentDto.getStreet());
        student.setMobile(studentDto.getMobile());
        student.setYear(studentDto.getYear());
        student.setDepartment(studentDto.getDepartment());
//        student.setRoomNo(studentDto.getRoomNo());
//        student.setIsActive(studentDto.getIsActive());
        Student save = this.studentRepo.save(student);
        log.info("Student updated");
        return this.modelMapper.map(save,StudentDto.class);
    }

    //to get all student form db
    @Override
    public List<StudentDto> getAllStudent() {
        List<Student> students = this.studentRepo.findAll();
        return students.stream().map(student -> this.modelMapper.map(student, StudentDto.class)).collect(Collectors.toList());
    }

    //to get a student with id
    @Override
    public StudentDto getStudent(int studentId) {
        Student student = this.studentRepo.findById(studentId).orElseThrow(() -> new ResourceNotFoundException("Student", "Id", studentId));
        return this.modelMapper.map(student,StudentDto.class);
    }

    //to delete a student
    @Override
    public void deleteStudent(int studentId) {
        Student student = this.studentRepo.findById(studentId).orElseThrow(() -> new ResourceNotFoundException("Student", "Id", studentId));
        String gender = student.getGender();
        int year = student.getYear();
        int roomNo = student.getRoomNo();
        if(gender.equalsIgnoreCase("male")) {
            if (year == 1) {
                Room room = roomService.findByRoomAndHostel(roomNo, Constants.VIVEKANANDA);
                if(room.getIsEmpty() != 0) {
                    room.setIsEmpty(room.getIsEmpty() - 1);
                }
            } else if (year == 2 || year == 3) {
                Room room = roomService.findByRoomAndHostel(roomNo, Constants.JCBOSE);
                if(room.getIsEmpty() != 0) {
                    room.setIsEmpty(room.getIsEmpty() - 1);
                }
            } else if (year == 4) {
                Room room = roomService.findByRoomAndHostel(roomNo, Constants.APJ);
                if(room.getIsEmpty() != 0) {
                    room.setIsEmpty(room.getIsEmpty() - 1);
                }
            }
        }
        else{
            Room room = roomService.findByRoomAndHostel(roomNo, Constants.SAROJINI);
            if(room.getIsEmpty() != 0) {
                room.setIsEmpty(room.getIsEmpty() - 1);
            }
        }

        student.setIsActive(0);
        studentRepo.save(student);
    }

    //find student by first name
    @Override
    public List<StudentDto> getByFirstName(String firstName) {
        List<Student> students = this.studentRepo.findByFirstName(firstName);
        return students.stream().map(student -> this.modelMapper.map(student, StudentDto.class)).collect(Collectors.toList());
    }

    //find student by state name
    @Override
    public List<StudentDto> getByStateName(String state) {
        List<Student> students = this.studentRepo.findByState(state);
        return students.stream().map(student -> this.modelMapper.map(student, StudentDto.class)).collect(Collectors.toList());
    }

    @Override
    public StudentDto getByRoll(String roll) {
        Student student = studentRepo.findByRoll(roll);
        if(student==null){
            throw new UserDoesNotExistException("Student With Roll No- "+roll+" Not Found");
        }
        return modelMapper.map(student,StudentDto.class);
    }

    @Override
    public StudentDto getStudentByAadhaar(String aadhaar) {
        Student student = studentRepo.findByAadhaar(aadhaar);
        if (student==null){
            throw new UserDoesNotExistException("Student With Aadhaar No- "+aadhaar+" Not Found");
        }
        return modelMapper.map(student,StudentDto.class);
    }

    @Override
    public StudentDto getStudentByMobile(String mobile) {
        Student student = studentRepo.findByMobile(mobile);
        if (student==null){
            throw new UserDoesNotExistException("Student With Mobile No- "+mobile+" Not Found");
        }
        return modelMapper.map(student,StudentDto.class);
    }

}
