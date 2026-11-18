# Global Hands · Frontend

Aplicación web de Global Hands creada con Vue 3, Vue Router y Vite. La interfaz está en español y consume la API Spring Boot del directorio `../globalhands`.

## Requisitos

- Node.js 22.18 o superior, o 24.12 o superior
- API Spring Boot en ejecución en `http://localhost:8080`
- MySQL ejecutándose con la base `globalhands` preparada para que Spring pueda arrancar

## Arrancar en Code OSS

Desde esta carpeta (`frontend`):

```bash
npm install
npm run dev
```

Abre la dirección local que indique Vite. Usa `http://localhost:5173` para que coincida con el origen CORS configurado en Spring.

En IntelliJ, arranca también `GlobalhandsApplication`. Los dos servidores deben permanecer abiertos: Vue sirve la interfaz en el puerto 5173 y Spring proporciona los datos en el puerto 8080.

La URL de API predeterminada está definida en `src/services/api.js`. Si Spring usa otra dirección, crea un archivo `.env.local` en esta carpeta con:

```text
VITE_API_URL=http://localhost:8080/api
```

## Páginas

- `/`: inicio y oportunidades destacadas
- `/voluntariados`: listado, búsqueda y filtros compatibles con la API
- `/voluntariados/:id`: detalle y solicitud para participar
- `/organizaciones` y `/organizaciones/:id`: listado y detalle con oportunidades
- `/login` y `/registro`: autenticación conectada al backend
- `/perfil` y `/solicitudes`: páginas privadas que necesitan iniciar sesión
- `/recuperar`: vista informativa; el backend aún no tiene recuperación de contraseña

## Organización del código

- `src/components`: cabecera, pie, tarjeta de oportunidad y diseño de autenticación
- `src/services`: comunicación HTTP y funciones de cada área de la API
- `src/views`: páginas de la aplicación
- `src/router`: rutas y protección de páginas privadas
- `src/assets/styles.css`: estilos globales y adaptación a móvil

## Ilustraciones provisionales

Los dibujos provisionales están en `src/assets/images/` y se importan por separado:

- `volunteer-hero-sketch.png`: imagen principal de la portada
- `news-volunteer-sketch.png`: noticia destacada y tarjetas de voluntariado
- `community-about-sketch.png`: bloque «Sobre nosotros»
- `auth-coast-sketch.png`: panel lateral de acceso, registro y recuperación

Para sustituir uno, guarda la nueva imagen con el mismo nombre y formato en esa carpeta. Vite mantendrá las vistas conectadas sin cambiar su estructura.

Google, habilidades y disponibilidad requieren servicios o cambios del backend y están indicados en la interfaz como pendientes.
