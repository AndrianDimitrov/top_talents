package com.topTalents.topTalents.service;

import com.topTalents.topTalents.data.dto.MatchCalendarDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface MatchCalendarService {

    MatchCalendarDTO createMatchCalendar(MatchCalendarDTO dto);

    MatchCalendarDTO updateMatchCalendar(Long id, MatchCalendarDTO dto);

    MatchCalendarDTO getMatchCalendarById(Long id);

    List<MatchCalendarDTO> getAllMatchCalendars();

    void deleteMatchCalendar(Long id);

    List<MatchCalendarDTO> getMatchCalendarsByDateRange(LocalDateTime start, LocalDateTime end);

    List<MatchCalendarDTO> getMatchCalendarsByTeam(Long teamId);

}
