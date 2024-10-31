package com.odk.Repository;


import com.odk.Entity.Entite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EntiteOdcRepository extends JpaRepository<Entite, Long> {

    @Query("SELECT COUNT(a) FROM Activite a WHERE a.entite.id = :entiteId")
    Long countActivitiesByEntiteId(@Param("entiteId") Long entiteId);

    long count();

}
