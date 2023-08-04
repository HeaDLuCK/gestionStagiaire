package com.gestion.stagiaires.service;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.gestion.stagiaires.entities.InfosEtablissementEntity;
import com.gestion.stagiaires.repository.InfosEtablissementRepository;

@Service
public class InfosEtablissementService extends BaseService<InfosEtablissementEntity, InfosEtablissementRepository> {
    @Autowired
	private InfosEtablissementRepository etablissementRepository;

    /**
	 * il renverra l'id et libelle du matière
	 */
	public ResponseEntity<Object>  getEtablissementInfo(){
		Map<String, Object> body = new HashMap<>();// output
		body.put("data", etablissementRepository.findForSelect());
		return ResponseEntity.status(HttpStatus.OK).body(body);
	}
}
