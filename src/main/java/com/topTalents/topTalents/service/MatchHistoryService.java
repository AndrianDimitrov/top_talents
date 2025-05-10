package com.topTalents.topTalents.service;

import com.topTalents.topTalents.data.dto.MatchHistoryDTO;

import java.util.List;

public interface MatchHistoryService {

    MatchHistoryDTO createMatchHistory(MatchHistoryDTO dto);

    MatchHistoryDTO updateMatchHistory(Long id, MatchHistoryDTO dto);

    MatchHistoryDTO getMatchHistoryById(Long id);

    List<MatchHistoryDTO> getAllMatchHistory();

    void deleteMatchHistory(Long id);

    List<MatchHistoryDTO> getMatchHistoryByTalent(Long talentId);
}
