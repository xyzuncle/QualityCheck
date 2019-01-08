package com.quality;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
public class QualityApplication {

	public static void main(String[] args) {
		SpringApplication.run(QualityApplication.class, args);
	}
}
