package com.gestion.stagiaires.authentication;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gestion.stagiaires.config.JwtService;
import com.gestion.stagiaires.entities.InfosStagiaireEntity;
import com.gestion.stagiaires.repository.InfosStagiaireRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

	private final  InfosStagiaireRepository stagiaireRepository;
	private final  JwtService jwtService;
	private final  AuthenticationManager authenticationManager;

	public ResponseEntity<Object> authenticate(AuthenticationRequest request) {
		Map<String,String> output=new LinkedHashMap<>();

		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getLogin(), request.getMot_de_passe()));
		InfosStagiaireEntity user = stagiaireRepository.findByLogin(request.getLogin()).orElseThrow(() -> new UsernameNotFoundException("user not found"));
		String jwtToken = jwtService.generateToken(user);
		output.put("username", user.getLogin());
		output.put("token", jwtToken);
		output.put("time", LocalDateTime.now().toString());
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(output);
	}
}
