package com.heech.hellcoding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class HellcodingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HellcodingApplication.class, args);
	}

}
