# MDD

MDD est une application web full-stack développée avec Spring Boot (backend) et Angular (frontend).

Elle permet aux utilisateurs de :

- S’inscrire et se connecter de manière sécurisée (JWT)
- Créer et consulter des articles
- Commenter des articles
- S’abonner et se désabonner à des thèmes (topics)
- Gérer leur profil utilisateur

---

# Technologies utilisées

## Backend

- Java
- Spring Boot
- Spring Security (JWT)
- Spring Data JPA
- Hibernate
- MySQL
- IntelliJ

## Frontend

- Angular (Standalone Components)
- Angular Material
- Reactive Forms
- RxJS
- TypeScript

---

# 🏗 Architecture

## Backend

Organisation en couches :

- controller → gestion des endpoints REST
- service → logique métier
- repository → accès base de données
- model → entités JPA
- dto → objets de transfert
- payload → requêtes et réponses
- mapper → transformation entité ↔ DTO
- security → configuration JWT

---

## Frontend

Structure :

- features
- services
- interfaces
- layout

---

# Installation

---

## Backend

### Prérequis

- Java 17.0.12
- IntelliJ
- MySQL

### Configuration

Dans le fichier application.properties :

spring.datasource.url=jdbc:mysql://localhost:3306/mdd_db  
spring.datasource.username=root  
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update  
spring.jpa.show-sql=true

jwt.secret=your_secret_key  
jwt.expiration=86400000

### Lancement

Dans le backend :

mvn clean install  
mvn spring-boot:run

Le serveur démarre sur :  
http://localhost:8080

---

## Frontend

### Prérequis

- Node v20.19.6
- Angular CLI 14.1.3
- Angular: 14.2.6

### Installation

Dans le dossier frontend :

npm install

### Configuration

Modifier src/environments/environment.ts :

apiUrl: 'http://localhost:8080'

### Lancement

ng serve

Application disponible sur :  
http://localhost:4200
