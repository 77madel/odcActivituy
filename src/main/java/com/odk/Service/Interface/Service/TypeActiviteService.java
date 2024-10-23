package com.odk.Service.Interface.Service;

import com.odk.Entity.TypeActivite;
import com.odk.Repository.TypeActiviteRepository;
import com.odk.Service.Interface.CrudService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TypeActiviteService implements CrudService<TypeActivite, Long> {

    private TypeActiviteRepository typeActiviteRepository;

    @Override
    public TypeActivite add(TypeActivite typeActivite) {
        return typeActiviteRepository.save(typeActivite);
    }

    public List<TypeActivite> addAll(List<TypeActivite> typeActivites) {
        return typeActiviteRepository.saveAll(typeActivites);
    }

    @Override
    public List<TypeActivite> List() {
        return typeActiviteRepository.findAll();
    }

    @Override
    public Optional<TypeActivite> findById(Long id) {
        return findById(id);
    }

    @Override
    public TypeActivite update(TypeActivite typeActivite, Long id) {
        return typeActiviteRepository.findById(id).map(
                p -> {
                    p.setType(typeActivite.getType());
                    return typeActiviteRepository.save(p);
                }).orElseThrow(()-> new RuntimeException("L'id n'existe pas"));
    }

    @Override
    public void delete(Long id) {
        TypeActivite typeActivite = typeActiviteRepository.findById(id).orElse(null);
        assert typeActivite != null;
        typeActiviteRepository.delete(typeActivite);

    }
}
