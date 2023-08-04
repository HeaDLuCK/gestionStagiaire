package com.gestion.stagiaires.entities;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gestion.stagiaires.dto.EtablissementDto;
import com.gestion.stagiaires.dto.ProfDto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "infos_stagiaire", uniqueConstraints = @UniqueConstraint(columnNames = { "nom", "prenom" }))
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class InfosStagiaireEntity extends BaseEntity implements UserDetails {

	@Column(nullable = false, unique = true)
	@Size(min = 5, message = "le numéro doit contenir 5 caractères")
	@NotEmpty(message = "le numéro ne peut pas être vide")
	private String numero;

	@Column(length = 120, nullable = false)
	@NotEmpty(message = "nom ne peut pas être vide")
	private String nom;

	@Column(length = 120)
	private String prenom;

	@Column
	@NotNull(message = "la date de naissance ne peut pas être vide")
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date date_de_naissance;

	@Column
	private String adresse;

	// jointure pour Liste_de_professeur;
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "stagiaire_professeur_association", joinColumns = @JoinColumn(name = "stagiaire_id"), inverseJoinColumns = @JoinColumn(name = "prof_id"))
	@JsonIgnore
	List<InfosProfEntity> professeurs = new ArrayList<>();

	@Transient
	List<ProfDto> professoursList = new ArrayList<>();

	public List<ProfDto> getProfesseursList() {
		List<ProfDto> data = new ArrayList<>();
		getProfesseurs().forEach(elem -> {
			data.add(new ProfDto(elem.getId(), elem.getNom(), elem.getPrenom()));
		});
		return data;
	}

	// jointure pour établissement;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "etablissement_id")
	@JsonIgnore
	private InfosEtablissementEntity etablissement;

	@Transient
	@JsonProperty("etablissement")
	private EtablissementDto etablissementdto;

	public EtablissementDto getEtablissementdto(){
		if(etablissement != null){
			return  new EtablissementDto(etablissement.getId(), etablissement.getLibelle());
		}
		return null;
		
	}
	

	@Column(length = 20, nullable = false, unique = true)
	@NotEmpty(message = "login / username ne peut pas être vide")
	private String login;

	@Column(length = 255,nullable = false)
	@NotEmpty(message = "insérez un nouveau mot de passe ou un ancien mot de passe pour mettre à jour avec succès")
	private String mot_de_passe;


	private Boolean status = true;

	public Integer stagiaireAge() throws ParseException {
		Date dt1 = this.getDate_de_naissance();
		Date dt2 = new Date();
		Long diff = dt2.getTime() - dt1.getTime();
		return (int) (TimeUnit.MILLISECONDS.toDays(diff) / 365);

	};

	@Transient
	private List<Long> professeurs_ids;// pour define les professeurs

	@Transient
	private Long etablissement_id;// pour define l'etablissement

	@Override
	@JsonIgnore
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	@JsonIgnore
	public String getPassword() {
		return mot_de_passe;
	}

	@Override
	@JsonIgnore
	public String getUsername() {
		return login;
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isEnabled() {
		return true;
	}

}
