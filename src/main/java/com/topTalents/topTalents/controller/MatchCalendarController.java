package com.topTalents.topTalents.controller;

import com.topTalents.topTalents.data.dto.MatchCalendarDTO;
import com.topTalents.topTalents.service.serviceImpl.MatchCalendarServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/match-calendars")
public class MatchCalendarController {
    private final MatchCalendarServiceImpl matchCalendarService;

    @Autowired
    public MatchCalendarController(MatchCalendarServiceImpl matchCalendarService) {
        this.matchCalendarService = matchCalendarService;
    }

    @PostMapping
    public ResponseEntity<MatchCalendarDTO> createMatchCalendar(@Valid @RequestBody MatchCalendarDTO dto) {
        MatchCalendarDTO created = matchCalendarService.createMatchCalendar(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MatchCalendarDTO> updateMatchCalendar(@PathVariable Long id,
                                                                @Valid @RequestBody MatchCalendarDTO dto) {
        MatchCalendarDTO updated = matchCalendarService.updateMatchCalendar(id, dto);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MatchCalendarDTO> getMatchCalendarById(@PathVariable Long id) {
        MatchCalendarDTO dto = matchCalendarService.getMatchCalendarById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<MatchCalendarDTO>> getAllMatchCalendars() {
        List<MatchCalendarDTO> dtos = matchCalendarService.getAllMatchCalendars();
        return ResponseEntity.ok(dtos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMatchCalendar(@PathVariable Long id) {
        matchCalendarService.deleteMatchCalendar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<MatchCalendarDTO>> getMatchCalendarsByDateRange(
            @RequestParam("start") String start,
            @RequestParam("end") String end) {
        LocalDateTime startDate = LocalDateTime.parse(start);
        LocalDateTime endDate = LocalDateTime.parse(end);
        List<MatchCalendarDTO> dtos = matchCalendarService.getMatchCalendarsByDateRange(startDate, endDate);
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/by-team/{teamId}")
    public ResponseEntity<List<MatchCalendarDTO>> getMatchCalendarsByTeam(@PathVariable Long teamId) {
        List<MatchCalendarDTO> dtos = matchCalendarService.getMatchCalendarsByTeam(teamId);
        return ResponseEntity.ok(dtos);
    }
}
