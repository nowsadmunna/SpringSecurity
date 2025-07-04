package org.example.springsecurity.controller;

import org.example.springsecurity.Entity.User;
import org.example.springsecurity.dto.LoginRequest;
import org.example.springsecurity.dto.RegisterRequest;
import org.example.springsecurity.service.UserService;
import org.example.springsecurity.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired private UserService userService;
    @Autowired private JwtUtil jwtUtil;

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest req) {
        User user = new User();
        user.setUsername(req.username);
        user.setPassword(req.password);
        user.setRole(req.role);
        System.out.println(user);
        return userService.register(user) ? "Registration successful" : "Username already exists";
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest req) {
        return userService.authenticate(req.username, req.password)
                .map(user -> jwtUtil.generateToken(user.getUsername(), user.getRole().name()))
                .orElse("Invalid credentials");
    }

}