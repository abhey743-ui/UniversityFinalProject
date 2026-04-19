package com.user.Entity;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userID",nullable = false)
    private User userId;

    @Column(nullable = false,name = "city")
    private String city;

    @Column(nullable = false,name = "postCode")
    private String postcode;

    @Column(nullable = false,name = "country")
    private String country;

    @Column(name = "nearByPlace")
    private String nearByPlace;


}
