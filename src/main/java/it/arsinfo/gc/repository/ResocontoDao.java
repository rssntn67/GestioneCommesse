package it.arsinfo.gc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import it.arsinfo.gc.entity.Commessa;
import it.arsinfo.gc.entity.Resoconto;
import it.arsinfo.gc.entity.VoceCosto;

public interface ResocontoDao extends JpaRepository<Resoconto, Long>{

    List<Resoconto> findByCommessa(Commessa commessa);
    List<Resoconto> findByVoceCosto(VoceCosto voceCosto);

}
