package com.gestion.stagiaires.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gestion.stagiaires.entities.InfosStagiaireEntity;

public interface InfosStagiaireRepository  extends  IBaseRepository<InfosStagiaireEntity, Long> {

	@Query("SELECT id FROM InfosStagiaireEntity is WHERE is.nom = :nom and is.prenom = :prenom")
	Long findByFullName(
			@Param("nom") String nom,
			@Param("prenom") String prenom
			);
	Optional<InfosStagiaireEntity> findByLogin(String login);
}
