<script setup>
import { onMounted, ref } from 'vue'
import { request } from '@/services/api'

const profile = ref(null)
const loading = ref(true)
const error = ref('')

onMounted(async () => {
  try {
    profile.value = await request('/users/me')
  } catch (cause) {
    error.value = cause.message
  } finally {
    loading.value = false
  }
})
</script>

<template>
  <main class="page-shell account-page"><span class="eyebrow">Tu espacio</span><h1>Mi perfil</h1>
    <p v-if="loading" class="loading-state">Cargando perfil…</p>
    <p v-else-if="error" class="notice notice-error">{{ error }}</p>
    <section v-else-if="profile" class="profile-card"><div class="profile-avatar">{{ profile.firstName?.charAt(0) }}{{ profile.lastName?.charAt(0) }}</div>
      <div><h2>{{ profile.firstName }} {{ profile.lastName }}</h2><p>{{ profile.email }}</p></div>
      <dl class="profile-details"><div><dt>País</dt><dd>{{ profile.country || 'Sin indicar' }}</dd></div>
        <div><dt>Ciudad</dt><dd>{{ profile.city || 'Sin indicar' }}</dd></div><div><dt>Teléfono</dt><dd>{{ profile.phone || 'Sin indicar' }}</dd></div></dl>
      <RouterLink class="button button-primary" to="/solicitudes">Ver mis solicitudes</RouterLink>
    </section>
  </main>
</template>
