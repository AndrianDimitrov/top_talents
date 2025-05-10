package com.topTalents.topTalents.data.mapper;

import com.topTalents.topTalents.data.dto.ScoutDTO;
import com.topTalents.topTalents.data.entity.Scout;
import com.topTalents.topTalents.data.entity.Talent;

import java.util.ArrayList;
import java.util.List;

public class ScoutMapper {
    public static ScoutDTO toDTO(Scout scout) {
        if (scout == null) {
            return null;
        }
        ScoutDTO dto = new ScoutDTO();
        dto.setId(scout.getId());
        dto.setFirstName(scout.getFirstName());
        dto.setLastName(scout.getLastName());
        dto.setEmail(scout.getEmail());

        if (scout.getUser() != null) {
            dto.setUserId(scout.getUser().getId());
        }

        if (scout.getFollowedTalents() != null) {
            List<Long> talentIds = new ArrayList<>();
            for (Talent talent : scout.getFollowedTalents()) {
                talentIds.add(talent.getId());
            }
            dto.setFollowedTalentIds(talentIds);
        }
        return dto;
    }

    public static Scout toEntity(ScoutDTO dto) {
        if (dto == null) {
            return null;
        }
        Scout scout = new Scout();
        scout.setId(dto.getId());
        scout.setFirstName(dto.getFirstName());
        scout.setLastName(dto.getLastName());
        scout.setEmail(dto.getEmail());
        return scout;
    }
}
