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

import com.gestion.stagiaires.entities.InfosMatiereEntity;
import com.gestion.stagiaires.service.InfosMatiereService;

@RestController
@RequestMapping("/api/v1/authorize/matiere")
public class InfosMatiereController {

    @Autowired
    private InfosMatiereService matiereService;

    @GetMapping
    public ResponseEntity<Object> getall() {
        return matiereService.getAll();
    }

    @PostMapping()
    public ResponseEntity<Object> ajouter(@RequestBody InfosMatiereEntity matiere) throws ParseException {
        return matiereService.ajouter_update(matiere);
    }

    @PutMapping("/update")
    public ResponseEntity<Object> update(@RequestBody InfosMatiereEntity matiere) throws ParseException {
        return matiereService.ajouter_update(matiere);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> delete(@RequestHeader("id") Long id) {
        return matiereService.delete(id);

    }
}
