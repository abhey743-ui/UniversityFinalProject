package com.user.Entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userId",nullable = false)
    private User userId;

    @ManyToOne
    @JoinColumn(name = "role",nullable = false)
    private Roles roleId;

}
