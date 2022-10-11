package com.p3ngine.br.aimservice.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

public class WebSecurityConfig {//extends WebSecurityConfigurerAdapter {
//    @Autowired
//    private ObjectMapper objectMapper = new ObjectMapper();
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests((authz) -> authz
//                        .anyRequest().authenticated()
//                )
//                .httpBasic(withDefaults());
//        return http.build();
//    }
//
//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring().antMatchers("*/users/token");
//    }
}
