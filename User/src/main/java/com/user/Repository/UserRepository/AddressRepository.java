package com.user.Repository.UserRepository;

import com.user.Entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address,Long> {

    List<Address> findByUserId_Id(Long id);

    Address findByUserId_IdAndId(Long id, Long id1);
}
