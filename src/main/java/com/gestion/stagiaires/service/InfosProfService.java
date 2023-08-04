package com.gestion.stagiaires.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.gestion.stagiaires.entities.InfosMatiereEntity;
import com.gestion.stagiaires.entities.InfosProfEntity;
import com.gestion.stagiaires.entities.InfosStagiaireEntity;
import com.gestion.stagiaires.repository.InfosProfRepository;

@Service
public class InfosProfService extends BaseService<InfosProfEntity, InfosProfRepository> {

    @Autowired
    private InfosProfRepository profRepository;

    @Autowired
    @Lazy
    private InfosMatiereService matiereService;

    @Autowired
    @Lazy
    private InfosStagiaireService stagiaireService;

    /**
     * il renverra l'id et le nom complet du professeur
     */
    public ResponseEntity<Object> getProfInfo() {
        Map<String, Object> body = new HashMap<>();// output
        body.put("data", profRepository.findForSelect());
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    /**
     * obtenir numéro pour nouveau professeur
     *
     */
    public ResponseEntity<Object> getGenereNumero() {
        Map<String, String> body = new HashMap<>();
        Long NumeroGenere = profRepository.findLastNumero();
        body.put("numero", InfosStagiaireService.createNumber(NumeroGenere + 1));
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    @Override
    public ResponseEntity<Object> ajouter_update(InfosProfEntity professeur) throws ParseException {
        Map<String, Object> body = new HashMap<>();// output
        if (professeur.getId() == null) {
            Long derniere_id = profRepository.findLastNumero() != null ? profRepository.findLastNumero() : 0;
            Long id = derniere_id + 1;
            String helper = InfosStagiaireService.createNumber(id);
            if (!helper.equals(professeur.getNumero())) {
                body.put("message", "le numéro doit être unique");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
            }
        } else {
            /**
             * vérifier si le stagiaire dans la base de données
             */
            InfosProfEntity validated_professeur = super.findOne(professeur.getId());
            if (validated_professeur != null) {
                String helper = professeur.getNumero();
                if (!helper.equals(validated_professeur.getNumero())) {
                    body.put("message", "le numéro doit être unique");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
                }
            }
        }
        if (!stagiaireService.valide_le_nom_complet(professeur.getNom(), professeur.getPrenom(), professeur.getId())) {
            body.put("message", "cet nom complet est utilisé");
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(body);
        }

        return super.ajouter_update(professeur);

    }

    public ResponseEntity<Object> ajouter_update_jointure(InfosProfEntity professeur)
            throws ParseException {
        Map<String, Object> body = new HashMap<>();// output
        if (professeur.getStagiaire_ids() != null) {
            if (!professeur.getStagiaire_ids().isEmpty()) {
                professeur.getStagiaire_ids().forEach(id -> {
                    InfosStagiaireEntity stagiaireEntity = stagiaireService.findOne(id);
                    if (stagiaireEntity != null) {
                        professeur.getStagiaires().add(stagiaireEntity);
                    }
                });

            }
        }

        if (professeur.getMatiere_id() != null) {
            InfosMatiereEntity matiere = matiereService.findOne(professeur.getMatiere_id());
            if (matiere != null) {
                professeur.setMatiere(matiere);
            }
        } else {
            body.put("matiere_id", "tu devrais ajouter un matière");
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(body);
        }

        return this.ajouter_update(professeur);
    }

    public ResponseEntity<Object> countProfByMatiere() {
        Map<String, Object> body = new HashMap<>();// output
        body.put("data", profRepository.countByMatiere());
        return ResponseEntity.status(HttpStatus.OK).body(body);
    };

    public ResponseEntity<Object> countStagiaireByProf() {
        Map<String, Object> body = new HashMap<>();// output
        List<Object[]> results = new ArrayList<>();
        List<InfosProfEntity> professeurs = profRepository.findAll();
        professeurs.forEach(prof -> {
            Object[] row = { prof.getNom() + " " + prof.getPrenom(), prof.getStagiaires().size() };
            results.add(row);
        });
        body.put("data", results);
        return ResponseEntity.status(HttpStatus.OK).body(body);
    };

}
