package com.topTalents.topTalents.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SystemStats {
    private long userCount;
    private long talentCount;
    private long scoutCount;
    private long teamCount;
    private long scheduledMatchCount;
    private long matchHistoryCount;
}