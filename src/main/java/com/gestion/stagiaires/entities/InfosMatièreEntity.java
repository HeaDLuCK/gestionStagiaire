package com.gestion.stagiaires.entities;

import java.util.Set;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="infos_matière")
@NoArgsConstructor
@Data
public class InfosMatièreEntity {
	@Id
	@Max(value=99999 ,message = "5 caractères est la longueur maximale")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter(AccessLevel.NONE)
	private Long numéro;
	
	@Column(length=200)
	@NotNull
	private String libellé;
	

	private Boolean status=true;
	
	//jointure pour Liste des profs
	@OneToMany(mappedBy = "matière",cascade = CascadeType.ALL)
	private Set<InfosProfEntity> liste_des_profs;
	
}
