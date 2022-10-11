package com.p3ngine.br.aimservice.service;

import com.p3ngine.br.aimservice.dto.UserAuthDTO;
import org.keycloak.representations.AccessTokenResponse;

public interface IUserService {

    public AccessTokenResponse generateToken(UserAuthDTO userAuthDTO) throws Exception;

    public void verifyToken(AccessTokenResponse token) throws Exception;

    public AccessTokenResponse refreshToken(AccessTokenResponse token) throws Exception;

}
