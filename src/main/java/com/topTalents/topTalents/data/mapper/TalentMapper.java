package com.topTalents.topTalents.data.mapper;

import com.topTalents.topTalents.data.dto.TalentDTO;
import com.topTalents.topTalents.data.entity.MatchHistory;
import com.topTalents.topTalents.data.entity.Talent;

import java.util.stream.Collectors;

public class TalentMapper {
    public static TalentDTO toDTO(Talent talent) {
        if (talent == null) {
            return null;
        }
        TalentDTO dto = new TalentDTO();
        dto.setId(talent.getId());
        dto.setFirstName(talent.getFirstName());
        dto.setLastName(talent.getLastName());
        dto.setAge(talent.getAge());
        dto.setPosition(talent.getPosition() != null ? talent.getPosition().name() : null);
        dto.setPhotoPath(talent.getPhotoPath());
        if (talent.getUser() != null) {
            dto.setUserId(talent.getUser().getId());
        }
        if (talent.getTeam() != null) {
            dto.setTeamId(talent.getTeam().getId());
            dto.setTeamName(talent.getTeam().getName());
        }

        dto.setMatchesPlayed(talent.getMatchesPlayed());
        dto.setGoals(talent.getGoals());
        dto.setAssists(talent.getAssists());
        dto.setCleanSheets(talent.getCleanSheets());

        dto.setMatchHistoryIds(
                talent.getMatchHistory().stream()
                        .map(MatchHistory::getId)
                        .collect(Collectors.toList())
        );
        return dto;
    }

    public static Talent toEntity(TalentDTO dto) {
        if (dto == null) {
            return null;
        }
        Talent talent = new Talent();
        talent.setId(dto.getId());
        talent.setFirstName(dto.getFirstName());
        talent.setLastName(dto.getLastName());
        talent.setAge(dto.getAge());
        talent.setPhotoPath(dto.getPhotoPath());
        if (dto.getPosition() != null) {
            talent.setPosition(Enum.valueOf(com.topTalents.topTalents.data.enums.Position.class, dto.getPosition()));
        }
        return talent;
    }
}
