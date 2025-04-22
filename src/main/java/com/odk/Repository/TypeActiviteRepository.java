package com.odk.Repository;

import com.odk.Entity.TypeActivite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TypeActiviteRepository extends JpaRepository<TypeActivite, Long> {
    List<TypeActivite> findByEntites_Id(Long entiteId);
}
