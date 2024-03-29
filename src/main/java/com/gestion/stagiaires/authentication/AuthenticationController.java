package com.gestion.stagiaires.authentication;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gestion.stagiaires.entities.InfosStagiaireEntity;
import com.gestion.stagiaires.service.InfosStagiaireService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

	@Autowired
	private AuthenticationService authenticationService;

	@Autowired
	private InfosStagiaireService stagiaireService;

	

	@PostMapping("/register")
	public ResponseEntity<Object> register(@Valid @RequestBody InfosStagiaireEntity stagiaire)
			throws ParseException {
				stagiaire.setId(null);
				return stagiaireService.ajouter_update_jointure(stagiaire);

	}

	@PostMapping("/login")
	public ResponseEntity<Object> login(@Valid @RequestBody AuthenticationRequest request) {
		return authenticationService.authenticate(request);
	}

}
