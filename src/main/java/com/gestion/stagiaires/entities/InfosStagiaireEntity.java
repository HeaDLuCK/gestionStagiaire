package com.gestion.stagiaires.entities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "infos_stagiaire")
@Data
@Builder
@NoArgsConstructor
public class InfosStagiaireEntity implements UserDetails {

	
	@Id
	@Max(value=99999 ,message = "5 caractères est la longueur maximale")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter(AccessLevel.NONE)
	private Long numéro;

	@Column(length = 120 , nullable = false)
	@NotEmpty(message = "nom ne peut pas être vide")
	private String nom;

	@Column(length = 120)
	private String prénom;

	@Column
	@DateTimeFormat(pattern ="dd/MM/yyyy" )
	@NotNull
	private Date date_de_naissance;

	@Transient
    public Integer stagiaireAge() throws ParseException {
		SimpleDateFormat franceDateFormat = new SimpleDateFormat("dd/mm/yyyy");   //format de date pour france 
		Date dt1=franceDateFormat.parse(this.getDate_de_naissance());
		Date dt2=new Date();
		Long diff=dt2.getTime()-dt1.getTime();
		return (int) (TimeUnit.MILLISECONDS.toDays(diff) / 365);
		
	};
	@Column
	private String Addresse;


//	jointure pour  Liste_de_professeur;
	@ManyToMany(cascade={CascadeType.PERSIST, CascadeType.DETACH})
	@JoinTable(
			name = "stagiaire_professeur_association",
			joinColumns = @JoinColumn(name = "prof_id" , referencedColumnName = "numéro"),
			inverseJoinColumns = @JoinColumn(name = "stagiaire_id", referencedColumnName = "numéro")
			)
	Set<InfosProfEntity> liste_de_professeur;

//	jointure pour  établissement;
	@OneToOne
	@JoinColumn(name="etablissement",referencedColumnName = "libelle")
	private InfosÉtablissementEntity établissement;

	@Column(length = 20, nullable = false)
	@NotEmpty(message = "login/username ne peut pas être vide")
	private String login;

	@Column(length = 20)
	@NotEmpty(message = "le mot de passe ne peut pas être vide")
	private String mot_de_passe;
	

	private Boolean status=true;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getPassword() {
		return  mot_de_passe;
	}

	@Override
	public String getUsername() {
		return login;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
