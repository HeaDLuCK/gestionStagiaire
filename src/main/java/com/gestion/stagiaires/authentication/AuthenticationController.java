package com.gestion.stagiaires.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gestion.stagiaires.entities.InfosStagiaireEntity;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
	
	@Autowired
	private AuthenticationService authenticationService;
	
	@PostMapping("/register")
	public ResponseEntity<Object> register(@Valid @RequestBody InfosStagiaireEntity stagiaire) {
		return authenticationService.register(stagiaire);
	}
	
	@PostMapping("/login")
	public ResponseEntity<Object> login (@Valid @RequestBody AuthenticationRequest request) {
		return authenticationService.authenticate(request);
	}
	

}
