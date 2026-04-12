package com.yeontteo.manghanmap.manghanmap.service;

import com.yeontteo.manghanmap.manghanmap.domain.Vibe;
import com.yeontteo.manghanmap.manghanmap.domain.University;
import com.yeontteo.manghanmap.manghanmap.repository.VibeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VibeService {

    private final VibeRepository vibeRepository;

    public List<Vibe> getVibes(Long universityId) {
        return vibeRepository.findByUniversityIdOrderByCreatedAtDesc(universityId);
    }

    public Vibe save(University university, String text) {
        Vibe vibe = new Vibe();
        vibe.setUniversity(university);
        vibe.setText(text);
        return vibeRepository.save(vibe);
    }
}