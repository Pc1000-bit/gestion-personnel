-- Création de la base de données
CREATE DATABASE IF NOT EXISTS gestion_personnel CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE gestion_personnel;

-- Table des Départements
CREATE TABLE IF NOT EXISTS departement (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    lieu VARCHAR(100),
    nom_responsable VARCHAR(100)
) ENGINE=InnoDB;

-- Table des Employés
CREATE TABLE IF NOT EXISTS employe (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom_complet VARCHAR(150) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    telephone VARCHAR(20),
    poste VARCHAR(100),
    salaire DOUBLE,
    date_embauche DATE,
    departement_id BIGINT,
    CONSTRAINT fk_departement FOREIGN KEY (departement_id) REFERENCES departement(id) ON DELETE SET NULL
) ENGINE=InnoDB;

-- Table des Rôles
CREATE TABLE IF NOT EXISTS role (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(50) NOT NULL UNIQUE
) ENGINE=InnoDB;

-- Table des Utilisateurs
CREATE TABLE IF NOT EXISTS utilisateur (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    identifiant VARCHAR(50) NOT NULL UNIQUE,
    mot_de_passe VARCHAR(255) NOT NULL,
    actif BOOLEAN DEFAULT TRUE
) ENGINE=InnoDB;

-- Table de jointure Utilisateurs-Rôles
CREATE TABLE IF NOT EXISTS utilisateur_roles (
    utilisateur_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (utilisateur_id, role_id),
    CONSTRAINT fk_utilisateur FOREIGN KEY (utilisateur_id) REFERENCES utilisateur(id) ON DELETE CASCADE,
    CONSTRAINT fk_role FOREIGN KEY (role_id) REFERENCES role(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- Insertion des rôles par défaut
INSERT IGNORE INTO role (nom) VALUES ('ROLE_ADMIN'), ('ROLE_RH'), ('ROLE_MANAGER');

-- Insertion d'un admin par défaut (mot de passe: admin123 haché avec BCrypt)
-- Note: Le mot de passe haché peut varier, voici un exemple pour 'admin123'
INSERT IGNORE INTO utilisateur (identifiant, mot_de_passe, actif) 
VALUES ('admin', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.TVuHOn2', TRUE);

INSERT IGNORE INTO utilisateur_roles (utilisateur_id, role_id) 
SELECT u.id, r.id FROM utilisateur u, role r 
WHERE u.identifiant = 'admin' AND r.nom = 'ROLE_ADMIN';
