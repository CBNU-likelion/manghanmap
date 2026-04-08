package com.yeontteo.manghanmap.manghanmap.controller;

import com.yeontteo.manghanmap.manghanmap.domain.University;
import com.yeontteo.manghanmap.manghanmap.domain.Vibe;
import com.yeontteo.manghanmap.manghanmap.service.UniversityService;
import com.yeontteo.manghanmap.manghanmap.service.VibeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/universities")
@RequiredArgsConstructor
public class VibeController {

    private final UniversityService universityService;
    private final VibeService vibeService;

    @GetMapping("/{id}/vibes")
    public ResponseEntity<List<Vibe>> getVibes(@PathVariable Long id) {
        return ResponseEntity.ok(vibeService.getVibes(id));
    }

    @PostMapping("/{id}/vibes")
    public ResponseEntity<Vibe> addVibe(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {

        String text = body.get("text");
        if (text == null || text.isBlank() || text.length() > 50) {
            return ResponseEntity.badRequest().build();
        }

        University university = universityService.getById(id);
        return ResponseEntity.status(201).body(vibeService.save(university, text));
    }
}