package com.gestion.stagiaires.service;

import java.text.ParseException;
import java.util.HashMap;
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
    @Lazy
    private InfosMatiereService matiereService;

    @Autowired
    @Lazy
    private InfosStagiaireService stagiaireService;

    @Override
    public ResponseEntity<Object> ajouter_update(InfosProfEntity professeur) throws ParseException {
        Map<String, Object> body = new HashMap<>();// output
        if (!stagiaireService.valide_le_nom_complet(professeur.getNom(), professeur.getPrenom(), professeur.getId())) {
            body.put("message", "cet nom complet est utilisé");
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(body);
        }
        return super.ajouter_update(professeur);
    }

    public ResponseEntity<Object> ajouter_update_jointure(InfosProfEntity professeur)
            throws ParseException {
        if (professeur.getStagiaire_ids() != null) {
            if (!professeur.getStagiaire_ids().isEmpty()) {
                professeur.getStagiaire_ids().stream().map(id -> {
                    InfosStagiaireEntity stagiaireEntity = stagiaireService.findOne(id);
                    if (stagiaireEntity != null) {
                        professeur.getListe_des_eleves().add(stagiaireEntity);
                    }
                    return id;
                });
            }
        }

        if (professeur.getMatiere_id() != null) {
            InfosMatiereEntity matiere = matiereService.findOne(professeur.getMatiere_id());
            if (matiere != null) {
                professeur.setMatiere(matiere);
            }
        }

        return this.ajouter_update(professeur);
    }

}
