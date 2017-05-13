package com.ekreative.hackathon.challenge.android;

import com.ekreative.hackathon.challenge.model.ModelConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import(ModelConfig.class)
@SpringBootApplication
public class AndroidApplication {

	public static void main(String[] args) {
		SpringApplication.run(AndroidApplication.class, args);
	}
}
