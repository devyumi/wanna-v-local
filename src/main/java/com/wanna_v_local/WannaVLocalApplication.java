package com.wanna_v_local;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WannaVLocalApplication {

	public static void main(String[] args) {
		SpringApplication.run(WannaVLocalApplication.class, args);
	}

}
