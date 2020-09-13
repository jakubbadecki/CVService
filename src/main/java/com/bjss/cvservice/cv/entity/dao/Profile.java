package com.bjss.cvservice.cv.entity.dao;

import com.bjss.cvservice.cv.entity.dto.ProfileDTO;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
public class Profile implements Serializable {
    private static final long serialVersionUID = 6255706317075168125L;

    @Id
    private String id;
    private String name;
    private String surname;
    private String jobTitle;
    private String email;
    private String phoneNumber;
    private Date dateOfBirth;
    private Address address;

    @Builder
    public Profile(String name, String surname, String jobTitle, String email, String phoneNumber, Date dateOfBirth, Address address) {
        this.name = name;
        this.surname = surname;
        this.jobTitle = jobTitle;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
    }

    public static Profile fromDTO(ProfileDTO profileDTO) {
        return Profile.builder()
                .address(Address.fromDTO(profileDTO.getAddress()))
                .dateOfBirth(profileDTO.getDateOfBirth())
                .email(profileDTO.getEmail())
                .jobTitle(profileDTO.getJobTitle())
                .name(profileDTO.getName())
                .phoneNumber(profileDTO.getPhoneNumber())
                .surname(profileDTO.getSurname())
                .build();
    }
}
