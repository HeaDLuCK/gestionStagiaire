package com.gestion.stagiaires.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="infos_établissement")
@NoArgsConstructor
@Data
public class InfosÉtablissementEntity {

	@Id
	@Column(length=200, nullable = false)
	private String libelle;
	
	@Column(length=255, nullable = false)
	private String adresse;

}
