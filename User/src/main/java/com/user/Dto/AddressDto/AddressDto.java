package com.user.Dto.AddressDto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressDto {

    private Long id;
    private Long userId;
    private String city;
    private String country;
    private String postcode;
    private String nearByPlace;
}
