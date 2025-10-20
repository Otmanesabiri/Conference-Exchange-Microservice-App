# Spécification Fonctionnelle - Application de Gestion des Conférences

## Périmètres des Microservices

### Microservice Keynote
**Périmètre :** Gestion complète des intervenants (keynotes)
- Création, modification, suppression et consultation des keynotes
- Recherche des keynotes par critères (nom, fonction, etc.)
- Attribution de keynotes aux conférences
- Exposition d'API REST pour l'interopérabilité avec les autres services

**Responsabilités :**
- Maintenir l'intégrité des données des keynotes
- Fournir des endpoints pour la gestion des keynotes
- Communiquer avec le service de conférences via Open Feign

### Microservice Conference
**Périmètre :** Gestion complète des conférences et des reviews
- Création, modification, suppression et consultation des conférences
- Gestion des inscriptions aux conférences
- Gestion complète des reviews (ajout, modification, suppression)
- Calcul des scores moyens des conférences basé sur les reviews
- Communication avec le service Keynote pour obtenir les informations des intervenants

**Responsabilités :**
- Maintenir l'intégrité des données des conférences et des reviews
- Fournir des endpoints pour la gestion des conférences et des reviews
- Utiliser Open Feign pour communiquer avec le service Keynote
- Implémenter des circuit breakers avec Resilience4J

### Service Auth (Keycloak)
**Périmètre :** Gestion de l'authentification et de l'autorisation
- Authentification des utilisateurs (OAuth2/OIDC)
- Gestion des rôles et permissions
- Sécurisation des APIs des microservices
- Single Sign-On (SSO) pour l'application

**Responsabilités :**
- Fournir des tokens JWT pour l'authentification
- Gérer les sessions utilisateurs
- Configurer les clients et les realms
- Gérer les politiques d'accès

### Frontend (Angular)
**Périmètre :** Interface utilisateur de l'application
- Interfaces pour la gestion des keynotes
- Interfaces pour la gestion des conférences
- Système de notation et de reviews
- Tableaux de bord et statistiques
- Formulaires d'inscription aux conférences

**Responsabilités :**
- Fournir une expérience utilisateur intuitive
- Communiquer avec les microservices via la Gateway
- Gérer l'authentification et les sessions côté client
- Implémenter une interface responsive

## Schéma de Base de Données

### Base de Données Keynote-Service
**Entité: Keynote**
- `id` (Long) : Identifiant unique du keynote
- `nom` (String) : Nom de famille du keynote
- `prenom` (String) : Prénom du keynote
- `email` (String) : Adresse email du keynote
- `fonction` (String) : Fonction ou titre professionnel du keynote

### Base de Données Conference-Service
**Entité: Conference**
- `id` (Long) : Identifiant unique de la conférence
- `titre` (String) : Titre de la conférence
- `type` (Enum) : Type de conférence (ACADEMIQUE, COMMERCIALE)
- `date` (LocalDateTime) : Date et heure de la conférence
- `duree` (Integer) : Durée de la conférence en minutes
- `nombreInscrits` (Integer) : Nombre de personnes inscrites
- `score` (Double) : Score moyen de la conférence basé sur les reviews
- `keynoteId` (Long) : Référence à l'identifiant du keynote assigné (clé étrangère)

**Entité: Review**
- `id` (Long) : Identifiant unique de la review
- `date` (LocalDateTime) : Date de la review
- `texte` (String) : Contenu textuel de la review
- `note` (Integer) : Note attribuée (1 à 5 étoiles)
- `conferenceId` (Long) : Référence à la conférence concernée (clé étrangère)

**Relation: Conference - Review**
Une conférence peut avoir plusieurs reviews (relation One-to-Many)

**Relation: Keynote - Conference**
Un keynote peut être assigné à plusieurs conférences, et une conférence peut avoir un keynote assigné (relation Many-to-One)

## Services Techniques

### Gateway Service
- Basé sur Spring Cloud Gateway
- Point d'entrée unique pour toutes les requêtes
- Routage vers les microservices appropriés
- Intégration de la sécurité OAuth2/OIDC

### Discovery Service
- Basé sur Eureka Server ou Consul Discovery
- Enregistrement et découverte dynamique des services
- Equilibrage de charge

### Config Service
- Basé sur Spring Cloud Config ou Consul Config
- Configuration centralisée des microservices
- Gestion des propriétés par environnement

## Sécurité
- Authentification basée sur OAuth2 et OIDC avec Keycloak
- Protection des APIs des microservices
- Gestion des rôles (admin, utilisateur)
- Validation des tokens JWT

## Documentation
- Documentation des APIs REST avec OpenAPIDoc (Swagger)
- Documentation des endpoints disponibles pour chaque service

## Résilience
- Circuit breakers avec Resilience4J
- Gestion des timeouts et des retries
- Fallback pour les services indisponibles

## Déploiement
- Conteneurisation avec Docker
- Orchestration avec Docker Compose
- Configuration des dépendances entre services