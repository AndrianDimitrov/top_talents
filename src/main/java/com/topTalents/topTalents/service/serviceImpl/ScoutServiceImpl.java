package com.topTalents.topTalents.service.serviceImpl;

import com.topTalents.topTalents.data.dto.ScoutDTO;
import com.topTalents.topTalents.data.dto.TalentDTO;
import com.topTalents.topTalents.data.entity.Scout;
import com.topTalents.topTalents.data.entity.Talent;
import com.topTalents.topTalents.data.entity.User;
import com.topTalents.topTalents.data.mapper.ScoutMapper;
import com.topTalents.topTalents.data.mapper.TalentMapper;
import com.topTalents.topTalents.repository.ScoutRepository;
import com.topTalents.topTalents.repository.TalentRepository;
import com.topTalents.topTalents.repository.UserRepository;
import com.topTalents.topTalents.service.ScoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ScoutServiceImpl implements ScoutService {
    private final ScoutRepository scoutRepository;
    private final UserRepository userRepository;
    private final TalentRepository talentRepository;

    @Autowired
    public ScoutServiceImpl(ScoutRepository scoutRepository,
                            UserRepository userRepository,
                            TalentRepository talentRepository) {
        this.scoutRepository = scoutRepository;
        this.userRepository = userRepository;
        this.talentRepository = talentRepository;
    }

    @Override
    public ScoutDTO createScout(ScoutDTO scoutDTO) {
        Scout scout = ScoutMapper.toEntity(scoutDTO);

        User user = userRepository.findById(scoutDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + scoutDTO.getUserId()));
        scout.setUser(user);

        if (scoutDTO.getFollowedTalentIds() != null && !scoutDTO.getFollowedTalentIds().isEmpty()) {
            List<Talent> talents = scoutDTO.getFollowedTalentIds()
                    .stream()
                    .map(talentId -> talentRepository.findById(talentId)
                            .orElseThrow(() -> new RuntimeException("Talent not found with id: " + talentId)))
                    .collect(Collectors.toList());
            scout.setFollowedTalents(talents);
        }

        Scout savedScout = scoutRepository.save(scout);
        return ScoutMapper.toDTO(savedScout);
    }

    @Override
    public ScoutDTO updateScout(Long id, ScoutDTO scoutDTO) {
        Scout existingScout = scoutRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Scout not found with id: " + id));

        existingScout.setFirstName(scoutDTO.getFirstName());
        existingScout.setLastName(scoutDTO.getLastName());
        existingScout.setEmail(scoutDTO.getEmail());

        Scout updatedScout = scoutRepository.save(existingScout);
        return ScoutMapper.toDTO(updatedScout);
    }


    @Override
    public ScoutDTO addFollowedTalents(Long scoutId, List<Long> talentIds) {
        Scout scout = scoutRepository.findById(scoutId)
                .orElseThrow(() -> new RuntimeException("Scout not found with id: " + scoutId));

        addFollowedTalent(scout, talentIds);

        Scout updatedScout = scoutRepository.save(scout);
        return ScoutMapper.toDTO(updatedScout);
    }


    public void addFollowedTalent(Scout scout, List<Long> talentIds) {
        if (talentIds != null && !talentIds.isEmpty()) {
            List<Talent> talentsToAdd = talentIds.stream()
                    .map(talentId -> talentRepository.findById(talentId)
                            .orElseThrow(() -> new RuntimeException("Talent not found with id: " + talentId)))
                    .collect(Collectors.toList());

            for (Talent talent : talentsToAdd) {
                if (!scout.getFollowedTalents().contains(talent)) {
                    scout.getFollowedTalents().add(talent);
                }
            }
        }
    }

    @Override
    public Optional<ScoutDTO> getScoutById(Long id) {
        return scoutRepository.findById(id)
                .map(ScoutMapper::toDTO);
    }

    @Override
    public List<ScoutDTO> getAllScouts() {
        return scoutRepository.findAll()
                .stream()
                .map(ScoutMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteScout(Long id) {
        if (!scoutRepository.existsById(id)) {
            throw new RuntimeException("Scout not found with id: " + id);
        }
        scoutRepository.deleteById(id);
    }

    @Override
    public List<TalentDTO> getFollowedTalentsForScout(Long scoutId) {
        Scout scout = scoutRepository.findById(scoutId)
                .orElseThrow(() -> new RuntimeException("Scout not found with id: " + scoutId));
        return scout.getFollowedTalents()
                .stream()
                .map(TalentMapper::toDTO)
                .collect(Collectors.toList());
    }
}
