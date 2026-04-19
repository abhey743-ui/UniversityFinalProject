package com.user.Dto.AddressDto;


import lombok.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddAddress {

    private Long id;

    @NotBlank(message = "city cannot be blank")
    private String city;

    @NotBlank(message = "country cannot be blank")
    private String country;

    @NotBlank(message = "postcode cannot be blank")
    private String postcode;

    private String nearByPlace;
}
