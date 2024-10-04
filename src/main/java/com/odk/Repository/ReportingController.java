package com.odk.Repository;

import com.odk.Entity.StatistiqueGenre;
import com.odk.Service.Interface.Service.ReportingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reporting")
public class ReportingController {

    @Autowired
    private ReportingService reportingService;

    @GetMapping("/participants-par-genre")
    public ResponseEntity<List<StatistiqueGenre>> obtenirStatistiquesParGenre() {
        List<StatistiqueGenre> stats = reportingService.obtenirStatistiquesParGenre();
        return ResponseEntity.ok(stats);
    }
}
