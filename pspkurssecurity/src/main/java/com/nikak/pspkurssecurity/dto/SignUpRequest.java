package com.nikak.pspkurssecurity.dto;

import com.nikak.pspkurssecurity.entities.Role;
import lombok.Data;

@Data
public class SignUpRequest {

    private String name;
    private String email;

    private String password;
    private Role role;

}
