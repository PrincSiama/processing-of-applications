package dev.sosnovsky.applications;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ProcessingOfApplications {

	public static void main(String[] args) {
		SpringApplication.run(ProcessingOfApplications.class, args);
	}

}
