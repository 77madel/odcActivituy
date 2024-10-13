package com.odk.Repository;

import com.odk.Entity.Activite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ActiviteRepository extends JpaRepository<Activite, Long> {


   Optional<Activite> findByNom(String nom);
}
