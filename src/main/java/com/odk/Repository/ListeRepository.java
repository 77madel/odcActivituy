package com.odk.Repository;

import com.odk.Entity.Liste;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ListeRepository  extends JpaRepository<Liste, Long> {

//    Optional<Liste> findById(Long id);
}
