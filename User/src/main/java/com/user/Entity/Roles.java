package com.user.Entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Entity
@Getter
@Setter
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private Long id;

    @Column(name = "roleName",nullable = false)
    private String  roleName;

    @OneToMany(mappedBy = "roleId")
    private List<UserRole> userRoles;

    @OneToMany(mappedBy = "roleId")
    private List<RolePermission> rolePermissionList;

}
