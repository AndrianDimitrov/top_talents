package com.topTalents.topTalents.service;

import com.topTalents.topTalents.data.dto.TeamDTO;

import java.util.List;

public interface TeamService {

    TeamDTO createTeam(TeamDTO teamDTO);

    TeamDTO getTeamById(Long id);

    List<TeamDTO> getAllTeams();

    TeamDTO updateTeam(Long id, TeamDTO teamDTO);

    void deleteTeam(Long id);
}
