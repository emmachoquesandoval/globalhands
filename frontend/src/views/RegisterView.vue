<script setup>
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import AuthLayout from '@/components/AuthLayout.vue'
import { register } from '@/services/authService'

const form = reactive({ firstName: '', lastName: '', email: '', password: '', confirmPassword: '', country: '', city: '' })
const router = useRouter()
const error = ref('')
const loading = ref(false)

async function submit() {
  error.value = ''
  if (form.password !== form.confirmPassword) {
    error.value = 'Las contraseñas no coinciden.'
    return
  }
  loading.value = true
  try {
    await register({
      firstName: form.firstName,
      lastName: form.lastName,
      email: form.email,
      password: form.password,
      country: form.country,
      city: form.city,
    })
    router.push('/perfil')
  } catch (cause) {
    error.value = cause.message
  } finally {
    loading.value = false
  }
}
</script>

<template>
  <AuthLayout title="Tu compromiso hace la diferencia" subtitle="Únete a una comunidad de personas y organizaciones que trabajan por un mundo más justo, solidario y sostenible.">
    <div class="auth-form-heading"><span class="eyebrow">Empieza hoy</span><h2>Crea tu cuenta</h2>
      <p>Únete a Global Hands y descubre oportunidades para ayudar.</p></div>
    <form class="form-stack" @submit.prevent="submit">
      <div class="form-row">
        <label class="form-field" for="firstName">Nombre<input id="firstName" v-model.trim="form.firstName" autocomplete="given-name" placeholder="Tu nombre" required maxlength="120" /></label>
        <label class="form-field" for="lastName">Apellido<input id="lastName" v-model.trim="form.lastName" autocomplete="family-name" placeholder="Tu apellido" required maxlength="120" /></label>
      </div>
      <label class="form-field" for="registerEmail">Correo electrónico<input id="registerEmail" v-model.trim="form.email" type="email" autocomplete="email" placeholder="tu@email.com" required maxlength="255" /></label>
      <div class="form-row">
        <label class="form-field" for="registerPassword">Contraseña<input id="registerPassword" v-model="form.password" type="password" autocomplete="new-password" placeholder="Mínimo 8 caracteres" required minlength="8" maxlength="72" /></label>
        <label class="form-field" for="confirmPassword">Confirmar contraseña<input id="confirmPassword" v-model="form.confirmPassword" type="password" autocomplete="new-password" placeholder="Repite tu contraseña" required minlength="8" maxlength="72" /></label>
      </div>
      <div class="form-row">
        <label class="form-field" for="country">País<input id="country" v-model.trim="form.country" autocomplete="country-name" placeholder="España" required maxlength="100" /></label>
        <label class="form-field" for="city">Ciudad<input id="city" v-model.trim="form.city" autocomplete="address-level2" placeholder="Tu ciudad" required maxlength="100" /></label>
      </div>
      <p v-if="error" class="notice notice-error">{{ error }}</p>
      <button class="button button-primary button-wide" type="submit" :disabled="loading">{{ loading ? 'Creando cuenta…' : 'Crear cuenta' }}</button>
    </form>
    <p class="auth-switch">¿Ya tienes cuenta? <RouterLink class="text-link" to="/login">Inicia sesión</RouterLink></p>
    <p class="form-note">El registro con Google se añadirá en una fase posterior.</p>
  </AuthLayout>
</template>
