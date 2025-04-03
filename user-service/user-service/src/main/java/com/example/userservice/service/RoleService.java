package com.example.userservice.service;

import com.example.userservice.models.Role;
import com.example.userservice.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;


    public List<Role> getByRoleName(String name) {
        return roleRepository.findByRoleName(name);
    }

    public List<Role> getAllRoles(Pageable pageable) {
        Page<Role> page = roleRepository.findAll(pageable);
        return page.getContent();
    }

    public Optional<Role> getRoleById(Long id) {
        return roleRepository.findById(id);
    }

    @Transactional
    public Role createRole(Role role) {
        return roleRepository.save(role);
    }
    
    @Transactional
    public Role updateRole(Long id, Role role) {
        if (roleRepository.existsById(id)) {
            role.setRoleId(id); 
            return roleRepository.save(role);
        }
        return null; 
    }

    @Transactional
    public boolean deleteRole(Long id) {
        if (roleRepository.existsById(id)) {
            roleRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
