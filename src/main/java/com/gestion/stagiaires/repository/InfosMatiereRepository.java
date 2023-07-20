package com.gestion.stagiaires.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.gestion.stagiaires.entities.InfosMatiereEntity;

public interface InfosMatiereRepository  extends  JpaRepository<InfosMatiereEntity, Long> {

}
