CREATE DATABASE IF NOT EXISTS academic_attendance;

USE academic_attendance;

CREATE TABLE IF NOT EXISTS Disciplina (
    codigo VARCHAR(255) PRIMARY KEY,
    nome VARCHAR(255),
    cargaHoraria INT
);

CREATE TABLE IF NOT EXISTS Frequencia (
    frequencia_id SERIAL PRIMARY KEY,
    data DATE,
    presencaAusencia BOOLEAN,
    codigo VARCHAR(255),
    int faltas,
    FOREIGN KEY (codigo) REFERENCES Disciplina (codigo)
);

CREATE TABLE IF NOT EXISTS Autoavaliacao (
    autoavaliacao_id SERIAL PRIMARY KEY,
    autoNota FLOAT,
    comentario TEXT,
    frequencia_id BIGINT UNSIGNED,
    FOREIGN KEY (frequencia_id) REFERENCES Frequencia (frequencia_id)
);
