package com.gestion.stagiaires.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gestion.stagiaires.dto.ProfDto;
import com.gestion.stagiaires.entities.InfosProfEntity;

public interface InfosProfRepository extends JpaRepository<InfosProfEntity, Long> {
    @Query("SELECT ip.id,CONCAT(ip.nom, \" \", ip.prenom) FROM InfosProfEntity ip WHERE ip.status = true")
    Optional<ProfDto> findForSelect();

    @Query("SELECT matiere, COUNT(id) FROM InfosProfEntity GROUP BY matiere")
    Optional<Object[]> countByMatiere();

    @Query("SELECT MAX(CAST(ip.numero AS int)) from InfosProfEntity ip ")
	Long findLastNumero();
}
