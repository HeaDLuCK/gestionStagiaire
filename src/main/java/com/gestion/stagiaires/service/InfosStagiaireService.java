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
	private PasswordEncoder passwordEncoder;

	@Autowired
	private InfosProfService profService;

	@Autowired
	private InfosEtablissementService etablissementService;

	// public ResponseEntity<Object> getAll() {
	// Map<String, Object> body = new HashMap<>();// output
	// System.out.println(stagiaireRepository.findById((long) 1).get());
	// int list = stagiaireRepository.findById((long)
	// 1).get().getListe_de_professeurs().size();
	// body.put("data", list);

	// return ResponseEntity.status(HttpStatus.OK).body(body);

	// }

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

	/**
	 * create Number with 5 digits
	 */
	public static String createNumber(Long number) {
		int OUTPUT_LENGTH = 5;
		if (number == null) {
			return null;
		}
		int numberLength = String.valueOf(number).length();
		int helper = OUTPUT_LENGTH - numberLength;
		String output = ""; // the return value
		for (int i = 0; i < helper; i++) {
			output += "0";
		}
		output += number;
		return output;
	}

	/**
	 * obtenir numéro pour nouveau stagiaire
	 *
	 */
	public ResponseEntity<Object> getGenereNumero() {
		Map<String, String> body = new HashMap<>();
		Long NumeroGenere = stagiaireRepository.findLastNumero();

		body.put("numero", createNumber(NumeroGenere + 1));
		return ResponseEntity.status(HttpStatus.OK).body(body);
	}

	/**
	 * il renverra l'id et le nom complet du stagiaire
	 */
	public ResponseEntity<Object> getStagiaireInfo() {
		Map<String, Object> body = new HashMap<>();// output
		body.put("data", stagiaireRepository.findForSelect());
		return ResponseEntity.status(HttpStatus.OK).body(body);
	}

	@Override
	public ResponseEntity<Object> ajouter_update(InfosStagiaireEntity stagiaire) throws ParseException {
		Map<String, Object> body = new HashMap<>();// output
		if (stagiaire.getId() == null) {
			Long derniere_id = stagiaireRepository.findLastNumero() != null ? stagiaireRepository.findLastNumero() : 0;
			Long id = derniere_id + 1;
			String helper = createNumber(id);
			if (!helper.equals(stagiaire.getNumero())) {
				body.put("message", "le numéro doit être unique");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
			}
		} else {
			/**
			 * vérifier si le stagiaire dans la base de données
			 */
			InfosStagiaireEntity validated_stagiaire = super.findOne(stagiaire.getId());
			if (validated_stagiaire != null) {
				String helper = stagiaire.getNumero();
				if (!helper.equals(validated_stagiaire.getNumero())) {
					body.put("message", "le numéro doit être unique");
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
				}
			}
		}
		if (!this.valide_lage(stagiaire.stagiaireAge())) {
			body.put("message", "l'âge doit être inférieur à 23 ans et supérieur à 10 ans");
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(body);
		}
		if (!this.valide_le_nom_complet(stagiaire.getNom(), stagiaire.getPrenom(), stagiaire.getId())) {
			body.put("message", "cet nom complet est utilisé");
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(body);
		}
		stagiaire.setMot_de_passe(passwordEncoder.encode(stagiaire.getMot_de_passe()));

		return super.ajouter_update(stagiaire);
	}

	public ResponseEntity<Object> ajouter_update_jointure(InfosStagiaireEntity stagiaire)
			throws ParseException {
		if (stagiaire.getProfesseurs_ids() != null) {
			if (!stagiaire.getProfesseurs_ids().isEmpty()) {
				List<InfosProfEntity> helper=new ArrayList<>();
				stagiaire.getProfesseurs_ids().forEach(id -> {
					InfosProfEntity professeur = profService.findOne(id);
					if (professeur != null) {
						helper.add(professeur);
					}
				});
				stagiaire.setProfesseurs(helper);
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
