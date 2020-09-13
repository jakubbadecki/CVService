package com.bjss.cvservice.cv.entity.dao;

import com.bjss.cvservice.cv.entity.dto.CompanyHistoryDTO;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
public class CompanyHistory implements Serializable {
    private static final long serialVersionUID = -6918097260515358459L;

    @Id
    private String id;
    private String companyName;
    private String jobTitle;
    private Date startDate;
    private Date endDate;
    private String description;

    @Builder
    public CompanyHistory(String companyName, String jobTitle, Date startDate, Date endDate, String description) {
        this.companyName = companyName;
        this.jobTitle = jobTitle;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
    }

    public static CompanyHistory fromDTO(CompanyHistoryDTO companyHistoryDTO) {
        return CompanyHistory.builder()
                .companyName(companyHistoryDTO.getCompanyName())
                .description(companyHistoryDTO.getDescription())
                .endDate(companyHistoryDTO.getEndDate())
                .jobTitle(companyHistoryDTO.getJobTitle())
                .startDate(companyHistoryDTO.getStartDate())
                .build();
    }
}
