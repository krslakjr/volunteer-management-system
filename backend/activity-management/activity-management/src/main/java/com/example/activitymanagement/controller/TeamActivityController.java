package com.example.activitymanagement.controller;

import com.example.activitymanagement.models.TeamActivity;
import com.example.activitymanagement.service.TeamActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/team-activities")
public class TeamActivityController {

    private final TeamActivityService teamActivityService;

    @Autowired
    public TeamActivityController(TeamActivityService teamActivityService) {
        this.teamActivityService = teamActivityService;
    }

    @GetMapping
    public ResponseEntity<List<TeamActivity>> getAllTeamActivities() {
        List<TeamActivity> teamActivities = teamActivityService.getAllTeamActivities();
        return ResponseEntity.ok(teamActivities);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamActivity> getTeamActivityById(@PathVariable Long id) {
        TeamActivity teamActivity = teamActivityService.getTeamActivityById(id);
        return ResponseEntity.ok(teamActivity);
    }

    @PostMapping
    public ResponseEntity<TeamActivity> createTeamActivity(@RequestBody TeamActivity teamActivity) {
        TeamActivity createdTeamActivity = teamActivityService.createTeamActivity(teamActivity);
        return new ResponseEntity<>(createdTeamActivity, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeamActivity> updateTeamActivity(@PathVariable Long id, @RequestBody TeamActivity teamActivity) {
        TeamActivity updatedTeamActivity = teamActivityService.updateTeamActivity(id, teamActivity);
        return ResponseEntity.ok(updatedTeamActivity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeamActivity(@PathVariable Long id) {
        teamActivityService.deleteTeamActivity(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}