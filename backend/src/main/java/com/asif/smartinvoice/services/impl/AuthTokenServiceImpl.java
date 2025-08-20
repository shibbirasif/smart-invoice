package com.asif.smartinvoice.services.impl;

import com.asif.smartinvoice.entities.AuthToken;
import com.asif.smartinvoice.repositories.AuthTokenRepository;
import com.asif.smartinvoice.services.AuthTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthTokenServiceImpl implements AuthTokenService {

    private final AuthTokenRepository authTokenRepository;
    private static final SecureRandom secureRandom = new SecureRandom();

    @Override
    public AuthToken createToken(UUID userId, String tokenType, LocalDateTime expiresAt) {
        String tokenValue = generateSecureToken();

        AuthToken authToken = AuthToken.builder()
            .userId(userId)
            .token(tokenValue)
            .tokenType(tokenType)
            .expiresAt(expiresAt)
            .used(false)
            .build();

        return authTokenRepository.save(authToken);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AuthToken> getTokenById(UUID id) {
        return authTokenRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AuthToken> getTokenByValue(String token) {
        return authTokenRepository.findByToken(token);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AuthToken> getValidToken(String token) {
        return authTokenRepository.findValidToken(token);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuthToken> getTokensByUserId(UUID userId) {
        return authTokenRepository.findByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuthToken> getValidTokensByUserIdAndType(UUID userId, String tokenType) {
        return authTokenRepository.findValidTokensByUserIdAndType(userId, tokenType);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuthToken> getExpiredTokens() {
        return authTokenRepository.findExpiredTokens();
    }

    @Override
    public void markTokenAsUsed(String token) {
        int updatedRows = authTokenRepository.markTokenAsUsed(token);
        if (updatedRows == 0) {
            throw new RuntimeException("Token not found or already used");
        }
    }

    @Override
    public void deleteToken(UUID id) {
        if (!authTokenRepository.existsById(id)) {
            throw new RuntimeException("Token not found");
        }
        authTokenRepository.deleteById(id);
    }

    @Override
    public int deleteExpiredTokens() {
        LocalDateTime cutoffTime = LocalDateTime.now().minusDays(7); // Delete tokens expired for more than 7 days
        return authTokenRepository.deleteExpiredTokens(cutoffTime);
    }

    @Override
    public int deleteTokensByUserIdAndType(UUID userId, String tokenType) {
        return authTokenRepository.deleteByUserIdAndTokenType(userId, tokenType);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isTokenValid(String token) {
        return getValidToken(token).isPresent();
    }

    private String generateSecureToken() {
        byte[] randomBytes = new byte[32];
        secureRandom.nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }
}
