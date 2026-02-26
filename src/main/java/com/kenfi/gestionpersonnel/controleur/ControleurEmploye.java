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
        if (result.hasErrors()) {
            System.out.println("Erreurs lors de l'enregistrement de l'employé : " + result.getAllErrors());
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
