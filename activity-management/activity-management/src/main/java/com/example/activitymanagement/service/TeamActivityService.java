package com.example.activitymanagement.service;

import com.example.activitymanagement.exception.ResourceNotFoundException;
import com.example.activitymanagement.models.TeamActivity;
import com.example.activitymanagement.repository.TeamActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamActivityService {

    private final TeamActivityRepository teamActivityRepository;

    @Autowired
    public TeamActivityService(TeamActivityRepository teamActivityRepository) {
        this.teamActivityRepository = teamActivityRepository;
    }

    public List<TeamActivity> getAllTeamActivities() {
        return teamActivityRepository.findAll();
    }

    public TeamActivity getTeamActivityById(Long id) {
        return teamActivityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Team activity with ID " + id + " not found"));
    }

    public TeamActivity createTeamActivity(TeamActivity teamActivity) {
        return teamActivityRepository.save(teamActivity);
    }

    public TeamActivity updateTeamActivity(Long id, TeamActivity teamActivity) {
        TeamActivity existingTeamActivity = teamActivityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Team activity with ID " + id + " not found"));

        existingTeamActivity.setTeam(teamActivity.getTeam());
        existingTeamActivity.setActivity(teamActivity.getActivity());

        return teamActivityRepository.save(existingTeamActivity);
    }

    public void deleteTeamActivity(Long id) {
        TeamActivity existingTeamActivity = teamActivityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Team activity with ID " + id + " not found"));

        teamActivityRepository.delete(existingTeamActivity);
    }
}