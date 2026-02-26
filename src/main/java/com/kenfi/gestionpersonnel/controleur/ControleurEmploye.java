package com.kenfi.gestionpersonnel.controleur;

import com.kenfi.gestionpersonnel.modele.Employe;
import com.kenfi.gestionpersonnel.modele.Departement;
import com.kenfi.gestionpersonnel.service.ServiceDepartement;
import com.kenfi.gestionpersonnel.service.ServiceEmploye;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/rh/employes")
public class ControleurEmploye {

    @Autowired
    private ServiceEmploye serviceEmploye;

    @Autowired
    private ServiceDepartement serviceDepartement;

    @GetMapping
    public String lister(Model modele, @RequestParam(value = "nom", required = false) String nom) {
        if (nom != null && !nom.isEmpty()) {
            modele.addAttribute("employes", serviceEmploye.rechercherParNom(nom));
        } else {
            modele.addAttribute("employes", serviceEmploye.listerTous());
        }
        return "liste-employes";
    }

    @GetMapping("/ajouter")
    public String afficherFormulaireAjout(Model modele) {
        Employe employe = new Employe();
        employe.setDepartement(new Departement());
        modele.addAttribute("employe", employe);
        modele.addAttribute("departements", serviceDepartement.listerTous());
        return "formulaire-employe";
    }

    @PostMapping("/enregistrer")
    public String enregistrer(@ModelAttribute("employe") Employe employe, org.springframework.validation.BindingResult result, Model modele) {
        // 0. Validation des CHAMPS OBLIGATOIRES
        if (employe.getNomComplet() == null || employe.getNomComplet().trim().isEmpty()) {
            result.rejectValue("nomComplet", "error.employe", "Le nom complet est obligatoire.");
        }
        if (employe.getEmail() == null || employe.getEmail().trim().isEmpty()) {
            result.rejectValue("email", "error.employe", "L'adresse email est obligatoire.");
        }
        if (employe.getPoste() == null || employe.getPoste().trim().isEmpty()) {
            result.rejectValue("poste", "error.employe", "Le poste est obligatoire.");
        }
        if (employe.getDateEmbauche() == null) {
            result.rejectValue("dateEmbauche", "error.employe", "La date d'embauche est obligatoire.");
        }
        if (employe.getDepartement() == null || employe.getDepartement().getId() == null) {
            result.rejectValue("departement", "error.employe", "Le département est obligatoire.");
        }

        // 1. Validation du NOM (Unique)
        if (employe.getNomComplet() != null && !employe.getNomComplet().trim().isEmpty()) {
            java.util.Optional<Employe> nomDoublon = serviceEmploye.trouverParNomExact(employe.getNomComplet());
            if (nomDoublon.isPresent() && (employe.getId() == null || !nomDoublon.get().getId().equals(employe.getId()))) {
                result.rejectValue("nomComplet", "error.employe", "Ce nom complet est déjà utilisé.");
            }
        }

        // 2. Validation de l'EMAIL (Doublon et format @)
        if (employe.getEmail() != null && !employe.getEmail().contains("@")) {
            result.rejectValue("email", "error.employe", "L'email doit contenir un caractère '@'.");
        } else {
            java.util.Optional<Employe> emailDoublon = serviceEmploye.trouverParEmail(employe.getEmail());
            if (emailDoublon.isPresent() && (employe.getId() == null || !emailDoublon.get().getId().equals(employe.getId()))) {
                result.rejectValue("email", "error.employe", "Cet email est déjà utilisé.");
            }
        }

        // 3. Validation du SALAIRE (Minimum SMIG et Entier) - OPTIONNEL
        if (employe.getSalaire() != null) {
            if (employe.getSalaire() < 150000) {
                result.rejectValue("salaire", "error.employe", "Le SMIG au GABON c'est 150 000 FCFA, rentre une valeur supp ou égale à 150 000 FCFA.");
            } else if (employe.getSalaire() % 1 != 0) {
                result.rejectValue("salaire", "error.employe", "Le salaire doit être un nombre entier (pas de virgules).");
            }
        }

        // 4. Validation du TÉLÉPHONE (Débuts et longueur) - OPTIONNEL
        String tel = employe.getTelephone();
        if (tel != null && !tel.trim().isEmpty()) {
            boolean prefixeValide = tel.startsWith("062") || tel.startsWith("074") || 
                                    tel.startsWith("066") || tel.startsWith("077") || tel.startsWith("076");
            if (!prefixeValide) {
                result.rejectValue("telephone", "error.employe", "Le numéro doit commencer par 062, 074, 066, 077 ou 076.");
            } else if (tel.length() != 9) {
                result.rejectValue("telephone", "error.employe", "Le numéro de téléphone doit contenir exactement 9 chiffres.");
            }
        }

        // 5. Validation du POSTE (Pas de caractères spéciaux)
        String poste = employe.getPoste();
        if (poste != null && !poste.isEmpty()) {
            // Autorise lettres, chiffres et espaces seulement
            if (!poste.matches("^[a-zA-Z0-9 àâäéèêëîïôöùûüçÀÂÄÉÈÊËÎÏÔÖÙÛÜÇ]*$")) {
                result.rejectValue("poste", "error.employe", "Le poste ne doit pas contenir de caractères spéciaux (uniquement lettres, chiffres et espaces).");
            }
        }

        if (result.hasErrors()) {
            modele.addAttribute("departements", serviceDepartement.listerTous());
            return "formulaire-employe";
        }
        
        System.out.println("Enregistrement de l'employé : " + employe.getNomComplet());
        
        // Si aucun département n'est sélectionné, on met l'objet à null
        if (employe.getDepartement() != null && employe.getDepartement().getId() == null) {
            employe.setDepartement(null);
        }
        
        serviceEmploye.enregistrer(employe);
        return "redirect:/rh/employes";
    }

    @GetMapping("/modifier/{id}")
    public String afficherFormulaireModification(@PathVariable Long id, Model modele) {
        Employe employe = serviceEmploye.trouverParId(id).orElseThrow();
        if (employe.getDepartement() == null) {
            employe.setDepartement(new Departement());
        }
        modele.addAttribute("employe", employe);
        modele.addAttribute("departements", serviceDepartement.listerTous());
        return "formulaire-employe";
    }

    @GetMapping("/supprimer/{id}")
    public String supprimer(@PathVariable Long id) {
        serviceEmploye.supprimer(id);
        return "redirect:/rh/employes";
    }
}
