package com.kenfi.gestionpersonnel.depot;

import com.kenfi.gestionpersonnel.modele.Departement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartementDepot extends JpaRepository<Departement, Long> {
}
