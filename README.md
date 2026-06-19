# Pokemon API

API REST para el sistema de gestion de Pokemon, construida con **Spring Boot 3.4.3**.

## Tecnologias

| Capa | Tecnologia |
|------|-----------|
| Framework | Spring Boot 3.4.3 |
| Lenguaje | Java 21 |
| Base de datos | PostgreSQL (Supabase) / H2 (desarrollo) |
| ORM | Spring Data JPA / Hibernate |
| Documentacion API | SpringDoc OpenAPI (Swagger UI) |
| Build | Maven |
| Utilidades | Lombok, HikariCP |

## Requisitos

- **Java 21** o superior
- **Maven 3.8+**

## Como ejecutar

### Opcion 1: Desarrollo (H2, sin configuracion)

Recomendado para pruebas rapidas. Usa una base de datos H2 en memoria.

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

La API estara disponible en `http://localhost:8080`.

### Opcion 2: Produccion (Supabase PostgreSQL)

Requiere una cuenta de Supabase con las siguientes variables de entorno:

```bash
cp .env.example .env
# Editar .env con tus credenciales de Supabase
mvn spring-boot:run
```

## Documentacion de la API (Swagger)

Una vez ejecutando la aplicacion, abre:

👉 [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

### Endpoints disponibles

#### Pokemon Controller

| Metodo | Ruta | Descripcion |
|--------|------|-------------|
| `GET` | `/pokemons/{tipo}` | Obtener pokemons por tipo (agua, fuego, planta, electrico, psiquico) |
| `POST` | `/pokemons` | Crear un nuevo pokemon |

#### Entrenador Controller

| Metodo | Ruta | Descripcion |
|--------|------|-------------|
| `POST` | `/entrenador/login` | Iniciar sesion con email |
| `GET` | `/entrenador/{uuid}/pokemons` | Obtener pokemons de un entrenador |
| `POST` | `/entrenador/{uuid}/pokemons/{pokemonUuid}` | Capturar un pokemon |

### Ejemplos de uso (curl)

```bash
# Obtener pokemons de tipo agua
curl http://localhost:8080/pokemons/agua

# Iniciar sesion como Ash
curl -X POST http://localhost:8080/entrenador/login \
  -H "Content-Type: application/json" \
  -d '{"email":"ash@gmail.com"}'

# Crear un nuevo pokemon
curl -X POST http://localhost:8080/pokemons \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Pikachu","descripcion":"Pokemon electrico","fechaDescubrimiento":"1996-02-27","tipoPokemonId":4,"generacion":1}'

# Ver pokemons de Ash (reemplazar UUID)
curl http://localhost:8080/entrenador/aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaaa/pokemons

# Capturar un pokemon (reemplazar UUIDs)
curl -X POST http://localhost:8080/entrenador/aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaaa/pokemons/ccccccc1-cccc-cccc-cccc-ccccccccccc1
```

## Base de datos

### Produccion (Supabase PostgreSQL)

La aplicacion usa PostgreSQL en produccion. Las tablas y esquemas se crean automaticamente con `ddl-auto=update`.

### Desarrollo (H2 in-memory)

Con el perfil `dev`, se usa una base de datos H2 en memoria que se crea y destruye con cada ejecucion. Los datos de prueba se cargan automaticamente.

Consola H2 disponible en: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)

## Configuracion

### Variables de entorno (produccion)

| Variable | Descripcion |
|----------|-------------|
| `DB_URL` | URL de conexion a PostgreSQL |
| `DB_USERNAME` | Usuario de la base de datos |
| `DB_PASSWORD` | Contrasena de la base de datos |
> **Nota:** Las credenciales en `.env.example` están intencionalmente expuestas para propósitos de evaluación académica. La base de datos contiene únicamente datos de prueba y no almacena información sensible.


Copia `.env.example` a `.env` y completa los valores.

## Compilacion

```bash
# Compilar el proyecto
mvn clean compile

# Compilar y ejecutar tests
mvn clean test

# Empaquetar JAR
mvn clean package -DskipTests
java -jar target/backend-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev
```
