package com.topTalents.topTalents.service.serviceImpl;

import com.topTalents.topTalents.data.dto.TeamDTO;
import com.topTalents.topTalents.data.entity.Team;
import com.topTalents.topTalents.data.mapper.TeamMapper;
import com.topTalents.topTalents.repository.TeamRepository;
import com.topTalents.topTalents.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;

    @Autowired
    public TeamServiceImpl(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    public TeamDTO createTeam(TeamDTO teamDTO) {
        Team team = TeamMapper.toEntity(teamDTO);
        Team savedTeam = teamRepository.save(team);
        return TeamMapper.toDTO(savedTeam);
    }

    @Override
    public TeamDTO getTeamById(Long id) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Team not found with id: " + id));
        return TeamMapper.toDTO(team);
    }

    @Override
    public List<TeamDTO> getAllTeams() {
        return teamRepository.findAll()
                .stream()
                .map(TeamMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TeamDTO updateTeam(Long id, TeamDTO teamDTO) {
        Team existingTeam = teamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Team not found with id: " + id));

        existingTeam.setName(teamDTO.getName());
        existingTeam.setCity(teamDTO.getCity());
        existingTeam.setAgeGroup(teamDTO.getAgeGroup());
        Team updatedTeam = teamRepository.save(existingTeam);
        return TeamMapper.toDTO(updatedTeam);
    }

    @Override
    public void deleteTeam(Long id) {
        if (!teamRepository.existsById(id)) {
            throw new RuntimeException("Team not found with id: " + id);
        }
        teamRepository.deleteById(id);
    }
}
