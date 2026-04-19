package com.user.Repository.UserRepository;
import com.user.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository  extends JpaRepository<User,String> {

    User findAllById(Long id);

    Optional<User> findById(Long id);
}
