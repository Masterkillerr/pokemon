-- Esquema de base de datos Pokemon
-- Para parcial

CREATE SCHEMA IF NOT EXISTS pokemon;

-- Tabla: pueblo
CREATE TABLE pokemon.pueblo (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    uuid VARCHAR(100) NOT NULL
);

-- Tabla: entrenador
CREATE TABLE pokemon.entrenador (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    fecha_nacimiento DATE NOT NULL,
    fecha_vinculacion DATE NOT NULL,
    pueblo_id INTEGER NOT NULL,
    uuid VARCHAR(100) NOT NULL,
    FOREIGN KEY (pueblo_id) REFERENCES pokemon.pueblo(id)
);

-- Tabla: tipo_pokemon
CREATE TABLE pokemon.tipo_pokemon (
    id SERIAL PRIMARY KEY,
    descripcion VARCHAR(100) NOT NULL,
    uuid VARCHAR(100) NOT NULL
);

-- Tabla: pokemon
CREATE TABLE pokemon.pokemon (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT NOT NULL,
    tipo_pokemon INTEGER NOT NULL,
    fecha_descubrimiento DATE NOT NULL,
    generacion INTEGER NOT NULL,
    uuid VARCHAR(100) NOT NULL,
    FOREIGN KEY (tipo_pokemon) REFERENCES pokemon.tipo_pokemon(id)
);

-- Tabla: pokemon_captura (relacion entre pokemon y entrenador)
CREATE TABLE pokemon.pokemon_captura (
    pokemon_id INTEGER NOT NULL,
    entrenador_id INTEGER NOT NULL,
    PRIMARY KEY (pokemon_id, entrenador_id),
    FOREIGN KEY (pokemon_id) REFERENCES pokemon.pokemon(id),
    FOREIGN KEY (entrenador_id) REFERENCES pokemon.entrenador(id)
);
