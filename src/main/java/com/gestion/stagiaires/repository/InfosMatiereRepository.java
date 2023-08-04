package com.gestion.stagiaires.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gestion.stagiaires.dto.MatiereDto;
import com.gestion.stagiaires.entities.InfosMatiereEntity;

public interface InfosMatiereRepository  extends  JpaRepository<InfosMatiereEntity, Long> {
    @Query("SELECT new com.gestion.stagiaires.dto.MatiereDto(im.id,im.libelle) FROM InfosMatiereEntity im WHERE im.status = true")
	List<MatiereDto> findForSelect();

    @Query("SELECT MAX(CAST(im.numero AS int)) from InfosMatiereEntity im ")
	Long findLastNumero();
}
