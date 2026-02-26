package com.kenfi.gestionpersonnel.controleur;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ControleurSecurite {

    @GetMapping("/connexion")
    public String connexion() {
        return "connexion";
    }
}
