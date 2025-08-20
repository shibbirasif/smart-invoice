package com.asif.smartinvoice.services;

import com.asif.smartinvoice.entities.AuthToken;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AuthTokenService {

    AuthToken createToken(UUID userId, String tokenType, LocalDateTime expiresAt);

    Optional<AuthToken> getTokenById(UUID id);

    Optional<AuthToken> getTokenByValue(String token);

    Optional<AuthToken> getValidToken(String token);

    List<AuthToken> getTokensByUserId(UUID userId);

    List<AuthToken> getValidTokensByUserIdAndType(UUID userId, String tokenType);

    List<AuthToken> getExpiredTokens();

    void markTokenAsUsed(String token);

    void deleteToken(UUID id);

    int deleteExpiredTokens();

    int deleteTokensByUserIdAndType(UUID userId, String tokenType);

    boolean isTokenValid(String token);
}
