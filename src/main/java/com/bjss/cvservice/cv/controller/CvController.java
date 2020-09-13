package com.bjss.cvservice.cv.controller;

import com.bjss.cvservice.cv.entity.dto.CvDTO;
import com.bjss.cvservice.cv.service.CvService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("cv")
public class CvController {

    private final CvService cvService;

    @Autowired
    public CvController(CvService cvService) {
        this.cvService = cvService;
    }

    @GetMapping
    public CvDTO getCv(Authentication authentication) {
        return cvService.getCv(authentication.getName());
    }

    @PostMapping
    public void updateCv(@Valid @RequestBody CvDTO cv, Authentication authentication) {
        cvService.saveCv(authentication.getName(), cv);
    }

    @DeleteMapping
    public void deleteCv(Authentication authentication) {
        cvService.deleteCv(authentication.getName());
    }
}
