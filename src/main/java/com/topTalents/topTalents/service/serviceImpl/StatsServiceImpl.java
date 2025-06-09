package com.topTalents.topTalents.service.serviceImpl;

import com.topTalents.topTalents.data.dto.SystemStats;
import com.topTalents.topTalents.repository.MatchCalendarRepository;
import com.topTalents.topTalents.repository.MatchHistoryRepository;
import com.topTalents.topTalents.repository.ScoutRepository;
import com.topTalents.topTalents.repository.TalentRepository;
import com.topTalents.topTalents.repository.TeamRepository;
import com.topTalents.topTalents.repository.UserRepository;
import com.topTalents.topTalents.service.StatsService;
import org.springframework.stereotype.Service;

@Service
public class StatsServiceImpl implements StatsService {

    private final UserRepository userRepo;
    private final TalentRepository talentRepo;
    private final ScoutRepository scoutRepo;
    private final TeamRepository teamRepo;
    private final MatchCalendarRepository calendarRepo;
    private final MatchHistoryRepository historyRepo;

    public StatsServiceImpl(UserRepository userRepo,
                            TalentRepository talentRepo,
                            ScoutRepository scoutRepo,
                            TeamRepository teamRepo,
                            MatchCalendarRepository calendarRepo,
                            MatchHistoryRepository historyRepo) {
        this.userRepo = userRepo;
        this.talentRepo = talentRepo;
        this.scoutRepo = scoutRepo;
        this.teamRepo = teamRepo;
        this.calendarRepo = calendarRepo;
        this.historyRepo = historyRepo;
    }

    @Override
    public SystemStats getSystemStats() {
        return new SystemStats(
                userRepo.count(),
                talentRepo.count(),
                scoutRepo.count(),
                teamRepo.count(),
                calendarRepo.count(),
                historyRepo.count()
        );
    }
}