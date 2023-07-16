package com.gestion.stagiaires.controller;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gestion.stagiaires.entities.InfosStagiaireEntity;
import com.gestion.stagiaires.service.InfosStagiaireService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/stagiaire")
public class InfosStagiaireController {

	@Autowired
	private InfosStagiaireService stagiaireService;

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
			Long id = stagiaireService.findByFullName(nom, prénom);
			if (id != null) {
				return false;
			}
		}
		return true;
	}
	

	@GetMapping
	public ResponseEntity<Object> getStagiaires() {
		Map<String,Object> stagiaires=new HashMap<>();
		stagiaires.put("stagiaires", stagiaireService.findAll());
		return ResponseEntity.status(HttpStatus.OK).body(stagiaires);
	}
	
	@PostMapping
	public ResponseEntity<Object> storeStagiaire(@Valid @RequestBody InfosStagiaireEntity stagiaire)
			throws ParseException {
		Map<String, Object> body = new HashMap<>();// output
		if (!this.valide_lâge(stagiaire.stagiaireAge())) {
			body.put("message", "l'âge doit être inférieur à 23 ans et supérieur à 10 ans");
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(body);
		}
		if (!this.valide_le_nom_complet(stagiaire.getNom(), stagiaire.getPrénom())) {
			body.put("message", "cet nom complet est utilisé");
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(body);
		}
		stagiaireService.save(stagiaire);
		body.put("message", "stagiaire inséré avec succès");
		return ResponseEntity.status(HttpStatus.CREATED).body(body);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Object> updateStagiaire(@PathVariable("id") Long numéro,
			@Valid @RequestBody InfosStagiaireEntity stagiaireNewInfo) {
		InfosStagiaireEntity stagiaireInfoBeforeUpdate = stagiaireService.findById(numéro).get();
		stagiaireInfoBeforeUpdate.setNom(stagiaireNewInfo.getNom());
		stagiaireInfoBeforeUpdate.setPrénom(stagiaireNewInfo.getPrénom());
		stagiaireInfoBeforeUpdate.setDate_de_naissance(stagiaireNewInfo.getDate_de_naissance());
		stagiaireInfoBeforeUpdate.setAddresse(stagiaireNewInfo.getAddresse());
		stagiaireInfoBeforeUpdate.setÉtablissement(stagiaireNewInfo.getÉtablissement());
		stagiaireInfoBeforeUpdate.setLogin(stagiaireNewInfo.getLogin());
		stagiaireInfoBeforeUpdate.setMot_de_passe(stagiaireNewInfo.getMot_de_passe());
		stagiaireService.save(stagiaireNewInfo);

		Map<String,String> output=new HashMap<>();
		output.put("message", "Stagiaire avec numéro "+stagiaireInfoBeforeUpdate.getNuméro()+" Mis à jour avec succés");

		return ResponseEntity.status(HttpStatus.ACCEPTED).body(output);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteStagiaire(@PathVariable("id") Long numéro) {
		Map<String,String> output= new HashMap<>();
		Optional<InfosStagiaireEntity> stagiaire= stagiaireService.findById(numéro);
		if(stagiaire.isPresent()){
			stagiaireService.deleteById(numéro);
			output.put("message","Stagiaire avec numéro "+numéro+" supprime avec succés" );
			return ResponseEntity.status(HttpStatus.CREATED).body(output);
		}else{
			output.put("message","inconnu stagiaire inséré avec numéro "+numéro+", vérifier le numéro saisi" );
			return ResponseEntity.status(HttpStatus.CREATED).body(output);
		}
	}

}
