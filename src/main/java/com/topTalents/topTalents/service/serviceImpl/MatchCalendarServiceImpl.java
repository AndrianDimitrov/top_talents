package com.topTalents.topTalents.service.serviceImpl;

import com.topTalents.topTalents.data.dto.MatchCalendarDTO;
import com.topTalents.topTalents.data.entity.MatchCalendar;
import com.topTalents.topTalents.data.entity.Team;
import com.topTalents.topTalents.data.mapper.MatchCalendarMapper;
import com.topTalents.topTalents.repository.MatchCalendarRepository;
import com.topTalents.topTalents.repository.TeamRepository;
import com.topTalents.topTalents.service.MatchCalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MatchCalendarServiceImpl implements MatchCalendarService {

    private final MatchCalendarRepository matchCalendarRepository;
    private final TeamRepository teamRepository;

    @Autowired
    public MatchCalendarServiceImpl(MatchCalendarRepository matchCalendarRepository,
                                    TeamRepository teamRepository) {
        this.matchCalendarRepository = matchCalendarRepository;
        this.teamRepository = teamRepository;
    }

    @Override
    public MatchCalendarDTO createMatchCalendar(MatchCalendarDTO dto) {
        Team homeTeam = teamRepository.findById(dto.getHomeTeamId())
                .orElseThrow(() -> new RuntimeException("Home team not found with id: " + dto.getHomeTeamId()));

        Team guestTeam = teamRepository.findById(dto.getGuestTeamId())
                .orElseThrow(() -> new RuntimeException("Guest team not found with id: " + dto.getGuestTeamId()));

        MatchCalendar matchCalendar = MatchCalendarMapper.toEntity(dto);
        matchCalendar.setHomeTeam(homeTeam);
        matchCalendar.setGuestTeam(guestTeam);

        MatchCalendar saved = matchCalendarRepository.save(matchCalendar);
        return MatchCalendarMapper.toDTO(saved);
    }

    @Override
    public MatchCalendarDTO updateMatchCalendar(Long id, MatchCalendarDTO dto) {
        MatchCalendar existing = matchCalendarRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("MatchCalendar not found with id: " + id));

        existing.setMatchDateTime(dto.getMatchDateTime());
        existing.setDescription(dto.getDescription());

        if (dto.getHomeTeamId() != null) {
            Team homeTeam = teamRepository.findById(dto.getHomeTeamId())
                    .orElseThrow(() -> new RuntimeException("Home team not found with id: " + dto.getHomeTeamId()));
            existing.setHomeTeam(homeTeam);
        }
        if (dto.getGuestTeamId() != null) {
            Team guestTeam = teamRepository.findById(dto.getGuestTeamId())
                    .orElseThrow(() -> new RuntimeException("Guest team not found with id: " + dto.getGuestTeamId()));
            existing.setGuestTeam(guestTeam);
        }

        MatchCalendar updated = matchCalendarRepository.save(existing);
        return MatchCalendarMapper.toDTO(updated);
    }

    @Override
    public MatchCalendarDTO getMatchCalendarById(Long id) {
        MatchCalendar mc = matchCalendarRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("MatchCalendar not found with id: " + id));
        return MatchCalendarMapper.toDTO(mc);
    }

    @Override
    public List<MatchCalendarDTO> getAllMatchCalendars() {
        return matchCalendarRepository.findAll()
                .stream()
                .map(MatchCalendarMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteMatchCalendar(Long id) {
        if (!matchCalendarRepository.existsById(id)) {
            throw new RuntimeException("MatchCalendar not found with id: " + id);
        }
        matchCalendarRepository.deleteById(id);
    }


    @Override
    public List<MatchCalendarDTO> getMatchCalendarsByDateRange(LocalDateTime start, LocalDateTime end) {
        return matchCalendarRepository.findByMatchDateTimeBetween(start, end)
                .stream()
                .map(MatchCalendarMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<MatchCalendarDTO> getMatchCalendarsByTeam(Long teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Team not found with id: " + teamId));

        List<MatchCalendarDTO> homeMatches = matchCalendarRepository.findByHomeTeam(team)
                .stream()
                .map(MatchCalendarMapper::toDTO)
                .collect(Collectors.toList());

        List<MatchCalendarDTO> guestMatches = matchCalendarRepository.findByGuestTeam(team)
                .stream()
                .map(MatchCalendarMapper::toDTO)
                .toList();

        homeMatches.addAll(guestMatches);
        return homeMatches;
    }
}
