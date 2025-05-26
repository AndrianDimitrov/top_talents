package com.topTalents.topTalents.data.entity;

import com.topTalents.topTalents.data.enums.Position;
import com.topTalents.topTalents.util.PositionConverter;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "talents")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Talent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    private int age;

    @Convert(converter = PositionConverter.class)
    private Position position;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    private int matchesPlayed;

    private int goals;

    private int assists;

    private int cleanSheets;

    private String photoPath;

    @OneToMany(mappedBy = "talent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MatchHistory> matchHistory = new ArrayList<>();
}
