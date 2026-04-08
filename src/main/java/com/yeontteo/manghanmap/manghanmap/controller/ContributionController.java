package com.yeontteo.manghanmap.manghanmap.controller;

import com.yeontteo.manghanmap.manghanmap.domain.University;
import com.yeontteo.manghanmap.manghanmap.service.ContributionService;
import com.yeontteo.manghanmap.manghanmap.service.UniversityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/universities")
@RequiredArgsConstructor
public class ContributionController {

    private final UniversityService universityService;
    private final ContributionService contributionService;

    @PostMapping("/{id}/contribute")
    public ResponseEntity<Map<String, Object>> contribute(
            @PathVariable Long id,
            @RequestBody Map<String, Object> body) {

        University university = universityService.getById(id);

        int progress      = (int) body.get("progress");
        double sleepHours = ((Number) body.get("sleepHours")).doubleValue();
        boolean isCrammer = (boolean) body.get("isCrammer");
        int examFear      = (int) body.get("examFear");

        contributionService.save(university, progress, sleepHours, isCrammer, examFear);
        Map<String, Object> updatedStats = contributionService.getStats(university);

        return ResponseEntity.status(201).body(Map.of(
                "message", "기여 완료",
                "updatedStats", updatedStats
        ));
    }
}