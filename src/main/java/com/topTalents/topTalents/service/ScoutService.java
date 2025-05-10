package com.topTalents.topTalents.service;

import com.topTalents.topTalents.data.dto.ScoutDTO;
import com.topTalents.topTalents.data.dto.TalentDTO;

import java.util.List;
import java.util.Optional;

public interface ScoutService {
    ScoutDTO createScout(ScoutDTO scoutDTO);

    ScoutDTO updateScout(Long id, ScoutDTO scoutDTO);

    ScoutDTO addFollowedTalents(Long scoutId, List<Long> talentIds);

    List<TalentDTO> getFollowedTalentsForScout(Long scoutId);

    Optional<ScoutDTO> getScoutById(Long id);

    List<ScoutDTO> getAllScouts();

    void deleteScout(Long id);
}
