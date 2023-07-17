package com.gestion.stagiaires.service;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.gestion.stagiaires.entities.InfosStagiaireEntity;
import com.gestion.stagiaires.repository.InfosStagiaireRepository;


@Service
public class InfosStagiaireService {
	
	@Autowired
	InfosStagiaireRepository stagiaireRepository;
	

	public ResponseEntity<Object> get_stagiaires() {
		Map<String,Object> stagiaires=new HashMap<>();
		stagiaires.put("stagiaires", stagiaireRepository.findAll());
		return ResponseEntity.status(HttpStatus.OK).body(stagiaires);
	}
	
	
	/*
	 * valider  si l'âge est entre 10 ans et 23 ans
	 */
	private boolean valide_lâge(Integer age){
		if (age < 10 || age> 23) {
			return  false;
		}
		return true;
	}
	/*
	 * valider  si le nom complet est unique
	 */
	private boolean valide_le_nom_complet(String nom,String prénom){
		if(prénom != null){
			Long id = stagiaireRepository.findByFullName(nom, prénom);
			if (id != null) {
				return false;
			}
		}
		return true;
	}

	/*
	 * BCryptPasswordEncoder bcrypt= new BCryptPasswordEncoder();
	 * bcrypt.matches(thispassword,db password)
	 */
	
	private String encryptedPassword(String password) {
		BCryptPasswordEncoder bcrypt= new BCryptPasswordEncoder();
		String encryptedPassword = bcrypt.encode(password);
		return encryptedPassword;
	}
	
	
	public ResponseEntity<Object> ajouter_stagiaire(InfosStagiaireEntity stagiaire) throws ParseException {
		Map<String, Object> body = new HashMap<>();// output
		if (!this.valide_lâge(stagiaire.stagiaireAge())) {
			body.put("message", "l'âge doit être inférieur à 23 ans et supérieur à 10 ans");
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(body);
		}
		if (!this.valide_le_nom_complet(stagiaire.getNom(), stagiaire.getPrénom())) {
			body.put("message", "cet nom complet est utilisé");
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(body);
		}
		stagiaire.setMot_de_passe(this.encryptedPassword(stagiaire.getMot_de_passe()));
		stagiaireRepository.save(stagiaire);
		body.put("message", "stagiaire inséré avec succès");
		return ResponseEntity.status(HttpStatus.CREATED).body(body);
	}
	
	public ResponseEntity<Object> update_stagiaire(Long numéro,InfosStagiaireEntity stagiaireNewInfo){
		InfosStagiaireEntity stagiaireInfoBeforeUpdate = stagiaireRepository.findById(numéro).get();
		stagiaireInfoBeforeUpdate.setNom(stagiaireNewInfo.getNom());
		stagiaireInfoBeforeUpdate.setPrénom(stagiaireNewInfo.getPrénom());
		stagiaireInfoBeforeUpdate.setDate_de_naissance(stagiaireNewInfo.getDate_de_naissance());
		stagiaireInfoBeforeUpdate.setAddresse(stagiaireNewInfo.getAddresse());
		stagiaireInfoBeforeUpdate.setÉtablissement(stagiaireNewInfo.getÉtablissement());
		stagiaireInfoBeforeUpdate.setLogin(stagiaireNewInfo.getLogin());
		stagiaireInfoBeforeUpdate.setMot_de_passe(this.encryptedPassword(stagiaireNewInfo.getMot_de_passe()));
		stagiaireRepository.save(stagiaireNewInfo);

		Map<String,String> output=new HashMap<>();
		output.put("message", "Stagiaire avec numéro "+stagiaireInfoBeforeUpdate.getNuméro()+" Mis à jour avec succés");

		return ResponseEntity.status(HttpStatus.ACCEPTED).body(output);
	}
	
	public ResponseEntity<Object> delete_stagiaire(Long numéro) {
		Map<String,String> output= new HashMap<>();
		Optional<InfosStagiaireEntity> stagiaire= stagiaireRepository.findById(numéro);
		if(stagiaire.isPresent()){
			stagiaireRepository.deleteById(numéro);
			output.put("message","Stagiaire avec numéro "+numéro+" supprime avec succés" );
			return ResponseEntity.status(HttpStatus.CREATED).body(output);
		}else{
			output.put("message","inconnu stagiaire inséré avec numéro "+numéro+", vérifier le numéro saisi" );
			return ResponseEntity.status(HttpStatus.CREATED).body(output);
		}
	}
	
	

}
