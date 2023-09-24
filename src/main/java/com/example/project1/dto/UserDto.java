package com.example.project1.dto;

import com.example.project1.validation.Mobile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;
    
    @NotEmpty(message = "Email should not be empty")
    private String name;

    @Mobile
    @NotEmpty(message = "Email should not be empty")
    private String phoneNo;

    @NotEmpty(message = "Email should not be empty")
    @Email
    private String email;

    @NotEmpty(message = "Password should not be empty")
    private String password;

    @NotEmpty(message = "Password should not be empty")
    private String confirmPassword;
}
