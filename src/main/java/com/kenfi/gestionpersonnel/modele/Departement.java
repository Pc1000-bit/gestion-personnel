package com.kenfi.gestionpersonnel.modele;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Departement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    private String lieu;

    private String nomResponsable;

    @OneToMany(mappedBy = "departement")
    private List<Employe> employes;

    public Departement() {}

    public Departement(Long id, String nom, String lieu, String nomResponsable, List<Employe> employes) {
        this.id = id;
        this.nom = nom;
        this.lieu = lieu;
        this.nomResponsable = nomResponsable;
        this.employes = employes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getNomResponsable() {
        return nomResponsable;
    }

    public void setNomResponsable(String nomResponsable) {
        this.nomResponsable = nomResponsable;
    }

    public List<Employe> getEmployes() {
        return employes;
    }

    public void setEmployes(List<Employe> employes) {
        this.employes = employes;
    }
}
