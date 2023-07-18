package com.gestion.stagiaires.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.stagiaires.entities.InfosEtablissementEntity;
import com.gestion.stagiaires.repository.InfosEtablissementRepository;

@Service
public class InfosEtablissementService  extends BaseService<InfosEtablissementEntity, InfosEtablissementRepository> {

	@Autowired
	InfosEtablissementRepository etablissementRepository;
	
	
}
