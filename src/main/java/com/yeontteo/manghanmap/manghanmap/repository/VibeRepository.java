package com.yeontteo.manghanmap.manghanmap.repository;

import com.yeontteo.manghanmap.manghanmap.domain.Vibe;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VibeRepository extends JpaRepository<Vibe, Long> {
    List<Vibe> findByUniversityIdOrderByCreatedAtAsc(Long universityId);
}