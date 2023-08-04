package com.gestion.stagiaires.controller;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gestion.stagiaires.entities.InfosProfEntity;
import com.gestion.stagiaires.service.InfosProfService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/authorize/professeur")
public class InfosProfController {

    @Autowired
    private InfosProfService profService;

    @GetMapping
    public ResponseEntity<Object> getall() {
        return profService.getAll();
    }
    @GetMapping("/countprof")
    public ResponseEntity<Object> countProfByMatiere(){
        return profService.countProfByMatiere();
    }

    @GetMapping("/countstagiaire")
    public ResponseEntity<Object> countStagiaireByProf(){
        return profService.countStagiaireByProf();
    }

    @GetMapping("/numerogenere")
	public ResponseEntity<Object> numeroGenere() {
		return profService.getGenereNumero();
	}

    @GetMapping("/select")
    public ResponseEntity<Object> profSelect(){
        return profService.getProfInfo();
    }

    @PostMapping
    public ResponseEntity<Object> ajouter(@Valid @RequestBody InfosProfEntity professeur) throws ParseException {
        return profService.ajouter_update_jointure(professeur);
    }

    @PutMapping("/update")
    public ResponseEntity<Object> update(@RequestHeader("id") Long id,@Valid @RequestBody InfosProfEntity professeur) throws ParseException {
        if(id != professeur.getId()){
					Map<String,String> message=new HashMap<>();
					message.put("message", "mauvaise id vérifier les entrées");
					return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(message);
				}
        return profService.ajouter_update_jointure(professeur);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> delete(@RequestHeader("id") Long id) {
        return profService.delete(id);

    }
}
