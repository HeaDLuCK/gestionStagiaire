package com.gestion.stagiaires.controller;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@RequestMapping("/api/v1/authorize/professeur")
public class InfosProfController {

    @Autowired
    private InfosProfService profService;

    @GetMapping
    public ResponseEntity<Object> getall() {
        return profService.getAll();
    }

    @PostMapping()
    public ResponseEntity<Object> ajouter(@RequestBody InfosProfEntity professeur) throws ParseException {
        return profService.ajouter_update(professeur);
    }

    @PutMapping("/update")
    public ResponseEntity<Object> update(@RequestBody InfosProfEntity professeur) throws ParseException {
        return profService.ajouter_update(professeur);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> delete(@RequestHeader("id") Long id) {
        return profService.delete(id);

    }
}
