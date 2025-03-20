package com.example.participationservice.service;

import com.example.participationservice.models.Participation;
import com.example.participationservice.repository.ParticipationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParticipationService {

    @Autowired
    private ParticipationRepository participationRepository;

    public List<Participation> getAllParticipations() {
        return participationRepository.findAll();
    }

    public Optional<Participation> getParticipationById(Long id) {
        return participationRepository.findById(id);
    }

    public Participation createParticipation(Participation participation) {
        return participationRepository.save(participation);
    }

    public Participation updateParticipation(Long id, Participation participationDetails) {
        Optional<Participation> optionalParticipation = participationRepository.findById(id);

        if (optionalParticipation.isPresent()) {
            Participation participation = optionalParticipation.get();
            participation.setVolunteer(participationDetails.getVolunteer());
            participation.setActivity(participationDetails.getActivity());
            participation.setRegistrationDate(participationDetails.getRegistrationDate());
            participation.setAttendanceStatus(participationDetails.getAttendanceStatus());
            return participationRepository.save(participation);
        }
        return null;
    }

    public boolean deleteParticipation(Long id) {
        if (participationRepository.existsById(id)) {
            participationRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
