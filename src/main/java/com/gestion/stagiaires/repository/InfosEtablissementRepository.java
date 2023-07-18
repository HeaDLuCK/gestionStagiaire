package com.gestion.stagiaires.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.gestion.stagiaires.entities.InfosEtablissementEntity;

public interface InfosEtablissementRepository  extends  JpaRepository<InfosEtablissementEntity, Long> {

}
