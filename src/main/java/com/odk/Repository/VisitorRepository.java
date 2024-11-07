package com.odk.Repository;

import com.odk.Entity.visitor.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitorRepository extends JpaRepository<Visitor, Integer> {
}
