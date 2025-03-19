package com.example.activitymanagement.service;

import com.example.activitymanagement.models.TeamActivity;
import com.example.activitymanagement.repository.TeamActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamActivityService {

    private final TeamActivityRepository teamActivityRepository;

    @Autowired
    public TeamActivityService(TeamActivityRepository teamActivityRepository) {
        this.teamActivityRepository = teamActivityRepository;
    }

    // Get all TeamActivities
    public List<TeamActivity> getAllTeamActivities() {
        return teamActivityRepository.findAll();
    }

    // Get a specific TeamActivity by ID
    public Optional<TeamActivity> getTeamActivityById(Long id) {
        return teamActivityRepository.findById(id);
    }

    // Create a new TeamActivity
    public TeamActivity createTeamActivity(TeamActivity teamActivity) {
        return teamActivityRepository.save(teamActivity);
    }

    // Update an existing TeamActivity
    public Optional<TeamActivity> updateTeamActivity(Long id, TeamActivity teamActivity) {
        return teamActivityRepository.findById(id)
                .map(existingTeamActivity -> {
                    existingTeamActivity.setTeam(teamActivity.getTeam());
                    existingTeamActivity.setActivity(teamActivity.getActivity());
                    return teamActivityRepository.save(existingTeamActivity);
                });
    }

    // Delete a TeamActivity
    public boolean deleteTeamActivity(Long id) {
        return teamActivityRepository.findById(id)
                .map(existingTeamActivity -> {
                    teamActivityRepository.delete(existingTeamActivity);
                    return true;
                })
                .orElse(false);
    }
}
