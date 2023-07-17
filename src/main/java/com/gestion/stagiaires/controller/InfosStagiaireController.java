package com.gestion.stagiaires.controller;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
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

	@GetMapping
	public ResponseEntity<Object> getStagiaires() {
		return stagiaireService.get_stagiaires();
	}
	
	@PostMapping
	public ResponseEntity<Object> storeStagiaire(@Valid @RequestBody InfosStagiaireEntity stagiaire)
			throws ParseException {
		return stagiaireService.ajouter_stagiaire(stagiaire);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Object> updateStagiaire(@PathVariable("id") Long numéro,
			@Valid @RequestBody InfosStagiaireEntity stagiaireNewInfo) {
		return stagiaireService.update_stagiaire(numéro, stagiaireNewInfo);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteStagiaire(@PathVariable("id") Long numéro) {
		return stagiaireService.delete_stagiaire(numéro);
	}

}
