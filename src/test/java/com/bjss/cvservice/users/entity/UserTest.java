package com.bjss.cvservice.users.entity;

import com.bjss.cvservice.cv.entity.dao.Cv;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

class UserTest {
    private static final String TEST_USERNAME = "testUsername";
    private static final String TEST_PASSWORD = "testPassword";

    @Test
    void shouldBuildDAOWhenUsingBuilder() {
        User user = getUser();

        assertEquals(TEST_USERNAME, user.getUsername());
        assertEquals(TEST_PASSWORD, user.getPassword());
        assertEquals(User.Role.USER, user.getRole());
        assertEquals(Collections.singletonList(new SimpleGrantedAuthority(User.Role.USER.name())), user.getAuthorities());
        assertTrue(user.isEnabled());
        assertTrue(user.isAccountNonLocked());
        assertTrue(user.isCredentialsNonExpired());
        assertTrue(user.isAccountNonExpired());
    }

    @Test
    void shouldUserContainCvWhenAdded() {
        User user = getUser();
        Cv cv = Cv.builder().build();

        user.setCv(cv);
        assertEquals(cv, user.getCv());
    }

    private User getUser() {
        return User.builder()
                .role(User.Role.USER)
                .username(TEST_USERNAME)
                .password(TEST_PASSWORD)
                .build();
    }
}