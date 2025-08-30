package com.asif.smartinvoice.repositories;

import com.asif.smartinvoice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    Optional<User> findByPhone(String phone);

    @Query("SELECT u FROM User u WHERE u.email = :emailOrPhone OR u.phone = :emailOrPhone")
    Optional<User> findByEmailOrPhone(@Param("emailOrPhone") String emailOrPhone);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);
}
