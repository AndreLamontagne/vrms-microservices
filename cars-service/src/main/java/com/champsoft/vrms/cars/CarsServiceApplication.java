package com.champsoft.vrms.cars;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.champsoft.vrms")
public class CarsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarsServiceApplication.class, args);
	}

}

