<script setup>
import { onMounted, ref } from 'vue'
import { useRouter } from 'vue-router'
import OpportunityCard from '@/components/OpportunityCard.vue'
import heroSketch from '@/assets/images/volunteer-hero-sketch.png'
import newsSketch from '@/assets/images/news-volunteer-sketch.png'
import aboutSketch from '@/assets/images/community-about-sketch.png'
import { getCategories, getOpportunities } from '@/services/opportunityService'
import { getOrganizations } from '@/services/organizationService'

const router = useRouter()
const searchText = ref('')
const city = ref('')
const opportunities = ref([])
const categories = ref([])
const organizations = ref([])
const opportunitiesError = ref('')
const organizationsError = ref('')
const loading = ref(true)

const newsItems = [
  { type: 'Blog', date: 'Hace 4 días', title: '5 formas de apoyar a organizaciones sin salir de casa' },
  { type: 'Actualización', date: 'Hace 1 semana', title: 'Nuevas convocatorias para colaborar con entidades sociales' },
  { type: 'Transparencia', date: 'Hace 10 días', title: 'Guía para elegir proyectos con información clara y seguimiento' },
]

const questions = [
  { question: '¿Cómo sé que una oportunidad es segura?', answer: 'Lee las condiciones del proyecto y revisa la información de contacto antes de inscribirte. Global Hands presenta las oportunidades publicadas por las organizaciones.' },
  { question: '¿Puedo apuntarme si no tengo experiencia previa?', answer: 'Sí. Muchas oportunidades están pensadas para personas que quieren empezar a participar y aprender en el camino.' },
  { question: '¿Cómo funciona el proceso de inscripción?', answer: 'Selecciona una oportunidad, consulta sus detalles y envía tu solicitud. Podrás revisar tus solicitudes desde tu perfil.' },
  { question: '¿Puedo donar o apoyar sin ser voluntario?', answer: 'Las campañas de donación todavía no están disponibles en esta primera versión de la plataforma.' },
]

onMounted(async () => {
  const [opportunityResult, categoryResult, organizationResult] = await Promise.allSettled([
    getOpportunities({ page: 0, size: 3 }),
    getCategories(),
    getOrganizations(),
  ])

  if (opportunityResult.status === 'fulfilled') opportunities.value = opportunityResult.value.content || []
  else opportunitiesError.value = opportunityResult.reason.message

  if (categoryResult.status === 'fulfilled') categories.value = categoryResult.value

  if (organizationResult.status === 'fulfilled') organizations.value = organizationResult.value.slice(0, 3)
  else organizationsError.value = organizationResult.reason.message

  loading.value = false
})

function search() {
  router.push({ path: '/voluntariados', query: { q: searchText.value, city: city.value } })
}

function searchCategory(categoryId) {
  router.push({ path: '/voluntariados', query: { categoryId } })
}
</script>

<template>
  <main class="figma-home">
    <section class="figma-hero">
      <img class="figma-hero-image" :src="heroSketch" alt="Voluntarios plantan juntos un árbol" />
      <div class="figma-hero-shade"></div>
      <form class="figma-search" @submit.prevent="search">
        <label class="figma-search-field">
          <span aria-hidden="true">⌕</span><input v-model="searchText" placeholder="¿Qué causa te interesa?" aria-label="Buscar causa o voluntariado" />
          <span class="search-divider"></span><strong>Voluntariado</strong>
        </label>
        <label class="figma-search-field">
          <span aria-hidden="true">⌖</span><input v-model="city" placeholder="Ciudad o región" aria-label="Ciudad o región" />
        </label>
        <button class="button button-primary" type="submit">Buscar <span aria-hidden="true">→</span></button>
      </form>
      <div class="figma-hero-copy">
        <span class="eyebrow">Global Hands · Conecta y colabora</span>
        <h1>Oportunidades para<br />cambiar el mundo</h1>
        <p>La causa sí que importa.</p>
      </div>
      <div class="quick-cards" id="access">
        <RouterLink class="quick-card" to="/voluntariados">
          <span class="quick-icon">♡</span><h2>Voluntariado</h2>
          <p>Participa en proyectos locales, campañas y actividades de apoyo.</p>
          <span class="quick-link">Ver oportunidades <span>→</span></span>
        </RouterLink>
        <a class="quick-card quick-card-muted" href="#access-areas" title="Disponible próximamente">
          <span class="quick-icon">✳</span><h2>Empleos</h2>
          <p>Encuentra empleos con propósito en el tercer sector.</p>
          <span class="quick-link">Próximamente <span>→</span></span>
        </a>
        <a class="quick-card quick-card-muted" href="#access-areas" title="Disponible próximamente">
          <span class="quick-icon">♡</span><h2>Donaciones</h2>
          <p>Descubre campañas solidarias y formas de apoyar.</p>
          <span class="quick-link">Próximamente <span>→</span></span>
        </a>
      </div>
    </section>

    <section id="access-areas" class="figma-section area-section">
      <div class="figma-section-heading">
        <span class="eyebrow">Acceso rápido</span><h2>Explora por área</h2>
        <p>Encuentra la forma de participar que mejor se adapte a tus intereses y disponibilidad.</p>
      </div>
      <div v-if="categories.length" class="area-links">
        <button v-for="category in categories" :key="category.id" class="area-link" @click="searchCategory(category.id)">
          <span class="area-dot">✳</span><span>{{ category.name }}</span><span class="area-arrow">→</span>
        </button>
      </div>
      <p v-else class="section-note">Las categorías aparecerán aquí cuando el backend esté conectado.</p>
    </section>

    <section id="news" class="figma-section news-section">
      <div class="figma-section-heading">
        <span class="eyebrow">Noticias</span><h2>Noticias y actualizaciones</h2>
        <p>Lo último sobre proyectos, organizaciones y tendencias del sector.</p>
      </div>
      <div class="news-grid">
        <article class="featured-news">
          <div class="news-image-wrap"><img :src="newsSketch" alt="Voluntaria pasea con un perro en un entorno natural" /><span class="sample-badge">Imagen provisional</span></div>
          <div class="featured-news-copy"><span class="news-meta">Comunidad · Lectura de 4 min</span>
            <h3>Colaborar también empieza con pequeños gestos cotidianos</h3>
            <p>Historias de personas que aportan su tiempo y talento a causas cercanas.</p>
            <span class="text-link">Leer más <span>→</span></span></div>
        </article>
        <div class="news-list">
          <article v-for="item in newsItems" :key="item.title" class="news-list-item">
            <div class="news-item-meta"><span>{{ item.type }}</span><time>{{ item.date }}</time></div>
            <h3>{{ item.title }}</h3><span class="text-link">Leer más <span>→</span></span>
          </article>
        </div>
      </div>
      <p class="section-note">Las noticias son contenido provisional de diseño.</p>
    </section>

    <section id="about" class="about-section">
      <div class="about-copy">
        <span class="eyebrow">Sobre nosotros</span>
        <h2>Conecta a personas, organizaciones y causas para construir un impacto más cercano y real.</h2>
        <p>Global Hands es un espacio de encuentro para el voluntariado, el empleo con sentido y las donaciones con transparencia. Queremos que encontrar una oportunidad sea fácil, claro y accesible para todos.</p>
        <div class="about-actions"><RouterLink class="button button-light" to="/voluntariados">Conoce nuestras oportunidades</RouterLink>
          <RouterLink class="button button-outline-light" to="/organizaciones">Organizaciones</RouterLink></div>
      </div>
      <div class="about-image-wrap"><img :src="aboutSketch" alt="Dos voluntarios comparten una actividad de jardinería con una mujer mayor" /><span class="image-caption">Una comunidad que se cuida</span></div>
    </section>

    <section id="organizations" class="figma-section organizations-section">
      <div class="figma-section-heading heading-inline">
        <div><span class="eyebrow">Organizaciones</span><h2>Organizaciones que publican oportunidades</h2>
          <p>Conoce a las entidades que buscan apoyo y talento.</p></div>
        <RouterLink class="text-link" to="/organizaciones">Ver todas <span>→</span></RouterLink>
      </div>
      <p v-if="organizationsError" class="notice notice-error">{{ organizationsError }}</p>
      <div v-else-if="loading" class="section-note">Cargando organizaciones…</div>
      <div v-else-if="organizations.length" class="figma-org-grid">
        <RouterLink v-for="organization in organizations" :key="organization.id" class="figma-org-card" :to="`/organizaciones/${organization.id}`">
          <span class="org-initial">{{ organization.name?.charAt(0) || 'G' }}</span>
          <h3>{{ organization.name }}</h3><p>{{ organization.description || 'Una organización comprometida con generar un impacto positivo.' }}</p>
          <span class="text-link">Ver perfil <span>→</span></span>
        </RouterLink>
      </div>
      <div v-else class="section-note">{{ opportunitiesError || 'Las organizaciones se mostrarán cuando Spring esté ejecutándose.' }}</div>
    </section>

    <section id="faq" class="figma-section faq-section">
      <div class="figma-section-heading"><span class="eyebrow">FAQ</span><h2>Preguntas frecuentes</h2>
        <p>Respuestas rápidas a las dudas más comunes antes de empezar.</p></div>
      <div class="faq-grid">
        <details v-for="item in questions" :key="item.question" class="faq-item">
          <summary>{{ item.question }}<span aria-hidden="true">⌄</span></summary><p>{{ item.answer }}</p>
        </details>
      </div>
    </section>
  </main>
</template>
