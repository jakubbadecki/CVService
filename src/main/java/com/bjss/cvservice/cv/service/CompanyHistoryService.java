package com.bjss.cvservice.cv.service;

import com.bjss.cvservice.cv.entity.dao.CompanyHistory;
import com.bjss.cvservice.cv.entity.dao.Cv;
import com.bjss.cvservice.cv.entity.dto.CompanyHistoryDTO;
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
public class CompanyHistoryService {
    private final UserRepository userRepository;
    private final UserService userService;

    @Autowired
    public CompanyHistoryService(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    public List<CompanyHistoryDTO> getCompanyHistory(String username) {
        User user = userService.loadUserByUsername(username);
        return Optional.ofNullable(user.getCv())
                .map(Cv::getCompanyHistoryList)
                .orElse(Collections.emptyList()).stream()
                .map(CompanyHistoryDTO::fromDAO)
                .collect(Collectors.toList());
    }

    public void saveCompanyHistory(String username, List<CompanyHistoryDTO> companyHistoryDTOList) {
        try {
            User user = userService.loadUserByUsername(username);
            Cv cv = Optional.ofNullable(user.getCv())
                    .orElseThrow(() -> new CVServiceException(MessageFormat
                            .format("No CV assigned for user {0}!", username)));
            List<CompanyHistory> companyHistoryList = cv.getCompanyHistoryList();
            companyHistoryList.clear();
            companyHistoryList.addAll(companyHistoryDTOList.stream()
                    .map(CompanyHistory::fromDTO)
                    .collect(Collectors.toList()));
            userRepository.save(user);
            log.info("Company History uploaded for user {}", username);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CVServiceException(e.getMessage());
        }
    }

    public void addCompanyHistory(String username, List<CompanyHistoryDTO> companyHistoryDTOList) {
        try {
            User user = userService.loadUserByUsername(username);
            Cv cv = Optional.ofNullable(user.getCv())
                    .orElseThrow(() -> new CVServiceException(MessageFormat
                            .format("No CV assigned for user {0}!", username)));
            List<CompanyHistory> companyHistoryList = companyHistoryDTOList.stream()
                    .map(CompanyHistory::fromDTO)
                    .collect(Collectors.toList());
            cv.getCompanyHistoryList().addAll(companyHistoryList);
            userRepository.save(user);
            log.info("Company History added for user {}", username);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CVServiceException(e.getMessage());
        }
    }

    public void deleteCompanyHistory(String username, String companyName) {
        User user = userService.loadUserByUsername(username);
        Cv cv = Optional.ofNullable(user.getCv())
                .orElseThrow(() -> new CVServiceException(MessageFormat
                        .format("No CV assigned for user {0}!", username)));
        CompanyHistory companyHistory = cv.getCompanyHistoryList().stream()
                .filter(history -> history.getCompanyName().equals(companyName))
                .findAny()
                .orElseThrow(() -> new CVServiceException("Company History with provided name doesn't exist!"));
        cv.getCompanyHistoryList().remove(companyHistory);
        userRepository.save(user);
        log.info("Successfully deleted provided Company History for user {}", username);
    }
}
