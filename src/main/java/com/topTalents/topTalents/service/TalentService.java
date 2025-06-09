package com.topTalents.topTalents.service;

import com.topTalents.topTalents.data.dto.TalentDTO;

import java.util.List;

public interface TalentService {

    TalentDTO createTalent(TalentDTO talentDTO);

    TalentDTO updateTalent(Long id, TalentDTO talentDTO);

    TalentDTO getTalentById(Long id);

    void deleteTalent(Long id);

    void updatePhoto(Long talentId, String filename);

    TalentDTO getTalentByUserId(Long userId);

    List<TalentDTO> getAllTalents();
}
