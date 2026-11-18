# GlobalHands API

Backend inicial para GlobalHands, desarrollado con Java 21, Spring Boot, Maven y MySQL 8. Los nombres de las clases y de la API están en inglés; el mapeo de JPA conecta esas clases con las tablas y columnas en español de `globalhands.sql`.

## Requisitos

- Java 21
- Maven
- MySQL 8

## Preparar la base de datos

Desde el cliente MySQL, ejecutar el archivo `../globalhands.sql`. Crea la base `globalhands`, sus tablas y unos registros de ejemplo para probar las consultas. El script está pensado para una base de datos vacía; no borres una base existente sin guardar antes sus datos.

El backend usa `ddl-auto: validate`: comprueba que las tablas coincidan con el SQL y no modifica el esquema automáticamente.

## Configuración y arranque

Por defecto, Spring se conecta a `jdbc:mysql://localhost:3306/globalhands` con usuario `root` y contraseña vacía. Si tu instalación usa otros datos, configura estas variables antes de arrancar:

```text
DB_URL=jdbc:mysql://localhost:3306/globalhands
DB_USERNAME=root
DB_PASSWORD=tu_contraseña
JWT_SECRET=una_clave_base64_de_al_menos_32_bytes
FRONTEND_URL=http://localhost:5173
```

En la carpeta del proyecto:

```bash
./mvnw spring-boot:run
```

En Windows:

```text
mvnw.cmd spring-boot:run
```

El servidor escucha en `http://localhost:8080`. `GET /api/health` debe responder `{"status":"UP","application":"globalhands"}`.

## API disponible

| Método | Ruta | Acceso | Uso |
| --- | --- | --- | --- |
| `POST` | `/api/auth/register` | Público | Crear cuenta y recibir token |
| `POST` | `/api/auth/login` | Público | Iniciar sesión y recibir token |
| `GET` | `/api/health` | Público | Comprobar que el servidor responde |
| `GET` | `/api/categories` | Público | Listar categorías |
| `GET` | `/api/organizations` | Público | Listar organizaciones |
| `GET` | `/api/organizations/{id}` | Público | Consultar una organización |
| `GET` | `/api/opportunities` | Público | Buscar oportunidades publicadas |
| `GET` | `/api/opportunities/{id}` | Público | Consultar una oportunidad publicada |
| `GET` | `/api/users/me` | Token requerido | Consultar el perfil propio |
| `POST` | `/api/opportunities/{id}/applications` | Token requerido | Solicitar participar |
| `GET` | `/api/applications/me` | Token requerido | Listar solicitudes propias |

La búsqueda acepta `q`, `country`, `city`, `modality`, `categoryId`, `organizationId`, `page` y `size`. Las modalidades disponibles son `presencial`, `online` e `hibrida`.

## Prueba rápida

Registrar una cuenta (los campos se alinean con el formulario de Figma):

```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H 'Content-Type: application/json' \
  -d '{"firstName":"Ana","lastName":"García","email":"ana@example.com","password":"clave1234","country":"España","city":"Madrid"}'
```

La respuesta incluye `accessToken`. Para consultar el perfil, enviar ese token:

```bash
curl http://localhost:8080/api/users/me \
  -H 'Authorization: Bearer PEGA_AQUI_EL_ACCESS_TOKEN'
```

Para ver oportunidades de Madrid:

```bash
curl 'http://localhost:8080/api/opportunities?city=Madrid'
```

## Alcance actual

Incluye registro e inicio de sesión con contraseña cifrada, token JWT, consulta de oportunidades, organizaciones y categorías, perfil y solicitudes. Los filtros de habilidades y disponibilidad, la recuperación de contraseña y el acceso con Google requieren ampliar el esquema o configurar servicios externos; quedan para una fase posterior.
