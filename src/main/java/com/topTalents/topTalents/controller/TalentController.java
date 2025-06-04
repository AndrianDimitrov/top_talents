package com.topTalents.topTalents.controller;

import com.topTalents.topTalents.data.dto.TalentDTO;
import com.topTalents.topTalents.service.FileStorageService;
import com.topTalents.topTalents.service.serviceImpl.TalentServiceImpl;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;

@RestController
@RequestMapping("/api/talents")
public class TalentController {

    private final TalentServiceImpl talentService;
    private final FileStorageService fileStorageService;

    @Autowired
    public TalentController(TalentServiceImpl talentService, FileStorageService fileStorageService) {
        this.talentService = talentService;
        this.fileStorageService = fileStorageService;
    }

    @PostMapping
    public ResponseEntity<TalentDTO> createTalent(@Valid @RequestBody TalentDTO talentDTO) {
        TalentDTO createdTalent = talentService.createTalent(talentDTO);
        return new ResponseEntity<>(createdTalent, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TalentDTO> updateTalent(@PathVariable Long id,
                                                  @Valid @RequestBody TalentDTO talentDTO) {
        TalentDTO updatedTalent = talentService.updateTalent(id, talentDTO);
        return ResponseEntity.ok(updatedTalent);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TalentDTO> getTalentById(@PathVariable Long id) {
        TalentDTO talentDTO = talentService.getTalentById(id);
        return ResponseEntity.ok(talentDTO);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<TalentDTO> getTalentByUserId(@PathVariable Long userId) {
        TalentDTO talentDTO = talentService.getTalentByUserId(userId);
        return ResponseEntity.ok(talentDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTalent(@PathVariable Long id) {
        talentService.deleteTalent(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/photo")
    public ResponseEntity<String> uploadPhoto(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) throws IOException {

        String filename = fileStorageService.storeImage(file);
        talentService.updatePhoto(id, filename);

        String imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/uploads/")
                .path(filename)
                .toUriString();

        return ResponseEntity.ok(imageUrl);
    }
}
