package com.asif.smartinvoice.services;

import com.asif.smartinvoice.entities.Role;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoleService {

    Role createRole(Role role);

    Role updateRole(UUID id, Role role);

    Optional<Role> getRoleById(UUID id);

    Optional<Role> getRoleByName(String name);

    List<Role> getAllRoles();

    void deleteRole(UUID id);

    boolean existsByName(String name);
}
