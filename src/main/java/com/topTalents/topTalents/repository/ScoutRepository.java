package com.topTalents.topTalents.repository;

import com.topTalents.topTalents.data.entity.Scout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScoutRepository extends JpaRepository<Scout, Long> {

    List<Scout> findByFirstNameIgnoreCase(String firstName);

    List<Scout> findByLastNameIgnoreCase(String lastName);

    List<Scout> findByFirstNameIgnoreCaseAndLastNameIgnoreCase(String firstName, String lastName);
}
