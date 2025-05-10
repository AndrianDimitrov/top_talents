package com.topTalents.topTalents.repository;

import com.topTalents.topTalents.data.entity.MatchHistory;
import com.topTalents.topTalents.data.entity.Talent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MatchHistoryRepository extends JpaRepository<MatchHistory, Long> {

    List<MatchHistory> findByTalent(Talent talent);

    List<MatchHistory> findByMatchDateBetween(LocalDate start, LocalDate end);

    List<MatchHistory> findByOpponentTeamIgnoreCase(String opponentTeam);

    List<MatchHistory> findByIsStarter(boolean isStarter);
}
