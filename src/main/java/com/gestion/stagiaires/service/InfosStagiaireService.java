package com.gestion.stagiaires.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gestion.stagiaires.entities.InfosStagiaireEntity;

@Repository
public interface InfosStagiaireService extends JpaRepository<InfosStagiaireEntity, Long>  {
	@Query("SELECT numéro FROM InfosStagiaireEntity is WHERE is.nom = :nom and is.prénom = :prenom")
	Long findByFullName(
			@Param("nom") String nom,
			@Param("prenom") String prénom
			);


}
