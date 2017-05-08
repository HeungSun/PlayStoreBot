package io.userhabit.kongsuny;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@EnableAutoConfiguration
@EnableBatchProcessing
@ImportResource("classpath:application-context.xml")
public class PlaystorebotApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlaystorebotApplication.class, args);
	}
}
