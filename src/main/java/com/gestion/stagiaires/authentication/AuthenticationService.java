package com.gestion.stagiaires.authentication;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gestion.stagiaires.config.JwtService;
import com.gestion.stagiaires.entities.InfosStagiaireEntity;
import com.gestion.stagiaires.repository.InfosStagiaireRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	
	private  InfosStagiaireRepository stagiaireRepository;
	private  PasswordEncoder passwordEncoder;
	private  JwtService jwtService;
	private  AuthenticationManager authenticationManager;

	public ResponseEntity<Object> authenticate(AuthenticationRequest request) {
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getLogin(), request.getMot_de_passe()));
		InfosStagiaireEntity user = stagiaireRepository.findByLogin(request.getLogin()).orElseThrow();
		String jwtToken = jwtService.generateToken(user);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(new HashMap<String, String>().put("token", jwtToken));
	}
	
	public ResponseEntity<Object> register(InfosStagiaireEntity stagiaire){
		stagiaire.setMot_de_passe(passwordEncoder.encode(stagiaire.getMot_de_passe()));
		stagiaireRepository.save(stagiaire);
		String jwtToken = jwtService.generateToken(stagiaire);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(new HashMap<String,String>().put("token", jwtToken));
		
	}
	

}
