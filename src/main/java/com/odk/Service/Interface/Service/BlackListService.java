package com.odk.Service.Interface.Service;

import com.odk.Entity.BlackList;
import com.odk.Repository.BlackListRepository;
import com.odk.Service.Interface.CrudService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BlackListService implements CrudService<BlackList,Long> {

    private BlackListRepository blackListRepository;

    @Override
    public BlackList add(BlackList blackList) {
        return blackListRepository.save(blackList);
    }

    @Override
    public List<BlackList> List() {
        return blackListRepository.findAll();
    }

    @Override
    public Optional<BlackList> findById(Long id) {
        return blackListRepository.findById(id);
    }

    @Override
    public BlackList update(BlackList blackList, Long id) {
        return blackListRepository.findById(id).map(
                p -> {
                    p.setNom(blackList.getNom());
                    p.setEmail(blackList.getEmail());
                    p.setPrenom(blackList.getPrenom());
                    p.setPhone(blackList.getPhone());
                    return blackListRepository.save(p);
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Votre id n'existe pas"));

    }

    @Override
    public void delete(Long id) {
       Optional<BlackList> blackListOptional = blackListRepository.findById(id);
       blackListOptional.ifPresent(blackListRepository::delete);

    }

    public boolean isParticipantBlacklisted(String email, String phone) {
        boolean isEmailBlacklisted = blackListRepository.findByEmail(email).isPresent();
        boolean isPhoneBlacklisted = blackListRepository.findByPhone(phone).isPresent();
        return isEmailBlacklisted || isPhoneBlacklisted;
    }
}
