package com.gestion.stagiaires.entities;

import java.util.Set;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="infos_prof")
@Data
@NoArgsConstructor
public class InfosProfEntity {
	
	@Id
	@Max(value=99999 ,message = "5 caractères est la longueur maximale")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Setter(AccessLevel.NONE)
	@NotNull
	private Long numéro;
	
	@Column(length = 120, nullable = false)
	@NotNull
	private String nom;
	
	@Column(length= 120)
	private String prénom;
	
	@Column(length=255)
	private String adresse;
	

	private Boolean status=true;
	
	//jointure pour matière
	@ManyToOne
	@JoinColumn(name="matière", nullable = false)
	private InfosMatièreEntity matière;
	
	//jointure pour eleves
	@ManyToMany(mappedBy = "liste_de_professeur",cascade={CascadeType.PERSIST, CascadeType.DETACH})
	private Set<InfosStagiaireEntity> liste_des_élèves;
	
	

}
