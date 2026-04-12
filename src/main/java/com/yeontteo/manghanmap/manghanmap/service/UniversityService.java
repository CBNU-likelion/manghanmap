package com.yeontteo.manghanmap.manghanmap.service;

import com.yeontteo.manghanmap.manghanmap.dto.UniversityResponse;
import com.yeontteo.manghanmap.manghanmap.domain.University;
import com.yeontteo.manghanmap.manghanmap.repository.UniversityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UniversityService {

    private final UniversityRepository universityRepository;
    private final ProvinceResolver provinceResolver;

    public List<University> getAll() {
        return universityRepository.findAll();
    }

    public List<University> search(String q, String province) {
        if (q != null) return universityRepository.findByNameContaining(q);
        if (province != null) {
            return universityRepository.findAll()
                    .stream()
                    .filter(university -> provinceResolver.matchesProvince(university, province))
                    .toList();
        }
        return universityRepository.findAll();
    }

    public University getById(Long id) {
        return universityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("대학교를 찾을 수 없어요"));
    }

    public UniversityResponse toResponse(University university) {
        return new UniversityResponse(
                university.getId(),
                university.getName(),
                provinceResolver.resolveProvince(university),
                university.getCurrentMood(),
                university.getLat(),
                university.getLng()
        );
    }
}