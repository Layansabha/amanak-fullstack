package com.example.amanakk_backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Date;


@RestController
@RequestMapping("/api/v1/cases")
public class CaseController {

    @Autowired
    private CaseService caseService;

    @PostMapping
    public ResponseEntity<Case> submitCase(@RequestBody Case newCase) {
        newCase.setReportedAt(new Date()); // Set the reporting date to current date
        newCase.setStatus("Report Submitted"); // A new case has completed only the submission step.
        Case savedCase = caseService.saveCase(newCase);
        return ResponseEntity.ok(savedCase);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Case> getCase(@PathVariable Long id) {
        return caseService.getCaseById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
