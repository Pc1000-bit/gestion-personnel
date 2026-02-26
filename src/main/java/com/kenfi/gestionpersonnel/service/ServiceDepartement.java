package com.kenfi.gestionpersonnel.service;

import com.kenfi.gestionpersonnel.depot.DepartementDepot;
import com.kenfi.gestionpersonnel.modele.Departement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceDepartement {

    @Autowired
    private DepartementDepot departementDepot;

    public List<Departement> listerTous() {
        return departementDepot.findAll();
    }

    public Optional<Departement> trouverParId(Long id) {
        return departementDepot.findById(id);
    }

    public Departement enregistrer(Departement departement) {
        return departementDepot.save(departement);
    }

    public void supprimer(Long id) {
        departementDepot.deleteById(id);
    }

    public long compterTotal() {
        return departementDepot.count();
    }
}
