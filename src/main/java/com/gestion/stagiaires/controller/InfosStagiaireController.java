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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gestion.stagiaires.authentication.AuthenticationService;
import com.gestion.stagiaires.entities.InfosStagiaireEntity;
import com.gestion.stagiaires.service.InfosStagiaireService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/authrized/stagiaire")
public class InfosStagiaireController {

	@Autowired
	private InfosStagiaireService stagiaireService;
	@Autowired
	private AuthenticationService registerService;

	@GetMapping
	public ResponseEntity<Object> getStagiaires() {
		return stagiaireService.getAll();
	}
	
	@PostMapping
	public ResponseEntity<Object> storeStagiaire(@Valid @RequestBody InfosStagiaireEntity stagiaire)
			throws ParseException {
		return registerService.register(stagiaire);
	}

	@PutMapping("/update")
	public ResponseEntity<Object> updateStagiaire(@Valid @RequestBody InfosStagiaireEntity stagiaireNewInfo) {
		return stagiaireService.update(stagiaireNewInfo);
	}

	@DeleteMapping("/delete")
	public ResponseEntity<Object> deleteStagiaire(@RequestHeader("id") Long id) {
		return stagiaireService.delete(id);
	}

}
