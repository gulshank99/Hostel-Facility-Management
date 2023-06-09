package com.hms.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminDto {
    private Integer id;
    @NotEmpty(message = "Must Not be Empty")
    @Email(message = "Not a Valid Email Format")
    private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Getter(onMethod_ = @JsonIgnore)
    @NotBlank
    @Size(min = 4,message = "Password must have more than 4 letters")
    private String password;
    @NotEmpty(message = "Must Not be Empty")
    private String fullName;
}
