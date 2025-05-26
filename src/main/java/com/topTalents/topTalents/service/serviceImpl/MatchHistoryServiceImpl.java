package com.topTalents.topTalents.service.serviceImpl;

import com.topTalents.topTalents.data.dto.MatchHistoryDTO;
import com.topTalents.topTalents.data.entity.MatchHistory;
import com.topTalents.topTalents.data.entity.Talent;
import com.topTalents.topTalents.data.enums.Position;
import com.topTalents.topTalents.data.mapper.MatchHistoryMapper;
import com.topTalents.topTalents.repository.MatchHistoryRepository;
import com.topTalents.topTalents.repository.TalentRepository;
import com.topTalents.topTalents.service.MatchHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MatchHistoryServiceImpl implements MatchHistoryService {

    private final MatchHistoryRepository matchHistoryRepository;
    private final TalentRepository talentRepository;

    @Autowired
    public MatchHistoryServiceImpl(MatchHistoryRepository matchHistoryRepository,
                                   TalentRepository talentRepository) {
        this.matchHistoryRepository = matchHistoryRepository;
        this.talentRepository = talentRepository;
    }

    @Override
    public MatchHistoryDTO createMatchHistory(MatchHistoryDTO dto) {
        Talent talent = talentRepository.findById(dto.getTalentId())
                .orElseThrow(() -> new RuntimeException("Talent not found with id: " + dto.getTalentId()));

        MatchHistory matchHistory = MatchHistoryMapper.toEntity(dto);
        matchHistory.setTalent(talent);

        matchHistory.setRating(computeRating(talent.getPosition(), dto.getGoals(), dto.getAssists(), dto.isCleanSheet(), dto.isStarter()));

        MatchHistory saved = matchHistoryRepository.save(matchHistory);

        updateTalentAggregates(talent);
        return MatchHistoryMapper.toDTO(saved);
    }

    @Override
    public MatchHistoryDTO updateMatchHistory(Long id, MatchHistoryDTO dto) {
        MatchHistory existing = matchHistoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("MatchHistory not found with id: " + id));

        existing.setMatchDate(dto.getMatchDate());
        existing.setOpponentTeam(dto.getOpponentTeam());
        existing.setGoals(dto.getGoals());
        existing.setAssists(dto.getAssists());
        existing.setStarter(dto.isStarter());
        existing.setCleanSheet(dto.isCleanSheet());

        existing.setRating(computeRating(existing.getTalent().getPosition(),
                dto.getGoals(), dto.getAssists(), dto.isCleanSheet(), dto.isStarter()));

        MatchHistory updated = matchHistoryRepository.save(existing);

        updateTalentAggregates(updated.getTalent());

        return MatchHistoryMapper.toDTO(updated);
    }

    @Override
    public MatchHistoryDTO getMatchHistoryById(Long id) {
        MatchHistory matchHistory = matchHistoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("MatchHistory not found with id: " + id));
        return MatchHistoryMapper.toDTO(matchHistory);
    }

    @Override
    public List<MatchHistoryDTO> getAllMatchHistory() {
        return matchHistoryRepository.findAll()
                .stream()
                .map(MatchHistoryMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteMatchHistory(Long id) {
        if (!matchHistoryRepository.existsById(id)) {
            throw new RuntimeException("MatchHistory not found with id: " + id);
        }
        matchHistoryRepository.deleteById(id);
    }

    @Override
    public List<MatchHistoryDTO> getMatchHistoryByTalent(Long talentId) {
        Talent talent = talentRepository.findById(talentId)
                .orElseThrow(() -> new RuntimeException("Talent not found with id: " + talentId));
        return matchHistoryRepository.findByTalent(talent)
                .stream()
                .map(MatchHistoryMapper::toDTO)
                .collect(Collectors.toList());
    }

    private double computeRating(
            Position position,
            int goals,
            int assists,
            boolean cleanSheet,
            boolean starter
    ) {
        if (!starter) {
            return 0.0;
        }

        double rating;
        if (position == Position.GOALKEEPER) {
            rating = cleanSheet ? 8.0 : 5.0;
        } else {
            rating = 6.0 + goals * 1.0 + assists * 0.75;
        }

        if (rating < 0.0) {
            rating = 0.0;
        } else if (rating > 10.0) {
            rating = 10.0;
        }

        return rating;
    }

    private void updateTalentAggregates(Talent talent) {
        List<MatchHistory> history = matchHistoryRepository.findByTalent(talent);

        int matchesPlayed = history.size();

        int totalGoals = history.stream().mapToInt(MatchHistory::getGoals).sum();
        int totalAssists = history.stream().mapToInt(MatchHistory::getAssists).sum();

        int totalCleanSheets = 0;
        if (talent.getPosition() == Position.GOALKEEPER) {
            totalCleanSheets = (int) history.stream().filter(MatchHistory::isCleanSheet).count();
        }

        talent.setMatchesPlayed(matchesPlayed);
        talent.setGoals(totalGoals);
        talent.setAssists(totalAssists);
        talent.setCleanSheets(totalCleanSheets);

        talentRepository.save(talent);
    }
}
