package com.yeontteo.manghanmap.manghanmap.service;

import com.yeontteo.manghanmap.manghanmap.domain.Contribution;
import com.yeontteo.manghanmap.manghanmap.domain.University;
import com.yeontteo.manghanmap.manghanmap.repository.ContributionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class ContributionService {

    private final ContributionRepository contributionRepository;

    public Map<String, Object> getStats(University university) {
        List<Contribution> list = contributionRepository.findByUniversityId(university.getId());

        if (list.isEmpty()) {
            return Map.of(
                    "universityId", university.getId(),
                    "contributionCount", 0,
                    "avgProgress", 0.0,
                    "avgSleepHours", 0.0,
                    "crammerCount", 0,
                    "avgExamFear", 0.0,
                    "doomScore", 0.0
            );
        }

        double avgProgress   = list.stream().mapToInt(Contribution::getProgress).average().orElse(0);
        double avgSleep      = list.stream().mapToDouble(Contribution::getSleepHours).average().orElse(0);
        long crammerCount    = list.stream().filter(c -> Boolean.TRUE.equals(c.getIsCrammer())).count();
        double avgExamFear   = list.stream().mapToInt(Contribution::getExamFear).average().orElse(0);
        double doomScore     = calcDoomScore(avgProgress, avgSleep, crammerCount, list.size(), avgExamFear);

        Map<String, Object> result = new HashMap<>();
        result.put("universityId", university.getId());
        result.put("contributionCount", list.size());
        result.put("avgProgress", Math.round(avgProgress * 10.0) / 10.0);
        result.put("avgSleepHours", Math.round(avgSleep * 10.0) / 10.0);
        result.put("crammerCount", crammerCount);
        result.put("avgExamFear", Math.round(avgExamFear * 10.0) / 10.0);
        result.put("doomScore", Math.round(doomScore * 10.0) / 10.0);
        return result;
    }

    private double calcDoomScore(double avgProgress, double avgSleep,
                                 long crammerCount, int total, double avgExamFear) {
        double progressScore = (100 - avgProgress) * 0.30;
        double sleepScore    = Math.max(8 - avgSleep, 0) * 10 * 0.25;
        double crammerScore  = ((double) crammerCount / total) * 100 * 0.25;
        double fearScore     = avgExamFear * 10 * 0.20;
        return progressScore + sleepScore + crammerScore + fearScore;
    }

    public Contribution save(University university, int progress, double sleepHours,
                             boolean isCrammer, int examFear) {
        Contribution c = new Contribution();
        c.setUniversity(university);
        c.setProgress(progress);
        c.setSleepHours(sleepHours);
        c.setIsCrammer(isCrammer);
        c.setExamFear(examFear);
        return contributionRepository.save(c);
    }
}