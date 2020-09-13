package com.bjss.cvservice.cv.controller;

import com.bjss.cvservice.cv.entity.dto.SkillDTO;
import com.bjss.cvservice.cv.service.SkillsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("skills")
public class SkillsController {

    private final SkillsService skillsService;

    @Autowired
    public SkillsController(SkillsService skillsService) {
        this.skillsService = skillsService;
    }

    @GetMapping
    public List<SkillDTO> getSkills(Authentication authentication) {
        return skillsService.getSkills(authentication.getName());
    }

    @PostMapping
    public void saveSkills(@Valid @RequestBody List<SkillDTO> skillDTOList, Authentication authentication) {
        skillsService.saveSkills(authentication.getName(), skillDTOList);
    }

    @PutMapping
    public void addSkills(@Valid @RequestBody List<SkillDTO> skillDTOList, Authentication authentication) {
        skillsService.addSkills(authentication.getName(), skillDTOList);
    }

    @DeleteMapping
    public void deleteSkill(@Valid @RequestParam String name, Authentication authentication) {
        skillsService.deleteSkill(authentication.getName(), name);
    }
}
