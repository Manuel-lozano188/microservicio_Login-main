-- V1__Creando_tabla_usuarios.sql
CREATE TABLE IF NOT EXISTS usuarios (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    rol VARCHAR(50) NOT NULL DEFAULT 'ROLE_USUARIO',
    PRIMARY KEY (id)
);

CREATE INDEX idx_usuario_email ON usuarios(email);