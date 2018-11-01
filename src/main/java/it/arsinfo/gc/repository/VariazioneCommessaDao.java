package it.arsinfo.gc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import it.arsinfo.gc.entity.Commessa;
import it.arsinfo.gc.entity.VariazioneCommessa;

public interface VariazioneCommessaDao extends JpaRepository<VariazioneCommessa, Long>{

    List<VariazioneCommessa> findByCommessa(Commessa commessa);
    List<VariazioneCommessa> findByDescrStartsWithIgnoreCase(String descr);

}
