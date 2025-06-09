package com.topTalents.topTalents.controller;

import com.topTalents.topTalents.data.dto.SystemStats;
import com.topTalents.topTalents.service.StatsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminStatsController {

    private final StatsService statsService;

    public AdminStatsController(StatsService statsService) {
        this.statsService = statsService;
    }

    @GetMapping("/stats")
    public ResponseEntity<SystemStats> getSystemStats() {
        return ResponseEntity.ok(statsService.getSystemStats());
    }
}
