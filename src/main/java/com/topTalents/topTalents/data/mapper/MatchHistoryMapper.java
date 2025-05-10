package com.topTalents.topTalents.data.mapper;

import com.topTalents.topTalents.data.dto.MatchHistoryDTO;
import com.topTalents.topTalents.data.entity.MatchHistory;

public class MatchHistoryMapper {

    public static MatchHistoryDTO toDTO(MatchHistory matchHistory) {
        if (matchHistory == null) {
            return null;
        }
        MatchHistoryDTO dto = new MatchHistoryDTO();
        dto.setId(matchHistory.getId());
        dto.setMatchDate(matchHistory.getMatchDate());
        dto.setOpponentTeam(matchHistory.getOpponentTeam());
        dto.setGoals(matchHistory.getGoals());
        dto.setAssists(matchHistory.getAssists());
        dto.setStarter(matchHistory.isStarter());
        dto.setRating(matchHistory.getRating());
        if (matchHistory.getTalent() != null) {
            dto.setTalentId(matchHistory.getTalent().getId());
        }
        return dto;
    }

    public static MatchHistory toEntity(MatchHistoryDTO dto) {
        if (dto == null) {
            return null;
        }
        MatchHistory matchHistory = new MatchHistory();
        matchHistory.setId(dto.getId());
        matchHistory.setMatchDate(dto.getMatchDate());
        matchHistory.setOpponentTeam(dto.getOpponentTeam());
        matchHistory.setGoals(dto.getGoals());
        matchHistory.setAssists(dto.getAssists());
        matchHistory.setStarter(dto.isStarter());
        matchHistory.setRating(dto.getRating());
        return matchHistory;
    }
}
