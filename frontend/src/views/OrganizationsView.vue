<script setup>
import { onMounted, ref } from 'vue'
import { getOrganizations } from '@/services/organizationService'

const organizations = ref([])
const loading = ref(true)
const error = ref('')

onMounted(async () => {
  try {
    organizations.value = await getOrganizations()
  } catch (cause) {
    error.value = cause.message
  } finally {
    loading.value = false
  }
})
</script>

<template>
  <main>
    <section class="page-hero"><span class="eyebrow">Juntas llegamos más lejos</span><h1>Organizaciones solidarias</h1>
      <p>Conoce a las organizaciones que convierten la colaboración en cambios reales.</p></section>
    <section class="section page-shell">
      <p v-if="error" class="notice notice-error">{{ error }}</p>
      <p v-else-if="loading" class="loading-state">Cargando organizaciones…</p>
      <div v-else-if="organizations.length" class="organization-grid">
        <article v-for="organization in organizations" :key="organization.id" class="organization-card">
          <div class="organization-mark">{{ organization.name?.charAt(0) || 'G' }}</div>
          <span class="eyebrow">Organización colaboradora</span><h2>{{ organization.name }}</h2>
          <p>{{ organization.description || 'Una organización comprometida con generar un impacto positivo.' }}</p>
          <p class="card-location">⌖ {{ [organization.city, organization.country].filter(Boolean).join(', ') || 'Ubicación por confirmar' }}</p>
          <RouterLink class="text-link" :to="`/organizaciones/${organization.id}`">Ver oportunidades <span>→</span></RouterLink>
        </article>
      </div>
      <div v-else class="empty-state"><span class="empty-icon">♡</span><h3>Aún no hay organizaciones</h3><p>Las organizaciones aparecerán aquí cuando estén disponibles.</p></div>
    </section>
  </main>
</template>
