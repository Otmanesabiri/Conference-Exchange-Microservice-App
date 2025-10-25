package org.glsid.exchange_conferce_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ExchangeConferceAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExchangeConferceAppApplication.class, args);
	}

}
