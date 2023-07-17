package com.gestion.stagiaires.authentication;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {
	@NotEmpty(message = "login  ne peut pas être vide")
    private String login;
	@NotEmpty(message = "mot de passe  ne peut pas être vide")
    private String mot_de_passe;
}
