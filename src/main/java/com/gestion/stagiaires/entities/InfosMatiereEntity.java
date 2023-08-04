package com.gestion.stagiaires.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gestion.stagiaires.dto.ProfDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "infos_matiere")
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class InfosMatiereEntity extends BaseEntity {

	@Column(nullable = false, unique = true)
	@Size(min = 5, message = "le numéro doit contenir 5 caractères")
	@NotEmpty(message = "le numéro ne peut pas être vide")
	private String numero;

	@Column(length = 200, nullable = false)
	@NotEmpty(message = "libelle ne peut pas être vide")
	private String libelle;

	@Column
	private Boolean status = true;

	// jointure pour Liste des profs
	@OneToMany(mappedBy = "matiere",fetch = FetchType.LAZY)
	@JsonIgnore
	private List<InfosProfEntity> liste_des_profs = new ArrayList<>();

	@Transient
	private List<ProfDto> professeursList = new ArrayList<>();

	public List<ProfDto> getProfesseursList() {
		List<ProfDto> data = new ArrayList<>();
		getListe_des_profs().forEach(elem -> {
			data.add(new ProfDto(elem.getId(), elem.getNom(), elem.getPrenom()));
		});
		return data;
	}

	// @Transient
	private List<Long> professeurs_ids;// pour define les professeurs

}
