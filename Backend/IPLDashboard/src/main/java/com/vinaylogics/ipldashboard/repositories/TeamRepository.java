package com.vinaylogics.ipldashboard.repositories;

import com.vinaylogics.ipldashboard.models.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {
    Optional<Team> findTeamByTeamName(String teamName);
}