package com.bjss.cvservice.cv.entity.dao;

import com.bjss.cvservice.cv.entity.dto.AddressDTO;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class Address implements Serializable {
    private static final long serialVersionUID = 6330792583459556188L;

    @Id
    private String id;
    private Integer streetNumber;
    private String street;
    private String city;
    private String postCode;

    @Builder
    public Address(Integer streetNumber, String street, String city, String postCode) {
        this.streetNumber = streetNumber;
        this.street = street;
        this.city = city;
        this.postCode = postCode;
    }

    public static Address fromDTO(AddressDTO addressDTO) {
        return Address.builder()
                .city(addressDTO.getCity())
                .street(addressDTO.getStreet())
                .postCode(addressDTO.getPostCode())
                .streetNumber(addressDTO.getStreetNumber())
                .build();
    }
}
