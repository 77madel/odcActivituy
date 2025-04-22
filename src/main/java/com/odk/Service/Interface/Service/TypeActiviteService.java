package com.odk.Service.Interface.Service;

import com.odk.Entity.TypeActivite;
import com.odk.Repository.TypeActiviteRepository;
import com.odk.Service.Interface.CrudService;
import com.odk.dto.TypeActiviteDTO;
import com.odk.dto.TypeActiviteMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TypeActiviteService implements CrudService<TypeActivite, Long> {

    private TypeActiviteRepository typeActiviteRepository;
    private TypeActiviteMapper typeActiviteMapper;


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

    public List<TypeActiviteDTO> allList() {
        return typeActiviteRepository.findAll().stream()
                .map(typeActiviteMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<TypeActivite> findById(Long id) {
        return findById(id);
    }

    public List<TypeActiviteDTO> getByEntiteId(Long entiteId) {
        List<TypeActivite> types = typeActiviteRepository.findByEntites_Id(entiteId);
        return types.stream().map(typeActiviteMapper::toDTO).collect(Collectors.toList());
    }


    public Optional<TypeActiviteDTO> findParId(Long id) {
        Optional<TypeActivite> typeActiviteOptional = typeActiviteRepository.findById(id);
        return typeActiviteOptional.map(typeActiviteMapper::toDTO);
    }

    @Override
    public TypeActivite update(TypeActivite typeActivite, Long id) {
        return typeActiviteRepository.findById(id).map(
                p -> {
                    p.setType(typeActivite.getType());
                    return typeActiviteRepository.save(p);
                }).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "L'id n'existe pas"));
    }

    @Override
    public void delete(Long id) {
        TypeActivite typeActivite = typeActiviteRepository.findById(id).orElse(null);
        assert typeActivite != null;
        typeActiviteRepository.delete(typeActivite);

    }
}
