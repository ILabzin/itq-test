package com.labzin.documentservice.itqtestapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DocumentServiceItqTestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(DocumentServiceItqTestApiApplication.class, args);
	}

}
