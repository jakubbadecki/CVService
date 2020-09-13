package com.bjss.cvservice.cv.entity.dto;

import com.bjss.cvservice.cv.entity.dao.CompanyHistory;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class CompanyHistoryDTO {
    private String companyName;
    private String jobTitle;
    private Date startDate;
    private Date endDate;
    private String description;

    @Builder
    public CompanyHistoryDTO(String companyName, String jobTitle, Date startDate, Date endDate, String description) {
        this.companyName = companyName;
        this.jobTitle = jobTitle;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
    }

    public static CompanyHistoryDTO fromDAO(CompanyHistory companyHistory) {
        return CompanyHistoryDTO.builder()
                .companyName(companyHistory.getCompanyName())
                .description(companyHistory.getDescription())
                .endDate(companyHistory.getEndDate())
                .jobTitle(companyHistory.getJobTitle())
                .startDate(companyHistory.getStartDate())
                .build();
    }
}
