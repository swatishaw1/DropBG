package com.example.DropBGBackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class DropBgApplication {

	public static void main(String[] args) {
		SpringApplication.run(DropBgApplication.class, args);
	}

}
