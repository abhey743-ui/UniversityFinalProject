package com.user.Entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCredentials{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private Long id;

    @Column(name = "userName",nullable = true)
    private String userName;

    @Column(name = "password",nullable = true)
    private String password;

    @Column(name = "providerId")
    private String providerId;

    private String providerName;


    @OneToOne
    private User userId;

}
