package com.hms.security;

import com.hms.entity.Admin;
import com.hms.entity.Student;
import com.hms.repo.AdminRepo;
import com.hms.repo.StudentRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    AdminRepo adminRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Loading Username From DB");
        Admin user = adminRepo.findByEmail(username);
        List<SimpleGrantedAuthority> authorities =new ArrayList<>();
        if (user==null){
            throw new UsernameNotFoundException("User Does Not Exist");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),authorities);
    }
}

