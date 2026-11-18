<script setup>
import { onMounted, ref } from 'vue'
import { useRoute } from 'vue-router'
import OpportunityCard from '@/components/OpportunityCard.vue'
import { getOpportunities } from '@/services/opportunityService'
import { getOrganization } from '@/services/organizationService'

const route = useRoute()
const organization = ref(null)
const opportunities = ref([])
const loading = ref(true)
const error = ref('')

onMounted(async () => {
  try {
    const [organizationResult, opportunitiesResult] = await Promise.all([
      getOrganization(route.params.id),
      getOpportunities({ organizationId: route.params.id, page: 0, size: 50 }),
    ])
    organization.value = organizationResult
    opportunities.value = opportunitiesResult.content || []
  } catch (cause) {
    error.value = cause.message
  } finally {
    loading.value = false
  }
})
</script>

<template>
  <main class="page-shell detail-page">
    <RouterLink class="back-link" to="/organizaciones">← Ver organizaciones</RouterLink>
    <p v-if="loading" class="loading-state">Cargando organización…</p>
    <div v-else-if="error" class="notice notice-error">{{ error }}</div>
    <template v-else-if="organization">
      <section class="organization-detail-hero"><div class="organization-mark organization-mark-large">{{ organization.name?.charAt(0) || 'G' }}</div>
        <div><span class="eyebrow">Organización colaboradora</span><h1>{{ organization.name }}</h1>
          <p class="card-location">⌖ {{ [organization.city, organization.country].filter(Boolean).join(', ') || 'Ubicación por confirmar' }}</p></div>
      </section>
      <section class="detail-section"><h2>Sobre la organización</h2><p>{{ organization.description || 'Esta organización trabaja para generar un impacto positivo.' }}</p>
        <a v-if="organization.email" class="text-link" :href="`mailto:${organization.email}`">Contactar a la organización →</a></section>
      <section class="detail-section"><div class="section-heading"><div><span class="eyebrow">Participa</span><h2>Oportunidades abiertas</h2></div></div>
        <div v-if="opportunities.length" class="opportunity-grid"><OpportunityCard v-for="item in opportunities" :key="item.id" :opportunity="item" /></div>
        <div v-else class="empty-state compact-empty"><h3>No hay oportunidades publicadas ahora</h3><p>Vuelve pronto para consultar nuevas convocatorias.</p></div></section>
    </template>
  </main>
</template>
