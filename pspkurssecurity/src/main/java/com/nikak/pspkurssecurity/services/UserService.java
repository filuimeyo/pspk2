package com.nikak.pspkurssecurity.services;

import com.nikak.pspkurssecurity.dto.ProfileResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
   UserDetailsService userDetailsService();

   ProfileResponse getProfileInfo(String email);
}
