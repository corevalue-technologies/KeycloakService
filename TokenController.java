package com.p3ngine.br.aimservice.controller;

import com.p3ngine.br.aimservice.dto.UserAuthDTO;
import com.p3ngine.br.aimservice.service.IUserService;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/token")
public class TokenController {
    @Autowired
    private IUserService userService;

    @PostMapping
    public ResponseEntity<AccessTokenResponse> generateToken(@RequestBody UserAuthDTO userAuthDTO) throws Exception {
        return ResponseEntity.ok(userService.generateToken(userAuthDTO));
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verifyToken(@RequestBody AccessTokenResponse token) throws Exception {
        userService.verifyToken(token);
        return ResponseEntity.ok("");
    }

    @PostMapping("/refresh")
    public ResponseEntity<AccessTokenResponse> refreshToken(@RequestBody AccessTokenResponse token) throws Exception {
        return ResponseEntity.ok(userService.refreshToken(token));
    }
}
