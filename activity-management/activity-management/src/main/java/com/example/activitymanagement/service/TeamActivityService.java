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

    public List<TeamActivity> getAllTeamActivities() {
        return teamActivityRepository.findAll();
    }

    public Optional<TeamActivity> getTeamActivityById(Long id) {
        return teamActivityRepository.findById(id);
    }

    public TeamActivity createTeamActivity(TeamActivity teamActivity) {
        return teamActivityRepository.save(teamActivity);
    }

    public Optional<TeamActivity> updateTeamActivity(Long id, TeamActivity teamActivity) {
        return teamActivityRepository.findById(id)
                .map(existingTeamActivity -> {
                    existingTeamActivity.setTeam(teamActivity.getTeam());
                    existingTeamActivity.setActivity(teamActivity.getActivity());
                    return teamActivityRepository.save(existingTeamActivity);
                });
    }

    public boolean deleteTeamActivity(Long id) {
        return teamActivityRepository.findById(id)
                .map(existingTeamActivity -> {
                    teamActivityRepository.delete(existingTeamActivity);
                    return true;
                })
                .orElse(false);
    }
}
