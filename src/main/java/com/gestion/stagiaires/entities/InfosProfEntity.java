package com.gestion.stagiaires.entities;

import java.util.List;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="infos_prof")
@Data
@NoArgsConstructor
public class InfosProfEntity  extends BaseEntity {
	
	@Column(nullable = false,unique = true)
	@Size(min = 5,message = "le numéro doit contenir 5 caractères")
	@Setter(value=AccessLevel.NONE)
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
	@JoinColumn(name="matiere", nullable = false)
	private InfosMatiereEntity matiere;
	
	//jointure pour eleves
	@ManyToMany(mappedBy = "liste_de_professeur",cascade={CascadeType.PERSIST, CascadeType.DETACH})
	private List<InfosStagiaireEntity> liste_des_eleves;
	
	

}
