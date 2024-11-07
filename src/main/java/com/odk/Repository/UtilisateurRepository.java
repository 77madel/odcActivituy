package com.odk.Repository;

import com.odk.Entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {

    Optional<Utilisateur> findByEmail(String Email);
    @Query("SELECT u FROM Utilisateur u JOIN u.role r WHERE r.nom = 'Personnel'")
    List<Utilisateur> findByRoleNom(String roleName);

    long count();

}
