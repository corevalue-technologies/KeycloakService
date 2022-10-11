package com.p3ngine.br.aimservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableAsync
@EnableWebMvc
public class AimServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AimServiceApplication.class, args);
	}

}
