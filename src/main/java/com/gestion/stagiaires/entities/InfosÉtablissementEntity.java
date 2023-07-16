package com.gestion.stagiaires.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="infos_établissement")
@NoArgsConstructor
@Data
public class InfosÉtablissementEntity {

	@Id
	@Column(length=200)
	@NotNull
	private String libelle;
	
	@Column(length=255)
	private String adresse;

}
