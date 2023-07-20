package com.gestion.stagiaires.controller;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gestion.stagiaires.entities.InfosStagiaireEntity;
import com.gestion.stagiaires.service.InfosStagiaireService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/authorize/stagiaire")
@Validated
public class InfosStagiaireController {

	@Autowired
	private InfosStagiaireService stagiaireService;

	@GetMapping
	public ResponseEntity<Object> getall() {
		return stagiaireService.getAll();
	}

	// method ajouter est dans AuthenticationController comme register

	@PutMapping("/update")
	public ResponseEntity<Object> update(@Valid @RequestBody InfosStagiaireEntity stagiaire)
			throws ParseException {
		return stagiaireService.ajouter_update_jointure(stagiaire);
	}

	@DeleteMapping("/delete")
	public ResponseEntity<Object> delete(@RequestHeader("id") Long id) {
		return stagiaireService.delete(id);
	}

}
