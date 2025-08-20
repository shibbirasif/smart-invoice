package com.asif.smartinvoice.repositories;

import com.asif.smartinvoice.entities.AuthToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AuthTokenRepository extends JpaRepository<AuthToken, UUID> {

    Optional<AuthToken> findByToken(String token);

    List<AuthToken> findByUserId(UUID userId);

    List<AuthToken> findByUserIdAndTokenType(UUID userId, String tokenType);

    @Query("SELECT at FROM AuthToken at WHERE at.token = :token AND at.used = false AND at.expiresAt > CURRENT_TIMESTAMP")
    Optional<AuthToken> findValidToken(@Param("token") String token);

    @Query("SELECT at FROM AuthToken at WHERE at.userId = :userId AND at.tokenType = :tokenType AND at.used = false AND at.expiresAt > CURRENT_TIMESTAMP")
    List<AuthToken> findValidTokensByUserIdAndType(@Param("userId") UUID userId, @Param("tokenType") String tokenType);

    @Query("SELECT at FROM AuthToken at WHERE at.expiresAt < CURRENT_TIMESTAMP")
    List<AuthToken> findExpiredTokens();

    @Modifying
    @Query("UPDATE AuthToken at SET at.used = true WHERE at.token = :token")
    int markTokenAsUsed(@Param("token") String token);

    @Modifying
    @Query("DELETE FROM AuthToken at WHERE at.expiresAt < :expiresBefore")
    int deleteExpiredTokens(@Param("expiresBefore") LocalDateTime expiresBefore);

    @Modifying
    @Query("DELETE FROM AuthToken at WHERE at.userId = :userId AND at.tokenType = :tokenType")
    int deleteByUserIdAndTokenType(@Param("userId") UUID userId, @Param("tokenType") String tokenType);
}
