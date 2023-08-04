package com.gestion.stagiaires.dto;

import lombok.Data;

@Data
public class ProfDto {
    private Long id;
    private String nom_complet;

    public ProfDto(Long id, String nom, String prenom) {
        this.id = id;
        this.nom_complet = nom + (prenom == null ? "" : prenom);
    }
}
