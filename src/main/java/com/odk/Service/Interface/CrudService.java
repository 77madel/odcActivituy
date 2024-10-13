package com.odk.Service.Interface;

import com.odk.Entity.Participant;

import java.util.List;
import java.util.Optional;

public interface CrudService<T, ID> {
    T add(T entity);

    List<T> List();
    Optional<T> findById(ID id);
    T update(T entity, ID id);
    void delete(ID id);
}
