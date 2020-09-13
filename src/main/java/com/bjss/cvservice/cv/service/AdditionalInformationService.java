package com.bjss.cvservice.cv.service;

import com.bjss.cvservice.cv.entity.dao.AdditionalInformation;
import com.bjss.cvservice.cv.entity.dao.Cv;
import com.bjss.cvservice.cv.entity.dto.AdditionalInformationDTO;
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
public class AdditionalInformationService {
    private final UserRepository userRepository;
    private final UserService userService;

    @Autowired
    public AdditionalInformationService(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    public List<AdditionalInformationDTO> getAdditionalInformation(String username) {
        User user = userService.loadUserByUsername(username);
        return Optional.ofNullable(user.getCv())
                .map(Cv::getAdditionalInformationList)
                .orElse(Collections.emptyList()).stream()
                .map(AdditionalInformationDTO::fromDAO)
                .collect(Collectors.toList());
    }

    public void saveAdditionalInformation(String username, List<AdditionalInformationDTO> additionalInformationDTOList) {
        try {
            User user = userService.loadUserByUsername(username);
            Cv cv = Optional.ofNullable(user.getCv())
                    .orElseThrow(() -> new CVServiceException(MessageFormat
                            .format("No CV assigned for user {0}!", username)));
            List<AdditionalInformation> additionalInformationList = cv.getAdditionalInformationList();
            additionalInformationList.clear();
            additionalInformationList.addAll(additionalInformationDTOList.stream()
                    .map(AdditionalInformation::fromDTO)
                    .collect(Collectors.toList()));
            userRepository.save(user);
            log.info("Additional Information uploaded for user {}", username);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CVServiceException(e.getMessage());
        }
    }

    public void addAdditionalInformation(String username, List<AdditionalInformationDTO> additionalInformationDTOList) {
        try {
            User user = userService.loadUserByUsername(username);
            Cv cv = Optional.ofNullable(user.getCv())
                    .orElseThrow(() -> new CVServiceException(MessageFormat
                            .format("No CV assigned for user {0}!", username)));
            List<AdditionalInformation> additionalInformationList = additionalInformationDTOList.stream()
                    .map(AdditionalInformation::fromDTO)
                    .collect(Collectors.toList());
            cv.getAdditionalInformationList().addAll(additionalInformationList);
            userRepository.save(user);
            log.info("Additional Information added for user {}", username);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CVServiceException(e.getMessage());
        }
    }

    public void deleteAdditionalInformation(String username, String name) {
        User user = userService.loadUserByUsername(username);
        Cv cv = Optional.ofNullable(user.getCv())
                .orElseThrow(() -> new CVServiceException(MessageFormat
                        .format("No CV assigned for user {0}!", username)));
        AdditionalInformation additionalInformation = cv.getAdditionalInformationList().stream()
                .filter(information -> information.getName().equals(name))
                .findAny()
                .orElseThrow(() -> new CVServiceException("Additional Information with provided name doesn't exist!"));
        cv.getAdditionalInformationList().remove(additionalInformation);
        userRepository.save(user);
        log.info("Successfully deleted provided Company History for user {}", username);
    }
}
