package com.gestion.stagiaires.service;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.gestion.stagiaires.dto.MatiereDto;
import com.gestion.stagiaires.entities.InfosMatiereEntity;
import com.gestion.stagiaires.entities.InfosProfEntity;
import com.gestion.stagiaires.repository.InfosMatiereRepository;

@Service
@Lazy
public class InfosMatiereService extends BaseService<InfosMatiereEntity,InfosMatiereRepository>{

	@Autowired
	private InfosMatiereRepository matiereRepository;
    @Autowired
	private InfosProfService profService;

	/**
	 * il renverra l'id et le nom complet du matiere
	 */
	public ResponseEntity<Object>  getProfInfo(){
		Map<String, Object> body = new HashMap<>();// output
		Optional<MatiereDto> matieres=matiereRepository.findForSelect();
		body.put("data", matieres.get());
		return ResponseEntity.status(HttpStatus.OK).body(body);
	}

    public  ResponseEntity<Object> ajouter_update_jointure(InfosMatiereEntity matiere) throws ParseException {
		
		if(matiere.getProfesseurs_ids() != null ){
			if (!matiere.getProfesseurs_ids().isEmpty()  ) {
			matiere.getProfesseurs_ids().forEach(id -> {
				InfosProfEntity professeur = profService.findOne(id);
				if (professeur != null) {
					matiere.getListe_des_profs().add(professeur);
				}
			});
		}
		}
		Map<String, Object> body = new HashMap<>();// output
		if (matiere.getId() == null) {
			Long derniere_id=matiereRepository.findLastNumero()!= null ? matiereRepository.findLastNumero(): 0;
			Long id = derniere_id + 1;
			String helper = InfosStagiaireService.createNumber(id);
			if (!helper.equals(matiere.getNumero())) {
				body.put("message", "le numéro doit être unique");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
			}
		} else {
			/**
			 * vérifier si le stagiaire dans la base de données
			*/
			InfosMatiereEntity validated_matiere = super.findOne(matiere.getId());
			if (validated_matiere != null) {
				String helper =matiere.getNumero();
				if (!helper.equals(validated_matiere.getNumero())) {
					body.put("message", "le numéro doit être unique");
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
				}
			}
		}
		
		return this.ajouter_update(matiere);
	}
}
