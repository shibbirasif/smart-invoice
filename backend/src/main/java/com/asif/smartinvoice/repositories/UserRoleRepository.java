package com.asif.smartinvoice.repositories;

import com.asif.smartinvoice.entities.UserRole;
import com.asif.smartinvoice.entities.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {

    List<UserRole> findByUserId(UUID userId);

    List<UserRole> findByRoleId(UUID roleId);

    @Query("SELECT ur FROM UserRole ur JOIN FETCH ur.role WHERE ur.userId = :userId")
    List<UserRole> findByUserIdWithRole(@Param("userId") UUID userId);

    @Query("SELECT ur FROM UserRole ur JOIN FETCH ur.user WHERE ur.roleId = :roleId")
    List<UserRole> findByRoleIdWithUser(@Param("roleId") UUID roleId);

    boolean existsByUserIdAndRoleId(UUID userId, UUID roleId);

    void deleteByUserIdAndRoleId(UUID userId, UUID roleId);
}
