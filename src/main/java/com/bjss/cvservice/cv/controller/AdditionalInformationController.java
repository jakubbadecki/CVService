package com.bjss.cvservice.cv.controller;

import com.bjss.cvservice.cv.entity.dto.AdditionalInformationDTO;
import com.bjss.cvservice.cv.service.AdditionalInformationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("additional-information")
public class AdditionalInformationController {

    private final AdditionalInformationService additionalInformationService;

    @Autowired
    public AdditionalInformationController(AdditionalInformationService additionalInformationService) {
        this.additionalInformationService = additionalInformationService;
    }

    @GetMapping
    public List<AdditionalInformationDTO> getAdditionalInformation(Authentication authentication) {
        return additionalInformationService.getAdditionalInformation(authentication.getName());
    }

    @PostMapping
    public void saveAdditionalInformation(@Valid @RequestBody List<AdditionalInformationDTO> companyHistoryDTOList,
                                          Authentication authentication) {
        additionalInformationService.saveAdditionalInformation(authentication.getName(), companyHistoryDTOList);
    }

    @PutMapping
    public void addAdditionalInformation(@Valid @RequestBody List<AdditionalInformationDTO> companyHistoryDTOList,
                                         Authentication authentication) {
        additionalInformationService.addAdditionalInformation(authentication.getName(), companyHistoryDTOList);
    }

    @DeleteMapping
    public void deleteAdditionalInformation(@Valid @RequestParam String name, Authentication authentication) {
        additionalInformationService.deleteAdditionalInformation(authentication.getName(), name);
    }
}
