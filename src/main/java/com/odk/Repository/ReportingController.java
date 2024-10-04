package com.odk.Repository;

import com.odk.Entity.StatistiqueGenre;
import com.odk.Service.Interface.Service.ReportingService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reporting")
@AllArgsConstructor
public class ReportingController {

    private ReportingService reportingService;

    @GetMapping("/participants-par-genre")
    public ResponseEntity<List<StatistiqueGenre>> StatistiquesParGenre() {
        List<StatistiqueGenre> stats = reportingService.StatistiquesParGenre();
        return ResponseEntity.ok(stats);
    }
}
