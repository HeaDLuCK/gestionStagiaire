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

import com.gestion.stagiaires.entities.InfosMatiereEntity;
import com.gestion.stagiaires.service.InfosMatiereService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/authorize/matiere")
public class InfosMatiereController {

    @Autowired
    private InfosMatiereService matiereService;

    @GetMapping("/numerogenere")
	public ResponseEntity<Object> numeroGenere() {
		return matiereService.getGenereNumero();
	}

    @GetMapping
    public ResponseEntity<Object> getall() {
        return matiereService.getAll();
    }

    @GetMapping("/select")
    public ResponseEntity<Object> matiereSelect(){
        return matiereService.getMatiereInfo();
    }

    @PostMapping()
    public ResponseEntity<Object> ajouter(@Valid @RequestBody InfosMatiereEntity matiere) throws ParseException {
        return matiereService.ajouter_update_jointure(matiere);
    }

    @PutMapping("/update")
    public ResponseEntity<Object> update(@RequestHeader("id") Long id,@Valid @RequestBody InfosMatiereEntity matiere)
            throws ParseException {
        if (id != matiere.getId()) {
            Map<String, String> message = new HashMap<>();
            message.put("message", "mauvaise id vérifier les entrées");
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(message);
        }
        return matiereService.ajouter_update_jointure(matiere);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> delete(@RequestHeader("id") Long id) {
        return matiereService.delete(id);

    }
}
