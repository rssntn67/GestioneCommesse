package it.arsinfo.gc;


import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GestioneCommesseApplication {

	private static final Logger log = LoggerFactory.getLogger(GestioneCommesseApplication.class);
		
		
	public static void main(String[] args) {
		SpringApplication.run(GestioneCommesseApplication.class, args);
	}
	
	@Bean
	@Transactional
	public CommandLineRunner loadData(
								) {
		return (args) -> {
			// save a couple of customers
						

		};
	}
}
