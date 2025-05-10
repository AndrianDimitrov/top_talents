package com.topTalents.topTalents.repository;

import com.topTalents.topTalents.data.entity.MatchCalendar;
import com.topTalents.topTalents.data.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MatchCalendarRepository extends JpaRepository<MatchCalendar, Long> {

    List<MatchCalendar> findByMatchDateTimeBetween(LocalDateTime start, LocalDateTime end);

    List<MatchCalendar> findByHomeTeam(Team homeTeam);

    List<MatchCalendar> findByGuestTeam(Team guestTeam);
}
