package com.p3ngine.br.aimservice.service.impl;

import com.p3ngine.br.aimservice.dto.UserAuthDTO;
import com.p3ngine.br.aimservice.service.IKeycloak;
import com.p3ngine.br.aimservice.service.IUserService;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IKeycloak keycloakService;

    @Override
    public AccessTokenResponse generateToken(UserAuthDTO userAuthDTO) throws Exception {
        if(!userAuthDTO.getVersion().equals("1.0.0")){
            throw new Exception("Version Mismatch");
        }
        return keycloakService.generateToken(userAuthDTO);
    }

    @Override
    public void verifyToken(AccessTokenResponse token) throws Exception {
        keycloakService.verifyToken(token);
    }

    @Override
    public AccessTokenResponse refreshToken(AccessTokenResponse token) throws Exception {
        return keycloakService.refreshToken(token);
    }
}
