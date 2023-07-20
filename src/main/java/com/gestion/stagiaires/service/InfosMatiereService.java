package com.gestion.stagiaires.service;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.gestion.stagiaires.entities.InfosMatiereEntity;
import com.gestion.stagiaires.entities.InfosProfEntity;
import com.gestion.stagiaires.repository.InfosMatiereRepository;

@Service
@Lazy
public class InfosMatiereService extends BaseService<InfosMatiereEntity,InfosMatiereRepository>{

    @Autowired
	private InfosProfService profService;

    public  ResponseEntity<Object> ajouter_update_jointure(InfosMatiereEntity matiere) throws ParseException {
		if(matiere.getProfesseurs_ids() != null ){
			if (!matiere.getProfesseurs_ids().isEmpty()  ) {
			matiere.getProfesseurs_ids().stream().map(id -> {
				InfosProfEntity professeur = profService.findOne(id);
				if (professeur != null) {
					matiere.getListe_des_profs().add(professeur);
				}
				return id;
			});
		}
		}
		
		return this.ajouter_update(matiere);
	}
}
