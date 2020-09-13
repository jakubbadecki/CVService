package com.bjss.cvservice.cv.service;

import com.bjss.cvservice.cv.entity.dao.Cv;
import com.bjss.cvservice.cv.entity.dto.CvDTO;
import com.bjss.cvservice.exception.CVServiceException;
import com.bjss.cvservice.users.entity.User;
import com.bjss.cvservice.users.repository.UserRepository;
import com.bjss.cvservice.users.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Optional;

@Slf4j
@Service
public class CvService {
    private final UserRepository userRepository;
    private final UserService userService;

    @Autowired
    public CvService(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    public CvDTO getCv(String username) {
        User user = userService.loadUserByUsername(username);
        return Optional.ofNullable(user.getCv())
                .map(CvDTO::fromDAO)
                .orElseThrow(() -> new CVServiceException(
                        MessageFormat.format("No CV found for user {0}", username)));
    }

    public void saveCv(String username, CvDTO cvDTO) {
        try {
            User user = userService.loadUserByUsername(username);
            user.setCv(Cv.fromDTO(cvDTO));
            userRepository.save(user);
            log.info("CV uploaded for user {}", username);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CVServiceException(e.getMessage());
        }
    }

    public void deleteCv(String username) {
        try {
            User user = userService.loadUserByUsername(username);
            user.setCv(null);
            userRepository.save(user);
            log.info("CV deleted for user {}", username);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CVServiceException(e.getMessage());
        }
    }
}
