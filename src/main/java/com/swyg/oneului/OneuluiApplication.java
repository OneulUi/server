package com.swyg.oneului;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class OneuluiApplication {

	public static void main(String[] args) {
		SpringApplication.run(OneuluiApplication.class, args);
	}

}
