package com.kenfi.gestionpersonnel.controleur;

import com.kenfi.gestionpersonnel.service.ServiceEmploye;
import com.kenfi.gestionpersonnel.service.ServiceDepartement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ControleurPrincipal {

    @Autowired
    private ServiceEmploye serviceEmploye;

    @Autowired
    private ServiceDepartement serviceDepartement;

    @GetMapping({"/", "/tableau-de-bord"})
    public String tableauDeBord(Model modele) {
        modele.addAttribute("totalEmployes", serviceEmploye.compterTotal());
        modele.addAttribute("masseSalariale", serviceEmploye.calculerMasseSalariale());
        modele.addAttribute("totalDepartements", serviceDepartement.compterTotal());
        return "tableau-de-bord";
    }
}
