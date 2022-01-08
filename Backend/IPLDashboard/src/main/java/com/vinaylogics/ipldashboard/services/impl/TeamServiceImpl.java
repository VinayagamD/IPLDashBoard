package com.vinaylogics.ipldashboard.services.impl;

import com.vinaylogics.ipldashboard.exceptions.DataNotFoundException;
import com.vinaylogics.ipldashboard.models.Team;
import com.vinaylogics.ipldashboard.repositories.MatchRepository;
import com.vinaylogics.ipldashboard.repositories.TeamRepository;
import com.vinaylogics.ipldashboard.services.TeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final TeamRepository repository;
    private final MatchRepository matchRepository;


    @Override
    public Team getTeam(String teamName) {
        Team team = repository.findTeamByTeamName(teamName).orElseThrow(() -> new DataNotFoundException("Team with name "+teamName+ " not found"));
        team.setMatches(matchRepository.findLatestMatchesByTeam(teamName,4));
        return team;
    }
}
