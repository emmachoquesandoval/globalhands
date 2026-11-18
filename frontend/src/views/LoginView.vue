<script setup>
import { reactive, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import AuthLayout from '@/components/AuthLayout.vue'
import { login } from '@/services/authService'

const form = reactive({ email: '', password: '' })
const router = useRouter()
const route = useRoute()
const error = ref('')
const loading = ref(false)

async function submit() {
  error.value = ''
  loading.value = true
  try {
    await login({ ...form })
    const destination = typeof route.query.redirect === 'string' ? route.query.redirect : '/perfil'
    router.push(destination)
  } catch (cause) {
    error.value = cause.message
  } finally {
    loading.value = false
  }
}
</script>

<template>
  <AuthLayout title="Voluntariado que transforma vidas" subtitle="Encuentra oportunidades de voluntariado, conecta con organizaciones y forma parte del cambio.">
    <div class="auth-form-heading"><span class="eyebrow">Nos alegra verte</span><h2>Iniciar sesión</h2>
      <p>Accede a tu cuenta para continuar colaborando.</p></div>
    <form class="form-stack" @submit.prevent="submit">
      <label class="form-field" for="email">Correo electrónico<input id="email" v-model.trim="form.email" type="email" autocomplete="email" placeholder="tu@email.com" required /></label>
      <label class="form-field" for="password">Contraseña<input id="password" v-model="form.password" type="password" autocomplete="current-password" placeholder="Tu contraseña" required /></label>
      <RouterLink class="text-link align-end" to="/recuperar">¿Has olvidado tu contraseña?</RouterLink>
      <p v-if="error" class="notice notice-error">{{ error }}</p>
      <button class="button button-primary button-wide" type="submit" :disabled="loading">{{ loading ? 'Accediendo…' : 'Iniciar sesión' }}</button>
    </form>
    <div class="auth-divider"><span>¿Todavía no tienes cuenta?</span></div>
    <RouterLink class="button button-outline button-wide" to="/registro">Crear una cuenta</RouterLink>
    <p class="form-note">El acceso con Google se añadirá en una fase posterior.</p>
  </AuthLayout>
</template>
