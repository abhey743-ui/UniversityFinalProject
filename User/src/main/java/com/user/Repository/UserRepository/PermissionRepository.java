package com.user.Repository.UserRepository;

import com.user.Entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface   PermissionRepository extends JpaRepository<Permission,Long> {

    @Query("""
    select rp.permissionId.permissionName
    from Roles r
    join r.userRoles ur
    join r.rolePermissionList rp
    where ur.userId.id = :userId
""")
    List<String> getPermission(@Param("userId") Long userId);



}

