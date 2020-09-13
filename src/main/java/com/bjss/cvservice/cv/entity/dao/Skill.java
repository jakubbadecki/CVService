package com.bjss.cvservice.cv.entity.dao;

import com.bjss.cvservice.cv.entity.dto.SkillDTO;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class Skill implements Serializable {
    private static final long serialVersionUID = -4116492810704288648L;

    @Id
    private String id;
    private String name;
    private String description;

    @Builder
    public Skill(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public static Skill fromDTO(SkillDTO skillDTO) {
        return Skill.builder()
                .description(skillDTO.getDescription())
                .name(skillDTO.getName())
                .build();
    }
}
