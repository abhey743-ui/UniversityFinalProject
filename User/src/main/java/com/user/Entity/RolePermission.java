package com.user.Entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class RolePermission {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name ="roleId",nullable = false )
    private Roles roleId;

    @ManyToOne
    @JoinColumn(name = "permissionId",nullable = false)
    private Permission permissionId;



}
