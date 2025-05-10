package com.topTalents.topTalents.data.mapper;

import com.topTalents.topTalents.data.dto.UserDTO;
import com.topTalents.topTalents.data.entity.User;
import com.topTalents.topTalents.data.enums.UserType;

public class UserMapper {
    public static UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setUserType(user.getUserType() != null ? user.getUserType().name() : null);
        return dto;
    }

    public static User toEntity(UserDTO dto) {
        if (dto == null) {
            return null;
        }
        User user = new User();
        user.setId(dto.getId());
        user.setEmail(dto.getEmail());
        if (dto.getUserType() != null) {
            user.setUserType(UserType.valueOf(dto.getUserType()));
        }
        return user;
    }
}
