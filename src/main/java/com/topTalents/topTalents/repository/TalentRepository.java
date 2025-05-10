package com.topTalents.topTalents.repository;

import com.topTalents.topTalents.data.entity.Talent;
import com.topTalents.topTalents.data.entity.Team;
import com.topTalents.topTalents.data.enums.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TalentRepository extends JpaRepository<Talent, Long> {

    List<Talent> findByFirstNameIgnoreCase(String firstName);

    List<Talent> findByLastNameIgnoreCase(String lastName);

    List<Talent> findByFirstNameAndLastNameIgnoreCase(String firstName, String lastName);

    List<Talent> findByTeam(Team team);

    List<Talent> findByPosition(Position position);
}
