package com.yeontteo.manghanmap.manghanmap.dto;

public record UniversityResponse(
        Long id,
        String name,
        String province,
        String currentMood,
        Double lat,
        Double lng
) {
}
