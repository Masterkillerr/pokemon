-- Esquema de base de datos Pokemon para Supabase
-- Schema: pokemon

CREATE SCHEMA IF NOT EXISTS pokemon;

-- Tabla: pueblo
CREATE TABLE IF NOT EXISTS pokemon.pueblo (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    uuid VARCHAR(100) NOT NULL
);

-- Tabla: entrenador
CREATE TABLE IF NOT EXISTS pokemon.entrenador (
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
CREATE TABLE IF NOT EXISTS pokemon.tipo_pokemon (
    id SERIAL PRIMARY KEY,
    descripcion VARCHAR(100) NOT NULL,
    uuid VARCHAR(100) NOT NULL
);

-- Tabla: pokemon
CREATE TABLE IF NOT EXISTS pokemon.pokemon (
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
CREATE TABLE IF NOT EXISTS pokemon.pokemon_captura (
    pokemon_id INTEGER NOT NULL,
    entrenador_id INTEGER NOT NULL,
    PRIMARY KEY (pokemon_id, entrenador_id),
    FOREIGN KEY (pokemon_id) REFERENCES pokemon.pokemon(id),
    FOREIGN KEY (entrenador_id) REFERENCES pokemon.entrenador(id)
);

-- Seed data: tipos de pokemon
INSERT INTO pokemon.tipo_pokemon (id, descripcion, uuid) VALUES
(1, 'agua', '11111111-1111-1111-1111-111111111111'),
(2, 'planta', '22222222-2222-2222-2222-222222222222'),
(3, 'fuego', '33333333-3333-3333-3333-333333333333'),
(4, 'electrico', '44444444-4444-4444-4444-444444444444')
ON CONFLICT (id) DO NOTHING;

-- Seed data: pueblos
INSERT INTO pokemon.pueblo (id, nombre, uuid) VALUES
(1, 'Pueblo Paleta', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa'),
(2, 'Ciudad Celeste', 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb')
ON CONFLICT (id) DO NOTHING;

-- Seed data: entrenadores
INSERT INTO pokemon.entrenador (id, nombre, apellido, email, fecha_nacimiento, fecha_vinculacion, pueblo_id, uuid) VALUES
(1, 'Ash', 'Ketchum', 'ash@gmail.com', '2000-05-10', '2024-01-01', 1, 'cccccccc-cccc-cccc-cccc-cccccccccccc')
ON CONFLICT (id) DO NOTHING;

-- Seed data: pokemons
INSERT INTO pokemon.pokemon (id, nombre, descripcion, tipo_pokemon, fecha_descubrimiento, generacion, uuid) VALUES
(1, 'bulbasaur', 'planta', 2, '2024-01-03', 1, 'dddddddd-dddd-dddd-dddd-dddddddddddd'),
(2, 'squirtle', 'agua', 1, '2024-01-03', 1, 'eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee')
ON CONFLICT (id) DO NOTHING;
