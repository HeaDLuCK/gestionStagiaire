package com.gestion.stagiaires.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gestion.stagiaires.dto.MatiereDto;
import com.gestion.stagiaires.entities.InfosMatiereEntity;

public interface InfosMatiereRepository  extends  JpaRepository<InfosMatiereEntity, Long> {
    @Query("SELECT im.id,im.libelle FROM InfosMatiereEntity im WHERE im.status = true")
	Optional<MatiereDto> findForSelect();

    @Query("SELECT MAX(CAST(im.numero AS int)) from InfosMatiereEntity im ")
	Long findLastNumero();
}
