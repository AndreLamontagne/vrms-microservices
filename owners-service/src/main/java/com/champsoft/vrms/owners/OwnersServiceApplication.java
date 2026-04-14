package com.champsoft.vrms.owners;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.champsoft.vrms")
public class OwnersServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OwnersServiceApplication.class, args);
	}

}

