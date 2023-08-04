package com.gestion.stagiaires.config;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.google.gson.Gson;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	@Autowired
	private AuthenticationProvider authenticationProvider;
	@Autowired
	private JwtAuthenticationFilter jwtAuthFilter;

	private Gson gson = new Gson();

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(authorize -> authorize
						.requestMatchers("/api/v1/auth/**", "/api/v1/authorize/stagiaire/numerogenere",
								"/api/v1/authorize/professeur/select", "/api/v1/authorize/etablissement/select")
						.permitAll()
						.anyRequest().authenticated())
				.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authenticationProvider(authenticationProvider)
				.exceptionHandling(exception -> exception.authenticationEntryPoint((request, response, e) -> {
					Map<String, Object> error = new LinkedHashMap<>();
					error.put("message", "unauthorized");
					error.put("time", LocalDateTime.now().toString());
					response.setContentType("application/json");
					response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					response.getWriter().write(gson.toJson(error));
				}))
				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
				.cors(Customizer.withDefaults());
		return http.build();
	}

}
