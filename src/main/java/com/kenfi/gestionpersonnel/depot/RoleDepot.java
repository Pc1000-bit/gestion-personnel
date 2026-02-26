package com.kenfi.gestionpersonnel.depot;

import com.kenfi.gestionpersonnel.modele.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleDepot extends JpaRepository<Role, Long> {
    Optional<Role> findByNom(String nom);
}
