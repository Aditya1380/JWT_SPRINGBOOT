package com.aditya.sec.dto;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
	private String username;
    private String password;
    private Set<String> roles; // e.g. ["ROLE_USER", "ROLE_ADMIN"]
}
