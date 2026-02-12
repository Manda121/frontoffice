-- Supprimer la base si elle existe
DROP DATABASE IF EXISTS reservation;

-- Créer la base
CREATE DATABASE reservation;

-- Se connecter à la base
\c reservation;

-- Table hotel
CREATE TABLE hotel (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    ville VARCHAR(255) NOT NULL,
    adresse VARCHAR(255) NOT NULL
);

-- Table reservation
CREATE TABLE reservation (
    id SERIAL PRIMARY KEY,
    id_hotel INTEGER NOT NULL,
    nom VARCHAR(255) NOT NULL,
    date_heure_arrivee TIMESTAMP NOT NULL,
    CONSTRAINT fk_hotel
        FOREIGN KEY (id_hotel)
        REFERENCES hotel(id)
        ON DELETE CASCADE
);

-- Table voiture
CREATE TABLE voiture (
    id SERIAL PRIMARY KEY,
    marque VARCHAR(255) NOT NULL,
    nb_place INTEGER NOT NULL,
    type VARCHAR(255) NOT NULL,
    carburant CHAR(1) NOT NULL CHECK (carburant IN ('d', 'e', 'h'))
);

-- Table token
CREATE TABLE token (
    id SERIAL PRIMARY KEY,
    token VARCHAR(255) NOT NULL,
    date_heure_expiration TIMESTAMP NOT NULL
);
