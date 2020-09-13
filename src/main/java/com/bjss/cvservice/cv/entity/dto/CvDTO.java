package com.bjss.cvservice.cv.entity.dto;

import com.bjss.cvservice.cv.entity.dao.Cv;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class CvDTO {
    private ProfileDTO profile;
    private List<CompanyHistoryDTO> companyHistoryList;
    private List<SkillDTO> skills;
    private List<AdditionalInformationDTO> additionalInformationList;

    @Builder
    public CvDTO(ProfileDTO profile, List<CompanyHistoryDTO> companyHistoryList, List<SkillDTO> skills,
                 List<AdditionalInformationDTO> additionalInformationList) {
        this.profile = profile;
        this.companyHistoryList = companyHistoryList;
        this.skills = skills;
        this.additionalInformationList = additionalInformationList;
    }

    public static CvDTO fromDAO(Cv cv) {
        return CvDTO.builder()
                .additionalInformationList(cv.getAdditionalInformationList().stream()
                        .map(AdditionalInformationDTO::fromDAO)
                        .collect(Collectors.toList()))
                .companyHistoryList(cv.getCompanyHistoryList().stream()
                        .map(CompanyHistoryDTO::fromDAO)
                        .collect(Collectors.toList()))
                .profile(Optional.ofNullable(cv.getProfile())
                        .map(ProfileDTO::fromDAO)
                        .orElse(null))
                .skills(cv.getSkills().stream()
                        .map(SkillDTO::fromDAO)
                        .collect(Collectors.toList()))
                .build();
    }
}
