package com.asif.smartinvoice.services;

import com.asif.smartinvoice.entities.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

    User createUser(User user);

    User updateUser(UUID id, User user);

    Optional<User> getUserById(UUID id);

    Optional<User> getUserByEmail(String email);

    Optional<User> getUserByPhone(String phone);

    Optional<User> getUserByEmailOrPhone(String emailOrPhone);

    List<User> getAllUsers();

    void deleteUser(UUID id);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    void assignRoleToUser(UUID userId, String roleName);

    void removeRoleFromUser(UUID userId, String roleName);

    List<String> getUserRoles(UUID userId);
}
