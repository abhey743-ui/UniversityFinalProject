package com.user.Entity;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "UserInfo")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private Long id;

    @Column(name = "firstName",nullable = false)
    public String FirstName;

    @Column(name="lastName")
    private String LastName;

    @Column(name="mobileNumber")
    private String mobileNumber;

    @OneToMany(mappedBy = "userId")
    private List<Address> Address;

    @OneToMany(mappedBy = "userId")
    private List<UserRole> userRoles;


}
