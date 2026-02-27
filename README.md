# 🏢 Système de Gestion du Personnel (SGP)

Un outil moderne, léger et sécurisé conçu pour centraliser et automatiser la gestion des ressources humaines d'une entreprise. Ce projet a été développé dans le cadre d'un module de formation en Licence 3 Informatique.

## 🚀 Fonctionnalités Clés

- **Tableau de Bord Dynamique** : Visualisation en temps réel du nombre total d'employés et de la masse salariale globale (en FCFA).
- **Gestion Complète (CRUD)** : Création, consultation, modification et suppression des employés et des départements.
- **Recherche Intelligente** : Filtrage rapide des employés par nom.
- **Sécurité Intégrée** : Authentification des utilisateurs et protection des accès via un système de connexion sécurisé.
- **Expérience Utilisateur (UX)** : Interface réactive avec indicateur visuel de chargement (Spinner) lors de l'authentification.

## ⚖️ Rigueur Métier & Conformité (Spécificités Gabon)

L'application intègre des contrôles stricts pour assurer l'intégrité des données et le respect de la législation :
- **Respect du Droit du Travail** : Blocage automatique de tout salaire inférieur au **SMIG en vigueur au Gabon (150 000 FCFA)**.
- **Vérification de l'Unicité** : Interdiction des doublons sur les adresses emails et les noms de départements.
- **Validation du Téléphone** : Formatage obligatoire aux normes locales (commençant par 062, 074, 066, 077, 076) avec une longueur de 9 chiffres.
- **Protection des données** : Validation multi-couche (Frontend HTML5 + Backend Java) pour une sécurité maximale.

## 🛠️ Stack Technique

- **Backend** : Java 17+, Spring Boot 3
- **Persistance** : Spring Data JPA
- **Base de données** : MySQL
- **Frontend** : Thymeleaf & Vanilla CSS (Design épuré et professionnel)
- **Sécurité** : Spring Security

## 💻 Installation et Lancement

### Prérequis
- Java JDK 17 ou supérieur
- MySQL
- Maven (inclus via le wrapper)

### Installation
1. Clonez le dépôt :
   ```bash
   git clone https://github.com/Pc1000-bit/gestion-personnel.git
   ```
2. Importez la base de données :
   Exécutez le fichier `bdd_initiale.sql` dans votre instance MySQL.
3. Configurez l'accès à la base de données dans `src/main/resources/application.properties`.

### Lancement
Double-cliquez simplement sur le fichier :
`LANCER_APPLICATION.bat`
*(Le script détecte automatiquement le démarrage du serveur).*

---
*Développé par Kenfi - 2026*
