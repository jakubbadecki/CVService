package com.bjss.cvservice.users.service;

import com.bjss.cvservice.exception.CVServiceException;
import com.bjss.cvservice.users.entity.User;
import com.bjss.cvservice.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void createUser(String username, String password) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new CVServiceException("Username already exists!");
        }
        userRepository.save(User.builder()
                .username(username)
                .password(bCryptPasswordEncoder.encode(password))
                .role(User.Role.USER)
                .build());
    }

    public void deleteUser(String username) {
        User user = loadUserByUsername(username);
        userRepository.delete(user);
    }

    @Override
    public User loadUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new CVServiceException(
                        MessageFormat.format("Cannot find username {0}!", username))
                );
    }
}
