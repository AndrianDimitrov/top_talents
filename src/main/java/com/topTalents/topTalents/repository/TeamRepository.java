package com.topTalents.topTalents.repository;

import com.topTalents.topTalents.data.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

   Optional <Team> findByNameIgnoreCase(String name);

    List<Team> findByAgeGroup(String ageGroup);

    List<Team> findByCityIgnoreCase(String city);
}
