package com.topTalents.topTalents.data.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class TeamDTO {

    private Long id;

    @NotBlank(message = "Team name is required")
    private String name;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "Age group is required")
    private String ageGroup;

    private List<Long> playerIds;
}
