package com.odk.Repository;

import com.odk.Entity.BlackList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface BlackListRepository extends JpaRepository<BlackList, Long> {
    Optional<BlackList> findByEmail(String email);
    Optional<BlackList> findByPhone(String phone);
}
