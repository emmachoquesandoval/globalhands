# GlobalHands

GlobalHands es una aplicación web para consultar oportunidades de voluntariado, conocer organizaciones y solicitar participar. El proyecto está dividido en una interfaz Vue 3 y una API Java con Spring Boot; ambas usan MySQL.

## Requisitos

Instala estas herramientas antes de empezar:

- **Git**, para descargar el proyecto (o descomprime el ZIP del proyecto).
- **Java 21** (JDK).
- **Node.js 22.18 o superior, o 24.12 o superior**, con npm. Comprueba la versión con `node --version` y `npm --version`.
- **MySQL 8.0 o superior**, iniciado y accesible localmente.

Maven no hace falta instalarlo: el backend incluye el Maven Wrapper (`mvnw`/`mvnw.cmd`). Los comandos siguientes funcionan en Windows, macOS y Linux; donde cambia el comando se indica expresamente.

## 1. Obtener el proyecto

Clona el repositorio y entra en la carpeta:

```bash
git clone <URL_DEL_REPOSITORIO>
cd proyecto_final
```

Si recibiste el proyecto como ZIP, descomprímelo y abre una terminal en la carpeta que contiene `globalhands.sql`, `globalhands/` y `frontend/`.

## 2. Preparar MySQL

Inicia el servicio de MySQL. Después ejecuta el script `globalhands.sql` que está en la raíz del proyecto. El script crea la base `globalhands`, las tablas y datos de ejemplo.

Con el cliente de línea de comandos, desde la raíz del proyecto:

```bash
mysql -u root -p < globalhands.sql
```

Introduce la contraseña de MySQL cuando la solicite. Si tu usuario no tiene contraseña, prueba `mysql -u root < globalhands.sql`. También puedes abrir e importar `globalhands.sql` desde MySQL Workbench.

> El script puede volver a ejecutarse para crear las tablas que falten, pero los datos de ejemplo pueden duplicarse o fallar por restricciones únicas. Haz una copia antes de importar sobre una base con datos que quieras conservar.

## 3. Configurar y arrancar la API

La configuración predeterminada espera MySQL en `localhost:3306`, base `globalhands`, usuario `root` y contraseña vacía. Si tu instalación usa otros valores, define estas variables de entorno antes de iniciar:

| Variable | Predeterminado | Uso |
| --- | --- | --- |
| `DB_URL` | `jdbc:mysql://localhost:3306/globalhands` | URL JDBC de MySQL |
| `DB_USERNAME` | `root` | Usuario de MySQL |
| `DB_PASSWORD` | vacío | Contraseña de MySQL |
| `JWT_SECRET` | clave de desarrollo | Secreto Base64 para firmar tokens |
| `FRONTEND_URL` | `http://localhost:5173` | Origen permitido por CORS |

Para un entorno real, cambia `JWT_SECRET` por una clave privada Base64 de al menos 32 bytes. En Linux/macOS puedes generar una con `openssl rand -base64 32`.

Desde la raíz del proyecto, arranca el backend:

**macOS / Linux**

```bash
cd globalhands
./mvnw spring-boot:run
```

**Windows (PowerShell o CMD)**

```bat
cd globalhands
mvnw.cmd spring-boot:run
```

La primera ejecución descarga las dependencias Maven y puede tardar unos minutos. Mantén esta terminal abierta. La API queda disponible en `http://localhost:8080`; puedes comprobarla visitando `http://localhost:8080/api/health`.

### Variables de entorno por sistema

Configúralas en la misma terminal desde la que iniciarás el backend:

**macOS / Linux (bash o zsh)**

```bash
export DB_USERNAME=mi_usuario
export DB_PASSWORD='mi_contraseña'
export JWT_SECRET="$(openssl rand -base64 32)"
```

**Windows PowerShell**

```powershell
$env:DB_USERNAME = "mi_usuario"
$env:DB_PASSWORD = "mi_contraseña"
$env:JWT_SECRET = "PEGA_AQUI_UN_SECRETO_BASE64_DE_32_BYTES"
```

**Windows CMD**

```bat
set DB_USERNAME=mi_usuario
set DB_PASSWORD=mi_contraseña
set JWT_SECRET=PEGA_AQUI_UN_SECRETO_BASE64_DE_32_BYTES
```

Si utilizas los valores predeterminados, no necesitas definirlas.

## 4. Instalar y arrancar la web

Abre una segunda terminal y ejecuta:

```bash
cd frontend
npm install
npm run dev
```

Abre la dirección que indique Vite; normalmente es `http://localhost:5173`. Mantén abiertas las dos terminales (API y web) y MySQL en ejecución mientras uses la aplicación.

La interfaz usa `http://localhost:8080/api` como API por defecto. Si la API está en otra dirección, crea `frontend/.env.local` con:

```dotenv
VITE_API_URL=http://localhost:8080/api
```

Reinicia Vite después de cambiar esta configuración. Si cambias el puerto o el origen de la web, configura también `FRONTEND_URL` en el backend con ese origen.

## Ejecución rápida en dos terminales

Con MySQL configurado y la base de datos importada:

```text
Terminal 1: cd globalhands && ./mvnw spring-boot:run  (Windows: mvnw.cmd spring-boot:run)
Terminal 2: cd frontend && npm install && npm run dev
```

## Solución de problemas

- **No conecta con MySQL:** comprueba que el servicio está iniciado, el puerto y las credenciales. Ajusta `DB_URL`, `DB_USERNAME` y `DB_PASSWORD`.
- **Error de tablas o columnas al iniciar Spring:** importa `globalhands.sql` en la base `globalhands`. Hibernate valida el esquema, pero no lo crea ni lo actualiza.
- **La web no puede llamar a la API:** verifica que el backend responde en `/api/health`, que `VITE_API_URL` apunta a `/api` y que `FRONTEND_URL` coincide con el origen de Vite.
- **Puerto ocupado:** detén el proceso que lo usa o configura otro puerto y actualiza las URLs correspondientes.
- **Permiso denegado al ejecutar `./mvnw`:** en macOS/Linux ejecuta `chmod +x mvnw` dentro de `globalhands` y vuelve a intentarlo. También puedes usar `bash mvnw spring-boot:run`.

## Compilar para producción

Desde `frontend/`, ejecuta `npm run build`; los archivos listos para servir se generan en `frontend/dist/`. Para compilar el backend, desde `globalhands/` ejecuta `./mvnw clean package` (Windows: `mvnw.cmd clean package`). El JAR se genera en `globalhands/target/`.

## Estructura

- `frontend/`: aplicación Vue 3, Vue Router y Vite.
- `globalhands/`: API Spring Boot, Java 21 y Maven.
- `globalhands.sql`: esquema y datos iniciales de MySQL.

