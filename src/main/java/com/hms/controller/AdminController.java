package com.hms.controller;

import com.hms.dto.AdminDto;
import com.hms.security.JwtTokenHelper;
import com.hms.service.AdminService;
import com.hms.utils.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("hms/admin")
@Slf4j
public class AdminController {
    @Autowired
    private AdminService adminService;

    @Autowired
    JwtTokenHelper jwtTokenHelper;

    //POST - add admin
    @PostMapping("/add")
    public ResponseEntity<AdminDto> addAdmin(@Valid @RequestBody AdminDto adminDto){
        AdminDto adminDto1 = this.adminService.addAdmin(adminDto);
        return new ResponseEntity<>(adminDto1, HttpStatus.CREATED);
    }

    //GET - get admin by email
    @GetMapping(params = "email")
    public ResponseEntity<AdminDto> getByEmail(@RequestParam String email){
        AdminDto adminDto = this.adminService.findByEmail(email);
        return new ResponseEntity<>(adminDto,HttpStatus.OK);
    }

    //GET - get all admin
    @GetMapping("/")
    public ResponseEntity<List<AdminDto>> getAll(){
        List<AdminDto> allAdmin = this.adminService.getAllAdmin();
        return new ResponseEntity<>(allAdmin,HttpStatus.OK);
    }

    //PUT - update admin
    @PutMapping("/update/{adminId}")
    public ResponseEntity<AdminDto> updateAdmin(@RequestBody AdminDto adminDto,@PathVariable int adminId){
        AdminDto adminDto1 = this.adminService.updateAdmin(adminDto, adminId);
        return new ResponseEntity<>(adminDto1,HttpStatus.OK);
    }

    //DELETE - delete admin
    @DeleteMapping("delete/{adminId}")
    public ResponseEntity<ApiResponse> deleteAdmin(@PathVariable int adminId, HttpServletRequest request){
        Integer id = jwtTokenHelper.getUserIdFromToken(request);
        if(id==adminId){
            return new ResponseEntity<>(new ApiResponse("You Cannot Delete Yourself",true),HttpStatus.EXPECTATION_FAILED);
        }
        this.adminService.deleteAdmin(adminId);
        return new ResponseEntity<>(new ApiResponse("Admin deleted successfully",true),HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<AdminDto> getAdminById(@PathVariable("id") int id){
        AdminDto user = adminService.getAdminById(id);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }
}
