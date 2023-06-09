package com.hms.service;

import com.hms.dto.AdminDto;

import java.util.List;

public interface AdminService {

    //find by email
    AdminDto findByEmail(String email);

    //add admin
    AdminDto addAdmin(AdminDto adminDto);

    //update admin
    AdminDto updateAdmin(AdminDto adminDto, int adminId);

    //get all admin
    List<AdminDto> getAllAdmin();

    //delete admin
    void deleteAdmin(int adminId);
    AdminDto getAdminById(int id);

    void resetPassword(AdminDto adminDto, String decodedPassword);
}
