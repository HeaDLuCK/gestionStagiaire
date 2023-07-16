package com.gestion.stagiaires.service;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gestion.stagiaires.entities.InfosÉtablissementEntity;

@Repository
public interface InfosÉtablissementService extends  CrudRepository<InfosÉtablissementEntity, String>  {

}
