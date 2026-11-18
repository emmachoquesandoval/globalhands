<script setup>
import logo from '@/assets/globalhands-logo.png'
</script>

<template>
  <footer class="site-footer">
    <div class="footer-main">
      <div class="footer-about">
        <RouterLink class="brand brand-footer" to="/">
          <img class="brand-logo" :src="logo" alt="" />
        </RouterLink>
        <p>Conecta, colabora y cambia el mundo. Encuentra una causa que te mueva.</p>
      </div>
      <div class="footer-column">
        <h2>Ayuda</h2>
        <RouterLink to="/#faq">Preguntas frecuentes</RouterLink>
        <a href="mailto:info@globalhands.test">Contacto</a>
        <a href="#">Privacidad</a>
        <a href="#">Términos</a>
      </div>
      <div class="footer-column">
        <h2>Enlaces</h2>
        <RouterLink to="/voluntariados">Voluntariado</RouterLink>
        <RouterLink to="/#access-areas">Empleo</RouterLink>
        <RouterLink to="/#access-areas">Donaciones</RouterLink>
        <RouterLink to="/organizaciones">Organizaciones</RouterLink>
      </div>
      <div class="footer-column">
        <h2>Legales</h2>
        <RouterLink to="/#about">Transparencia</RouterLink>
        <RouterLink to="/login">Iniciar sesión</RouterLink>
        <RouterLink to="/#news">Blog</RouterLink>
      </div>
    </div>
    <div class="footer-bottom"><span>© 2026 Global Hands. Todos los derechos reservados.</span><span>Política de privacidad · Términos de uso · Cookies</span></div>
  </footer>
</template>
