package com.odk.Controller;

import com.odk.Entity.StatistiqueGenre;
import com.odk.Service.Interface.Service.ReportingService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reporting")
@AllArgsConstructor
public class ReportingController {

    private ReportingService reportingService;

    @GetMapping("/participants-par-genre")
    public ResponseEntity<List<StatistiqueGenre>> StatistiquesParGenre() {
        List<StatistiqueGenre> stats = reportingService.StatistiquesParGenre();
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/participants-par-genre-homme")
    public ResponseEntity<List<StatistiqueGenre>> StatistiquesParGenreHomme() {
        List<StatistiqueGenre> stats = reportingService.StatistiquesParGenreHomme();
        return ResponseEntity.ok(stats);
    }
}
