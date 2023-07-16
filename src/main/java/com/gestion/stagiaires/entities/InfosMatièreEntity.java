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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter(AccessLevel.NONE)
	private Long numéro;
	
	@Column(length=200)
	@NotNull
	private String libellé;
	
	//jointure pour Liste des profs
	@OneToMany(mappedBy = "matière",cascade = CascadeType.ALL)
	private Set<InfosProfEntity> liste_des_profs;
	
}
