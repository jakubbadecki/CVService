package com.bjss.cvservice.users.controller;

import com.bjss.cvservice.exception.CVServiceException;
import com.bjss.cvservice.users.entity.UserDTO;
import com.bjss.cvservice.users.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.MessageFormat;

@Slf4j
@RestController
@RequestMapping("user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@Valid @ModelAttribute UserDTO user) {
        String username = user.getUsername();
        try {
            userService.createUser(username, user.getPassword());
            log.info("User {} created successfully!", username);
        } catch (Exception e) {
            log.error("Exception: ", e);
            throw new CVServiceException(
                    MessageFormat.format("Username {0} already exists!", username));
        }
    }

    @GetMapping
    public UserDTO getUser(Authentication authentication) {
        UserDetails userDetails = userService.loadUserByUsername(authentication.getName());
        return UserDTO.builder()
                .username(userDetails.getUsername())
                .password(userDetails.getPassword())
                .build();
    }

    @DeleteMapping
    public void deleteUser(Authentication authentication) {
        String username = authentication.getName();
        userService.deleteUser(authentication.getName());
        log.info("Successfully removed {} user.", username);
    }
}
