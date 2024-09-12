package com.sptingboot.blog.service;

import com.sptingboot.blog.payload.LoginDto;
import com.sptingboot.blog.payload.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);

    String register(RegisterDto registerDto);
}
