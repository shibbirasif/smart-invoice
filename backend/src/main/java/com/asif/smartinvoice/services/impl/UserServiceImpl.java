package com.asif.smartinvoice.services.impl;

import com.asif.smartinvoice.entities.Role;
import com.asif.smartinvoice.entities.User;
import com.asif.smartinvoice.entities.UserRole;
import com.asif.smartinvoice.repositories.RoleRepository;
import com.asif.smartinvoice.repositories.UserRepository;
import com.asif.smartinvoice.repositories.UserRoleRepository;
import com.asif.smartinvoice.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;

    @Override
    public User createUser(User user) {
        if (user.getEmail() != null && existsByEmail(user.getEmail())) {
            throw new RuntimeException("User with email already exists");
        }
        if (user.getPhone() != null && existsByPhone(user.getPhone())) {
            throw new RuntimeException("User with phone already exists");
        }
        return userRepository.save(user);
    }

    @Override
    public User updateUser(UUID id, User user) {
        User existingUser = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getName() != null) {
            existingUser.setName(user.getName());
        }
        if (user.getEmail() != null && !user.getEmail().equals(existingUser.getEmail())) {
            if (existsByEmail(user.getEmail())) {
                throw new RuntimeException("User with email already exists");
            }
            existingUser.setEmail(user.getEmail());
        }
        if (user.getPhone() != null && !user.getPhone().equals(existingUser.getPhone())) {
            if (existsByPhone(user.getPhone())) {
                throw new RuntimeException("User with phone already exists");
            }
            existingUser.setPhone(user.getPhone());
        }

        return userRepository.save(existingUser);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getUserById(UUID id) {
        return userRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getUserByPhone(String phone) {
        return userRepository.findByPhone(phone);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getUserByEmailOrPhone(String emailOrPhone) {
        return userRepository.findByEmailOrPhone(emailOrPhone);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByPhone(String phone) {
        return userRepository.existsByPhone(phone);
    }

    @Override
    public void assignRoleToUser(UUID userId, String roleName) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        Role role = roleRepository.findByName(roleName)
            .orElseThrow(() -> new RuntimeException("Role not found"));

        if (!userRoleRepository.existsByUserIdAndRoleId(userId, role.getId())) {
            UserRole userRole = UserRole.builder()
                .userId(userId)
                .roleId(role.getId())
                .build();
            userRoleRepository.save(userRole);
        }
    }

    @Override
    public void removeRoleFromUser(UUID userId, String roleName) {
        Role role = roleRepository.findByName(roleName)
            .orElseThrow(() -> new RuntimeException("Role not found"));
        userRoleRepository.deleteByUserIdAndRoleId(userId, role.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getUserRoles(UUID userId) {
        return userRoleRepository.findByUserIdWithRole(userId)
            .stream()
            .map(userRole -> userRole.getRole().getName())
            .collect(Collectors.toList());
    }
}
