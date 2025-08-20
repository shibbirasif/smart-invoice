package com.asif.smartinvoice.services.impl;

import com.asif.smartinvoice.entities.Role;
import com.asif.smartinvoice.repositories.RoleRepository;
import com.asif.smartinvoice.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role createRole(Role role) {
        if (existsByName(role.getName())) {
            throw new RuntimeException("Role with name already exists");
        }
        return roleRepository.save(role);
    }

    @Override
    public Role updateRole(UUID id, Role role) {
        Role existingRole = roleRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Role not found"));

        if (role.getName() != null && !role.getName().equals(existingRole.getName())) {
            if (existsByName(role.getName())) {
                throw new RuntimeException("Role with name already exists");
            }
            existingRole.setName(role.getName());
        }

        return roleRepository.save(existingRole);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Role> getRoleById(UUID id) {
        return roleRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Role> getRoleByName(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public void deleteRole(UUID id) {
        if (!roleRepository.existsById(id)) {
            throw new RuntimeException("Role not found");
        }
        roleRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByName(String name) {
        return roleRepository.existsByName(name);
    }
}
