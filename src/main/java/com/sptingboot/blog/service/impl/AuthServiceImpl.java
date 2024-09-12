package com.sptingboot.blog.service.impl;

import com.sptingboot.blog.entity.Role;
import com.sptingboot.blog.entity.User;
import com.sptingboot.blog.exception.BlogApiException;
import com.sptingboot.blog.payload.LoginDto;
import com.sptingboot.blog.payload.RegisterDto;
import com.sptingboot.blog.repository.RoleRepository;
import com.sptingboot.blog.repository.UserRepository;
import com.sptingboot.blog.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    private AuthenticationManager authenticationManager;
    private UserRepository  userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           UserRepository userRepository, RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String login(LoginDto loginDto){
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDto.getUsernameOrEmail(), loginDto.getPassword()
            ));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            return "User Logged-In Successfully...";
        }
        catch (AuthenticationException e) {
            System.out.println("unauthroized");
            throw new BlogApiException(HttpStatus.UNAUTHORIZED, "Invalid Credentials");
        }

    }

    @Override
    public String register(RegisterDto registerDto) {
//        Check if the userName already existed or not
        if(userRepository.existsByUsername(registerDto.getUsername())){
             throw new BlogApiException(HttpStatus.BAD_REQUEST, "Username already existed");
        }
//        Check if the email already existed or not
        if(userRepository.existsByEmail(registerDto.getEmail())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Email already existed");
        }
        User user = new User();
        user.setName(registerDto.getName());
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

//        set the roles
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER").get();
        roles.add(userRole);
        user.setRoles(roles);
        userRepository.save(user);

        return "User Registered Successfully...";
    }


}
