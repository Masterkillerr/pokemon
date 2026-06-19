# Proyecto Pokemon - API REST con Spring Boot

## Arquitectura
- Arquitectura similar a **Gestion_Ganadera** (proyecto existente del usuario)
- **Spring Boot** con Java
- **Swagger** obligatorio (para documentación de API)
- **CORS** habilitado
- **Commits por cada servicio** terminado (uno por endpoint)
- Usar **GitHub** correctamente

## Modelo de Datos

### Tabla: pokemon.pueblo
| Campo | Tipo | Constraints |
|-------|------|-------------|
| id | SERIAL | PRIMARY KEY |
| nombre | VARCHAR(100) | NOT NULL |
| uuid | VARCHAR(100) | NOT NULL |

### Tabla: pokemon.entrenador
| Campo | Tipo | Constraints |
|-------|------|-------------|
| id | SERIAL | PRIMARY KEY |
| nombre | VARCHAR(50) | NOT NULL |
| apellido | VARCHAR(50) | NOT NULL |
| email | VARCHAR(100) | NOT NULL, UNIQUE |
| fecha_nacimiento | DATE | NOT NULL |
| fecha_vinculacion | DATE | NOT NULL |
| pueblo_id | INTEGER | NOT NULL, FK -> pueblo(id) |
| uuid | VARCHAR(100) | NOT NULL |

> **Nota:** El campo `email` se agregó para soportar el endpoint POST /entrenador/login/

### Tabla: pokemon.tipo_pokemon
| Campo | Tipo | Constraints |
|-------|------|-------------|
| id | SERIAL | PRIMARY KEY |
| descripcion | VARCHAR(100) | NOT NULL |
| uuid | VARCHAR(100) | NOT NULL |

### Tabla: pokemon.pokemon
| Campo | Tipo | Constraints |
|-------|------|-------------|
| id | SERIAL | PRIMARY KEY |
| nombre | VARCHAR(100) | NOT NULL |
| descripcion | TEXT | NOT NULL |
| tipo_pokemon | INTEGER | FK -> tipo_pokemon(id) |
| fecha_descubrimiento | DATE | NOT NULL |
| generacion | INTEGER | NOT NULL |
| uuid | VARCHAR(100) | NOT NULL |

### Tabla: pokemon.pokemon_captura (relación Pokemon-Entrenador)
| Campo | Tipo | Constraints |
|-------|------|-------------|
| pokemon_id | INTEGER | FK -> pokemon(id), PK compuesto |
| entrenador_id | INTEGER | FK -> entrenador(id), PK compuesto |

## Relaciones
- pokemon.pokemon → pokemon_captura (1:N)
- pokemon.entrenador → pokemon_captura (1:N)
- pokemon.pueblo → pokemon.entrenador (1:N)
- pokemon.tipo_pokemon → pokemon.pokemon (1:N)

## Servicios (Endpoints) - 5 Servicios

### Servicio 1: POST /entrenador/login/
- **Descripción:** Obtener UUID de un usuario por su email
- **Request:**
  ```json
  { "email": "ash@gmail.com" }
  ```
- **Response:**
  ```json
  { "uuid": "a1b2c3d4-e5f6-7890-abcd-ef1234567890" }
  ```

### Servicio 2: GET /pokemons/{tipo}
- **Descripción:** Listar pokemons de un tipo registrado en el sistema
- **Request:** `/pokemons/agua`
- **Response:**
  ```json
  [
    {
      "id": 2,
      "nombre": "squirtle",
      "descripcion": "tortuga small",
      "tipo_pokemon": {
        "id": 1,
        "descripcion": "agua",
        "uuid": "123432432"
      },
      "fecha_descubrimiento": "2024-01-03",
      "generacion": 1,
      "uuid": "b2c3d4e5-f6a7-8901-bcde-f12345678901"
    }
  ]
  ```

### Servicio 3: POST /pokemons
- **Descripción:** Registrar un pokemon nuevo en el sistema
- **Request:**
  ```json
  {
    "nombre": "programador",
    "descripcion": "desarrollador full",
    "fecha_descubrimiento": "2024-01-03",
    "tipo_pokemon_id": 1
  }
  ```
> **Nota:** Los campos `generacion` (por defecto 1) y `uuid` se asignan automáticamente por el sistema.
- **Response:**
  ```json
  {
    "id": 1,
    "nombre": "programador",
    "descripcion": "desarrollador full",
    "tipo_pokemon": {
      "id": 1,
      "descripcion": "agua",
      "uuid": "123432432"
    },
    "fecha_descubrimiento": "2024-01-03",
    "generacion": 1,
    "uuid": "a1b2c3d4-e5f6-7890-abcd-ef1234567890"
  }
  ```

### Servicio 4: GET /entrenador/{uuid}/pokemons
- **Descripción:** Listar pokemons de un entrenador
- **Request:** `/entrenador/f33242443244.../pokemons`
- **Response:** Lista completa de pokemons del entrenador
  ```json
  [
    {
      "id": 1,
      "nombre": "bulbasaur",
      "descripcion": "planta",
      "tipo_pokemon": {
        "id": 2,
        "descripcion": "planta",
        "uuid": "abc123"
      },
      "fecha_descubrimiento": "2024-01-03",
      "generacion": 1,
      "uuid": "a1b2c3d4-e5f6-7890-abcd-ef1234567890"
    },
    {
      "id": 4,
      "nombre": "charmander",
      "descripcion": "lagartija de fuego",
      "tipo_pokemon": {
        "id": 3,
        "descripcion": "fuego",
        "uuid": "def456"
      },
      "fecha_descubrimiento": "2024-02-10",
      "generacion": 1,
      "uuid": "b2c3d4e5-f6a7-8901-bcde-f12345678901"
    }
  ]
  ```

### Servicio 5: POST /entrenador/{uuid}/pokemons/{pokemonUuid}
- **Descripción:** Agregar un pokemon al listado de pokemons del entrenador
- **Request:** `/entrenador/{uuid}/pokemons/{pokemonUuid}`
  - El pokemonUuid corresponde a un pokemon que todavía no tenga el entrenador
- **Response Exitosa:** Lista completa de pokemons del entrenador actualizada
  ```json
  [
    {
      "id": 1,
      "nombre": "bulbasaur",
      "descripcion": "planta",
      "tipo_pokemon": {
        "id": 2,
        "descripcion": "planta",
        "uuid": "abc123"
      },
      "fecha_descubrimiento": "2024-01-03",
      "generacion": 1,
      "uuid": "a1b2c3d4-e5f6-7890-abcd-ef1234567890"
    },
    {
      "id": 5,
      "nombre": "pikachu",
      "descripcion": "raton electrico",
      "tipo_pokemon": {
        "id": 4,
        "descripcion": "electrico",
        "uuid": "ghi789"
      },
      "fecha_descubrimiento": "2024-03-15",
      "generacion": 1,
      "uuid": "c3d4e5f6-a7b8-9012-cdef-123456789012"
    }
  ]
  ```
- **Response Error (pokemon ya registrado):**
  ```json
  {
    "error": true,
    "message": "pokemon ya esta registrado al entrenador"
  }
  ```

## Checklist de Implementación
- [ ] Entidades JPA (Pueblo, Entrenador, TipoPokemon, Pokemon, PokemonCaptura)
- [ ] Repositorios JPA para cada entidad
- [ ] Configuración de Swagger
- [ ] Configuración de CORS
- [ ] Servicio 1: POST /entrenador/login/
- [ ] Servicio 2: GET /pokemons/{tipo}
- [ ] Servicio 3: POST /pokemons
- [ ] Servicio 4: GET /entrenador/{uuid}/pokemons
- [ ] Servicio 5: POST /entrenador/{uuid}/pokemons/{pokemonUuid}
- [ ] Commit por cada servicio terminado
- [ ] Push a GitHub
