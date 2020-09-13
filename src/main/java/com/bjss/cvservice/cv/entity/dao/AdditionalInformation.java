package com.bjss.cvservice.cv.entity.dao;

import com.bjss.cvservice.cv.entity.dto.AdditionalInformationDTO;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
public class AdditionalInformation implements Serializable {
    private static final long serialVersionUID = 4365126034772753689L;

    @Id
    private String id;

    private String name;
    private String description;
    private Type type;
    private Date date;

    @Builder
    public AdditionalInformation(String name, String description, Type type, Date date) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.date = date;
    }

    public static AdditionalInformation fromDTO(AdditionalInformationDTO additionalInformationDTO) {
        return AdditionalInformation.builder()
                .date(additionalInformationDTO.getDate())
                .description(additionalInformationDTO.getDescription())
                .name(additionalInformationDTO.getName())
                .type(Type.valueOf(additionalInformationDTO.getType().name()))
                .build();
    }

    @Getter
    public enum Type {
        CERTIFICATE,
        COURSE,
        OTHER
    }
}
