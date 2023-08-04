package com.gestion.stagiaires.dto;

import lombok.Data;

@Data
public class StagiareDto {
    private Long id;
    private String nom_complet;

    public StagiareDto(Long id, String nom, String prenom) {
        this.id = id;
        this.nom_complet = nom + (prenom == null ? "" : prenom);
    }
}
