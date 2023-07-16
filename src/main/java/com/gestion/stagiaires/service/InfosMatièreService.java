package com.gestion.stagiaires.service;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gestion.stagiaires.entities.InfosMatièreEntity;

@Repository
public interface InfosMatièreService  extends  CrudRepository<InfosMatièreEntity, Long>{

}
