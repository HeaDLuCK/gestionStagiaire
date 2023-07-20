package com.gestion.stagiaires.service;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.gestion.stagiaires.entities.BaseEntity;

public abstract class BaseService<E extends BaseEntity,R extends JpaRepository<E,Long>> {
	

	@Autowired
	private R repository;
	
	
	public ResponseEntity<Object> getAll() {
		Map<String, Object> body = new HashMap<>();// output
		body.put("stagiaires", repository.findAll());
		
		return ResponseEntity.status(HttpStatus.OK).body(body);
		
	}

	public E findOne(Long id){
		Optional<E> anonymous = repository.findById(id);  // il peut être stagiaire ou ...
        return anonymous.isPresent() ? anonymous.get() : null;
	}
	
	public ResponseEntity<Object> ajouter_update(E object) throws ParseException {
		Map<String, Object> body = new HashMap<>();// output
		repository.save(object);
		body.put("message", "ajouté ou mis à jour avec succès");
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(body);
	}

	
	public ResponseEntity<Object> delete(Long id) {
		Map<String,String> output= new HashMap<>();
		Optional<E> object= repository.findById(id);
		if(object.isPresent()){
			repository.deleteById(id);
			output.put("message",id+" supprime avec succés" );
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(output);
		}else{
			output.put("message","inconnu id "+id+", vérifier le numéro saisi" );
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(output);
		}
	}
	
}
