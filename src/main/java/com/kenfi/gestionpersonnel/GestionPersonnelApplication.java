package com.kenfi.gestionpersonnel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.kenfi.gestionpersonnel.modele.Role;
import com.kenfi.gestionpersonnel.modele.Utilisateur;
import com.kenfi.gestionpersonnel.depot.RoleDepot;
import com.kenfi.gestionpersonnel.depot.UtilisateurDepot;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Collections;
import java.util.HashSet;

@SpringBootApplication
public class GestionPersonnelApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionPersonnelApplication.class, args);
	}

	@Bean
	CommandLineRunner init(RoleDepot roleDepot, UtilisateurDepot utilisateurDepot, PasswordEncoder passwordEncoder) {
		return args -> {
			if (roleDepot.findByNom("ROLE_ADMIN").isEmpty()) {
				roleDepot.save(new Role(null, "ROLE_ADMIN"));
				roleDepot.save(new Role(null, "ROLE_RH"));
				roleDepot.save(new Role(null, "ROLE_MANAGER"));
			}

			utilisateurDepot.findByIdentifiant("admin").ifPresentOrElse(
				admin -> {
					admin.setMotDePasse(passwordEncoder.encode("admin123"));
					roleDepot.findByNom("ROLE_ADMIN").ifPresent(role -> {
						admin.setRoles(new HashSet<>(Collections.singletonList(role)));
					});
					utilisateurDepot.save(admin);
					System.out.println("Compte administrateur mis à jour : admin / admin123");
				},
				() -> {
					Utilisateur admin = new Utilisateur();
					admin.setIdentifiant("admin");
					admin.setMotDePasse(passwordEncoder.encode("admin123"));
					admin.setActif(true);
					roleDepot.findByNom("ROLE_ADMIN").ifPresent(role -> {
						admin.setRoles(new HashSet<>(Collections.singletonList(role)));
					});
					utilisateurDepot.save(admin);
					System.out.println("Compte administrateur créé : admin / admin123");
				}
			);
		};
	}
}
