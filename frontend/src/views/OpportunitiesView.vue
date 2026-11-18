<script setup>
import { onMounted, reactive, ref } from 'vue'
import { useRoute } from 'vue-router'
import OpportunityCard from '@/components/OpportunityCard.vue'
import { getCategories, getOpportunities } from '@/services/opportunityService'

const route = useRoute()
const categories = ref([])
const opportunities = ref([])
const total = ref(0)
const page = ref(0)
const loading = ref(false)
const error = ref('')
const filters = reactive({ q: '', city: '', modality: '', categoryId: '' })
const modalities = ['presencial', 'online', 'hibrida']

async function loadResults() {
  loading.value = true
  error.value = ''
  try {
    const result = await getOpportunities({ ...filters, page: page.value, size: 12 })
    opportunities.value = result.content || []
    total.value = result.totalElements || 0
  } catch (cause) {
    error.value = cause.message
    opportunities.value = []
  } finally {
    loading.value = false
  }
}

function applyFilters() {
  page.value = 0
  loadResults()
}

function clearFilters() {
  filters.q = ''
  filters.city = ''
  filters.modality = ''
  filters.categoryId = ''
  applyFilters()
}

function changePage(nextPage) {
  page.value = nextPage
  loadResults()
  window.scrollTo({ top: 430, behavior: 'smooth' })
}

onMounted(async () => {
  filters.q = String(route.query.q || '')
  filters.city = String(route.query.city || '')
  try {
    categories.value = await getCategories()
  } catch (cause) {
    error.value = cause.message
  }
  loadResults()
})
</script>

<template>
  <main>
    <section class="opportunity-hero">
      <span class="eyebrow">Presencial u online, cerca de ti o en cualquier parte</span>
      <h1>Encuentra tu voluntariado</h1>
      <p>Elige una causa y encuentra dónde marcar la diferencia.</p>
      <form class="search-bar" @submit.prevent="applyFilters">
        <label class="search-field"><span aria-hidden="true">⌕</span><input v-model="filters.q" placeholder="Educación, medio ambiente…" aria-label="Buscar por texto" /></label>
        <label class="search-field"><span aria-hidden="true">⌖</span><input v-model="filters.city" placeholder="Ciudad o región" aria-label="Filtrar por ciudad" /></label>
        <button class="button button-primary" type="submit">Buscar</button>
      </form>
    </section>

    <section class="results-layout">
      <aside class="filter-panel">
        <div class="filter-title"><h2>Filtros</h2><button class="text-button" @click="clearFilters">Limpiar</button></div>
        <fieldset>
          <legend>Categorías</legend>
          <label v-for="category in categories" :key="category.id" class="check-row">
            <input v-model="filters.categoryId" type="radio" name="category" :value="String(category.id)" />
            <span>{{ category.name }}</span>
          </label>
        </fieldset>
        <fieldset>
          <legend>Modalidad</legend>
          <label v-for="modality in modalities" :key="modality" class="check-row">
            <input v-model="filters.modality" type="radio" name="modality" :value="modality" />
            <span>{{ modality }}</span>
          </label>
        </fieldset>
        <fieldset class="filter-unavailable">
          <legend>Habilidades y disponibilidad</legend>
          <p>Estos filtros se incorporarán cuando estén disponibles en el backend.</p>
        </fieldset>
        <button class="button button-primary filter-submit" @click="applyFilters">Aplicar filtros</button>
      </aside>

      <div class="results-content">
        <div class="results-heading"><div><span class="eyebrow">Encuentra tu lugar</span><h2>Oportunidades para ti</h2></div>
          <span v-if="!loading" class="results-count">{{ total }} resultados</span></div>
        <p v-if="error" class="notice notice-error">{{ error }}</p>
        <div v-else-if="loading" class="loading-state">Buscando oportunidades…</div>
        <div v-else-if="opportunities.length" class="opportunity-grid">
          <OpportunityCard v-for="item in opportunities" :key="item.id" :opportunity="item" />
        </div>
        <div v-else class="empty-state"><span class="empty-icon">⌕</span><h3>No encontramos oportunidades</h3>
          <p>Prueba con otros filtros o borra la búsqueda.</p><button class="button button-outline" @click="clearFilters">Limpiar filtros</button></div>
        <nav v-if="total > 12" class="pagination" aria-label="Paginación">
          <button class="button button-outline" :disabled="page === 0" @click="changePage(page - 1)">Anterior</button>
          <span>Página {{ page + 1 }} de {{ Math.ceil(total / 12) }}</span>
          <button class="button button-outline" :disabled="(page + 1) * 12 >= total" @click="changePage(page + 1)">Siguiente</button>
        </nav>
      </div>
    </section>
  </main>
</template>
