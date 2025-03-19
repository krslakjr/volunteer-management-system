package com.example.activitymanagement.controller;

import com.example.activitymanagement.models.TeamActivity;
import com.example.activitymanagement.service.TeamActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/team-activities")
public class TeamActivityController {

    private final TeamActivityService teamActivityService;

    @Autowired
    public TeamActivityController(TeamActivityService teamActivityService) {
        this.teamActivityService = teamActivityService;
    }

    // Get all TeamActivities
    @GetMapping
    public ResponseEntity<List<TeamActivity>> getAllTeamActivities() {
        List<TeamActivity> teamActivities = teamActivityService.getAllTeamActivities();
        return new ResponseEntity<>(teamActivities, HttpStatus.OK);
    }

    // Get a specific TeamActivity by ID
    @GetMapping("/{id}")
    public ResponseEntity<TeamActivity> getTeamActivityById(@PathVariable Long id) {
        Optional<TeamActivity> teamActivity = teamActivityService.getTeamActivityById(id);
        return teamActivity.map(activity -> new ResponseEntity<>(activity, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Create a new TeamActivity
    @PostMapping
    public ResponseEntity<TeamActivity> createTeamActivity(@RequestBody TeamActivity teamActivity) {
        TeamActivity createdTeamActivity = teamActivityService.createTeamActivity(teamActivity);
        return new ResponseEntity<>(createdTeamActivity, HttpStatus.CREATED);
    }

    // Update an existing TeamActivity
    @PutMapping("/{id}")
    public ResponseEntity<TeamActivity> updateTeamActivity(@PathVariable Long id, @RequestBody TeamActivity teamActivity) {
        Optional<TeamActivity> updatedTeamActivity = teamActivityService.updateTeamActivity(id, teamActivity);
        return updatedTeamActivity.map(activity -> new ResponseEntity<>(activity, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Delete a TeamActivity
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeamActivity(@PathVariable Long id) {
        boolean isDeleted = teamActivityService.deleteTeamActivity(id);
        return isDeleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
