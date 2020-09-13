package com.bjss.cvservice.cv.entity.dto;

import com.bjss.cvservice.cv.entity.dao.Address;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddressDTO {
    private Integer streetNumber;
    private String street;
    private String city;
    private String postCode;

    @Builder
    public AddressDTO(Integer streetNumber, String street, String city, String postCode) {
        this.streetNumber = streetNumber;
        this.street = street;
        this.city = city;
        this.postCode = postCode;
    }

    public static AddressDTO fromDAO(Address address) {
        return AddressDTO.builder()
                .street(address.getStreet())
                .city(address.getCity())
                .postCode(address.getPostCode())
                .streetNumber(address.getStreetNumber())
                .build();
    }
}
