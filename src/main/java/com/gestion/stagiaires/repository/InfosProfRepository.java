package com.gestion.stagiaires.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gestion.stagiaires.dto.ProfDto;
import com.gestion.stagiaires.entities.InfosProfEntity;

public interface InfosProfRepository extends JpaRepository<InfosProfEntity, Long> {
    @Query("SELECT new com.gestion.stagiaires.dto.ProfDto(ip.id,ip.nom, ip.prenom) FROM InfosProfEntity ip WHERE ip.status = true")
    List<ProfDto> findForSelect();

    @Query("SELECT matiere.libelle , COUNT(id) FROM InfosProfEntity GROUP BY matiere")
    List<Object[]> countByMatiere();

    @Query("SELECT MAX(CAST(ip.numero AS int)) from InfosProfEntity ip ")
    Long findLastNumero();
}
