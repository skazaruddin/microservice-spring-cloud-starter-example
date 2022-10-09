package io.azar.ms.example.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaInitializer {

	public static void main(String[] args) {
		SpringApplication.run(EurekaInitializer.class, args);
	}

}
