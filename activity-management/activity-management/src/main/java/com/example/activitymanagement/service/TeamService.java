package com.example.activitymanagement.service;

import com.example.activitymanagement.models.Team;
import com.example.activitymanagement.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamService {

    private final TeamRepository teamRepository;

    @Autowired
    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    // Get all teams
    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    // Get Team by ID
    public Optional<Team> getTeamById(Long id) {
        return teamRepository.findById(id);
    }

    // Create a new Team
    public Team createTeam(Team team) {
        return teamRepository.save(team);
    }

    // Update an existing Team
    public Optional<Team> updateTeam(Long id, Team team) {
        return teamRepository.findById(id)
                .map(existingTeam -> {
                    existingTeam.setTeamName(team.getTeamName());
                    existingTeam.setTeamActivities(team.getTeamActivities());
                    return teamRepository.save(existingTeam);
                });
    }

    // Delete a Team
    public boolean deleteTeam(Long id) {
        return teamRepository.findById(id)
                .map(existingTeam -> {
                    teamRepository.delete(existingTeam);
                    return true;
                })
                .orElse(false);
    }
}
