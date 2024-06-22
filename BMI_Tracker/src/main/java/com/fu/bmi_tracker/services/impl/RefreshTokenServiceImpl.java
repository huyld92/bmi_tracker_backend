/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.services.impl;

import com.fu.bmi_tracker.exceptions.TokenException;
import com.fu.bmi_tracker.model.entities.RefreshToken;
import com.fu.bmi_tracker.repository.AccountRepository;
import com.fu.bmi_tracker.repository.RefreshTokenRepository;
import com.fu.bmi_tracker.services.RefreshTokenService;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Value("${huydd.app.jwtRefreshExpirationMs}")
    private Long refreshTokenDurationMs;

    @Autowired
    private RefreshTokenRepository repository;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Iterable<RefreshToken> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<RefreshToken> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public RefreshToken save(RefreshToken t) {
        return repository.save(t);
    }

    @Override
    public RefreshToken createRefreshToken(Integer memberId) {
        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setAccount(accountRepository.findById(memberId).get());
        refreshToken.setExpiryDate(LocalDateTime.now().plusSeconds(refreshTokenDurationMs / 1000));
        refreshToken.setRefreshToken(UUID.randomUUID().toString());

        refreshToken = repository.save(refreshToken);
        return refreshToken;
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(LocalDateTime.now()) < 0) {
            repository.delete(token);
            throw new TokenException(
                    token.getRefreshToken(), 
                    "Refresh token was expired. Please make a new signin request");
        }

        return token;
    }

    @Override
    @Transactional
    public void deleteByAccountID(Integer accountID) {
        repository.deleteByAccount_AccountID(accountID);
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return repository.findByRefreshToken(token);
    }

    @Override
    @Transactional
    public void deleteAllExpiredSince(LocalDateTime now) {
        repository.deleteByExpiryDateLessThan(now);
    }

}
