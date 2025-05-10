package com.topTalents.topTalents.data.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MatchHistoryDTO {
    private Long id;

    @NotNull(message = "Match date is required")
    private LocalDate matchDate;

    @NotBlank(message = "Opponent team is required")
    private String opponentTeam;

    @Min(value = 0, message = "Goals cannot be negative")
    private int goals;

    @Min(value = 0, message = "Assists cannot be negative")
    private int assists;

    @NotNull
    private boolean isStarter;

    @DecimalMin(value = "0.0", inclusive = true, message = "Rating must be at least 0")
    @DecimalMax(value = "10.0", inclusive = true, message = "Rating must not exceed 10")
    private double rating;

    @NotNull(message = "Talent id is required")
    private Long talentId;
}
