package com.kenfi.gestionpersonnel.modele;

import jakarta.persistence.*;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Employe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nomComplet;

    @Column(nullable = false, unique = true)
    private String email;

    private String telephone;

    private String poste;

    private Double salaire;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateEmbauche;

    @ManyToOne
    @JoinColumn(name = "departement_id")
    private Departement departement;

    public Employe() {}

    public Employe(Long id, String nomComplet, String email, String telephone, String poste, Double salaire, LocalDate dateEmbauche, Departement departement) {
        this.id = id;
        this.nomComplet = nomComplet;
        this.email = email;
        this.telephone = telephone;
        this.poste = poste;
        this.salaire = salaire;
        this.dateEmbauche = dateEmbauche;
        this.departement = departement;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomComplet() {
        return nomComplet;
    }

    public void setNomComplet(String nomComplet) {
        this.nomComplet = nomComplet;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPoste() {
        return poste;
    }

    public void setPoste(String poste) {
        this.poste = poste;
    }

    public Double getSalaire() {
        return salaire;
    }

    public void setSalaire(Double salaire) {
        this.salaire = salaire;
    }

    public LocalDate getDateEmbauche() {
        return dateEmbauche;
    }

    public void setDateEmbauche(LocalDate dateEmbauche) {
        this.dateEmbauche = dateEmbauche;
    }

    public Departement getDepartement() {
        return departement;
    }

    public void setDepartement(Departement departement) {
        this.departement = departement;
    }
}
