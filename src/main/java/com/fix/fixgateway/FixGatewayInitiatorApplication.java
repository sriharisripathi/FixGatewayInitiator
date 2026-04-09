package com.fix.fixgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.fix.fixgateway"})
public class FixGatewayInitiatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(FixGatewayInitiatorApplication.class, args);
	}

}
