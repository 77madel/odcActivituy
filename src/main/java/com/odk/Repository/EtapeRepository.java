package com.odk.Repository;

import com.odk.Entity.Etape;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EtapeRepository extends JpaRepository<Etape, Long> {

    List<Etape> findEtapeById(Long id);
}
