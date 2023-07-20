package com.gestion.stagiaires.entities;

import java.util.List;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name="infos_matiere")
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class InfosMatiereEntity extends BaseEntity{
	
	@Column(nullable = false,unique = true)
	@Size(min = 5,message = "le numéro doit contenir 5 caractères")
	private String numero;
	
	@Column(length=200)
	@NotNull
	private String libelle;
	
	@Column
	private Boolean status=true;
	
	//jointure pour Liste des profs
	@OneToMany(mappedBy = "matiere",cascade = CascadeType.ALL)
	private List<InfosProfEntity> liste_des_profs;


	@Transient
	private List<Long> professeurs_ids;//pour define les professeurs
	
}
