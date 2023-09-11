package com.mx.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class Application {

	@Bean
	AuditorAware<Long> auditorProvider(){
		return new AuditorAwareImpl();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
