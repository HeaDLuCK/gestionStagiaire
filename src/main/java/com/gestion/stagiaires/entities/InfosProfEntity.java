package com.gestion.stagiaires.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gestion.stagiaires.dto.MatiereDto;
import com.gestion.stagiaires.dto.StagiareDto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@Table(name = "infos_prof")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class InfosProfEntity extends BaseEntity {

	@Column(nullable = false, unique = true)
	@Size(min = 5, message = "le numéro doit contenir 5 caractères")
	@NotEmpty(message = "le numéro ne peut pas être vide")
	private String numero;

	@Column(length = 120, nullable = false)
	@NotNull
	private String nom;

	@Column(length = 120)
	private String prenom;

	@Column(length = 255)
	private String adresse;

	@Column
	private Boolean status = true;

	// jointure pour matière
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "matiere_id")
	@JsonIgnore
	private InfosMatiereEntity matiere;

	@Transient
	@JsonProperty("matiere")
	private MatiereDto matieredto;

	public MatiereDto getMatieredto() {
		if (matiere != null) {
			return new MatiereDto(matiere.getId(), matiere.getLibelle());
		}
		return null;
	}

	// jointure pour eleves
	@ManyToMany(mappedBy = "professeurs", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<InfosStagiaireEntity> stagiaires = new ArrayList<>();

	@Transient
	List<StagiareDto> stagiairesList = new ArrayList<>();

	public List<StagiareDto> getStagiairesList() {
		List<StagiareDto> data = new ArrayList<>();
		getStagiaires().forEach(elem -> {
			data.add(new StagiareDto(elem.getId(), elem.getNom(), elem.getPrenom()));
		});
		return data;
	}

	@Transient
	private List<Long> stagiaire_ids;// pour define les stagiaires

	@Transient
	private Long matiere_id;// pour define l'matiere

}
