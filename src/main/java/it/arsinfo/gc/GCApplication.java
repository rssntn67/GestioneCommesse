package it.arsinfo.gc;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import it.arsinfo.gc.entity.Commessa;
import it.arsinfo.gc.entity.Resoconto;
import it.arsinfo.gc.entity.Resoconto.Tipologia;
import it.arsinfo.gc.entity.UserInfo;
import it.arsinfo.gc.entity.UserInfo.Role;
import it.arsinfo.gc.entity.VariazioneCommessa;
import it.arsinfo.gc.entity.VoceCosto;
import it.arsinfo.gc.repository.CommessaDao;
import it.arsinfo.gc.repository.ResocontoDao;
import it.arsinfo.gc.repository.UserInfoDao;
import it.arsinfo.gc.repository.VariazioneCommessaDao;
import it.arsinfo.gc.repository.VoceCostoDao;

@SpringBootApplication
public class GCApplication {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Value("${load.sample.data}")
    private String loadSampleData;

    private static final Logger log = LoggerFactory.getLogger(GCApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(GCApplication.class, args);
    }

    @Bean
    @Transactional
    public CommandLineRunner loadData(PasswordEncoder passwordEncoder,
            UserInfoDao userInfoDao, CommessaDao commessaDao,
            VariazioneCommessaDao variazioneCommessaDao,
            VoceCostoDao voceCostoDao, ResocontoDao resocontoDao) {
        return (args) -> {
            UserInfo administrator = userInfoDao.findByUsername("admin");
            if (administrator == null) {
                administrator = new UserInfo("admin",
                                             passwordEncoder.encode("admin"),
                                             Role.ADMIN);
                userInfoDao.save(administrator);
                log.info("creato user admin/admin");
            }

            if (loadSampleData != null && loadSampleData.equals("true")) {
                log.info("End Loading Sample Data");
                DateFormat formatter = new SimpleDateFormat("MM/dd/yy");

                VoceCosto vc1 = new VoceCosto();
                vc1.setVoce("Carburanti");
                vc1.setDescr("Il costo per il carburante");
                voceCostoDao.save(vc1);

                VoceCosto vc2 = new VoceCosto();
                vc2.setVoce("Materiale");
                vc2.setDescr("Il costo per il materiale");
                voceCostoDao.save(vc2);

                VoceCosto vc3 = new VoceCosto();
                vc3.setVoce("Personale");
                vc3.setDescr("Il costo per il personale");
                voceCostoDao.save(vc3);

                Commessa commessa1 = new Commessa();
                commessa1.setNome("ABC123");
                commessa1.setDescr("ZVF Commessa Diretta CS");
                commessa1.setImporto(new BigDecimal(100000000));
                commessa1.setInizio(formatter.parse("01/20/17"));
                commessa1.setFine(formatter.parse("01/20/19"));
                commessaDao.save(commessa1);

                VariazioneCommessa var1 = new VariazioneCommessa();
                var1.setCommessa(commessa1);
                var1.setDescr("Prima variazione solo data fine proroga di un mese");
                var1.setImporto(BigDecimal.ZERO);
                var1.setInizio(formatter.parse("01/20/17"));
                var1.setFine(formatter.parse("02/20/19"));
                variazioneCommessaDao.save(var1);

                VariazioneCommessa var2 = new VariazioneCommessa();
                var2.setCommessa(commessa1);
                var2.setDescr("Prima variazione solo importo +155999");
                var2.setImporto(new BigDecimal(155999));
                var2.setInizio(formatter.parse("01/20/17"));
                var2.setFine(formatter.parse("02/20/19"));
                variazioneCommessaDao.save(var2);

                Resoconto r1 = new Resoconto();
                r1.setCommessa(commessa1);
                r1.setVoceCosto(vc1);
                r1.setImporto(new BigDecimal(23567));
                r1.setData(formatter.parse("01/20/18"));
                r1.setTipologia(Tipologia.ANNUALE);
                resocontoDao.save(r1);

                Resoconto r2 = new Resoconto();
                r2.setCommessa(commessa1);
                r2.setVoceCosto(vc2);
                r2.setImporto(new BigDecimal(123567));
                r2.setData(formatter.parse("01/20/18"));
                r2.setTipologia(Tipologia.ANNUALE);
                resocontoDao.save(r2);

                Resoconto r3 = new Resoconto();
                r3.setCommessa(commessa1);
                r3.setVoceCosto(vc3);
                r3.setImporto(new BigDecimal(723567));
                r3.setData(formatter.parse("01/20/18"));
                r3.setTipologia(Tipologia.ANNUALE);
                resocontoDao.save(r3);
                
                UserInfo adp = new UserInfo("user", passwordEncoder.encode("pass"), Role.USER);
                adp.setLocked(true);
                userInfoDao.save(adp);
                log.info("creato user user/pass");
                log.info("End Loading Sample Data");
           
            }

        };
    }
}
