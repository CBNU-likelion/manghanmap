package com.yeontteo.manghanmap.manghanmap.controller;

import com.yeontteo.manghanmap.manghanmap.domain.University;
import com.yeontteo.manghanmap.manghanmap.repository.UniversityRepository;
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
    private final UniversityRepository universityRepository;

    @PostMapping("/{id}/contribute")
    public ResponseEntity<Map<String, Object>> contribute(
            @PathVariable Long id,
            @RequestBody Map<String, Object> body) {
        return saveContribution(id, body);
    }

    @PostMapping("/contribute")
    public ResponseEntity<Map<String, Object>> contributeLegacy(
            @RequestBody Map<String, Object> body) {
        Long universityId = ((Number) body.get("universityId")).longValue();
        return saveContribution(universityId, body);
    }

    private ResponseEntity<Map<String, Object>> saveContribution(Long universityId, Map<String, Object> body) {
        University university = universityService.getById(universityId);

        int progress      = ((Number) body.get("progress")).intValue();
        double sleepHours = ((Number) body.get("sleepHours")).doubleValue();
        boolean isCrammer = (boolean) body.get("isCrammer");
        int examFear      = ((Number) body.get("examFear")).intValue();
        Object moodValue = body.get("currentMood");
        String currentMood = moodValue instanceof String ? ((String) moodValue).trim() : "";

        contributionService.save(university, progress, sleepHours, isCrammer, examFear);
        if (!currentMood.isEmpty()) {
            university.setCurrentMood(currentMood);
            universityRepository.save(university);
        }
        Map<String, Object> updatedStats = contributionService.getStats(university);

        return ResponseEntity.status(201).body(Map.of(
                "message", "기여 완료",
                "updatedStats", updatedStats
        ));
    }
}