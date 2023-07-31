package com.gestion.stagiaires.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name="infos_etablissement")
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class InfosEtablissementEntity extends BaseEntity{
	
	@Column(length=200, nullable = false,unique = true)
	@NotEmpty(message = "libelle ne peut pas être vide")
	private String libelle;
	
	@Column(length=255, nullable = true)
	private String adresse;

}
