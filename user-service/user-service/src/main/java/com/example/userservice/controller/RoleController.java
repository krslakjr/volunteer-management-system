package com.example.userservice.controller;

import com.example.userservice.models.Role;
import com.example.userservice.repository.RoleRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleRepository roleRepository;

    public RoleController(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @GetMapping("/{roleName}")
    public ResponseEntity<Role> getRoleByName(@PathVariable String roleName) {
        Optional<Role> roleOpt = roleRepository.findByRoleName(roleName);
        return roleOpt.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/id/{id}")
public ResponseEntity<Role> getRoleById(@PathVariable Long id) {
    Optional<Role> roleOpt = roleRepository.findById(id);
    return roleOpt.map(ResponseEntity::ok)
                  .orElseGet(() -> ResponseEntity.notFound().build());
}

    @GetMapping
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        return ResponseEntity.ok(roles);
    }

    @PostMapping
    public ResponseEntity<Role> createRole(@RequestBody Role role) {
        Role saved = roleRepository.save(role);
        return ResponseEntity.status(201).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Role> updateRole(@PathVariable Long id, @RequestBody Role role) {
        Optional<Role> roleOpt = roleRepository.findById(id);
        if (roleOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Role existing = roleOpt.get();
        existing.setRoleName(role.getRoleName());
        Role updated = roleRepository.save(existing);
        return ResponseEntity.ok(updated);
    }

  @DeleteMapping("/{id}")
public ResponseEntity<HttpStatus> deleteRole(@PathVariable Long id) {
    if (!roleRepository.existsById(id)) {
        return ResponseEntity.notFound().build();
    }
    roleRepository.deleteById(id);
    return ResponseEntity.ok(HttpStatus.OK);
}

}
