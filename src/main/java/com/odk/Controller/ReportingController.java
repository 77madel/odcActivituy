package com.odk.Controller;

import com.odk.Entity.StatistiqueGenre;
import com.odk.Service.Interface.Service.ActiviteParticipantService;
import com.odk.Service.Interface.Service.ReportingService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reporting")
@AllArgsConstructor
public class ReportingController {

    private ReportingService reportingService;
    private ActiviteParticipantService activiteParticipantService;

    @GetMapping("/counts-by-genre")
    public Map<String, Long> getCountsByGenre() {
        return activiteParticipantService.getCountsByGenre();
    }

    /*@GetMapping("/participants-par-genre")
    public ResponseEntity<List<StatistiqueGenre>> StatistiquesParGenre() {
        List<StatistiqueGenre> stats = reportingService.StatistiquesParGenre();
        return ResponseEntity.ok(stats);
    }*/
    @GetMapping("/participants-par-genre")
    public ResponseEntity<List<StatistiqueGenre>> StatistiquesParGenre() {
        List<StatistiqueGenre> stats = reportingService.StatistiquesParGenre();
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/participant")
    public ResponseEntity<Map<String, Object>> getEtapeStatistics() {
        return ResponseEntity.ok(reportingService.getParticipantStatistics());
    }
}
