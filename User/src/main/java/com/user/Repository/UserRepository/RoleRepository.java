package com.user.Repository.UserRepository;
import com.user.Entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RoleRepository extends JpaRepository<Roles,Long> {


    @Query("""
    select r.roleName
    from Roles r
    join r.userRoles u
    where u.userId.id = :userId
""")
    List<String> getRoles(@Param("userId") Long userId);



}
