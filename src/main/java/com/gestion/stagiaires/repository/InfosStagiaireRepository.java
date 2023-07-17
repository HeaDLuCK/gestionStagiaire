package com.gestion.stagiaires.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.gestion.stagiaires.entities.InfosStagiaireEntity;

public interface InfosStagiaireRepository  extends  CrudRepository<InfosStagiaireEntity, Long> {

	@Query("SELECT numéro FROM InfosStagiaireEntity is WHERE is.nom = :nom and is.prénom = :prenom")
	Long findByFullName(
			@Param("nom") String nom,
			@Param("prenom") String prénom
			);

	Optional<InfosStagiaireEntity> findByLogin(String login);
}
