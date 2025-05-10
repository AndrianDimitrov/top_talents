package com.topTalents.topTalents.data.mapper;

import com.topTalents.topTalents.data.dto.TeamDTO;
import com.topTalents.topTalents.data.entity.Talent;
import com.topTalents.topTalents.data.entity.Team;

import java.util.List;
import java.util.stream.Collectors;

public class TeamMapper {
    public static TeamDTO toDTO(Team team) {
        if (team == null) {
            return null;
        }
        TeamDTO dto = new TeamDTO();
        dto.setId(team.getId());
        dto.setName(team.getName());
        dto.setCity(team.getCity());
        dto.setAgeGroup(team.getAgeGroup());
        if (team.getPlayers() != null) {
            List<Long> playerIds = team.getPlayers().stream()
                    .map(Talent::getId)
                    .collect(Collectors.toList());
            dto.setPlayerIds(playerIds);
        }
        return dto;
    }

    public static Team toEntity(TeamDTO dto) {
        if (dto == null) {
            return null;
        }
        Team team = new Team();
        team.setId(dto.getId());
        team.setName(dto.getName());
        team.setCity(dto.getCity());
        team.setAgeGroup(dto.getAgeGroup());
        return team;
    }
}
