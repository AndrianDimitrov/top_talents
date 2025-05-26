package com.topTalents.topTalents.service;

import com.topTalents.topTalents.data.dto.TalentDTO;

public interface TalentService {

    TalentDTO createTalent(TalentDTO talentDTO);

    TalentDTO updateTalent(Long id, TalentDTO talentDTO);

    TalentDTO getTalentById(Long id);

    void deleteTalent(Long id);

    void updatePhoto(Long talentId, String filename);
}
