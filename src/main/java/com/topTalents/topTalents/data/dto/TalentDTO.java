package com.topTalents.topTalents.data.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class TalentDTO {
    private Long id;

    @NotNull(message = "User id is required")
    private Long userId;

    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    private String lastName;

    @Min(value = 5, message = "Age must be at least 5")
    @Max(value = 19, message = "Age must be at most 19")
    private int age;

    @NotNull(message = "Position is required")
    private String position;

    private Long teamId;

    private String teamName;

    private int matchesPlayed;

    private int goals;

    private int assists;

    @Min(value = 0, message = "Clean sheets cannot be negative")
    private int cleanSheets;

    private String photoPath;

    private List<Long> matchHistoryIds;
}
