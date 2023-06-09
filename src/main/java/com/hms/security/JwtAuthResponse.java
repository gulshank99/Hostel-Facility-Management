package com.hms.security;

import com.hms.dto.AdminDto;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtAuthResponse {
    private AdminDto user;
    private String token;
    private String expiration;
}
