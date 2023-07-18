package com.gestion.stagiaires.authentication;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gestion.stagiaires.config.JwtService;
import com.gestion.stagiaires.entities.InfosStagiaireEntity;
import com.gestion.stagiaires.repository.InfosStagiaireRepository;
import com.gestion.stagiaires.service.InfosStagiaireService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	
	private  InfosStagiaireRepository stagiaireRepository;
	private  PasswordEncoder passwordEncoder;
	private  JwtService jwtService;
	private InfosStagiaireService stagiaireService;
	private  AuthenticationManager authenticationManager;

	public ResponseEntity<Object> authenticate(AuthenticationRequest request) {
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getLogin(), request.getMot_de_passe()));
		InfosStagiaireEntity user = stagiaireRepository.findByLogin(request.getLogin()).orElseThrow();
		String jwtToken = jwtService.generateToken(user);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(new HashMap<String, String>().put("token", jwtToken));
	}
	
	
	public ResponseEntity<Object> register(InfosStagiaireEntity stagiaire) throws ParseException {
		Map<String, Object> body = new HashMap<>();// output
		
		if (!stagiaireService.valide_lage(stagiaire.stagiaireAge())) {
			body.put("message", "l'âge doit être inférieur à 23 ans et supérieur à 10 ans");
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(body);
		}
		if (!stagiaireService.valide_le_nom_complet(stagiaire.getNom(), stagiaire.getPrenom())) {
			body.put("message", "cet nom complet est utilisé");
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(body);
		}
		stagiaire.setMot_de_passe(passwordEncoder.encode(stagiaire.getMot_de_passe()));
		stagiaireRepository.save(stagiaire);
		body.put("message", "stagiaire inséré avec succès");
		return ResponseEntity.status(HttpStatus.CREATED).body(body);
	}
}
