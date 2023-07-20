package com.gestion.stagiaires.dto;

import java.util.Date;
import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StagiaireDTO {

    @Size(min = 5,message = "le numéro doit contenir 5 caractères")
    @NotEmpty(message = "le numéro ne peut pas être vide")
    private String numero;

    @NotEmpty(message = "nom ne peut pas être vide")
    private String nom;
    
    private String prenom;

    @NotNull(message = "la date de naissance ne peut pas être vide")
    private Date date_de_naissance;

    private String addresse;

    private List<Long> liste_de_professeur;

    private Long etablissement;

    @NotEmpty(message = "login / username ne peut pas être vide")
    private String login;
    
    @NotEmpty(message = "le mot de passe ne peut pas être vide")
    private String mot_de_passe;

    private Boolean status=true;

}
