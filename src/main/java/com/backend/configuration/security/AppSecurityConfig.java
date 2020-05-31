package com.backend.configuration.security;



import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.backend.entities.Utilisateur;
import com.backend.exceptions.NotFoundException;
import com.backend.services.UtilisateurService;
import com.google.common.collect.ImmutableList;


@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	UtilisateurService utilisateurService;
	
	UserPrincipalDetailsService service;
	
	@Autowired
	public AppSecurityConfig(UserPrincipalDetailsService service) {

		this.service = service;
	}
	
	@SuppressWarnings("unused")
	@PostConstruct
	public void init() {
		
		try {
		List<Utilisateur>  currentUserList = utilisateurService.getUtilisateurs(null);
		} catch (NotFoundException e) {
			Utilisateur    utilisateur    = new Utilisateur();
			utilisateur.setUsername("admin");
			utilisateur.setPassword("admin");
			utilisateur.setRole("Admin");
			utilisateurService.addUtilisateur(utilisateur);
			
		}
	    
	}

	@Bean
	public DaoAuthenticationProvider autProvider()
	{
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(service);
		provider.setPasswordEncoder(new BCryptPasswordEncoder());
		
		return provider;
	}


@Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(ImmutableList.of(""));
        configuration.setAllowedMethods(ImmutableList.of("HEAD",
                "GET", "POST", "PUT", "DELETE", "PATCH"));
        // setAllowCredentials(true) is important, otherwise:
        // The value of the 'Access-Control-Allow-Origin' header in the response must not be the wildcard '' when the request's credentials mode is 'include'.
        configuration.setAllowCredentials(true);
        // setAllowedHeaders is important! Without it, OPTIONS preflight request
        // will fail with 403 Invalid CORS request
        configuration.setAllowedHeaders(ImmutableList.of("Authorization", "Cache-Control", "Content-Type"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http		
			.cors()
			.and()
			.authorizeRequests()
			//categorie
			.antMatchers(HttpMethod.POST,"/categorie").hasRole("Admin")		//creer categorie
			.antMatchers(HttpMethod.GET,"/categories").hasAnyRole("Admin","User")	//afficher categories
			.antMatchers("/categorie/{id}").hasRole("Admin")		//modifier supprimer categorie
			//unite de mesure
			.antMatchers(HttpMethod.POST,"/uniteDeMesure").hasRole("Admin")		//creer unite
			.antMatchers(HttpMethod.GET,"/unitesDeMesure").hasAnyRole("Admin","User")	//afficher unite
			.antMatchers("/uniteDeMesure/{id}").hasRole("Admin")		//modifier supprimer unite
			//fournisseur
			.antMatchers(HttpMethod.POST,"/fournisseur").hasRole("Admin")		//creer fournisseur
			.antMatchers(HttpMethod.GET,"/fournisseurs").hasAnyRole("Admin","User")	//afficher fournisseurs
			.antMatchers("/fournisseur/{id}").hasRole("Admin")		//modifier supprimer fournisseur
			//emplacement
			.antMatchers(HttpMethod.POST,"/emplacement").hasRole("Admin")		//creer emplacements
			.antMatchers(HttpMethod.GET,"/emplacements").hasAnyRole("Admin","User")	//afficher emplacements
			.antMatchers("/emplacement/{id}").hasRole("Admin")		//modifier supprimer emplacement

			//produit
			.antMatchers(HttpMethod.POST,"/produit").hasRole("User")	//creer produit
			.antMatchers(HttpMethod.GET,"/produits").hasRole("User")	//afficher produits
			.antMatchers(HttpMethod.GET,"/stock/{id}/produits").hasRole("User")	//afficher produits par stock
			.antMatchers(HttpMethod.GET,"/categorie/{id}/produits").hasRole("User")	//afficher produits par categorie
			.antMatchers(HttpMethod.GET,"/fournisseur/{id}/produits").hasRole("User")	//afficher produits par fournisseur
			.antMatchers(HttpMethod.GET,"/UniteDeMesure/{id}/produits").hasRole("User")	//afficher produits par uniteDeMesure
			.antMatchers(HttpMethod.GET,"/produits/{nom}").hasRole("User")	//afficher meme produit dans differents stocks
			.antMatchers("/produit/{id}").hasRole("User")		//modifier supprimer produit
			
			//stock
			.antMatchers(HttpMethod.POST,"/stock").hasRole("User")		//creer stock
			.antMatchers(HttpMethod.GET,"/stocks").hasRole("User")	//afficher les stocks
			.antMatchers(HttpMethod.GET,"/emplacement/{id}/stock").hasRole("User")	//afficher stock d'un emplacement
			.antMatchers("/stock/{id}").hasRole("User")		//modifier supprimer stock	
			
			//inventaire
			.antMatchers(HttpMethod.POST,"/inventaire").hasRole("User")		//creer inventaire
			.antMatchers(HttpMethod.GET,"/stock/{id}/inventaires").hasAnyRole("User")	//afficher inventaires d'un stock
			.antMatchers(HttpMethod.GET,"/inventaires").hasAnyRole("User")	//afficher inventaires
			
			//mouvement
			.antMatchers(HttpMethod.POST,"/mouvement").hasRole("User")		//creer mouvement
			.antMatchers(HttpMethod.GET,"/stock/{id}/mouvements").hasAnyRole("User")	//afficher mouvements d'un stock
			.antMatchers(HttpMethod.GET,"/mouvements").hasAnyRole("User")	//afficher mouvements

			//utilisateur
			.antMatchers(HttpMethod.POST,"/utilisateur").hasRole("Admin")		//creer utilisateur
			.antMatchers(HttpMethod.GET,"/utilisateurs").hasRole("Admin")	//afficher utilisateurs
			.antMatchers("/utilisateur/{id}").hasRole("Admin")		//modifier supprimer utilisateur
			.antMatchers(HttpMethod.GET,"/utilisateur/{username}").hasRole("Admin")		//chercher utilisateur par username
			.and()
			.httpBasic()
			.and()
			.csrf().disable()
			;
			
		
		
		super.configure(http);
	}
	
	
	

}
