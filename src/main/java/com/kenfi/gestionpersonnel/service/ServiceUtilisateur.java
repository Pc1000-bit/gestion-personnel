package com.kenfi.gestionpersonnel.service;

import com.kenfi.gestionpersonnel.depot.UtilisateurDepot;
import com.kenfi.gestionpersonnel.modele.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceUtilisateur implements UserDetailsService {

    @Autowired
    private UtilisateurDepot utilisateurDepot;

    @Override
    public UserDetails loadUserByUsername(String identifiant) throws UsernameNotFoundException {
        System.out.println("Tentative de connexion pour : " + identifiant);
        
        Utilisateur utilisateur = utilisateurDepot.findByIdentifiant(identifiant)
                .orElseThrow(() -> {
                    System.out.println("Utilisateur " + identifiant + " non trouvé en BDD.");
                    return new UsernameNotFoundException("Utilisateur non trouvé");
                });

        if (utilisateur.getRoles() == null || utilisateur.getRoles().isEmpty()) {
            System.out.println("ATTENTION : L'utilisateur " + identifiant + " n'a aucun rôle !");
        }

        List<SimpleGrantedAuthority> authorities = utilisateur.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getNom()))
                .collect(Collectors.toList());

        System.out.println("Utilisateur trouvé : " + identifiant + " avec les rôles : " + authorities);

        return new User(
                utilisateur.getIdentifiant(),
                utilisateur.getMotDePasse(),
                utilisateur.isActif(),
                true, true, true,
                authorities
        );
    }
}
