package com.vinaylogics.ipldashboard.controllers;

import com.vinaylogics.ipldashboard.models.Team;
import com.vinaylogics.ipldashboard.services.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/team")
public class TeamController {

    private final TeamService service;

    @GetMapping("/{teamName}")
    public Team getTeam(@PathVariable String teamName){
        return service.getTeam(teamName);
    }
}
