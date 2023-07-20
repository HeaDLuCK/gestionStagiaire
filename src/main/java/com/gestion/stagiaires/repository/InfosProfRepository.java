package com.gestion.stagiaires.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.gestion.stagiaires.entities.InfosProfEntity;

public interface InfosProfRepository  extends  JpaRepository<InfosProfEntity, Long> {

}
