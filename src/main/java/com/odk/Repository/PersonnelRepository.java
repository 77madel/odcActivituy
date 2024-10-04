package com.odk.Repository;

import com.odk.Entity.Participant;
import com.odk.Entity.Personnel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PersonnelRepository extends JpaRepository<Personnel, Long> {

    @Query("SELECT u FROM Utilisateur u JOIN u.role r WHERE r.nom = 'Personnel'")
    List<Personnel> findPersonnels();
}
