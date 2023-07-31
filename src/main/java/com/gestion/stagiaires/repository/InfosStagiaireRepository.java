package com.gestion.stagiaires.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gestion.stagiaires.dto.StagiareDto;
import com.gestion.stagiaires.entities.InfosStagiaireEntity;

public interface InfosStagiaireRepository  extends  JpaRepository<InfosStagiaireEntity, Long> {

	@Query("SELECT id FROM InfosStagiaireEntity is WHERE is.nom = :nom and is.prenom = :prenom ")
	Long findByFullName(
			@Param("nom") String nom,
			@Param("prenom") String prenom
			);

	Optional<InfosStagiaireEntity> findByLogin(String login);

	@Query("SELECT is.id,CONCAT(is.nom, \" \", is.prenom) FROM InfosStagiaireEntity is WHERE is.status = true")
	Optional<StagiareDto> findForSelect();


	@Query("SELECT is.id from InfosStagiaireEntity is ORDER BY is.id DESC LIMIT 1")
	Long findLastId();

	@Query("SELECT MAX(CAST(is.numero AS int)) from InfosStagiaireEntity is ")
	Long findLastNumero();
}
