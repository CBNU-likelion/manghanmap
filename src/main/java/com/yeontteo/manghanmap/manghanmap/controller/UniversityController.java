package com.yeontteo.manghanmap.manghanmap.controller;

import com.yeontteo.manghanmap.manghanmap.domain.University;
import com.yeontteo.manghanmap.manghanmap.service.ContributionService;
import com.yeontteo.manghanmap.manghanmap.service.UniversityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/universities")
@RequiredArgsConstructor
public class UniversityController {

    private final UniversityService universityService;
    private final ContributionService contributionService;

    @GetMapping
    public ResponseEntity<List<University>> getUniversities(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String province) {
        return ResponseEntity.ok(universityService.search(q, province));
    }

    @GetMapping("/{id}")
    public ResponseEntity<University> getUniversity(@PathVariable Long id) {
        return ResponseEntity.ok(universityService.getById(id));
    }

    @GetMapping("/{id}/stats")
    public ResponseEntity<Map<String, Object>> getStats(@PathVariable Long id) {
        University university = universityService.getById(id);
        return ResponseEntity.ok(contributionService.getStats(university));
    }
}