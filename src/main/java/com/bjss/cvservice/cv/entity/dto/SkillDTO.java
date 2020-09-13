package com.bjss.cvservice.cv.entity.dto;

import com.bjss.cvservice.cv.entity.dao.Skill;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SkillDTO {
    private String name;
    private String description;

    @Builder
    public SkillDTO(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public static SkillDTO fromDAO(Skill skill) {
        return SkillDTO.builder()
                .description(skill.getDescription())
                .name(skill.getName())
                .build();
    }
}
