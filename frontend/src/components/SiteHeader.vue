<script setup>
import { onBeforeUnmount, onMounted, ref } from 'vue'
import { RouterLink, useRouter } from 'vue-router'
import { getSessionUser } from '@/services/api'
import { logout } from '@/services/authService'
import logo from '@/assets/globalhands-logo.png'

const menuOpen = ref(false)
const router = useRouter()
const user = ref(getSessionUser())

function updateUser() {
  user.value = getSessionUser()
}

onMounted(() => window.addEventListener('globalhands:session-changed', updateUser))
onBeforeUnmount(() => window.removeEventListener('globalhands:session-changed', updateUser))

function signOut() {
  logout()
  router.push('/')
}
</script>

<template>
  <header class="site-header">
    <RouterLink class="brand" to="/" aria-label="Global Hands, inicio">
      <img class="brand-logo" :src="logo" alt="" />
    </RouterLink>

    <button
      class="menu-toggle"
      type="button"
      :aria-expanded="menuOpen"
      aria-label="Abrir menú"
      @click="menuOpen = !menuOpen"
    >
      <span></span><span></span><span></span>
    </button>

    <nav class="site-nav" :class="{ 'is-open': menuOpen }" aria-label="Navegación principal">
      <RouterLink to="/voluntariados" @click="menuOpen = false">Voluntariado</RouterLink>
      <RouterLink to="/#access-areas" @click="menuOpen = false">Empleo</RouterLink>
      <RouterLink to="/#access-areas" @click="menuOpen = false">Donaciones</RouterLink>
      <RouterLink to="/#about" @click="menuOpen = false">Transparencia</RouterLink>
      <RouterLink v-if="user" to="/solicitudes" @click="menuOpen = false">Mis solicitudes</RouterLink>
      <RouterLink v-if="user" to="/perfil" @click="menuOpen = false">Mi perfil</RouterLink>
      <button v-if="user" class="button button-small button-outline" @click="signOut">Cerrar sesión</button>
      <RouterLink v-else class="button button-small button-primary" to="/login" @click="menuOpen = false">
        Acceso / Registro
      </RouterLink>
    </nav>
  </header>
</template>
