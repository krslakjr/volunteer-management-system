package com.example.participationservice.controller;

import com.example.participationservice.exception.ResourceNotFoundException;
import com.example.participationservice.models.Participation;
import com.example.participationservice.service.ActivityClientService;
import com.example.participationservice.service.ParticipationService;
import com.example.participationservice.service.UserClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/participations")
public class ParticipationController {

    private final ParticipationService participationService;
    private final UserClientService userClientService;
    private final ActivityClientService activityClientService;

    public ParticipationController(UserClientService client, ActivityClientService activityClientService, ParticipationService service) {
        this.userClientService = client;
        this.participationService = service;
        this.activityClientService = activityClientService;
    }

    @GetMapping
    public List<Participation> getAllParticipations() {
        return participationService.getAllParticipations();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Participation> getParticipationById(@PathVariable Long id) {
        Optional<Participation> participation = participationService.getParticipationById(id);
        return participation.map(ResponseEntity::ok)
        .orElseThrow(() -> new ResourceNotFoundException("Participation not found with id " + id, "id"));
    }

    @PostMapping
    public ResponseEntity<?> createParticipation(@Valid @RequestBody Participation participation) {
        try {
            Long userId = participation.getVolunteer().getVolunteerId();
            Long activityId = participation.getActivity().getActivityId();

            if (!userClientService.isValidVolunteer(userId)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("User must exist and have role Volunteer");
            }

            activityClientService.doesActivityExist(activityId);
            activityClientService.decreaseActivitySlot(activityId);

            participationService.createParticipation(participation);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (ResponseStatusException ex) {
            return ResponseEntity.status(ex.getStatusCode())
                    .body(ex.getReason());
        }
    }

    @PutMapping("/{id}")
public ResponseEntity<Participation> updateParticipation(@PathVariable Long id, @Valid @RequestBody Participation participationDetails) {
    try {
        Participation updatedParticipation = participationService.updateParticipation(id, participationDetails);
        return ResponseEntity.ok(updatedParticipation);
    } catch (ResourceNotFoundException ex) {
        throw ex; 
    }
}


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParticipation(@PathVariable Long id) {
        if (participationService.deleteParticipation(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/paginated")
public ResponseEntity<Page<Participation>> getPaginatedParticipations(Pageable pageable) {
    return ResponseEntity.ok(participationService.getParticipationsPaginated(pageable));
}
}