//package com.backend.configuration.security;
//
//
//
//import java.util.List;
//
//import javax.annotation.PostConstruct;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
//import com.backend.entities.Utilisateur;
//import com.backend.exceptions.NotFoundException;
//import com.backend.services.UtilisateurService;
//
//
//@Configuration
//@EnableWebSecurity
//public class AppSecurityConfig extends WebSecurityConfigurerAdapter {
//	
//	@Autowired
//	UtilisateurService utilisateurService;
//	
//	UserPrincipalDetailsService service;
//	
//	@Autowired
//	public AppSecurityConfig(UserPrincipalDetailsService service) {
//
//		this.service = service;
//	}
//	
//	@PostConstruct
//	public void init() {
//		
//		try {
//		List<Utilisateur>  currentUserList = utilisateurService.getUtilisateurs(null);
//		} catch (NotFoundException e) {
//			Utilisateur    utilisateur    = new Utilisateur();
//			utilisateur.setUsername("admin");
//			utilisateur.setPassword("admin");
//			utilisateur.setRole("Admin");
//			utilisateurService.addUtilisateur(utilisateur);
//			
//		}
//	    
//	}
//
//	@Bean
//	public DaoAuthenticationProvider autProvider()
//	{
//		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//		provider.setUserDetailsService(service);
//		provider.setPasswordEncoder(new BCryptPasswordEncoder());
//		
//		return provider;
//	}
//
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		
//		http
//			.authorizeRequests()
//			//categorie
//			.antMatchers(HttpMethod.POST,"/categorie").hasRole("Admin")		//creer categories
//			.antMatchers(HttpMethod.GET,"/categories").hasAnyRole("Admin","User")	//afficher categories
//			.antMatchers("/categorie/{id}").hasRole("Admin")		//modifier supprimer categorie
//			//unite de mesure
//			.antMatchers(HttpMethod.POST,"/uniteDeMesure").hasRole("Admin")		//creer unite
//			.antMatchers(HttpMethod.GET,"/unitesDeMesure").hasAnyRole("Admin","User")	//afficher unite
//			.antMatchers("/uniteDeMesure/{id}").hasRole("Admin")		//modifier supprimer unite
//			//fournisseur
//			.antMatchers(HttpMethod.POST,"/fournisseur").hasRole("Admin")		//creer fournisseurs
//			.antMatchers(HttpMethod.GET,"/fournisseurs").hasAnyRole("Admin","User")	//afficher fournisseurs
//			.antMatchers("/fournisseur/{id}").hasRole("Admin")		//modifier supprimer fournisseur
//			//emplacement
//			.antMatchers(HttpMethod.POST,"/emplacement").hasRole("Admin")		//creer emplacements
//			.antMatchers(HttpMethod.GET,"/emplacements").hasAnyRole("Admin","User")	//afficher emplacements
//			.antMatchers("/emplacement/{id}").hasRole("Admin")		//modifier supprimer emplacement
//
//			//produit
//			.antMatchers(HttpMethod.POST,"/produit").hasRole("User")	//creer produits
//			.antMatchers(HttpMethod.GET,"/categorie/{id}/produits").hasRole("User")	//afficher produits par categorie
//			.antMatchers(HttpMethod.GET,"/fournisseur/{id}/produits").hasRole("User")	//afficher produits par fournisseur
//			.antMatchers(HttpMethod.GET,"/stock/{id}/produits").hasRole("User")	//afficher produits par stock
//			.antMatchers(HttpMethod.GET,"/uniteDeMesure/{id}/produits").hasRole("User")	//afficher produits par unité de mesure
//			.antMatchers("/produit/{id}").hasRole("User")		//modifier supprimer produit
//			
//			//stock
//			.antMatchers(HttpMethod.POST,"/stock").hasRole("User")		//creer stocks
//			.antMatchers("/emplacement/{id}/stock").hasRole("User")	//afficher stock
//			.antMatchers("/stock/{id}").hasRole("User")		//modifier supprimer stock	
//			
//			//		HNA FIN WSLT
//			
//			
//			//inventaire
//			.antMatchers(HttpMethod.POST,"/inventaire").hasRole("Admin")		//creer inventaires
//			.antMatchers(HttpMethod.GET,"/stock/{id}/inventaires").hasAnyRole("Admin","User")	//afficher inventaires
//			.antMatchers("/inventaire/{id}").hasRole("Admin")		//modifier supprimer inventaire
//			//mouvement
//			.antMatchers(HttpMethod.POST,"/mouvement").hasRole("User")		//creer mouvements
//			.antMatchers(HttpMethod.GET,"/stock/mouvements").hasAnyRole("User")	//afficher mouvements
//			.and()
//			.httpBasic()
//			.and()
//			.csrf().disable()
//			;
//			
//		
//		
//		super.configure(http);
//	}
//	
//	
//	
//
//}
