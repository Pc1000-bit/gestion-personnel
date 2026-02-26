package com.kenfi.gestionpersonnel.depot;

import com.kenfi.gestionpersonnel.modele.Departement;
import com.kenfi.gestionpersonnel.modele.Employe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeDepot extends JpaRepository<Employe, Long> {
    List<Employe> findByDepartement(Departement departement);
    List<Employe> findByNomCompletContainingIgnoreCase(String nom);
    java.util.Optional<Employe> findByNomCompletIgnoreCase(String nomComplet);
    java.util.Optional<Employe> findByEmail(String email);
}
