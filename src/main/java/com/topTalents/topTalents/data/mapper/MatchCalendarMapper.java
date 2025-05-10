package com.topTalents.topTalents.data.mapper;

import com.topTalents.topTalents.data.dto.MatchCalendarDTO;
import com.topTalents.topTalents.data.entity.MatchCalendar;
import com.topTalents.topTalents.data.entity.Team;


public class MatchCalendarMapper {

    public static MatchCalendarDTO toDTO(MatchCalendar matchCalendar) {
        if (matchCalendar == null) {
            return null;
        }
        MatchCalendarDTO dto = new MatchCalendarDTO();
        dto.setId(matchCalendar.getId());
        dto.setMatchDateTime(matchCalendar.getMatchDateTime());
        dto.setDescription(matchCalendar.getDescription());

        Team homeTeam = matchCalendar.getHomeTeam();
        if (homeTeam != null) {
            dto.setHomeTeamId(homeTeam.getId());
            dto.setHomeTeamName(homeTeam.getName());
        }

        Team guestTeam = matchCalendar.getGuestTeam();
        if (guestTeam != null) {
            dto.setGuestTeamId(guestTeam.getId());
            dto.setGuestTeamName(guestTeam.getName());
        }
        return dto;
    }

    public static MatchCalendar toEntity(MatchCalendarDTO dto) {
        if (dto == null) {
            return null;
        }
        MatchCalendar matchCalendar = new MatchCalendar();
        matchCalendar.setId(dto.getId());
        matchCalendar.setMatchDateTime(dto.getMatchDateTime());
        matchCalendar.setDescription(dto.getDescription());

        if (dto.getHomeTeamId() != null) {
            Team homeTeam = new Team();
            homeTeam.setId(dto.getHomeTeamId());
            homeTeam.setName(dto.getHomeTeamName());
            matchCalendar.setHomeTeam(homeTeam);
        }
        if (dto.getGuestTeamId() != null) {
            Team guestTeam = new Team();
            guestTeam.setId(dto.getGuestTeamId());
            guestTeam.setName(dto.getGuestTeamName());
            matchCalendar.setGuestTeam(guestTeam);
        }
        return matchCalendar;
    }
}
