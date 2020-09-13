package com.bjss.cvservice.cv.service;

import com.bjss.cvservice.cv.entity.dao.Cv;
import com.bjss.cvservice.cv.entity.dao.Skill;
import com.bjss.cvservice.cv.entity.dto.SkillDTO;
import com.bjss.cvservice.exception.CVServiceException;
import com.bjss.cvservice.users.entity.User;
import com.bjss.cvservice.users.repository.UserRepository;
import com.bjss.cvservice.users.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SkillsService {
    private final UserRepository userRepository;
    private final UserService userService;

    @Autowired
    public SkillsService(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }


    public List<SkillDTO> getSkills(String username) {
        User user = userService.loadUserByUsername(username);
        return Optional.ofNullable(user.getCv())
                .map(Cv::getSkills)
                .orElse(Collections.emptyList()).stream()
                .map(SkillDTO::fromDAO)
                .collect(Collectors.toList());
    }

    public void saveSkills(String username, List<SkillDTO> skillDTOList) {
        try {
            User user = userService.loadUserByUsername(username);
            Cv cv = Optional.ofNullable(user.getCv())
                    .orElseThrow(() -> new CVServiceException(MessageFormat
                            .format("No CV assigned for user {0}!", username)));
            List<Skill> skills = cv.getSkills();
            skills.addAll(skillDTOList.stream()
                    .map(Skill::fromDTO)
                    .collect(Collectors.toList()));
            userRepository.save(user);
            log.info("Skills uploaded for user {}", username);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CVServiceException(e.getMessage());
        }
    }

    public void addSkills(String username, List<SkillDTO> skillDTOList) {
        try {
            User user = userService.loadUserByUsername(username);
            Cv cv = Optional.ofNullable(user.getCv())
                    .orElseThrow(() -> new CVServiceException(MessageFormat
                            .format("No CV assigned for user {0}!", username)));
            List<Skill> skills = skillDTOList.stream()
                    .map(Skill::fromDTO)
                    .collect(Collectors.toList());
            cv.getSkills().addAll(skills);
            userRepository.save(user);
            log.info("Skills added for user {}", username);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CVServiceException(e.getMessage());
        }
    }

    public void deleteSkill(String username, String name) {
        User user = userService.loadUserByUsername(username);
        Cv cv = Optional.ofNullable(user.getCv())
                .orElseThrow(() -> new CVServiceException(MessageFormat
                        .format("No CV assigned for user {0}!", username)));
        Skill skill = cv.getSkills().stream()
                .filter(skillToRemove -> skillToRemove.getName().equals(name))
                .findAny()
                .orElseThrow(() -> new CVServiceException("Skill with provided name doesn't exist!"));
        cv.getSkills().remove(skill);
        userRepository.save(user);
        log.info("Successfully deleted provided Skill for user {}", username);
    }
}
