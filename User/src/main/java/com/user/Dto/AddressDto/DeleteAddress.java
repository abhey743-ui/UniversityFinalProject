package com.user.Dto.AddressDto;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeleteAddress {
    private Long id;
    private String city;
    private String country;
    private String postcode;
    private String nearByPlace;
}
