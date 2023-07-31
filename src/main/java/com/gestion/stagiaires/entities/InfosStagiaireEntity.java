package com.gestion.stagiaires.entities;

import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
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
@JsonIdentityInfo(
  generator = ObjectIdGenerators.PropertyGenerator.class, 
  property = "id")
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
	private String addresse;

	// jointure pour Liste_de_professeur;
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "stagiaire_professeur_association", joinColumns = @JoinColumn(name = "prof_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "stagiaire_id", referencedColumnName = "id"))
	List<InfosProfEntity> liste_de_professeur;

	// jointure pour établissement;
	@OneToOne
	@JoinColumn(name = "etablissement_id", referencedColumnName = "id")
	private InfosEtablissementEntity etablissement;

	@Column(length = 20, nullable = false, unique = true)
	@NotEmpty(message = "login / username ne peut pas être vide")
	private String login;

	@Column(length = 255)
	@NotEmpty(message = "le mot de passe ne peut pas être vide")
	// @JsonIgnore
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
