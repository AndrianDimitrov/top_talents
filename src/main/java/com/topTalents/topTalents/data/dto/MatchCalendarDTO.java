package com.topTalents.topTalents.data.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MatchCalendarDTO {

    private Long id;

    @NotNull(message = "Match date and time are required")
    private LocalDateTime matchDateTime;

    @NotNull(message = "Home team id is required")
    private Long homeTeamId;
    private String homeTeamName;

    @NotNull(message = "Guest team id is required")
    private Long guestTeamId;
    private String guestTeamName;

    private String description;
}
