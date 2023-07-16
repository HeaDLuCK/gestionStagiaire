package com.gestion.stagiaires.service;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gestion.stagiaires.entities.InfosProfEntity;

@Repository
public interface InfosProfService extends CrudRepository<InfosProfEntity, Long>{

}
