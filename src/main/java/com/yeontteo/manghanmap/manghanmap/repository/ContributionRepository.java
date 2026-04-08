package com.yeontteo.manghanmap.manghanmap.repository;

import com.yeontteo.manghanmap.manghanmap.domain.Contribution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ContributionRepository extends JpaRepository<Contribution, Long> {
    List<Contribution> findByUniversityId(Long universityId);

    @Query("SELECT COUNT(c) FROM Contribution c WHERE c.university.id = :universityId")
    Long countByUniversityId(Long universityId);
}