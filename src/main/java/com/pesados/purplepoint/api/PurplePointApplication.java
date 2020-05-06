package com.pesados.purplepoint.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

@SpringBootApplication
public class PurplePointApplication {

	public static void main(String[] args) {
		SpringApplication.run(PurplePointApplication.class, args);
	}

	@Bean
	public StandardServletMultipartResolver multipartResolver() {
	    return new StandardServletMultipartResolver();
	}

}
