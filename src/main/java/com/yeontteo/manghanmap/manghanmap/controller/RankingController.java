package com.yeontteo.manghanmap.manghanmap.controller;

import com.yeontteo.manghanmap.manghanmap.domain.University;
import com.yeontteo.manghanmap.manghanmap.service.ContributionService;
import com.yeontteo.manghanmap.manghanmap.service.UniversityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/ranking")
@RequiredArgsConstructor
public class RankingController {

    private final UniversityService universityService;
    private final ContributionService contributionService;

    @GetMapping("/doom")
    public ResponseEntity<List<Map<String, Object>>> getDoomRanking(
            @RequestParam(defaultValue = "5") int limit) {

        List<University> all = universityService.getAll();

        List<Map<String, Object>> ranking = all.stream()
                .map(u -> {
                    Map<String, Object> stats = contributionService.getStats(u);
                    Map<String, Object> item = new HashMap<>();
                    item.put("universityId", u.getId());
                    item.put("universityName", u.getName());
                    item.put("doomScore", stats.get("doomScore"));
                    return item;
                })
                .sorted((a, b) -> Double.compare(
                        (double) b.get("doomScore"),
                        (double) a.get("doomScore")))
                .limit(limit)
                .collect(Collectors.toList());

        for (int i = 0; i < ranking.size(); i++) {
            ranking.get(i).put("rank", i + 1);
        }

        return ResponseEntity.ok(ranking);
    }
}