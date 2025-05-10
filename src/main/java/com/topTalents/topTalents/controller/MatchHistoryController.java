package com.topTalents.topTalents.controller;

import com.topTalents.topTalents.data.dto.MatchHistoryDTO;
import com.topTalents.topTalents.service.serviceImpl.MatchHistoryServiceImpl;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/match-history")
public class MatchHistoryController {

    private final MatchHistoryServiceImpl matchHistoryService;

    @Autowired
    public MatchHistoryController(MatchHistoryServiceImpl matchHistoryService) {
        this.matchHistoryService = matchHistoryService;
    }

    @PostMapping
    public ResponseEntity<MatchHistoryDTO> createMatchHistory(@Valid @RequestBody MatchHistoryDTO dto) {
        MatchHistoryDTO created = matchHistoryService.createMatchHistory(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MatchHistoryDTO> updateMatchHistory(@PathVariable Long id,
                                                              @Valid @RequestBody MatchHistoryDTO dto) {
        MatchHistoryDTO updated = matchHistoryService.updateMatchHistory(id, dto);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MatchHistoryDTO> getMatchHistoryById(@PathVariable Long id) {
        MatchHistoryDTO dto = matchHistoryService.getMatchHistoryById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<MatchHistoryDTO>> getAllMatchHistory() {
        List<MatchHistoryDTO> dtos = matchHistoryService.getAllMatchHistory();
        return ResponseEntity.ok(dtos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMatchHistory(@PathVariable Long id) {
        matchHistoryService.deleteMatchHistory(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/by-talent/{talentId}")
    public ResponseEntity<List<MatchHistoryDTO>> getMatchHistoryByTalent(@PathVariable Long talentId) {
        List<MatchHistoryDTO> dtos = matchHistoryService.getMatchHistoryByTalent(talentId);
        return ResponseEntity.ok(dtos);
    }
}
