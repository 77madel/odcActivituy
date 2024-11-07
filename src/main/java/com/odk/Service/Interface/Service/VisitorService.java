package com.odk.Service.Interface.Service;

import com.odk.Entity.visitor.Visitor;
import com.odk.Repository.VisitorRepository;
import org.springframework.stereotype.Service;

@Service
public class VisitorService {

    public VisitorRepository visitorRepository;

    public VisitorService(VisitorRepository visitorRepository) {
        this.visitorRepository = visitorRepository;
    }

    public void saveVisitorInfo(Visitor visitor) {
        visitorRepository.save(visitor);
    }

}
