package it.arsinfo.gc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import it.arsinfo.gc.entity.Commessa;

public interface CommessaDao extends JpaRepository<Commessa, Long>{

    List<Commessa> findByNomeStartsWithIgnoreCase(String nome);
    List<Commessa> findByDescrStartsWithIgnoreCase(String descr);

}
