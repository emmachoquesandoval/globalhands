<script setup>
import { onMounted, ref } from 'vue'
import { getMyApplications } from '@/services/applicationService'

const applications = ref([])
const loading = ref(true)
const error = ref('')

onMounted(async () => {
  try {
    applications.value = await getMyApplications()
  } catch (cause) {
    error.value = cause.message
  } finally {
    loading.value = false
  }
})
</script>

<template>
  <main class="page-shell account-page"><span class="eyebrow">Tu participación</span><h1>Mis solicitudes</h1>
    <p v-if="loading" class="loading-state">Cargando solicitudes…</p>
    <p v-else-if="error" class="notice notice-error">{{ error }}</p>
    <div v-else-if="applications.length" class="application-list">
      <article v-for="application in applications" :key="application.id" class="application-card">
        <div><span class="eyebrow">Solicitud {{ application.id }}</span><h2>{{ application.opportunityTitle }}</h2>
          <p v-if="application.message">{{ application.message }}</p><small>Enviada el {{ new Date(application.submittedAt).toLocaleDateString('es-ES') }}</small></div>
        <span class="status-pill">{{ application.status }}</span>
      </article>
    </div>
    <div v-else class="empty-state"><span class="empty-icon">♡</span><h3>Aún no has enviado solicitudes</h3>
      <p>Explora las oportunidades y encuentra una causa para ti.</p><RouterLink class="button button-primary" to="/voluntariados">Explorar voluntariados</RouterLink></div>
  </main>
</template>
