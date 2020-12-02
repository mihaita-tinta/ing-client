package com.ing.client;

import com.ing.client.config.ClientConfigurationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ClientConfigurationProperties.class)
public class IngStarterClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(IngStarterClientApplication.class, args);
	}

}
