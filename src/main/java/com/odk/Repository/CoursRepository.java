package com.odk.Repository;

import com.odk.Controller.CoursController;
import com.odk.Entity.Cours;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoursRepository extends JpaRepository<Cours, Long> {

}
