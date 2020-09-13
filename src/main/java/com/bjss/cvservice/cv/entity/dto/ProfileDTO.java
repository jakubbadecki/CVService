package com.bjss.cvservice.cv.entity.dto;

import com.bjss.cvservice.cv.entity.dao.Profile;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class ProfileDTO {
    private String name;
    private String surname;
    private String jobTitle;
    private String email;
    private String phoneNumber;
    private Date dateOfBirth;
    private AddressDTO address;

    @Builder
    public ProfileDTO(String name, String surname, String jobTitle, String email, String phoneNumber, Date dateOfBirth, AddressDTO address) {
        this.name = name;
        this.surname = surname;
        this.jobTitle = jobTitle;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
    }

    public static ProfileDTO fromDAO(Profile profile) {
        return ProfileDTO.builder()
                .address(AddressDTO.fromDAO(profile.getAddress()))
                .dateOfBirth(profile.getDateOfBirth())
                .email(profile.getEmail())
                .jobTitle(profile.getJobTitle())
                .name(profile.getName())
                .phoneNumber(profile.getPhoneNumber())
                .surname(profile.getSurname())
                .build();
    }
}
