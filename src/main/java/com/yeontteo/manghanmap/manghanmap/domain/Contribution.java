package com.yeontteo.manghanmap.manghanmap.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "contribution")
public class Contribution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "university_id")
    private University university;

    private Integer progress;       // 진도율 0~100
    private Double sleepHours;      // 수면시간
    private Boolean isCrammer;      // 벼락치기 여부
    private Integer examFear;       // 공포지수 0~10

    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}