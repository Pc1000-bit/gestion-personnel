package com.kenfi.gestionpersonnel.controleur;

import com.kenfi.gestionpersonnel.modele.Departement;
import com.kenfi.gestionpersonnel.service.ServiceDepartement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/departements")
public class ControleurDepartement {

    @Autowired
    private ServiceDepartement serviceDepartement;

    @GetMapping
    public String lister(Model modele) {
        modele.addAttribute("departements", serviceDepartement.listerTous());
        return "liste-departements";
    }

    @GetMapping("/ajouter")
    public String afficherFormulaireAjout(Model modele) {
        modele.addAttribute("departement", new Departement());
        return "formulaire-departement";
    }

    @GetMapping("/modifier/{id}")
    public String afficherFormulaireModification(@PathVariable Long id, Model modele) {
        Departement departement = serviceDepartement.trouverParId(id).orElseThrow();
        modele.addAttribute("departement", departement);
        return "formulaire-departement";
    }

    @PostMapping("/enregistrer")
    public String enregistrer(@ModelAttribute("departement") Departement departement, org.springframework.validation.BindingResult result) {
        // Vérification du doublon de nom
        java.util.Optional<Departement> deptExistant = serviceDepartement.trouverParNom(departement.getNom());
        if (deptExistant.isPresent() && (departement.getId() == null || !deptExistant.get().getId().equals(departement.getId()))) {
            result.rejectValue("nom", "error.departement", "Ce nom de département existe déjà.");
        }

        if (result.hasErrors()) {
            return "formulaire-departement";
        }

        serviceDepartement.enregistrer(departement);
        return "redirect:/admin/departements";
    }

    @GetMapping("/supprimer/{id}")
    public String supprimer(@PathVariable Long id) {
        serviceDepartement.supprimer(id);
        return "redirect:/admin/departements";
    }
}
