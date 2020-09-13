package com.bjss.cvservice.users.repository;

import com.bjss.cvservice.users.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static org.junit.Assert.*;

@ExtendWith(SpringExtension.class)
@DataMongoTest
class UserRepositoryTest {
    public static final String TEST_PASSWORD = "testPassword";

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldReturnUserWhenFindByUsername() {
        String username = UUID.randomUUID().toString();
        User user = User.builder()
                .username(username)
                .password(TEST_PASSWORD)
                .build();
        mongoTemplate.save(user);
        assertEquals(user, userRepository.findByUsername(username).orElse(null));
    }

    @Test
    void shouldReturnEmptyOptionalWhenNoUserFound() {
        assertTrue(userRepository.findByUsername(UUID.randomUUID().toString()).isEmpty());
    }

    @Test
    void shouldThrowExceptionOnSaveWhenNullUser() {
        assertThrows(IllegalArgumentException.class, () -> userRepository.save(null));
    }
}