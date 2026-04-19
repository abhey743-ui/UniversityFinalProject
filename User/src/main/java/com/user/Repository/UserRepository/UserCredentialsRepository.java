package com.user.Repository.UserRepository;

import com.user.Entity.UserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCredentialsRepository extends JpaRepository<UserCredentials,Long> {
}
