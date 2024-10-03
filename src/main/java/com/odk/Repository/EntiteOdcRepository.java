package com.odk.Repository;


import com.odk.Entity.Entite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntiteOdcRepository extends JpaRepository<Entite, Long> {

}
