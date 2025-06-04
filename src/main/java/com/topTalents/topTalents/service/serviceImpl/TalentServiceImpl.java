package com.topTalents.topTalents.service.serviceImpl;

import com.topTalents.topTalents.data.dto.TalentDTO;
import com.topTalents.topTalents.data.entity.Talent;
import com.topTalents.topTalents.data.entity.Team;
import com.topTalents.topTalents.data.entity.User;
import com.topTalents.topTalents.data.enums.Position;
import com.topTalents.topTalents.data.mapper.TalentMapper;
import com.topTalents.topTalents.repository.TalentRepository;
import com.topTalents.topTalents.repository.TeamRepository;
import com.topTalents.topTalents.repository.UserRepository;
import com.topTalents.topTalents.service.TalentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TalentServiceImpl implements TalentService {

    private final TalentRepository talentRepository;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;

    @Autowired
    public TalentServiceImpl(TalentRepository talentRepository,
                             UserRepository userRepository,
                             TeamRepository teamRepository) {
        this.talentRepository = talentRepository;
        this.userRepository = userRepository;
        this.teamRepository = teamRepository;
    }

    @Override
    public TalentDTO createTalent(TalentDTO talentDTO) {
        Talent talent = TalentMapper.toEntity(talentDTO);

        User user = userRepository.findById(talentDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + talentDTO.getUserId()));
        talent.setUser(user);

        if (talentDTO.getTeamId() != null) {
            Team team = teamRepository.findById(talentDTO.getTeamId())
                    .orElseThrow(() -> new RuntimeException("Team not found with id: " + talentDTO.getTeamId()));
            talent.setTeam(team);
        }

        Talent savedTalent = talentRepository.save(talent);

        return TalentMapper.toDTO(savedTalent);
    }

    @Override
    public TalentDTO updateTalent(Long id, TalentDTO talentDTO) {
        Talent existing = talentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Talent not found with id: " + id));

        existing.setFirstName(talentDTO.getFirstName());
        existing.setLastName(talentDTO.getLastName());
        existing.setAge(talentDTO.getAge());
        existing.setPosition(Position.valueOf(talentDTO.getPosition()));

        if (talentDTO.getTeamId() != null) {
            Team team = teamRepository.findById(talentDTO.getTeamId())
                    .orElseThrow(() -> new RuntimeException("Team not found with id: " + talentDTO.getTeamId()));
            existing.setTeam(team);
        }

        Talent updated = talentRepository.save(existing);

        return TalentMapper.toDTO(updated);
    }

    @Override
    public TalentDTO getTalentById(Long id) {
        Talent talent = talentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Talent not found with id: " + id));
        return TalentMapper.toDTO(talent);
    }

    @Override
    public TalentDTO getTalentByUserId(Long userId) {
        Talent talent = talentRepository.findByUser_Id(userId)
                .orElseThrow(() -> new RuntimeException("Talent not found for userId: " + userId));
        return TalentMapper.toDTO(talent);
    }

    @Override
    public void deleteTalent(Long id) {
        if (!talentRepository.existsById(id)) {
            throw new RuntimeException("Talent not found with id: " + id);
        }
        talentRepository.deleteById(id);
    }

    @Override
    public void updatePhoto(Long talentId, String filename) {
        Talent talent = talentRepository.findById(talentId)
                .orElseThrow(() -> new RuntimeException("Talent not found with id: " + talentId));
        talent.setPhotoPath(filename);
        talentRepository.save(talent);
    }
}
