package com.bjss.cvservice.cv.entity.dao;

import com.bjss.cvservice.cv.entity.dto.CvDTO;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class Cv implements Serializable {
    private static final long serialVersionUID = 2831434281013890289L;

    @Id
    private String id;
    private Profile profile;
    private List<CompanyHistory> companyHistoryList = Collections.emptyList();
    private List<Skill> skills = Collections.emptyList();
    private List<AdditionalInformation> additionalInformationList = Collections.emptyList();

    @Builder
    public Cv(Profile profile, List<CompanyHistory> companyHistoryList, List<Skill> skills, List<AdditionalInformation> additionalInformationList) {
        this.profile = profile;
        this.companyHistoryList = companyHistoryList;
        this.skills = skills;
        this.additionalInformationList = additionalInformationList;
    }

    public static Cv fromDTO(CvDTO cvDTO) {
        return Cv.builder()
                .additionalInformationList(cvDTO.getAdditionalInformationList().stream()
                        .map(AdditionalInformation::fromDTO)
                        .collect(Collectors.toList()))
                .companyHistoryList(cvDTO.getCompanyHistoryList().stream()
                        .map(CompanyHistory::fromDTO)
                        .collect(Collectors.toList()))
                .profile(Profile.fromDTO(cvDTO.getProfile()))
                .skills(cvDTO.getSkills().stream()
                        .map(Skill::fromDTO)
                        .collect(Collectors.toList()))
                .build();
    }
}
