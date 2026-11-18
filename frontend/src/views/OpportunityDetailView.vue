<script setup>
import { onMounted, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { hasSession } from '@/services/api'
import { applyToOpportunity } from '@/services/applicationService'
import { getOpportunity } from '@/services/opportunityService'
import detailSketch from '@/assets/images/news-volunteer-sketch.png'

const route = useRoute()
const router = useRouter()
const opportunity = ref(null)
const loading = ref(true)
const error = ref('')
const message = ref('')
const notice = ref('')
const sending = ref(false)

onMounted(async () => {
  try {
    opportunity.value = await getOpportunity(route.params.id)
  } catch (cause) {
    error.value = cause.message
  } finally {
    loading.value = false
  }
})

async function apply() {
  if (!hasSession()) {
    router.push({ path: '/login', query: { redirect: route.fullPath } })
    return
  }
  sending.value = true
  notice.value = ''
  try {
    await applyToOpportunity(opportunity.value.id, message.value)
    notice.value = 'Tu solicitud se ha enviado correctamente.'
    message.value = ''
  } catch (cause) {
    notice.value = cause.message
  } finally {
    sending.value = false
  }
}
</script>

<template>
  <main class="page-shell detail-page">
    <RouterLink class="back-link" to="/voluntariados">← Volver a voluntariados</RouterLink>
    <p v-if="loading" class="loading-state">Cargando oportunidad…</p>
    <div v-else-if="error" class="notice notice-error">{{ error }}</div>
    <article v-else-if="opportunity" class="detail-card">
      <div class="detail-art opportunity-art"><img :src="detailSketch" alt="Ilustración provisional de voluntariado" /><span class="sample-badge">Ilustración provisional</span></div>
      <div class="detail-content">
        <div class="tag-row"><span class="tag">{{ opportunity.modality }}</span><span v-for="category in opportunity.categories" :key="category.id" class="tag tag-soft">{{ category.name }}</span></div>
        <h1>{{ opportunity.title }}</h1>
        <RouterLink class="text-link" :to="`/organizaciones/${opportunity.organizationId}`">{{ opportunity.organizationName }} →</RouterLink>
        <p class="detail-location">⌖ {{ [opportunity.city, opportunity.country].filter(Boolean).join(', ') || 'Ubicación por confirmar' }}</p>
        <p class="detail-description">{{ opportunity.description }}</p>
        <dl class="detail-facts"><div><dt>Modalidad</dt><dd>{{ opportunity.modality }}</dd></div>
          <div><dt>Plazas</dt><dd>{{ opportunity.places ?? 'Por confirmar' }}</dd></div>
          <div><dt>Fecha de inicio</dt><dd>{{ opportunity.startDate || 'Por confirmar' }}</dd></div></dl>
        <form class="apply-form" @submit.prevent="apply"><label for="application-message">Cuéntale a la organización por qué te interesa (opcional)</label>
          <textarea id="application-message" v-model="message" maxlength="5000" rows="4" placeholder="Escribe un mensaje breve"></textarea>
          <p v-if="notice" class="notice" :class="notice.includes('correctamente') ? 'notice-success' : 'notice-error'">{{ notice }}</p>
          <button class="button button-primary" type="submit" :disabled="sending">{{ sending ? 'Enviando…' : 'Solicitar participar' }}</button>
        </form>
      </div>
    </article>
  </main>
</template>
