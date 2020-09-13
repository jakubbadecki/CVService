package com.bjss.cvservice.cv.controller;

import com.bjss.cvservice.cv.entity.dto.CompanyHistoryDTO;
import com.bjss.cvservice.cv.service.CompanyHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("company-history")
public class CompanyHistoryController {

    private final CompanyHistoryService companyHistoryService;

    @Autowired
    public CompanyHistoryController(CompanyHistoryService companyHistoryService) {
        this.companyHistoryService = companyHistoryService;
    }

    @GetMapping
    public List<CompanyHistoryDTO> getCompanyHistory(Authentication authentication) {
        return companyHistoryService.getCompanyHistory(authentication.getName());
    }

    @PostMapping
    public void saveCompanyHistory(@Valid @RequestBody List<CompanyHistoryDTO> companyHistoryDTOList,
                                   Authentication authentication) {
        companyHistoryService.saveCompanyHistory(authentication.getName(), companyHistoryDTOList);
    }

    @PutMapping
    public void addCompanyHistory(@Valid @RequestBody List<CompanyHistoryDTO> companyHistoryDTOList,
                                  Authentication authentication) {
        companyHistoryService.addCompanyHistory(authentication.getName(), companyHistoryDTOList);
    }

    @DeleteMapping
    public void deleteCompanyHistory(@Valid @RequestParam String companyName, Authentication authentication) {
        companyHistoryService.deleteCompanyHistory(authentication.getName(), companyName);
    }
}
