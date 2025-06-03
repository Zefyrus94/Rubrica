CREATE DATABASE IF NOT EXISTS rubrica;
USE rubrica;
CREATE TABLE IF NOT EXISTS persona (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50),
    cognome VARCHAR(50),
    indirizzo VARCHAR(100),
    telefono VARCHAR(20),
    eta INT
);

CREATE TABLE IF NOT EXISTS utenti (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL
);
-- Dati di test
INSERT INTO utenti (username, password) VALUES ('admin', '1234');
