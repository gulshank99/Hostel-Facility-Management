package com.hms.service;

import com.hms.dto.AdminDto;
import com.hms.entity.Admin;
import com.hms.exceptions.ResourceNotFoundException;
import com.hms.exceptions.UserDoesNotExistException;
import com.hms.exceptions.UserExistException;
import com.hms.repo.AdminRepo;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AdminServiceImpl implements AdminService{
    @Autowired
    private AdminRepo adminRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    //find admin by email
    @Override
    public AdminDto findByEmail(String email) {
        Admin user = adminRepo.findByEmail(email);
        if(user == null){
            throw new UserDoesNotExistException("Admin doesn't exists");
        }
        return modelMapper.map(user,AdminDto.class);
    }

    // add admin
    @Override
    public AdminDto addAdmin(AdminDto adminDto) {
        log.info("Adding admin");
        Admin user = adminRepo.findByEmail(adminDto.getEmail());
        if(user!=null){
            throw new UserExistException("Admin Already Exist");
        }
        Admin admin = this.modelMapper.map(adminDto, Admin.class);
        String encoded=passwordEncoder.encode(admin.getPassword());
        log.info("encrypt- "+encoded);
        admin.setPassword(encoded);
        Admin save = this.adminRepo.save(admin);
        return this.modelMapper.map(save,AdminDto.class);
    }

    //update admin
    @Override
    public AdminDto updateAdmin(AdminDto adminDto, int adminId) {
        Admin admin = this.adminRepo.findById(adminId).orElseThrow(() -> new ResourceNotFoundException("Admin", "Id", adminId));
        admin.setEmail(adminDto.getEmail());
//        admin.setPassword(adminDto.getPassword());
        admin.setFullName(adminDto.getFullName());
        Admin save = this.adminRepo.save(admin);
        return this.modelMapper.map(save,AdminDto.class);
    }

    //get all admin
    @Override
    public List<AdminDto> getAllAdmin() {
        List<Admin> admins = this.adminRepo.findAll();
        return admins.stream().map(admin -> this.modelMapper.map(admin,AdminDto.class)).collect(Collectors.toList());
    }

    //delete admin
    @Override
    public void deleteAdmin(int adminId) {
        Admin admin = this.adminRepo.findById(adminId).orElseThrow(() -> new ResourceNotFoundException("Admin", "Id", adminId));
        this.adminRepo.delete(admin);
    }

    @Override
    public AdminDto getAdminById(int id) {
        Admin admin = adminRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Admin", "Id", id));
        return modelMapper.map(admin,AdminDto.class);
    }

    @Override
    public void resetPassword(AdminDto adminDto, String password) {
        log.info("resetting password");
        adminDto.setPassword(password);
        Admin admin = modelMapper.map(adminDto, Admin.class);
        String encoded=passwordEncoder.encode(admin.getPassword());
        log.info("encoded-"+encoded);
        admin.setPassword(encoded);
        adminRepo.save(admin);
    }
}
