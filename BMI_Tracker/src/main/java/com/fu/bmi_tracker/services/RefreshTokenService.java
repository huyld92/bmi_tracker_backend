/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fu.bmi_tracker.services;

import com.fu.bmi_tracker.model.entities.RefreshToken;
import java.util.Optional;

/**
 *
 * @author Duc Huy
 */
public interface RefreshTokenService extends GeneralService<RefreshToken> {

    public Optional<RefreshToken> findByToken(String token);

    public RefreshToken createRefreshToken(Integer userId);

    public RefreshToken verifyExpiration(RefreshToken token);

    public void deleteByAccountID(Integer accountID);
}
