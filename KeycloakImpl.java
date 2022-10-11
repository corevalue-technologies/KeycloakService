package com.p3ngine.br.aimservice.service.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.p3ngine.br.aimservice.config.BRMessageSource;
import com.p3ngine.br.aimservice.dto.UserAuthDTO;
import com.p3ngine.br.aimservice.dto.VerifyTokenDTO;
import com.p3ngine.br.aimservice.mapper.ObjectToUrlEncodedConverter;
import com.p3ngine.br.aimservice.service.IKeycloak;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.token.TokenManager;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.RefreshToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.keycloak.util.JsonSerialization.mapper;

@Service
public class KeycloakImpl implements IKeycloak {

    @Value("${keycloak.auth-server-url}")
    private String host;

    @Value("${keycloak.realm}")
    private String realmName;

    @Value("${keycloak.resource}")
    private String clientId;

    @Value("${keycloak.credentials.secret}")
    private String clientSecret;

    private static Logger logger = LoggerFactory.getLogger(KeycloakImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private BRMessageSource source;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public AccessTokenResponse generateToken(UserAuthDTO userAuthDTO){
        Keycloak instance = Keycloak.getInstance(host, realmName, userAuthDTO.getUsername(), userAuthDTO.getPassword(),clientId, clientSecret);
        TokenManager tokenmanager = instance.tokenManager();
        return tokenmanager.getAccessToken();
    }

    @Override
    public void verifyToken(AccessTokenResponse token) throws Exception {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            Map<String, String> map = new LinkedHashMap();
            map.put("client_id", clientId);
            map.put("client_secret", clientSecret);
            map.put("token", token.getToken());
            map.put("token_type_hint", "requesting_party_token");

            restTemplate.getMessageConverters().add(new ObjectToUrlEncodedConverter(mapper));

            String url = getIntrospectionUrl();

            ResponseEntity response = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity(map, headers), String.class);

            VerifyTokenDTO status = objectMapper.readValue(response.getBody().toString(), VerifyTokenDTO.class);

            if(status.getActive()){
                logger.info(source.getMessage("response.token_verified"));
                return;
            }else{
                logger.info(source.getMessage("error.token_expired"));
                throw new Exception(source.getMessage("error.token_expired"));
            }
        }catch (Exception e){
            logger.error("Error occurred: "+e);
            throw new Exception(source.getMessage("error.invalid_request"));
        }
    }

    @Override
    public AccessTokenResponse refreshToken(AccessTokenResponse token) throws Exception {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            Map<String, String> map = new LinkedHashMap();
            map.put("client_id", clientId);
            map.put("client_secret", clientSecret);
            map.put("refresh_token", token.getRefreshToken());
            map.put("grant_type", "refresh_token");

            restTemplate.getMessageConverters().add(new ObjectToUrlEncodedConverter(mapper));

            String url = getRefreshTokenUrl();

            ResponseEntity response = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity(map, headers), String.class);

            AccessTokenResponse newToken = objectMapper.readValue(response.getBody().toString(), AccessTokenResponse.class);
            logger.info(source.getMessage("response.token_refreshed"));
            return newToken;
        }catch (Exception e){
            logger.error("Error occurred: "+e);
            throw new Exception(source.getMessage("error.invalid_refresh_token"));
        }
    }

    private String getIntrospectionUrl(){
        return host+"/realms/"+realmName+"/protocol/openid-connect/token/introspect";
    }

    private String getRefreshTokenUrl(){
        return host+"/realms/"+realmName+"/protocol/openid-connect/token";
    }

}
