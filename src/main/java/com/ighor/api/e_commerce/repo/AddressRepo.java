package com.ighor.api.e_commerce.repo;

import com.ighor.api.e_commerce.model.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepo extends JpaRepository<Address, Long> {
    List<Address> findByUserId(Long userId);
}
