package com.sptingboot.blog.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class passwordByCrypt {

    public static void main(String[] args) {
        PasswordEncoder encodedPassword = new BCryptPasswordEncoder();
        System.out.println(encodedPassword.encode("user"));
        System.out.println(encodedPassword.encode("admin"));
    }
}
