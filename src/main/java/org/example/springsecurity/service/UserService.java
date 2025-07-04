package org.example.springsecurity.service;

import org.example.springsecurity.Entity.User;
import org.example.springsecurity.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired private UserRepository userRepo;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public boolean register(User user) {
        if (userRepo.findByUsername(user.getUsername()).isPresent()) return false;
        user.setPassword(encoder.encode(user.getPassword()));
        userRepo.save(user);
        return true;
    }

    public Optional<User> authenticate(String username, String rawPassword) {
        return userRepo.findByUsername(username)
                .filter(user -> encoder.matches(rawPassword, user.getPassword()));
    }
}