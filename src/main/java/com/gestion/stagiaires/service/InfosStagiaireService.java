package com.gestion.stagiaires.service;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gestion.stagiaires.entities.InfosStagiaireEntity;
import com.gestion.stagiaires.repository.InfosStagiaireRepository;


@Service
public class InfosStagiaireService extends BaseService<InfosStagiaireEntity, InfosStagiaireRepository> {
	
	@Autowired
	InfosStagiaireRepository stagiaireRepository;

	private  PasswordEncoder passwordEncoder;
	
	/*
	 * valider  si l'âge est entre 10 ans et 23 ans
	 */
	public boolean valide_lage(Integer age){
		if (age < 10 || age> 23) {
			return  false;
		}
		return true;
	}
	/*
	 * valider  si le nom complet est unique
	 */
	
	public boolean valide_le_nom_complet(String nom,String prenom){
		if(prenom != null){
			Long id = stagiaireRepository.findByFullName(nom, prenom);
			if (id != null) {
				return false;
			}
		}
		return true;
	}


	public  ResponseEntity<Object> ajouter(InfosStagiaireEntity stagiaire){
		Map<String, Object> body = new HashMap<>();// output
		if (!this.valide_lage(stagiaire.stagiaireAge())) {
			body.put("message", "l'âge doit être inférieur à 23 ans et supérieur à 10 ans");
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(body);
		}
		if (!this.valide_le_nom_complet(stagiaire.getNom(), stagiaire.getPrenom())) {
			body.put("message", "cet nom complet est utilisé");
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(body);
		}
		
		return super.ajouter_update(stagiaire,"stagiaire inséré avec succès");
	}
	
	public ResponseEntity<Object> update(InfosStagiaireEntity stagiaire){
		Map<String, Object> body = new HashMap<>();// output
		if (!this.valide_lage(stagiaire.stagiaireAge())) {
			body.put("message", "l'âge doit être inférieur à 23 ans et supérieur à 10 ans");
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(body);
		}
		stagiaire.setMot_de_passe(passwordEncoder.encode(stagiaire.getMot_de_passe()));
		
		return super.ajouter_update(stagiaire, "Mis à jour avec succés");
	}
	
}
