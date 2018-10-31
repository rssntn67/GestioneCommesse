package it.arsinfo.gc;


import java.math.BigDecimal;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import it.arsinfo.gc.entity.Commessa;
import it.arsinfo.gc.repository.CommessaDao;

@SpringBootApplication
public class GestioneCommesseApplication {

    
	private static final Logger log = LoggerFactory.getLogger(GestioneCommesseApplication.class);
		
		
	public static void main(String[] args) {
		SpringApplication.run(GestioneCommesseApplication.class, args);
	}
	
	@Bean
	@Transactional
	public CommandLineRunner loadData(CommessaDao commessaDao
								) {
		return (args) -> {
		    Commessa commessa1 = new Commessa();
		    commessa1.setNome("ABC123");
                    commessa1.setDescr("ZVF");
                    commessa1.setImporto(new BigDecimal(100000000));
                    commessaDao.save(commessa1);
		};
	}
}
