package com.gestion.stagiaires.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gestion.stagiaires.dto.EtablissementDto;
import com.gestion.stagiaires.entities.InfosEtablissementEntity;

public interface InfosEtablissementRepository extends JpaRepository<InfosEtablissementEntity, Long> {

    @Query("SELECT new com.gestion.stagiaires.dto.EtablissementDto(ie.id,ie.libelle) FROM InfosEtablissementEntity ie")
	List<EtablissementDto> findForSelect();
}
