package com.gestion.stagiaires.service;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.gestion.stagiaires.entities.BaseEntity;
import com.gestion.stagiaires.entities.InfosProfEntity;
import com.gestion.stagiaires.entities.InfosStagiaireEntity;

public abstract class BaseService<E extends BaseEntity,R > {
	

	@Autowired
	private R repository;
	
	
	public ResponseEntity<Object> getAll() {
		Map<String, Object> body = new HashMap<>();// output
		body.put("stagiaires", repository.findAll());
		
		return ResponseEntity.status(HttpStatus.OK).body(body);
		
	}
	
	public ResponseEntity<Object> ajouter_update(E object,String message) {
		Map<String, Object> body = new HashMap<>();// output
		repository.save(object);
		body.put("message", message);
		return ResponseEntity.status(HttpStatus.CREATED).body(body);
	}
	
	public ResponseEntity<Object> update(E object,String message) {
		Map<String, Object> body = new HashMap<>();// output
		repository.save(object);
		body.put("message",message);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(body);
	}

	
	public ResponseEntity<Object> delete(Long id) {
		Map<String,String> output= new HashMap<>();
		Optional<E> object= repository.findById(id);
		if(object.isPresent()){
			repository.deleteById(id);
			output.put("message","Stagiaire avec numéro "+id+" supprime avec succés" );
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(output);
		}else{
			output.put("message","inconnu stagiaire inséré avec numéro "+id+", vérifier le numéro saisi" );
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(output);
		}
	}
	
}
