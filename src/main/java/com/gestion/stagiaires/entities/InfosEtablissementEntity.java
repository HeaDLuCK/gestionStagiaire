package com.gestion.stagiaires.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="infos_etablissement")
@NoArgsConstructor
@Data
public class InfosEtablissementEntity extends BaseEntity{
	
	@Column(length=200, nullable = false,unique = true)
	private String libelle;
	
	@Column(length=255, nullable = false)
	private String adresse;

}
