package com.example.spring4shellweb.service;

import com.example.spring4shellweb.User;
import com.example.spring4shellweb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    

    public boolean authenticate(String username, String password) {
        Optional<User> userOpt = userRepository.findByEmail(username);
        if (userOpt.isEmpty()) {
            return false;
        }
        User user = userOpt.get();
        return user.getPassword().equals(password);
    }
}
