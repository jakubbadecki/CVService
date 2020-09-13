package com.bjss.cvservice.cv.entity.dto;

import com.bjss.cvservice.cv.entity.dao.AdditionalInformation;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class AdditionalInformationDTO {
    private String name;
    private String description;
    private Type type;
    private Date date;

    @Builder
    public AdditionalInformationDTO(String name, String description, Type type, Date date) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.date = date;
    }

    public static AdditionalInformationDTO fromDAO(AdditionalInformation additionalInformation) {
        return AdditionalInformationDTO.builder()
                .date(additionalInformation.getDate())
                .description(additionalInformation.getDescription())
                .name(additionalInformation.getName())
                .type(Type.valueOf(additionalInformation.getType().name()))
                .build();
    }

    @Getter
    public enum Type {
        CERTIFICATE,
        COURSE,
        OTHER
    }
}
