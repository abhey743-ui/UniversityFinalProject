package com.user.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private Long id;

    @Column(name = "permissionName",nullable = false)
    private String permissionName;

    @OneToMany(mappedBy = "permissionId")
    private List<RolePermission> rolePermissionList;

}
