package com.odk.Repository;

import com.odk.Entity.Participant;
import com.odk.Entity.Vigile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface VigileRepository extends CrudRepository<Vigile, Long> {

    @Query("SELECT u FROM Utilisateur u JOIN u.role r WHERE r.nom = 'Vigile'")
    List<Vigile> findVigiles();
}
