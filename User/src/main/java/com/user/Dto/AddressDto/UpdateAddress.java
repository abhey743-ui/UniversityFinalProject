package com.user.Dto.AddressDto;


import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateAddress {
    private Long id;
    private String city;
    private String country;
    private String postcode;
    private String nearByPlace;
}
