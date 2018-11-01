package it.arsinfo.gc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import it.arsinfo.gc.entity.VoceCosto;

public interface VoceCostoDao extends JpaRepository<VoceCosto, Long>{

    List<VoceCosto> findByVoceStartsWithIgnoreCase(String voce);
    List<VoceCosto> findByDescrStartsWithIgnoreCase(String descr);

}
