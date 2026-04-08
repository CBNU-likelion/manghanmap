package com.yeontteo.manghanmap.manghanmap.repository;

import com.yeontteo.manghanmap.manghanmap.domain.University;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UniversityRepository extends JpaRepository<University, Long> {
    List<University> findByNameContaining(String name);
    List<University> findByProvince(String province);
}