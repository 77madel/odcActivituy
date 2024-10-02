package com.odk.Service.Interface.Service;

import com.odk.Entity.EntiteOdc;
import com.odk.Repository.EntiteOdcRepository;
import com.odk.Service.Interface.CrudService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EntiteOdcService implements CrudService<EntiteOdc, Long> {

    private EntiteOdcRepository entiteOdcRepository;
    @Override
    public EntiteOdc add(EntiteOdc entiteOdc) {
        return entiteOdcRepository.save(entiteOdc);
    }

    @Override
    public List<EntiteOdc> List() {
        return entiteOdcRepository.findAll();
    }

    @Override
    public Optional<EntiteOdc> findById(Long id) {
        return entiteOdcRepository.findById(id);
    }

    @Override
    public EntiteOdc update(EntiteOdc entity, Long id) {
        Optional<EntiteOdc> entiteOdc = entiteOdcRepository.findById(id);
        if (entiteOdc.isPresent()) {
            return entiteOdcRepository.save(entity);
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        Optional<EntiteOdc> entiteOdc = entiteOdcRepository.findById(id);
        if (entiteOdc.isPresent()) {
            entiteOdcRepository.deleteById(id);
        }
    }
}
