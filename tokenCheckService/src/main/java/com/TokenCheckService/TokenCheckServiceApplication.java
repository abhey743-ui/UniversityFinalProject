package com.TokenCheckService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication(exclude = {
        org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration.class
})
public class TokenCheckServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TokenCheckServiceApplication.class, args);
	}

}
