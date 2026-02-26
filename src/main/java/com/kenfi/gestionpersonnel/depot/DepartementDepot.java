package com.kenfi.gestionpersonnel.depot;

import com.kenfi.gestionpersonnel.modele.Departement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartementDepot extends JpaRepository<Departement, Long> {
    Optional<Departement> findByNom(String nom);
}
