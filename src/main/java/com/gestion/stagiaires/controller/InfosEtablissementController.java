package com.gestion.stagiaires.controller;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gestion.stagiaires.entities.InfosEtablissementEntity;
import com.gestion.stagiaires.service.InfosEtablissementService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@RestController
@RequestMapping("/api/v1/authorize/etablissement")
public class InfosEtablissementController {
	@Autowired
	private InfosEtablissementService etablissementService;

	@GetMapping
	public ResponseEntity<Object> getall() {
		return etablissementService.getAll();
	}

	@PostMapping()
	public ResponseEntity<Object> ajouter(@Valid @RequestBody InfosEtablissementEntity etablissement) throws ParseException  {
		return etablissementService.ajouter_update(etablissement);
	}

	@PutMapping("/update")
	public ResponseEntity<Object> update(@Valid @RequestBody InfosEtablissementEntity etablissement) throws ParseException {
		return etablissementService.ajouter_update(etablissement);
	}

	@DeleteMapping("/delete")
	public ResponseEntity<Object> delete(@RequestHeader("id") Long id) {
		return etablissementService.delete(id);

	}

}
