package com.mx.api;

import java.util.Date;
import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
@SpringBootConfiguration
@ComponentScan
@Slf4j
public class Application implements CommandLineRunner{

	@Bean
	AuditorAware<Long> auditorProvider(){
		return new AuditorAwareImpl();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@PostConstruct
    public void init(){
		//Setting Spring Boot SetTimeZone
		final String defaultTimeZone = "America/Mexico_City";
		TimeZone.setDefault(TimeZone.getTimeZone(defaultTimeZone));
		log.info("Default Time Zone: " + defaultTimeZone);
		log.info("Date: " + new Date());
    }
	
	@Override
	public void run(String... args) {
		log.info("RUN CFSOUL REST SERVICES APP");
	}


}
