package com.kenfi.gestionpersonnel.service;

import com.kenfi.gestionpersonnel.depot.EmployeDepot;
import com.kenfi.gestionpersonnel.modele.Departement;
import com.kenfi.gestionpersonnel.modele.Employe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceEmploye {

    @Autowired
    private EmployeDepot employeDepot;

    public List<Employe> listerTous() {
        return employeDepot.findAll();
    }

    public List<Employe> rechercherParNom(String nom) {
        return employeDepot.findByNomCompletContainingIgnoreCase(nom);
    }

    public List<Employe> listerParDepartement(Departement departement) {
        return employeDepot.findByDepartement(departement);
    }

    public Optional<Employe> trouverParId(Long id) {
        return employeDepot.findById(id);
    }

    public Employe enregistrer(Employe employe) {
        return employeDepot.save(employe);
    }

    public void supprimer(Long id) {
        employeDepot.deleteById(id);
    }

    public long compterTotal() {
        return employeDepot.count();
    }

    public double calculerMasseSalariale() {
        return employeDepot.findAll().stream()
                .mapToDouble(e -> e.getSalaire() != null ? e.getSalaire() : 0.0)
                .sum();
    }
}
