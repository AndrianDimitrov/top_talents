package com.topTalents.topTalents.controller;

import com.topTalents.topTalents.data.dto.ScoutDTO;
import com.topTalents.topTalents.data.dto.TalentDTO;
import com.topTalents.topTalents.service.serviceImpl.ScoutServiceImpl;
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
import java.util.Optional;

@RestController
@RequestMapping("/api/scouts")
public class ScoutController {
    private final ScoutServiceImpl scoutService;

    @Autowired
    public ScoutController(ScoutServiceImpl scoutService) {
        this.scoutService = scoutService;
    }

    @PostMapping
    public ResponseEntity<ScoutDTO> createScout(@Valid @RequestBody ScoutDTO scoutDTO) {
        ScoutDTO createdScout = scoutService.createScout(scoutDTO);
        return new ResponseEntity<>(createdScout, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScoutDTO> updateScout(@PathVariable Long id, @Valid @RequestBody ScoutDTO scoutDTO) {
        ScoutDTO updatedScout = scoutService.updateScout(id, scoutDTO);
        return ResponseEntity.ok(updatedScout);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScoutDTO> getScoutById(@PathVariable Long id) {
        Optional<ScoutDTO> scoutOpt = scoutService.getScoutById(id);
        return scoutOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ScoutDTO>> getAllScouts() {
        List<ScoutDTO> scouts = scoutService.getAllScouts();
        return ResponseEntity.ok(scouts);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteScout(@PathVariable Long id) {
        scoutService.deleteScout(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{scoutId}/followed-talents")
    public ResponseEntity<List<TalentDTO>> getFollowedTalents(@PathVariable Long scoutId) {
        List<TalentDTO> talents = scoutService.getFollowedTalentsForScout(scoutId);
        return ResponseEntity.ok(talents);
    }

    @PostMapping("/{scoutId}/followed-talents")
    public ResponseEntity<ScoutDTO> addFollowedTalents(@PathVariable Long scoutId,
                                                       @RequestBody List<Long> talentIds) {
        ScoutDTO updatedScout = scoutService.addFollowedTalents(scoutId, talentIds);
        return ResponseEntity.ok(updatedScout);
    }
}
