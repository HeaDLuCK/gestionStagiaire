package com.gestion.stagiaires.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name="infos_matiere")
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@JsonIdentityInfo(
  generator = ObjectIdGenerators.PropertyGenerator.class, 
  property = "id")
public class InfosMatiereEntity extends BaseEntity{
	
	@Column(nullable = false,unique = true)
	@Size(min = 5,message = "le numéro doit contenir 5 caractères")
	@NotEmpty(message = "le numéro ne peut pas être vide")
	private String numero;
	
	@Column(length=200,nullable = false)
	@NotEmpty(message = "libelle ne peut pas être vide")
	private String libelle;
	
	@Column
	private Boolean status=true;
	
	//jointure pour Liste des profs
	@OneToMany(mappedBy = "matiere",cascade = CascadeType.ALL)
	private List<InfosProfEntity> liste_des_profs;


	@Transient
	private List<Long> professeurs_ids;//pour define les professeurs
	
}
