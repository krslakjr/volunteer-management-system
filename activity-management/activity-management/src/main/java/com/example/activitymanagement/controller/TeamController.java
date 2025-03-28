package com.example.activitymanagement.controller;

import com.example.activitymanagement.exception.ResourceNotFoundException;
import com.example.activitymanagement.models.Team;
import com.example.activitymanagement.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/teams")
public class TeamController {

    private final TeamService teamService;

    @Autowired
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping
    public List<Team> getAllTeams() {
        return teamService.getAllTeams();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Team> getTeamById(@PathVariable Long id) {
        Team team = teamService.getTeamById(id);  
        return new ResponseEntity<>(team, HttpStatus.OK);  
    }

    @PostMapping
    public ResponseEntity<Team> createTeam(@Valid @RequestBody Team team) {
        try {
            Team savedTeam = teamService.createTeam(team);
            return new ResponseEntity<>(savedTeam, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new RuntimeException("Internal server error while creating team");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Team> updateTeam(@PathVariable Long id, @Valid @RequestBody Team team) {
        Optional<Team> updatedTeam = teamService.updateTeam(id, team);
        if (updatedTeam.isPresent()) {
            return new ResponseEntity<>(updatedTeam.get(), HttpStatus.OK);
        }
        throw new ResourceNotFoundException("Team with ID " + id + " not found for update");
    }
    

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteTeam(@PathVariable Long id) {
        boolean isDeleted = teamService.deleteTeam(id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        throw new ResourceNotFoundException("Team with ID " + id + " not found for deletion");
    }
}