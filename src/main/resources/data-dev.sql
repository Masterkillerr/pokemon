-- Seed data for H2 (dev profile)
-- Tables are created fresh (create-drop), so no ON CONFLICT needed

INSERT INTO pokemon.pueblo (nombre, uuid) VALUES ('Pueblo Paleta', '11111111-1111-1111-1111-111111111111');
INSERT INTO pokemon.pueblo (nombre, uuid) VALUES ('Ciudad Celeste', '22222222-2222-2222-2222-222222222222');
INSERT INTO pokemon.pueblo (nombre, uuid) VALUES ('Pueblo Lavanda', '33333333-3333-3333-3333-333333333333');

INSERT INTO pokemon.tipo_pokemon (descripcion, uuid) VALUES ('agua', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa');
INSERT INTO pokemon.tipo_pokemon (descripcion, uuid) VALUES ('fuego', 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb');
INSERT INTO pokemon.tipo_pokemon (descripcion, uuid) VALUES ('planta', 'cccccccc-cccc-cccc-cccc-cccccccccccc');
INSERT INTO pokemon.tipo_pokemon (descripcion, uuid) VALUES ('electrico', 'dddddddd-dddd-dddd-dddd-dddddddddddd');
INSERT INTO pokemon.tipo_pokemon (descripcion, uuid) VALUES ('psiquico', 'eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee');

INSERT INTO pokemon.entrenador (nombre, apellido, email, fecha_nacimiento, fecha_vinculacion, pueblo_id, uuid)
VALUES ('Ash', 'Ketchum', 'ash@gmail.com', '1987-05-22', '2024-01-15', 1, 'aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaaa');

INSERT INTO pokemon.entrenador (nombre, apellido, email, fecha_nacimiento, fecha_vinculacion, pueblo_id, uuid)
VALUES ('Misty', 'Water', 'misty@gmail.com', '1989-07-18', '2024-02-01', 2, 'bbbbbbb2-bbbb-bbbb-bbbb-bbbbbbbbbbbb');

INSERT INTO pokemon.pokemon (nombre, descripcion, tipo_pokemon, fecha_descubrimiento, generacion, uuid)
VALUES ('Squirtle', 'pokemon de agua', 1, '1996-02-27', 1, 'ccccccc1-cccc-cccc-cccc-ccccccccccc1');

INSERT INTO pokemon.pokemon (nombre, descripcion, tipo_pokemon, fecha_descubrimiento, generacion, uuid)
VALUES ('Charmander', 'pokemon de fuego', 2, '1996-02-27', 1, 'ccccccc2-cccc-cccc-cccc-ccccccccccc2');

INSERT INTO pokemon.pokemon (nombre, descripcion, tipo_pokemon, fecha_descubrimiento, generacion, uuid)
VALUES ('Bulbasaur', 'pokemon de planta', 3, '1996-02-27', 1, 'ccccccc3-cccc-cccc-cccc-ccccccccccc3');

INSERT INTO pokemon.pokemon (nombre, descripcion, tipo_pokemon, fecha_descubrimiento, generacion, uuid)
VALUES ('Pikachu', 'pokemon electrico', 4, '1996-02-27', 1, 'ccccccc4-cccc-cccc-cccc-ccccccccccc4');

INSERT INTO pokemon.pokemon_captura (pokemon_id, entrenador_id) VALUES (1, 1);
INSERT INTO pokemon.pokemon_captura (pokemon_id, entrenador_id) VALUES (4, 1);
