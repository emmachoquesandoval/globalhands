<script setup>
import { ref } from 'vue'
import AuthLayout from '@/components/AuthLayout.vue'

const email = ref('')
const message = ref('')

function submit() {
  message.value = 'La recuperación de contraseña se habilitará cuando configuremos el envío de correo.'
}
</script>

<template>
  <AuthLayout title="Voluntariado que transforma vidas" subtitle="Encuentra oportunidades de voluntariado, conecta con organizaciones y forma parte del cambio.">
    <div class="auth-form-heading"><RouterLink class="text-link" to="/login">← ¿Ya recordaste tu contraseña? Inicia sesión</RouterLink>
      <h2>Recupera tu contraseña</h2><p>Introduce tu correo electrónico y te enviaremos un enlace para restablecerla.</p></div>
    <form class="form-stack" @submit.prevent="submit"><label class="form-field" for="recoveryEmail">Correo electrónico
      <input id="recoveryEmail" v-model.trim="email" type="email" autocomplete="email" placeholder="tu@email.com" required /></label>
      <button class="button button-primary button-wide" type="submit">Solicitar enlace</button></form>
    <p v-if="message" class="notice notice-info recovery-notice">{{ message }}</p>
  </AuthLayout>
</template>
