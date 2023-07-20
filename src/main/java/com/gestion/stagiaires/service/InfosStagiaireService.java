package com.gestion.stagiaires.service;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gestion.stagiaires.entities.InfosEtablissementEntity;
import com.gestion.stagiaires.entities.InfosProfEntity;
import com.gestion.stagiaires.entities.InfosStagiaireEntity;
import com.gestion.stagiaires.repository.InfosStagiaireRepository;

@Service
@Lazy
public class InfosStagiaireService extends BaseService<InfosStagiaireEntity, InfosStagiaireRepository> {

	@Autowired
	InfosStagiaireRepository stagiaireRepository;

	@Autowired
	private  PasswordEncoder passwordEncoder;

	@Autowired
	private InfosProfService profService;

	@Autowired
	private InfosEtablissementService etablissementService;


	/*
	 * valider si l'âge est entre 10 ans et 23 ans
	 */
	public boolean valide_lage(Integer age) {
		if (age < 10 || age > 23) {
			return false;
		}
		return true;
	}
	/*
	 * valider si le nom complet est unique
	 */

	public boolean valide_le_nom_complet(String nom, String prenom, Long id) {
		if (prenom != null) {

			/*
			 * le deuxième id est l'identifiant que j'ai obtenu à partir de cette requête
			 * et je vais le comparer avec l'identifiant du stagiaire/professeur
			 * s'ils correspondent, cela signifie que c'est le même stagiaire/professeur
			 */

			Long second_id = stagiaireRepository.findByFullName(nom, prenom);
			if (second_id != null && id != second_id) {
				return false;
			}
		}
		return true;
	}

	@Override
	public ResponseEntity<Object> ajouter_update(InfosStagiaireEntity stagiaire) throws ParseException {
		Map<String, Object> body = new HashMap<>();// output
		if (!this.valide_lage(stagiaire.stagiaireAge())) {
			body.put("message", "l'âge doit être inférieur à 23 ans et supérieur à 10 ans");
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(body);
		}
		if (!this.valide_le_nom_complet(stagiaire.getNom(), stagiaire.getPrenom(), stagiaire.getId())) {
			body.put("message", "cet nom complet est utilisé");
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(body);
		}
		stagiaire.setMot_de_passe(passwordEncoder.encode(stagiaire.getMot_de_passe()));
		System.out.println(stagiaire);
		return super.ajouter_update(stagiaire);
	}

	public ResponseEntity<Object> ajouter_update_jointure(InfosStagiaireEntity stagiaire)
			throws ParseException {
		if (stagiaire.getProfesseurs_ids() != null) {
			if (!stagiaire.getProfesseurs_ids().isEmpty()) {
				stagiaire.getProfesseurs_ids().stream().map(id -> {
					InfosProfEntity professeur = profService.findOne(id);
					if (professeur != null) {
						stagiaire.getListe_de_professeur().add(professeur);
					}
					return id;
				});
			}
		}
		if (stagiaire.getEtablissement_id() != null) {
			InfosEtablissementEntity etablissement = etablissementService.findOne(stagiaire.getEtablissement_id());
			if (etablissement != null) {
				stagiaire.setEtablissement(etablissement);
			}
		}

		return this.ajouter_update(stagiaire);
	}

}
