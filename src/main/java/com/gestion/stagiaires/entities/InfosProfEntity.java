package com.gestion.stagiaires.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name="infos_prof")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonIdentityInfo(
  generator = ObjectIdGenerators.PropertyGenerator.class, 
  property = "id")
public class InfosProfEntity  extends BaseEntity {
	

	@Column(nullable = false,unique = true)
	@Size(min = 5,message = "le numéro doit contenir 5 caractères")
	@NotEmpty(message = "le numéro ne peut pas être vide")
	private String numero;
	
	@Column(length = 120, nullable = false)
	@NotNull
	private String nom;
	
	@Column(length= 120)
	private String prenom;
	
	@Column(length=255)
	private String adresse;
	
	@Column
	private Boolean status=true;
	
	//jointure pour matière
	@ManyToOne
	@JoinColumn(name="matiere")
	private InfosMatiereEntity matiere;
	
	//jointure pour eleves
	@ManyToMany(mappedBy = "liste_de_professeur",cascade={CascadeType.ALL})
	private List<InfosStagiaireEntity> liste_des_eleves;
	
	@Transient
	private List<Long> stagiaire_ids;//pour define les stagiaires

	@Transient
	@NotNull(message = "tu devrais ajouter un matière")
	private Long matiere_id;//pour define l'matiere


}
