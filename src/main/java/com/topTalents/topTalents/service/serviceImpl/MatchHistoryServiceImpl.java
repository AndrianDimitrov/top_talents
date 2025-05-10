package com.topTalents.topTalents.service.serviceImpl;

import com.topTalents.topTalents.data.dto.MatchHistoryDTO;
import com.topTalents.topTalents.data.entity.MatchHistory;
import com.topTalents.topTalents.data.entity.Talent;
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
        MatchHistory saved = matchHistoryRepository.save(matchHistory);

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
        existing.setRating(dto.getRating());

        MatchHistory updated = matchHistoryRepository.save(existing);
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
}
