package com.example.participationservice.controller;

import com.example.participationservice.models.Participation;
import com.example.participationservice.service.ParticipationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/participations")
public class ParticipationController {

    @Autowired
    private ParticipationService participationService;

    @GetMapping
    public List<Participation> getAllParticipations() {
        return participationService.getAllParticipations();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Participation> getParticipationById(@PathVariable Long id) {
        Optional<Participation> participation = participationService.getParticipationById(id);
        return participation.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Participation> createParticipation(@Valid @RequestBody Participation participation) {
        Participation createdParticipation = participationService.createParticipation(participation);
        return ResponseEntity.ok(createdParticipation);
    }

    @PutMapping("/{id}")
<<<<<<< HEAD
    public ResponseEntity<Participation> updateParticipation(@PathVariable Long id, @Valid @RequestBody Participation participationDetails) {
=======
    public ResponseEntity<Participation> updateParticipation(@PathVariable Long id,@Valid @RequestBody Participation participationDetails) {
>>>>>>> 1f92f07d26c618f4ab802b3c248b0b97d353dacb
        Participation updatedParticipation = participationService.updateParticipation(id, participationDetails);
        if (updatedParticipation != null) {
            return ResponseEntity.ok(updatedParticipation);
        } else {
            return ResponseEntity.notFound().build();
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
}
