package com.bjss.cvservice.users.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserDTOTest {

    @Test
    void shouldBuildDTOWhenUsingBuilder() {
        String testPassword = "testPassword";
        String testUsername = "testUsername";

        UserDTO userDTO = UserDTO.builder()
                .username(testUsername)
                .password(testPassword)
                .build();

        assertEquals(testPassword, userDTO.getPassword());
        assertEquals(testUsername, userDTO.getUsername());
    }
}