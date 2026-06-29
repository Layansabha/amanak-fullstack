package com.example.amanakk_backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class CaseService {

    @Autowired
    private CaseRepository caseRepository;

    public Case saveCase(Case newCase) {
        return caseRepository.save(newCase);
    }

    public Optional<Case> getCaseById(Long id) {
        return caseRepository.findById(id);
    }

    public void deleteCase(Long id) {
        caseRepository.deleteById(id);
    }

    public Iterable<Case> getAllCases() {
        return caseRepository.findAll();
    }
}
